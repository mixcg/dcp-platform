package com.dunzung.cloud.framework.event;

import lombok.Data;

import java.util.List;

/**
 * Created by duanzj on 2018/12/1.
 */
@Data
public class UserEvent extends BaseEvent {

    private String provinceId;

    private List<String>  sidList;

    public UserEvent() {

    }

    public UserEvent(List<String> sidList, String action){
        super.setAction(action);
        this.sidList = sidList;
    }


}
