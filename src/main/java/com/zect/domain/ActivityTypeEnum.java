package com.zect.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ActivityTypeEnum {

    PLATFORM("1","平台"),
    SHOP("2","店铺");
    private String id;
    private String value;

}
