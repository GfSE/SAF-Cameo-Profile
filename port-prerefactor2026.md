# Porting valuable PreRefactor2026 additions into main

Status: **completed 2026-11-09** — the 9 portable diagrams were ported into main (see `changes.md` 2026-11-09, "port PreRefactor2026 branch diagram-template improvements").
Created: 2026-11-09. Sources: `SAF-Cameo-Profile-PreRefactor2026` clone (branch `PreRefactor2026`, commit `cc64530`), `SAF-Specification` branches `main` vs `origin/PreRefactor2026`, this repo's `changes.md` (2026-03-20).

## Goal

Port the diagram work of commit `cc64530` "Add missing SAF diagram types and unify diagram frame properties" from the pre-refactor branch into main, carefully remapping stereotypes/viewpoints to the post-refactor SAF Grid and validating against the spec change notes (`changes.md`) and the spec + profile models.

## What the pre-refactor branch added (commit `cc64530`, ~23.4k lines)

1. **10 new diagram types** in `SAF_Plugin/data/defaults/data/diagrams/` (one `descriptor.xml` each).
2. **Diagram frame property unification**: a 29-line `<elementstyle name="Diagram">` PropertyVisitorAcceptor block (`SHOW_DIAGRAM_TYPE`, `SHOW_ABBREVIATED_DIAGRAM_TYPE`, `SHOW_CONTEXT_TYPE`, `SHOW_PARAMETERS`, `SHOW_FRAME_OWNER`) added to all diagrams. **Main lacks this block in 13 of the 14 overlapping diagrams** (only System State has it).
3. **Toolbar/palette enrichment** on existing diagrams (operational context/process palettes, icons, manipulators, `stereotypestyle` blocks, `SAF_ System State` -> `SAF_SystemState` typo fix).
4. Branch-identity churn (NOT portable): pre-refactor abbreviations/categories on line 2, `/version/PreRefactor2026/` helpID URLs, `C:\Program Files\...` module locations, `SAF_Profile.mdzip` binary bump, `files.info` build leftovers, MDR version labels.

## Viewpoint mapping (authoritative: `SAF-Specification` `main` vs `origin/PreRefactor2026`, `src/_data/viewpoints.json`)

The refactor **abolished the Logical domain** and renamed Common -> Architecture Management, Functional -> Conceptual.

| Pre-refactor viewpoint | Post-refactor viewpoint (main) |
|---|---|
| Logical Functional Mapping Viewpoint | System Function Mapping Viewpoint (C8_SFUM) |
| Logical Internal Exchange Viewpoint | System Internal Exchange Viewpoint (C4_SIEX) |
| Logical Internal Interaction Viewpoint | System Internal Interaction Viewpoint (C4_SITI) |
| Logical Structure Definition Viewpoint | System Structure Definition Viewpoint (C2_SSTD) |
| Operational Domain Item Kind Viewpoint | Operational Exchange Type Definition Viewpoint (O2_OETD) |
| System Domain Item Kind Viewpoint | System Exchange Type Definition Viewpoint (C2_SETD) |
| Physical Logical Item Mapping Viewpoint | (no direct counterpart - dropped/merged) |

New viewpoints in main: Operational/Physical/System (Internal/Context) Interaction, System Function Mapping, System Process White Box (C3_SPWB), System Structure Definition, Exchange Type Definitions, Physical Context/Internal Interaction (P4_*).

### The 10 new diagrams -> post-refactor mapping

| New diagram (pre-refactor abbrev) | Post-refactor target | Status |
|---|---|---|
| SAF Concept Diagram (`D2_COTD`) | - | **SKIP**: duplicate of existing `SAF_DevPlugin` diagram; SAF_Plugin copy also depends on `SAF_SCM_Profile.mdzip` |
| SAF Logical Internal Exchange Diagram (`L4_LIEX`) | `C4_SIEX` System Internal Exchange | portable, code remap |
| SAF Logical Structure Definition Diagram (`L2_LSTD`) | `C2_SSTD` System Structure Definition | portable, code remap |
| SAF Operational Context Exchange Diagram (`O1_OCXE`) | `O1_OCXE` (unchanged) | portable, category fix only |
| SAF Physical Structure Definition Diagram (`P2_PSTD`) | `P2_PSTD` (unchanged) | portable, category fix only |
| SAF Stakeholder Identification Diagram (`O2_STID`) | `O2_STID` (unchanged) | portable, category fix only |
| SAF System Context Exchange Diagram (`F1_SCXE`) | `C1_SCXE` | portable, code remap |
| SAF System Functional Refinement Diagram (`F3_SFRE`) | `C3_SFRE` | portable, code remap |
| SAF System Process Diagram (`F3_SPRO`) | `C3_SPRO` (separate `C3_SPWB` already in main) | portable, code remap |
| SAF System Use Case Diagram (`F1_SUCD`) | `C1_SUCD` | portable, code remap |

