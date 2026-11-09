import com.haarer.saf.rules.RuleDefinition
import com.haarer.saf.rules.RuleSupport
import com.haarer.saf.rules.RuleTrigger
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Diagram
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype

@RuleDefinition(name = "R1: Operational Process diagram -> SAF_OperationalProcess on context Activity", order = 10)
class OperationalProcessRule {

    @RuleTrigger(type = Diagram.class, stereotypes = ["SAF_O3_OPRO"])
    void apply(Project project, Element element) {
        Diagram diagram = element
        Element context = diagram.getContext()
        if (!(context instanceof Activity)) {
            RuleSupport.log("R1: skip diagram '" + diagram.getName() + "': context is "
                    + describe(context) + ", not an Activity")
            return
        }
        Activity activity = (Activity) context
        Stereotype stereotype = RuleSupport.findStereotype(project, "SAF_OperationalProcess")
        if (stereotype == null) {
            RuleSupport.log("R1: skip diagram '" + diagram.getName() + "': no 'SAF_OperationalProcess' stereotype in project (SAF profile not applied?)")
            return
        }
        if (StereotypesHelper.hasStereotype(activity, stereotype)) {
            return
        }
        StereotypesHelper.addStereotype(activity, stereotype)
        RuleSupport.log("R1: applied 'SAF_OperationalProcess' to Activity '" + activity.getName()
                + "' (from SAF Operational Process diagram '" + diagram.getName() + "')")
    }

    private static String describe(Object o) {
        o == null ? "null" : o.getClass().getSimpleName()
    }
}
