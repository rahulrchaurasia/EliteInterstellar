package com.interstellar.elite.secure

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.interstellar.elite.BaseActivity
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.databinding.ActivityRoadSideAssistBinding
import com.interstellar.elite.facade.PrefManager

class RoadSideAssistActivity : BaseActivity() , View.OnClickListener , IResponseSubcriber {


    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

    lateinit var binding: ActivityRoadSideAssistBinding
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_road_side_assist)

       binding = ActivityRoadSideAssistBinding.inflate(layoutInflater)
       supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       supportActionBar!!.setDisplayShowHomeEnabled(true)
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
    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.btnPolicy -> {


                showDialog("Please wait..")

                loginEntity.let {
                    authenticationController.getTataPeep(
                        it!!.mobile.toString(),
                        this@RoadSideAssistActivity)


                }


            }
        }
    }

    override fun OnSuccess(apiResponse: APIResponse, message: String) {
        TODO("Not yet implemented")
    }

    override fun OnFailure(error: String) {
        TODO("Not yet implemented")
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