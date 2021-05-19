package com.interstellar.elite.core.response.miscNonRto;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.miscNonRto.InsuranceCompanyEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class MotorInsuranceListResponse extends APIResponse {


    private List<InsuranceCompanyEntity> Data;

    public List<InsuranceCompanyEntity> getData() {
        return Data;
    }

    public void setData(List<InsuranceCompanyEntity> Data) {
        this.Data = Data;
    }


}
