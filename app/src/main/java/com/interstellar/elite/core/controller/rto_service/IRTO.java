package com.interstellar.elite.core.controller.rto_service;


import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.requestmodel.Rto.AdditionHypothecationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AddressEndorsementRCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.AssistanceObtainingRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.DriverDLVerificationRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.PaperToSmartCardRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.RCRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.TransferOwnershipRequestEntity;
import com.interstellar.elite.core.requestmodel.Rto.VehicleRegCertificateRequestEntity;

/**
 * Created by Rahul  on 14-12-2018.
 */

public interface IRTO {

    void saveRCService1(RCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAdditionHypothecation(AdditionHypothecationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveTransferOwnership(TransferOwnershipRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveDriverDLVerification(DriverDLVerificationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAddressEndorsementRC(AddressEndorsementRCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void savePaperSmartCard(PaperToSmartCardRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveVehicleRegCertificate(VehicleRegCertificateRequestEntity entity, IResponseSubcriber iResponseSubcriber);


}
