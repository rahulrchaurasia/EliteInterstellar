package com.interstellar.elite.login

import BaseActivityKotlin
import ServiceName
import ServiceRequest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.EligibilityUserResponse
import com.interstellar.elite.core.response.LoginResponse
import com.interstellar.elite.databinding.ActivityLoginBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.forgot.ForgotPasswordActivity
import com.interstellar.elite.home.HomeActivity
import com.interstellar.elite.payment.demo.DemoRazorActivity




class LoginActivity :  BaseActivityKotlin(), View.OnClickListener ,IResponseSubcriber {

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager

    lateinit var binding : ActivityLoginBinding
    var loginEntity: UserEntity? = null


    var perms = arrayOf(
        "android.permission.CAMERA",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE",
    )


    private val PERMISSION_REQUEST_CODE = 1001



//   // var prefManager: PrefManager? = null
    var strToken: String? = ""
    var deviceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
       // setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }


        initialize()

        setListener()

        setTextChangeListener()
        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController

//        if (!checkPermission()) {
//            requestPermission()
//        }


    }

    private fun initialize(){

        prefManager = PrefManager(this)

        binding.includedLayout.etMobile.setText("" + prefManager.getMobile())
        binding.includedLayout.etPassword.setText("" + prefManager.getPassword())


    }

    private fun setListener() {

        binding.includedLayout.btnSignIn.setOnClickListener(this)
        binding.includedLayout.tvRegistration.setOnClickListener(this)
        binding.includedLayout.tvForgotPassword.setOnClickListener(this)
    }

    private fun setTextChangeListener() {

        binding.includedLayout.etMobile.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedLayout.tilLogin.error = null
            }
        }
        binding.includedLayout.etPassword.doOnTextChanged { text, start, before, count ->

            if(text!!.length >0){
                binding.includedLayout.tilPassword.error = null
            }
        }

    }

   fun moveToVerifyCode(){

       val homeActivity = Intent(this@LoginActivity, VerifyUserActivity::class.java)
       startActivity(homeActivity)

    }

    fun moveToHome(){

        val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(homeActivity)
        this.finish()
    }

//    private fun initialize() {
//        etPassword = findViewById(R.id.etPassword) as EditText
//        etMobile = findViewById(R.id.etMobile) as EditText
//        btnSignIn = findViewById(R.id.btnSignIn) as Button
//        tvRegistration = findViewById(R.id.tvRegistration) as TextView
//        tvForgotPassword = findViewById(R.id.tvForgotPassword) as TextView
//        btnSignIn = findViewById(R.id.btnSignIn) as Button
//        btnSignIn!!.setOnClickListener(this)
//        tvRegistration!!.setOnClickListener(this)
//        tvForgotPassword!!.setOnClickListener(this)
//    }



    //region Event
    override fun onClick(view: View?) {

        hideKeyBoard(binding.includedLayout.btnSignIn)
        when (view?.id) {

            R.id.btnSignIn -> {
                if (!isEmpty(binding.includedLayout.etMobile)) {

                    binding.includedLayout.tilLogin.error = "Enter Mobile No."

                    return
                }
                else if ((binding.includedLayout.etMobile.text.toString().length < 10)) {
                    binding.includedLayout.etMobile.requestFocus()
                    binding.includedLayout.tilLogin.error = "Enter Valid Mobile No."
                    return
                }
                else if (!isEmpty(binding.includedLayout.etPassword)) {

                    binding.includedLayout.tilPassword.error = "Enter Password"

                    return
                }

                prefManager.setMobile(binding.includedLayout.etMobile.text.toString())
                prefManager.setPassword(binding.includedLayout.etPassword.text.toString())

                //               strToken = prefManager.getToken()
//                strToken = if (prefManager.getToken() != null) {
//                    prefManager.getToken()
//                } else {
//                    ""
//                }





                showLoading("Please wait..")

                authenticationController.getLogin(
                    binding.includedLayout.etMobile.text.toString(),
                    binding.includedLayout.etPassword.text.toString(),
                    "",
                    "",
                    this
                )


            }
            R.id.tvRegistration -> {


               startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))


            }

            R.id.tvForgotPassword -> {


                 startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))



            }


        }
    }



    override fun OnSuccess(apiResponse: APIResponse, message: String) {




        if (apiResponse is LoginResponse) {

            if (apiResponse.status_code == 0) {

                 loginEntity   = prefManager.getUserData()

                if(loginEntity?.isgoldverify?: "" == ("")){

                   // showLoading("Verifying User..")

                    loginEntity.let {

                        authenticationController.getUserEligibility(
                            it!!.mobile.toString(),
                            it!!.vehicleno,
                            this@LoginActivity
                        )
                        //
                    }

                }
                else {

                    dismissDialog()
                    val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(homeActivity)
                    this.finish()
                }

            }
        }

        else if (apiResponse is EligibilityUserResponse) {

            dismissDialog()
            if (apiResponse.EliteEligibilityCheckResult.status_code == 0) {

                prefManager.storeUserEligibility(
                    apiResponse.EliteEligibilityCheckResult.EliteEligibilityCheckdetails.get(0)
                )

                // Todo : Eligibility Response Success hence Verify for Elite Gold

                moveToVerifyCode()


            }
            else{
                /************************ real code (Below code temp )*******************************
                // Todo : Response Failed hence Directly Open Elite Plus

             ********************************************************/

                moveToHome()


                // temp added 05   loginEntity!!.mobile.equals("9403834308") 167815
                // later we have to remove  getEligibilityCall()and directly check   checkEligibility() 8779909962
                // Todo : Commented : Suppose below mob no success response
//                 7400445766  9898147711
//                if(loginEntity!!.mobile.equals("9833797088")   || loginEntity!!.mobile.equals("8779909962")  || loginEntity!!.mobile.equals("9412365852")){
//
//
//                    moveToVerifyCode()
//
//                }else{
//
//                    // For Elite Plus
//                    moveToHome()
//                }

                // Todo : till here



            }

        }
    }

    override fun OnFailure(error: String) {

        dismissDialog()
        hideKeyBoard(binding.includedLayout.btnSignIn)
        //Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(error)
    }

    //endregion


    //region permission
    private fun checkPermission(): Boolean {


        val camera = ContextCompat.checkSelfPermission(applicationContext, perms.get(0))


        val write_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(1))
        val read_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(2))

        return camera == PackageManager.PERMISSION_GRANTED && write_external == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermission() {



            //below android 11
            ActivityCompat.requestPermissions(this, perms, PERMISSION_REQUEST_CODE)

    }

}