package com.infinity.interactive.scanqr.generateqr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infinity.interactive.scanqr.generateqr.data.preference.GenerateModel
import com.infinity.interactive.scanqr.generateqr.databinding.GenerateRecyclerBinding

class GenerateAdapter(
    private val context: Context,
    private val generateModelList: List<GenerateModel>
) : RecyclerView.Adapter<GenerateAdapter.GenerateViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenerateViewHolder {
        val binding =
            GenerateRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenerateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenerateViewHolder, position: Int) {
        holder.bind(generateModelList[position])
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int {
        return generateModelList.size
    }

    inner class GenerateViewHolder(private val binding: GenerateRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(generateModel: GenerateModel) {

            binding.generateTxtImg.setImageResource(generateModel.img_icon)
            binding.generateText.text = generateModel.category_name
            binding.anotherRecycler.setOnClickListener {
                clickListener?.onItemClicked(layoutPosition)
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(position: Int)
    }
}

