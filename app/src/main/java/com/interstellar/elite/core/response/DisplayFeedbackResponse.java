package com.interstellar.elite.core.response;


import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.model.FeedBackDisplayEntity;

import java.util.List;

/**
 * Created by Rahul on 08/01/2019.
 */

public class DisplayFeedbackResponse extends APIResponse {


    private List<FeedBackDisplayEntity> Data;

    public List<FeedBackDisplayEntity> getData() {
        return Data;
    }

    public void setData(List<FeedBackDisplayEntity> Data) {
        this.Data = Data;
    }


}
