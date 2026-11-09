import com.haarer.saf.rules.RuleDefinition
import com.haarer.saf.rules.RuleSupport
import com.haarer.saf.rules.RuleTrigger
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype

@RuleDefinition(name = "R4: Part of SAF_OperationalContext (OCXE diagram) -> SAF_OperationalContextRole", order = 40)
class OperationalContextRoleRule {

    @RuleTrigger(type = Property.class, skipIfHas = ["SAF_OperationalContextRole"])
    void apply(Project project, Element element) {
        Property part = element
        if (!part.isComposite() || part.isDerived()) {
            return
        }
        Element owner = part.getOwner()
        if (owner == null || !RuleSupport.hasAppliedStereotype(owner, "SAF_OperationalContext")) {
            return
        }
        Stereotype stereotype = RuleSupport.findStereotype(project, "SAF_OperationalContextRole")
        if (stereotype == null) {
            RuleSupport.log("R4: skip part '" + part.getName() + "': no 'SAF_OperationalContextRole' stereotype in project (SAF profile not applied?)")
            return
        }
        if (StereotypesHelper.hasStereotype(part, stereotype)) {
            return
        }
        StereotypesHelper.addStereotype(part, stereotype)
        RuleSupport.log("R4: applied 'SAF_OperationalContextRole' to part '" + part.getName()
                + "' (of " + RuleSupport.describe(owner) + ")")
    }
}
