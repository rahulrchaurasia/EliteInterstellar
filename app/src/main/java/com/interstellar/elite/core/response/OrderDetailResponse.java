package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.OrderDetailEntity;

import java.util.List;

/**
 * Created by IN-RB on 05-02-2018.
 */

public class OrderDetailResponse extends APIResponse {


    private List<OrderDetailEntity> Data;

    public List<OrderDetailEntity> getData() {
        return Data;
    }

    public void setData(List<OrderDetailEntity> Data) {
        this.Data = Data;
    }


}
