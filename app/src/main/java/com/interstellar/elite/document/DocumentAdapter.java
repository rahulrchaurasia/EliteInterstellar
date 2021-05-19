package com.interstellar.elite.document;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.interstellar.elite.R;
import com.interstellar.elite.core.model.DocumentViewEntity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocItem> {

    Activity mContext;
    List<DocumentViewEntity> lstDoc;

    public DocumentAdapter(Activity mContext, List<DocumentViewEntity> lstDoc) {
        this.mContext = mContext;
        this.lstDoc = lstDoc;
    }


    public class DocItem extends RecyclerView.ViewHolder {
        LinearLayout llDocumentUpload;
        ImageView ivPhoto, ivPhotoCam, ivPhotoGallery;
        TextView txtDOC, txtViewDoc;


        public DocItem(View itemView) {
            super(itemView);

            llDocumentUpload = (LinearLayout) itemView.findViewById(R.id.llDocumentUpload);

            txtDOC = (TextView) itemView.findViewById(R.id.txtDOC);
            txtViewDoc = (TextView) itemView.findViewById(R.id.txtViewDoc);

            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            ivPhotoCam = (ImageView) itemView.findViewById(R.id.ivPhotoCam);
            //  ivPhotoGallery = (ImageView) itemView.findViewById(R.id.ivPhotoGallery);


        }
    }

    @Override
    public DocItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_doc_order_item, parent, false);

        return new DocItem(itemView);
    }

    @Override
    public void onBindViewHolder(DocItem holder, int position) {

        final DocumentViewEntity entity = lstDoc.get(position);

        holder.txtDOC.setText("" + entity.getDocument_name());

        if (entity.getPath().trim().equals("")) {
            holder.ivPhoto.setImageResource(R.drawable.doc_notuploaded);
            holder.txtViewDoc.setVisibility(View.INVISIBLE);

        } else {
            holder.ivPhoto.setImageResource(R.drawable.doc_uploaded);
            holder.txtViewDoc.setVisibility(View.VISIBLE);
        }

        if (entity.getDocstatus() == 2) {
            holder.ivPhotoCam.setVisibility(View.GONE);
        } else {
            holder.ivPhotoCam.setVisibility(View.VISIBLE);
        }


        holder.ivPhotoCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((DocUploadActivity) mContext).getActionCamera(entity);
            }
        });


        holder.txtViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((DocUploadActivity) mContext).getActionView(entity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return lstDoc.size();
    }


    public void updateList(DocumentViewEntity curEntity) {

        for (int pos = 0; pos < lstDoc.size(); pos++) {
            if (lstDoc.get(pos).getDoc_id() == (curEntity.getDoc_id())) {

                lstDoc.set(pos, curEntity);

            }
        }

        notifyDataSetChanged();

        //  refreshAdapter(lstSpecial);
    }


}
