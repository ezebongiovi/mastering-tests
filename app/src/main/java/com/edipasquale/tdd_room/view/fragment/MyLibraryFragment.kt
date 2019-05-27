package com.edipasquale.tdd_room.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.edipasquale.tdd_room.view.adapter.LibraryListAdapter
import com.edipasquale.tdd_room.view.adapter.MainPageAdapter
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.RoomApplication
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.factory.ImageViewModelFactory
import com.edipasquale.tdd_room.repository.ImageRepository
import com.edipasquale.tdd_room.util.MediaStoreUtil
import com.edipasquale.tdd_room.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_my_library.view.*
import javax.inject.Inject


class MyLibraryFragment : IdlerFragment(), MainPageAdapter.SearchListener,
    LibraryListAdapter.ImageActionListener {

    @Inject
    lateinit var repository: ImageRepository

    private lateinit var mViewModel: ImageViewModel
    private lateinit var mAdapter: LibraryListAdapter

    override fun onSearch(query: String) {
        mViewModel.getLibrary(query)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setIdling(true)

        (context!!.applicationContext as RoomApplication).getRoomComponent().inject(this)

        mAdapter = LibraryListAdapter(this)
        mViewModel = ViewModelProviders.of(this, ImageViewModelFactory(repository))
            .get(ImageViewModel::class.java)

        mViewModel.libraryModel.observe(this, Observer { model ->
            mAdapter.submitList(model.pictures)
            setIdling(true)
        })
    }

    override fun onResume() {
        super.onResume()

        setIdling(false)
        mViewModel.getLibrary(null)
    }

    override fun onPause() {
        super.onPause()
        setIdling(true)
    }

    override fun onDestroy() {
        setIdling(true)
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_library, container, false)

        view.recyclerView.adapter = mAdapter
        view.recyclerView.layoutManager = GridLayoutManager(context, 2)

        return view
    }

    override fun deleteImage(image: Image) {
        mViewModel.deleteFromLibrary(image)
    }

    override fun shareImage(image: Image) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(
                context!!,
                context!!.packageName,
                MediaStoreUtil.getImageFile(context!!, image)
            )
        )
        startActivity(Intent.createChooser(intent, "Share with..."))
    }
}