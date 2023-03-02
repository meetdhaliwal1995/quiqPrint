package com.quiqprint.hub_android

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.quiqprint.hub_android.databinding.ActivityMainBinding
import com.quiqprint.hub_android.di.NetworkModule
import com.quiqprint.hub_android.di.SharedPreference
import com.quiqprint.hub_android.repo.PrintRepository
import java.io.File


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding

    var printRepository: PrintRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val sharedPreference = SharedPreference(this)
        val getpath = sharedPreference.singleImageData
        printRepository = PrintRepository(NetworkModule.myApi())

        Log.e("getpathStiring", getpath.toString())


        val file = File(getpath.toString())

        CustomPrintManager(this)

//        val pdfBitmap = pdfToBitmap(file)

//        binding?.ivPath?.setImageBitmap(pdfBitmap)


//        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//        binding?.ivPath?.setImageBitmap(bitmap)

//        CoroutineScope(Dispatchers.IO).launch {
//            printRepository?.getCommandData("63ee30fa4a2b443717b1e4aa")?.collectLatest {
//
//            }
//        }
    }

    fun observable() {

    }

    fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("Tokenn", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get the FCM token
            val token = task.result

            // Do something with the token
            Log.d("Tokennnnn", "FCM registration token: $token")
        })
    }


    fun pdfToBitmap(file: File): Bitmap? {
        // Create a parcel file descriptor to read the PDF file
        val pfd: ParcelFileDescriptor =
            ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        // Create a PdfRenderer object to render the PDF pages as images
        val renderer = PdfRenderer(pfd)

        // Get the first page of the PDF
        val page = renderer.openPage(0)

        // Create a bitmap object to store the PDF page as an image
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

        // Render the PDF page as an image and store it in the bitmap object
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        // Close the page and the PdfRenderer object
        page.close()
        renderer.close()

        // Return the bitmap image
        return bitmap
    }

}