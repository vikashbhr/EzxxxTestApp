package com.technovikash.ezxxxtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView

class SecondActivity : AppCompatActivity() {
    private var name: String? = null
    private var phoneNumber: String? = null
    private var city: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        intent?.let {
            name = it.getStringExtra("text_name")
            phoneNumber = it.getStringExtra("text_phone")
            city = it.getStringExtra("text_city")
        }

        val textView: AppCompatTextView = findViewById(R.id.x_text_view)
        textView.text = buildString {
            append("Your Name : $name \n ")
            append("Your Phone : $phoneNumber \n ")
            append("Your City : $city")
        }
    }
}