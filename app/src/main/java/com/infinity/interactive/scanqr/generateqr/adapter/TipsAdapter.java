package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.data.constant.SupportedModelClass;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    public int selectedPosition = -1;
    Context context;
    List<SupportedModelClass> supportedModelClassList;
    ClickListener clickListener;


    public TipsAdapter(Context context, List<SupportedModelClass> backgroundImagesList) {
        this.context = context;
        this.supportedModelClassList = backgroundImagesList;
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NotNull
    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supported_layout, parent, false);
        return new TipsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, int position) {


        holder.supportedText.setText(supportedModelClassList.get(position).getSupportText());
        if(position==0){
            holder.supported_qr_img.setRotation(90);
            holder.supported_qr_img.setImageResource(supportedModelClassList.get(position).getQrImg());
        }
        else if(position==2){
            holder.supported_qr_img.setRotation(45);
            holder.supported_qr_img.setImageResource(supportedModelClassList.get(position).getQrImg());
        }
        else{
            holder.supported_qr_img.setImageResource(supportedModelClassList.get(position).getQrImg());
        }
        

        holder.correct_qr_img.setImageResource(supportedModelClassList.get(position).getSupportImg());

    }


    @Override
    public int getItemCount() {
        return supportedModelClassList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public interface ClickListener {

        void onItemClicked(int position);

    }

    public static class TipsViewHolder extends RecyclerView.ViewHolder {


        ImageView supported_qr_img, correct_qr_img;
        TextView supportedText;

        public TipsViewHolder(View itemView, int viewType) {
            super(itemView);

            supported_qr_img = itemView.findViewById(R.id.qr_img);
            correct_qr_img = itemView.findViewById(R.id.correct_qr);
            supportedText = itemView.findViewById(R.id.qr_text);

        }
    }
}
