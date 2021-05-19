package com.interstellar.elite.makemodel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.interstellar.elite.R;
import com.interstellar.elite.core.model.MakeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class MakeNewAdapter extends RecyclerView.Adapter<MakeNewAdapter.ProductItem> implements Filterable {

    Activity mContext;
    List<MakeEntity> MakeList;



    public MakeNewAdapter(Activity mContext, List<MakeEntity> MakeList) {
        this.mContext = mContext;
        this.MakeList = MakeList;

    }


    public class ProductItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        LinearLayout lyParent;


        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);

        }
    }

    @Override
    public ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_city_search_item, parent, false);

        return new ProductItem(itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {

        final MakeEntity entity = MakeList.get(position);

        holder.txtTitle.setText("" + entity.getMake().toUpperCase());
        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MakeActivity) mContext).getMakeData(entity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return MakeList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<MakeEntity> filterList = new ArrayList<>();
                    for (int i = 0; i < MakeList.size(); i++) {
                        if ((MakeList.get(i).getMake().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(MakeList.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = MakeList.size();
                    results.values = MakeList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                MakeList = (ArrayList<MakeEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void findAll(List<MakeEntity> makeList) {
        this.MakeList = makeList;
        notifyDataSetChanged();
    }
}
