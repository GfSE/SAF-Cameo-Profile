# 3DS Cameo Systems Modeler Repository

This repository provides the current release of the **SAF Profile** intended to be used with Cameo Systems Modeler 2024x refresh 1. Please see the model file itself for licensing terms.

If you need help, have a question or for general discussion, please head over to the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).


## SAF Plugin (recommended)
 **[SAF_Plugin.zip](SAF_Plugin/SAF_Plugin.zip)** installs the SAF Profile, additional diagram types for some viewpoints, additional project templates, and the FFDS Example Model. The plugin is required to open the SAF FFDS Example Model. The plugin also provides the SCM Profile that is used to develop viewpoints in SAF.

The SAF Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in Cameo.

## SAF Profile
**[SAF_Profile.mdzip:](SAF_Plugin/profiles/SAF_Profile.mdzip)** The mdzip file contains the SAF Profile with SAF Stereotypes according to the [SAF-Specification](https://github.com/GfSE/SAF-Specification/). See this [List of Stereotypes](https://github.com/GfSE/SAF-Specification/blob/main/stereotypes.md).

## SCM Profile
**[SAF_SCM_Profile.mdzip:](SAF_Plugin/profiles/SAF_SCM_Profile.mdzip)** The mdzip file contains the SCM used to develop SAF Viewpoints, e.g., to extend SAF.
This development documentation intends to help with the modification or extension of SAF. If you need help with extending SAF, please contact the SAF developers at the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

## SAF FFDS Example Model
**[SAF_FFDS.mdzip:](SAF_Plugin/samples/SAF/SAF_FFDS.mdzip)** The mdzip file contains the example used in the SAF Specification. The model requires the SAF_Plugin to be installed. 

*Note, that the Example model is in no way intended to represent a complete model description of a Fire Forest Detection System. The model's main purpose is to serve as an example for the application of SAF Viewpoints.*

## Versions
This is the main branch that reflects the current state of development, and it is updated as required.

The following releases are available, each of which is kept in a separate branch:
* [Initial Release](https://github.com/GfSE/SAF-Cameo-Profile/tree/Initial-Release)
* [TdSE2023](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2023)
