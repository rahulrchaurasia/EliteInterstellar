package com.interstellar.elite.secure

import ServiceName
import ServiceRequest
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.interstellar.elite.BaseActivity
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.DBPersistanceController
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.GloabalAssureEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntity
import com.interstellar.elite.core.requestmodel.RoadSideRequestEntityItem
import com.interstellar.elite.core.response.EliteGlobalAssuredetail
import com.interstellar.elite.core.response.GlobalAssureLandmarkResponse
import com.interstellar.elite.databinding.ActivityRoadSideAssistBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Constants
import com.interstellar.elite.utility.DateTimePicker
import com.interstellar.elite.utility.Utility
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection


class RoadSideAssistActivity : BaseActivity(), View.OnClickListener, IResponseSubcriber {


    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    var YEAR_MANUFACTURE = ""

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
        bindData()
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
        binding.includedRSA.acYearManufacture.setOnClickListener(this)
        binding.includedRSA.etDate.setOnClickListener(this)


    }

    fun bindData() {

        binding.includedRSA.apply {

            acMake.setText(prefManager.getMakeFromEligibility())
            acModel.setText(prefManager.getModelFromEligibility())
            etFullName.setText(loginEntity!!.name)
            etVehicle.setText(loginEntity!!.vehicleno)
        }
    }


    fun getPdfFile(gloabalAssureEntity: GloabalAssureEntity) {

        gloabalAssureEntity.CertificateFile.let {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)

            downloadPdf(it, "Elite_GlobalAssureDocs" + Utility.currentDateTime())

        }
    }


    private fun validate(): Boolean {

        if (!isEmpty(binding.includedRSA.etFullName)) {
            binding.includedRSA.etFullName.requestFocus()

            return false
        } else if (!isEmpty(binding.includedRSA.etVehicle)) {
            binding.includedRSA.etVehicle.requestFocus()

            return false
        } else if (!isEmpty(binding.includedRSA.acMake)) {
            binding.includedRSA.acMake.requestFocus()

            return false
        } else if (!isEmpty(binding.includedRSA.acModel)) {
            binding.includedRSA.acModel.requestFocus()

            return false
        } else if (binding.includedRSA.acYearManufacture.text.toString().trim().equals("")) {
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


                    var roadSideRequestEntityItem = RoadSideRequestEntityItem(
                        City = binding.includedRSA.etCitye.text.toString(),
                        ClientName = binding.includedRSA.etFullName.text.toString(),
                        ExpiryDate = binding.includedRSA.etDate.text.toString(),
                        Make = binding.includedRSA.acMake.text.toString(),
                        Model = binding.includedRSA.acModel.text.toString(),
                        MobileNo = binding.includedRSA.etMobile.text.toString(),
                        ReferenceNo = loginEntity?.activation_code ?: "",
                        RegistrationNo = binding.includedRSA.etVehicle.text.toString(),
                        YOM = YEAR_MANUFACTURE
                    )


                    var mitem = RoadSideRequestEntity()

                    mitem.add(roadSideRequestEntityItem)

                    var RsaRequestString = Gson().toJson(mitem)

                    Log.i("MYDATA", Gson().toJson(mitem).toString())

                    ////////////////////////////////////////

                    //  getHttpRequest()

                    showDialog("Loading Pdf..")

                    authenticationController.getLandmarkEliteGlobalAssureQuery(
                        strBody = RsaRequestString,
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

        if (apiResponse is GlobalAssureLandmarkResponse) {

            cancelDialog()

            if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult != null) {


                if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult != null) {


                    if (apiResponse.InsertOtherCustDetailsForGlobalAssureResult.status_code.equals("0")) {


                        var eliteGlobalAssuredetail: EliteGlobalAssuredetail =
                            apiResponse.InsertOtherCustDetailsForGlobalAssureResult.EliteGlobalAssuredetails.get(
                                0
                            )

                        eliteGlobalAssuredetail.PolicyLink.let {


                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            startActivity(browserIntent)

                            downloadPdf(it, "Elite_GlobalAssureDocs" + Utility.currentDateTime())

                        }

                    }


                }


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