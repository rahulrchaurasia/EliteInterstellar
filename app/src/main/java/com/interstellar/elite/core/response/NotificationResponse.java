package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.NotificationEntity;

import java.util.List;

/**
 * Created by IN-RB on 20-11-2018.
 */

public class NotificationResponse extends APIResponse {


    private List<NotificationEntity> Data;

    public List<NotificationEntity> getData() {
        return Data;
    }

    public void setData(List<NotificationEntity> Data) {
        this.Data = Data;
    }


}
