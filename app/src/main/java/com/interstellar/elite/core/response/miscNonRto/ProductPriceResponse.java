package com.interstellar.elite.core.response.miscNonRto;



import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.miscNonRto.ProductPriceEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class ProductPriceResponse extends APIResponse {

    private List<ProductPriceEntity> Data;

    public List<ProductPriceEntity> getData() {
        return Data;
    }

    public void setData(List<ProductPriceEntity> Data) {
        this.Data = Data;
    }


}
