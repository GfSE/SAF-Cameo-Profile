## 2026-11-11 Add more custom diagrams

*   SAF Internal Exchange Diagram
*   SAF Operational Context Interaction Diagram
*   SAF Operational Domain Item Kind Diagram
*   SAF Operational Domain Item Table
*   SAF Physical Context Exchange Diagram
*   SAF Physical Context Exchange Table
*   SAF Physical Context Interaction Diagram
*   SAF Physical Internal Interaction Diagram
*   SAF System Capability Definition Diagram
*   SAF System Functional Breakdown Structure Diagram

## 2026-11-09 (SAF Model Rules: R4 operational context role)

*   new rule R4 (`rules/operational_context_role.groovy`): a newly created part property (composite, non-derived) owned by a `SAF_OperationalContext` element — e.g. a part created in an Operational Context Exchange diagram -> the part gets the `SAF_OperationalContextRole` stereotype; idempotent, never overrides an existing role tag
*   stubs: added `Property` (isComposite/isDerived) and `Element.getOwner()` — signatures verified against the live kernel via introspection
*   SAF Predefined Structure template: the context role parts of "Family Home Context" (adult, child, storage, the magic machine) were plain non-composite reference properties — corrected to composite (part) semantics
*   rebuilt SAF\_Plugin.zip (git-ignored build artifact); resource descriptor now lists the fourth rule

## 2026-11-09 (SAF Model Rules integration)

*   integrated the SAF Model Rules plugin (com.haarer.saf.rules) into the SAF Plugin: it is now built and shipped inside SAF\_Plugin.zip and installs to `plugins/com.haarer.saf.rules/` together with the profile
*   new `SAF_RulesPlugin/` source directory: Java core (`src/`), compile-time Cameo SDK stubs (`stubs/`), Groovy rules (`rules/`), `plugin.xml` template, and `build-rules.sh`
*   `build-rules.sh`: hermetic build (no Cameo install needed) — compiles stubs + Java core with `javac --release 17` against the Groovy 5.0.0 jar (Maven Central, cached in git-ignored `build/`), packages `cameo-saf-rules-<version>.jar`, syntax-checks the Groovy rules, stages jar + expanded `plugin.xml` + `rules/` into `SAF_Plugin/plugins/com.haarer.saf.rules/` (git-ignored build output)
*   `genplugin.groovy`: runs `build-rules.sh` first (version label passed through) and adds `plugins/` to the manifest scan dirs — the jar, plugin.xml, and rule sources are listed in the resource descriptor and included in the zip
*   rules auto-apply SAF stereotypes while modeling (Operational Process / System Process diagrams -> `SAF_OperationalProcess` / `SAF_SystemProcess` on their Activity; States in a System State machine -> `SAF_SystemState`); idempotent, hot-reloaded from `rules/` at runtime, requires the Automaton plugin

## 2026-11-09 (post-port review fixes)

