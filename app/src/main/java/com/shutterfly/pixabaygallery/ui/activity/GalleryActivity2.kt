package com.shutterfly.pixabaygallery.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shutterfly.pixabaygallery.adapters.GalleryAdapter
import com.shutterfly.pixabaygallery.databinding.ActivityGalleryBinding
import com.shutterfly.pixabaygallery.repositories.GalleryRepository
import com.shutterfly.pixabaygallery.ui.gallery_list.viewmodel.GalleryViewModel
import com.shutterfly.pixabaygallery.ui.gallery_list.viewmodel.GalleryViewModelFactory
import kotlinx.coroutines.launch

class GalleryActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding

    private val viewModel by viewModels<GalleryViewModel> {
        GalleryViewModelFactory(GalleryRepository())
    }

    private val galleryAdapter = GalleryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
        setupObservers()
    }

    private fun setupUI() {
        // disable search button since we start with an empty search text
        binding.searchButton.isEnabled = false
        // setup toolbar
        setSupportActionBar(binding.toolbar)
        // setup recycler view related stuff
        with(binding.recycler) {
            this.adapter = galleryAdapter
            this.setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.searchButton.setOnClickListener {
            val searchTerm = binding.searchText.text.toString()
//            viewModel.onSearchButtonClicked(searchTerm)
        }
    }

    private fun setupObservers() {
        // observe for search input changes so we can enable/disable search button when it's empty
        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.searchButton.isEnabled = s.isNotBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // observe for new images data
        viewModel.imageListObservable.observe(this) { data ->
            lifecycleScope.launch {
                galleryAdapter.submitData(data)
            }
        }
    }
}