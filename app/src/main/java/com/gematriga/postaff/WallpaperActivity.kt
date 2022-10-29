package com.gematriga.postaff

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.gematriga.postaff.databinding.ActivityWallpaperBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWallpaperBinding
    private var imageUri : Uri? = null
    lateinit var dialog: BottomSheetDialog
    lateinit var sheet : View
    var url : String? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)


        url = intent.getStringExtra("URL")

        Glide.with(this).load(url).into(binding.photoView)


        binding.floatingButton.setOnClickListener {

            dialog = BottomSheetDialog(this)
            sheet = layoutInflater.inflate(R.layout.bottom_sheet,null)
            dialog.setContentView(sheet)
            dialog.show()

            sheet.findViewById<LinearLayout>(R.id.apply_wallpaper).setOnClickListener {

                setWallpaper("Home")

            }

            sheet.findViewById<LinearLayout>(R.id.apply_lockscreen).setOnClickListener {

                setWallpaper("LockScreen")

            }

            sheet.findViewById<LinearLayout>(R.id.both_wallpaper).setOnClickListener {

                setWallpaper("BothScreen")

            }

            sheet.findViewById<LinearLayout>(R.id.save_layout).setOnClickListener {

                downloadImage()

            }

            sheet.findViewById<LinearLayout>(R.id.share_Layout).setOnClickListener {

                shareImage()

            }




            /*val bottomSheetDialog = BottomSheetDialog(
                this@WallpaperActivity,
                R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet)
            )

            bottomSheetView.findViewById<LinearLayout>(R.id.apply_wallpaper).setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {

                    val result: Bitmap = Picasso.get().load(url).get()

                    val wallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)

                    try {

                        wallpaperManager.setBitmap(result)

                        this@WallpaperActivity.runOnUiThread(Runnable {
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Wallpaper Changed Succesfully",
                                Toast.LENGTH_LONG
                            ).show()
                        })

                    } catch (_: IOException) {


                    }
                }

                bottomSheetDialog.dismiss()

            }
            bottomSheetView.findViewById<LinearLayout>(R.id.close_layout).setOnClickListener {

                bottomSheetDialog.dismiss()

            }
            bottomSheetView.findViewById<LinearLayout>(R.id.share_Layout).setOnClickListener {

                val intent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_STREAM, url)
                    this.type = "image/jpeg"
                }
                startActivity(Intent.createChooser(intent, resources.getText(R.string.image_share)))
            }
            bottomSheetView.findViewById<LinearLayout>(R.id.apply_lockscreen).setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {

                    val result: Bitmap = Picasso.get().load(url).get()

                    val wallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)

                    try {

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_LOCK)
                            this@WallpaperActivity.runOnUiThread(Runnable {
                                Toast.makeText(
                                    this@WallpaperActivity,
                                    "Lockscreen Changed Succesfully",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                            bottomSheetDialog.dismiss()
                        }else{
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Olmadı be",
                                Toast.LENGTH_LONG
                            ).show()
                        }



                    } catch (_: IOException) {


                    }
                }
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.both_wallpaper).setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {

                    val result: Bitmap = Picasso.get().load(url).get()

                    val wallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)

                    try {

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_LOCK)
                            wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_SYSTEM)
                            this@WallpaperActivity.runOnUiThread(Runnable {
                                Toast.makeText(
                                    this@WallpaperActivity,
                                    "Both Screen Changed Succesfully",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                            bottomSheetDialog.dismiss()
                        }else{
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Olmadı be",
                                Toast.LENGTH_LONG
                            ).show()
                        }



                    } catch (_: IOException) {


                    }
                }

            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()*/
        }
    }

    @SuppressLint("SetWorldReadable")
    fun shareImage() {

        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(10) { alphabet.random() }.joinToString("")

        val drawable: Drawable = binding.photoView.getDrawable()
        val bitmap = (drawable as BitmapDrawable).bitmap

        try {
            val file = File(
                applicationContext.externalCacheDir,
                File.separator + "poStaff_$randomString.jpeg"
            )
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val photoURI = FileProvider.getUriForFile(
                applicationContext,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
            intent.putExtra(Intent.EXTRA_STREAM, photoURI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/jpeg"
            startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dialog.dismiss()

    }

    private fun downloadImage(){

        val fN = url.toString()
        Toast.makeText(this@WallpaperActivity,fN.toString(),Toast.LENGTH_LONG).show()

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("PexelsImage")
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)

        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        dm.enqueue(request)

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setWallpaper(choose : String) {



        val url = intent.getStringExtra("URL")


        GlobalScope.launch(Dispatchers.IO) {

            val result: Bitmap = Picasso.get().load(url).get()

            val wallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)

            try {

                if (choose == "Home") {

                    wallpaperManager.setBitmap(result)
                    this@WallpaperActivity.runOnUiThread(Runnable {
                        Toast.makeText(
                            this@WallpaperActivity,
                            "HomeScreen Changed Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                } else if (choose == "LockScreen") {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_LOCK)
                        this@WallpaperActivity.runOnUiThread(Runnable {
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Lockscreen Changed Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    }

                }else if (choose == "BothScreen") {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        wallpaperManager.setBitmap(result)
                        wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_LOCK)
                        this@WallpaperActivity.runOnUiThread(Runnable {
                            Toast.makeText(
                                this@WallpaperActivity,
                                "Changed on two screens.",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                    }

                }

                dialog.dismiss()

            }catch (io: IOException) {

                Toast.makeText(this@WallpaperActivity, io.localizedMessage, Toast.LENGTH_LONG).show()

            }
        }
    }
}