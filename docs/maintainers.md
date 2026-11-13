# SAF Profile Maintenance

Documentation for maintainers of `SAF_Profile.mdzip`. User-facing information on what the profile ships is in [profile-support.md](profile-support.md).

The profile contains a **`SAF_Maintainers`** package with Groovy macros that keep the profile in sync with the [SAF-Specification](https://github.com/GfSE/SAF-Specification) and with this repository. Run them from the **Macro Runner** (the Macro tool window, or right-click the macro > Run). The open model must be `SAF_Profile.mdzip`; the runner dialog is prefilled with the default parameter values listed below.

## UpdateSAFProfileFromSpec

Updates profile content from the spec data files (`src/_data/` of a SAF-Specification checkout):

- **stereotype documentation** - all SAF stereotypes, from `stereotypes.json`
- **viewpoint objects** - documentation plus the `viewpointID`, `domain`, `aspect` tags, from `viewpoints.json` and `realizeconcept.json`
- **F1 help customizations** - `helpID` of the `Customization` elements, pointing to the spec web pages (diagram customizations get the viewpoint page, all others the stereotype page)
- **branch-aware links** - for any branch other than `main`, `saf.gfse.org` links are rewritten to `https://saf.gfse.org/version/<branch>/...`

| Parameter | Default | Meaning |
| --- | --- | --- |
| `branch` | `main` | spec branch; selects the documentation base URL and link rewriting |
| `generatorpath` | `<workspace>/SAF-Specification` | SAF-Specification checkout (must contain `src/_data`) |

Runs inside a transactional session; on error the session is rolled back.

## GenerateProfileSupportDoc

Regenerates [profile-support.md](profile-support.md) - the user-facing support table (viewpoints x degree of support), the automation rules table, and the validation rules table.

Data sources:

- spec: `src/_data/viewpoints.json` (name, VP_ID, domain, maturity)
- plugin: `SAF_Plugin/data/defaults/data/diagrams/*/descriptor.xml` (custom diagram types per viewpoint)
- open model: viewpoint objects (`SAF_Viewpoint` stereotype), diagram stereotypes, and the validation rules (Constraints with the `validationRule` stereotype in `SAF_ValidationSuite`)
- rules: the `@RuleDefinition` annotations in `SAF_RulesPlugin/rules/*.groovy`

| Parameter | Default |
| --- | --- |
| `specroot` | `<workspace>/SAF-Specification` |
| `pluginpath` | `<workspace>/SAF-Cameo-Profile/SAF_Plugin` |
| `rulesdir` | `<workspace>/SAF-Cameo-Profile/SAF_RulesPlugin/rules` |
| `outputpath` | `<workspace>/SAF-Cameo-Profile/docs/profile-support.md` |

`<workspace>` = `/home/mac/projects/AI/omp/workspace` on the maintainer machine (the values stored in the macro).

**Keep the doc in sync:** rerun the macro after changes to spec viewpoints, the plugin diagram templates, the profile validation rules, or the rules sources - in particular when preparing a release branch: viewpoints removed from the release must no longer appear as supported.
