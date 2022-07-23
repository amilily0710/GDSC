package com.example.gdsc_project.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gdsc_project.R
import com.example.gdsc_project.databinding.FragmentSelectPolicyBinding
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class SelectField(
//
//    val location: String? = null,
//    val supportArea: String? = null,
////    val age : String
//) : Parcelable

class SelectPolicyFragment : Fragment() {
    private var binding: FragmentSelectPolicyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPolicyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            locationSpn.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.location,
                android.R.layout.simple_list_item_1
            )
            check.setOnClickListener {
                val action =
                    SelectPolicyFragmentDirections.actionSelectPolicyFragmentToNavigationHome(
                        location = locationSpn.selectedItem.toString(),
                        age = editAgeText.text.toString().toInt()
                    )
                findNavController().navigate(action)
            }
        }
    }
}


