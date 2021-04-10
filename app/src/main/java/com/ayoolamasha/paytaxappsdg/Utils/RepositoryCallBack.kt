package com.ayoolamasha.paytaxappsdg.Utils

import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpError

interface RepositoryCallBack<T : Any> {
    fun onComplete(apiResult: ApiResult.Error<SignUpError>)
}