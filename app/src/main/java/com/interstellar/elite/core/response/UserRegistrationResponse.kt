package com.interstellar.elite.core.response

import com.interstellar.elite.core.APIResponse
import com.interstellar.elite.core.model.LoginEntity
import com.interstellar.elite.core.model.RegistrationResponseEntity

class UserRegistrationResponse (
  val Data: List<RegistrationResponseEntity>
  ):APIResponse()