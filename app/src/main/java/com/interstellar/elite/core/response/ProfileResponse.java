package com.interstellar.elite.core.response;


import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.ProfileEntity;

import java.util.List;

/**
 * Created by Rahul on 25/07/2019.
 */
public class ProfileResponse extends APIResponse {


    private List<ProfileEntity> Data;

    public List<ProfileEntity> getData() {
        return Data;
    }

    public void setData(List<ProfileEntity> Data) {
        this.Data = Data;
    }


}
