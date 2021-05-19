package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.DocProductEnity;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class ProductDocumentResponse extends APIResponse {


    private List<DocProductEnity> Data;

    public List<DocProductEnity> getData() {
        return Data;
    }

    public void setData(List<DocProductEnity> Data) {
        this.Data = Data;
    }


}
