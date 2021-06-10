package com.interstellar.elite.rto.RtoMain

import BaseActivityKotlin
import android.content.Intent

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat


import com.interstellar.elite.R
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.databinding.ActivityRtoListBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.home.dashboardPrdList.DashBoardProductListActivity
import com.interstellar.elite.utility.Constants


class RtoListActivity : BaseActivityKotlin() , View.OnClickListener{

    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

    lateinit var binding : ActivityRtoListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRtoListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()
        setUserEligibility()
        setUserData()
        setListener()


        binding.includeRtoToolbar.txtTitle.text = "RTO+"

    }


    fun setUserEligibility() {

        //region Commented
//        if (prefManager.getUserEligibility() != null) {
//            val eligibilityEntity = prefManager.getUserEligibility()
//
//            if (eligibilityEntity?.eligible?.toUpperCase().equals("YES")) {
//                imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_gold))
//            } else {
//                imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_plus))
//            }
//
//        }
 //endregion

        var isGolduser = loginEntity?.isgoldverify?: ""

        isGolduser.let {

            if (it!!.toUpperCase().equals("Y")) {
                binding.includeRtoToolbar.imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_gold))
            }else{
                binding.includeRtoToolbar.imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_plus))
            }

        }
   }

    private fun setListener() {

        binding.includeRtoList.cvRcBook.setOnClickListener(this)
        binding.includeRtoList.cvDlAssistance.setOnClickListener(this)

    }


    fun setUserData(){

        loginEntity.let {

            binding.includeRtoToolbar.txtName.text = "Welcome "+it?.name ?:""

        }
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cvRcBook -> {

                moveToDashBoardListActivity(Constants.RTO_RC_BOOK_List,"RC BOOK ASSISTANCE")
            }
            R.id.cvDlAssistance -> {
                  moveToDashBoardListActivity(Constants.RTO_DRIVING_LICENSE_List,"DRIVING LICENSE ASSISTANCE")
            }

        }
    }


    fun moveToDashBoardListActivity(dbProductType : String,strTitle : String){

        val intent = Intent(this@RtoListActivity, DashBoardProductListActivity::class.java)
        intent.putExtra(Constants.DB_PRODUCT_TYPE,dbProductType)
        intent.putExtra("Title", strTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

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