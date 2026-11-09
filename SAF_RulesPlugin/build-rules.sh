#!/usr/bin/env bash
# Build the SAF Model Rules plugin (com.haarer.saf.rules) and stage it into
# SAF_Plugin/plugins/com.haarer.saf.rules/ so that genplugin.groovy includes it
# in the resource manifest (descriptor.xml) and in SAF_Plugin.zip.
#
# The build is hermetic (no Cameo installation required): the Java core is
# compiled against minimal compile-time stubs of the Cameo SDK (stubs/) and
# the Groovy jar from Maven Central. The stub API surface is identical across
# Cameo versions, so one stub set works for all targets.
#
# The Groovy rules are NOT compiled into the jar: they are shipped as sources
# (rules/*.groovy next to the jar) and compiled by the plugin at runtime
# (with hot reload). They are syntax-checked here at build time.
#
# Usage:
#   bash SAF_RulesPlugin/build-rules.sh <pluginVersion> [automatonVersion]
#
#   <pluginVersion>    version label, e.g. 'main', 'latest' or a release tag
#   [automatonVersion] required Automaton plugin version (default: 2026x)
#
# Output (staged for genplugin.groovy):
#   SAF_Plugin/plugins/com.haarer.saf.rules/cameo-saf-rules-<pluginVersion>.jar
#   SAF_Plugin/plugins/com.haarer.saf.rules/plugin.xml
#   SAF_Plugin/plugins/com.haarer.saf.rules/rules/*.groovy

set -euo pipefail
exec 2>&1  # merge stderr into stdout (captured as one stream by the caller)

VERSION="${1:?usage: build-rules.sh <pluginVersion> [automatonVersion]}"
AUTOMATON="${2:-2026x}"
RELEASE=17

HERE="$(cd "$(dirname "$0")" && pwd)"
ROOT="$(dirname "$HERE")"
BUILD="$HERE/build"
WORK="$BUILD/work"
DIST="$ROOT/SAF_Plugin/plugins/com.haarer.saf.rules"
JAR_NAME="cameo-saf-rules-${VERSION}.jar"

# Groovy bundled with Cameo 2026x (via the Automaton plugin); also the
# compiler used for the rule syntax check.
GROOVY_VERSION="5.0.0"
GROOVY_JAR="$BUILD/groovy-${GROOVY_VERSION}.jar"
MAVEN_URL="https://repo1.maven.org/maven2/org/apache/groovy/groovy/${GROOVY_VERSION}/groovy-${GROOVY_VERSION}.jar"

echo "=== Building SAF Model Rules (version ${VERSION}, automaton ${AUTOMATON}) ==="
rm -rf "$WORK"
mkdir -p "$WORK/stubs" "$WORK/classes"

# 1. Groovy jar (compile classpath for the groovy.lang.GroovyClassLoader import)
if [ ! -f "$GROOVY_JAR" ]; then
  echo "--- Downloading groovy-${GROOVY_VERSION}.jar ---"
  curl -sfL "$MAVEN_URL" -o "$GROOVY_JAR" || { echo "ERROR: download failed: $MAVEN_URL" >&2; exit 1; }
fi

# 2. Compile the Cameo SDK stubs (compile-only)
STUB_SOURCES=$(find "$HERE/stubs" -name '*.java' | sort)
echo "--- Compiling $(echo "$STUB_SOURCES" | wc -l) stub files (release ${RELEASE}) ---"
javac --release "$RELEASE" -d "$WORK/stubs" $STUB_SOURCES

# 3. Compile the Java core against stubs + Groovy
CORE_SOURCES=$(find "$HERE/src" -name '*.java' | sort)
echo "--- Compiling $(echo "$CORE_SOURCES" | wc -l) core files (release ${RELEASE}) ---"
javac --release "$RELEASE" -cp "$WORK/stubs:$GROOVY_JAR" -d "$WORK/classes" $CORE_SOURCES

# 4. Package the plugin jar
echo "--- Packaging ${JAR_NAME} ---"
jar --create --file "$WORK/${JAR_NAME}" -C "$WORK/classes" .

# 5. Syntax-check the Groovy rules (shipped as sources, compiled at runtime)
echo "--- Syntax-checking Groovy rules ---"
java -cp "$WORK/stubs:$GROOVY_JAR:$WORK/classes" \
  org.codehaus.groovy.tools.FileSystemCompiler -d "$WORK/rulecheck" "$HERE"/rules/*.groovy

# 6. Stage into the SAF_Plugin tree (picked up by genplugin.groovy: manifest + zip)
rm -rf "$DIST"
mkdir -p "$DIST/rules"
cp "$WORK/${JAR_NAME}" "$DIST/"
sed -e "s|\${version}|${VERSION}|g" -e "s|\${automatonVersion}|${AUTOMATON}|g" \
  "$HERE/plugin.xml" > "$DIST/plugin.xml"
cp "$HERE"/rules/*.groovy "$DIST/rules/"

echo "=== Staged: ${DIST} ==="
ls -la "$DIST" "$DIST/rules"
