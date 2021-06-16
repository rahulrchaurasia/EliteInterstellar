package com.interstellar.elite.secure.RSA

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.interstellar.elite.R
import com.interstellar.elite.core.model.GloabalAssureEntity
import java.util.ArrayList


class RoadSideAssistAdapter(var pdfList : MutableList<GloabalAssureEntity>, val context: Context)
    : RecyclerView.Adapter<RoadSideAssistAdapter.PdfClass> (){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfClass {
        return PdfClass(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_rsa_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PdfClass, position: Int) {

        var  entity = pdfList.get(position)

        holder.txtVehicleNo.setText(entity.RegistrationNo)

        holder.lyParent.setOnClickListener{

            (context as RoadSideAssistActivity).getPdfFile(entity)
        }

    }

    override fun getItemCount(): Int {

        return pdfList.size
    }

    fun update(modelList : MutableList<GloabalAssureEntity>){
        pdfList = modelList
        notifyDataSetChanged()
    }

    class PdfClass(view: View) : RecyclerView.ViewHolder(view){


        val txtVehicleNo =view.findViewById(R.id.txtVehicleNo) as TextView
        val lyParent =view.findViewById(R.id.lyParent) as LinearLayout


    }



}