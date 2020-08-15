package com.zect.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityDTO {

    private Integer id;
    private Integer status;
    private Date startTime;
    private Date endTime;


}
