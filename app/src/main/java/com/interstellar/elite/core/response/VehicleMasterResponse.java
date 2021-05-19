package com.interstellar.elite.core.response;


import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.VehicleMasterEntity;

/**
 * Created by Nilesh Birhade on 15-11-2018.
 */

public class VehicleMasterResponse extends APIResponse {

    private DataEntity Data;

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public static class DataEntity {



        private VehicleMasterEntity VehicleMasterResult;

        public VehicleMasterEntity getVehicleMasterResult() {
            return VehicleMasterResult;
        }

        public void setVehicleMasterResult(VehicleMasterEntity VehicleMasterResult) {
            this.VehicleMasterResult = VehicleMasterResult;
        }

    }
}
