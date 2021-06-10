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
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.*
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.response.UserRegistrationResponse
import com.interstellar.elite.core.response.VerifyUserOTPRegisterResponse
import com.interstellar.elite.databinding.ActivitySignUpBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Constants




class SignUpActivity : BaseActivityKotlin(), View.OnClickListener, IResponseSubcriber  {

    lateinit var binding :ActivitySignUpBinding

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager

    lateinit var makeEntity : MakeEntity
    lateinit var modelEntity : ModelEntity
    var loginEntity: UserEntity? = null
    var policyEntity: PolicyEntity? = null


    var OTP = "000000"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initialize()

        setListner()

        setOnTextChangeListner()


    }

    fun setOnTextChangeListner(){

        //region TextChange

        binding.includedSignup.etFullName.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilName.error = null
            }
        }

        binding.includedSignup.etMobile.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilMobile.error = null
            }
        }

        binding.includedSignup.etEmail.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilEmail.error = null
            }
        }

        binding.includedSignup.etVehicle.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilVehicle.error = null
            }
        }

        binding.includedSignup.etPassword.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilPwd.error = null
            }
        }

        binding.includedSignup.etconfirmPassword.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedSignup.tilConfPwd.error = null
            }
        }

        //endregion

    }
    private fun initialize() {

        binding.includedSignup.etVehicle.setFilters(arrayOf(AllCaps(), LengthFilter(10)))
        prefManager = PrefManager(this)
        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController



    }

    // Not in Used
//    private fun bindDetails() {
//        lyMake.visibility = View.VISIBLE
//        lyModel.visibility = View.VISIBLE
//        lyVehicle.visibility = View.VISIBLE
//        etFullName.isEnabled = false
//        etVehicle.isEnabled = false
//        acMake.isEnabled = false
//        acModel.isEnabled = false
//        etFullName.setText(policyEntity?.InsuredName ?: "")
//        etVehicle.setText(policyEntity?.VehicleNumber ?: "")
//        acMake.setText(policyEntity?.Make ?: "")
//        acModel.setText(policyEntity?.Model ?: "")
//    }

    fun setListner() {
        binding.includedSignup.btnSubmit.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)
    }

    private fun validateRegistration(): Boolean {
        if (!isEmpty(binding.includedSignup.etFullName)) {
            binding.includedSignup.etFullName.requestFocus()
            binding.includedSignup.tilName.error = "Enter Name"
            return false
        }


        if (!isEmpty(binding.includedSignup.etMobile)) {
            binding.includedSignup.etMobile.requestFocus()
            binding.includedSignup.tilMobile.error = "Enter Mobile"
            return false
        }
        if ((binding.includedSignup.etMobile.text.toString().length < 10)) {
            binding.includedSignup.etMobile.requestFocus()
            binding.includedSignup.tilMobile.error = "Enter Mobile"
            return false
        }
        if (!isEmpty(binding.includedSignup.etEmail)) {
            binding.includedSignup.etEmail.requestFocus()
            binding.includedSignup.tilEmail.error = "Enter Email"
            return false
        }
        if (!isValideEmailID(binding.includedSignup.etEmail)) {
            binding.includedSignup.etEmail.requestFocus()
            binding.includedSignup.tilEmail.error = "Enter Valid Email"
            return false
        }
        if (!isEmpty(binding.includedSignup.etVehicle)) {
            binding.includedSignup.etVehicle.requestFocus()
            binding.includedSignup.tilVehicle.error = "Enter Vehicle Number"
            return false
        }
        if (!isEmpty(binding.includedSignup.etPassword)) {
            binding.includedSignup.etPassword.requestFocus()
            binding.includedSignup.tilPwd.error = "Enter Password"
            return false
        }
        if (binding.includedSignup.etPassword.text.toString().trim { it <= ' ' }.length < 3) {
            binding.includedSignup.etPassword.requestFocus()
            binding.includedSignup.tilPwd.error = "Minimum length should be 3"
            return false
        }
        if (!isEmpty(binding.includedSignup.etconfirmPassword)) {
            binding.includedSignup.etconfirmPassword.requestFocus()
            binding.includedSignup.tilConfPwd.error = "Confirm Password"
            return false
        }
        if (binding.includedSignup.etPassword.text.toString() != binding.includedSignup.etconfirmPassword.text.toString()) {
            binding.includedSignup.etconfirmPassword.requestFocus()
            binding.includedSignup.tilConfPwd.error = "Password Mismatch"
            return false
        }
        return true
    }

    //region comment
