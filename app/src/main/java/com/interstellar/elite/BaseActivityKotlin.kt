

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.Html
import android.util.Patterns
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.interstellar.elite.BaseFragment
import com.interstellar.elite.R
import com.interstellar.elite.utility.Constants
import com.interstellar.elite.utility.Utility
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.regex.Pattern


open class BaseActivityKotlin : AppCompatActivity() {

    lateinit var dialog: AlertDialog
    lateinit var dialogView: View
    // lateinit var applicationPersistance: ApplicationPersistance

    val DISCONNECT_TIMEOUT: Long = 300000 // 5 min = 5 * 60 * 1000 ms


    var popUpListener: PopUpListener? = null
    var customPopUpListener: CustomPopUpListener? = null

    var alertDocDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
    }

    open fun isValidPan(editText: EditText): Boolean {
        val panNo = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
        val panNoAlter = editText.text.toString().toUpperCase()
        return !(panNoAlter.isEmpty() || !panNoAlter.matches(panNo.toRegex()))
    }

    open fun callConfirm(context: Context) {
        try {
            val builder = AlertDialog.Builder(context)

            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.layout_call_dialog, null)

            val txtCall = dialogView.findViewById<TextView>(R.id.txtCall)
            val txtCancel = dialogView.findViewById<TextView>(R.id.txtCancel)

            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            txtCall.setOnClickListener {
                dialog.dismiss()
                val intent = Intent().apply {
                    action = Intent.ACTION_DIAL
                    data = Uri.parse("tel:1800313205205")
                }
                startActivity(intent)

            }

            txtCancel.setOnClickListener {
                dialog.dismiss()
            }

        } catch (ex: Exception) {
            Toast.makeText(context, "Please try again..", Toast.LENGTH_SHORT).show()
        }

    }


    fun getSnakeBar(view: View, strMessage: String) {

        // inflate the custom_snackbar_view created previously
        // inflate the custom_snackbar_view created previously
        val snackbar = Snackbar
            .make(view!!, "", Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)

        val customSnackView: View = layoutInflater.inflate(R.layout.layout_custom_snackbar, null)

        snackbar.view.setBackgroundColor(Color.TRANSPARENT)


        val snackbarLayout :Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
        snackbarLayout.setPadding(0,0,0,0)
        snackbarLayout.addView(customSnackView)

        val txtMessage : TextView = customSnackView.findViewById(R.id.txtMessage)
        txtMessage.setText(strMessage)

        val txtAction : TextView = customSnackView.findViewById(R.id.txtAction)

        txtAction.setOnClickListener{

            showAlert("Data")
        }

        snackbar.show()


//        val snackbar = Snackbar
//            .make(view!!, strMessage!!, Snackbar.LENGTH_INDEFINITE)
//            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
//
//        snackbar.setAction("ok", View.OnClickListener { v ->
//            snackbar.dismiss()
//        })
//        snackbar.show()
    }

    protected fun showMessage(
        view: View,
        message: String,
        action: String,
        onClickListener: View.OnClickListener?
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(action, onClickListener).show()
    }

    open fun isValidePhoneNumber(phone: String): Boolean {
        var check = false
        val phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"
       check = if (!Pattern.matches("[a-zA-Z]+", phone)) {
      //  check = if (!Pattern.matches(phoneNumberPattern, phone)) {
            if (phone.length < 6 || phone.length > 13) {
                false
            } else {
                true
            }
        } else {
            false
        }
        return check
    }

    fun isValideEmailID(editText: EditText): Boolean {
        val emailEntered = editText.text.toString().trim { it <= ' ' }
        return !(emailEntered.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches())
    }

    //region Progress Dialog

    private fun initDialog() {
        val builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
    }

    fun showLoading(message: CharSequence) {
        val msg = dialogView.findViewById<TextView>(R.id.txtProgressTitle)
        msg.text = message
        dialog.show()
    }

    fun dismissDialog() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    //endregion

    //region session five min


