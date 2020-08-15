package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class ActivityStateMachinePersist implements StateMachinePersist<States, Events, ActivityDTO> {
    @Override
    public void write(StateMachineContext<States, Events> context, ActivityDTO activity) {
        //这里不做任何持久化工作
    }

    @Override
    public StateMachineContext<States, Events> read(ActivityDTO activity) throws Exception {
        StateMachineContext<States, Events> result = new DefaultStateMachineContext<States, Events>(States.valueOf(States.getStateById(String.valueOf(activity.getStatus()))),
                null, null, null, null, "activityMachine");
        return result;
    }
}
