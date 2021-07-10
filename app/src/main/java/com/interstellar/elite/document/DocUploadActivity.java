package com.interstellar.elite.document;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.snackbar.Snackbar;
import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.model.DocumentViewEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.response.DocumentResponse;
import com.interstellar.elite.core.response.DocumentViewResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.home.HomeActivity;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.utility.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.MultipartBody;

import static android.os.Build.VERSION.SDK_INT;

public class DocUploadActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber, BaseActivity.CustomPopUpListener {


    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;

    HashMap<String, Integer> body;
    MultipartBody.Part part;
    File Docfile;
    File file;

    private Uri imageUri;
    private Uri cropImageUri;
    int OrderID;
    Button btnSubmit;

    private String DOC1 = "ELITE_DOC";

    ///////////

    UserEntity loginEntity;

    TextView txtDocVerify;
    RecyclerView rvProduct;
    LinearLayout lyParent;
    DocumentAdapter mAdapter;
    PrefManager prefManager;
    List<DocumentViewEntity> lstDoc;
    DocumentViewEntity documentViewEntity;
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerCustomPopUp(DocUploadActivity.this);
        if (getIntent().getExtras() != null) {
            OrderID = getIntent().getIntExtra("ORDER_ID", 0);

            if (OrderID != 0) {
                showDialog();
                new ProductController(this).getDocumentView(String.valueOf(OrderID), DocUploadActivity.this);
            }
        }

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        initialize();

