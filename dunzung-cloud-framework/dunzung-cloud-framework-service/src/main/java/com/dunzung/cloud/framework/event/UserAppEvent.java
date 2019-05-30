package com.dunzung.cloud.framework.event;

import lombok.Data;

/**
 * Created by duanzj on 2018/12/1.
 */
@Data
public class UserAppEvent extends BaseEvent {

    private String userId;

    private String path;

    public UserAppEvent() {

    }

    public UserAppEvent(String userId, String path, String action) {
        super.setAction(action);
        this.userId = userId;
        this.path = path;
    }


}
