package com.interstellar.elite.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.interstellar.elite.BaseFragment
import com.interstellar.elite.R
import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.IResponseSubcriber
import com.interstellar.elite.core.controller.product.ProductController
import com.interstellar.elite.core.model.NotificationEntity
import com.interstellar.elite.core.model.UserEntity
import com.interstellar.elite.core.response.NotificationResponse
import com.interstellar.elite.document.DocUploadActivity
import com.interstellar.elite.facade.PrefManager
import com.interstellar.elite.request.OrderActivity
import kotlinx.android.synthetic.main.content_notification.*
import java.util.*


class NotificationFragment : BaseFragment() ,IResponseSubcriber {
    // TODO: Rename and change types of parameters
    lateinit var prefManager: PrefManager
    var loginEntity: UserEntity? = null

   // var rvNotify: RecyclerView? = null
    var NotificationLst: List<NotificationEntity>? = null
    var mAdapter: NotificationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notification, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireActivity())
        loginEntity = prefManager.getUserData()

        initialize()

        showDialog()
        ProductController(requireActivity()).getNotifcation(loginEntity!!.user_id, "1", this)

    }

    companion object {

        @JvmStatic
        fun newInstance(): NotificationFragment = NotificationFragment()
    }

    private fun initialize() {
        NotificationLst = ArrayList()
        prefManager.setNotificationCounter(0)
        rvNotify!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireActivity())
        rvNotify!!.layoutManager = layoutManager
    }

    fun redirectToMain(notifyEntity: NotificationEntity) {
        if (notifyEntity.notifyflag.toUpperCase() == "DR") {
            startActivity(Intent(requireActivity(), DocUploadActivity::class.java))

        } else if (notifyEntity.notifyflag.toUpperCase() == "NA") {
            startActivity(Intent(requireActivity(), OrderActivity::class.java))

        }
    }

    override fun OnSuccess(response: APIResponse, message: String) {
        cancelDialog()
        if (response is NotificationResponse) {
            if (response.status_code == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                NotificationLst = (response as NotificationResponse).data
                mAdapter = NotificationAdapter(this, NotificationLst)
                rvNotify.adapter = mAdapter
            } else {
                rvNotify.adapter = null
                Snackbar.make(rvNotify, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun OnFailure(error: String) {
        cancelDialog()
        val toast = Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG)
        toast.show()
    }
}