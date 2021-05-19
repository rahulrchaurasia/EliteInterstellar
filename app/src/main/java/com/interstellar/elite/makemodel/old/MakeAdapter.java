package com.interstellar.elite.makemodel.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.MakeEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class MakeAdapter extends ArrayAdapter<MakeEntity> {

    Context context;
    int resource, textViewResourceId;
    List<MakeEntity> items, tempItems, suggestions;

    public MakeAdapter(Context context, int resource, int textViewResourceId, List<MakeEntity> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<MakeEntity>(items); // this makes the difference.
        suggestions = new ArrayList<MakeEntity>();
    }

    @Nullable
    @Override
    public MakeEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_people, parent, false);
        }
        MakeEntity carMasterEntity = items.get(position);
        if (carMasterEntity != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(carMasterEntity.getMake());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((MakeEntity) resultValue).getMake();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (MakeEntity people : tempItems) {
                    if (people.getMake().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try{
                List<MakeEntity> filterList = (ArrayList<MakeEntity>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (MakeEntity people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }
            }catch (Exception ex){

            }

        }
    };
}