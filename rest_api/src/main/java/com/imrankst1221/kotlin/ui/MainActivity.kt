package com.imrankst1221.kotlin.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.imrankst1221.kotlin.api.ApiService
import com.imrankst1221.kotlin.Constants
import com.imrankst1221.kotlin.api.R
import com.imrankst1221.kotlin.UtilMethods
import com.imrankst1221.kotlin.api.request.LoginPostData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this

        // login post request
        loginApiCall("user-001", "pas1221")

        // user get request

    }


    // login post request
    @SuppressLint("CheckResult")
    private fun loginApiCall(userID: String, userPassword: String){
        if(UtilMethods.isConnectedToInternet(mContext)){
            UtilMethods.showLoading(mContext)
            val observable = ApiService.loginApiCall().doLogin(LoginPostData(userID, userPassword))
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ loginResponse ->
                        UtilMethods.hideLoading()

                        /** loginResponse is response data class*/

                    }, { error ->
                        UtilMethods.hideLoading()
                        UtilMethods.showLongToast(mContext, error.message.toString())
                    }
                    )
        }else{
            UtilMethods.showLongToast(mContext, "No Internet Connection!")
        }
    }

    // user info get request
    @SuppressLint("CheckResult")
    private fun userApiCall(userID: Int){
        if(UtilMethods.isConnectedToInternet(mContext)){
            UtilMethods.showLoading(mContext)
            val observable = ApiService.userApiCall().getUser(Constants.API_AUTHORIZATION_KEY, userID)
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ userResponse ->
                        UtilMethods.hideLoading()

                        /** userResponse is response data class*/

                    }, { error ->
                        UtilMethods.hideLoading()
                        UtilMethods.showLongToast(mContext, error.message.toString())
                    }
                    )
        }else{
            UtilMethods.showLongToast(mContext, "No Internet Connection!")
        }
    }
}
