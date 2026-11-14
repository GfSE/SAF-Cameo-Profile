import com.haarer.saf.rules.RuleDefinition
import com.haarer.saf.rules.RuleSupport
import com.haarer.saf.rules.RuleTrigger
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype

@RuleDefinition(name = "R5: Part of SAF_ConceptualContext (SCXE diagram) -> SAF_ConceptualContextRole", order = 50)
class ConceptualContextRoleRule {

    @RuleTrigger(type = Property.class, skipIfHas = ["SAF_ConceptualContextRole", "SAF_ConceptualContextRole_SoI"])
    void apply(Project project, Element element) {
        Property part = element
        if (!part.isComposite() || part.isDerived()) {
            return
        }
        Element owner = part.getOwner()
        if (owner == null || !RuleSupport.hasAppliedStereotype(owner, "SAF_ConceptualContext")) {
            return
        }
        Stereotype stereotype = RuleSupport.findStereotype(project, "SAF_ConceptualContextRole")
        if (stereotype == null) {
            RuleSupport.log("R5: skip part '" + part.getName() + "': no 'SAF_ConceptualContextRole' stereotype in project (SAF profile not applied?)")
            return
        }
        if (StereotypesHelper.hasStereotype(part, stereotype)) {
            return
        }
        StereotypesHelper.addStereotype(part, stereotype)
        removePlainPartProperty(project, part)
        RuleSupport.log("R5: applied 'SAF_ConceptualContextRole' to part '" + part.getName()
                + "' (of " + RuleSupport.describe(owner) + ")")
    }

    /**
     * Remove the plain PartProperty stereotype once the context role has
     * been applied (by the caller). The role is derived from PartProperty,
     * so the property remains a part property through the role.
     */
    private void removePlainPartProperty(Project project, Property part) {
        Stereotype partProperty = RuleSupport.findStereotype(project, "PartProperty")
        if (partProperty != null && StereotypesHelper.hasStereotype(part, partProperty)) {
            StereotypesHelper.removeStereotype(part, partProperty)
        }
    }
}
