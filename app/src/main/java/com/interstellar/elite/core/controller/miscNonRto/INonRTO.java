package com.interstellar.elite.core.controller.miscNonRto;


import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.requestmodel.miscNonRto.ClaimAssistanceHospitalizationRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.BeyondLifeFinancialRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ClaimGuidanceHospRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ComplimentaryCreditReportRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ComplimentaryLoanAuditRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ExpertReviewOfCurrentHealthPolicyRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.LifeInsurancePolicyNomineeRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.MiscReminderPUCRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProductPriceRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.ProvideClaimAssRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.SpecialBenefitsRequestEntity;
import com.interstellar.elite.core.requestmodel.miscNonRto.TransferBenefitsNCBRequestEntity;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public interface INonRTO {

    void saveProvideClaimAssistance(ProvideClaimAssRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveClaimGuidanceHosp(ClaimGuidanceHospRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveMiscRemiderPUC(MiscReminderPUCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveSpecialBenifits(SpecialBenefitsRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAnalysisCurrentHealth(ExpertReviewOfCurrentHealthPolicyRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveLifeInsurancePolicyNominee(LifeInsurancePolicyNomineeRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveBeyondLifeFinancial(BeyondLifeFinancialRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveComplimentaryCreditReport(ComplimentaryCreditReportRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveComplimentaryLoanAudit(ComplimentaryLoanAuditRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveTransferNCBBenefits(TransferBenefitsNCBRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getProductTAT(ProductPriceRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getMotorInsuranceList(IResponseSubcriber iResponseSubcriber);

    void getHealthInsuranceList(IResponseSubcriber iResponseSubcriber);
}
