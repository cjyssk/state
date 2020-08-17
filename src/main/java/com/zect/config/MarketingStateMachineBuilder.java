package com.zect.config;

import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;

public interface MarketingStateMachineBuilder {
    String getName();

    StateMachine<ActivityStates, ActivityEvents> build(BeanFactory beanFactory) throws Exception;

    // 活动对应的builder name
    String ACTIVITY_BUILDER_NAME = "activityStateMachineBuilder";

    // 提报商品对应的builder name
    String REGISTRATION_BUILDER_NAME = "registrationStateMachineBuilder";
}
