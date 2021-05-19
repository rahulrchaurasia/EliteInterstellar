package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.RtoProductDisplayMainEntity;

import java.util.List;

/**
 * Created by IN-RB on 08-08-2018.
 */

public class RtoProductDisplayResponse extends APIResponse {
    private List<RtoProductDisplayMainEntity> Data;

    public List<RtoProductDisplayMainEntity> getData() {
        return Data;
    }

    public void setData(List<RtoProductDisplayMainEntity> Data) {
        this.Data = Data;
    }








}
