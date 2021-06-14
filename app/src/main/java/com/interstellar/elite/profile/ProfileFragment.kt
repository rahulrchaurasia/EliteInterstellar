package com.interstellar.elite.profile

import BaseFragmentKotlin
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.interstellar.elite.BaseFragment.isValideEmailID
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.register.RegisterController
import com.interstellar.elite.core.model.PincodeEntity
import com.interstellar.elite.core.model.ProfileEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.requestmodel.RegisterRequest
import com.interstellar.elite.core.response.PincodeResponse
import com.interstellar.elite.core.response.ProfileResponse
import com.interstellar.elite.core.response.UserRegistrationResponse
import com.interstellar.elite.databinding.FragmentProfileBinding
import com.interstellar.elite.facade.PrefManager
//import kotlinx.android.synthetic.main.fragment_profile.*
//import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : BaseFragmentKotlin(),IResponseSubcriber ,View.OnClickListener{
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentProfileBinding
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    var profileEntity: ProfileEntity? = null
    var pincodeEntity :PincodeEntity? = null

    var isDataUploaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

          binding =  FragmentProfileBinding.inflate(inflater, container, false)
          return binding.root
        //return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireActivity())
        loginEntity = prefManager.getUserData()

        setListener()
        showLoading("Please Wait")
        RegisterController(activity).getUserProfile(this@ProfileFragment)


    }

    private fun setListener() {
        binding.btnSubmit.setOnClickListener(this)

        binding.etPincode.addTextChangedListener(pincodeTextWatcher)
    }

    private fun validateRegistration(): Boolean {

        if (!isEmpty(binding.etEmail)) {
            binding.etEmail.requestFocus()
            binding.etEmail.setError("Enter Email")
            return false
        }
        if (!isValideEmailID(binding.etEmail)) {
            binding.etEmail.requestFocus()
            binding.etEmail.setError("Enter Valid Email")
            return false
        }
        if (!validatePinCode(binding.etPincode,binding.tilPincode)) {

            return false
        }
//        if (!isEmpty(etFullName)) {
//            etFullName.requestFocus()
//            etFullName.setError("Enter Name")
//            return false
//        }
//        if (!isEmpty(etVehicle)) {
//            etVehicle.requestFocus()
//            etVehicle.setError("Enter Vehicle Number")
//            return false
//        }
//        if (!isEmpty(acMake)) {
//            acMake.requestFocus()
//            acMake.setError("Enter Make")
//            return false
//        }
//        if (IsMakeValid == false) {
//            acMake.requestFocus()
//            acMake.setError("Invalid Make")
//            return false
//        }
//        if (acModel.getVisibility() == View.VISIBLE) {
//            if (!isEmpty(acModel)) {
//                acModel.requestFocus()
//                acModel.setError("Enter Model")
//                return false
//            }
//            if (IsModelValid == false) {
//                acModel.requestFocus()
//                acModel.setError("Invalid Model")
//                return false
//            }
//        }
        return true
    }
    private fun setRegisterRequest() {
        val registerRequest = RegisterRequest(
            otp = "",
            emailid = binding.etEmail.text.toString(),
            mobile = binding.etMobile.text.toString(),
            name = binding.etFullName.text.toString(),

            vehicle_no = binding.etVehicle.text.toString(),
            pincode = binding.etPincode.text.toString(),
            city = binding.etCity.text.toString(),
            state = binding.etState.text.toString(),
            user_id = loginEntity!!.user_id!!.toString(),

            password = "",
            company_id = "0",
            lat = "0",
            lon = "0",


            area = "",
            ProductCode = "",
            RiskStartDate = "",
            InsuredName = "",
            RiskEndDate = "",
            policy_no = "",
            PolicyStatus = "",
            ResponseStatus = "",

            Make =  binding.acMake.text.toString(),
            Model = binding.acModel.text.toString(),

        )

        showLoading("Please Wait")
        RegisterController(activity).saveUserProfile(registerRequest, this)
    }

    private var pincodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (start < 6) {
                binding.etCity.setText("")
                binding.etState.setText("")
                binding.etArea.setText("")
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length == 6 && isDataUploaded) {
                showLoading("Fetching City...")
                RegisterController(activity).getCityState(
                    binding.etPincode.getText().toString(),
                    this@ProfileFragment
                )
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun setProfile(profile: ProfileEntity) {

        binding.apply {

         etFullName.setText("" + profile.name)
        etVehicle.setText("" + profile.vehicle_no)
        acMake.setText("" + profile.make)
        acModel.setText("" + profile.model)
        etEmail.setText("" + profile.emailid)
        etMobile.setText("" + profile.mobile)
        etPincode.setText("" + profile.pincode)
        etCity.setText("" + profile.city_id)
        etState.setText("" + profile.state_id)
        acMake.setText(prefManager.getMakeFromEligibility())
        acModel.setText(prefManager.getModelFromEligibility())
        etMobile.setEnabled(false)
        etFullName.setEnabled(false)
        acMake.setEnabled(false)
        acModel.setEnabled(false)
        }

    }

    override fun onResume() {
       // activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        super.onResume()

    }
    companion object {

        @JvmStatic
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun OnSuccess(apiResponse: APIResponse, message: String) {
        hideKeyBoard(binding.etEmail)
        dismissDialog()
        if (apiResponse is ProfileResponse) {
            if (apiResponse.status_code == 0) {

                profileEntity   = apiResponse.data?.get(0)

                isDataUploaded = false
                profileEntity.let {

                    setProfile(it!!)

                    isDataUploaded = true
                }

            }

        } else if (apiResponse is UserRegistrationResponse) {
            if (apiResponse.status_code === 0) {

                val snack = Snackbar.make(binding.etEmail, apiResponse.message, Snackbar.LENGTH_LONG)
                snack.show()
                //Toast.makeText(requireActivity(), apiResponse.message, Toast.LENGTH_LONG)
            }
        } else if (apiResponse is PincodeResponse) {
            if (apiResponse.status_code === 0) {
                pincodeEntity = apiResponse.Data.get(0)
                if (pincodeEntity != null) {
                    binding.etArea.setText("" + pincodeEntity?.postname ?: "")
                    binding.etCity.setText("" + pincodeEntity?.cityname ?: "")
                    binding.etState.setText("" + pincodeEntity?.state_name ?: "")
                }

            }
        }
    }

    override fun OnFailure(error: String) {


        dismissDialog()
    }

    override fun onClick(view: View) {

        hideKeyBoard(view)
        when (view.id ){

            R.id.btnSubmit -> {

                if (validateRegistration() == true) {
                    setRegisterRequest()
                }
            }

        }

    }

    fun onSNACK(view: View){
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, "Replace with your own action",
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        textView.textSize = 28f
        snackbar.show()
    }


}