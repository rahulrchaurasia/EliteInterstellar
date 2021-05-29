package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.EliteActivationCodeResult

data class ActivationCodeLandmarkResponse(
    val EliteActivationCodeResult: EliteActivationCodeResult
):APIResponse()