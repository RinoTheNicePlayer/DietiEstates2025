package com.example.dietiestates25.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dietiestates25.R
import com.example.dietiestates25.controller.S3Controller

class CreatePropertyActivity: AppCompatActivity() {
    private lateinit var pickImagesLauncher: ActivityResultLauncher<Intent>
    private lateinit var s3Controller: S3Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_property)
        s3Controller = S3Controller(this)

        pickImagesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    if (it.clipData != null) {
                        for (i in 0 until it.clipData!!.itemCount) {
                            val imageUri = it.clipData!!.getItemAt(i).uri
                            s3Controller.uploadInputStream(imageUri)
                        }
                    } else if (it.data != null) {
                        val imageUri = it.data!!
                        s3Controller.uploadInputStream(imageUri)
                    }
                }
            }
        }

        val uploadButton = findViewById<Button>(R.id.uploadButton)

        uploadButton.setOnClickListener {
            pickImagesFromGallery()
        }
    }

    private fun pickImagesFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        pickImagesLauncher.launch(Intent.createChooser(intent, "Select Images"))
    }
}