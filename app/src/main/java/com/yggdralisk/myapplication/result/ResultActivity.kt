package com.yggdralisk.myapplication.result

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yggdralisk.myapplication.R
import com.yggdralisk.myapplication.data.model.DataModel
import com.yggdralisk.myapplication.main.MainActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    companion object {
        const val RESPONSE_EXTRA = "RESPONSE_EXTRA"
        const val CODE_EXTRA = "CODE_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setupListeners()
        retrieveAndSetData()
    }

    private fun setupListeners() {
        cameraButton.setOnClickListener{
            setResult(MainActivity.RESULT_RETAKE_SCAN)
            this.finish()
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun retrieveAndSetData() {
        retrieveAndSetDataModels()
        retrieveAndSetCode()
    }

    private fun retrieveAndSetDataModels() {
        if (intent.hasExtra(RESPONSE_EXTRA)) {
            val response = intent.getParcelableArrayExtra(RESPONSE_EXTRA)
            setData(response.map { o -> o as DataModel }.toTypedArray())
        }
    }

    private fun retrieveAndSetCode() {
        if (intent.hasExtra(CODE_EXTRA)) {
            setEanCodeAsToolbarTitle(intent.getStringExtra(CODE_EXTRA))
        }
    }

    private fun setEanCodeAsToolbarTitle(code: String?) {
        code?.let {
            this@ResultActivity.title = code
        }
    }

    private fun setData(data: Array<DataModel>?) {
        data?.let {
            dataRecycler.layoutManager = LinearLayoutManager(this)
            dataRecycler.adapter = DataResultAdapter(it)
        }
    }
}
