package com.yggdralisk.myapplication.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.yggdralisk.myapplication.R
import com.yggdralisk.myapplication.data.model.ApiResponse
import com.yggdralisk.myapplication.result.ResultActivity
import com.yggdralisk.myapplication.scanner.ScannerActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    companion object {
        const val SCANNER_REQUEST_CODE = 1
        const val RESULT_REQUEST_CODE = 2
        const val RESULT_RETAKE_SCAN = 11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
    }

    private fun setupListeners() {
        cameraButton.setOnClickListener {
            openScannerActivity()
        }

        confirmButton.setOnClickListener {
            validateAndPostEanCode()
        }
    }

    private fun validateAndPostEanCode() {
        if (eanCode.text.isNotBlank()) {
            postEanCode(eanCode.text.toString().trim())
        } else {
            eanCode.error = getString(R.string.ean_code_empty_error)
        }
    }

    private fun postEanCode(eanCode: String) {
        AndroidNetworking.get("http://:8080/call")
                .addQueryParameter("t", "1")
                .addQueryParameter("s1", eanCode)
                .build()
                .getAsObject(ApiResponse::class.java, object : ParsedRequestListener<ApiResponse> {
                    override fun onResponse(response: ApiResponse) {
                        Timber.d(response.toString())
                        handleResponse(response)
                    }

                    override fun onError(error: ANError) {
                        logAndShowError(error)
                    }
                })
    }

    private fun handleResponse(response: ApiResponse) {
        if (response.success) {
            startResultActivity(response)
        } else {
            logAndShowError(Throwable(response.error))
        }
    }

    private fun startResultActivity(response: ApiResponse) {
        startActivityForResult(
                Intent(this, ResultActivity::class.java)
                        .putExtra(ResultActivity.RESPONSE_EXTRA, response.data.first().toTypedArray())
                        .putExtra(ResultActivity.CODE_EXTRA, eanCode.text.toString())
                , RESULT_REQUEST_CODE)
    }

    private fun logAndShowError(throwable: Throwable?) {
        Timber.d(throwable)
        toast(R.string.generic_error)
    }

    private fun openScannerActivity() {
        startActivityForResult(Intent(this, ScannerActivity::class.java), SCANNER_REQUEST_CODE)
    }

    private fun retrieveAndSetResult(data: Intent?) {
        data?.let {
            eanCode.setText(data.getStringExtra(ScannerActivity.EAN_EXTRA))
            validateAndPostEanCode()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SCANNER_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    retrieveAndSetResult(data)
                }
            }

            RESULT_REQUEST_CODE -> {
                if (resultCode == RESULT_RETAKE_SCAN) {
                    openScannerActivity()
                }
            }

            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
