
SHELL = sh
CP="C:\Program Files\Git\usr\bin\cp.exe"
LS="C:\Program Files\Git\usr\bin\ls.exe"
RM="C:\Program Files\Git\usr\bin\rm.exe"
ZIP="C:\Program Files\7-Zip\7z.exe"

SAF_SOURCE=../saf/

MODELS= $(SAF_SOURCE)/SAF_Profile.mdzip $(SAF_SOURCE)/SAF_SCM_Profile.mdzip
SAMPLES= $(SAF_SOURCE)/SAF_FFDS.mdzip $(SAF_SOURCE)/SAF_Library.mdzip $(SAF_SOURCE)/Library.mdzip


.PRECIOUS: $(MODELS) $(SAMPLES)

all: SAF_Plugin/SAF_Plugin.zip

clean:
	$(RM) SAF_Plugin/SAF_Plugin.zip


SAF_Plugin/SAF_Plugin.zip: SAF_Plugin/profiles/SAF_Profile.mdzip SAF_Plugin/profiles/SAF_SCM_Profile.mdzip SAF_Plugin/samples/SAF/SAF_FFDS.mdzip SAF_Plugin/samples/SAF/SAF_Library.mdzip SAF_Plugin/samples/SAF/Library.mdzip SAF_Plugin/data/resourcemanager/MDR_Profile_SAF_75377_descriptor.xml
	(cd SAF_Plugin ;  $(ZIP) a SAF_Plugin.zip data profiles samples templates )

SAF_Plugin/profiles/SAF_Profile.mdzip:  $(SAF_SOURCE)/SAF_Profile.mdzip
	$(CP) $< $@

SAF_Plugin/profiles/SAF_SCM_Profile.mdzip: $(SAF_SOURCE)/SAF_SCM_Profile.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/SAF_FFDS.mdzip:  $(SAF_SOURCE)/SAF_FFDS.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/Library.mdzip:  $(SAF_SOURCE)/Library.mdzip
	$(CP) $< $@

SAF_Plugin/samples/SAF/SAF_Library.mdzip:  $(SAF_SOURCE)/SAF_Library.mdzip
	$(CP) $< $@