## Stereotype ID remap (resolved from spec `_data` ID maps, both branches)

77 distinct `<stereotype id>` references across the 10 new descriptors. **Key finding: element IDs survived the refactor - renames kept their IDs**, so XML references stay valid; only `name` attributes and line-2 abbreviations need updating.

### Renamed in refactor - same ID, new name (15): port as-is, update `name` attrs

| Profile ID | pre-refactor name | post-refactor name (main) | used by |
|---|---|---|---|
| `_19_0_1_26f0132_1547201742246_537565_42154` | SAF_LogicalElement | SAF_ConceptualSystem | Logical Structure  |
| `_19_0_1_26f0132_1547202431219_827789_42473` | SAF_LogicalExternalSystem | SAF_LogicalExternalSystem_deprecated | System Use Case Di |
| `_19_0_1_8710274_1547464060642_374530_42162` | SAF_LogicalSOI | SAF_LogicalSOI_deprecated | Logical Structure  |
| `_19_0_1_8710274_1547464071851_635988_42197` | SAF_PhysicalSystem | SAF_PhysicalSystem_deprecated | Physical Structure |
| `_19_0_2_26f0132_1558076117783_295122_47010` | SAF_LogicalUser | SAF_ConceptualUser | System Use Case Di |
| `_19_0_2_26f0132_1558090471537_935611_48414` | SAF_PhysicalSoftwareElement | SAF_PhysicalSoftware | Physical Structure |
| `_19_0_2_26f0132_1558090490396_87116_48464` | SAF_PhysicalHardwareElement | SAF_PhysicalHardware | Physical Structure |
| `_19_0_2_26f0132_1558090585793_420384_48529` | SAF_PhysicalElement | SAF_PhysicalSystem | Physical Structure |
| `_19_0_2_26f0132_1562313107961_803548_148677` | SAF_LogicalEnvironment | SAF_ConceptualEnvironment | System Use Case Di |
| `_19_0_4_26f0132_1625209562736_284155_381` | SAF_L2_LSTD | SAF_C2_SSTD | Logical Structure  |
| `_19_0_4_26f0132_1625209687705_869914_419` | SAF_L4_LIEX | SAF_C4_SIEX | Logical Internal E |
| `_19_0_4_6d8019d_1617380277228_633159_552` | SAF_F3_SPRO | SAF_C3_SPRO | System Process Dia |
| `_19_0_4_6d8019d_1617380348210_24096_555` | SAF_F1_SUCD | SAF_C1_SUCD | System Use Case Di |
| `_19_0_4_6d8019d_1618081524318_206808_1303` | SAF_F1_SCXE | SAF_C1_SCXE | System Context Exc |
| `_2021x_2_6d8019d_1677616004198_497557_24505` | SAF_F3_SFRE | SAF_C3_SFRE | System Functional  |

### References to DEPRECATED elements (3): need replacement or removal - **WG decision**

`changes.md` (2026-03-20) added the replacements: `SAF_ConceptualContextRole_SoI` and `SAF_PhysicalContextRole_SoI` (new part-property stereotypes).

| Profile ID | deprecated name | used by |
|---|---|---|
| `_19_0_1_26f0132_1547202431219_827789_42473` | SAF_LogicalExternalSystem | System Use Case Di |
| `_19_0_1_8710274_1547464060642_374530_42162` | SAF_LogicalSOI | Logical Structure  |
| `_19_0_1_8710274_1547464071851_635988_42197` | SAF_PhysicalSystem | Physical Structure |

### Unchanged (16): port as-is

Allocate, FlowProperty, ItemFlow, ProxyPort, SAF_ContextAction, SAF_FunctionAction, SAF_O1_OCXE, SAF_OperationalStory, SAF_P2_PSTD, SAF_Stakeholder, SAF_StakeholderRelation, SAF_StakeholderRepresenting, SAF_StakeholderRequirement, SAF_StakeholderRequirementImposition, SAF_SystemUseCase, SCM_Concept

### Standard profile elements (39): resolve in main data, portable as-is (UML/SysML metaclasses - Block, View, Viewpoint, Requirement, Trace, InterfaceBlock, ...)

### Unresolved in either spec dataset (7): standard-profile IDs (`_11_5EAPbeta_*`, `_12_*`, `_15_*`) - verify against the live profile's standard requiredPlugins

