package com.example.twitter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.twitter.api.Client
import com.example.twitter.api.Service
import com.example.twitter.databinding.ActivityMainBinding
import com.example.twitter.model.User
import com.example.twitter.viewmodel.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var fcm_token: String? = null
    private var loginViewModel: LoginViewModel? = null
    private var mActivityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("failed", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                fcm_token = task.result?.token
//                Log.e("fcm_token", fcm_token)
                initBinding()
            })


//        btnLogin.setOnClickListener {
//            loadJSON()
//            Log.e("sdfsdf","Sdfsdf")
//        }
    }

    private fun initBinding() {
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loginViewModel = LoginViewModel(this@MainActivity, fcm_token)
        loginViewModel?.ldUsename?.value = "dangkhai98nd@gmail.com"
        loginViewModel?.ldPassword?.value = "123123"
        mActivityMainBinding.apply {
            this?.lifecycleOwner = this@MainActivity
            this?.loginViewModel = loginViewModel
        }

//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
//        loginViewModel?.edtUsename?.value = "dangkhai98nd@gmail.com"
//        DataBindingUtil.setContentView<ActivityMainBinding>(
//            this, R.layout.activity_main
//        ).apply {
//            this.lifecycleOwner = this@MainActivity
//            this.loginViewModel = loginViewModel
//        }
    }

    private fun loadJSON() {
        try {

            val service: Service? = Client.getClient()?.create(Service::class.java)
            val requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", "${edtUsername.text}")
                .addFormDataPart("password", "${edtPassword.text}")
                .addFormDataPart("fcm_token", "$fcm_token").build()
            val call: Call<User>? = service?.getApiUser(requestBody)

            call?.run {
                enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("failed ", "${edtUsername.text}")
                        loadJSON()
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Log.e("success", "done")
                        val user: User? = response.body()
                        if (user?.status == true) {
                            Log.e("access_token_main", response.body()?.data?.access_token)
                            val intent = Intent(this@MainActivity, FeedActivity::class.java)
                            intent.putExtra("access_token", response.body()?.data?.access_token)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            this@MainActivity.startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "login faild", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
    }


}
