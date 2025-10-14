# 3DS Cameo Systems Modeler Resources for SAF users

This repository provides the current release of the **SAF Profile** intended to be used with Cameo Systems Modeler 2024x refresh 1. Please see the model file itself for licensing terms.

If you need help, have a question or for general discussion, please visit [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).


## SAF Plugin (recommended for model users)
**[SAF_Plugin.zip](SAF_Plugin/SAF_Plugin.zip)** installs the SAF Profile, additional diagram types for some viewpoints, additional project templates, and the FFDS Example Model. The plugin is required to open the SAF FFDS Example Model. The plugin also provides the SCM Profile, which can used to develop viewpoints in SAF.

This plugin is for users of SAF wanting to create system models with SAF.

The SAF Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in Cameo.

# 3DS Cameo Systems Modeler Resources for SAF developers
The following files are provided for those who want to extend the SAF.
Especially for modification of the SAF Profile it is not recommended to install the SAF Plugin. The following files should be installed separately. 
This, because the plugin installation location provides an additional path to the profile which can confuse, which of the profile files is used in the end. 

For development within the SAF Working group, only the Dev Plugin is necessary, because all other resources are provided by the TWC.

## SAF Profile
**[SAF_Profile.mdzip:](SAF_Plugin/profiles/SAF_Profile.mdzip)** The mdzip file contains the SAF Profile with SAF Stereotypes according to the [SAF-Specification](https://saf.gfse.org). See this [List of Stereotypes](https://saf.gfse.org/userdoc/stereotypes.html).

## SCM Profile
**[SAF_SCM_Profile.mdzip:](SAF_Plugin/profiles/SAF_SCM_Profile.mdzip)** The mdzip file contains the SCM Profile used to develop SAF Viewpoints.
This [development documentation](https://saf.gfse.org/devdoc/devdoc.html) intends to help with the modification or extension of SAF. If you need help with extending SAF, please contact the SAF developers at the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

## SAF FFDS Example Model
**[SAF_FFDS.mdzip:](SAF_Plugin/samples/SAF/SAF_FFDS.mdzip)** The mdzip file contains the example used in the SAF Specification. It is included in the SAF Plugin.

*Note, that the example model is in no way intended to represent a complete model description of a Fire Forest Detection System. The model's main purpose is to serve as an example for the application of SAF Viewpoints.*

## SAF Dev Plugin
**[SAF_DevPlugin.zip](SAF_Plugin/SAF_DevPlugin.zip)** installs only the Diagram Types. Its main use is for development of the SAF Profile, SAF Specification and so on. This is, because when developing SAF the user plugin should not be installed.

The SAF Dev Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in Cameo.
Do not install both plugins !

## Versions
This is the main branch that reflects the current state of development, and it is updated as required.

The following releases are available, each of which is kept in a separate branch:
* [Initial Release](https://github.com/GfSE/SAF-Cameo-Profile/tree/Initial-Release)
* [TdSE2023](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2023)
