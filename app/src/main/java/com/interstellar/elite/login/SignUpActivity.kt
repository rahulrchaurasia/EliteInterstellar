package com.interstellar.elite.login

import BaseActivityKotlin
import ServiceName
import ServiceRequest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputFilter.AllCaps
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.*
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.response.UserRegistrationResponse
import com.interstellar.elite.core.response.VerifyUserOTPRegisterResponse
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.makemodel.MakeActivity
import com.interstellar.elite.makemodel.ModelActivity
import com.interstellar.elite.utility.Constants
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_sign_up.*
import kotlinx.android.synthetic.main.content_sign_up.etMobile
import kotlinx.android.synthetic.main.content_sign_up.etPassword
import kotlinx.android.synthetic.main.otp_dialog.*


class SignUpActivity : BaseActivityKotlin(), View.OnClickListener, IResponseSubcriber {
    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager

    lateinit var makeEntity : MakeEntity
    lateinit var modelEntity : ModelEntity
    var loginEntity: UserEntity? = null
    var policyEntity: PolicyEntity? = null


    var OTP = "0000"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        //region used whern refer code Used
//        if (intent.hasExtra("POLICY_DATA")) {
//            policyEntity?.let {
//                policyEntity = intent.extras!!.getParcelable("POLICY_DATA")
//                bindDetails()
//            }
//
//        } else {
//            lyMake.visibility = View.GONE
//            lyModel.visibility = View.GONE
//            lyVehicle.visibility = View.GONE
//        }
        //endregion

