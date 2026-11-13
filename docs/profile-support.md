# SAF Profile Support

What the SAF Profile (installed by **SAF_Plugin.zip**) supports: the SAF viewpoints with their degree of support, the automation rules of the SAF Model Rules plugin, and the validation rules shipped in the profile.

This file documents the current state of the SAF Profile.

## Supported Viewpoints

The SAF Specification defines 52 user-facing viewpoints in four domains. The fifth domain (SAF Development) is used by SAF spec authors and is not covered here.

Degree of support:

- **UI integration** - the plugin ships a custom diagram type for the viewpoint (dedicated toolbar and element palette).
- **Stereotypes present** - the SAF stereotypes of the viewpoint are in the profile, but no dedicated diagram; the viewpoint is modeled with plain diagram types.
- **Unsupported** - the profile does not yet contain the viewpoint.

| Domain | Viewpoint | VP_ID | Support | Remarks |
| --- | --- | --- | --- | --- |
| Architecture Management | Argumentation Assurance Viewpoint | `A2_ARAS` | UI integration | spec maturity: released |
| Architecture Management | Common Standards Definition Viewpoint | `A2_CSTD` | UI integration | spec maturity: released |
| Architecture Management | Grid Definition Viewpoint | `A2_GRID` | UI integration | spec maturity: under construction |
| Architecture Management | Common Terms Definition Viewpoint | `A2_TRMD` | UI integration | spec maturity: released |
| Architecture Management | EA Traceability Viewpoint | `A8_EATR` | UI integration | spec maturity: released; 3 diagram types |
| Conceptual | System Context Definition Viewpoint | `C1_SCXD` | UI integration | spec maturity: released |
| Conceptual | System Context Exchange Viewpoint | `C1_SCXE` | UI integration | spec maturity: released |
| Conceptual | System Use Case Viewpoint | `C1_SUCD` | UI integration | spec maturity: released |
| Conceptual | System Capability Definition Viewpoint | `C2_SCYD` | UI integration | spec maturity: released |
| Conceptual | System Exchange Type Definition Viewpoint | `C2_SETD` | Stereotypes present | spec maturity: released |
| Conceptual | System Functional Breakdown Structure Viewpoint | `C2_SFBS` | UI integration | spec maturity: released |
| Conceptual | System Structure Definition Viewpoint | `C2_SSTD` | UI integration | spec maturity: released |
| Conceptual | System Functional Refinement Viewpoint | `C3_SFRE` | UI integration | spec maturity: released |
| Conceptual | System Process Viewpoint | `C3_SPRO` | UI integration | spec maturity: released |
| Conceptual | System Process White Box Viewpoint | `C3_SPWB` | Stereotypes present | spec maturity: proposed; diagram stereotype only (no viewpoint object yet) |
| Conceptual | System State Viewpoint | `C3_SSTA` | UI integration | spec maturity: released |
| Conceptual | System Context Interaction Viewpoint | `C4_SCXI` | Stereotypes present | spec maturity: released |
| Conceptual | System Internal Exchange Viewpoint | `C4_SIEX` | UI integration | spec maturity: released |
| Conceptual | System Internal Interaction Viewpoint | `C4_SITI` | Stereotypes present | spec maturity: released |
| Conceptual | System Interface Definition Viewpoint | `C5_SIFD` | Stereotypes present | spec maturity: released |
| Conceptual | System Requirement Definition Viewpoint | `C6_SRQD` | Stereotypes present | spec maturity: released |
| Conceptual | Asset Identification Viewpoint | `C7_ASID` | Stereotypes present | spec maturity: proposed |
| Conceptual | Impact Analysis Viewpoint | `C7_IMAN` | Stereotypes present | spec maturity: proposed |
| Conceptual | Security Risk Analysis Viewpoint | `C7_RIAN` | Stereotypes present | spec maturity: proposed |
| Conceptual | Security Context Viewpoint | `C7_SECT` | Stereotypes present | spec maturity: proposed |
| Conceptual | Threat Szenario Viewpoint | `C7_THSC` | Stereotypes present | spec maturity: proposed |
| Conceptual | System Capability Mapping Viewpoint | `C8_SCYM` | Stereotypes present | spec maturity: released |
| Conceptual | System Function Mapping Viewpoint | `C8_SFUM` | Stereotypes present | spec maturity: released |
| Conceptual | System Requirement Traceability Viewpoint | `C8_SRQT` | Stereotypes present | spec maturity: released |
| Operational | Operational Context Definition Viewpoint | `O1_OCXD` | UI integration | spec maturity: released |
| Operational | Operational Context Exchange Viewpoint | `O1_OCXE` | UI integration | spec maturity: released |
| Operational | Operational Story Viewpoint | `O1_OSTY` | UI integration | spec maturity: released |
| Operational | Operational Capability Definition Viewpoint | `O2_OCYD` | UI integration | spec maturity: released |
| Operational | Operational Exchange Type Definition Viewpoint | `O2_OETD` | UI integration | spec maturity: released; 2 diagram types |
| Operational | Operational Performer Definition Viewpoint | `O2_OPRF` | UI integration | spec maturity: released; 2 diagram types |
| Operational | Stakeholder Identification Viewpoint | `O2_STID` | UI integration | spec maturity: released |
| Operational | Operational Process Viewpoint | `O3_OPRO` | UI integration | spec maturity: released |
| Operational | Operational State Viewpoint | `O3_OSTA` | Stereotypes present | spec maturity: released |
| Operational | Operational Context Interaction Viewpoint | `O4_OCXI` | UI integration | spec maturity: released |
| Operational | Stakeholder Requirement Definition Viewpoint | `O6_SKRD` | Stereotypes present | spec maturity: released |
| Operational | Operational Capability Mapping Viewpoint | `O8_OCYM` | Stereotypes present | spec maturity: released |
| Operational | Operational Process Mapping Viewpoint | `O8_OPRM` | Stereotypes present | spec maturity: released |
| Physical | Physical Context Definition Viewpoint | `P1_PCXD` | UI integration | spec maturity: released |
| Physical | Physical Context Exchange Viewpoint | `P1_PCXE` | UI integration | spec maturity: released; 2 diagram types |
| Physical | Physical Exchange Type Definition Viewpoint | `P2_PETD` | UI integration | spec maturity: released |
| Physical | Physical Structure Definition Viewpoint | `P2_PSTD` | UI integration | spec maturity: released |
| Physical | Physical Context Interaction Viewpoint | `P4_PCXI` | UI integration | spec maturity: under construction |
| Physical | Physical Internal Exchange Viewpoint | `P4_PIEX` | UI integration | spec maturity: released; 2 diagram types |
| Physical | Physical Internal Interaction Viewpoint | `P4_PITI` | UI integration | spec maturity: under construction |
| Physical | Physical Interface Definition Viewpoint | `P5_PIFD` | UI integration | spec maturity: released |
| Physical | Physical Functional Mapping Viewpoint | `P8_PFUM` | Stereotypes present | spec maturity: released |
| Physical | Physical Logical Mapping Viewpoint | `P8_PLOM` | UI integration | spec maturity: released |

