# Diagram Customization — Naming Convention

Conventions for the diagram templates in `SAF_Plugin/data/defaults/data/diagrams/`.
Each template is a directory containing one `descriptor.xml` (Cameo diagram customization).

## Descriptor anatomy

```
line 1  <?xml ... ?>
line 2  <diagram version="9" type="..." baseType="..." abbreviation="..." category="...">
line 3  <stereotype id="..."/>          ← viewpoint reference (id of the SAF viewpoint stereotype in the profile)
line 4  <requiredPlugin>...</requiredPlugin>
        <toolbarConfiguration>          ← the palette: ordered <category> sections
        ...
```

- `type` — the diagram name shown in the New Diagram menu and diagram titles.
- `abbreviation` — the SAF diagram abbreviation (`SAF_<domain>_<abbr>`), matching the viewpoint it implements.
- `baseType` — the stock Cameo/SysML diagram type the template is based on (e.g. `Class Diagram`).
- line 3 `<stereotype>` — **required in every diagram template**. The plugin/site tooling maps template → viewpoint through this id. (STID was missing it until 2026-11-09.)

## Palette sections (`<category>`)

A palette section has:

- `name` — the section title shown in the toolbar UI. **This is what the naming rules below apply to.**
- `id` — internal identifier. Must be unique *within* the descriptor; not shown in the UI. Reusing a stock id (e.g. `action_category_1156164534125_948326_0`) in a repurposed section is tolerated but discouraged — the id is a copy-paste trace from the section the template was derived from, and it is how the OCYD misname (`SAF Capability Diagram`) happened: a stock BDD section was repurposed and renamed, id left behind.
- `<actions>` — `<standard .../>` (stock Cameo actions) and `<custom ...>` (stereotype buttons).

### Rules

1. **SAF section = diagram name.** The section that carries the template's own SAF stereotype buttons MUST be named exactly the diagram's `type` attribute, e.g. `SAF Operational Capability Definition Diagram`, `SAF Stakeholder Identification Diagram`. One diagram, one section, same name as shown in the New Diagram menu.
2. **Stock sections keep stock names.** Sections that are stock Cameo toolbars copied into the template so standard buttons stay available keep their stock names unchanged: `Common`, `Block Definition Diagram`, `Requirements Diagram`, `Use Case Diagram`, `SysML Activity Diagram`.
3. **Directory name = `type` attribute.** The template directory must be named exactly as the `type` attribute. (Renaming a directory requires updating the plugin manifest entries in `data/resourcemanager/MDR_Profile_SAF_75377_descriptor.xml` and rebuilding `SAF_Plugin.zip`.)
4. **Button descriptions are clean labels.** A `<custom>` button's `description` is the element concept in plain language — `Claim`, `Operational Capability`, `Stakeholder Representing` — never a raw stereotype name (`Class«SAF_Claim»`, `SAF_ConceptualContext`), never the `SAF_` prefix, guillemets, or trailing spaces.
5. **No palette buttons for deprecated concepts.** Stereotypes named `*_deprecated` in the profile are not exposed as palette buttons (removed 2026-11-09 from SCXD, SSTD, SUCD). Their `<manipulators>` entries are kept: manipulators only appear when the user selects an existing element carrying the deprecated stereotype, which legacy models may still contain.

## Current status (2026-11-13)

26 diagram templates have a SAF palette section, and all 26 conform to rule 1; all 39 template directories conform to rule 3 (as of 2026-11-13; 2026-11-09 normalized 14 section names and 2 pre-refactor directory names, and 2026-11-11 added 8 more templates):

