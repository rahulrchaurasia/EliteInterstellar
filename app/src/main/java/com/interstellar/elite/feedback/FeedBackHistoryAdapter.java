package com.interstellar.elite.feedback;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.FeedBackDisplayEntity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class FeedBackHistoryAdapter extends RecyclerView.Adapter<FeedBackHistoryAdapter.InsurerItem> {

    Activity mContext;


    List<FeedBackDisplayEntity> feedBackDisplayEntityList;



    public FeedBackHistoryAdapter(Activity mContext, List<FeedBackDisplayEntity> feedBackDisplayEntityList) {
        this.mContext = mContext;
        this.feedBackDisplayEntityList = feedBackDisplayEntityList;

    }

    public class InsurerItem extends RecyclerView.ViewHolder {
        public TextView txtTitle ,txtBody;
        public LinearLayout lyParent;


        public InsurerItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public InsurerItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_feedback_list_item, parent, false);

        return new InsurerItem(itemView);

    }

    @Override
    public void onBindViewHolder(InsurerItem holder, int position) {

        final FeedBackDisplayEntity feedBackDisplayEntity = feedBackDisplayEntityList.get(position);

        holder.txtTitle.setText("" + feedBackDisplayEntity.getDisplay_request_id());
        holder.txtBody.setText("" + feedBackDisplayEntity.getFeedback_comment());

    }

    @Override
    public int getItemCount() {
        return feedBackDisplayEntityList.size();
    }
}
