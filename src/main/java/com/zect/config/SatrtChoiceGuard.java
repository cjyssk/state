package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.Events;
import com.zect.domain.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.Date;

public class SatrtChoiceGuard implements Guard<States, Events> {

    @Override
    public boolean evaluate(StateContext<States, Events> context) {
        Date now = new Date();
        System.out.println("SatrtChoiceGuard!!!!!!!活动是否已开始!!!!!!");
        boolean returnValue;
        ActivityDTO activity = context.getMessage().getHeaders().get("activity", ActivityDTO.class);
        if (activity.getStartTime().compareTo(now) >= 0) {
            returnValue = false;
        } else {
            returnValue = true;
        }
        System.out.println(activity.toString()+" is "+returnValue);
        return returnValue;
    }
}