*   palette-button audit across all 31 diagram templates (544 buttons): 67 button descriptions carried raw stereotype names (`Class«SAF_Claim»`, `SAF_ConceptualContext`, `SAF Physical System`, stray `»`, trailing spaces) — renamed to clean labels (`Claim`, `Conceptual Context`, `Physical System`, ...), camelCase split; the two `*_deprecated` buttons were removed afterwards (see below)
*   OPRF `Association«SAF_OperationalStakeholderRepresentation»` button: desc name does not exist in the profile (live stereotype is SAF\_StakeholderRepresenting) — renamed to `Stakeholder Representing`
*   ARAS `Counter Claim Claimable Item Making` button applied the wrong stereotype: SAF\_ClaimClaimableItemSupport (id ...\_443463\_25050) instead of SAF\_CounterClaimClaimableItemMaking (id ...\_312955\_23278) — id fixed
*   STID template was the only one missing the header `<stereotype>` line (viewpoint ref SAF\_O2\_STID) — added
*   `Test Case Statemachine` -> `Test Case State Machine` (4 templates)
*   kept as-is (same stereotype id, different element type or standard Cameo pair): `Block` / `Association Block` / `Association Block with Owned Ends` (distinct icons), `Value Type` (DataType) / `Enumeration` (Enumeration), `Test Case Activity/Interaction/State Machine` (Activity/Interaction/StateMachine)
*   palette-section naming per new convention (docs/diagram-customization.md): normalized all 23 SAF sections to their diagram name — OCYD (was `SAF Capability Diagram`), STID (was `SAF Stakeholder Identification`), plus 12 more missing `Diagram`/`SAF` prefix or dropping a word (EATR, OCXD, OCXE, OETD, OPRF, OPRO, OSTY, PIFD, PSTD, SCXD, SIEX, SSTD)
*   added `Conceptual System` to the SCXD SAF palette: button (stereotype SAF\_ConceptualSystem, live id ...\_537565\_42154, metaclass Class verified against live profile + spec data), placed alphabetically between `Conceptual Environment` and `Conceptual User`
*   SCXD manipulators for the conceptual element types, mirroring the established PCXD/SSTD pattern: `ConceptualSystem` (composition + generalization + generalization-opposite, same as its SSTD entry), `ConceptualUser` (generalization), `ConceptualEnvironment` (generalization + composition), `ConceptualContext` (composition to all four conceptual button types + generalization); this also removed two dangling target refs (buttons dropped in the port) from the ConceptualContext path
*   removed deprecated-concept palette buttons: `Logical SOI (deprecated)` (SCXD, SSTD), `Logical External System (deprecated)` (SCXD, SUCD); their `<manipulators>` entries kept for legacy elements carrying the deprecated stereotypes
*   renamed 2 stale pre-refactor template directories to their `type` attribute (directory = diagram name per convention): `SAF Operational Domain Item Kind Diagram` -> `SAF Operational Exchange Type Definition Diagram`, `SAF Operational Domain Item Table` -> `SAF Operational Exchange Type Definition Table`; plugin manifest file entries updated accordingly
*   documented the Diagram Customization naming convention: docs/diagram-customization.md (section = diagram name, stock sections keep stock names, directory = `type` attr, clean button labels, no deprecated palette buttons)
*   rebuilt SAF\_Plugin.zip (git-ignored build artifact)

## 2026-11-09

*   port PreRefactor2026 branch diagram-template improvements into main (post-refactor names)
*   9 new diagram templates: System Internal Exchange (SAF\_C4\_SIEX), System Structure Definition (SAF\_C2\_SSTD), Operational Context Exchange (SAF\_O1\_OCXE), Physical Structure Definition (SAF\_P2\_PSTD), Stakeholder Identification (SAF\_O2\_STID), System Context Exchange (SAF\_C1\_SCXE), System Functional Refinement (SAF\_C3\_SFRE), System Process (SAF\_C3\_SPRO), System Use Case (SAF\_C1\_SUCD)
*   6 templates with substantive improvements (3-way merged): Operational Context Definition, Operational Domain Item Kind (now Operational Exchange Type Definition), Operational Process, Operational Story, System Context Definition, System State
*   8 templates gained the new "Diagram" elementstyle block (abbreviated/type/context frame flags): Argumentation Assurance, Common Standards, EA Traceability, Operational Capability, Operational Performer, Physical Context, Physical Exchange Type, Physical Interface
*   semantic remaps applied while porting (pre-refactor -> post-refactor):
    *   stereotype display names re-pointed by element ID to live names (all 89 referenced profile elements verified against the live SAF\_Profile)
    *   Logical/Functional grid -> Conceptual: SAF\_LogicalContext -> SAF\_ConceptualContext, SAF\_LogicalEnvironment -> SAF\_ConceptualEnvironment, SAF\_LogicalUser -> SAF\_ConceptualUser, SAF\_LogicalElement -> SAF\_ConceptualSystem, SAF\_LogicalSOI -> SAF\_LogicalSOI\_deprecated, SAF\_LogicalExternalSystem -> SAF\_LogicalExternalSystem\_deprecated
    *   SAF\_OperationalDomainKind -> SAF\_OperationalExchangeType
    *   icon file prefixes: SAF\_F1\_/F2\_/F3\_ -> SAF\_C1\_/C2\_/C3\_, SAF\_L1\_..L4\_ -> SAF\_C1\_..C4\_
    *   header grid categories: "SAF LD/FD Diagrams" -> "SAF CD Diagrams" (and O/P counterparts)
    *   helpID URLs -> canonical https://saf.gfse.org/userdoc//.html
    *   module paths: C:\\Program Files\\Magic Systems of Systems Architect\\profiles\\ -> C:\\tools\\msoa\_r3\\profiles\\
    *   fixes: OCXD "Definintion" typo, ODIK category "Operational Domain Item Kind" -> "Operational Exchange Type Definition", OSTY trailing space, SIEX/SSTD body category "SAF Logical ..." -> "SAF System ..."
