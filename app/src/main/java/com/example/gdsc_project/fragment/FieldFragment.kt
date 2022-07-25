package com.example.gdsc_project.fragment

import android.os.Bundle
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
import com.example.gdsc_project.adapter.policy
import com.example.gdsc_project.databinding.FragmentFieldBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item.view.*

class FieldFragment : Fragment(){
    private var _binding: FragmentFieldBinding? = null
    var firestore: FirebaseFirestore? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

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

        recyclerView.adapter = RecyclerViewAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)




    }


    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // policy 클래스 ArrayList 생성성
        var po: ArrayList<policy> = arrayListOf()


        init {  // po의 문서를 불러온 뒤 policy으로 변환해 ArrayList에 담음

            //setFragmentResultListener("requestKey") { requestKey, bundle ->

                //val result = bundle.getString("bundleKey")
                firestore?.collection("po")?.whereEqualTo("지원분야", "주거금융")?.get()
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            for (snapshot in it.result!!.documents) {
                                var item = snapshot.toObject(policy::class.java)
                                po.add(item!!)
                            }
                            notifyDataSetChanged()


                    }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView


            viewHolder.name.text= po[position].정책명
            viewHolder.field.text = po[position].지원분야


        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return po.size
        }

    }
}