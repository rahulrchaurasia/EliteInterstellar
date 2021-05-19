package com.interstellar.elite.makemodel.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.ModelEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ModelAdapter extends ArrayAdapter<ModelEntity> {

    Context context;
    int resource, textViewResourceId;
    List<ModelEntity> items, tempItems, suggestions;

    public ModelAdapter(Context context, int resource, int textViewResourceId, List<ModelEntity> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ModelEntity>(items); // this makes the difference.
        suggestions = new ArrayList<ModelEntity>();
    }

    @Nullable
    @Override
    public ModelEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_people, parent, false);
        }
        ModelEntity carMasterEntity = items.get(position);
        if (carMasterEntity != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(carMasterEntity.getModel());
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
            String str = ((ModelEntity) resultValue).getModel();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ModelEntity people : tempItems) {
                    if (people.getModel().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            List<ModelEntity> filterList = (ArrayList<ModelEntity>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ModelEntity people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}