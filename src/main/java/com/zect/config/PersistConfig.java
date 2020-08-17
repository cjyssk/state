package com.zect.config;

import com.zect.domain.ActivityDTO;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class PersistConfig {

    @Autowired
    private ActivityStateMachinePersist activityStateMachinePersist;
    @Bean(name="activityPersister")
    public StateMachinePersister<ActivityStates, ActivityEvents, ActivityDTO> orderPersister() {
        return new DefaultStateMachinePersister<>(activityStateMachinePersist);
    }
}
