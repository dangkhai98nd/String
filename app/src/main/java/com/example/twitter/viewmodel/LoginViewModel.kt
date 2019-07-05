package com.example.twitter.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitter.FeedActivity
import com.example.twitter.api.Client
import com.example.twitter.api.Service
import com.example.twitter.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val mContext: Context,
    private val fcm_token: String?
) : ViewModel() {

    val ldUsename = MutableLiveData<String>()
    val ldPassword = MutableLiveData<String>()

    fun onClickLogin() {
        try {
            Log.e("username", ldUsename.value)
            Log.e("password", ldPassword.value)
            Log.e("fcm_token", fcm_token)
            val service: Service? = Client.getClient()?.create(Service::class.java)
            val requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", "${ldUsename.value}")
                .addFormDataPart("password", "${ldPassword.value}")
                .addFormDataPart("fcm_token", "$fcm_token").build()
            val call: Call<User>? = service?.getApiUser(requestBody)

            call?.run {
                enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("faild ", "${ldUsename.value}")
                        onClickLogin()
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Log.e("success", "done")
                        val user: User? = response.body()
                        if (user?.status == true) {
                            Log.e("access_token_main", response.body()?.data?.access_token)
                            val intent = Intent(mContext, FeedActivity::class.java)
                            intent.putExtra("access_token", response.body()?.data?.access_token)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            mContext.startActivity(intent)
                        } else {
                            Toast.makeText(mContext, "login faild", Toast.LENGTH_SHORT).show()
                            Log.e("Login", "faild")
                        }
                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
    }


}