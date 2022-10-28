package com.gematriga.postaff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gematriga.postaff.Adapters.WallAdapter
import com.gematriga.postaff.Models.Photo
import com.gematriga.postaff.Repository.WallpaperRepository
import com.gematriga.postaff.ViewModelFactory.ViewModel
import com.gematriga.postaff.ViewModelFactory.WallpaperViewModelFactory
import com.gematriga.postaff.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var viewModel : ViewModel
    lateinit var wallAdapter : WallAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.getWallpaper(newText.toString())

                return true
            }

        })

        val repository = WallpaperRepository()

        val viewmodelfactory = WallpaperViewModelFactory(repository)

        viewModel = ViewModelProvider(this,viewmodelfactory).get(ViewModel::class.java)

        setupRecyclerView()

        viewModel.wallpaperList.observe(this, Observer {

            if (it.isSuccessful){
                val response = it.body()

                if (response != null){

                    wallAdapter.setWallpaperData(response.photos as ArrayList<Photo>,this)

                }
            }
        })
    }

    private fun setupRecyclerView() {

        wallAdapter = WallAdapter()

        binding.recyclerView.apply {

            adapter = wallAdapter
            layoutManager = GridLayoutManager(this@MainActivity,2)

        }

    }

}