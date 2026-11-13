# 3DS Cameo Systems Modeler Resources for SAF users

This repository provides the current state of development \[tracking the main branch of the spec\] of the **SAF Profile** intended to be used with Cameo Systems Modeler/Magic Draw / Magic System of Systems Architect 2026x. Please see the model file itself for licensing terms.

Download the plugin at the [Releases Page of the Repository](https://github.com/GfSE/SAF-Cameo-Profile/releases).

If you need help, have a question, or want to join a general discussion, please visit the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

If there is an issue with one of the SAF Plugins for Cameo Systems Modeler, please [create an issue in this repository](https://github.com/GfSE/SAF-Cameo-Profile/issues/new/choose).

## SAF Plugin (recommended for model users)

**SAF\_Plugin.zip** installs the SAF Profile, additional diagram types for some viewpoints, the **SAF Modeler** perspective, additional project templates, the FFDS Example Model, and an automation rules plugin (see below). 

This plugin is for users of SAF wanting to create system models with SAF. It is required to open the SAF FFDS Example Model.

[SAF Profile Support](docs/profile-support.md) lists what the profile ships: the supported SAF viewpoints with their degree of support, the automation rules, and the validation rules.

The SAF Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in the modeling tool.

### SAF Automation Rules

The SAF Plugin now also installs the **SAF Automation Rules** plugin (`plugins/com.haarer.saf.rules/`), which keeps models in shape while you build them: it watches what you create and applies the matching SAF stereotype automatically — no manual tagging, never overriding anything you did deliberately.

See [SAF Profile Support](docs/profile-support.md) for the supported rules; the rules are Groovy scripts loaded at runtime (hot reload, ~2 s poll), and are extensible by SAF users.

### Custom Diagram Types

The plugin adds custom diagram types for most SAF Viewpoints to the New Diagram menu. The complete list per viewpoint is in [SAF Profile Support](docs/profile-support.md#supported-viewpoints).

### What's new in this snapshot (2026-11-14)

*   New user documentation: [SAF Profile Support](docs/profile-support.md) lists the supported viewpoints with their degree of support, the automation rules, and the validation rules; [Maintainer documentation](docs/maintainers.md) covers the profile maintenance macros
*   9 more custom diagram types (2026-11-14): System Exchange Type Definition, System Interface Definition, System Context Interaction, System Internal Interaction, the Common Standards, System Capability Definition and System Capability Mapping Tables, and the System Function Mapping / System Requirement Traceability Matrices
*   8 more custom diagram types (2026-11-11); the complete list per viewpoint is in [SAF Profile Support](docs/profile-support.md#supported-viewpoints)

The previous snapshot (2026-11-09) added:

*   New **SAF Modeler** perspective: organizes the diagram toolbars by SAF viewpoint (grouped per SAF domain), so the New Diagram dialog shows the SAF diagram types in SAF order. Switch to it via the perspective selector in Cameo.
*   The System Context Definition diagram gained a **Conceptual System** element button
*   Element buttons across all diagram types now show clean names (e.g. `Claim` instead of `Class«SAF_Claim»`); one button that applied the wrong element type was corrected
*   In each diagram, the element palette is now named exactly like the diagram type
*   Deprecated SAF concepts (Logical SOI, Logical External System) no longer appear in the element palettes

# 3DS Cameo Systems Modeler Resources for SAF developers

The following files are provided for those who want to extend the SAF.  
Especially for modification of the SAF Profile it is not recommended to install the SAF Plugin. The following files should be installed separately.  
This is because the plugin installation location provides an additional path to the profile, which makes it unclear which of the profile files is used in the end.

## SAF Profile

[**SAF\_Profile.mdzip:**](SAF_Plugin/profiles/SAF_Profile.mdzip) The mdzip file contains the SAF Profile with SAF Stereotypes according to the [SAF-Specification](https://saf.gfse.org). See this [List of Stereotypes](https://saf.gfse.org/userdoc/stereotypes.html). [Maintainers](docs/maintainers.md)

## SCM Profile

[**SAF\_SCM\_Profile.mdzip:**](SAF_DevPlugin/profiles/SAF_SCM_Profile.mdzip) The mdzip file contains the SCM Profile used to develop SAF Viewpoints.  
This [development documentation](https://saf.gfse.org/devdoc/devdoc.html) is intended to help with the modification or extension of SAF. If you need help with extending SAF, please contact the SAF developers at the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

## SAF FFDS Example Model

[**SAF\_FFDS.mdzip:**](SAF_Plugin/samples/SAF/SAF_FFDS.mdzip) The mdzip file contains the example used in the SAF Specification. It is included in the SAF Plugin. Note that it has a dependency on the NAF Profile, but this is only to demonstrate traceability.

_Note that the example model is in no way intended to represent a complete model description of a Fire Forest Detection System. The model's main purpose is to serve as an example for the application of SAF Viewpoints._

## SAF FFDS NAF Example Model

[**SAF\_FFDS\_NAF.mdzip:**](SAF_Plugin/samples/SAF/SAF_FFDS_NAF.mdzip) The mdzip file contains some NAF model elements as needed to demonstrate traceability in the SAF Specification. It is included in the SAF Plugin.

## SAF Dev Plugin

[**SAF\_DevPlugin.zip**](SAF_DevPlugin.zip) installs the Diagram Types and the SCM Profile.

Its main use is for developers of the SAF, and may be used by advanced users to extend SAF.

The SAF Dev Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in Cameo.

## Releases

Snapshots are built from the main branch, which reflects the current state of development.  
Since February 2026 we switched to automated builds; all snapshots since then are available from the Releases Page.

**Please download from the** [**Releases Page of the Repository**](https://github.com/GfSE/SAF-Cameo-Profile/releases)

The releases follow the scheme vcameo2026x-YYYY-MM-DD.  
e.g. vcameo2026x-2026-02-22 is a release for Cameo 2026x from 22nd February 2026

https://github.com/GfSE/SAF-Cameo-Profile/releases#release-vcameo2026x-2026-02-22 is the last release  
before the refactoring of the SAF grid

The following older releases are available, each of which is kept in a separate branch:

*   [Initial Release](https://github.com/GfSE/SAF-Cameo-Profile/tree/Initial-Release)
*   [TdSE2023](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2023)
*   [TdSE2024](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2024)
*   [TdSE2025](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2025)