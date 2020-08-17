package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class ActivityStateMachinePersist implements StateMachinePersist<ActivityStates, ActivityEvents, ActivityDTO> {
    @Override
    public void write(StateMachineContext<ActivityStates, ActivityEvents> context, ActivityDTO activity) {
        //持久化
    }

    @Override
    public StateMachineContext<ActivityStates, ActivityEvents> read(ActivityDTO activity) throws Exception {
        StateMachineContext<ActivityStates, ActivityEvents> result = new DefaultStateMachineContext<ActivityStates, ActivityEvents>(ActivityStates.valueOf(ActivityStates.getStateById(String.valueOf(activity.getStatus()))),
                null, null, null, null, "activityMachine");
        return result;
    }
}
