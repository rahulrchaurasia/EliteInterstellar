package com.interstellar.elite.document;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.interstellar.elite.BaseFragment;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.product.ProductController;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.response.DocumentResponse;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.utility.Utility;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocUploadFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;
    LinearLayout llDocumentUpload;
    ImageView ivLogo, ivClientLogo,
            ivPhotoCam, ivPhotoGallery, ivPanCam, ivPanGallery, ivCancelCam, ivCancelGallery, ivAadharCam, ivAadharGallery,
            ivAadhar, ivCancel, ivPan, ivPhoto;
    TextView txtViewDoc1, txtViewDoc2, txtViewDoc3, txtViewDoc4;

    HashMap<String, Integer> body;
    MultipartBody.Part part;
    File Docfile;
    File file;
    Uri imageUri;

    int OrderID;
    PrefManager prefManager;


    private int PROFILE = 1, PHOTO = 2, PAN = 3, AADHAR = 4;
    private String DOC1 = "DOC1", DOC2 = "DOC2", DOC3 = "DOC3", DOC4 = "DOC4";
    int type;


    UserEntity loginEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  return inflater.inflate(R.layout.fragment_doc_upload, container, false);

        View view = inflater.inflate(R.layout.fragment_doc_upload, container, false);
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();

        initialize(view);


        return view;
    }

    private void initialize(View view) {


        //  spProduct = (Spinner) view.view.findViewById(R.id.spProduct);

        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);

      //  ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
       // ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        ivPhotoCam = (ImageView) view.findViewById(R.id.ivPhotoCam);
        ivPhotoGallery = (ImageView) view.findViewById(R.id.ivPhotoGallery);
        ivPanCam = (ImageView) view.findViewById(R.id.ivPanCam);
        ivPanGallery = (ImageView) view.findViewById(R.id.ivPanGallery);

        ivCancelCam = (ImageView) view.findViewById(R.id.ivCancelCam);
        ivCancelGallery = (ImageView) view.findViewById(R.id.ivCancelGallery);
        ivAadharCam = (ImageView) view.findViewById(R.id.ivAadharCam);
        ivAadharGallery = (ImageView) view.findViewById(R.id.ivAadharGallery);

        ivAadhar = (ImageView) view.findViewById(R.id.ivAadhar);
        ivCancel = (ImageView) view.findViewById(R.id.ivCancel);
        ivPan = (ImageView) view.findViewById(R.id.ivPan);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);

        txtViewDoc1 = (TextView) view.findViewById(R.id.txtViewDoc1);
        txtViewDoc2 = (TextView) view.findViewById(R.id.txtViewDoc2);
        txtViewDoc3 = (TextView) view.findViewById(R.id.txtViewDoc3);
        txtViewDoc4 = (TextView) view.findViewById(R.id.txtViewDoc4);

        txtViewDoc1.setOnClickListener(this);
        txtViewDoc2.setOnClickListener(this);
        txtViewDoc3.setOnClickListener(this);
        txtViewDoc4.setOnClickListener(this);

        txtViewDoc1.setVisibility(View.INVISIBLE);
        txtViewDoc2.setVisibility(View.INVISIBLE);
        txtViewDoc3.setVisibility(View.INVISIBLE);
        txtViewDoc4.setVisibility(View.INVISIBLE);


        ivPhotoCam.setOnClickListener(this);
        ivPhotoGallery.setOnClickListener(this);
        ivPanCam.setOnClickListener(this);
        ivPanGallery.setOnClickListener(this);

        ivCancelCam.setOnClickListener(this);
        ivCancelGallery.setOnClickListener(this);
        ivAadharCam.setOnClickListener(this);
        ivAadharGallery.setOnClickListener(this);


    }

    private void setDocumentUpload(String URL) {

        if (type == 1) {
            ivPhoto.setImageResource(R.drawable.doc_uploaded);
            txtViewDoc1.setTag(URL);
            txtViewDoc1.setVisibility(View.VISIBLE);
            //  Glide.with(ProductActivity.this).load(URL).into(ivPhoto);
        } else if (type == 2) {
            ivPan.setImageResource(R.drawable.doc_uploaded);
            txtViewDoc2.setTag(URL);
            txtViewDoc2.setVisibility(View.VISIBLE);
            //  Glide.with(ProductActivity.this).load(URL).into(ivPan);
        } else if (type == 3) {
            ivCancel.setImageResource(R.drawable.doc_uploaded);
            txtViewDoc3.setTag(URL);
            txtViewDoc3.setVisibility(View.VISIBLE);
            //  Glide.with(ProductActivity.this).load(URL).into(ivCancel);
        } else if (type == 4) {
            ivAadhar.setImageResource(R.drawable.doc_uploaded);
            txtViewDoc4.setTag(URL);
            txtViewDoc4.setVisibility(View.VISIBLE);
            // Glide.with(ProductActivity.this).load(URL).into(ivAadhar);
        }
    }


    private void viewUploadFile(String url) {

        if (url.equals("")) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // TouchImageView ivDocFile;
        ImageView ivDocFile;
        Button btnClose;
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView;

        dialogView = inflater.inflate(R.layout.layout_view_doc, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
//        ivDocFile = (TouchImageView) dialogView.findViewById(R.id.ivDocFile);
        ivDocFile = (ImageView) dialogView.findViewById(R.id.ivDocFile);

        Glide.with(getActivity())
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

        alertDialog.setCancelable(false);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }

    private void launchCamera() {
        //Intent cameraIntent = new Intent(MediaStore.EXTRA_OUTPUT);
        //startActivityForResult(cameraIntent, CAMERA_REQUEST);

        // start default camera
        //  Uri imageUri = Uri.fromFile(saveImageToStorage(null,PHOTO_File));

        String FileName = "";

        switch (type) {
            case 1:
                FileName = "Doc1";
                break;
            case 2:
                FileName = "Doc2";
                break;
            case 3:
                FileName = "Doc3";
                break;
            case 4:
                FileName = "Doc4";
                break;

        }
        Docfile = createFile(FileName);
        imageUri = FileProvider.getUriForFile(getActivity(),
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.txtViewDoc1:
                viewUploadFile(txtViewDoc1.getTag().toString());
                break;

            case R.id.txtViewDoc2:
                viewUploadFile(txtViewDoc2.getTag().toString());
                break;

            case R.id.txtViewDoc3:
                viewUploadFile(txtViewDoc3.getTag().toString());
                break;

            case R.id.txtViewDoc4:
                viewUploadFile(txtViewDoc4.getTag().toString());
                break;


            case R.id.ivPhotoCam:
                type = 1;
                launchCamera();
                break;

            case R.id.ivPhotoGallery:
                type = 1;
                openGallery();
                break;

            case R.id.ivPanCam:
                type = 2;
                launchCamera();
                break;

            case R.id.ivPanGallery:
                type = 2;
                openGallery();
                break;

            case R.id.ivCancelCam:
                type = 3;
                launchCamera();
                break;

            case R.id.ivCancelGallery:
                type = 3;
                openGallery();
                break;

            case R.id.ivAadharCam:
                type = 4;
                launchCamera();
                break;

            case R.id.ivAadharGallery:
                type = 4;
                openGallery();
                break;


        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof DocumentResponse) {
            if (response.getStatus_code() == 0) {


                setDocumentUpload(((DocumentResponse) response).getData().get(0).getPath());

            }
        }

    }

    @Override
    public void OnFailure(String error ) {
        cancelDialog();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = null;
            try {
                mphoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                mphoto = getResizedBitmap(mphoto, 800);


            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (type) {
                case 1:
                    showDialog();
                    file = saveImageToStorage(mphoto, DOC1);
                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PROFILE,OrderID);

                    new ProductController(getActivity()).uploadDocuments(part, body, this);
                    break;
                case 2:
                    showDialog();
                    file = saveImageToStorage(mphoto, DOC2);
                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PHOTO, OrderID);
                    new ProductController(getActivity()).uploadDocuments(part, body, this);
                    break;
                case 3:

                    showDialog();
                    file = saveImageToStorage(mphoto, DOC3);
                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PAN,OrderID);
                    new ProductController(getActivity()).uploadDocuments(part, body, this);
                    break;

                case 4:
                    showDialog();
                    file = saveImageToStorage(mphoto, DOC4);
                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(getActivity(), loginEntity.getUser_id(), AADHAR,OrderID);
                    new ProductController(getActivity()).uploadDocuments(part, body, this);
                    break;

            }


        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Bitmap mphoto = null;
            try {
                mphoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                mphoto = getResizedBitmap(mphoto, 800);
                switch (type) {
                    case 1:
                        showDialog();
                        file = saveImageToStorage(mphoto, DOC1);
                        part = Utility.getMultipartImage(file);
                        body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PROFILE,OrderID);

                        new ProductController(getActivity()).uploadDocuments(part, body, this);
                        break;
                    case 2:
                        showDialog();
                        file = saveImageToStorage(mphoto, DOC2);
                        part = Utility.getMultipartImage(file);
                        body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PHOTO,OrderID);
                        new ProductController(getActivity()).uploadDocuments(part, body, this);
                        break;
                    case 3:

                        showDialog();
                        file = saveImageToStorage(mphoto, DOC3);
                        part = Utility.getMultipartImage(file);
                        body = Utility.getBody(getActivity(), loginEntity.getUser_id(), PAN,OrderID);
                        new ProductController(getActivity()).uploadDocuments(part, body, this);
                        break;

                    case 4:
                        showDialog();
                        file = saveImageToStorage(mphoto, DOC4);
                        part = Utility.getMultipartImage(file);
                        body = Utility.getBody(getActivity(), loginEntity.getUser_id(), AADHAR,OrderID);
                        new ProductController(getActivity()).uploadDocuments(part, body, this);
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