## Automation Rules (SAF Model Rules)

The plugin installs the **SAF Model Rules** plugin, which applies SAF stereotypes automatically while you model. The rules listen to model commits: when an element is created and the change is committed, every rule whose trigger matches the new element is evaluated. All rules are idempotent - they never override a stereotype you applied deliberately. Rules are hot-reloaded from `rules/*.groovy`.

| Rule | Trigger | Action |
| --- | --- | --- |
| R1 | Operational Process diagram | SAF_OperationalProcess on context Activity |
| R2 | States in a SAF_C3_SSTA state machine | SAF_SystemState |
| R3 | System Process diagram | SAF_SystemProcess on context Activity |
| R4 | Part of SAF_OperationalContext (OCXE diagram) | SAF_OperationalContextRole |

### R1: Operational Process diagram

When you create an **Operational Process diagram** (stereotype `SAF_O3_OPRO`), the rule applies the `SAF_OperationalProcess` stereotype to the diagram's **context Activity**, so the Activity is recognized as the operational process the diagram depicts. It skips (with a log note) when the diagram context is not an Activity, or when the SAF profile is not applied to the project.

### R2: States in a SAF_C3_SSTA state machine

When you create a **State** inside a state machine that has a System State diagram (stereotype `SAF_C3_SSTA`) - including nested states in subregions - the rule applies the `SAF_SystemState` stereotype to the state. States in state machines without a C3_SSTA diagram are left untouched.

