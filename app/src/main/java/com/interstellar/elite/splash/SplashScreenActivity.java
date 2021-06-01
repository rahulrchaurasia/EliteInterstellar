package com.interstellar.elite.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.document.DocUploadActivity;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.home.HomeActivity;
import com.interstellar.elite.login.LoginActivity;
import com.interstellar.elite.utility.Constants;
import com.interstellar.elite.welcome.EulaActivity;
import com.interstellar.elite.welcome.WelcomeActivity;


public class SplashScreenActivity extends BaseActivity implements IResponseSubcriber {

    PrefManager prefManager;
    TextView txtGroup;
    ImageView imglogo;
    private final int SPLASH_DISPLAY_LENGTH = 2100;


    Animation topAnim,bottomAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        imglogo = (ImageView) findViewById(R.id.imglogo);
        txtGroup = (TextView) findViewById(R.id.txtGroup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        prefManager = new PrefManager(this);

        initialize();
        verify();
       // checkNetwork();
    }

    private void initialize(){

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imglogo.setAnimation(topAnim);
        txtGroup.setAnimation(bottomAnim);

    }
    private void fetchCar() {
        new RegisterController(this).getCarVehicleMaster(this);
    }

    private void fetchCity() {
        new RegisterController(SplashScreenActivity.this).getCityMainMaster(this);
    }

//    private void fetchUserConstatnt() {
//        new RegisterController(this).getUserConstatnt(null);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchMasters();
    }

    private void fetchMasters() {

          fetchCity();
//        if(prefManager.getCityData().size() == 0)
//        {
//            fetchCity();
//        }

//        if (prefManager.getMake() == null) {
//            fetchCar();
//        }




    }

    @Override
    public void OnSuccess(APIResponse response, String message) {


    }

    @Override
    public void OnFailure(String error) {


    }

    public void verify() {
        if (!Constants.checkInternetStatus(SplashScreenActivity.this)) {

            Snackbar snackbar = Snackbar.make(txtGroup, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            snackbar.show();



        } else {
            if (prefManager.isFirstTimeLaunch()) {
              //  startActivity(new Intent(this, WelcomeActivity.class));
                startActivity(new Intent(this, EulaActivity.class));

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Todo comment :  For Direct Openenig of Home
                        //region commented
//                        UserEntity loginEntity = prefManager.getUserData();
//                        if (loginEntity != null) {
//                            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
//
//                        } else {
//                            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                        }
                        //endregion

                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));

                    }
                }, SPLASH_DISPLAY_LENGTH);

            }
        }
    }


}
