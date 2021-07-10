package com.interstellar.elite.home

//import com.interstellar.elite.BuildConfig
import BaseActivityKotlin
import ServiceName
import ServiceRequest
import android.Manifest.permission
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

import com.interstellar.elite.BuildConfig.VERSION_NAME
import com.interstellar.elite.BuildConfig.VERSION_CODE
import com.interstellar.elite.R
import com.interstellar.elite.TermsCondtion.TermsConditionFragment
import com.interstellar.elite.aboutUs.AboutUsFragment
import com.interstellar.elite.changePassword.ChangePasswordFragment
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.EligibilityEntity
import com.interstellar.elite.core.model.UserConstatntEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.EligibilityUserResponse
import com.interstellar.elite.core.response.UserConsttantResponse
import com.interstellar.elite.databinding.ActivityHomeBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.home.dashboard.DashboardFragment
import com.interstellar.elite.login.LoginActivity
import com.interstellar.elite.notification.NotificationFragment
import com.interstellar.elite.profile.ProfileFragment
import com.interstellar.elite.request.OrderDetailFragment
import com.interstellar.elite.utility.Constants

//import kotlinx.android.synthetic.main.activity_home.*
//import kotlinx.android.synthetic.main.activity_rto_list.*
//import kotlinx.android.synthetic.main.app_bar_navigation.*
//import kotlinx.android.synthetic.main.layout_dashboard_banner.*
//import kotlinx.android.synthetic.main.layout_dashboard_banner.view.*