- `_11_5EAPbeta_be00301_1147430310770_313444_1322` (4 diagrams: Logical Structure , Physical Structure, Stakeholder Identi, System Use Case Di)
- `_11_5EAPbeta_be00301_1147430329106_6539_1367` (4 diagrams: Logical Structure , Physical Structure, Stakeholder Identi, System Use Case Di)
- `_11_5EAPbeta_be00301_1147767840464_372327_467` (6 diagrams: Logical Internal E, Logical Structure , Operational Contex, Physical Structure, Stakeholder Identi, System Context Exc)
- `_12_0EAPbeta_be00301_1161355503976_51059_459` (6 diagrams: Logical Internal E, Logical Structure , Operational Contex, Physical Structure, Stakeholder Identi, System Context Exc)
- `_12_0_be00301_1164123483951_695645_2041` (6 diagrams: Logical Internal E, Logical Structure , Operational Contex, Physical Structure, Stakeholder Identi, System Context Exc)
- `_15_0_be00301_1199377756297_348405_2678` (6 diagrams: Logical Internal E, Logical Structure , Operational Contex, Physical Structure, Stakeholder Identi, System Context Exc)
- `_15_0_be00301_1199378032543_992832_3096` (6 diagrams: Logical Internal E, Logical Structure , Operational Contex, Physical Structure, Stakeholder Identi, System Context Exc)

## Build-filter constraint

`genplugin.groovy:148` only packages descriptors whose category matches `^SAF (A[CM]|[CDOP]D) Diagrams$`. Ported diagrams must be re-categorized: `SAF FD Diagrams` / `SAF LD Diagrams` -> `SAF CD Diagrams`; `SAF OD/PD Diagrams` stay. Line-2 abbreviations must use post-refactor codes (see mapping above).

## Porting plan

### Phase 1 - Frame property unification (safe, mechanical)
- [ ] Add the 29-line `<elementstyle name="Diagram">` block to main's 13 diagrams lacking it (Argumentation Assurance, Common Standards Definition, EA Traceability, Operational Capability, Operational Context Definition, Operational Domain Item Kind, Operational Performer Definition, Operational Process, Operational Story, Physical Context Definition, Physical Exchange Type Definition, Physical Interface Definition, System Context Definition).
- [ ] Verify: rebuild plugin (`make`/genplugin), open diagrams in Cameo, check frame shows diagram type/abbrev.

### Phase 2 - Live-model verification (needs model loaded)
- [ ] Load main's `SAF_Plugin/profiles/SAF_Profile.mdzip` in Cameo (MCP surface).
- [ ] Confirm all 31 SAF-specific IDs (15 renamed + 16 kept) exist under post-refactor names.
- [ ] Confirm the 7 unresolved standard IDs resolve via the profile's standard requiredPlugins.
- [ ] Fetch post-refactor IDs for `SAF_ConceptualContextRole_SoI` / `SAF_PhysicalContextRole_SoI` (replacements for the 3 deprecated refs).
- [ ] Cross-check against the post-refactor spec model (MSOSA2026xHF2) that C2_SSTD / C4_SIEX / C4_SITI viewpoints own these diagram types (spec-level validation).

### Phase 3 - Port the 9 diagrams (after Phase 2 + WG decisions)
- [ ] Copy the 9 descriptor dirs into main's `SAF_Plugin/data/defaults/data/diagrams/`.
- [ ] Rewrite line 2 per mapping table (abbrev + category).
- [ ] Update `name` attributes on the 15 renamed stereotypes.
- [ ] Replace/remove the 3 deprecated references (per WG decision).
- [ ] Fix helpID (main site URLs, no `/version/PreRefactor2026/`); fix module `location` + icon `filename` to main conventions (icons are base64-embedded - portable).
- [ ] Do NOT port: Concept Diagram SAF_Plugin copy, `SAF_Profile.mdzip`, `files.info`, MDR version labels, genplugin/README/workflow build commits.

### Phase 4 - Toolbar/palette enrichment (selective, after Phase 3)
- [ ] Evaluate per diagram: Operational Context Definition (+BDD palette), Operational Process (pin styles, manipulators), Story/Domain Item Kind/State (icons, stereotypestyle, typo fix). System Context Definition's `SAF Logical *` toolbar items are pre-refactor content -> replace with `SAF Conceptual *` counterparts or drop.

## Open questions (WG / spec)

1. Replacements for the 3 deprecated stereotype refs (`SAF_LogicalExternalSystem`, `SAF_LogicalSOI`, old `SAF_PhysicalSystem`) in the ported toolbars: use the new `*_ContextRole_SoI` stereotypes, or drop the toolbar entries?
2. `F3_SPRO` -> `C3_SPRO` (direct lineage) - confirm the pre-refactor SPRO diagram content matches the post-refactor SPRO viewpoint (main separately added C3_SPWB).
3. `O2_STID` (Stakeholder Identification) kept its code in the post-refactor grid - confirm it belongs to the Operational domain diagrams category (not Development).
4. `Physical Logical Item Mapping Viewpoint` was dropped - any pre-refactor diagram content for it is out of scope (none exists among the 10).

## Verification protocol (after port)

1. `make clean && make dist` in SAF_Plugin; inspect generated MDR descriptor list for the 9 new diagram types.
2. Load plugin in Cameo: all 9 diagram types create; toolbar/manipulator stereotypes apply (no silently-dropped filters).
3. `saf_check_consistency` + `validation_run` via MCP; spot-check one ported diagram per domain (C/O/P).
