package com.example.walmart_challenge_qv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walmart_challenge_qv.adapter.CountriesAdapter
import com.example.walmart_challenge_qv.databinding.FragmentListBinding
import com.example.walmart_challenge_qv.utils.ResponseState

/**
 *Recycler View fragment that shows a scrollable list of countries with
 * some other information
 */
class ListFragment : BaseFragment(){

    private val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        CountriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.countriesRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = mAdapter
        }

        countriesViewModel.countries.observe(viewLifecycleOwner){ state ->
            when (state){
                is ResponseState.LOADING -> {
                    Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
                    binding.countriesRecycler.visibility = View.GONE
                    binding.countryProgress.visibility = View.VISIBLE
                }
                is ResponseState.SUCCESS -> {
                    binding.countriesRecycler.visibility = View.VISIBLE
                    binding.countryProgress.visibility = View.GONE
                    Toast.makeText(context, "SHOWING COUNTRIES...", Toast.LENGTH_LONG).show()
                    mAdapter.addCountries(state.countries)
                }
                is ResponseState.ERROR -> {
                    binding.countriesRecycler.visibility = View.GONE
                    binding.countryProgress.visibility = View.GONE

                    state.error.localizedMessage?.let {
                        errorBox(message = it) {
                            countriesViewModel.getAllCountries()
                        }
                    }
                }
            }
        }

        countriesViewModel.getAllCountries()

        // Inflate the layout for this fragment
        return binding.root
    }
}