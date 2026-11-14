import com.haarer.saf.rules.RuleDefinition
import com.haarer.saf.rules.RuleSupport
import com.haarer.saf.rules.RuleTrigger
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype

@RuleDefinition(name = "R6: Part of SAF_PhysicalContext (PCXE diagram) -> SAF_PhysicalContextRole", order = 60)
class PhysicalContextRoleRule {

    @RuleTrigger(type = Property.class, skipIfHas = ["SAF_PhysicalContextRole", "SAF_PhysicalContextRole_SoI"])
    void apply(Project project, Element element) {
        Property part = element
        if (!part.isComposite() || part.isDerived()) {
            return
        }
        Element owner = part.getOwner()
        if (owner == null || !RuleSupport.hasAppliedStereotype(owner, "SAF_PhysicalContext")) {
            return
        }
        // The UI "SOI" button creates a block whose documentation carries the
        // "soi" flag; connecting it to the context auto-creates a part typed to
        // that block. When the part's TYPE documentation contains "soi" (exact,
        // case-sensitive) the part is a SoI part.
        def partType = part.getType()
        def comments = (partType == null) ? null : partType.getOwnedComment()
        List soiComments = new ArrayList()
        if (comments != null) {
            if (comments instanceof List) {
                for (Object c : (List) comments) {
                    if (c != null && c.getBody() != null && c.getBody().contains("soi")) {
                        soiComments.add(c)
                    }
                }
            } else if (comments.getBody() != null && comments.getBody().contains("soi")) {
                soiComments.add(comments)
            }
        }
        boolean isSoI = !soiComments.isEmpty()
        String roleName = isSoI ? "SAF_PhysicalContextRole_SoI" : "SAF_PhysicalContextRole"
        Stereotype stereotype = RuleSupport.findStereotype(project, roleName)
        if (stereotype == null) {
            RuleSupport.log("R6: skip part '" + part.getName() + "': no '" + roleName + "' stereotype in project (SAF profile not applied?)")
            return
        }
        if (StereotypesHelper.hasStereotype(part, stereotype)) {
            return
        }
        StereotypesHelper.addStereotype(part, stereotype)
        removePlainPartProperty(project, part)
        for (Object c : soiComments) {
            String body = c.getBody()
            String cleaned = body.replace("soi", "")
            c.setBody(cleaned)
            RuleSupport.log("R6: stripped 'soi' marker from type '" + partType.getName() + "' documentation -> '" + cleaned + "'")
        }
        RuleSupport.log("R6: applied '" + roleName + "' to part '" + part.getName()
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
