package com.example.globofly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.globofly.Interface.JsonPLaceHolderApi
import com.example.globofly.Model.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.VISIBLE

        getDataFromApi()

    }

    private fun getDataFromApi(){

        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var jsonPLaceHolderApi: JsonPLaceHolderApi = retrofit.create(JsonPLaceHolderApi::class.java)
        var call: Call<List<User>> = jsonPLaceHolderApi.getUserInformation()

        call.enqueue(object : Callback<List<User>> {

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                txtShowDataEndPoint.text = "Failure: ${t.message}"
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (!response.isSuccessful) {
                    txtShowDataEndPoint.text = "Service Error Code: ${response.code()}"
                    return
                }

                progressBar.visibility = View.GONE

                var responseData: List<User> = response.body()!!
                var outputString: String = "";

                for (data in responseData) {

                    outputString += "userId: ${data.userId}\n"
                    outputString += "id: ${data.id}\n"
                    outputString += "title: ${data.title}\n"
                    outputString += "body: ${data.body}\n\n"

                    txtShowDataEndPoint.append(outputString)
                }
            }
        })


    }
}
