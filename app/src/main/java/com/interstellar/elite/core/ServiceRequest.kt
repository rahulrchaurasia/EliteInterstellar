

import android.content.Context
import com.interstellar.elite.core.BaseController
import com.interstellar.elite.core.controller.AuthenticationController

import java.util.*

open class ServiceRequest {

    companion object {
        private val objectContainer = WeakHashMap<Int, Any>()
        lateinit var baseController: BaseController


        fun getService(serviceName: Int, context: Context): BaseController {

            if (objectContainer.containsKey(serviceName)) {
                baseController = objectContainer.get(serviceName) as BaseController
                return baseController
            }

            when (serviceName) {

                ServiceName.AUTHENTICATION.value -> {

                    baseController = AuthenticationController(context)
                }
//                ServiceName.MYLOAN.value -> {
//                    baseController = LoanController(context)
//                }
//                ServiceName.TOPUPOFFER.value ->{
//                    baseController = TopUpOfferController(context)
//                }


            }

            objectContainer.put(serviceName, baseController)

            return baseController!!
        }

    }
}