*   .gitignore: ignore \*.info (MagicDraw workspace artifact)
*   skipped from branch: SAF Concept Diagram for prod plugin (references pre-refactor stereotype \_19\_0\_1\_26f0132\_1554446849880\_209491\_45751 which no longer exists in the profile; its category "SAF Development" is excluded from the prod release zip by the genplugin filter anyway; the dev plugin already ships the working SAF\_D2\_COTD variant), SAF\_Profile.mdzip (main's is the post-refactor authority), MDR resource descriptors (version-stamp change only), genplugin.groovy/gendevplugin.groovy (main already contains the versionLabel description improvement), README.md and build-plugins.yml (branch-specific PreRefactor2026 wording), files.info artifacts (machine noise)
*   verification: all 15 ported templates pass — every stereotype id/name reference resolves to the live profile name, no pre-refactor names/paths/URLs remain, xmllint clean; the 8 elementstyle-only files are byte-identical to the branch except the 3 A-domain headers which keep main's post-refactor abbreviations (SAF\_A2\_\*/SAF\_A8\_\*)

## 2026-03-20

*   rename SAF\_LogcicalElement to SAF\_ConceptualSystem
*   rename SAF\_LogcicalSOI to SAF\_LogicalSOI\_deprecated
*   new stereotype on part property SAF\_ConceptualContextRole\_SoI
*   rename SAF\_LogicalContextRole to SAF\_ConceptualContextRole
*   rename SAF\_LogicalInternalRole to SAF\_ConceptualInternalRole
*   rename SAF\_LogicalExternalSystem to SAF\_LogicalExternalSystem\_deprecated
*   rename SAF\_PhysicalExternalSystem to SAF\_PhysicalExternalSystem\_deprecated
*   rename SAF\_PhysicalSystem to SAF\_PhysicalSystem\_deprecated
*   new stereotype on part property SAF\_PhysicalContextRole\_SoI
*   rename SAF\_PhysicalSoftwareElement to SAF\_PhysicalSoftware
*   rename SAF\_PhysicalElement to SAF\_PhysicalSystem
*   rename SAF\_PhysicalHardwareElement to SAF\_PhysicalHardware
*   rename SAF\_PhysicalSystem to SAF\_PhysicalSystem\_deprecated
*   rename SAF\_O2\_ODIK to SAF\_O2\_OETD
*   rename SAF\_O2\_ODIK\_Table to SAF\_O2\_OETD\_Table
*   rename SAF\_F2\_SDIK to SAF\_F2\_SETD
*   rename SAF\_F2\_SDIK\_Table to SAF\_F2\_SETD\_Table
*   rename SAF\_ContextFunction to SAF\_ContextFunction\_deprecated
*   rename SAF\_SystemFunction to SAF\_Function
*   new stereotype SAF\_FunctionContribution
*   rename SAF\_SystemFunctionSupport to SAF\_FunctionSupport
*   rename SAF\_DomainKind to SAF\_ConceptualExchangeType
*   rename SAF\_DomainKindComposition to SAF\_ConceptualExchangeTypeComposition
*   rename SAF\_DomainKindDerivation to SAF\_ConceptualExchangeTypeDerivation
*   rename SAF\_SystemPartialFunction to SAF\_SystemPartialFunction\_deprecated
*   rename SAF\_LogicalContext to SAF\_ConceptualContext
*   rename SAF\_LogicalEnvironment to SAF\_ConceptualEnvironment
*   rename SAF\_LogicalUser to SAF\_ConceptualUser
*   rename SAF\_LogicalContextElementActing to SAF\_ConceptualContextElementActing
*   rename SAF\_OperationalDomainKind to SAF\_OperationalExchangeType
*   rename SAF\_OperationalDomainKindComposition to SAF\_OperationalExchangeTypeComposition