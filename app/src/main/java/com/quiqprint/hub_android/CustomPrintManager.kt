package com.quiqprint.hub_android

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.*
import android.print.pdf.PrintedPdfDocument
import java.io.FileOutputStream
import java.io.IOException


class CustomPrintManager(context: Context) {

    init {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        // Set job name, which will be displayed in the print queue
        val jobName = "${context.getString(R.string.app_name)} Document"
        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document

        val bitmap1 = BitmapFactory.decodeResource(
            context.resources,
            com.google.android.material.R.drawable.abc_ic_clear_material
        )
        val bitmap2 = BitmapFactory.decodeResource(
            context.resources,
            com.google.android.material.R.drawable.abc_ab_share_pack_mtrl_alpha
        )

        printManager.print(jobName, ViewPrintAdapter(context, listOf(bitmap1, bitmap2), 0), null)
    }


}

class ViewPrintAdapter(
    var context: Context,
    private val bitmapList: List<Bitmap>,
    private var indexOfBitmap: Int
) :
    PrintDocumentAdapter() {
    var myPdfDocument: PdfDocument? = null
    var totalPages = 0
    private var pageHeight = 0
    private var pageWidth = 0
    override fun onLayout(
        oldAttributes: PrintAttributes,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal,
        callback: LayoutResultCallback,
        extras: Bundle
    ) {
        myPdfDocument = PrintedPdfDocument(context, newAttributes)
        totalPages = bitmapList.size / 4
        pageHeight = newAttributes.mediaSize!!.heightMils / 1000 * 72
        pageWidth = newAttributes.mediaSize!!.widthMils / 1000 * 72
        if (cancellationSignal.isCanceled) {
            callback.onLayoutCancelled()
            return
        }
        if (totalPages > 0) {
            val builder = PrintDocumentInfo.Builder("print_output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(totalPages)
            indexOfBitmap = 0
            val info = builder.build()
            callback.onLayoutFinished(info, true)
        } else {
            callback.onLayoutFailed("Page count is zero.")
        }
    }

    override fun onWrite(
        pages: Array<PageRange>,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal,
        callback: WriteResultCallback
    ) {
        for (i in 0 until totalPages) {
            if (pageInRange(pages, i)) {
                val newPage = PageInfo.Builder(
                    pageWidth,
                    pageHeight, i
                ).create()
                val page = myPdfDocument!!.startPage(newPage)
                if (cancellationSignal.isCanceled) {
                    callback.onWriteCancelled()
                    myPdfDocument!!.close()
                    myPdfDocument = null
                    return
                }
                drawPage(page, i)
                myPdfDocument!!.finishPage(page)
            }
        }
        try {
            myPdfDocument!!.writeTo(
                FileOutputStream(
                    destination.fileDescriptor
                )
            )
        } catch (e: IOException) {
            callback.onWriteFailed(e.toString())
            return
        } finally {
            myPdfDocument!!.close()
            myPdfDocument = null
        }
        callback.onWriteFinished(pages)
    }

    private fun pageInRange(pageRanges: Array<PageRange>, page: Int): Boolean {
        for (i in pageRanges.indices) {
            if (page >= pageRanges[i].start &&
                page <= pageRanges[i].end
            ) return true
        }
        return false
    }

    private fun drawPage(page: PdfDocument.Page, pageNum: Int) {
        var pageNumber = pageNum
        val canvas: Canvas = page.canvas
        pageNumber++ // Make sure page numbers start at 1
        val titleBaseLine = 72
        val leftMargin = 54
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 40f
        val paint1 = Paint()
        paint1.isAntiAlias = true
        paint1.isFilterBitmap = true
        paint1.isDither = true
        //canvas.drawText( "Test Print Document Page " + pageNumber,leftMargin,titleBaseLine,paint);
        paint.textSize = 14f
        //canvas.drawText("This is some test content to verify that custom document printing works", leftMargin, titleBaseLine + 35, paint);
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.barcode_template);
//        // Define an offset value between canvas and bitmap
//        int offset = 50;
//
//        // Initialize a new Bitmap to hold the source bitmap
//        Bitmap dstBitmap = Bitmap.createBitmap(
//                bitmap.getWidth() + offset * 2, // Width
//                bitmap.getHeight() + offset * 2, // Height
//                Bitmap.Config.ARGB_8888 // Config
//        );
//
//        // Initialize a new Canvas instance
//        Canvas canvas1 = new Canvas(dstBitmap);
//        canvas1.drawBitmap(
//                bitmap, // Bitmap
//                offset, // Left
//                offset, // Top
//                null // Paint
//        );
        if (pageNumber % 2 == 0) paint.color = Color.RED else paint.color = Color.GREEN
        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            com.google.android.material.R.drawable.avd_show_password
        )
        val bitmap1 = bitmapList[indexOfBitmap]
        val bitmap2 = bitmapList[indexOfBitmap + 1]
        val bitmap3 = bitmapList[indexOfBitmap + 2]
        val bitmap4 = bitmapList[indexOfBitmap + 3]
        indexOfBitmap += 4
        val pageInfo = page.info
        //        canvas.drawBitmap(getResizedBitmap(bitm,700), (canvas.getWidth() - bitm.getWidth()) / 2, (canvas.getHeight() - bitm.getHeight()) / 2, paint1);
//        canvas.drawBitmap(bitmapList.get(0), 0, 0, paint1);
        canvas.drawBitmap(getResizedBitmap(bitmap, 800), 0f, 0f, null)
        canvas.drawBitmap(getResizedBitmap(bitmap1, 135), 77f, 144f, paint1)
        canvas.drawBitmap(getResizedBitmap(bitmap2, 125), 370f, 550f, paint1)
        canvas.drawBitmap(getResizedBitmap(bitmap3, 125), 370f, 144f, paint1)
        canvas.drawBitmap(getResizedBitmap(bitmap4, 135), 77f, 550f, paint1)
        //        indexOfBitmap+=4;
        //canvas.drawCircle(pageInfo.getPageWidth() / 2, pageInfo.getPageHeight() / 2, 150, paint);
    }

    companion object {
        fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }
    }
}