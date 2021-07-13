package com.interstellar.elite.secure.RSA

import ServiceName
import ServiceRequest
import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.interstellar.elite.BaseActivity
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.DBPersistanceController
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.controller.register.RegisterController
import com.interstellar.elite.core.model.GloabalAssureEntity
import com.interstellar.elite.core.model.PincodeEntity
import com.interstellar.elite.core.model.ProfileEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntity
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntityItem
import com.interstellar.elite.core.response.*
import com.interstellar.elite.databinding.ActivityRoadSideAssistBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Constants
import com.interstellar.elite.utility.DateTimePicker
import com.interstellar.elite.utility.Utility
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class RoadSideAssistActivity : BaseActivity(), View.OnClickListener, IResponseSubcriber {


    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    var YEAR_MANUFACTURE = ""
    var isDataUploaded = true


    lateinit var mAdapter: RoadSideAssistAdapter

    var gloabalAssureList = mutableListOf<GloabalAssureEntity>()



    lateinit var binding: ActivityRoadSideAssistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_road_side_assist)

        binding = ActivityRoadSideAssistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        init()
        setListner()
        setOnTextChangeListner()
        bindData()

//        showDialog("Please Wait")
//        RegisterController(this).getUserProfile(this@RoadSideAssistActivity)

        showDialog("Please Wait")
        AuthenticationController(this).getGlobalAssureCount(
            (loginEntity!!.user_id).toString(),
            this@RoadSideAssistActivity
        )
    }

    private fun init() {

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()

        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController


        val yearOFList: List<String> = DBPersistanceController.getYearOfManufacture()

        //Create adapter
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@RoadSideAssistActivity, R.layout.dropdown_item, yearOFList
        )

        binding.includedRSA.acYearManufacture.setAdapter(adapter)

        binding.includedRSA.acYearManufacture.setOnItemClickListener(
            OnItemClickListener { parent, arg1, pos, id ->

                Constants.hideKeyBoard(binding.includedRSA.acYearManufacture, this)
                YEAR_MANUFACTURE = adapter.getItem(pos) ?: ""
                binding.includedRSA.tilYearManufacture.error = null

            })

    }

    private fun setListner() {

        binding.includedRSA.btnPolicy.setOnClickListener(this)
        binding.includedRSA.etPincode.addTextChangedListener(pincodeTextWatcher)
        binding.includedRSA.btnGetMakeModel.setOnClickListener(this)
        binding.includedRSA.acYearManufacture.setOnClickListener(this)
        binding.includedRSA.etDate.setOnClickListener(this)


    }



    fun setOnTextChangeListner() {

        //region TextChange

        binding.includedRSA.etFullName.doOnTextChanged { text, start, before, count ->

            if (text!!.length > 0) {
                binding.includedRSA.tilName.error = null
            }
        }

        binding.includedRSA.etMobile.doOnTextChanged { text, start, before, count ->

            if (text!!.length > 0) {
                binding.includedRSA.tilMobile.error = null
            }
        }


        binding.includedRSA.etVehicle.doOnTextChanged { text, start, before, count ->

            if (text!!.length > 0) {
                binding.includedRSA.tilVehicle.error = null
            }
        }

        binding.includedRSA.etCity.doOnTextChanged { text, start, before, count ->

            if (text!!.length > 0) {
                binding.includedRSA.tilCity.error = null
            }
        }


        //endregion

    }

    fun bindData() {


        binding.includedRSA.apply {

//            acMake.setText(prefManager.getMakeFromEligibility())
//            acModel.setText(prefManager.getModelFromEligibility())
//            etFullName.setText(loginEntity!!.name)
//            etVehicle.setText(loginEntity!!.vehicleno)
//            etMobile.setText(loginEntity!!.mobile)


            txtSearchHistory.visibility = View.GONE

        }

        binding.includedRSA.rvRSA.layoutManager = LinearLayoutManager(this@RoadSideAssistActivity)

        mAdapter = RoadSideAssistAdapter(gloabalAssureList, this@RoadSideAssistActivity)
        binding.includedRSA.rvRSA.adapter = mAdapter

    }





    private var pincodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (start < 6) {
                binding.includedRSA.etCity.setText("")

            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length == 6 && isDataUploaded) {

                binding.includedRSA.tilPincode.error = null
                showDialog("Fetching City...")
                RegisterController(this@RoadSideAssistActivity).getCityState(
                    binding.includedRSA.etPincode.getText().toString(),
                    this@RoadSideAssistActivity
                )
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    fun getPdfFile(gloabalAssureEntity: GloabalAssureEntity) {

        gloabalAssureEntity.CertificateFile.let {


            downloadPdf(it, "Elite_GlobalAssureDocs" + Utility.currentDateTime())

        }
    }


    fun callLandmarkGlobalAssureRequest() {

        var roadSideRequestEntityItem = RoadSideRequestEntityItem(
            City = binding.includedRSA.etCity.text.toString(),
            ClientName = binding.includedRSA.etFullName.text.toString().trim(),
            ExpiryDate = binding.includedRSA.etDate.text.toString().trim(),
            Make = binding.includedRSA.acMake.text.toString().trim(),
            Model = binding.includedRSA.acModel.text.toString().trim(),
            MobileNo = binding.includedRSA.etMobile.text.toString().trim(),
            ReferenceNo = loginEntity?.activation_code ?: "",
            RegistrationNo = binding.includedRSA.etVehicle.text.toString().trim(),
            YOM = YEAR_MANUFACTURE
        )


        var mitem = RoadSideRequestEntity()

        mitem.add(roadSideRequestEntityItem)

        var RsaRequestString = Gson().toJson(mitem)

        try {
            Log.d(
                "URL",
                "http://elite.landmarkinsurance.in/EliteAppService.svc/InsertOtherCustDetailsForGlobalAssure?js=" + Gson().toJson(
                    mitem
                ).toString()
            )
        }catch( ex : Exception) {

        }



        showDialog("Loading Pdf..")

        authenticationController.getLandmarkEliteGlobalAssureQuery(
            strBody = RsaRequestString,
            iResponseSubcriber = this@RoadSideAssistActivity
        )

    }


    private fun validate(): Boolean {

        if (!isEmpty(binding.includedRSA.etFullName)) {
            binding.includedRSA.etFullName.requestFocus()
            binding.includedRSA.tilName.error = "Enter Name"
            return false
        }
        if (!isEmpty(binding.includedRSA.etMobile)) {
            binding.includedRSA.etMobile.requestFocus()
            binding.includedRSA.tilMobile.error = "Enter Mobile No."
            return false
        }
        if ((binding.includedRSA.etMobile.text.toString().length < 10)) {
            binding.includedRSA.etMobile.requestFocus()
            binding.includedRSA.tilMobile.error = "Enter Valid Mobile No."
            return false
        } else if (!isEmpty(binding.includedRSA.etVehicle)) {
            binding.includedRSA.etVehicle.requestFocus()
            binding.includedRSA.tilVehicle.error = "Enter Vehicle Number"

            return false
        } else if (!isEmpty(binding.includedRSA.acMake)) {
            binding.includedRSA.acMake.requestFocus()
            binding.includedRSA.tilMake.error = "Enter Make"

            return false
        } else if (!isEmpty(binding.includedRSA.acModel)) {
            binding.includedRSA.acModel.requestFocus()
            binding.includedRSA.tilModel.error = "Enter Model"

            return false
        } else if (!validatePinCode(binding.includedRSA.etPincode, binding.includedRSA.tilPincode)) {

            return false
        }
        else if (!isEmpty(binding.includedRSA.etCity)) {
            binding.includedRSA.etCity.requestFocus()
            binding.includedRSA.tilCity.error = "Enter City"

            return false
        }
        else if (binding.includedRSA.acYearManufacture.text.toString().trim().equals("")) {
            binding.includedRSA.tilYearManufacture.error = "Select Year Of Manufacture"

            return false
        } else if (YEAR_MANUFACTURE.equals("")) {
            binding.includedRSA.tilYearManufacture.error = "Select Year Of Manufacture"

            return false
        } else if (!isEmpty(binding.includedRSA.etDate)) {
            binding.includedRSA.tilDate.error = "Enter Date"

            return false
        }



        return true
    }

    override fun onClick(view: View?) {

        Constants.hideKeyBoard(view, this)
        when (view?.id) {

            R.id.btnPolicy -> {

                if (validate()) {


                    showDialog("Please Wait..")

                    authenticationController.getGlobalAssureVerification(
                        LoginID = "" + loginEntity!!.user_id,
                        RegistrationNo = binding.includedRSA.etVehicle.text.toString().trim(),
                        iResponseSubcriber = this@RoadSideAssistActivity
                    )


                    //region OLD One
//                    loginEntity.let {
//
//                        authenticationController.getLandmarkEliteGlobalAssure(
//                            strBody = RsaRequestString,
//                            iResponseSubcriber = this@RoadSideAssistActivity
//                        )
//
//                    }

                    //endregion


                }

            }

            R.id.btnGetMakeModel -> {

                if (!isEmpty(binding.includedRSA.etVehicle)) {
                    binding.includedRSA.etVehicle.requestFocus()
                    binding.includedRSA.tilVehicle.error = "Enter Vehicle Number"

                    return
                } else {

                    showDialog("Please Wait..")

                    authenticationController.getPolicyBossVehicleInfo(
                        RegistrationNumber = binding.includedRSA.etVehicle.text.toString(),
                        iResponseSubcriber = this@RoadSideAssistActivity
                    )
                }
            }

            R.id.etDate -> {

                DateTimePicker.showOpenDatePickerDialog(
                    view.context
                ) { view1, year, monthOfYear, dayOfMonth ->
                    if (view1.isShown) {
                        val calendar = Calendar.getInstance()
                        calendar[year, monthOfYear] = dayOfMonth
                        val currentDay: String = simpleDateFormat.format(calendar.time)
                        binding.includedRSA.etDate.setText(currentDay)
                        binding.includedRSA.tilDate.setError(null)
                    }
                }

            }

        }
    }


    override fun OnSuccess(apiResponse: APIResponse, message: String) {


        //region comment
//        if(apiResponse is GlobalAssureResponse) {
//
//            cancelDialog()
//
//            if(apiResponse.status_code == 0){
//
//                var gloabalAssureEntity : GloabalAssureEntity = apiResponse.Data.get(0)
//
//                getPdfFile(gloabalAssureEntity)
//
//            }
//            else{
//
//                showDialog("Loading Pdf..")
//                loginEntity.let {
//
//                    authenticationController.getLandmarkEliteGlobalAssure(
//                        it!!.mobile.toString(),
//                        it!!.vehicleno,
//                        this@RoadSideAssistActivity
//                    )
//
//                }
//            }
//        }
//
//        else if(apiResponse is GlobalAssureLandmarkResponse){
//
//            if(apiResponse.GlobalAssureResult.CertificateFile != null && apiResponse.GlobalAssureResult.CertificateFile != ""   ){
//
//                var globalAssureResult : GlobalAssureResult = apiResponse.GlobalAssureResult;
//
//                globalAssureResult.CertificateFile.let {
//
//
//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                    startActivity(browserIntent)
//
//                    downloadPdf(it, "Elite_GlobalAssureDocs" + Utility.currentDateTime())
//
//                }
//
//
//                authenticationController.insertGlobalAssure(
//
//                    loginEntity!!.mobile,
//                    loginEntity!!.vehicleno,
//                    globalAssureResult.CertificateNo,
//                    globalAssureResult.CertificateFile,
//                    this@RoadSideAssistActivity
//
//                )
//
//            }else{
//
//                // temp 05
//               // tempAdded()
//               cancelDialog()
//                showAlert("Pdf Not Found.\nContact Landmark Admin")
//            }
//
//
//        }
//
//        else  if (apiResponse is CommonResponse) {
//            // :insertGlobalAssure : Result after Insert Response of local db.
//            cancelDialog()
//            // getCustomToast(" Insertde in Our db")
//
//        }

        //endregion


        if (apiResponse is ProfileResponse) {
            cancelDialog()
            if (apiResponse.status_code == 0) {

                var profileEntity: ProfileEntity? = apiResponse.data?.get(0)

                isDataUploaded = false
                profileEntity.let {

                    binding.includedRSA.etPincode.setText("" + it?.pincode)
                    binding.includedRSA.etCity.setText("" + it?.city_id)

                    isDataUploaded = true
                }

            }

        } else if (apiResponse is PincodeResponse) {
            cancelDialog()

            if (apiResponse.status_code === 0) {

                var pincodeEntity: PincodeEntity? = apiResponse.Data.get(0)
                if (pincodeEntity != null) {

                    binding.includedRSA.etCity.setText("" + pincodeEntity?.cityname ?: "")

                }

            }

        }


        // 1> Count : For showing the data and if count 4 than not allow user to insert data in form
        else if (apiResponse is GlobalAssureResponse) {

            cancelDialog()
            if (apiResponse.status_code == 0) {


                var gloabalAssureList: MutableList<GloabalAssureEntity> = apiResponse.Data

                if (gloabalAssureList.get(0).RegistrationNo != null) {


                    if(gloabalAssureList.size > 3) {

                        binding.includedRSA.lyContainer.visibility = View.GONE

                    }else{

                        binding.includedRSA.lyContainer.visibility = View.VISIBLE

                    }

                    binding.includedRSA.txtSearchHistory.visibility = View.VISIBLE

                   /// 05 latest

                    mAdapter.update(gloabalAssureList)

                  //  binding.includedRSA.svParent.fullScroll(View.FOCUS_DOWN)
                    binding.includedRSA.svParent.postDelayed(
                        Runnable {  binding.includedRSA.svParent.fullScroll(View.FOCUS_DOWN) },
                        300
                    )

                }


            }


        }

        // 2 // Verify user have enterd new record or already saved data
        // if new than allow to hit Landmark API
        else if (apiResponse is VerifyGlobalAssureResponse) {

            var entity: VerifyGlobalAssureEntity? = apiResponse.Data.get(0)


            if (entity?.SavedStatus ?: 1 == 0) {

                // Called Landmark Api

                callLandmarkGlobalAssureRequest()

            } else {

                cancelDialog()
                showAlert("Vehicle number policy is  already generated. ")
            }

        }
        // 3> called
        else if (apiResponse is GlobalAssureLandmarkResponse) {

            cancelDialog()

            if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult != null) {


                if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult != null) {


                    if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult.status.toUpperCase()
                            .contentEquals("SUCCESS")
                    ) {


                        val lastIndex = apiResponse.InsertOtherCustDetailsForGlobalAssureResult.EliteGlobalAssuredetails.size -1
                        var eliteGlobalAssuredetail: EliteGlobalAssuredetail = apiResponse.InsertOtherCustDetailsForGlobalAssureResult.EliteGlobalAssuredetails.get(
                            lastIndex
                        )

                        if (eliteGlobalAssuredetail.PolicyLink.trim().length > 0) {
                            eliteGlobalAssuredetail.PolicyLink.let {

                                Log.d(
                                    "URL Last List Detail",
                                    eliteGlobalAssuredetail.PolicyLink + " Vehicle " + eliteGlobalAssuredetail.RegistrationNo
                                )
                                downloadPdf(
                                    it,
                                    "Elite_GlobalAssureDocs" + Utility.currentDateTime()
                                )


                                showDialog()
                                authenticationController.insertGlobalAssure(

                                    LoginID = loginEntity!!.user_id.toString(),
                                    MobileNo = binding.includedRSA.etMobile.text.toString(),
                                    RegistrationNo = binding.includedRSA.etVehicle.text.toString(),
                                    CertificateNo = "",
                                    CertificateFile = it,
                                    iResponseSubcriber = this@RoadSideAssistActivity

                                )


                            }
                        } else {
                            //getCustomToast("No PDF Found at Server!!")
                            getSnakeBar(binding.ParentLayout, "No PDF Found At Server!!")
                        }

                    } else {

                        //  getCustomToast("No Data Found at Server!!")
                        getSnakeBar(binding.ParentLayout, "No Data Found At Server!!")
                    }


                }


            }
        }

        else if (apiResponse is CommonResponse) {
            // :insertGlobalAssure : Result after Insert Response of local db.
            cancelDialog()
            // getCustomToast(" Insertde in Our db")

        }

        else if (apiResponse is PolicyBossVehicleInfoResponse) {


            if (apiResponse.Make_Name != null && apiResponse.Model_Name != null) {


                if (!apiResponse.Make_Name.equals("")) {
                    binding.includedRSA.acMake.setText(apiResponse.Make_Name)
                    binding.includedRSA.acModel.setText(apiResponse.Model_Name)
                } else {

                    getSnakeBar(binding.ParentLayout, "No Data Found!!")
                }

            } else {
                getSnakeBar(binding.ParentLayout, "No Data Found!!")
            }
        }


    }

    override fun OnFailure(error: String) {
        cancelDialog()
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


    fun downloadPdf(url: String?, name: String) {
        Toast.makeText(this, "Downlaod started..", Toast.LENGTH_LONG).show()
        val r = DownloadManager.Request(Uri.parse(url))
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$name.pdf")
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url))
        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(r)
    }
}