package com.interstellar.elite.rto.RtoMain

import BaseActivityKotlin
import android.content.Intent

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat


import com.interstellar.elite.R
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.home.dashboardPrdList.DashBoardProductListActivity
import com.interstellar.elite.utility.Constants
import kotlinx.android.synthetic.main.activity_rto_list.*
import kotlinx.android.synthetic.main.content_rto_list.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class RtoListActivity : BaseActivityKotlin() , View.OnClickListener{

    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rto_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()
        setUserEligibility()
        setUserData()
        setListener()


        txtTitle.text = "RTO+"

    }


    fun setUserEligibility() {
        if (prefManager.getUserEligibility() != null) {
            val eligibilityEntity = prefManager.getUserEligibility()

            if (eligibilityEntity?.eligible?.toUpperCase().equals("YES")) {
                imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_gold))
            } else {
                imglogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.elite_plus))
            }

        }
    }
    private fun setListener() {

        cvRcBook.setOnClickListener(this)
        cvDlAssistance.setOnClickListener(this)

    }


    fun setUserData(){

        loginEntity.let {

            txtName.text = "Welcome "+it?.name ?:""

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