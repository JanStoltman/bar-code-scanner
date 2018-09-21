package com.yggdralisk.myapplication.scanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.barcode.Barcode
import com.yggdralisk.myapplication.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_scanner.*
import org.jetbrains.anko.toast
import timber.log.Timber

class ScannerActivity : AppCompatActivity() {
    companion object {
        const val EAN_EXTRA = "EAN_EXTRA"
        const val CAMERA_REQUEST_CODE = 1
    }

    private var scannerDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
    }

    override fun onStart() {
        super.onStart()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            setupScanner()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    private fun setupScanner() {
        scannerDisposable = barcodeView
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setActivityResultAndFinish(it)
                }, {
                    logAndShowError(it)
                })
    }

    private fun setActivityResultAndFinish(barcode: Barcode) {
        this.setResult(Activity.RESULT_OK, Intent().putExtra(EAN_EXTRA, barcode.rawValue))
        this.finish()
    }

    override fun onBackPressed() {
        this.setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun logAndShowError(throwable: Throwable?) {
        Timber.d(throwable)
        toast(R.string.generic_error)
    }

    override fun onStop() {
        super.onStop()
        scannerDisposable?.dispose()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    setupScanner()
                } else {
                    onBackPressed()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
