package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.RateDisplayEntity;

import java.util.List;

/**
 * Created by Rahul on 08/01/2019.
 */

public class DisplayRateResponse extends APIResponse {


    private List<RateDisplayEntity> Data;

    public List<RateDisplayEntity> getData() {
        return Data;
    }

    public void setData(List<RateDisplayEntity> Data) {
        this.Data = Data;
    }


}
