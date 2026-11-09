## 2026-11-09 (SAF Model Rules: R4 operational context role)
* new rule R4 (`rules/operational_context_role.groovy`): a newly created part property (composite, non-derived) owned by a `SAF_OperationalContext` element — e.g. a part created in an Operational Context Exchange diagram -> the part gets the `SAF_OperationalContextRole` stereotype; idempotent, never overrides an existing role tag
* stubs: added `Property` (isComposite/isDerived) and `Element.getOwner()` — signatures verified against the live kernel via introspection
* SAF Predefined Structure template: the context role parts of "Family Home Context" (adult, child, storage, the magic machine) were plain non-composite reference properties — corrected to composite (part) semantics
* rebuilt SAF_Plugin.zip (git-ignored build artifact); resource descriptor now lists the fourth rule

## 2026-11-09 (SAF Model Rules integration)
* integrated the SAF Model Rules plugin (com.haarer.saf.rules) into the SAF Plugin: it is now built and shipped inside SAF_Plugin.zip and installs to `plugins/com.haarer.saf.rules/` together with the profile
* new `SAF_RulesPlugin/` source directory: Java core (`src/`), compile-time Cameo SDK stubs (`stubs/`), Groovy rules (`rules/`), `plugin.xml` template, and `build-rules.sh`
* `build-rules.sh`: hermetic build (no Cameo install needed) — compiles stubs + Java core with `javac --release 17` against the Groovy 5.0.0 jar (Maven Central, cached in git-ignored `build/`), packages `cameo-saf-rules-<version>.jar`, syntax-checks the Groovy rules, stages jar + expanded `plugin.xml` + `rules/` into `SAF_Plugin/plugins/com.haarer.saf.rules/` (git-ignored build output)
* `genplugin.groovy`: runs `build-rules.sh` first (version label passed through) and adds `plugins/` to the manifest scan dirs — the jar, plugin.xml, and rule sources are listed in the resource descriptor and included in the zip
* rules auto-apply SAF stereotypes while modeling (Operational Process / System Process diagrams -> `SAF_OperationalProcess` / `SAF_SystemProcess` on their Activity; States in a System State machine -> `SAF_SystemState`); idempotent, hot-reloaded from `rules/` at runtime, requires the Automaton plugin

## 2026-11-09 (post-port review fixes)
* palette-button audit across all 31 diagram templates (544 buttons): 67 button descriptions carried raw stereotype names (`Class«SAF_Claim»`, `SAF_ConceptualContext`, `SAF Physical System`, stray `»`, trailing spaces) — renamed to clean labels (`Claim`, `Conceptual Context`, `Physical System`, ...), camelCase split; the two `*_deprecated` buttons were removed afterwards (see below)
* OPRF `Association«SAF_OperationalStakeholderRepresentation»` button: desc name does not exist in the profile (live stereotype is SAF_StakeholderRepresenting) — renamed to `Stakeholder Representing`
* ARAS `Counter Claim Claimable Item Making` button applied the wrong stereotype: SAF_ClaimClaimableItemSupport (id ..._443463_25050) instead of SAF_CounterClaimClaimableItemMaking (id ..._312955_23278) — id fixed
* STID template was the only one missing the header `<stereotype>` line (viewpoint ref SAF_O2_STID) — added
* `Test Case Statemachine` -> `Test Case State Machine` (4 templates)
* kept as-is (same stereotype id, different element type or standard Cameo pair): `Block` / `Association Block` / `Association Block with Owned Ends` (distinct icons), `Value Type` (DataType) / `Enumeration` (Enumeration), `Test Case Activity/Interaction/State Machine` (Activity/Interaction/StateMachine)
* palette-section naming per new convention (docs/diagram-customization.md): normalized all 23 SAF sections to their diagram name — OCYD (was `SAF Capability Diagram`), STID (was `SAF Stakeholder Identification`), plus 12 more missing `Diagram`/`SAF` prefix or dropping a word (EATR, OCXD, OCXE, OETD, OPRF, OPRO, OSTY, PIFD, PSTD, SCXD, SIEX, SSTD)
* added `Conceptual System` to the SCXD SAF palette: button (stereotype SAF_ConceptualSystem, live id ..._537565_42154, metaclass Class verified against live profile + spec data), placed alphabetically between `Conceptual Environment` and `Conceptual User`
* SCXD manipulators for the conceptual element types, mirroring the established PCXD/SSTD pattern: `ConceptualSystem` (composition + generalization + generalization-opposite, same as its SSTD entry), `ConceptualUser` (generalization), `ConceptualEnvironment` (generalization + composition), `ConceptualContext` (composition to all four conceptual button types + generalization); this also removed two dangling target refs (buttons dropped in the port) from the ConceptualContext path
* removed deprecated-concept palette buttons: `Logical SOI (deprecated)` (SCXD, SSTD), `Logical External System (deprecated)` (SCXD, SUCD); their `<manipulators>` entries kept for legacy elements carrying the deprecated stereotypes
* renamed 2 stale pre-refactor template directories to their `type` attribute (directory = diagram name per convention): `SAF Operational Domain Item Kind Diagram` -> `SAF Operational Exchange Type Definition Diagram`, `SAF Operational Domain Item Table` -> `SAF Operational Exchange Type Definition Table`; plugin manifest file entries updated accordingly
* documented the Diagram Customization naming convention: docs/diagram-customization.md (section = diagram name, stock sections keep stock names, directory = `type` attr, clean button labels, no deprecated palette buttons)
* rebuilt SAF_Plugin.zip (git-ignored build artifact)

