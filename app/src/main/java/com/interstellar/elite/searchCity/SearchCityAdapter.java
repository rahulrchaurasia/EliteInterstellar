package com.interstellar.elite.searchCity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.CityMainEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ProductItem> implements Filterable {

    Activity mContext;
    List<CityMainEntity> CityList;



    public SearchCityAdapter(Activity mContext, List<CityMainEntity> CityList) {
        this.mContext = mContext;
        this.CityList = CityList;

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

        final CityMainEntity entity = CityList.get(position);

        holder.txtTitle.setText("" + entity.getCityname().toUpperCase());
        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SearchCityActivity) mContext).getCity(entity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return CityList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<CityMainEntity> filterList = new ArrayList<>();
                    for (int i = 0; i < CityList.size(); i++) {
                        if ((CityList.get(i).getCityname().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(CityList.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = CityList.size();
                    results.values = CityList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                CityList = (ArrayList<CityMainEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void findAll(List<CityMainEntity> cityList) {
        CityList = cityList;
        notifyDataSetChanged();
    }
}
