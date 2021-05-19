package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.Version


data class VersionCodeResponse(
    var Version: Version?
) : APIResponse()