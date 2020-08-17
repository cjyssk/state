package com.zect.config.manager;

import com.zect.domain.*;

public interface StateMachineSendEventManager {

    /**
     * 发送状态机event，调用ManagerImpl中具体实现，同时处理状态机持久化
     * <p>
     * 用于状态变更
     *
     * @param activity
     * @param operationTypeEnum
     * @param eventEnum
     * @return
     */
    void sendStatusChangeEvent(ActivityDTO activity,
                                                 ActivityOperationTypeEnum operationTypeEnum,
                                                 ActivityEvents eventEnum) throws Exception;

}
