import com.haarer.saf.rules.RuleDefinition
import com.haarer.saf.rules.RuleSupport
import com.haarer.saf.rules.RuleTrigger
import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Diagram
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine

@RuleDefinition(name = "R2: States in a SAF_C3_SSTA state machine -> SAF_SystemState", order = 20)
class SystemStateRule {

    @RuleTrigger(type = State.class, skipIfHas = ["SAF_SystemState"])
    void apply(Project project, Element element) {
        State state = element
        StateMachine sm = stateMachineOf(state)
        if (sm == null) {
            return
        }
        if (!hasSafSstaDiagram(sm)) {
            return
        }

        Stereotype stereotype = RuleSupport.findStereotype(project, "SAF_SystemState")
        if (stereotype == null) {
            RuleSupport.log("R2: skip state '" + state.getName() + "': no 'SAF_SystemState' stereotype in project (SAF profile not applied?)")
            return
        }
        if (StereotypesHelper.hasStereotype(state, stereotype)) {
            return
        }
        StereotypesHelper.addStereotype(state, stereotype)
        RuleSupport.log("R2: applied 'SAF_SystemState' to State '" + state.getName()
                + "' (in state machine '" + sm.getName() + "')")
    }

    private static StateMachine stateMachineOf(State state) {
        Region region = state.getContainer()
        while (region != null) {
            StateMachine sm = region.getStateMachine()
            if (sm != null) {
                return sm
            }
            State owner = region.getState()
            region = owner == null ? null : owner.getContainer()
        }
        return null
    }

    private static boolean hasSafSstaDiagram(StateMachine sm) {
        for (Element owned : sm.getOwnedElement()) {
            if (owned instanceof Diagram && RuleSupport.hasAppliedStereotype(owned, "SAF_C3_SSTA")) {
                return true
            }
        }
        for (Diagram d : sm.get_diagramOfContext()) {
            if (RuleSupport.hasAppliedStereotype(d, "SAF_C3_SSTA")) {
                return true
            }
        }
        return false
    }
}
