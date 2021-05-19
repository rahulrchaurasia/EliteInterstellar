package com.interstellar.elite.core

interface IResponseSubcriber {

    abstract fun OnSuccess(apiResponse: APIResponse, message: String)

    abstract fun OnFailure(error: String)

}