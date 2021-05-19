package com.interstellar.elite.facade

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interstellar.elite.core.model.*
import java.util.*

class PrefManager(context: Context) {


    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    //var TAG = "ERROR_TAG"
    var TAG_REFERRER = "REFERRER"


    val TAG = this::class.java.simpleName

  /*  var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor
*/
    //shared pref name



    // shared pref mode
    var PRIVATE_MODE = 0

    // Shared preferences file name
    private val PREF_NAME = "ELITE_CUSTOMER"

    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

    private val USER_ELIGIBILITY = "UserEligibility"
    private val IS_GOLD_USER_ELIGIBILITY = "IsGoldUserEligibility"
    private val IS_FIRST_TIME_SHOWCASEVIEW = "IsFirstTimeShowCaseView"
    private val IS_PRODUCT_MASTER_UPDATE = "isProductMasterUpdate"
    private val IS_RTO_MASTER_UPDATE = "isProductMasterUpdate"
    private val IS_DB_VERSION_UPDATED = "isDBVersiomrUpdate"
    private val IS_CITY_VERSION_UPDATED = "isCityBVersiomrUpdate"

    private val MOBILE = "ELITE_MOBILE"
    private val PASSWORD = "ELITE_PASSWORD"

    private val VEHICLE_MASTER = "com.splash_vehicle_data"
    private val USER_CONSTATNT = "user_constatnt"
    private val CITY_CONSTATNT = "cityr_constatnt"
    private val PUSH_NOTIFY_DATA = "push_notify_data"

    private val ELITE_COMPANY_NAME = "elite_company_name"
    private val ELITE_COMPANY_ID = "elite_company_id"

    private val IS_DEVICE_TOKEN = "devicetoken"
    var DEVICE_ID = "deviceID"
    var NOTIFICATION_COUNTER = "Notification_Counter"
    var USER_DATA = "user_data"


