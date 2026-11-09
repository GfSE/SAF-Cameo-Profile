# 3DS Cameo Systems Modeler Resources for SAF users

This repository provides the current state of development [tracking the main branch of the spec] of the **SAF Profile** intended to be used with Cameo Systems Modeler/Magic Draw / Magic System of Systems Architect 2026x . Please see the model file itself for licensing terms.

For We're also working on a plugin for the 2026x versions, see the [Releases Page of the Repository](https://github.com/GfSE/SAF-Cameo-Profile/releases).

If you need help, have a question or for general discussion, please visit [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

If there is an issue with one of the SAF Plugins for Cameo Systems Modeler, please [create an issue in this repository](https://github.com/GfSE/SAF-Cameo-Profile/issues/new/choose).

## SAF Plugin (recommended for model users)
**SAF_Plugin.zip** installs the SAF Profile, additional diagram types for some viewpoints, the **SAF Modeler** perspective, additional project templates, and the FFDS Example Model. The plugin is required to open the SAF FFDS Example Model. 

This plugin is for users of SAF wanting to create system models with SAF.

The SAF Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in the modeling tool.

### Custom Diagram Types

The plugin adds 31 custom diagram types to the New Diagram menu — one per SAF viewpoint (23 diagrams plus 8 table and matrix types):

**Conceptual domain**
- SAF System Context Definition Diagram
- SAF System Context Exchange Diagram
- SAF System Functional Refinement Diagram
- SAF System Internal Exchange Diagram
- SAF System Process Diagram
- SAF System State Diagram
- SAF System Structure Definition Diagram
- SAF System Use Case Diagram

**Operational domain**
- SAF Operational Capability Definition Diagram
- SAF Operational Context Definition Diagram
- SAF Operational Context Exchange Diagram
- SAF Operational Exchange Type Definition Diagram (+ Table)
- SAF Operational Performer Definition Diagram (+ Table)
- SAF Operational Process Diagram
- SAF Operational Story Diagram
- SAF Stakeholder Identification Diagram

**Physical domain**
- SAF Physical Context Definition Diagram
- SAF Physical Exchange Type Definition Diagram
- SAF Physical Interface Definition Diagram
- SAF Physical Structure Definition Diagram
- SAF Physical Internal Exchange Table
- SAF Physical Logical Mapping Matrix

**Assurance / management**
- SAF Argumentation Assurance Diagram
- SAF Common Standards Definition Diagram
- SAF EA Traceability Diagram (+ Matrix, + Table)
- SAF Common Terms Definition Table
- SAF Grid Overview Table

### What's new in this snapshot (2026-11-09)

- New **SAF Modeler** perspective: organizes the diagram toolbars by SAF viewpoint (grouped per SAF domain), so the New Diagram dialog shows the SAF diagram types in SAF order. Switch to it via the perspective selector in Cameo.
- The System Context Definition diagram gained a **Conceptual System** element button
- Element buttons across all diagram types now show clean names (e.g. `Claim` instead of `Class«SAF_Claim»`); one button that applied the wrong element type was corrected
- In each diagram, the element palette is now named exactly like the diagram type
- Deprecated SAF concepts (Logical SOI, Logical External System) no longer appear in the element palettes
- `Test Case Statemachine` buttons renamed to `Test Case State Machine` (4 diagram types)

# 3DS Cameo Systems Modeler Resources for SAF developers
The following files are provided for those who want to extend the SAF.
Especially for modification of the SAF Profile it is not recommended to install the SAF Plugin. The following files should be installed separately. 
This, because the plugin installation location provides an additional path to the profile which can confuse, which of the profile files is used in the end. 

## SAF Profile
**[SAF_Profile.mdzip:](SAF_Plugin/profiles/SAF_Profile.mdzip)** The mdzip file contains the SAF Profile with SAF Stereotypes according to the [SAF-Specification](https://saf.gfse.org). See this [List of Stereotypes](https://saf.gfse.org/userdoc/stereotypes.html).

## SCM Profile
**[SAF_SCM_Profile.mdzip:](SAF_DevPlugin/profiles/SAF_SCM_Profile.mdzip)** The mdzip file contains the SCM Profile used to develop SAF Viewpoints.
This [development documentation](https://saf.gfse.org/devdoc/devdoc.html) intends to help with the modification or extension of SAF. If you need help with extending SAF, please contact the SAF developers at the [SAF discussion forum](https://github.com/GfSE/SAF-Specification/discussions).

## SAF FFDS Example Model
**[SAF_FFDS.mdzip:](SAF_Plugin/samples/SAF/SAF_FFDS.mdzip)** The mdzip file contains the example used in the SAF Specification. It is included in the SAF Plugin. Note, that it has a dependency to the NAF Profile, but this is only to demonstrate traceability.

*Note, that the example model is in no way intended to represent a complete model description of a Fire Forest Detection System. The model's main purpose is to serve as an example for the application of SAF Viewpoints.*

## SAF FFDS NAF Example Model
**[SAF_FFDS_NAF.mdzip:](SAF_Plugin/samples/SAF/SAF_FFDS_NAF.mdzip)** The mdzip file contains some NAF model elements as needed to demonstrate traceability in the SAF Specification. It is included in the SAF Plugin.

## SAF Dev Plugin
**[SAF_DevPlugin.zip](SAF_DevPlugin.zip)** installs the Diagram Types and the SCM Profile. 

Its main use is for developers of the SAF, and may be used by advanced users to extend SAF.

The SAF Dev Plugin is installed using the Resource/Plugin Manager which can be accessed via the help menu in Cameo.

## Releases
This is the main branch reflecting the current state of development.
Since Frebruary 2026 we switched to automated builds, from now all snapshots are available from the Releases Page.

**Please download from the [Releases Page of the Repository](https://github.com/GfSE/SAF-Cameo-Profile/releases)**

The releases follow the schema v<cameoversion>-<releasedate>.
e.g. vcameo2026x-2026-02-22 is a release for cameo2026x from Feb 22th in 2026

https://github.com/GfSE/SAF-Cameo-Profile/releases#release-vcameo2026x-2026-02-26 is the last release 
before the refactoring of the SAF grid

The following older releases are available, each of which is kept in a separate branch:
* [Initial Release](https://github.com/GfSE/SAF-Cameo-Profile/tree/Initial-Release)
* [TdSE2023](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2023)
* [TdSE2024](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2024)
* [TdSE2025](https://github.com/GfSE/SAF-Cameo-Profile/tree/TdSE2025)
