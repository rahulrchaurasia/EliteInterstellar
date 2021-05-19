package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;

import java.util.List;

/**
 * Created by Rahul on 05/02/2018.
 */

public class ClientCommonResponse extends APIResponse {
    private List<Object> Data;

    public List<Object> getData() {
        return Data;
    }

    public void setData(List<Object> Data) {
        this.Data = Data;
    }
}
