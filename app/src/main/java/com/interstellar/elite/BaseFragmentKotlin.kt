
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.interstellar.elite.BaseFragment
import com.interstellar.elite.R


open class BaseFragmentKotlin : Fragment() {

    lateinit var dialog: AlertDialog
    lateinit var dialogView: View

    // var applicationPersistance: ApplicationPersistance? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog(requireActivity())
        //     applicationPersistance = ApplicationPersistance(activity!!)
//        if (applicationPersistance != null)
//            applicationPersistance!!.getUID()
//        else
//            showMessage(dialogView, "Session Expired", "", null)

    }


    //region Progress Dialog

    private fun initDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
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

    open fun validatePinCode(etPincode: EditText): Boolean {
        return if (!BaseFragment.isEmpty(etPincode) && etPincode.text.toString().length != 6) {
            etPincode.requestFocus()
            etPincode.error = "Enter Pincode"
            false
        } else {
            true
        }
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

    open fun isEmpty(editText: EditText): Boolean {
        val text = editText.text.toString().trim()
        return !text.isEmpty()
    }

    protected fun hideKeyBoard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun dialogSessionExpire(mContext: Context) {

        val builder = AlertDialog.Builder(mContext)

        //set title
        builder.setTitle("Session Expired.!")

        //set message
        builder.setMessage("Your session has been timed out. Please Log-In Again.")

        //set cancelable false
        builder.setCancelable(false)


        builder.setPositiveButton("LOG-IN", DialogInterface.OnClickListener { dialog, which ->

            //redirect to Login
            //  (mContext as HomeActivity).redirectLogin()
        })

        val alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireActivity()!!,
                R.color.hyperlink_color))
    }

    open fun callConfirm(context: Context) {
        try {
            val builder = AlertDialog.Builder(context)

            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.layout_call_dialog, null)

            val txtCall = dialogView.findViewById<TextView>(R.id.txtCall)
            val txtCancel = dialogView.findViewById<TextView>(R.id.txtCancel)


            builder.setView(dialogView)

            //builder.setTitle("Call")

            //builder.setMessage("1800-000-2184")
//            val positiveText = "Call"
//            val NegativeText = "Cancel"
//            builder.setPositiveButton(
//                positiveText
//            ) { dialog, which ->
//                val intentCalling = Intent(Intent.ACTION_DIAL)
//                intentCalling.data = Uri.parse("tel:$18000002184")
//                startActivity(intentCalling)
//            }
//
//            builder.setNegativeButton(NegativeText,
//                object : DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface, which: Int) {
//                        dialog.dismiss()
//                    }
//                })
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
}