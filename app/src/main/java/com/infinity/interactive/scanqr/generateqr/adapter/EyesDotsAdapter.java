package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;


public class EyesDotsAdapter extends RecyclerView.Adapter<EyesDotsAdapter.EyesDotsViewHolder> {

    Context context;
    List<Integer> socialList;

    private ClickListener clickListener;


    @NonNull
    @Override
    public EyesDotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eyes_dots_item, parent, false);
        return new EyesDotsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(EyesDotsViewHolder holder, int position) {

        holder.Image.setImageResource(socialList.get(position));


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }





    public class EyesDotsViewHolder extends RecyclerView.ViewHolder{

        ImageView Image;
        CardView cardView;

        public EyesDotsViewHolder(View itemView,int viewType) {
            super(itemView);


            Image= itemView.findViewById(R.id.itemImg);
            cardView= itemView.findViewById(R.id.eyesDotsRel);

            cardView.setOnClickListener(v -> {
                if (clickListener != null) {

                    clickListener.onItemClicked(getLayoutPosition());

                }
            });

        }
    }

    public interface ClickListener {

        void onItemClicked(int position);

    }



    public EyesDotsAdapter(Context context, List<Integer> socialList) {
        this.context = context;
        this.socialList = socialList;
    }


}



