package com.vishnevskiypro.startactivityforresult

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.vishnevskiypro.startactivityforresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnSetPhoto.setOnClickListener {
            openTakeImage()
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.data.let {
                val source = it?.let { it1 -> ImageDecoder.createSource(contentResolver, it1) }
                val imageBitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                binding.myImage.setImageBitmap(imageBitmap)
            }
        }
    }


    fun openTakeImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = INTENT_TYPE
        intent.resolveActivity(packageManager)?.let {
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    companion object{
        private const val INTENT_TYPE = "image/*"
        private const val REQUEST_CODE = 23
    }
}