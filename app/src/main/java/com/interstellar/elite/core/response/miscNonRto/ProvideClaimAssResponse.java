package com.interstellar.elite.core.response.miscNonRto;


import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.miscNonRto.ProvideClaimAssEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class ProvideClaimAssResponse extends APIResponse {

    private List<ProvideClaimAssEntity> Data;

    public List<ProvideClaimAssEntity> getData() {
        return Data;
    }

    public void setData(List<ProvideClaimAssEntity> Data) {
        this.Data = Data;
    }


}
