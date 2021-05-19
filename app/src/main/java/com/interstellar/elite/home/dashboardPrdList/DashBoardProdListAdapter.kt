package com.interstellar.elite.home.dashboardPrdList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.interstellar.elite.R
import com.interstellar.elite.core.model.DashProductEntity
import com.interstellar.elite.utility.Constants


class DashBoardProdListAdapter(val productList: List<DashProductEntity>, val context: Context, val dbProductType: String)
    : RecyclerView.Adapter<DashBoardProdListAdapter.ProductClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductClass {
        if(dbProductType.equals(Constants.Secure_List)){
            return ProductClass(LayoutInflater.from(context).inflate(R.layout.layout_dash_product_secure_item, parent, false))
        }
        else  if(dbProductType.equals(Constants.Assure_List)){

            return ProductClass(LayoutInflater.from(context).inflate(R.layout.layout_dash_product_assure_item, parent, false))
        }
        else{
            return ProductClass(LayoutInflater.from(context).inflate(R.layout.layout_dash_product_rto_item, parent, false))
        }

    }

    override fun onBindViewHolder(holder: ProductClass, position: Int) {
        var entity = productList.get(position);

        holder.txtProductName.setText(entity.title)
        holder.prodImage.setImageResource(entity.img)



        holder.cvParent.setOnClickListener {
            (context as DashBoardProductListActivity).getProduct(entity) }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

     class ProductClass(view: View) : RecyclerView.ViewHolder(view){
        // val prodImage =  view.imglogo
        val prodImage = view.findViewById(R.id.imglogo) as ImageView
        val txtProductName =view.findViewById(R.id.txtProductName) as TextView
         val cvParent =view.findViewById(R.id.cvParent) as CardView

    }

}
