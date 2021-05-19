package com.interstellar.elite.rto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.RtoCityMain;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class RtoMainAdapter extends RecyclerView.Adapter<RtoMainAdapter.RtoItem> {

    Fragment mContext;

    List<RtoCityMain> rtoProductDisplayList;

    IRTOCity iRTOCity;

    public RtoMainAdapter(Fragment mContext, List<RtoCityMain> rtoProductDisplayList, IRTOCity irtoCity) {
        this.mContext = mContext;
        this.rtoProductDisplayList = rtoProductDisplayList;
        iRTOCity = irtoCity;
    }

    public class RtoItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public LinearLayout lyParent;


        public RtoItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public RtoItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rto_list_item, parent, false);

        return new RtoItem(itemView);

    }

    @Override
    public void onBindViewHolder(RtoItem holder, int position) {

        final RtoCityMain rtoEntity = rtoProductDisplayList.get(position);

        holder.txtTitle.setText("" + rtoEntity.getSeries_no() + "-" + rtoEntity.getRto_location());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iRTOCity.getRTOCity(rtoEntity);

            }
        });


    }

    @Override
    public int getItemCount() {
        return rtoProductDisplayList.size();
    }
}
