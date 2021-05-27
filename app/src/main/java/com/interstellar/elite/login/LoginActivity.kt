package com.interstellar.elite.login

import BaseActivityKotlin
import ServiceName
import ServiceRequest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.response.LoginResponse
import com.interstellar.elite.databinding.ActivityLoginBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.forgot.ForgotPasswordActivity
import com.interstellar.elite.home.HomeActivity

import kotlinx.android.synthetic.main.content_login.etMobile
import kotlinx.android.synthetic.main.content_login.etPassword



class LoginActivity :  BaseActivityKotlin(), View.OnClickListener ,IResponseSubcriber {

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager

    lateinit var binding : ActivityLoginBinding


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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        initialize()

        setListener()

        setTextChangeListener()
        authenticationController = ServiceRequest.getService(
                ServiceName.AUTHENTICATION.value,
                this
        ) as AuthenticationController

        if (!checkPermission()) {
            requestPermission()
        }


    }

    private fun initialize(){

        prefManager = PrefManager(this)

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

        hideKeyBoard( binding.includedLayout.btnSignIn)
        when (view?.id) {

            R.id.btnSignIn -> {
                if (!isEmpty(etMobile)) {

                    binding.includedLayout.tilLogin.error = "Enter Mobile"

                    return
                }
                if (!isEmpty(etPassword)) {

                    binding.includedLayout.tilPassword.error = "Enter Password"

                    return
                }

                strToken = prefManager.getToken()
                strToken = if (prefManager.getToken() != null) {
                    prefManager.getToken()
                } else {
                    ""
                }


                showLoading("Please wait..")
                //  authenticationController.getLogin("9833797088", "123", "", "", this)
                authenticationController.getLogin(etMobile.text.toString(), etPassword.text.toString(), "", "", this)
//                val homeActivity = Intent(this@LoginActivity, TestActivity::class.java)
//                startActivity(homeActivity)
//                this.finish()


            }
            R.id.tvRegistration -> {

                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }

            R.id.tvForgotPassword -> {

                startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            }


        }
    }

    // Not in Used
    private  fun registrationDecision() {
        if (prefManager.getCompanyID()!!.trim().equals("0")) {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        } else {
            startActivity(Intent(this@LoginActivity, ClientDeclareActivity::class.java))
        }
    }

    override fun OnSuccess(apiResponse: APIResponse, message: String) {


        dismissDialog()

        if (apiResponse is LoginResponse) {

            if (apiResponse.status_code == 0) {

                val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(homeActivity)
                this.finish()
            }
        }
    }

    override fun OnFailure(error: String) {

        dismissDialog()
        hideKeyBoard( binding.includedLayout.btnSignIn)
        //Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(error)
    }

    //endregion


    //region permission
    private fun checkPermission(): Boolean {


        val camera = ContextCompat.checkSelfPermission(applicationContext, perms.get(0))


//        val write_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(1))
//        val read_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(2))
//
//        return camera == PackageManager.PERMISSION_GRANTED && write_external == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED

        if (SDK_INT >= Build.VERSION_CODES.R) {
          return  Environment.isExternalStorageManager()  && camera == PackageManager.PERMISSION_GRANTED
        } else {
            val write_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(1))
             val read_external = ContextCompat.checkSelfPermission(applicationContext, perms.get(2))
            return camera == PackageManager.PERMISSION_GRANTED && write_external == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED

        }
    }

    private fun requestPermission() {


        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                ActivityCompat.requestPermissions(this, perms, PERMISSION_REQUEST_CODE)
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(this, perms, PERMISSION_REQUEST_CODE)
        }
    }

}