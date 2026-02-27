package com.infinity.interactive.scanqr.generateqr.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.adapter.BarcodeAdapter.BarcodeViewHolder
import com.infinity.interactive.scanqr.generateqr.data.preference.GenerateModel

class BarcodeAdapter(var context: Context, var generateModelList: List<GenerateModel>) :
    RecyclerView.Adapter<BarcodeViewHolder?>() {
    private var clickListener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.barcode_recycler, parent, false)
        return BarcodeViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: BarcodeViewHolder, position: Int) {
        holder.generateImageView.setImageResource(generateModelList[position].img_icon)
        holder.generateTextView.text = generateModelList[position].category_name
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int {
        return generateModelList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    inner class BarcodeViewHolder(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var generateRelative: RelativeLayout
        var generateImageView: ImageView
        var generateTextView: TextView

        init {
            generateRelative = itemView.findViewById(R.id.anotherRecycler)
            generateImageView = itemView.findViewById(R.id.generate_txt_img)
            generateTextView = itemView.findViewById(R.id.generate_text)
            generateRelative.setOnClickListener { v: View? ->
                if (clickListener != null) {
                    clickListener!!.onItemClicked(layoutPosition)
                }
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(position: Int)
    }
}