    init {

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
//    fun PrefManager(context: Context?) {
//        _context = context
//        pref = _context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
//        editor = pref.edit()
//    }


   //region Login Entity
    fun storeUserData(entity: UserEntity?): Boolean {
        return try {
            editor!!.putString(USER_DATA, Gson().toJson(entity))
            editor!!.commit()
        } catch (ex: Exception) {
            Log.e(TAG, "Error in Vehicle Data" + ex.message)
            false
        }
    }


    fun getUserData(): UserEntity? {
        val userConstatnt = pref!!.getString(USER_DATA, "")
        return if (userConstatnt!!.length > 0) {
            Gson().fromJson(userConstatnt, UserEntity::class.java)
        } else {
            null
        }
    }

    fun getVehicleNumber(): String {

        if (getUserData() != null) {

            val userConstatnt = getUserData()

            return userConstatnt?.vehicleno?: ""
        } else {
            return ""
        }

    }

    fun getUserName(): String {

        if (getUserData() != null) {

            val userConstatnt = getUserData()

            return userConstatnt?.name?: ""
        } else {
            return ""
        }

    }

    //endregion

    //region master User Constant
    fun storeUserConstatnt(entity: UserConstatntEntity?): Boolean {
        editor!!.putString(USER_CONSTATNT, Gson().toJson(entity))
        return editor!!.commit()
    }

    fun getUserConstatnt(): UserConstatntEntity? {
        val userConstatnt = pref!!.getString(USER_CONSTATNT, "")
        return if (userConstatnt?.length?: 0 > 0) {
            Gson().fromJson(userConstatnt, UserConstatntEntity::class.java)
        } else {
            return  null
        }
    }

//    //endregion


    fun storeUserEligibility(entity: EligibilityEntity?):Boolean {
        editor!!.putString(USER_ELIGIBILITY, Gson().toJson(entity))
        return editor!!.commit()
    }



    fun getUserEligibility(): EligibilityEntity? {
        val eligibilityEntity = pref!!.getString(USER_ELIGIBILITY, "")
        return if (eligibilityEntity?.length ?: 0 > 0) {
            Gson().fromJson(eligibilityEntity, EligibilityEntity::class.java)
        } else {
            return null
        }

    }

    fun IsGoldUser(): String {
        val eligibilityEntity = pref!!.getString(USER_ELIGIBILITY, "")
        if (getUserEligibility() != null) {

            val eligibilityEntity = getUserEligibility()

            return eligibilityEntity?.eligible?: ""
        } else {
            return ""
        }

    }

    fun getMakeFromEligibility(): String {

        if (getUserEligibility() != null) {

            val eligibilityEntity = getUserEligibility()

            return eligibilityEntity?.Make?: ""
        } else {
            return ""
        }

    }


    fun getModelFromEligibility(): String {

        if (getUserEligibility() != null) {

            val eligibilityEntity = getUserEligibility()

            return eligibilityEntity?.Model?: ""
        } else {
            return ""
        }

    }


//
//    //region master vehicle
//
    //region master vehicle
    fun storeVehicle(entity: VehicleMasterEntity?): Boolean {
        editor!!.putString(VEHICLE_MASTER, Gson().toJson(entity))
        val isSaved = editor!!.commit()
        Log.d(TAG, "storeVehicle: $isSaved")
        return editor!!.commit()
    }

    fun getMake(): List<MakeEntity?>? {
        val fourWheeler = pref!!.getString(VEHICLE_MASTER, "")
        Log.d(TAG, "getMake: $fourWheeler")
        return if (fourWheeler!!.length > 0) {
            val vehicleMaster: VehicleMasterEntity = Gson().fromJson(fourWheeler, VehicleMasterEntity::class.java)
            vehicleMaster.getMake()
        } else {
            null
        }
    }

    fun getModel(entity: MakeEntity): List<ModelEntity?>? {
        return entity.getModel()
    }

    fun getVariant(entity: ModelEntity): List<VariantEntity?>? {
        return if (entity.getVariant() != null) entity.getVariant() else null
    }
//    //endregion
//
//
//    //region master NotificationData
//
//    //endregion
//    //region master NotificationData
//    fun storePushData(entity: NotifyEntity?): Boolean {
//        editor!!.putString(PUSH_NOTIFY_DATA, Gson().toJson(entity))
//        return editor!!.commit()
//    }
//
//    fun getPushNotifyData(): NotifyEntity? {
//        val pushDtata = pref!!.getString(PUSH_NOTIFY_DATA, "")
//        return if (pushDtata!!.length > 0) {
//            Gson().fromJson(pushDtata, NotifyEntity::class.java)
//        } else {
//            null
//        }
//    }
//
//    fun clearNotification() {
//        pref!!.edit().remove(PUSH_NOTIFY_DATA).commit()
//    }
//
//    //endregion
//
//
//    //region master User Constant
//
//    //endregion

//
//    //region master CityData
//
//    //endregion
    //region master CityData
    fun storeCityData(lstCityMain: List<CityMainEntity?>?): Boolean {
        val gson = Gson()
        val listCity = gson.toJson(
                lstCityMain,
                object : TypeToken<ArrayList<CityMainEntity?>?>() {}.type)
        editor!!.putString(CITY_CONSTATNT, listCity)
        return editor!!.commit()
    }

    fun getCityData(): List<CityMainEntity?>? {
        val listCity = pref!!.getString(CITY_CONSTATNT, "")
        return if (listCity!!.length > 0) {
            val listType = object : TypeToken<List<CityMainEntity?>?>() {}.type
            Gson().fromJson<Any>(listCity, listType) as List<CityMainEntity?>
        } else {
            ArrayList<CityMainEntity?>()
        }
    }

//
//    //endregion
//
//
//    //endregion
//    //
//    fun setFirstTimeShowCaseView(isFirstTime: Boolean) {
//        editor!!.putBoolean(IS_FIRST_TIME_SHOWCASEVIEW, isFirstTime)
//        editor!!.commit()
//    }
//
//    fun isFirstTimeShowCaseView(): Boolean {
//        return pref!!.getBoolean(IS_FIRST_TIME_SHOWCASEVIEW, true)
//    }
//
//
    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor!!.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor!!.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref!!.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }
//
//
//    fun setIsProductMasterUpdate(isFirstTime: Boolean) {
//        editor!!.putBoolean(IS_PRODUCT_MASTER_UPDATE, isFirstTime)
//        editor!!.commit()
//    }
//
//    fun IsProductMasterUpdate(): Boolean {
//        return pref!!.getBoolean(IS_PRODUCT_MASTER_UPDATE, true)
//    }
//
//    fun setIsRtoMasterUpdate(isFirstTime: Boolean) {
//        editor!!.putBoolean(IS_RTO_MASTER_UPDATE, isFirstTime)
//        editor!!.commit()
//    }
//
//    fun IsRtoMasterUpdate(): Boolean {
//        return pref!!.getBoolean(IS_RTO_MASTER_UPDATE, true)
//    }
//
//    ///
//
//
//    ///
//    fun setIsDBVersionUpdate(dbVersion: Int) {
//        editor!!.putInt(IS_DB_VERSION_UPDATED, dbVersion)
//        editor!!.commit()
//    }
//
//    fun getDBVersion(): Int? {
//        return pref!!.getInt(IS_DB_VERSION_UPDATED, 1)
//    }
//    ///
//
//    ///
//    fun setCityVersionUpdate(cityVersion: Int) {
//        editor!!.putInt(IS_CITY_VERSION_UPDATED, cityVersion)
//        editor!!.commit()
//    }
//
//    fun getCityVersion(): Int? {
//        return pref!!.getInt(IS_CITY_VERSION_UPDATED, 1)
//    }
//