## 2026-11-09
* port PreRefactor2026 branch diagram-template improvements into main (post-refactor names)
* 9 new diagram templates: System Internal Exchange (SAF_C4_SIEX), System Structure Definition (SAF_C2_SSTD), Operational Context Exchange (SAF_O1_OCXE), Physical Structure Definition (SAF_P2_PSTD), Stakeholder Identification (SAF_O2_STID), System Context Exchange (SAF_C1_SCXE), System Functional Refinement (SAF_C3_SFRE), System Process (SAF_C3_SPRO), System Use Case (SAF_C1_SUCD)
* 6 templates with substantive improvements (3-way merged): Operational Context Definition, Operational Domain Item Kind (now Operational Exchange Type Definition), Operational Process, Operational Story, System Context Definition, System State
* 8 templates gained the new "Diagram" elementstyle block (abbreviated/type/context frame flags): Argumentation Assurance, Common Standards, EA Traceability, Operational Capability, Operational Performer, Physical Context, Physical Exchange Type, Physical Interface
* semantic remaps applied while porting (pre-refactor -> post-refactor):
    * stereotype display names re-pointed by element ID to live names (all 89 referenced profile elements verified against the live SAF_Profile)
    * Logical/Functional grid -> Conceptual: SAF_LogicalContext -> SAF_ConceptualContext, SAF_LogicalEnvironment -> SAF_ConceptualEnvironment, SAF_LogicalUser -> SAF_ConceptualUser, SAF_LogicalElement -> SAF_ConceptualSystem, SAF_LogicalSOI -> SAF_LogicalSOI_deprecated, SAF_LogicalExternalSystem -> SAF_LogicalExternalSystem_deprecated
    * SAF_OperationalDomainKind -> SAF_OperationalExchangeType
    * icon file prefixes: SAF_F1_/F2_/F3_ -> SAF_C1_/C2_/C3_, SAF_L1_..L4_ -> SAF_C1_..C4_
    * header grid categories: "SAF LD/FD Diagrams" -> "SAF CD Diagrams" (and O/P counterparts)
    * helpID URLs -> canonical https://saf.gfse.org/userdoc/<Domain>/<Viewpoint>.html
    * module paths: C:\Program Files\Magic Systems of Systems Architect\profiles\ -> C:\tools\msoa_r3\profiles\
    * fixes: OCXD "Definintion" typo, ODIK category "Operational Domain Item Kind" -> "Operational Exchange Type Definition", OSTY trailing space, SIEX/SSTD body category "SAF Logical ..." -> "SAF System ..."
* .gitignore: ignore *.info (MagicDraw workspace artifact)
* skipped from branch: SAF Concept Diagram for prod plugin (references pre-refactor stereotype _19_0_1_26f0132_1554446849880_209491_45751 which no longer exists in the profile; its category "SAF Development" is excluded from the prod release zip by the genplugin filter anyway; the dev plugin already ships the working SAF_D2_COTD variant), SAF_Profile.mdzip (main's is the post-refactor authority), MDR resource descriptors (version-stamp change only), genplugin.groovy/gendevplugin.groovy (main already contains the versionLabel description improvement), README.md and build-plugins.yml (branch-specific PreRefactor2026 wording), files.info artifacts (machine noise)
* verification: all 15 ported templates pass — every stereotype id/name reference resolves to the live profile name, no pre-refactor names/paths/URLs remain, xmllint clean; the 8 elementstyle-only files are byte-identical to the branch except the 3 A-domain headers which keep main's post-refactor abbreviations (SAF_A2_*/SAF_A8_*)

## 2026-03-20
* rename SAF_LogcicalElement to SAF_ConceptualSystem
* rename SAF_LogcicalSOI to SAF_LogicalSOI_deprecated
* new stereotype on part property SAF_ConceptualContextRole_SoI
* rename SAF_LogicalContextRole to SAF_ConceptualContextRole
* rename SAF_LogicalInternalRole to SAF_ConceptualInternalRole
* rename SAF_LogicalExternalSystem to SAF_LogicalExternalSystem_deprecated
* rename SAF_PhysicalExternalSystem to SAF_PhysicalExternalSystem_deprecated
* rename SAF_PhysicalSystem to SAF_PhysicalSystem_deprecated
* new stereotype on part property SAF_PhysicalContextRole_SoI
* rename SAF_PhysicalSoftwareElement to SAF_PhysicalSoftware
* rename SAF_PhysicalElement to SAF_PhysicalSystem
* rename SAF_PhysicalHardwareElement to SAF_PhysicalHardware
* rename SAF_PhysicalSystem to SAF_PhysicalSystem_deprecated
* rename SAF_O2_ODIK to SAF_O2_OETD
* rename SAF_O2_ODIK_Table to SAF_O2_OETD_Table
* rename SAF_F2_SDIK to SAF_F2_SETD
* rename SAF_F2_SDIK_Table to SAF_F2_SETD_Table
* rename SAF_ContextFunction to SAF_ContextFunction_deprecated
* rename SAF_SystemFunction to SAF_Function
* new stereotype SAF_FunctionContribution
* rename SAF_SystemFunctionSupport to SAF_FunctionSupport
* rename SAF_DomainKind to SAF_ConceptualExchangeType
* rename SAF_DomainKindComposition to SAF_ConceptualExchangeTypeComposition
* rename SAF_DomainKindDerivation to SAF_ConceptualExchangeTypeDerivation
* rename SAF_SystemPartialFunction to SAF_SystemPartialFunction_deprecated
* rename SAF_LogicalContext to SAF_ConceptualContext
* rename SAF_LogicalEnvironment to SAF_ConceptualEnvironment
* rename SAF_LogicalUser to SAF_ConceptualUser
* rename SAF_LogicalContextElementActing to SAF_ConceptualContextElementActing
* rename SAF_OperationalDomainKind to SAF_OperationalExchangeType
* rename SAF_OperationalDomainKindComposition to SAF_OperationalExchangeTypeComposition