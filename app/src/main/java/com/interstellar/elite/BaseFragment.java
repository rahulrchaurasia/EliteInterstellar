package com.interstellar.elite;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.core.model.DocProductEnity;
import com.interstellar.elite.core.model.miscNonRto.ProvideClaimAssEntity;
import com.interstellar.elite.document.DocUploadActivity;
import com.interstellar.elite.product.ProductDocAdapter;
import com.interstellar.elite.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rohit on 17/01/16.
 */
public class BaseFragment extends Fragment {

    ProgressDialog dialog;

   CustomPopUpListener customPopUpListener;
    public BaseFragment() {

    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    protected void showDialog() {
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(getActivity(), "", msg, true);
    }

    public static boolean isValidePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        return !(phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern));
    }

    public static boolean isValideEmailID(EditText editText) {
        String emailEntered = editText.getText().toString().trim();
        return !(emailEntered.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches());
    }

    public static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return !(text.isEmpty());
    }

    public static boolean validatePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        if (phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern)) {
            return false;
        }
        return true;
    }

    public static boolean isValidPan(String Pan) {
//        String rx = "/[A-Z]{5}[0-9]{4}[A-Z]{1}$/";
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(Pan);
        if (matcher.matches()) {
            return true;

        } else {
            return false;
        }
    }

    public File createFile(String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".jpg";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);

        return outFile;
    }

    public File saveImageToStorage(Bitmap bitmap, String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".jpg";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public void openPopUp(final View view, String title, String desc, String positiveButtonName, String negativeButtonName, boolean isNegativeVisible, boolean isCancelable) {
        try {
            final Dialog dialog;
            dialog = new Dialog(this.getActivity(), R.style.CustomDialog);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_common_popup);

            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            tvOk.setText(positiveButtonName);

            TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
            tvCancel.setText(negativeButtonName);
            if (isNegativeVisible) {
                tvCancel.setVisibility(View.VISIBLE);
            } else {
                tvCancel.setVisibility(View.GONE);
            }

            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
            txtMessage.setText(desc);
            ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);

            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;  // Width
            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onPositiveButtonClick(dialog, view);
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onCancelButtonClick(dialog, view);
                }
            });
            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onCancelButtonClick(dialog, view);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // region CustomPopup

    public interface CustomPopUpListener {

        void onPositiveButtonClick(Dialog dialog, View view);

        void onCancelButtonClick(Dialog dialog, View view);

    }

    public void registerCustomPopUp(CustomPopUpListener customPopUpListener) {
        if (customPopUpListener != null)
            this.customPopUpListener = customPopUpListener;
    }

    //endregion


    public void getSnakeBar(View view ,String strMessage){


        // create an instance of the snackbar
        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // inflate the custom_snackbar_view created previously
        View customSnackView = getLayoutInflater().inflate(R.layout.layout_custom_snackbar, null);

        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

        // set the background of the default snackbar as transparent
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        // now change the layout of the snackbar
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        // set padding of the all corners as 0
        snackbarLayout.setPadding(0, 0, 0, 0);

        // register the button from the custom_snackbar_view layout file

        TextView txtMessage = customSnackView.findViewById(R.id.txtMessage);
        txtMessage.setText( ""+strMessage);

        TextView txtAction = customSnackView.findViewById(R.id.txtAction);

        // now handle the same button with onClickListener
        txtAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                snackbar.dismiss();
            }
        });

        // add the custom snack bar layout to snackbar layout
        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();

    }


//    fun getSnakeBar(view: View, strMessage: String) {
//
//        // inflate the custom_snackbar_view created previously
//        // inflate the custom_snackbar_view created previously
//        val snackbar = Snackbar
//                .make(view!!, "", Snackbar.LENGTH_INDEFINITE)
//            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
//
//        val customSnackView: View = layoutInflater.inflate(R.layout.layout_custom_snackbar, null)
//
//        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
//
//
//        val snackbarLayout :Snackbar.SnackbarLayout = snackbar.getView() as Snackbar.SnackbarLayout
//        snackbarLayout.setPadding(0,0,0,0)
//        snackbarLayout.addView(customSnackView)
//
//        val txtMessage : TextView = customSnackView.findViewById(R.id.txtMessage)
//        txtMessage.setText(strMessage)
//
//        val txtAction : TextView = customSnackView.findViewById(R.id.txtAction)
//
//        txtAction.setOnClickListener{
//
//            showAlert("Data")
//        }
//
//        snackbar.show()
//
//
////        val snackbar = Snackbar
////            .make(view!!, strMessage!!, Snackbar.LENGTH_INDEFINITE)
////            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
////
////        snackbar.setAction("ok", View.OnClickListener { v ->
////            snackbar.dismiss()
////        })
////        snackbar.show()
//    }








    public void getCustomToast(String strMessage) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            Toast.makeText(getActivity(),  HtmlCompat.fromHtml("<font color='#000000' ><b>" + strMessage + "</b></font>",  HtmlCompat.FROM_HTML_MODE_LEGACY
            ), Toast.LENGTH_LONG).show();


        }else {


            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.layout_custom_toast,
                    (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));


            TextView text = (TextView) layout.findViewById(R.id.txtMessage);
            text.setText("" + strMessage);

            Toast toast = new Toast(getActivity().getApplicationContext());
            // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }

    public void reqDocPopUp(List<DocProductEnity> lstDoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);

        RecyclerView rvProductDoc;
        ProductDocAdapter mAdapter = new ProductDocAdapter(getActivity(), lstDoc);
        Button btnClose;
        ImageView ivClose;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_doc_prod, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
        rvProductDoc = (RecyclerView) dialogView.findViewById(R.id.rvProductDoc);
        rvProductDoc.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProductDoc.setLayoutManager(layoutManager);
        rvProductDoc.setAdapter(mAdapter);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }




    public void showMiscPaymentAlert(final View view, String strhdr, final ProvideClaimAssEntity provideClaimAssEntity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);


        Button btnClose;
        TextView txtHdr ,txtMessage;
        LinearLayout lyReceipt;


        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_success_message, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyReceipt =    dialogView.findViewById(R.id.lyReceipt);
        btnClose =  dialogView.findViewById(R.id.btnClose);
        txtMessage =  dialogView.findViewById(R.id.txtMessage);
        txtHdr = dialogView.findViewById(R.id.txtHdr);

        txtHdr.setText(""+ strhdr);
        txtMessage.setText("" + provideClaimAssEntity.getDisplaymessage());

