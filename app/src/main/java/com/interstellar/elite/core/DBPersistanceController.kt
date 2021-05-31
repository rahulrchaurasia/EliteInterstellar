package com.interstellar.elite.core

import com.interstellar.elite.R
import com.interstellar.elite.core.model.DashProductEntity
import com.interstellar.elite.core.model.InsuranceCompany
import kotlinx.android.synthetic.main.fragment_secure_list.*

open class DBPersistanceController {



    companion object {


        // region company
        fun getCompDetail(): List<InsuranceCompany> {
            val CompanyList = ArrayList<InsuranceCompany>()
            //adding some dummy data to the list


            CompanyList.add(
                InsuranceCompany(
                    "1",
                    "Bajaj Allianz General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "2",
                    "Bharti Axa General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "3",
                    "Cholamandalam MS General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "4",
                    "Future Generali India Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(InsuranceCompany("5", "HDFC ERGO General Insurance Co. Ltd.", false, 0))
            CompanyList.add(
                InsuranceCompany(
                    "6",
                    "ICICI Lombard General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "7",
                    "IFFCO Tokio General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "8",
                    "Liberty Videocon General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(InsuranceCompany("9", "Magma HDI General Insurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("10", "National Insurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("11", "The New India Assurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("12", "The Oriental Insurance Co. Ltd.", false, 0))
            CompanyList.add(
                InsuranceCompany(
                    "13",
                    "Raheja QBE General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(InsuranceCompany("14", "Reliance General Insurance Co. Ltd.", false, 0))
            CompanyList.add(
                InsuranceCompany(
                    "15",
                    "Royal Sundaram Alliance Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(InsuranceCompany("16", "SBI General Insurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("17", "Shriram General Insurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("18", "Tata AIG General Insurance Co. Ltd.", false, 0))
            CompanyList.add(InsuranceCompany("19", "United India Insurance Co. Ltd.", false, 0))
            CompanyList.add(
                InsuranceCompany(
                    "20",
                    "Universal Sompo General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            CompanyList.add(
                InsuranceCompany(
                    "21",
                    "Kotak Mahindra General Insurance Co. Ltd.",
                    false,
                    0
                )
            )
            return CompanyList


        }
        //endregion

        //Secure List
        @JvmStatic
        fun getSecureList(type : String): List<DashProductEntity> {
            val productList = ArrayList<DashProductEntity>()

            //adding some dummy data to the list
            if(type.toUpperCase().equals("Y")) {
                productList.add(DashProductEntity(1, "401", R.drawable.cover, "Wallet/ATM/Key Cover"))
                productList.add(DashProductEntity(2, "402", R.drawable.rsa, "Road Side Assistancer"))
            }
            productList.add(
                DashProductEntity(
                        134,
                    "10",
                    R.drawable.claim_assistance,
                    "Claim Assistance in Case of Hospitalization"
                )
            )
            productList.add(
                DashProductEntity(
                    140, "14",
                    R.drawable.expert_review,
                    "Expert Review of Current Health Policy"
                )
            )
            productList.add(
                DashProductEntity(
                    132, "15",
                    R.drawable.nominee_change,
                    "Nominee Change for Existing Policy"
                )
            )

            return productList
        }

        //Assure List
        @JvmStatic
        fun getAssureList(type : String): List<DashProductEntity> {
            val productList = ArrayList<DashProductEntity>()

            //adding some dummy data to the list
            if(type.toUpperCase().equals("Y")) {
                productList.add(DashProductEntity(11, "501", R.drawable.pit_stop, "Pit Stop"))
                productList.add(DashProductEntity(10, "500", R.drawable.finpeace, "FinPeace"))
            }

            productList.add(DashProductEntity(131, "11", R.drawable.puc, "PUC Expiry Renewal Reminder"))
            productList.add(DashProductEntity(132, "13", R.drawable.ncb, "No Claim Bonus (NCB) Transfer"))

            return productList
        }


        //RcBook List
        @JvmStatic
        fun getRtoRcBookAssistList(): List<DashProductEntity> {
            val productList = ArrayList<DashProductEntity>()

            //adding some dummy data to the list
            productList.add(DashProductEntity(145, "1.2", R.drawable.rc_book, "RC Book for New Private Car"))
            productList.add(DashProductEntity(
                    146, "1.3",
                    R.drawable.rc_renewal,
                    "RC Book Renewal for Existing Private Car"
                )
            )
            productList.add(
                DashProductEntity(
                    143, "1.1",
                    R.drawable.duplicate_rc_book,
                    "Duplicate RC Book for Existing Private Car"
                )
            )
            ///
            productList.add(
                DashProductEntity(
                    154, "3.1",
                    R.drawable.addition_hp,
                    "Addition of Hypothecation in RC Book"
                )
            )

            productList.add(
                DashProductEntity(
                    155, "3.2",
                    R.drawable.termonation_hp,
                    "Termination of Hypothecation in RC Book"
                )
            )
            productList.add(
                DashProductEntity(
                    157, "4.1",
                    R.drawable.transfer_ownership,
                    "Transfer of Ownership of Private Car"
                )
            )



            return productList
        }

        @JvmStatic
        fun getRtoDrivingLicAssistList(): List<DashProductEntity> {
            val productList = ArrayList<DashProductEntity>()

            //adding some dummy data to the list
            productList.add(
                DashProductEntity(
                    149, "2.1",
                    R.drawable.dl_assistance,
                    "New Learner Driving License"
                )
            )
            productList.add(
                DashProductEntity(
                    150,
                    "2.2",
                    R.drawable.dl_renewal,
                    "Renewal Driving License"
                )
            )
            productList.add(
                DashProductEntity(
                    151, "2.3",
                    R.drawable.duplicate_dl,
                    "Duplicate Driving License"
                )
            )
            productList.add(
                DashProductEntity(
                    152, "2.4",
                    R.drawable.dl_correction,
                    "Correction Driving License"
                )
            )

            productList.add(
                DashProductEntity(
                    159, "5",
                    R.drawable.dl_verification,
                    "Verification of Driving License"
                )
            )
            productList.add(
                DashProductEntity(
                        161, "7",
                    R.drawable.paper_to_smartcard,
                    "Paper to Smart Card Driving License"
                )
            )


            return productList
        }
    }
}