    fun setMobile(mob: String?) {
        editor!!.putString(MOBILE, mob)
        editor!!.commit()
    }

    fun getMobile(): String {
        return pref.getString(MOBILE, "") ?: ""
    }

    fun setPassword(pwd: String?) {
        editor!!.putString(PASSWORD, pwd)
        editor!!.commit()
    }

    fun getPassword(): String {
        return pref.getString(PASSWORD, "") ?: ""
    }


    fun setDeviceID(deviceID: String?) {
        editor!!.putString(DEVICE_ID, deviceID)
        editor!!.commit()
    }

    fun getDeviceID(): String? {
        return pref!!.getString(DEVICE_ID, "")
    }

    fun getNotificationCounter(): Int {
        return pref!!.getInt(NOTIFICATION_COUNTER, 0)
    }

    fun setNotificationCounter(counter: Int) {
        editor!!.putInt(NOTIFICATION_COUNTER, counter)
        editor!!.commit()
    }
//
//
    fun setToken(token: String?) {
        editor!!.putString(IS_DEVICE_TOKEN, token)
        editor!!.commit()
    }

    fun getToken(): String? {
        return pref!!.getString(IS_DEVICE_TOKEN, "")
    }

//
//    //region Company Identification
//    fun setCompanyID(compID: String, compName: String) {
//        Log.d(TAG_REFERRER, "Company ID$compID And $compName")
//        editor!!.putString(ELITE_COMPANY_ID, compID)
//        editor!!.putString(ELITE_COMPANY_NAME, compName)
//        editor!!.commit()
//    }
//
    fun getCompanyID(): String? {
        return pref!!.getString(ELITE_COMPANY_ID, "0")
    }

    fun getCompanyName(): String? {
        return pref!!.getString(ELITE_COMPANY_NAME, "")
    }
//    //endregion
//
//
//    //endregion
    fun clearUserCache() {
        editor!!.remove(PASSWORD)
        editor!!.remove(MOBILE)
        editor!!.remove(USER_CONSTATNT)
        editor!!.remove(USER_DATA)
        editor!!.remove(USER_ELIGIBILITY)
        editor!!.commit()
    }


}