class HomeActivity : BaseActivityKotlin(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener, IResponseSubcriber {

    lateinit var fabHome: FloatingActionButton
    lateinit var navigationView: NavigationView
    lateinit var authenticationController: AuthenticationController

    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null
    var userConstatntEntity: UserConstatntEntity? = null


    lateinit var binding: ActivityHomeBinding

    lateinit var changePasswordFragment: ChangePasswordFragment
    lateinit var termsConditionFragment: TermsConditionFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        loginEntity = prefManager.getUserData()

        init()
        setListener()

        setUserData()

        showLoading("Please wait..")

        loginEntity.let {

            authenticationController.getUserEligibility(
                it!!.mobile.toString(),
                it!!.vehicleno,
                this@HomeActivity
            )
            //
        }

        checkForUpdate()

        //endregion


    }

    private fun checkForUpdate() {

        var serverVerCode = loginEntity?.version ?: "0"

        if (VERSION_CODE < serverVerCode.toLong()) {

            authenticationAlert("Update", getString(R.string.versionUpdate))
        }


    }

    // region method

    fun init() {

        binding.includeAppBar.dashboardBanner.txtName.text = ""
        binding.includeAppBar.dashboardBanner.txtNameOth.text = ""

        // Note : toolbar replace by null bec here using custom icon
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            null,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {}

        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null
        fabHome = findViewById<FloatingActionButton>(R.id.fab)
        binding.includeAppBar.bottomNavigationView.setBackground(null)
        binding.includeAppBar.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.includeAppBar.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )


        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            this
        ) as AuthenticationController

        init_headers()

        checkEligibility()

    }

    // Temp added for Showing the value
    fun getEligibilityCall() {

        var eligibleJson = ""
        if (loginEntity!!.mobile.equals("9898147711") || loginEntity!!.mobile.equals("9833797088")) {
            eligibleJson =
                "{\"EliteEligibilityCheckResult\":{\"EliteEligibilityCheckdetails\":[{\"Make\":\"Honda\",\"MobileNo\":\"\",\"Model\":\"Brio\",\"Premium\":\"\",\"RegistrationNo\":\"\",\"eligible\":\"YES\"}],\"message\":\"Vehicle is already registered\",\"status\":\"Failed\",\"status_code\":0}}"
        }
//        else{
//             eligibleJson = "{\"EliteEligibilityCheckResult\":{\"EliteEligibilityCheckdetails\":[{\"Make\":\"HONDA\",\"MobileNo\":\"\",\"Model\":\"ACCORD\",\"Premium\":\"\",\"RegistrationNo\":\"\",\"eligible\":\"N\"}],\"message\":\"Vehicle is already registered\",\"status\":\"Failed\",\"status_code\":0}}"

        //     }


        val objResponse = Gson().fromJson(eligibleJson, EligibilityUserResponse::class.java)

        var objEligibility: EligibilityEntity =
            objResponse.EliteEligibilityCheckResult.EliteEligibilityCheckdetails.get(
                0
            )
        prefManager.storeUserEligibility(objEligibility)
        checkEligibility()
        Log.d("ELIGOBILITY", objEligibility.toString())
    }

    fun setListener() {
        binding.includeAppBar.dashboardBanner.imgHamburger.setOnClickListener(this)
        binding.includeAppBar.dashboardBanner.imgHamburgerOth.setOnClickListener(this)
        fabHome.setOnClickListener(this)
        fabHome.performClick()
    }


    fun checkEligibility() {


        //region commented
//       prefManager.getUserEligibility()?.let {
//
//                   if(it!!.eligible.toUpperCase().equals("YES")){
//
//                       //Gold
//                       imglogo.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_gold
//                               )
//                       )
//                       imglogoDashboard.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_gold
//                               )
//                       )
//                       imglogo1.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_gold
//                               )
//                       )
//
//                   }
//                   else{
//                       //Plus
//                       imglogo.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_plus
//                               )
//                       )
//                       imglogo1.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_plus
//                               )
//                       )
//                       imglogoDashboard.setImageDrawable(
//                               ContextCompat.getDrawable(
//                                       applicationContext,
//                                       R.drawable.elite_plus
//                               )
//                       )
//                   }
//
//
//               }

        //endregion

        var isGolduser = loginEntity?.isgoldverify ?: ""

        isGolduser.let {

            if (it!!.toUpperCase().equals("Y")) {

                //Gold
                binding.includeAppBar.dashboardBanner.imglogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_gold
                    )
                )
                binding.includeAppBar.dashboardBanner.imglogoDashboard.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_gold
                    )
                )
                binding.includeAppBar.dashboardBanner.imglogo1.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_gold
                    )
                )

            } else {

                binding.includeAppBar.dashboardBanner.imglogo.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_plus
                    )
                )
                binding.includeAppBar.dashboardBanner.imglogo1.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_plus
                    )
                )
                binding.includeAppBar.dashboardBanner.imglogoDashboard.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.elite_plus
                    )
                )

            }


        }

    }


    private fun init_headers() {

        val headerView = navigationView.getHeaderView(0)
        val txtHeaderName = headerView.findViewById<View>(R.id.txtName) as TextView
        val txtHeaderVehicle = headerView.findViewById<View>(R.id.txtVehicle) as TextView
        val txtHeaderVersion = headerView.findViewById<View>(R.id.txtVersion) as TextView
        //  val txtHeaderVersion.setText("Version " + BuildConfig.VERSION_NAME!!)
        if (loginEntity != null) {
            txtHeaderName.text = "" + loginEntity?.name
            txtHeaderVehicle.setText("" + loginEntity?.vehicleno)

            txtHeaderVersion.setText("Version " + VERSION_NAME)
        } else {
            txtHeaderName.text = ""
            txtHeaderVehicle.setText("")
            txtHeaderVersion.text = ""
        }

    }


    private fun openFragment(fragment: Fragment, type: String) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        setbannerUI(type)


    }


    private fun setHome() {

        // toolbar.title = "Home"
        val currFragment = supportFragmentManager.findFragmentById(R.id.container)

        if (currFragment !is DashboardFragment) {
            val dashFragment = DashboardFragment.newInstance()
            openFragment(dashFragment, "HOME")

            // for Reset the bottom Navigation Menu
            binding.includeAppBar.bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        }
    }

    fun setUserData() {

        loginEntity.let {

            binding.includeAppBar.dashboardBanner.txtName.text = "Welcome " + it?.name ?: ""
            binding.includeAppBar.dashboardBanner.txtNameOth.text = "Welcome " + it?.name ?: ""
        }
    }

    fun resetBottomNavigationMenu() {

        // for Reset the bottom Navigation Menu
        binding.includeAppBar.bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
    }


    fun setChildDashListFragment(fragment: Fragment, dbProductType: String, strTitle: String) {

//         txtTitle.text = strTitle
//         setbannerUI("HomeChild")
//        val bundle = Bundle() // use bundle for paasing the data
//        bundle.putString(Constants.DB_PRODUCT_TYPE, dbProductType)
//         fragment.arguments = bundle
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, fragment)
//        transaction.addToBackStack("HomeChild")
//        transaction.commit()

    }

    // set Dashboard Banner And BottomNavigation
    private fun setbannerUI(type: String) {

        if (type.equals("HOME")) {
            fabHome.tag = "HOME"

            binding.includeAppBar.dashboardBanner.dashboardBanner.visibility = View.VISIBLE
            binding.includeAppBar.dashboardBanner.otherBanner.visibility = View.GONE

            //  bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_primary_color))

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                fabHome.setSupportBackgroundTintList(
                    AppCompatResources.getColorStateList(
                        fabHome.context,
                        R.color.button_color
                    )
                )
            }
        } else if (type.equals("HomeChild")) {
            fabHome.tag = "HomeChild"

            binding.includeAppBar.dashboardBanner.dashboardBanner.visibility = View.GONE
            binding.includeAppBar.dashboardBanner.otherBanner.visibility = View.VISIBLE

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                fabHome.setSupportBackgroundTintList(
                    AppCompatResources.getColorStateList(
                        fabHome.context,
                        R.color.button_color
                    )
                )
            }
        } else {
            fabHome.tag = "OTHER"

            binding.includeAppBar.dashboardBanner.dashboardBanner.visibility = View.GONE
            binding.includeAppBar.dashboardBanner.otherBanner.visibility = View.VISIBLE
            // bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color))

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                fabHome.setSupportBackgroundTintList(
                    AppCompatResources.getColorStateList(
                        fabHome.context,
                        R.color.gray_primary_color
                    )
                )
            }
        }
    }

    // endregion

    // region Dialog

    fun authenticationAlert(
        strhdr: String,
        strBody: String
    ) {
        val builder = AlertDialog.Builder(this@HomeActivity, R.style.CustomDialog);
        val btnClose: Button
        val txtHdr: TextView
        val txtMessage: TextView
        val lyReceipt: LinearLayout
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_success_message, null)
        builder.setView(dialogView)
        val alertDialog = builder.create()

        btnClose = dialogView.findViewById(R.id.btnClose)
        txtMessage = dialogView.findViewById(R.id.txtMessage)
        txtHdr = dialogView.findViewById(R.id.txtHdr)
        txtHdr.text = "" + strhdr
        txtMessage.text = "" + strBody
        btnClose.text = "Continue"
        btnClose.setOnClickListener {
            alertDialog.dismiss()

            prefManager.clearUserCache()

            openAppMarketPlace()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


    private fun dialogExit() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Do you want to exit the application?")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "YES"
        ) { dialog, _ ->
            dialog.cancel()
            this.finish()
        }

        builder.setNegativeButton(
            "NO",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            })
        val exitdialog = builder.create()
        exitdialog.show()

        exitdialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(
            ContextCompat.getColor(
                this,
                R.color.header_text_color
            )
        )
        exitdialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(
            ContextCompat.getColor(
                this,
                R.color.sub_text_color
            )
        )

    }

    private fun openAppMarketPlace() {
        val appPackageName = this.packageName // getPackageName() from Context or Activity object
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }

    //endregion

    //region Navigation Menu

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START, true)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        resetBottomNavigationMenu() // for deselect Bottom Navigation Menu

        val currFragment = supportFragmentManager.findFragmentById(R.id.container)

        when (menuItem.itemId) {

            R.id.nav_home -> {

                setHome()
            }
            R.id.nav_change_pwd -> {

                if (currFragment !is ChangePasswordFragment) {

                    binding.includeAppBar.dashboardBanner.txtTitle.text = "Change Password"

                    changePasswordFragment = ChangePasswordFragment()
                    openFragment(changePasswordFragment, "HomeChild")
                }

            }
            R.id.nav_terms -> {

                if (currFragment !is TermsConditionFragment) {

                    binding.includeAppBar.dashboardBanner.txtTitle.text = "Terms and  Condition"
                    termsConditionFragment = TermsConditionFragment()

                    openFragment(termsConditionFragment, "HomeChild")
                }

            }


            R.id.nav_logout -> {
                prefManager.clearUserCache();

                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish()
            }


        }

        closeDrawer()
        return true

    }

    //endregion

    // region Bottom Naviation Listener
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            val currFragment = supportFragmentManager.findFragmentById(R.id.container)
            when (item.itemId) {

                R.id.bottom_nav_aboutUS -> {
                    // toolbar.title = "About Us"
                    if (currFragment !is AboutUsFragment) {
                        binding.includeAppBar.dashboardBanner.txtTitle.text = "About Us"
                        item.isCheckable = true

                        val aboutUsFragment = AboutUsFragment.newInstance()

                        openFragment(aboutUsFragment, "")
                        return@OnNavigationItemSelectedListener true
                    }

                }
                R.id.bottom_nav_profile -> {
                    //  toolbar.title = "Profile"
                    if (currFragment !is ProfileFragment) {
                        binding.includeAppBar.dashboardBanner.txtTitle.text = "Profile"
                        item.isCheckable = true
                        val profileFragment = ProfileFragment.newInstance()
                        openFragment(profileFragment, "")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.bottom_nav_request -> {
                    //  toolbar.title = "Request"
                    if (currFragment !is OrderDetailFragment) {
                        binding.includeAppBar.dashboardBanner.txtTitle.text = "Request"
                        item.isCheckable = true
                        val requestFragment = OrderDetailFragment.newInstance()
                        openFragment(requestFragment, "")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.bottom_nav_notification -> {
                    //  toolbar.title = "Notification"
                    if (currFragment !is NotificationFragment) {
                        binding.includeAppBar.dashboardBanner.txtTitle.text = "Notification"
                        item.isCheckable = true
                        val artistsFragment = NotificationFragment.newInstance()
                        openFragment(artistsFragment, "")
                        return@OnNavigationItemSelectedListener true
                    }
                }
            }
            false
        }

    //endregion

    //region Event

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fab -> {
                setHome()
            }
            R.id.imgHamburger, R.id.imgHamburgerOth -> {
                openDrawer()
            }
        }
    }

    override fun onResume() {
        super.onResume()


        verifyNetwork()

    }

    private fun checkPermission(): Boolean {
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result = ContextCompat.checkSelfPermission(
                this@HomeActivity,
                permission.READ_EXTERNAL_STORAGE
            )
            val result1 = ContextCompat.checkSelfPermission(
                this@HomeActivity,
                permission.WRITE_EXTERNAL_STORAGE
            )
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermission() {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data =
                    Uri.parse(java.lang.String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                this@HomeActivity,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                Constants.PERMISSION_CAMERA_STORACGE_CONSTANT
            )
        }
    }

    fun verifyNetwork() {
        if (!Constants.checkInternetStatus(this@HomeActivity)) {

            checkNetwork()

        }
//        else {
//            // Note :--  User Constant are Required
//            apiService()
//        }


    }

    fun apiService() {

        showLoading("Please wait..")

        loginEntity.let {

            authenticationController.getUserConstatnt(it!!.user_id.toString(), this@HomeActivity)
        }
    }


    fun checkNetwork() {
        val builder = AlertDialog.Builder(this)
        val btnRetry: AppCompatButton
        val inflater = this.layoutInflater
        val dialogView: View
        dialogView = inflater.inflate(R.layout.lycheck_internet_dialog, null)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        // set the custom dialog components - text, image and button
        btnRetry = dialogView.findViewById<View>(R.id.btnRetry) as AppCompatButton
        btnRetry.setOnClickListener {
            alertDialog.dismiss()
            verifyNetwork()
        }
        alertDialog.setCancelable(false)
        // alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        alertDialog.show()
    }

    fun dialogStorage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permission Required..!")
        builder.setMessage("Please allow the storage permission")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "Allow"
        ) { dialog, id ->
            dialog.cancel()
            if (!checkPermission()) {
                requestPermission()
            }
        }
        val exitdialog = builder.create()
        exitdialog.show()
        val positive = exitdialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positive.setTextColor(resources.getColor(R.color.black))
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (!fabHome.tag.equals("HOME")) {
            fabHome.performClick()
            //setHome()
            // bottomNavigationView.selectedItemId(2)
        } else {
            //super.onBackPressed()
            dialogExit()

        }
    }


    override fun OnSuccess(apiResponse: APIResponse, message: String) {

        dismissDialog()


        if (apiResponse is EligibilityUserResponse) {
            if (apiResponse.EliteEligibilityCheckResult.status_code == 0) {

                prefManager.storeUserEligibility(
                    apiResponse.EliteEligibilityCheckResult.EliteEligibilityCheckdetails.get(0)
                )


            } else {

                // temp added 05
                // later we have to remove  getEligibilityCall()and directly check   checkEligibility() 8779909962

                // Todo : Commented
                if (loginEntity!!.mobile.equals("9833797088") || loginEntity!!.mobile.equals("9898147711")) {


                    getEligibilityCall()

                }

                // Todo : till here


            }

        }

        //endregion


        if (apiResponse is UserConsttantResponse) {
            if (apiResponse.status_code == 0) {


                userConstatntEntity = apiResponse.Data.get(0)

            }

        }
    }


    override fun OnFailure(error: String) {
        dismissDialog()
        // hideKeyBoard(nav_view)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.PERMISSION_CAMERA_STORACGE_CONSTANT) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    // Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    dialogStorage()
                }
            }
        }
    }

    //endregion

}