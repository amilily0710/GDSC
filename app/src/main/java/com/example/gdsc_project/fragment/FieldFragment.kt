package com.example.gdsc_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gdsc_project.R
import com.example.gdsc_project.adapter.FieldAdapter
import com.example.gdsc_project.adapter.policy
import com.example.gdsc_project.databinding.FragmentFieldBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.item.view.*

class FieldFragment : Fragment(){
    private var _binding: FragmentFieldBinding? = null
    var firestore: FirebaseFirestore? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!
    private val args by navArgs<FieldFragmentArgs>()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFieldBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        recyclerView = binding.recyclerview
        val po: ArrayList<policy> = arrayListOf()

        val result_ = args.fieldType // 내가 선택한 지원분야 변수 받아오는 것
        val location = args.location // 내가 선택했던 지역
        val age = args.age  // 내가 썼던 나이
        firestore?.collection("po")?.whereEqualTo("지원분야", result_)?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    for (snapshot in it.result!!.documents) {
                        val item = snapshot.toObject(policy::class.java)
                        val recieve_loc = item?.지역.toString()
                        val receive_age = item?.지원규모.toString()

                        if (recieve_loc.contains(args.location.toString())) {
                            if (receive_age != "제한없음") {
                                if (receive_age.length <= 4) {
                                    if (age.toString() == receive_age.substring(1, 3)) {
                                        po.add(item!!)
                                    }
                                } else {
                                    if (receive_age.substring(5, 7) == "이상") {
                                        if (age?.toInt()!! >= receive_age.substring(1, 3).toInt()) {
                                            po.add(item!!)
                                        }
                                    } else {
                                        if (age!!.toInt() in receive_age.substring(1, 3)
                                                .toInt()..receive_age.substring(5, 7).toInt()
                                        ) {
                                            po.add(item!!)
                                        }
                                    }
                                }

                            }
                            if(receive_age =="제한없음"){
                                po.add(item!!)
                            }

                            Log.d("나이", item?.지원규모.toString())
                            Log.d("정책명", item?.지역.toString())
                            Log.d("지원분야", item?.지원분야.toString())
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }



                recyclerView.adapter = FieldAdapter(po)
                recyclerView.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            }

    }
}

