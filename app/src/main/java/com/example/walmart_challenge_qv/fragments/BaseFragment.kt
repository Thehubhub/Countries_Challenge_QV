package com.example.walmart_challenge_qv.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.walmart_challenge_qv.R
import com.example.walmart_challenge_qv.adapter.CountriesAdapter
import com.example.walmart_challenge_qv.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    protected val countriesViewModel: CountriesViewModel by viewModels()

    fun errorBox(
        message: String = "Trying to resolve issue...",
        retry: () -> Unit){
            AlertDialog.Builder(requireContext())
                .setTitle("ERROR HAS OCCURRED.")
                .setPositiveButton("Retry"){
                    dialog, _ -> dialog.dismiss()
                    retry()
                }
                .setNegativeButton("Dismiss"){
                    dialog, _ -> dialog.dismiss()
                }
                .setMessage(message)
                .create()
                .show()
        }

}