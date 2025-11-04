#!/usr/bin/env groovy
// GitHub Copilot

import groovy.xml.MarkupBuilder
import groovy.io.FileType
import groovy.xml.XmlSlurper
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.io.FileOutputStream

// --- Constants / Header content ---
def OUTPUT_FILENAME = 'SAF_Plugin/data/resourcemanager/MDR_Profile_SAF_75377_descriptor.xml'

// resourceDescriptor attributes
def RESOURCE_ATTRS = [
	critical       : 'false',
	date           : '25-11-04',
	description    : "SAF Profile TdSE2025,\nProvider Name: GfSE SAF Working Group",
	group          : 'mac Resource',
	homePage       : 'https://saf.gfse.org/version/TdSE2025',
	id             : '75377',
	mdVersionMax   : 'higher',
	mdVersionMin   : '19.0',
	name           : 'SAF Profile',
	product        : 'SAF Profile',
	restartMagicdraw: 'true',
	type           : 'Profile'
]

// version and provider
def VERSION = [human: 'TdSE2025', internal: '100011', resource: '1000110']
def PROVIDER = [email: 'saf@gfse.org', homePage: 'https://saf.gfse.org/version/TdSE2025', name: 'GfSE SAF Working Group']

// requiredResource
def REQUIRED = [id: '1440', name: 'SysML', minVersion: [internal: '1900010', human: '19.0 SP3']]

// editions list
def EDITIONS = [
	'Reader',
	'Community',
	'Standard',
	'Professional Java',
	'Professional C++',
	'Professional C#',
	'Professional',
	'Architect',
	'Enterprise'
]

// installation file list (each entry used for both from and to)
// scan directories are inside the SAF_Plugin folder; strip that prefix for XML output
def BASE_SCAN_ROOT = 'SAF_Plugin'
def INSTALL_SCAN_DIRS = [
    'data/defaults',
    'profiles',
    'samples',
    'templates'
]

def INSTALLATION_FILES = []
def baseDir = new File(BASE_SCAN_ROOT)
def baseCanon = baseDir.exists() ? baseDir.canonicalPath.replace('\\','/') : new File('.').canonicalPath.replace('\\','/')

INSTALL_SCAN_DIRS.each { dirPath ->
    def dir = new File(baseDir, dirPath)
    if (dir.exists() && dir.isDirectory()) {
        dir.eachFileRecurse(FileType.FILES) { f ->
            def full = f.canonicalPath.replace('\\','/')
            // produce path relative to BASE_SCAN_ROOT so the XML entries do not include the prefix
            def rel = full.replaceFirst("^${baseCanon}/?", '')
            INSTALLATION_FILES << rel
        }
    }
}

// remove duplicates and sort for deterministic output
INSTALLATION_FILES = INSTALLATION_FILES.unique().sort()

// --- Determine output directory ---
// If a directory path is provided as first arg, use it; otherwise use the current directory.
def outDir = args && args[0] ? new File(args[0]) : new File('.')
if (!outDir.exists()) outDir.mkdirs()
def outFile = new File(outDir, OUTPUT_FILENAME)

// --- Build XML ---
outFile.withWriter('UTF-8') { writer ->
    // exact XML declaration with standalone="yes"
    writer.write('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n')
    def xml = new MarkupBuilder(writer)
    xml.doubleQuotes = true

    xml.resourceDescriptor(RESOURCE_ATTRS) {
        version(human: VERSION.human, internal: VERSION.internal, resource: VERSION.resource)
        provider(email: PROVIDER.email, homePage: PROVIDER.homePage, name: PROVIDER.name)
        EDITIONS.each { e -> mkp.yieldUnescaped("\n\t\t"); edition(e) } // create multiple <edition> elements
        // required resource
        requiredResource(id: REQUIRED.id, name: REQUIRED.name) {
            minVersion(internal: REQUIRED.minVersion.internal, human: REQUIRED.minVersion.human)
        }
        // installation files
        installation {
            INSTALLATION_FILES.each { f ->
                file(from: f, to: f)
            }
        }
    }
}

println "Wrote ${outFile.absolutePath}"

// create a zip of the SAF_Plugin tree (like the Makefile)
// zip will be created in the chosen outDir as SAF_Plugin.zip
def zipFile = new File(outDir, 'SAF_Plugin.zip')
// the plugin directory is expected under outDir (since descriptor was written there)
// fallback to local BASE_SCAN_ROOT if not present in outDir
def pluginDir = new File(outDir, BASE_SCAN_ROOT)
if (!pluginDir.exists()) pluginDir = new File(BASE_SCAN_ROOT)

if (pluginDir.exists() && pluginDir.isDirectory()) {
    zipFile.withOutputStream { fos ->
        ZipOutputStream zos = new ZipOutputStream(fos)
        try {
            pluginDir.eachFileRecurse(FileType.FILES) { f ->
                // path inside zip must be relative to the pluginDir and use forward slashes
                def relPath = f.canonicalPath.replace('\\','/').replaceFirst("^" + pluginDir.canonicalPath.replace('\\','/') + "/?", '')
                // skip adding the zip into itself if outDir == pluginDir parent
                if (relPath == zipFile.name) return

                // If this is a descriptor.xml, only include when category == "SAF Diagram"
                if (relPath.tokenize('/')[-1] == 'descriptor.xml') {
                    def includeDescriptor = false
                    try {
                        def root = new XmlSlurper().parse(f)
                        // find a <diagram> element anywhere in the document and read its category attribute
                        def diagramNode = root.'**'.find { it.name() == 'diagram' }
                        def categoryAttr = diagramNode ? diagramNode.@category?.toString() : null
                        includeDescriptor = (categoryAttr == 'SAF Diagrams')
                    } catch (Exception parseEx) {
                        println "Skipping ${relPath}: failed to parse descriptor XML (${parseEx.message})"
                        includeDescriptor = false
                    }
                    if (!includeDescriptor) {
                        println "Skipping descriptor (not 'SAF Diagrams'): ${relPath}"
                        return
                    }
                }

                ZipEntry entry = new ZipEntry(relPath)
                zos.putNextEntry(entry)
                f.withInputStream { is ->
                    byte[] buffer = new byte[8192]
                    int len
                    while ((len = is.read(buffer)) > 0) {
                        zos.write(buffer, 0, len)
                    }
                }
                zos.closeEntry()
            }
        } finally {
            zos.finish()
            zos.close()
        }
    }
    println "Created zip: ${zipFile.absolutePath}"
} else {
    println "No SAF_Plugin directory found to zip (looked at: ${pluginDir.absolutePath})"
}
