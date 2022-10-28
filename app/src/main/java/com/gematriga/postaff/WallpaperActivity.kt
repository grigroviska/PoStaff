package com.gematriga.postaff

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gematriga.postaff.databinding.ActivityWallpaperBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

                //Burası tamamlanacak :)

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
    private fun downloadImage(){

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("File")
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