//    fun onClickMake(view: View){
//        startActivityForResult(
//                Intent(this@SignUpActivity, MakeActivity::class.java),
//                Constants.SEARCH_MAKE_CODE)
//
//    }
//    fun onClickModel(view: View){
//
//        var data = makeEntity
//        startActivityForResult(
//                Intent(this@SignUpActivity, ModelActivity::class.java).putExtra("MAKE", makeEntity),
//                Constants.SEARCH_MODEL_CODE)
//
//    }

    //endregion


    //makeEntity
    override fun onClick(view: View?) {
        hideKeyBoard(binding.includedSignup.btnSubmit)
        when (view?.id) {

            R.id.btnSubmit -> {

                if (validateRegistration() == true) {



                    if (!Constants.checkInternetStatus(this@SignUpActivity)){


                        showAlert("Check Your Internet Connection!!")
                    }else{
                        showLoading("Please wait..")
                        authenticationController.verifyOTPTegistration(
                            email = binding.includedSignup.etEmail.text.toString(),
                            mobile = binding.includedSignup.etMobile.text.toString(),
                            this
                        )
                    }

                }
            }

            R.id.imgClose -> {

                this.finish()
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
            else{

                showOtpAlert()
            }
        }else if (apiResponse is UserRegistrationResponse) {

            if (apiResponse.status_code === 0 ) {
                getCustomToast(apiResponse.message)
                prefManager.clearLoginData()
                finish()
            }else{
                getCustomToast(apiResponse.message)
            }
        }
    }

    override fun OnFailure(error: String) {
        dismissDialog()

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
            name = ("" + binding.includedSignup.etFullName.text),
            emailid = ("" + binding.includedSignup.etEmail.text),
            mobile = ("" + binding.includedSignup.etMobile.text),

            password = ("" + binding.includedSignup.etPassword.getText()),
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

            vehicle_no = binding.includedSignup.etVehicle.text.toString(),
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
            val tvOk = dialog.findViewById<View>(R.id.tvOk) as Button?
            val resend = dialog.findViewById<View>(R.id.tvResend) as Button?
            val txtOTPMessage = dialog.findViewById<View>(R.id.txtOTPMessage) as TextView?
            val tvTime = dialog.findViewById<View>(R.id.tvTime) as TextView?
            val tvTitle = dialog.findViewById<View>(R.id.tvTitle) as TextView?
            val ivCross  =  dialog.findViewById<View>(R.id.ivCross) as ImageView?
            tvTitle!!.text = "Enter OTP sent on : " + binding.includedSignup.etMobile.text.toString()

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


            etOtp!!.doOnTextChanged { text, start, before, count ->

                if(text!!.length >0){
                    txtOTPMessage.visibility = View.GONE
                }
            }
            tvOk!!.setOnClickListener {

                    if (etOtp!!.getText().toString().equals(OTP)) {
                       dialog.dismiss()
                        setRegisterRequest(OTP)
                    } else if (etOtp!!.getText().toString().equals("000000")) {

                        dialog.dismiss()
                        setRegisterRequest(OTP)
                    } else {
                        txtOTPMessage.text = "Invalid OTP"
                        txtOTPMessage.visibility = View.VISIBLE
                    }

            }
            resend!!.setOnClickListener {
                etOtp!!.setText("")
                OTP = "000000"
                showLoading("Re-sending otp...")

                authenticationController.verifyOTPTegistration(
                    binding.includedSignup.etEmail.text.toString(),
                    binding.includedSignup.etMobile.text.toString(),
                    this
                )
            }
            ivCross!!.setOnClickListener{

                dialog.dismiss()
            }
            object : CountDownTimer(120000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    val minutes: Long = millisUntilFinished / 1000 / 60
                    val seconds = (millisUntilFinished / 1000 % 60)
                  //  tvTime!!.text = (millisUntilFinished / 1000).toString() + " seconds remaining"
                    tvTime!!.text = ""+ minutes +":"+ seconds
                }

                override fun onFinish() {
                    tvTime!!.text = ""
                     dialog.dismiss();
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
            dialog.dismiss()
        }
    }

    //region text watcher
    var pincodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (start < 6) {
                binding.includedSignup.etCity.setText("")
                binding.includedSignup.etState.setText("")
                binding.includedSignup.etArea.setText("")
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length == 6) {
                showLoading("Fetching City...")
//                RegisterController(this@SignUpActivity).getCityState(
//                    etPincode.text.toString(),
//                    this@SignUpActivity
//                )
                authenticationController.getCityState(
                    binding.includedSignup.etPincode.getText().toString(),
                    this@SignUpActivity
                )
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
                binding.includedSignup.acMake.setText(makeEntity.make)

            }
        }else if (requestCode == Constants.SEARCH_MODEL_CODE){
            if (data != null) {
                 modelEntity  = data.getParcelableExtra(Constants.SEARCH_MODEL_DATA)!!
                binding.includedSignup.acModel.setText(modelEntity.model)

            }
        }
    }


}