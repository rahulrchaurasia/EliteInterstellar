package com.interstellar.elite;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.interstellar.elite.core.model.DocProductEnity;
import com.interstellar.elite.product.ProductDocAdapter;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



/**
 * Created by Rohit on 12/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    //public Realm realm;
    ProgressDialog dialog;
    PopUpListener popUpListener;
    CustomPopUpListener customPopUpListener;

    AlertDialog alertDocDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Realm
        //Realm.init(this);
        // Get a Realm instance for this thread
        // realm = Realm.getDefaultInstance();
    }

//    @Override
//    protected void attachBaseContext(Context context) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected void showDialog() {
//        dialog = ProgressDialog.show(BaseActivity.this, "", "Loading...", true);
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        if (dialog == null)
            dialog = ProgressDialog.show(BaseActivity.this, "", msg, true);
        else {
            if (!dialog.isShowing())
                dialog = ProgressDialog.show(BaseActivity.this, "", msg, true);
        }
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

    public File createFile(String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".jpg";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);

        return outFile;
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

    public void showAlert(String strBody) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle("Elite");

            builder.setMessage(strBody);
            String positiveText = "Ok";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(BaseActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }

    //region Default Alert
    public void showAlertAction(final View view, String strBody) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle("Elite");

            builder.setMessage(strBody);
            String positiveText = "Ok";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (popUpListener != null) {

                                popUpListener.onPositiveButtonClick(view);
                            }

                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(BaseActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }

    public interface PopUpListener {

        void onPositiveButtonClick(View view);

    }

    public void registerPopUp(PopUpListener popUpListener) {
        if (popUpListener != null)
            this.popUpListener = popUpListener;
    }

    //endregion

    public void getCustomToast(String strMessage) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            Toast.makeText(getApplicationContext(),  HtmlCompat.fromHtml("<font color='#000000' ><b>" + strMessage + "</b></font>",  HtmlCompat.FROM_HTML_MODE_LEGACY
            ), Toast.LENGTH_LONG).show();

        }else {

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.layout_custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout_root));


            TextView text = (TextView) layout.findViewById(R.id.txtMessage);
            text.setText("" + strMessage);

            Toast toast = new Toast(getApplicationContext());
            // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }


    public void getSnakeBar1(View view, String strMessage){


        Snackbar snackbar = Snackbar
                .make(view, strMessage, Snackbar.LENGTH_LONG)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

               snackbar.setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      snackbar.dismiss();
                    }
                });

        snackbar.show();
    }

    public void getSnakeBar(View view ,String strMessage){


        // create an instance of the snackbar
        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // inflate the custom_snackbar_view created previously
        View customSnackView = getLayoutInflater().inflate(R.layout.layout_custom_snackbar, null);

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


    public void openPopUp(final View view, String title, String desc, String positiveButtonName, String negativeButtonName, boolean isNegativeVisible, boolean isCancelable) {
        try {
            final Dialog dialog;
            dialog = new Dialog(BaseActivity.this, R.style.CustomDialog);

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

    public void composeEmail(String addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{addresses});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        startActivity(Intent.createChooser(intent, "Email via..."));
    }

    public void reqDocPopUp(List<DocProductEnity> lstDoc) {

        if (alertDocDialog != null && alertDocDialog.isShowing()) {

            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        RecyclerView rvProductDoc;
        ProductDocAdapter mAdapter = new ProductDocAdapter(this, lstDoc);
        Button btnClose;
        ImageView ivClose;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_doc_prod, null);


        builder.setView(dialogView);
        alertDocDialog  = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
        rvProductDoc = (RecyclerView) dialogView.findViewById(R.id.rvProductDoc);
        rvProductDoc.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProductDoc.setLayoutManager(layoutManager);
        rvProductDoc.setAdapter(mAdapter);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDocDialog.dismiss();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDocDialog.dismiss();

            }
        });

        alertDocDialog.setCancelable(false);

        alertDocDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }


//    public void DownloadFromUrl(String fileURL, String fileName) {
//        File dir = Utility.createDirIfNotExists();
//
//        fileName = fileName + ".pdf";
//        fileName = fileName.replaceAll("\\s+", "");
//
//        File outFile = new File(dir, fileName);
//
//        try {
//            outFile.createNewFile();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        DownloadFile(fileURL, outFile);
//
//    }

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

            Uri selectedUri = FileProvider.getUriForFile(this,
                    this.getString(R.string.file_provider_authority), file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(selectedUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getExecutor(String imgURL, String tempfileName){

        showDialog();
        File dir;
        File outFile;
        String fileURL;
        String fileName;

        fileURL = imgURL;
        dir = Utility.createDirIfNotExists();
        fileName = tempfileName + ".pdf";
        fileName = fileName.replaceAll("\\s+", "");

        outFile = new File(dir, fileName);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler =  new Handler(Looper.getMainLooper());


        executor.execute(new Runnable() {
            @Override
            public void run() {



                try {
                    outFile.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                DownloadFile(fileURL, outFile);
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {

                cancelDialog();
                showPdf(outFile);
            }
        });


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


    public void openAppSetting()
    {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);
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


