package com.interstellar.elite.feedback;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.CompleteOrderEntity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 05-02-2018.
 */

public class ComplOrderAdapter extends RecyclerView.Adapter<ComplOrderAdapter.OrderDetailItem> {


    Activity mContext;
    List<CompleteOrderEntity> lstOrderDetail;
    ICompOrderData iCompOrderData;


    public ComplOrderAdapter(Activity mContext, List<CompleteOrderEntity> lstOrderDetail , ICompOrderData icompOrderData) {
        this.mContext = mContext;
        this.lstOrderDetail = lstOrderDetail;
        this.iCompOrderData= icompOrderData;

    }

    public class OrderDetailItem extends RecyclerView.ViewHolder {
        TextView txtReqID , txtBody;
        LinearLayout lyParent;


        public OrderDetailItem(View itemView) {
            super(itemView);
            txtReqID = (TextView) itemView.findViewById(R.id.txtReqID);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);

            lyParent =   (LinearLayout) itemView.findViewById(R.id.lyParent);

        }
    }

    @Override
    public OrderDetailItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_feedback_item, parent, false);

        return new OrderDetailItem(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailItem holder, int position) {


        final CompleteOrderEntity orderDetailEntity = lstOrderDetail.get(position);

        holder.txtReqID.setText("" + orderDetailEntity.getDisplay_order_id());
        holder.txtBody.setText("" + orderDetailEntity.getProduct_name());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  ((FeedbackActivity) mContext).getOrderData(orderDetailEntity);
                iCompOrderData.getOrderData(orderDetailEntity);
            }
        });


    }


    @Override
    public int getItemCount() {
        return lstOrderDetail.size();
    }
}
