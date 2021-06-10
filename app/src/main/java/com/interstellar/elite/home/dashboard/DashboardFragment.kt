package com.interstellar.elite.home.dashboard

import BaseFragmentKotlin
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.AuthenticationController
import com.interstellar.elite.core.model.LoginEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.databinding.FragmentDashboardBinding
import com.interstellar.elite.databinding.FragmentProfileBinding
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.home.dashboardPrdList.DashBoardProductListActivity
import com.interstellar.elite.rto.RtoMain.RtoListActivity
import com.interstellar.elite.utility.Constants
//import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment :BaseFragmentKotlin(), View.OnClickListener, IResponseSubcriber {

    lateinit var authenticationController: AuthenticationController
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

    lateinit var binding : FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_dashboard, container, false)

        binding =  FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticationController = ServiceRequest.getService(
            ServiceName.AUTHENTICATION.value,
            view.context
        ) as AuthenticationController

        init()
        setListener()


    }

    private fun init(){

        prefManager = PrefManager(requireActivity())
        loginEntity = prefManager.getUserData()

        loginEntity.let {

          binding.txtVehicle.text =  it?.vehicleno?: ""
        }


    }
    companion object {

        @JvmStatic
        fun newInstance(): DashboardFragment = DashboardFragment()
    }
    private fun setListener() {

        binding.cvAssure.setOnClickListener(this)
        binding.cvRto.setOnClickListener(this)
        binding.cvSecure.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cvSecure -> {

                val intent = Intent(activity,DashBoardProductListActivity::class.java)
                intent.putExtra(Constants.DB_PRODUCT_TYPE,Constants.Secure_List)
                intent.putExtra("Title","SECURE+")
                startActivity(intent)
            }
            R.id.cvAssure -> {
                val intent = Intent(activity,DashBoardProductListActivity::class.java)
                intent.putExtra(Constants.DB_PRODUCT_TYPE,Constants.Assure_List)
                intent.putExtra("Title","ASSURE+")
                startActivity(intent)
            }
            R.id.cvRto -> {

                val intent = Intent(activity, RtoListActivity::class.java)

                startActivity(intent)
            }

        }
    }



    //region Not in Used

    // Showing List of Product (ie SucureList ,Assure List
//    fun moveToDashBoardListFragment(dbProductType : String,strTitle : String){
//        val prodListFragment = DashBoardProdListFragment.newInstance()
//        (activity as HomeActivity).setChildDashListFragment(prodListFragment,dbProductType,strTitle)
//    }
//
//    // Showing List of RTO Product (having subType also)
//    fun moveToRtoListFragment(dbProductType : String,strTitle : String){
//
//        val prodListFragment = RtoListFragment.newInstance()
//        (activity as HomeActivity).setChildDashListFragment(prodListFragment,dbProductType,strTitle)
//    }

    //endregion

    override fun OnSuccess(apiResponse: APIResponse, message: String) {
        TODO("Not yet implemented")
    }

    override fun OnFailure(error: String) {
        TODO("Not yet implemented")
    }
}