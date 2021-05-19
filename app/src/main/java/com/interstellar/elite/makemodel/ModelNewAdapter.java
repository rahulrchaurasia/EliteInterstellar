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
import com.interstellar.elite.core.model.ModelEntity;
import com.interstellar.elite.core.model.ModelEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class ModelNewAdapter extends RecyclerView.Adapter<ModelNewAdapter.ProductItem> implements Filterable {

    Activity mContext;
    List<ModelEntity> ModelList;


    public ModelNewAdapter(Activity mContext, List<ModelEntity> ModelList) {
        this.mContext = mContext;
        this.ModelList = ModelList;

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

        final ModelEntity entity = ModelList.get(position);

        holder.txtTitle.setText("" + entity.getModel().toUpperCase());
        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ModelActivity) mContext).getModelData(entity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<ModelEntity> filterList = new ArrayList<>();
                    for (int i = 0; i < ModelList.size(); i++) {
                        if ((ModelList.get(i).getModel().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(ModelList.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = ModelList.size();
                    results.values = ModelList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                ModelList = (ArrayList<ModelEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void findAll(List<ModelEntity> ModelList) {
        this.ModelList = ModelList;
        notifyDataSetChanged();
    }
}
