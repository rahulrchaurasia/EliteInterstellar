package com.interstellar.elite.secure

import ServiceName
import ServiceRequest
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
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
import com.interstellar.elite.core.model.TataEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.CommonResponse
import com.interstellar.elite.core.response.TataLandmarkResponse
import com.interstellar.elite.core.response.TataResponse
import com.interstellar.elite.databinding.ActivityWalletBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.utility.Utility


class WalletActivity : BaseActivity() , View.OnClickListener , IResponseSubcriber {

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

    lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_wallet)

        binding = ActivityWalletBinding.inflate(layoutInflater)
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


    fun getTempJson(): TataLandmarkResponse  =

                 Gson().fromJson(
                         "{\"EliteTataPeepResult\":{\"EliteTataPeepdetails\":[{\"MobileNo\":\"9833797088\",\"RegistrationNo\":\"MH01CP4237\",\"PolicyLink\":\"\"}],\"message\":\"\",\"status\":\"Success\",\"status_code\":0}}", TataLandmarkResponse::class.java)




    fun tempAdded(){
        var tataEntity: TataEntity = getTempJson().EliteTataPeepResult.EliteTataPeepdetails.get(0)

        var PolicyLink = "http://elite.landmarkinsurance.in/TataPeepDocs/Elite_50MH.pdf"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(PolicyLink))
        startActivity(browserIntent)

        downloadPdf(PolicyLink, "Elite_TataPeepDocs"+Utility.currentDateTime())

        tataEntity.let {

            authenticationController.insertTataPeep(
                it!!.MobileNo.toString(),
                it!!.RegistrationNo,
                "http://elite.landmarkinsurance.in/TataPeepDocs/Elite_50MH.pdf",
                this@WalletActivity
            )

        }

    }



    private fun setListner(){

        binding.includedWallet.btnPolicy.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.btnPolicy -> {


                showDialog("Please wait..")

                loginEntity.let {
                    authenticationController.getTataPeep(
                            it!!.mobile.toString(),
                            this@WalletActivity
                    )

                }


            }
        }

    }


    // Todo :Note   :1> we check first data is in our db  if present dn show pdf () : getTataPeep
    //               2> if not in our db than hit from Landmark db and its response we have to save in our db : getLandmarkEliteTataPee
    //              3> hence Landmark response data we have to Insert Api for our db called  :insertTataPeep

    override fun OnSuccess(apiResponse: APIResponse, message: String) {


        if (apiResponse is TataResponse) {
            // getTataPeep : Tata local Display Response

            cancelDialog()
            if (apiResponse.status_code == 0) {

                var tataEntity: TataEntity = apiResponse.Data.get(0)
               // getExecutor(tataEntity.PolicyLink, "EliteWallet")

                tataEntity.PolicyLink.let {

//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                    startActivity(browserIntent)

                    downloadPdf(tataEntity.PolicyLink, "Elite_TataPeepDocs"+Utility.currentDateTime())
                }


            }
            else{

                 showDialog("Loading Pdf..")
                loginEntity.let {

                    authenticationController.getLandmarkEliteTataPee(
                            it!!.mobile.toString(),
                            it!!.vehicleno,
                            this@WalletActivity
                    )

                }
            }

        }

        else if (apiResponse is TataLandmarkResponse) {


          //   getLandmarkEliteTataPee :  hit from Landmark db
            if (apiResponse.EliteTataPeepResult.EliteTataPeepdetails != null) {

               var tataEntity: TataEntity = apiResponse.EliteTataPeepResult.EliteTataPeepdetails.get(0)


                if(tataEntity.PolicyLink != null && tataEntity.PolicyLink  != "") {
                    tataEntity.PolicyLink.let {

//                        val browserIntent =
//                            Intent(Intent.ACTION_VIEW, Uri.parse(tataEntity.PolicyLink))
//                        startActivity(browserIntent)

                        downloadPdf(
                            tataEntity.PolicyLink,
                            "Elite_TataPeepDocs" + Utility.currentDateTime()
                        )
                    }

                    //Todo : insert in our db when got success from landmark url

                    // showLoading("Loading Pdf..")
                    tataEntity.let {

                        authenticationController.insertTataPeep(
                           MobileNo =  it!!.MobileNo.toString(),
                           RegistrationNo =  it!!.RegistrationNo,
                           PolicyLink  =    it!!.PolicyLink ,
                            this@WalletActivity
                        )

                    }
                }else{
                    showAlert("Pdf Not Found.\nContact Landmark Admin")
                }

            }

            //Todo commented

            else{

                   // temp data added

               // tempAdded()
                cancelDialog()
                showAlert("Pdf Not Found.\nContact Landmark Admin")

            }


        }

        else  if (apiResponse is CommonResponse) {
               // :insertTataPeep : Result after Insert Response of local db.
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