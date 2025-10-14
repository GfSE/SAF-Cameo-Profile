
SHELL = sh

CP="C:\Program Files\Git\usr\bin\cp.exe"
LS="C:\Program Files\Git\usr\bin\ls.exe"
RM="C:\Program Files\Git\usr\bin\rm.exe"
ZIP="C:\Program Files\7-Zip\7z.exe"

CP="cp"
LS="ls"
RM="rm"
ZIP="7z"


SAF_SOURCE=../saf/

MODELS= $(SAF_SOURCE)/SAF_Profile.mdzip $(SAF_SOURCE)/SAF_SCM_Profile.mdzip $(SAF_SOURCE)/SAF_UAFTracing_Profile.mdzip
SAMPLES= $(SAF_SOURCE)/SAF_FFDS.mdzip $(SAF_SOURCE)/SAF_Library.mdzip $(SAF_SOURCE)/Library.mdzip $(SAF_SOURCE)/SAF_FFDS_NAF.mdzip


.PRECIOUS: $(MODELS) $(SAMPLES)

all: SAF_Plugin/SAF_Plugin.zip SAF_Plugin/SAF_DevPlugin.zip
dev: SAF_Plugin/SAF_DevPlugin.zip

clean:
	$(RM) SAF_Plugin/SAF_Plugin.zip SAF_Plugin/SAF_DevPlugin.zip
	

#to do : export these from TWC automagically and set the system profile flag before.
SAF_Plugin/SAF_Plugin.zip: SAF_Plugin/profiles/SAF_UAFTracing_Profile.mdzip SAF_Plugin/profiles/SAF_Profile.mdzip SAF_Plugin/profiles/SAF_SCM_Profile.mdzip SAF_Plugin/samples/SAF/SAF_FFDS.mdzip SAF_Plugin/samples/SAF/SAF_FFDS_NAF.mdzip SAF_Plugin/samples/SAF/SAF_Library.mdzip SAF_Plugin/samples/SAF/Library.mdzip SAF_Plugin/data/resourcemanager/MDR_Profile_SAF_75377_descriptor.xml
	(cd SAF_Plugin ;  $(ZIP) a SAF_Plugin.zip data/resourcemanager/MDR_Profile_SAF_75377_descriptor.xml data/defaults profiles samples templates )

SAF_Plugin/SAF_DevPlugin.zip: SAF_Plugin/data/resourcemanager/MDR_Profile_SAF_75378_descriptor.xml
	(cd SAF_Plugin ;  $(ZIP) a SAF_DevPlugin.zip data/resourcemanager/MDR_Profile_SAF_75378_descriptor.xml data/defaults )

SAF_Plugin/profiles/SAF_Profile.mdzip:  $(SAF_SOURCE)/SAF_Profile.mdzip
	$(CP) $< $@

SAF_Plugin/profiles/SAF_SCM_Profile.mdzip: $(SAF_SOURCE)/SAF_SCM_Profile.mdzip
	$(CP) $< $@

SAF_Plugin/profiles/SAF_UAFTracing_Profile.mdzip: $(SAF_SOURCE)/SAF_UAFTracing_Profile.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/SAF_FFDS.mdzip:  $(SAF_SOURCE)/SAF_FFDS.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/SAF_FFDS_NAF.mdzip:  $(SAF_SOURCE)/SAF_FFDS_NAF.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/Library.mdzip:  $(SAF_SOURCE)/Library.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/SAF_Library.mdzip:  $(SAF_SOURCE)/SAF_Library.mdzip
	$(CP) $< $@


