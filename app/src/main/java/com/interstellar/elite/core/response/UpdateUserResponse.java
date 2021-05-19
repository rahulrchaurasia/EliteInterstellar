package com.interstellar.elite.core.response;


import com.interstellar.elite.core.APIResponse;

/**
 * Created by Rahul on 03/02/2018.
 */

public class UpdateUserResponse extends APIResponse {
    /**
     * Data : Record updated successfully
     */

    private String Data;

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