        btnSubmit.setOnClickListener(this);

    }


    private void initialize() {

        lyParent = (LinearLayout) findViewById(R.id.lyParent);
        txtDocVerify = (TextView) findViewById(R.id.txtDocVerify);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);


        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DocUploadActivity.this);
        rvProduct.setLayoutManager(layoutManager);

    }

    /**
     * //TODO: Crop image activity to crop capture image.
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void setDocumentUpload(String urlPath) {


        if (documentViewEntity != null) {
            documentViewEntity.setPath(urlPath);

            mAdapter.updateList(documentViewEntity);
        }


    }


    private void viewUploadFile(String url) {

        if (url.equals("")) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DocUploadActivity.this, R.style.CustomDialog);

        // TouchImageView ivDocFile;
        ImageView ivDocFile,ivClose;
        Button btnClose;
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView;

        dialogView = inflater.inflate(R.layout.layout_view_doc, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
//        ivDocFile = (TouchImageView) dialogView.findViewById(R.id.ivDocFile);
        ivDocFile = (ImageView) dialogView.findViewById(R.id.ivDocFile);

        Glide.with(this)
                .load(url)

//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivDocFile);


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
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }

    private void launchCamera() {


        String FileName = "EliteDoc";

//
        Docfile = createFile(FileName);
        imageUri = FileProvider.getUriForFile(this,
                getString(R.string.file_provider_authority), Docfile);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void galleryCamPopUp() {

        if (!checkPermission()) {

            if (SDK_INT >= Build.VERSION_CODES.R) {

                if (!Environment.isExternalStorageManager()) {
                    requestStoragePermissionV11();
                }
                else{

                    checkRationalV11Above();

                }


            } else {
                checkRationale();
            }
        } else {

            showCamerGalleryPopUp();
        }
    }


    private void checkRationale(){
        if (checkRationalePermission()) {
            //Show Information about why you need the permission

            Snackbar.make(lyParent, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    requestPermission();
                }
            }).show();

        } else {
            openPopUp(rvProduct, "Need  Permission", "This app needs all permissions.", "GRANT", "DENNY", false, true);


        }
    }


    private void checkRationalV11Above(){

        if (checkCameraRationalePermissionV11()) {
            //Show Information about why you need the permission

            Snackbar.make(lyParent, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    requestCameraPermissionV11();
                }
            }).show();
        } else {
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
            //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
            openPopUp(rvProduct, "Need  Permission", "This app needs all permissions.", "GRANT", "DENNY", false, true);


        }
    }

    private void showCamerGalleryPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        LinearLayout lyCamera, lyGallery;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_cam_gallery, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyCamera = (LinearLayout) dialogView.findViewById(R.id.lyCamera);
        lyGallery = (LinearLayout) dialogView.findViewById(R.id.lyGallery);

        lyCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
                alertDialog.dismiss();

            }
        });

        lyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private boolean checkAllFileUploaded() {
        if (lstDoc.size() > 0) {
            for (int pos = 0; pos < lstDoc.size(); pos++) {
                if (lstDoc.get(pos).getPath().equalsIgnoreCase("")) {

                    return false;
                }
            }
        }
        return true;
    }

    private void setDocNote() {
        if (checkAllFileUploaded()) {
            txtDocVerify.setVisibility(View.GONE);
        } else {
            txtDocVerify.setVisibility(View.VISIBLE);

        }
    }

    public void ConfirmDocAlert(String Title, String strBody, final String strMobile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);


        Button btnSubmit;
        TextView txtTile, txtBody, txtMob;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_calling_popup, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
        txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
        txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);

        btnSubmit.setText("HOME");
        txtTile.setText(Title);
        txtBody.setText(strBody);
        txtMob.setText(strMobile);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                startActivity(new Intent(DocUploadActivity.this, HomeActivity.class));
                DocUploadActivity.this.finish();


            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof DocumentResponse) {
            if (response.getStatus_code() == 0) {

                setDocumentUpload(((DocumentResponse) response).getData().get(0).getPath());
                if (lstDoc != null && lstDoc.size() > 0) {

                    if (checkAllFileUploaded()) {

                        txtDocVerify.setVisibility(View.GONE);

                    } else {
                        txtDocVerify.setVisibility(View.VISIBLE);

                    }


                }

            }
        } else if (response instanceof DocumentViewResponse) {
            if (response.getStatus_code() == 0) {

                lstDoc = ((DocumentViewResponse) response).getData();
                mAdapter = new DocumentAdapter(DocUploadActivity.this, lstDoc);
                rvProduct.setAdapter(mAdapter);

                setDocNote();

            }
        }


    }


    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.PERMISSION_CAMMERA_STORAGE_V11_CONSTANT) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    //0005
                    if(!checkCameraPermissionV11()){

                       checkRationalV11Above();
                    }

                } else {
                    dialogStorage();
                }
            }
        }

        // Below For Cropping The Camera Image
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //extractTextFromImage();
            startCropImageActivity(imageUri);
        }
        // Below For Cropping The Gallery Image
        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            startCropImageActivity(selectedImageUri);
        }

        // Below  handle result of CropImageActivity

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                try {
                    cropImageUri = result.getUri();
                    Bitmap mphoto = null;
                    try {
                        mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cropImageUri);
                        mphoto = getResizedBitmap(mphoto, 800);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    showDialog();
                    file = saveImageToStorage(mphoto, DOC1);
                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(this, loginEntity.getUser_id(), documentViewEntity.getDoc_id(), OrderID);
                    new ProductController(this).uploadDocuments(part, body, this);

                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }


    public void getActionCamera(DocumentViewEntity entity) {
        documentViewEntity = entity;

        galleryCamPopUp();
    }


    public void getActionView(DocumentViewEntity entity) {
        documentViewEntity = entity;

        viewUploadFile(entity.getPath());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // region permission
    private boolean checkPermission() {


            int camera = ActivityCompat.checkSelfPermission(this, perms[0]);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            //Note : version  11 and above first we take only storage permission once it get than we ask for camera permission


            return Environment.isExternalStorageManager() && camera == PackageManager.PERMISSION_GRANTED;
        } else {
            int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(this, perms[1]);
            int READ_EXTERNAL = ActivityCompat.checkSelfPermission(this, perms[2]);


            return camera == PackageManager.PERMISSION_GRANTED
                    && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;

        }
    }

    //0005


    private boolean checkRationalePermission() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[2]);

        return camera || write_external || read_external;


    }


    private void requestStoragePermissionV11() {

                //    ActivityCompat.requestPermissions(this, new String[]{CAMERA}, Constants.PERMISSION_CAMERA_V11_CONSTANT);

                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, Constants.PERMISSION_CAMMERA_STORAGE_V11_CONSTANT);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, Constants.PERMISSION_CAMMERA_STORAGE_V11_CONSTANT);
                }


    }

    private boolean checkCameraPermissionV11(){


        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        return camera == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkCameraRationalePermissionV11() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, Manifest.permission.CAMERA);


        return camera ;


    }

    private void requestCameraPermissionV11() {

        // For Version 11 seperately called Camera permission to storage permission PERMISSION_CAMERA_V11_CONSTANT
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_CAMMERA_STORAGE_V11_CONSTANT);

    }

    private void requestPermission() {

            //below android 11
            ActivityCompat.requestPermissions(this, perms, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT);

    }




    public void dialogStorage() {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Permission Required..!");
        builder.setMessage("Please allow the storage permission");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (!checkPermission()) {
                            if (SDK_INT >= Build.VERSION_CODES.R) {

                                requestStoragePermissionV11();



                            }
                        }
                    }
                });


        androidx.appcompat.app.AlertDialog exitdialog = builder.create();
        exitdialog.show();
        Button positive = exitdialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setTextColor(getResources().getColor(R.color.black));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSION_CAMERA_STORACGE_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                        if (camera && writeExternal && readExternal) {

                            showCamerGalleryPopUp();

                        }




                }
                break;

            case Constants.PERMISSION_CAMMERA_STORAGE_V11_CONSTANT:


                if (SDK_INT >= Build.VERSION_CODES.R) {

                    if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        if (Environment.isExternalStorageManager()) {

                            showCamerGalleryPopUp();

                        }
                    } else {
                        // Permission request was denied.
                        Snackbar.make(lyParent, R.string.camera_permission_denied,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }else{

                }
                break;


        }
    }


    //endregion


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnSubmit){

            if (lstDoc != null && lstDoc.size() > 0) {

                if (checkAllFileUploaded()) {

                    txtDocVerify.setVisibility(View.GONE);
                    ConfirmDocAlert("Document Uploaded", getResources().getString(R.string.doc_popup), "");

                } else {
                    txtDocVerify.setVisibility(View.VISIBLE);

                    getSnakeBar(lyParent,"Document upload is pending ");

                }


            }
        }

    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.cancel();
        openAppSetting();
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        dialog.cancel();
    }


}
