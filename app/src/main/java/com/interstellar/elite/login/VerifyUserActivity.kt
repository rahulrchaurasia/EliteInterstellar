package com.interstellar.elite.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.google.gson.Gson
import com.interstellar.elite.BaseActivity
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.EligibilityEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.*
import com.interstellar.elite.databinding.ActivityVerifyUserBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.home.HomeActivity
import com.interstellar.elite.utility.Constants


class VerifyUserActivity : BaseActivity() ,View.OnClickListener,IResponseSubcriber {

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    lateinit var binding : ActivityVerifyUserBinding
    var ACTIVATION_CODE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_verify_user)

        binding = ActivityVerifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()

        setListner()

//        showDialog()
//
//        loginEntity.let {
//
//            authenticationController.getLandmarkEliteActivationCode(
//                it!!.mobile.toString(),
//                it!!.vehicleno,
//                this@VerifyUserActivity
//            )
//
//        }
    }
    private fun initialize(){

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()
        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController

    }

    private fun setListner(){

        binding.includedVerify.btnSubmit.setOnClickListener(this)
        binding.includedVerify.btnContinue.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)

        binding.includedVerify.etCode.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedVerify.tilCode.error = null
                binding.includedVerify.txtMessage.text = ""
                binding.includedVerify.txtMessage.visibility = View.GONE
            }
        }
    }

    fun tempVerifyActiveCode(){

        var activeCodeJson = ""
        if( loginEntity!!.mobile.equals("8779909962")  ||  loginEntity!!.mobile.equals("9833797088")  ||  loginEntity!!.mobile.equals("9412365852") ) {
            activeCodeJson = "{\"EliteActivationCodeResult\":{\"EliteActivationCodedetails\":[{\"ActivationCode\":\"SURE5766\",\"MobileNo\":\"7400445766\",\"RegistrationNo\":\"MH01CP4237\"}],\"message\":\"\",\"status\":\"Success\",\"status_code\":0}}"

            val objResponse =  Gson().fromJson(activeCodeJson, ActivationCodeLandmarkResponse::class.java)

            ACTIVATION_CODE =  objResponse.EliteActivationCodeResult.EliteActivationCodedetails.get(0).ActivationCode


            if(! ACTIVATION_CODE.equals("")){

                loginEntity.let {

                    authenticationController.insertActivationCode(
                        MobileNo = it!!.mobile.toString(),
                        RegistrationNo = it!!.vehicleno,
                        ActivationCode = ACTIVATION_CODE,
                        this@VerifyUserActivity
                    )

                }
            }
        }


    }

    fun verifyUser(){

        if(binding.includedVerify.etCode.text.toString().equals(ACTIVATION_CODE)){

            showDialog()
            authenticationController.updateGolduser(
                user_id = loginEntity!!.user_id.toString(),
                ActivationCode = binding.includedVerify.etCode.text.toString(),
                this@VerifyUserActivity
            )


        }else{

            binding.includedVerify.txtMessage.visibility = View.VISIBLE
            binding.includedVerify.txtMessage.text = "Activation Code Mismatch!!.Please try with Valid Code"
        }

    }

    fun authenticationAlert(
        strhdr: String,
        strBody: String
    ) {
        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
        val btnClose: Button
        val txtHdr: TextView
        val txtMessage: TextView
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_success_message, null)
        builder.setView(dialogView)
        val alertDialog = builder.create()

        btnClose = dialogView.findViewById(R.id.btnClose)
        txtMessage = dialogView.findViewById(R.id.txtMessage)
        txtHdr = dialogView.findViewById(R.id.txtHdr)
        txtHdr.text = "" + strhdr
        txtMessage.text = "" + strBody
        btnClose.setOnClickListener {
            alertDialog.dismiss()

            val homeActivity = Intent(this@VerifyUserActivity, HomeActivity::class.java)
            startActivity(homeActivity)
            this.finish()

        }
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


    override fun onClick(view: View?) {

        Constants.hideKeyBoard(binding.includedVerify.btnSubmit,this)
        when(view?.id) {



            R.id.btnSubmit ->{

                if(!isEmpty(binding.includedVerify.etCode))
                {
                    binding.includedVerify.tilCode.error = "Enter Activation Code"

                    return

                }

                verifyUser()

            }

            R.id.btnContinue ->{


                val homeActivity = Intent(this@VerifyUserActivity, HomeActivity::class.java)
                startActivity(homeActivity)
                this.finish()
            }

            R.id.imgClose -> {

                this.finish()
            }
        }

    }

    override fun OnSuccess(apiResponse: APIResponse, message: String) {

        if(apiResponse is ActivationCodeLandmarkResponse){
            //success
            if(apiResponse.EliteActivationCodeResult.status_code == 0){

                ACTIVATION_CODE =  apiResponse.EliteActivationCodeResult.EliteActivationCodedetails.get(0).ActivationCode

                 if(! ACTIVATION_CODE.equals("")){

                     loginEntity.let {

                         authenticationController.insertActivationCode(
                             MobileNo = it!!.mobile.toString(),
                             RegistrationNo = it!!.vehicleno,
                             ActivationCode = ACTIVATION_CODE,
                             this@VerifyUserActivity
                         )

                     }
                 }else{

                     binding.includedVerify.btnSubmit.visibility = View.GONE
                     binding.includedVerify.txtMessage.text = "Activation Code Not Found\n Contact Landmark Admin."

                 }

            }else {


                 // temp 05 temp Added
//                cancelDialog()
//                tempVerifyActiveCode()


                // Todo : 05 temp Requied below code

                loginEntity.let {
                    authenticationController.getActivationCode(
                        MobileNo = it!!.mobile,
                        this@VerifyUserActivity
                    )
                }


            }



        }

        else if(apiResponse is ActivationCodeResponse){

            cancelDialog()

            if(apiResponse.status_code == 0){

                ACTIVATION_CODE = apiResponse.Data.get(0).ActivationCode
            }else{
                showAlert("Activation Code Not Found\n Contact Admin.")
            }
        }
        else if(apiResponse is CommonResponse){

            cancelDialog()
        }
        else if(apiResponse is UpladeGoldResponse){

            cancelDialog()




            var modifyLogin =  loginEntity!!.copy(isgoldverify = "Y")
            prefManager.storeUserData(modifyLogin)

            authenticationAlert("Congratulation!!",getString(R.string.ActivationMessage))

        }
    }

    override fun OnFailure(error: String) {
        cancelDialog()
    }


}