package com.example.sport_app.Activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.sport_app.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnTakePhoto: Button
    private lateinit var btnOpenGallery: Button

    private var currentPhotoPath: String? = null
    private var photoURI: Uri? = null

    companion object {
        private const val REQUEST_CAMERA_PERMISSION_CODE = 100
        private const val REQUEST_GALLERY_PERMISSION_CODE = 101
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            photoURI?.let { uri ->
                imageView.setImageURI(uri)
                Toast.makeText(this, "Picture saved to: $currentPhotoPath", Toast.LENGTH_LONG).show()
                returnUriToCallingActivity(uri)
            }
        } else {
            Toast.makeText(this, "Taking picture failed or cancelled", Toast.LENGTH_SHORT).show()
            currentPhotoPath?.let { path ->
                val photoFile = File(path)
                if (photoFile.exists()) {
                    photoFile.delete()
                }
            }
            photoURI = null
            setResult(Activity.RESULT_CANCELED)
        }
    }

    private val openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                imageView.setImageURI(uri)
                photoURI = uri
                Toast.makeText(this, "Gallery picture selected", Toast.LENGTH_SHORT).show()
                returnUriToCallingActivity(uri)
            }
        } else {
            Toast.makeText(this, "Picture selection canceled", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
        }
    }

    private fun returnUriToCallingActivity(imageUri: Uri) {
        val resultIntent = Intent()
        resultIntent.putExtra("imageUri", imageUri.toString())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (storageDir != null && !storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        try {
            val photoFile: File = createImageFile()
            val uriForFile: Uri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                photoFile
            )
            photoURI = uriForFile
            takePictureLauncher.launch(uriForFile)
        } catch (ex: IOException) {
            photoURI = null
            Toast.makeText(this, "Error creating image file: ${ex.message}", Toast.LENGTH_LONG).show()
            ex.printStackTrace()
            setResult(Activity.RESULT_CANCELED)
            // No finalices la actividad aquí
        }
    }

    private fun dispatchOpenGalleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openGalleryLauncher.launch(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageView = findViewById(R.id.iv_camera)
        btnTakePhoto = findViewById(R.id.btn_TakePhoto)
        btnOpenGallery = findViewById(R.id.btn_OpenGalerry)

        btnTakePhoto.setOnClickListener {
            if (checkCameraPermission()) {
                dispatchTakePictureIntent()
            } else {
                requestCameraPermission()
            }
        }

        btnOpenGallery.setOnClickListener {
            if (checkGalleryPermission()) {
                dispatchOpenGalleryIntent()
            } else {
                requestGalleryPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_CANCELED)
                }
                return
            }
            REQUEST_GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gallery permission granted", Toast.LENGTH_SHORT).show()
                    dispatchOpenGalleryIntent()
                } else {
                    Toast.makeText(this, "Gallery permission denied", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_CANCELED)
                }
                return
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION_CODE
        )
    }

    private fun checkGalleryPermission(): Boolean {
        val permissionToRead = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return ContextCompat.checkSelfPermission(
            this,
            permissionToRead
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        requestPermissions(
            arrayOf(permissionToRequest),
            REQUEST_GALLERY_PERMISSION_CODE
        )
    }
}