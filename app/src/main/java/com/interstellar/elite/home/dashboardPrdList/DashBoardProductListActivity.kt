package com.interstellar.elite.home.dashboardPrdList

import BaseActivityKotlin
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.interstellar.elite.R
import com.interstellar.elite.core.DBPersistanceController
import com.interstellar.elite.core.model.DashProductEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.product.ProductMainActivity
import com.interstellar.elite.utility.Constants
import com.interstellar.elite.webview.WebViewHtmlActivity
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_secure_list.*
import java.util.*


class DashBoardProductListActivity : BaseActivityKotlin() {

    var mAdapter: DashBoardProdListAdapter? = null
    private var dbProductType: String? = null
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
   // private var strTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_product_list)
        setSupportActionBar(findViewById(R.id.toolbar)).apply {
            title = ""

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        txtTitle.text =  intent.getStringExtra("Title").toString()



        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()
        setUserEligibility()
        setUserData()

        dbProductType = intent.getStringExtra(Constants.DB_PRODUCT_TYPE).toString()

        if(dbProductType != null){

            init(dbProductType!!)


        }
    }

    fun setUserEligibility() {
        if (prefManager.getUserEligibility() != null) {
            val eligibilityEntity = prefManager.getUserEligibility()

            if (eligibilityEntity?.eligible?.toUpperCase().equals("YES")) {
                imglogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_gold
                    )
                )
            } else {
                imglogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_plus
                    )
                )
            }

        }
    }

    fun setUserData(){

        loginEntity.let {

            txtName.text = "Welcome "+it?.name ?:""

        }
    }
    fun init(dbProductType: String) {

        when (dbProductType) {
            Constants.Secure_List -> {

                bindData(
                    DBPersistanceController.getSecureList(
                        prefManager.getUserEligibility()?.eligible ?: ""
                    ), dbProductType
                )
            }
            Constants.Assure_List -> {
                bindData(
                    DBPersistanceController.getAssureList(
                        prefManager.getUserEligibility()?.eligible ?: ""
                    ), dbProductType
                )
            }
            Constants.RTO_RC_BOOK_List -> {
                bindData(DBPersistanceController.getRtoRcBookAssistList(), dbProductType)
                txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17F);
            }
            Constants.RTO_DRIVING_LICENSE_List -> {
                bindData(DBPersistanceController.getRtoDrivingLicAssistList(), dbProductType)
                txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17F);
            }
        }

    }

    fun bindData(productList: List<DashProductEntity>, dbProductType: String?) {

            rvDashboardProduct.layoutManager =
                LinearLayoutManager(this@DashBoardProductListActivity)

            //creating our adapter
            mAdapter = DashBoardProdListAdapter(productList, this, dbProductType!!)

            //now adding the adapter to recyclerview
            rvDashboardProduct.adapter = mAdapter
        }

    fun getProduct(entity: DashProductEntity) {


        if(entity.ProductCode.equals("500") || entity.ProductCode.equals("501") ){

            loadWeb(entity)
        }else{

            val intent = Intent(this, ProductMainActivity::class.java)
            intent.putExtra(Constants.NON_RTO_PRODUCT_DATA, entity)
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_NONRTO)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }


    }


    fun loadWeb(productEntity: DashProductEntity) {
        if (productEntity.ProductCode.equals("500", ignoreCase = true)) {
            val intent = Intent(this, WebViewHtmlActivity::class.java)
            intent.putExtra("title", "Finpeace")
            intent.putExtra("type", "FINPEACE")
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        } else if (productEntity.ProductCode.equals("501", ignoreCase = true)) {
            val intent = Intent(this, WebViewHtmlActivity::class.java)
            intent.putExtra("title", "Pit Stop ")
            intent.putExtra("type", "PIT")
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}