//        lyReceipt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(provideClaimAssEntity.getReceipt() != null)
//                {
//                   // new DownloadFromUrl(provideClaimAssEntity.getReceipt(), "EliteReceipt").execute();
//                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(provideClaimAssEntity.getReceipt()));
//                    startActivity(intent);
//                }
//            }
//        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(requireActivity(), DocUploadActivity.class)
                        .putExtra("ORDER_ID", provideClaimAssEntity.getId()));

                getActivity().finish();


            }
        });


        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }


    public void DownloadFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showPdf(File file) {
        try {
//            Uri selectedUri = FileProvider.getUriForFile(this,
//                    this.getString(R.string.file_provider_authority),
//                    new File(Environment.getExternalStorageDirectory() + "/MTC Report/" + FileName + ".pdf"));

            Uri selectedUri = FileProvider.getUriForFile(getActivity(),
                    this.getString(R.string.file_provider_authority), file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(selectedUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class DownloadFromUrl extends AsyncTask<Void, Void, Void> {


        File dir;
            File outFile;
        String fileURL;
        String fileName;

        public DownloadFromUrl(String imgURL, String tempfileName) {
            showDialog();
            fileURL = imgURL;
            dir = Utility.createDirIfNotExists();
            fileName = tempfileName + ".pdf";
            fileName = fileName.replaceAll("\\s+", "");

        }


        @Override
        protected Void doInBackground(Void... voids) {

            outFile = new File(dir, fileName);

            try {
                outFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            DownloadFile(fileURL, outFile);
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            cancelDialog();
            showPdf(outFile);
        }
    }


    public static boolean isValidPan(EditText editText) {
        String panNo = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        String panNoAlter = editText.getText().toString().toUpperCase();
        return !(panNoAlter.isEmpty() || !panNoAlter.matches(panNo));
    }


    public boolean validateMake (AutoCompleteTextView acMake , boolean IsMakeValid)
    {

        if (!isEmpty(acMake)){
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        } else if (IsMakeValid == false) {
            acMake.requestFocus();
            acMake.setError("Invalid Make");
            return false;

        }
        else{
            return true;
        }
    }

    public boolean validateInsCompName (EditText etInsComName,TextInputLayout textInputLayout)
    {

        if (!isEmpty(etInsComName)){
            etInsComName.requestFocus();
            textInputLayout.setError("Enter Insurer Company Name");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateProposerName (EditText etName,TextInputLayout textInputLayout)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            textInputLayout.setError("Enter Proposer Name");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateNominee (EditText etName,TextInputLayout textInputLayout)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            textInputLayout.setError("Enter Nominee Name");
            return false;
        }else{
            return  true;
        }
    }
    public boolean validateNomineeRel (EditText etName ,TextInputLayout textInputLayout)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            textInputLayout.setError("Enter Relation with Nominee");
            return false;
        }else{
            return  true;
        }
    }
    public boolean validateCity (EditText etCity,TextInputLayout textInputLayout)
    {

        if (!isEmpty(etCity)){
            etCity.requestFocus();
            textInputLayout.setError("Enter City");
        return false;
        }else{
            return  true;
        }
    }

    public boolean validateRTO (EditText etRTO, TextInputLayout textInputLayout)
    {

        if (!isEmpty(etRTO)){
            etRTO.requestFocus();
            textInputLayout.setError("Enter  RTO");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validatePinCode (EditText etPincode,TextInputLayout textInputLayout) {
        if (!isEmpty(etPincode) || etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            textInputLayout.setError("Enter Pincode");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateVehicle (EditText etVehicle)
    {
        if (!isEmpty(etVehicle)) {
            etVehicle.requestFocus();
            etVehicle.setError("Enter Vehicle Number");
            return false;
        } else{
            return  true;
        }
    }
    public boolean validateProposer (EditText etNameOfProposer, TextInputLayout textInputLayout)
    {
        if (!isEmpty(etNameOfProposer)) {

            textInputLayout.setError("Enter Policy Holder Name");
            return false;
        } else{
            return  true;
        }
    }

    public boolean validateSumAssured (EditText etSumAssured,TextInputLayout textInputLayout)
    {
        if (!isEmpty(etSumAssured)) {

            textInputLayout.setError("Enter Sum Insured");
            return false;
        } else{
            return  true;
        }
    }

    public TextWatcher getTextWatcher(final TextInputLayout textInputLayout) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if(charSequence.toString().length() >0){
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

}
