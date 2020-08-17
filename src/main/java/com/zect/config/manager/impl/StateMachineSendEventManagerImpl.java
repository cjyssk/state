package com.zect.config.manager.impl;

import com.zect.config.MarketingStateMachineBuildFactory;
import com.zect.config.manager.StateMachineSendEventManager;
import com.zect.domain.*;
import com.zect.util.ActivityStateMachineUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
public class StateMachineSendEventManagerImpl implements StateMachineSendEventManager {

    @Autowired
    private MarketingStateMachineBuildFactory marketingStateMachineBuildFactory;

    /*@Autowired
    @Qualifier("activityRedisStateMachinePersister")
    private StateMachinePersister<ActivityStates, ActivityEvents,String> activityRedisStateMachinePersister;*/

    @Resource(name="activityPersister")
    private StateMachinePersister<ActivityStates, ActivityEvents, ActivityDTO> activityStateMachinePersister;

    /**
     * 发送状态机event，调用ManagerImpl中具体实现，同时处理状态机持久化
     * <p>
     * 这里会send stateMachine event，从而跳转到对应的action --> ManagerImpl，出现事务嵌套的情况
     * <p>
     * 不过事务传播默认是TransactionDefinition.PROPAGATION_REQUIRED，所以还是同一个事务中，
     * 只是事务范围扩大至stateMachine的持久化场景了,不要修改默认的传播机制
     *
     * @return
     */
    @Override
    @Transactional(value = "activityTransactionManager", rollbackFor = {Exception.class})
    public void sendStatusChangeEvent(ActivityDTO activity,
                                                        ActivityOperationTypeEnum operationTypeEnum,
                                                        ActivityEvents eventEnum) throws Exception {

        // 获取状态机信息
        StateMachine<ActivityStates, ActivityEvents> stateMachine = getStateMachineFromStatusReq(activity, operationTypeEnum);


        boolean result = statusChangeCommonOps(stateMachine, activity, eventEnum);


        // 更新redis中数据
        // 发送event写log的动作还是放在业务里面，这里无法囊括所有业务数据
        /*if (result) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    // 将数据持久化到redis中,以activityId作为对应Key
                    try {
                        activityRedisStateMachinePersister.persist(stateMachine, request.getActivityId());
                    } catch (Exception e) {
                        log.error("Persist activityStateMachine error", e);
                    }
                }
            });
        }*/
    }


    /**
     * 状态处理的通用操作抽取
     *
     * @param stateMachine  状态机
     * @return 执行结果
     * @throws Exception 异常
     */
    private boolean statusChangeCommonOps(
            StateMachine<ActivityStates, ActivityEvents> stateMachine,
            ActivityDTO activity,
            ActivityEvents eventEnum) {

        log.info("activity statemachine send event={}", eventEnum);


        // 执行引擎，sendEvent,result为执行结果,通过actionListener跳转到对应的Action
        Message<ActivityEvents> eventMsg = MessageBuilder.
                withPayload(eventEnum)
                .setHeader("ACTIVITY_CONTEXT_KEY", activity)
                // 只有在需要判断（choice）的场景才用得到，guard实现中使用
                .setHeader("FINAL_STATUS_KEY", activity.getStatus())
                .build();

        // 取到对应的状态机，判断是否可以执行
        boolean result = false;

        // 状态机的当前状态，只有在执行结束后才会变化，也就是节点对应的action执行完才会变更
        // 所以在result=true的情况下，更新状态机的持久化状态才有效
        if (ActivityStateMachineUtils.acceptEvent(stateMachine, eventMsg)) {
            result = stateMachine.sendEvent(eventMsg);
            log.info("activity statemachine send event={},result={}", eventMsg, result);
        } else {
            log.error("当前订单无法执行请求动作");
            //throw new BusinessException(ExceptionCodeEnum.NO_ACTIVITY_STATE_MACHINE_TRANSTION_ERR, "当前订单无法执行请求动作");
        }
        return result;

    }

    /**
     * 从statusRequest中获取statemachine实例
     *
     * @param activity 活动
     * @param operationTypeEnum 操作类型
     * @return 状态机实例
     * @throws Exception 异常
     */
    private StateMachine<ActivityStates, ActivityEvents>
    getStateMachineFromStatusReq(ActivityDTO activity,
                                 ActivityOperationTypeEnum operationTypeEnum) throws Exception {
        log.info("activity status change request={},operationType={}", activity, operationTypeEnum);

        // 查询活动，判断请求数据是否合法
        /*BizOrder bizOrder = bizOrderRepository.selectByBizPrimaryKey(statusRequest.getBizCode());
        if (null == bizOrder
                || !StringUtils.equals(bizOrder.getBizType(), statusRequest.getBizOrderStatusModel().getBizType())
                || !StringUtils.equals(bizOrder.getOrderStatus(), statusRequest.getBizOrderStatusModel().getCurrentOrderStatus())
        ) {
            throw new BusinessException(BizOrderErrorCode.ORDER_COMMON_ILLEGAL_ARGUMENT, "请求数据与订单实际数据不符");
        }*/

        // 构造状态机模板
        StateMachine<ActivityStates, ActivityEvents> srcStateMachine = marketingStateMachineBuildFactory.createStateMachine(String.valueOf(activity.getId()));

        // 从redis中获取对应的statemachine，并判断当前节点是否可以满足，如果无法从redis中获取对应的的statemachine，则取自DB
        /*StateMachine<ActivityStates, ActivityEvents> stateMachine = activityRedisStateMachinePersister.restore(srcStateMachine, statusRequest.getActivityId());

        // 由于DB中已持久化，基本上不太可能出现null的情况，目前唯一能想到会出现的情况就是缓存击穿，先抛错
        if (null == stateMachine) {

            log.error("不存在活动对应的状态机实例");
            //throw new BusinessException(ExceptionCodeEnum.NO_CORRESPONDING_STATEMACHINE_ERR, "不存在活动对应的状态机实例");
        }*/
        log.info("activity stateMachine info is {}", srcStateMachine);
        activityStateMachinePersister.restore(srcStateMachine,activity);
        return srcStateMachine;
    }




}
