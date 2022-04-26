package com.interstellar.elite.home.dashboardPrdList

import BaseActivityKotlin
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.interstellar.elite.R
import com.interstellar.elite.core.DBPersistanceController
import com.interstellar.elite.core.model.DashProductEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.databinding.ActivityDashBoardProductListBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.product.ProductMainActivity
import com.interstellar.elite.secure.RSA.RoadSideAssistActivity
import com.interstellar.elite.secure.WalletActivity
import com.interstellar.elite.utility.Constants
import com.interstellar.elite.webview.CommonWebViewActivity

import java.util.*


class DashBoardProductListActivity : BaseActivityKotlin() {

    var mAdapter: DashBoardProdListAdapter? = null
    private var dbProductType: String? = null
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
   // private var strTitle: String? = null
    lateinit var  binding : ActivityDashBoardProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashBoardProductListBinding.inflate(layoutInflater)
       // setContentView(R.layout.activity_dash_board_product_list)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar)).apply {
            title = ""

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.includedToolbar.txtTitle.text =  intent.getStringExtra("Title").toString()



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

        //region Commented
//        if (prefManager.getUserEligibility() != null) {
//            val eligibilityEntity = prefManager.getUserEligibility()
//
//            if (eligibilityEntity?.eligible?.toUpperCase().equals("YES")) {
//                imglogo.setImageDrawable(
//                        ContextCompat.getDrawable(
//                                applicationContext,
//                                R.drawable.elite_gold
//                        )
//                )
//            } else {
//                imglogo.setImageDrawable(
//                        ContextCompat.getDrawable(
//                                applicationContext,
//                                R.drawable.elite_plus
//                        )
//                )
//            }
//
//        }
        //endregion

        var isGolduser = loginEntity?.isgoldverify?: ""

        isGolduser.let {

            if(it!!.toUpperCase().equals("Y")){

                //Gold
                binding.includedToolbar.imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_gold))

            }else{

                binding.includedToolbar.imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_plus))

            }


        }
    }

    fun setUserData(){

        loginEntity.let {

            binding.includedToolbar.txtName.text = "Welcome "+it?.name ?:""

        }
    }
    fun init(dbProductType: String) {

        when (dbProductType) {
            Constants.Secure_List -> {

//                bindData(
//                        DBPersistanceController.getSecureList( prefManager.getUserEligibility()?.eligible ?: ""), dbProductType
//                )

                bindData(
                    DBPersistanceController.getSecureList( loginEntity?.isgoldverify?: ""), dbProductType
                )
            }
            Constants.Assure_List -> {
                bindData(
                        DBPersistanceController.getAssureList(
                            loginEntity?.isgoldverify?: ""
                        ), dbProductType
                )
            }
            Constants.RTO_RC_BOOK_List -> {
                bindData(DBPersistanceController.getRtoRcBookAssistList(), dbProductType)
                binding.includedToolbar.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17F);
            }
            Constants.RTO_DRIVING_LICENSE_List -> {
                bindData(DBPersistanceController.getRtoDrivingLicAssistList(), dbProductType)
                binding.includedToolbar.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17F);
            }
        }

    }

    fun bindData(productList: List<DashProductEntity>, dbProductType: String?) {

        binding.includedDashBoardProduct.rvDashboardProduct.layoutManager =
                LinearLayoutManager(this@DashBoardProductListActivity)

            //creating our adapter
            mAdapter = DashBoardProdListAdapter(productList, this, dbProductType!!)

            //now adding the adapter to recyclerview
        binding.includedDashBoardProduct.rvDashboardProduct.adapter = mAdapter
        }

    fun getProduct(entity: DashProductEntity) {


        if(entity.ProductCode.equals("500") || entity.ProductCode.equals("501")  || entity.ProductCode.equals("403"))
        {

            loadWeb(entity)
        }else if(entity.ProductCode.equals("401")){

            val intent = Intent(this, WalletActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)

        } else if(entity.ProductCode.equals("402")){

            val intent = Intent(this, RoadSideAssistActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)

        } else{

            val intent = Intent(this, ProductMainActivity::class.java)
            intent.putExtra(Constants.NON_RTO_PRODUCT_DATA, entity)
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_NONRTO)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }


    }


    fun loadWeb(productEntity: DashProductEntity) {


            if (productEntity.ProductCode.equals("500", ignoreCase = true)) {



            var finPeaceUrl: String = "http://elite.interstellar.co.in/elite_gold/Elite_Gold_App/Finpeace.html?"

            val append = "name="+ loginEntity!!.name + "&MobileNo=" + loginEntity!!.mobile + "&RegistrationNo=" + loginEntity!!.vehicleno

            finPeaceUrl = finPeaceUrl + append

            Log.d("URL",finPeaceUrl);
            val intent = Intent(this, CommonWebViewActivity::class.java)
            intent.putExtra("URL", finPeaceUrl)
            intent.putExtra("TITLE", "My Wealth Protector")
            intent.putExtra("NAME", "FINPEACE")
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        } else if (productEntity.ProductCode.equals("501", ignoreCase = true)) {

            var pitShopUrl: String = "http://elite.interstellar.co.in/elite_gold/Elite_Gold_App/Pit_Stop.html?"


            val append = "name="+ loginEntity!!.name + "&MobileNo=" + loginEntity!!.mobile + "&RegistrationNo=" + loginEntity!!.vehicleno

            pitShopUrl = pitShopUrl + append

            Log.d("URL",pitShopUrl);
            val intent = Intent(this, CommonWebViewActivity::class.java)
            intent.putExtra("URL", pitShopUrl)
            intent.putExtra("TITLE", "Pit Stop ")
            intent.putExtra("NAME", "PIT")
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }else if (productEntity.ProductCode.equals("403", ignoreCase = true)) {

            //http://elite.interstellar.co.in/MaxBupa/Index.html?fba_id=1976&sub_fba_id=&ss_id=1976
            var opdShopUrl: String = "http://elite.interstellar.co.in/MaxBupa/Index.html?"


            val append = "fba_id="+ loginEntity!!.user_id + "&sub_fba_id=eliteplus&ss_id=1"

                opdShopUrl = opdShopUrl + append

            Log.d("URL",opdShopUrl);
            val intent = Intent(this, CommonWebViewActivity::class.java)
            intent.putExtra("URL", opdShopUrl)
            intent.putExtra("TITLE", "OPD COVER ")
            intent.putExtra("NAME", "OPD COVER")
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }



        else if (productEntity.ProductCode.equals("501", ignoreCase = true)) {

            var pitShopUrl: String = "http://elite.interstellar.co.in/elite_gold/Elite_Gold_App/Pit_Stop.html?"


            val append = "name="+ loginEntity!!.name + "&MobileNo=" + loginEntity!!.mobile + "&RegistrationNo=" + loginEntity!!.vehicleno

            pitShopUrl = pitShopUrl + append

            Log.d("URL",pitShopUrl);
            val intent = Intent(this, CommonWebViewActivity::class.java)
            intent.putExtra("URL", pitShopUrl)
            intent.putExtra("TITLE", "Pit Stop ")
            intent.putExtra("NAME", "PIT")
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

