package com.technovikash.ezxxxtestapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.*
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.technovikash.mynetwork.CustomUiResponse
import com.technovikash.mynetwork.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var customUiResponse: CustomUiResponse? = null
    private var customUIView: LinearLayoutCompat? = null

    private var buttonClickListener = View.OnClickListener {
        val bundle = Bundle()
        if (it.tag == "Submit") {
            customUiResponse?.uidata?.forEach { uiData ->
                if (uiData?.uitype == EDIT_TEXT) {
                    val editText: AppCompatEditText? = customUIView?.findViewWithTag(uiData.key)
                    bundle.putString(uiData.key, editText?.text.toString())
                }
            }
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    companion object {
        const val BASE_URL = "https://demo.ezetap.com"
        const val LABEL = "label"
        const val EDIT_TEXT = "edittext"
        const val BUTTON = "button"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customUIView = findViewById(R.id.linear_layout)
        val retrofitInstance = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Network::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.fetchCustomUI("/mobileapps/android_assignment.json")
            customUiResponse = result.body()
            withContext(Dispatchers.Main) {
                val logoView = findViewById<AppCompatImageView>(R.id.x_logo_image)
                val headingView = findViewById<AppCompatTextView>(R.id.x_heading)

                Glide.with(this@MainActivity)
                    .load(customUiResponse?.logoUrl)
                    .into(logoView)
                headingView.text = customUiResponse?.headingText

                customUiResponse?.uidata?.forEach {
                    when (it?.uitype) {
                        LABEL -> {
                            addView(getLabel(it.value ?: "", it.key ?: ""))
                        }
                        EDIT_TEXT -> {
                            addView(getEditText(it.hint ?: "", it.key ?: ""))
                        }
                        BUTTON -> {
                            addView(getButton(it.value ?: "", it.value ?: ""))
                        }
                    }
                }

            }
        }

    }

    private fun addView(view: View) = customUIView?.addView(view)

    private fun getLabel(text: String, tag: String): View {
        val layoutParam =
            LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val label = AppCompatTextView(this)
        label.layoutParams = layoutParam
        label.tag = tag
        label.text = text
        return label
    }

    private fun getEditText(hint: String, tag: String): AppCompatEditText {
        val layoutParam =
            LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val editText = AppCompatEditText(this)
        editText.layoutParams = layoutParam
        editText.tag = tag
        editText.hint = hint
        return editText
    }

    private fun getButton(text: String, tag: String): AppCompatButton {
        val layoutParam =
            LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val button = AppCompatButton(this)
        button.layoutParams = layoutParam
        button.tag = tag
        button.text = text
        button.setOnClickListener(buttonClickListener)
        return button
    }
}