//    private val disconnectHandler = Handler(object : Handler.Callback {
//        override fun handleMessage(msg: Message): Boolean {
//            return true
//        }
//    })


    open fun validatePinCode(etPincode: EditText): Boolean {
        return if (!BaseFragment.isEmpty(etPincode) || etPincode.text.toString().length != 6) {
            etPincode.requestFocus()
            etPincode.error = "Enter Pincode"
            false
        } else {
            true
        }
    }

//    private val disconnectCallback = Runnable {
//        kotlin.run {
//            sessionExpire()
//        }
//    }

//    open fun resetDisconnectTimer() {
//        disconnectHandler.removeCallbacks(disconnectCallback);
//        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
//    }
//
//    open fun stopDisconnectTimer() {
//        disconnectHandler.removeCallbacks(disconnectCallback);
//    }

    override fun onUserInteraction() {
        // resetDisconnectTimer()
    }

    override fun onResume() {
        super.onResume()
        //resetDisconnectTimer()
    }

    override fun onStop() {
        super.onStop()
        //stopDisconnectTimer()
    }
    //endregion

    protected fun hideKeyBoard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

//    open fun sessionExpire() {
//        try {
//            val builder = AlertDialog.Builder(this@BaseActivity)
//            //set title
//            builder.setTitle("Session Expired.!")
//
//            //set message
//            builder.setMessage("Your session has been timed out. Please Log-In Again.")
//
//
//            val positiveText = "LOG-IN"
//
//            builder.setPositiveButton(
//                positiveText
//            ) { dialog, which ->
//                val intent = Intent(this, LoginActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intent)
//            }
//
//            val dialog = builder.create()
//            dialog.setCancelable(false)
//            dialog.setCanceledOnTouchOutside(false)
//            dialog.show()
//        } catch (ex: Exception) {
//            Toast.makeText(this, "Please try again..", Toast.LENGTH_SHORT).show()
//        }
//
//    }

    fun callConfirm(Title: String, strBody: String, strMobile: String) {
        try {
            val builder = AlertDialog.Builder(this@BaseActivityKotlin)
            builder.setTitle(Title)

            builder.setMessage(strBody)
            val positiveText = "Call"
            val NegativeText = "Cancel"
            builder.setPositiveButton(
                positiveText
            ) { dialog, which ->
                val intentCalling = Intent(Intent.ACTION_DIAL)
                intentCalling.data = Uri.parse("tel:$strMobile")
                startActivity(intentCalling)
            }

            builder.setNegativeButton(NegativeText,
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                    }
                })
            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } catch (ex: Exception) {
            Toast.makeText(this, "Please try again..", Toast.LENGTH_SHORT).show()
        }

    }




    //////////////

