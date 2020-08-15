package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class AuditChoiceGuard implements Guard<States, Events> {

    @Override
    public boolean evaluate(StateContext<States, Events> context) {
        System.out.println("AuditChoiceGuard!!!!!!!审核!!!!!!");
        boolean returnValue;
        ActivityDTO activity = context.getMessage().getHeaders().get("activity", ActivityDTO.class);
        if (activity.getStatus() == 6) {
            returnValue = false;
        } else {
            returnValue = true;
        }
        System.out.println(activity.toString()+" is "+returnValue);
        return returnValue;
    }
}
