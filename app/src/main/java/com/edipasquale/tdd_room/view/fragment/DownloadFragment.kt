package com.edipasquale.tdd_room.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.RoomApplication
import com.edipasquale.tdd_room.databinding.FragmentDownloadBinding
import com.edipasquale.tdd_room.factory.ImageViewModelFactory
import com.edipasquale.tdd_room.model.ImageModel
import com.edipasquale.tdd_room.repository.ImageRepository
import com.edipasquale.tdd_room.view.adapter.MainPageAdapter
import com.edipasquale.tdd_room.viewmodel.ImageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_download.*
import javax.inject.Inject

class DownloadFragment : IdlerFragment(), MainPageAdapter.SearchListener {

    @Inject
    lateinit var repository: ImageRepository

    private lateinit var mBinding: FragmentDownloadBinding
    private lateinit var mViewModel: ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false)

        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context!!.applicationContext as RoomApplication).getRoomComponent().inject(this)

        mViewModel = ViewModelProviders.of(this, ImageViewModelFactory(repository))
            .get(ImageViewModel::class.java)

        mViewModel.model.observe(this, Observer { model ->
            setIdling(true)
            progressBar.hide()
            mBinding.model = model
        })

        mViewModel.error.observe(this, Observer { errorModel ->
            setIdling(true)
            progressBar.hide()
            Snackbar.make(
                rootView,
                errorModel.getErrorMessage(context!!)!!,
                Snackbar.LENGTH_INDEFINITE
            )
                .show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        setIdling(true)
    }

    override fun onSearch(query: String) {
        setIdling(false)
        progressBar.show()
        mViewModel.downloadImage(query)
    }

    @BindingAdapter("imageBitmap")
    fun loadImage(iv: ImageView, model: ImageModel) {
        iv.setImageBitmap(model.image?.picture)
    }
}