//    open fun isValidePhoneNumber(editText: EditText): Boolean {
//        val phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"
//        val phoneNumberEntered = editText.text.toString().trim { it <= ' ' }
//        return !(phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern))
//    }


    open fun isEmpty(editText: EditText): Boolean {
        val text = editText.text.toString().trim { it <= ' ' }
        return !text.isEmpty()
    }

    open fun createFile(name: String): File? {
        val outStream: FileOutputStream? = null
        val dir: File = Utility.createDirIfNotExists()
        var fileName = "$name.jpg"
        fileName = fileName.replace("\\s+".toRegex(), "")
        return File(dir, fileName)
    }

    open fun saveImageToStorage(bitmap: Bitmap, name: String): File? {
        var outStream: FileOutputStream? = null
        val dir: File = Utility.createDirIfNotExists()
        var fileName = "$name.jpg"
        fileName = fileName.replace("\\s+".toRegex(), "")
        val outFile = File(dir, fileName)
        try {
            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return outFile
    }

    open fun showAlert(strBody: String?) {
        try {
            val builder = AlertDialog.Builder(this@BaseActivityKotlin)
            builder.setTitle("Elite")
            builder.setMessage(strBody)
            val positiveText = "Ok"
            builder.setPositiveButton(
                positiveText
            ) { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this@BaseActivityKotlin, "Please try again..", Toast.LENGTH_SHORT).show()
        }
    }

    //region Default Alert
    open fun showAlertAction(view: View?, strBody: String?) {
        try {
            val builder = AlertDialog.Builder(this@BaseActivityKotlin)
            builder.setTitle("Elite")
            builder.setMessage(strBody)
            val positiveText = "Ok"
            builder.setPositiveButton(
                positiveText
            ) { dialog, which ->
                dialog.dismiss()
                if (popUpListener != null) {
                    popUpListener!!.onPositiveButtonClick(view)
                }
            }
            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this@BaseActivityKotlin, "Please try again..", Toast.LENGTH_SHORT).show()
        }
    }

    interface PopUpListener {
        fun onPositiveButtonClick(view: View?)
    }

    open fun registerPopUp(popUpListener: PopUpListener?) {
        if (popUpListener != null) {
            this.popUpListener = popUpListener
        }
    }

    //endregion

    //endregion


    open fun getCustomToast(strMessage: String) {





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val toast = Toast.makeText(
                applicationContext,
                HtmlCompat.fromHtml("<font color='#000000' ><b>" + strMessage + "</b></font>",  HtmlCompat.FROM_HTML_MODE_LEGACY
                ),Toast.LENGTH_LONG
            )

            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()



        }else {

            val layout = this.layoutInflater.inflate(
                R.layout.layout_custom_toast,
                findViewById(R.id.toast_layout_root)
            )
            val text = layout.findViewById<View>(R.id.txtMessage) as TextView
            text.text = "" + strMessage
            val toast = Toast(applicationContext)
            // use the application extension function
            toast.setGravity(Gravity.BOTTOM, 0, 200)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()

        }




    }

    open fun openPopUp(
        view: View?,
        title: String?,
        desc: String?,
        positiveButtonName: String?,
        negativeButtonName: String?,
        isNegativeVisible: Boolean,
        isCancelable: Boolean
    ) {
        try {
            val dialog: Dialog
            dialog = Dialog(this@BaseActivityKotlin, R.style.CustomDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_common_popup)
            val tvTitle = dialog.findViewById<View>(R.id.tvTitle) as TextView
            tvTitle.text = title
            val tvOk = dialog.findViewById<View>(R.id.tvOk) as TextView
            tvOk.text = positiveButtonName
            val tvCancel = dialog.findViewById<View>(R.id.tvCancel) as TextView
            tvCancel.text = negativeButtonName
            if (isNegativeVisible) {
                tvCancel.visibility = View.VISIBLE
            } else {
                tvCancel.visibility = View.GONE
            }
            val txtMessage = dialog.findViewById<View>(R.id.txtMessage) as TextView
            txtMessage.text = desc
            val ivCross = dialog.findViewById<View>(R.id.ivCross) as ImageView
            dialog.setCancelable(isCancelable)
            dialog.setCanceledOnTouchOutside(isCancelable)
            val dialogWindow = dialog.window
            val lp = dialogWindow!!.attributes
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT // Width
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // Height
            dialogWindow.attributes = lp
            dialog.show()
            tvOk.setOnClickListener { // Close dialog
                if (customPopUpListener != null) customPopUpListener!!.onPositiveButtonClick(
                    dialog,
                    view
                )
            }
            tvCancel.setOnClickListener { // Close dialog
                if (customPopUpListener != null) customPopUpListener!!.onCancelButtonClick(
                    dialog,
                    view
                )
            }
            ivCross.setOnClickListener { // Close dialog
                if (customPopUpListener != null) customPopUpListener!!.onCancelButtonClick(
                    dialog,
                    view
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    // region CustomPopup

    // region CustomPopup
    interface CustomPopUpListener {
        fun onPositiveButtonClick(dialog: Dialog?, view: View?)
        fun onCancelButtonClick(dialog: Dialog?, view: View?)
    }

    open fun registerCustomPopUp(customPopUpListener: CustomPopUpListener?) {

        if (customPopUpListener != null) {
            this.customPopUpListener = customPopUpListener
        }
    }

    //endregion

    //endregion
    open fun composeEmail(addresses: String, subject: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(addresses))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(Intent.createChooser(intent, "Email via..."))
    }

    //temp commented 05
//    open fun reqDocPopUp(lstDoc: List<DocProductEnity?>?) {
//        if (alertDocDialog != null && alertDocDialog.isShowing()) {
//            return
//        }
//        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
//        val rvProductDoc: RecyclerView
//        val mAdapter = ProductDocAdapter(this, lstDoc)
//        val btnClose: Button
//        val ivClose: ImageView
//        val inflater = this.layoutInflater
//        val dialogView: View = inflater.inflate(R.layout.layout_doc_prod, null)
//        builder.setView(dialogView)
//        alertDocDialog = builder.create()
//        // set the custom dialog components - text, image and button
//        btnClose = dialogView.findViewById<View>(R.id.btnClose) as Button
//        ivClose = dialogView.findViewById<View>(R.id.ivClose) as ImageView
//        rvProductDoc = dialogView.findViewById<View>(R.id.rvProductDoc) as RecyclerView
//        rvProductDoc.setHasFixedSize(true)
//        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
//        rvProductDoc.layoutManager = layoutManager
//        rvProductDoc.adapter = mAdapter
//        btnClose.setOnClickListener { alertDocDialog.dismiss() }
//        ivClose.setOnClickListener { alertDocDialog.dismiss() }
//        alertDocDialog.setCancelable(false)
//        alertDocDialog.show()
//        //  alertDialog.getWindow().setLayout(900, 600);
//    }



    open fun DownloadFile(fileURL: String?, directory: File?) {
        try {
            val f = FileOutputStream(directory)
            val u = URL(fileURL)
            val c = u.openConnection() as HttpURLConnection
            c.requestMethod = "GET"
            c.doOutput = true
            c.connect()
            val `in` = c.inputStream
            val buffer = ByteArray(1024)
            var len1 = 0
            while (`in`.read(buffer).also { len1 = it } > 0) {
                f.write(buffer, 0, len1)
            }
            f.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun getExecutor(imgURL: String, tempfileName: String) {

        var dir: File? = null
        var outFile: File? = null
        var fileURL: String
        var fileName: String

        showLoading("Please wait...")
        fileURL = imgURL
        dir = Utility.createDirIfNotExists()
        fileName = "$tempfileName.pdf"
        fileName = fileName.replace("\\s+".toRegex(), "")
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            // Do backbround Work here

            outFile = File(dir, fileName)

            try {
                outFile!!.createNewFile()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
            DownloadFile(fileURL, outFile)
        }
        handler.post {
            dismissDialog()
            showPdf(outFile)
        }
    }

    open fun showPdf(file: File?) {
        try {
//            Uri selectedUri = FileProvider.getUriForFile(this,
//                    this.getString(R.string.file_provider_authority),
//                    new File(Environment.getExternalStorageDirectory() + "/MTC Report/" + FileName + ".pdf"));
            val selectedUri = FileProvider.getUriForFile(
                this,
                this.getString(R.string.file_provider_authority), file!!
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(selectedUri, "application/pdf")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            this.startActivity(intent)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //temp commented 05
//     class DownloadFromUrl(imgURL: String, tempfileName: String) :
//        AsyncTask<Void?, Void?, Void?>() {
//        var dir: File
//        var outFile: File? = null
//        var fileURL: String
//        var fileName: String
//        protected override fun doInBackground(vararg voids: Void): Void? {
//            outFile = File(dir, fileName)
//            try {
//                outFile!!.createNewFile()
//            } catch (e1: IOException) {
//                e1.printStackTrace()
//            }
//            DownloadFile(fileURL, outFile)
//            return null
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        override fun onPostExecute(aVoid: Void?) {
//            cancelDialog()
//            showPdf(outFile)
//        }
//
//        init {
//            showDialog()
//            fileURL = imgURL
//            dir = Utility.createDirIfNotExists()
//            fileName = "$tempfileName.pdf"
//            fileName = fileName.replace("\\s+".toRegex(), "")
//        }
//    }


    open fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING)
    }
}