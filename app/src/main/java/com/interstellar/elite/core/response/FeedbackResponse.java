package com.interstellar.elite.core.response;



import com.interstellar.elite.core.APIResponse;

import java.util.List;

/**
 * Created by Rahul on 27/12/2018.
 */

public class FeedbackResponse extends APIResponse {


    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * feedbackid : 12
         * Message : FeedBack saved successfully
         */

        private int feedbackid;
        private String Message;

        public int getFeedbackid() {
            return feedbackid;
        }

        public void setFeedbackid(int feedbackid) {
            this.feedbackid = feedbackid;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }
    }
}
