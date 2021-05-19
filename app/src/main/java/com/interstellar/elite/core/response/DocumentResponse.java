package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.DocResultEntity;

import java.util.List;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class DocumentResponse extends APIResponse {
    private List<DocResultEntity> Data;

    public List<DocResultEntity> getData() {
        return Data;
    }

    public void setData(List<DocResultEntity> Data) {
        this.Data = Data;
    }




}
