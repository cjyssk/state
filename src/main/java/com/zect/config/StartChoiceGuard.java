package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.Date;


public class StartChoiceGuard implements Guard<ActivityStates, ActivityEvents> {

    @Override
    public boolean evaluate(StateContext<ActivityStates, ActivityEvents> context) {
        Date now = new Date();
        System.out.println("StartChoiceGuard!!!!!!!活动是否已开始!!!!!!");
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
