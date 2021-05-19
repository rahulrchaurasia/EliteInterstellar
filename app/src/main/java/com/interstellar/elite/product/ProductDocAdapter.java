package com.interstellar.elite.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.DocProductEnity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class ProductDocAdapter extends RecyclerView.Adapter<ProductDocAdapter.ProductItem> {

    Context mContext;
    List<DocProductEnity> lstDoc;

    public ProductDocAdapter(Context mContext, List<DocProductEnity> lstDoc) {
        this.mContext = mContext;
        this.lstDoc = lstDoc;

    }


    public class ProductItem extends RecyclerView.ViewHolder
    {
        public TextView txtTitle,txtDownload;


        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDownload  = (TextView) itemView.findViewById(R.id.txtDownload);
        }
    }

    @Override
    public ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_prd_doc_disp_item, parent, false);

        return new ProductItem (itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {

        final DocProductEnity docProductEnity = lstDoc.get(position);

        holder.txtTitle.setText( "" +docProductEnity.getDocument_name());

        if( docProductEnity.getDocumenturl().equalsIgnoreCase("")){

            holder.txtDownload.setVisibility(View.GONE);

        }else{
            holder.txtDownload.setVisibility(View.VISIBLE);
        }

        holder.txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ProductMainActivity) mContext).new DownloadFromUrl(docProductEnity.getDocumenturl(), docProductEnity.getDocument_name()).execute();


            }
        });

    }



    @Override
    public int getItemCount() {
        return lstDoc.size();
    }
}
