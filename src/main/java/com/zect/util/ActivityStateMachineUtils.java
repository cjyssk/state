package com.zect.util;

import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineUtils;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.DefaultTriggerContext;
import org.springframework.statemachine.trigger.Trigger;

public class ActivityStateMachineUtils {
    /**
     * 判断是否可以执行对应的event
     *
     * @param stateMachine
     * @param eventMsg
     * @return
     */
    public static boolean acceptEvent(StateMachine<ActivityStates, ActivityEvents> stateMachine,
                                      Message<ActivityEvents> eventMsg) {
        State<ActivityStates, ActivityEvents> cs = stateMachine.getState();

        for (Transition<ActivityStates, ActivityEvents> transition : stateMachine.getTransitions()) {
            State<ActivityStates, ActivityEvents> source = transition.getSource();
            Trigger<ActivityStates, ActivityEvents> trigger = transition.getTrigger();

            if (cs != null && StateMachineUtils.containsAtleastOne(source.getIds(), cs.getIds())) {
                if (trigger != null && trigger.evaluate(new DefaultTriggerContext<>(eventMsg.getPayload()))) {
                    return true;
                }
            }
        }
        return false;
    }

}
