package com.zect.config;

import com.google.common.collect.Maps;
import com.zect.domain.ActivityEvents;
import com.zect.domain.ActivityStates;
import com.zect.domain.ActivityTypeEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zect.config.MarketingStateMachineBuilder.ACTIVITY_BUILDER_NAME;


public class MarketingStateMachineBuildFactory implements InitializingBean {

    @Autowired
    private List<MarketingStateMachineBuilder> builders;

    @Autowired
    private BeanFactory beanFactory;

    /**
     * 用来存储builder-name及builder的map
     */
    private Map<String, MarketingStateMachineBuilder> builderMap = Maps.newConcurrentMap();

    /**
     * 用于存储bizType+subBizType 与 builder-name的集合
     */
    private Map<String, String> bizTypeBuilderMap = Maps.newConcurrentMap();

    /*public StateMachine<ActivityStates, ActivityEvents> createStateMachine(String bizType, String subBizType) {
        if (StringUtils.isBlank(subBizType)) {
            subBizType = "";
        }
        String key = StringUtils.trim(bizType) + StringUtils.trim(subBizType);

        String builderName = bizTypeBuilderMap.get(key);
        if (StringUtils.isBlank(builderName)) {
            //throw new Exception(ExceptionCodeEnum.NO_CORRESPONDING_STATEMACHINE_BUILDER, "当前业务没有对应的状态机配置，请检查");
            System.out.println("当前业务没有对应的状态机配置，请检查");
        }

        return createStateMachine(builderName);
    }*/

    public StateMachine<ActivityStates, ActivityEvents> createStateMachine(String builderName) {

        System.out.println("创建状态机模板");
        MarketingStateMachineBuilder builder = builderMap.get(builderName);

        StateMachine<ActivityStates, ActivityEvents> stateMachine = null;
        try {
            stateMachine = builder.build(beanFactory);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new BusinessException(BizOrderErrorCode.ORDER_GENERIC_EXCEPTION, e.getLocalizedMessage());
        }

        return stateMachine;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        builderMap = builders.stream().collect(Collectors.toMap(
                MarketingStateMachineBuilder::getName,
                Function.identity()
        ));

        // 暂时将bizType和subBizType XXX-单笔授信作为key，绑定对应的XXX状态机，后续还需要绑定别的业务
        //bizTypeBuilderMap.put(BizOrderBizTypeEnum.EMPLOAN.getOrderBizType(), ACTIVITY_BUILDER_NAME);
        // XXX 不区分子业务类型
        bizTypeBuilderMap.put(ActivityTypeEnum.PLATFORM.getId(), ACTIVITY_BUILDER_NAME);
    }
}
