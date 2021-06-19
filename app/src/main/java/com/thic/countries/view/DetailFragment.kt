package com.thic.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thic.countries.R
import com.thic.countries.utils.ImageUploader
import com.thic.countries.utils.ImageUploaderI
import com.thic.countries.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewmodel:DetailViewModel
    private lateinit var imageUpload:ImageUploaderI

    private var countryUuid:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUpload = ImageUploader()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailFragmentArgs.fromBundle(it).countryUuid
            Toast.makeText(requireContext(),"$countryUuid",Toast.LENGTH_SHORT).show()
        }

        viewmodel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        observeLiveData()
        viewmodel.getDataFromRoom(countryUuid)



    }

    private fun observeLiveData(){
        viewmodel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                countryNamePub.text = it.countryName
                countryRegionPub.text = it.countryRegion
                countryCurrencyPub.text = it.countryCurrency
                countryLanguagePub.text = it.countryLanguage
                imageUpload.imageUploadWithGlide(requireContext(),countryFlagImagePub,it.countryFlag)
            }
        })
    }
}