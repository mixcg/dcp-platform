package com.dunzung.cloud.framework.event;

import lombok.Data;

/**
 * Created by duanzj on 2018/12/13.
 */
@Data
public class SelfPanelEvent extends BaseEvent {

    private Long panelId;

    private Integer status;

}