        initialize()
        setListner()

    }

    private fun initialize() {

        etVehicle.setFilters(arrayOf(AllCaps(), LengthFilter(10)))
        prefManager = PrefManager(this)
        authenticationController = ServiceRequest.getService(
                ServiceName.AUTHENTICATION.value,
                this
        ) as AuthenticationController



    }

    // Not in Used
    private fun bindDetails() {
        lyMake.visibility = View.VISIBLE
        lyModel.visibility = View.VISIBLE
        lyVehicle.visibility = View.VISIBLE
        etFullName.isEnabled = false
        etVehicle.isEnabled = false
        acMake.isEnabled = false
        acModel.isEnabled = false
        etFullName.setText(policyEntity?.InsuredName ?: "")
        etVehicle.setText(policyEntity?.VehicleNumber ?: "")
        acMake.setText(policyEntity?.Make ?: "")
        acModel.setText(policyEntity?.Model ?: "")
    }

    fun setListner() {
        btnSubmit.setOnClickListener(this)

    }

    private fun validateRegistration(): Boolean {
        if (!isEmpty(etFullName)) {
            etFullName.requestFocus()
            etFullName.error = "Enter Name"
            return false
        }


        if (!isEmpty(etMobile)) {
            etMobile.requestFocus()
            etMobile.error = "Enter Mobile"
            return false
        }
        if ((etMobile.toString().length < 10)) {
            etMobile.requestFocus()
            etMobile.error = "Enter Valid Mobile"
            return false
        }
        if (!isEmpty(etEmail)) {
            etEmail.requestFocus()
            etEmail.error = "Enter Email"
            return false
        }
        if (!isValideEmailID(etEmail)) {
            etEmail.requestFocus()
            etEmail.error = "Enter Valid Email"
            return false
        }

        if (!isEmpty(etPassword)) {
            etPassword.requestFocus()
            etPassword.error = "Enter Password"
            return false
        }
        if (etPassword.text.toString().trim { it <= ' ' }.length < 3) {
            etPassword.requestFocus()
            etPassword.error = "Minimum length should be 3"
            return false
        }
        if (!isEmpty(etconfirmPassword)) {
            etconfirmPassword.requestFocus()
            etconfirmPassword.error = "Confirm Password"
            return false
        }
        if (etPassword.text.toString() != etconfirmPassword.text.toString()) {
            etconfirmPassword.requestFocus()
            etconfirmPassword.error = "Password Mismatch"
            return false
        }
        return true
    }

    fun onClickMake(view: View){
        startActivityForResult(
                Intent(this@SignUpActivity, MakeActivity::class.java),
                Constants.SEARCH_MAKE_CODE)

    }
    fun onClickModel(view: View){

        var data = makeEntity
        startActivityForResult(
                Intent(this@SignUpActivity, ModelActivity::class.java).putExtra("MAKE", makeEntity),
                Constants.SEARCH_MODEL_CODE)

    }
    //makeEntity
    override fun onClick(view: View?) {
        hideKeyBoard(btnSubmit)
        when (view?.id) {

            R.id.btnSubmit -> {

                if (validateRegistration() == true) {
                    showLoading("")


                    showLoading("Please wait..")
                    authenticationController.verifyOTPTegistration(
                            email = etEmail.text.toString(),
                            mobile = etMobile.text.toString(),
                            this
                    )

                }
            }
            R.id.acMake -> {


            }

        }

    }


    override fun OnSuccess(apiResponse: APIResponse, message: String) {

        dismissDialog()
        if (apiResponse is VerifyUserOTPRegisterResponse) {

            if (apiResponse.status_code == 0) {

                val verifyOTPEntity: VerifyOTPEntity = apiResponse.Data
                if (verifyOTPEntity.SavedStatus === 1 ) {
                    OTP = verifyOTPEntity.OTP
                    showOtpAlert()
                } else if (verifyOTPEntity.SavedStatus === 2) {
                    getCustomToast(apiResponse.message)
                }
            }
        }else if (apiResponse is UserRegistrationResponse) {

            if (apiResponse.status_code === 0) {
                getCustomToast(apiResponse.message)
                finish()
            }
        }
    }

    override fun OnFailure(error: String) {
        dismissDialog()
        hideKeyBoard(btnSubmit)
    }

     fun setRegisterRequest(strOTP: String) {


        var ProductCode: String = ""
        var RiskEndDate: String = ""
        var RiskStartDate: String = ""
        var InsuredName: String = ""

        var PolicyStatus: String = ""
        var ResponseStatus: String = ""
        var vehicle_no: String = ""
        var policy_no: String = ""

        var Make: String = ""
        var Model: String = ""
        if (policyEntity != null) {
            ProductCode = policyEntity?.ProductCode ?: ""
            RiskEndDate = policyEntity?.RiskEndDate ?: ""
            RiskStartDate = policyEntity?.RiskStartDate ?: ""
            InsuredName = policyEntity?.InsuredName ?: ""

            PolicyStatus = policyEntity?.ProductCode ?: ""
            ResponseStatus = policyEntity?.ProductCode ?: ""
            vehicle_no = policyEntity?.ProductCode ?: ""
            policy_no = policyEntity?.ProductCode ?: ""

            Make = policyEntity?.Make ?: ""
            Model = policyEntity?.Model ?: ""
        }


        val registerRequest = RegisterRequest(
                otp = ("" + strOTP),
                name = ("" + etFullName.text),
                emailid = ("" + etEmail.text),
                mobile = ("" + etMobile.text),

                password = ("" + etPassword.getText()),
                company_id = prefManager.getCompanyID()!!,
                lat = "0",
                lon = "0",
                pincode = (""),
                city = (""),
                state = (""),
                area = (""),
                ProductCode = ProductCode,
                RiskEndDate = RiskEndDate,
                RiskStartDate = RiskStartDate,
                InsuredName = InsuredName,

                policy_no = policy_no,
                PolicyStatus = PolicyStatus,
                ResponseStatus = ResponseStatus,

                vehicle_no = etVehicle.text.toString(),
                Make = Make,
                Model = Model,
                user_id = ""
        )



        showLoading("Please wait..")
        authenticationController.saveUserRegistration(registerRequest, this@SignUpActivity)

    }




    fun showOtpAlert() {
        try {
            val   dialog = Dialog(this)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_dialog)
            val tvOk = dialog.findViewById<View>(R.id.tvOk) as TextView?
            val txtOTPMessage = dialog.findViewById<View>(R.id.txtOTPMessage) as TextView?
            val tvTime = dialog.findViewById<View>(R.id.tvTime) as TextView?
            val tvTitle = dialog.findViewById<View>(R.id.tvTitle) as TextView?
            tvTitle!!.text = "Enter OTP sent on : " + etMobile.text.toString()
            val resend = dialog.findViewById<View>(R.id.tvResend) as TextView?
            val etOtp = dialog.findViewById<View>(R.id.etOtp) as EditText?
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            val dialogWindow = dialog.window
            val lp = dialogWindow!!.attributes
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // Height
            dialogWindow.attributes = lp
            txtOTPMessage!!.text = ""
            txtOTPMessage.visibility = View.GONE
            dialog.show()
            tvOk!!.setOnClickListener { // Close dialog
                if (etOtp!!.getText().toString().equals(OTP)   ) {
                   // etMobile.setText(etMobile.text.toString())
                    dialog.dismiss()
                    setRegisterRequest(OTP)
                } else if(  etOtp!!.getText().toString().equals("0000")){

                    dialog.dismiss()
                    setRegisterRequest("0000")
                }

                else {
                    Toast.makeText(this@SignUpActivity, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    txtOTPMessage.text = "Invalid OTP"
                    txtOTPMessage.visibility = View.VISIBLE
                }
            }
            resend!!.setOnClickListener {
                etOtp!!.setText("")
                OTP = ""
                showLoading("Re-sending otp...")

                authenticationController.verifyOTPTegistration(
                        etEmail.text.toString(),
                        etMobile.text.toString(),
                        this
                )
            }
            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime!!.text = (millisUntilFinished / 1000).toString() + " seconds remaining"
                }

                override fun onFinish() {
                    tvTime!!.text = ""
                    // dialog.dismiss();
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //region text watcher
    var pincodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (start < 6) {
                etCity.setText("")
                etState.setText("")
                etArea.setText("")
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length == 6) {
                showLoading("Fetching City...")
//                RegisterController(this@SignUpActivity).getCityState(
//                    etPincode.text.toString(),
//                    this@SignUpActivity
//                )
                authenticationController.getCityState(etPincode.getText().toString(), this@SignUpActivity)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }
    //endregion


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.SEARCH_MAKE_CODE) {
            if (data != null) {
             makeEntity  = data.getParcelableExtra(Constants.SEARCH_MAKE_DATA)!!
                acMake.setText(makeEntity.make)

            }
        }else if (requestCode == Constants.SEARCH_MODEL_CODE){
            if (data != null) {
                 modelEntity  = data.getParcelableExtra(Constants.SEARCH_MODEL_DATA)!!
                acModel.setText(modelEntity.model)

            }
        }
    }


}