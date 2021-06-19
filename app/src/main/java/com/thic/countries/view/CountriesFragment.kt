package com.thic.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.thic.countries.view.CountriesFragmentDirections
import com.thic.countries.R
import com.thic.countries.adapter.CountryAdapter
import com.thic.countries.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.fragment_countries.*

class CountriesFragment : Fragment() {

    private lateinit var viewModel: CountriesViewModel
    private lateinit var adapter : CountryAdapter
    var num:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CountryAdapter(arrayListOf(),requireActivity().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        viewModel.refreshData()

        main_recyclerView.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        main_recyclerView.adapter = adapter

        main_swipe_refresh.setOnRefreshListener {
            main_recyclerView.visibility = View.GONE
            main_error.visibility = View.GONE
            main_progress.visibility = View.VISIBLE
            viewModel.refreshData()
            main_swipe_refresh.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                main_progress.visibility = View.GONE
                main_recyclerView.visibility = View.VISIBLE
                main_error.visibility = View.GONE
                adapter.updateAdapter(it)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    main_error.visibility = View.VISIBLE
                    main_recyclerView.visibility = View.GONE
                }
                else main_error.visibility = View.GONE
                main_recyclerView.visibility = View.VISIBLE
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    main_progress.visibility = View.VISIBLE
                    main_recyclerView.visibility = View.GONE
                    main_error.visibility = View.GONE
                }
                else{
                    main_recyclerView.visibility = View.VISIBLE
                    main_progress.visibility = View.GONE
                }
            }
        })
    }
}
