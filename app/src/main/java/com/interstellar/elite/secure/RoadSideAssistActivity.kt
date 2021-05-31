package com.interstellar.elite.secure

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.google.gson.Gson
import com.interstellar.elite.BaseActivity
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.GloabalAssureEntity
import com.interstellar.elite.core.model.GlobalAssureResult
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.CommonResponse
import com.interstellar.elite.core.response.GlobalAssureLandmarkResponse
import com.interstellar.elite.core.response.GlobalAssureResponse
import com.interstellar.elite.core.response.TataLandmarkResponse
import com.interstellar.elite.databinding.ActivityRoadSideAssistBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Utility

class RoadSideAssistActivity : BaseActivity() , View.OnClickListener , IResponseSubcriber {


    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

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
    }

    private fun init() {

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()

        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController

    }

    private fun setListner(){

        binding.includedRSA.btnPolicy.setOnClickListener(this)
    }

    fun getPdfFile(gloabalAssureEntity : GloabalAssureEntity){

        gloabalAssureEntity.CertificateFile.let {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)

            downloadPdf(it, "Elite_GlobalAssureDocs"+ Utility.currentDateTime())

        }
    }

    fun getTempJson(): GlobalAssureLandmarkResponse  =

        Gson().fromJson(
            "{\"GlobalAssureResult\":{\"CertificateFile\":\"\",\"CertificateNo\":\"HA2905210000000006\",\"ErrorCode\":\"\",\"ErrorMessage\":\"\",\"ErrorMessageDetail\":\"\",\"PaymentLink\":\"\",\"TransId\":\"12345\",\"status\":\"Success\"}}",
              GlobalAssureLandmarkResponse::class.java)

    fun tempAdded(){


         var globalAssureResult :GlobalAssureResult  = getTempJson().GlobalAssureResult
        var CertificateNo = globalAssureResult.CertificateNo
        var CertificateFile = "http://newuatrsa.globalassure.com:8011/Temp/PDF/HA2905210000000006.pdf"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(CertificateFile))
        startActivity(browserIntent)

        downloadPdf(CertificateFile, "Elite_GlobalAssureDocs"+ Utility.currentDateTime())



        authenticationController.insertGlobalAssure(

            loginEntity!!.mobile,
            loginEntity!!.vehicleno,
            CertificateNo,
            CertificateFile,
            this@RoadSideAssistActivity

        )


    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.btnPolicy -> {


                showDialog("Please wait..")

                loginEntity.let {
                    authenticationController.getGlobalAssure(
                        it!!.mobile.toString(),
                        this@RoadSideAssistActivity)


                }


            }
        }
    }

    // Todo :Note   :1> we check first data is in our db  if present dn show pdf () : getGlobalAssure
    //               2> if not in our db than hit from Landmark db and its response we have to save in our db : getLandmarkEliteGlobalAssure
    //              3> hence Landmark response data we have to Insert Api for our db called  :insertGlobalAssure


    override fun OnSuccess(apiResponse: APIResponse, message: String) {



        if(apiResponse is GlobalAssureResponse) {

            cancelDialog()

            if(apiResponse.status_code == 0){

                var gloabalAssureEntity : GloabalAssureEntity = apiResponse.Data.get(0)

                getPdfFile(gloabalAssureEntity)

            }
            else{

                showDialog("Loading Pdf..")
                loginEntity.let {

                    authenticationController.getLandmarkEliteGlobalAssure(
                        it!!.mobile.toString(),
                        it!!.vehicleno,
                        this@RoadSideAssistActivity
                    )

                }
            }
        }

        else if(apiResponse is GlobalAssureLandmarkResponse){

            if(apiResponse.GlobalAssureResult.CertificateFile != null){

                var globalAssureResult : GlobalAssureResult = apiResponse.GlobalAssureResult;

                globalAssureResult.CertificateFile.let {

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(browserIntent)

                    downloadPdf(it, "Elite_GlobalAssureDocs"+ Utility.currentDateTime())

                }


                authenticationController.insertGlobalAssure(

                    loginEntity!!.mobile,
                    loginEntity!!.vehicleno,
                    globalAssureResult.CertificateNo,
                    globalAssureResult.CertificateFile,
                    this@RoadSideAssistActivity

                )

            }else{

                // temp 05
               // tempAdded()
                 cancelDialog()
                showAlert("Pdf Not Found.\nContact Landmark Admin")
            }


        }

        else  if (apiResponse is CommonResponse) {
            // :insertGlobalAssure : Result after Insert Response of local db.
            cancelDialog()
            // getCustomToast(" Insertde in Our db")

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