| Abbreviation | Diagram (`type`) | SAF section name | Notes |
|---|---|---|---|
| SAF_A2_ARAS | SAF Argumentation Assurance Diagram | SAF Argumentation Assurance Diagram | |
| SAF_A2_CSTD | SAF Common Standards Definition Diagram | SAF Common Standards Definition Diagram | |
| SAF_A8_EATR | SAF EA Traceability Diagram | SAF EA Traceability Diagram | normalized 2026-11-09 (was `EA Traceability Diagram`) |
| SAF_O2_OCYD | SAF Operational Capability Definition Diagram | SAF Operational Capability Definition Diagram | fixed 2026-11-09 (was `SAF Capability Diagram`) |
| SAF_O1_OCXD | SAF Operational Context Definition Diagram | SAF Operational Context Definition Diagram | normalized 2026-11-09 |
| SAF_O1_OCXE | SAF Operational Context Exchange Diagram | SAF Operational Context Exchange Diagram | normalized 2026-11-09 |
| SAF_O2_OETD | SAF Operational Exchange Type Definition Diagram | SAF Operational Exchange Type Definition Diagram | normalized 2026-11-09; directory renamed from pre-refactor `SAF Operational Domain Item Kind Diagram` |
| SAF_O2_OPRF | SAF Operational Performer Definition Diagram | SAF Operational Performer Definition Diagram | normalized 2026-11-09 (was `SAF Operational Performer Diagram`) |
| SAF_O3_OPRO | SAF Operational Process Diagram | SAF Operational Process Diagram | normalized 2026-11-09 |
| SAF_O1_OSTY | SAF Operational Story Diagram | SAF Operational Story Diagram | normalized 2026-11-09 |
| SAF_P1_PCXD | SAF Physical Context Definition Diagram | SAF Physical Context Definition Diagram | |
| SAF_P2_PETD | SAF Physical Exchange Type Definition Diagram | SAF Physical Exchange Type Definition Diagram | |
| SAF_P5_PIFD | SAF Physical Interface Definition Diagram | SAF Physical Interface Definition Diagram | normalized 2026-11-09 (was `Physical Interface Definition Diagram`) |
| SAF_P2_PSTD | SAF Physical Structure Definition Diagram | SAF Physical Structure Definition Diagram | normalized 2026-11-09 |
| SAF_O2_STID | SAF Stakeholder Identification Diagram | SAF Stakeholder Identification Diagram | fixed 2026-11-09 (was `SAF Stakeholder Identification`) |
| SAF_C1_SCXD | SAF System Context Definition Diagram | SAF System Context Definition Diagram | normalized 2026-11-09 (was `System Context Definition`) |
| SAF_C1_SCXE | SAF System Context Exchange Diagram | SAF System Context Exchange Diagram | |
| SAF_C3_SFRE | SAF System Functional Refinement Diagram | SAF System Functional Refinement Diagram | |
| SAF_C4_SIEX | SAF System Internal Exchange Diagram | SAF System Internal Exchange Diagram | normalized 2026-11-09 |
| SAF_C3_SPRO | SAF System Process Diagram | SAF System Process Diagram | |
| SAF_C3_SSTA | SAF System State Diagram | SAF System State Diagram | |
| SAF_C2_SSTD | SAF System Structure Definition Diagram | SAF System Structure Definition Diagram | normalized 2026-11-09 |
| SAF_C1_SUCD | SAF System Use Case Diagram | SAF System Use Case Diagram | |
| SAF_P1_PCXE | SAF Physical Context Exchange Diagram | SAF Physical Context Exchange Diagram | added 2026-11-11 |
| SAF_C2_SCYD | SAF System Capability Definition Diagram | SAF System Capability Definition Diagram | added 2026-11-11 |
| SAF_C2_SFBS | SAF System Functional Breakdown Structure Diagram | SAF System Functional Breakdown Structure Diagram | added 2026-11-11 |

The 9 table/matrix templates (Common Terms Definition Table, EA Traceability Matrix/Table, Grid Overview Table, SAF Operational Exchange Type Definition Table, Operational Performer Definition Table, Physical Context Exchange Table, Physical Internal Exchange Table, Physical Logical Mapping Matrix) have no palette and are out of scope.

Deviations and exceptions (as of 2026-11-13):

- `SAF Internal Exchange Diagram` (`SAF_P4_PIEX`) is the one rule 1 exception: its SAF section is named `SAF Physical Internal Exchange Diagram`, while the `type` attribute and the directory still carry the pre-refactor short name. Renaming requires a plugin manifest update and a `SAF_Plugin.zip` rebuild.
- The 3 sequence-diagram templates (`SAF_O4_OCXI`, `SAF_P4_PCXI`, `P4_PITI`) ship only the stock `Common` section, with no SAF palette section.
- The empty `SAF Concept Diagram/` directory has no `descriptor.xml` and is excluded from the production plugin (its `SAF Development` category is filtered out by `genplugin.groovy`).