### R3: System Process diagram

When you create a **System Process diagram** (stereotype `SAF_C3_SPRO`), the rule applies the `SAF_SystemProcess` stereotype to the diagram's **context Activity** - the conceptual-domain counterpart of R1.

### R4: Part of SAF_OperationalContext (OCXE diagram)

When you add a **part** (composition property) to a block stereotyped `SAF_OperationalContext` - the context block of an Operational Context Exchange (OCXE) diagram - the rule applies the `SAF_OperationalContextRole` stereotype, making the part an operational context role. Derived and non-composition properties are left untouched.

## Validation Rules

The profile ships validation rules (Constraints with the `validationRule` stereotype in `SAF_ValidationSuite`). They run when you validate the model (Tools > Validation); all of them report **warnings**.

| Group | Rule | Abbreviation | Severity | Message |
| --- | --- | --- | --- | --- |
| System Context (C1_SCXD) | SCXD SOI has exactly one role | `SAF-SCXD-SOI` | warning | A SAF System Context shall contain exactly one SOI part (SAF_ConceptualContextRole_SoI) |
| System Context (C1_SCXD) | SCXD SOI typed by Conceptual System | `SAF-SCXD-SOITYPE` | warning | The SOI part of a SAF System Context shall be typed by a SAF Conceptual System (SAF_ConceptualSystem) |
| System Context (C1_SCXD) | SCXD SysContextElementAssociation | `SAF-SCXD-AGGEL` | warning | A SAF System Context shall aggregate only one or more SAF Conceptual Systems, Conceptual Users and Conceptual Environment elements |
| System Context (C1_SCXD) | SCXD context element role present | `SAF-SCXD-CTXROLE` | warning | Every non-SOI part of a SAF System Context shall carry the SAF_ConceptualContextRole stereotype |
| System Context (C1_SCXD) | SCXD context element typed by conceptual element | `SAF-SCXD-CTXTYPE` | warning | Every non-SOI part of a SAF System Context shall be typed by a SAF Conceptual User, Environment or External System |
| General | OpCapabilityComposition | `WOPCAPCOMP` | warning | A SAF Operational Capability shall be associated only to SAF Operational Capabilities |
| General | SysCapabilityComposition | `WSYSCAPCOMP` | warning | A SAF System Capability shall be associated only to SAF System Capabilities |
| General | SysUseCaseElementAssociation | `WUCASSCTX` | warning | A SAF System UseCase shall be associated only to SAF Conceptual Context Elements |
| General | val-conn-ambiguos | `SAF-CON-NOFLOW` | warning | Flow Properties potentially incompatible |
| General | val-conn-cantcheck | `SAF-CON-NOCHK` | warning | Connector End lacks type. Cannot check flow validity |
| General | val-conn-mixup | `SAF-CON-MIXUP` | warning | Connectors should only connect elements of same kind. Don't mix ports and parts |
| General | val-layerrel-connected-elements | `SAF-LYR-MIXUP` | warning | A SAF Interface Layer Relationship shall either be between ports or between properties |
| General | val-layerrel-typed | `SAF-LYR-UNTYPED` | warning | A SAF Interface Layer Relationship shall have a type defining the mapping |
