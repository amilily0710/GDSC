package com.example.gdsc_project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gdsc_project.R
import com.example.gdsc_project.fragment.NewsFragmentDirections
import com.example.gdsc_project.model.Policy
import com.example.gdsc_project.model.Select
import com.example.gdsc_project.model.User
import com.google.protobuf.FieldType
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val dataset:ArrayList<Select>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val textTitle: TextView = view.findViewById(R.id.item_title)
        val textCount: TextView = view.findViewById(R.id.item_count)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return NewsViewHolder(layout)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val item = dataset[position]

        holder.textTitle.text = item.supportAreas.toString()
        holder.textCount.text = item.count.toString()

        holder.itemView.setOnClickListener {
            val action =
                NewsFragmentDirections
                    .actionNavigationHomeToFieldFragment(
                        fieldType = holder.textTitle.text.toString(),
                        age = item.age.toString(),
                        location = item.location.toString()
                    )
            it.findNavController().navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}