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


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>{

    Context context;
    List<Integer> generateColorList;
    public int selectedPosition  = -1;

    private ClickListener clickListener;


    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_recycler, parent, false);
        return new ColorViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {

       holder.colorCard.setCardBackgroundColor(generateColorList.get(position));

       if(selectedPosition == position){
            // do whatever you want to do to make it selected.
            holder.colorTick.setVisibility(View.VISIBLE);
        }
        else{
            holder.colorTick.setVisibility(View.GONE);
        }


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return generateColorList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ColorViewHolder extends RecyclerView.ViewHolder{

        CardView colorCard;
        ImageView colorTick;

        public ColorViewHolder(View itemView,int viewType) {
            super(itemView);


            colorCard=itemView.findViewById(R.id.color_card_1);
            colorTick=itemView.findViewById(R.id.color_tick);

            colorCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getLayoutPosition();
                    notifyDataSetChanged();
                    if (clickListener != null) {
                        clickListener.onItemClicked(getLayoutPosition());
                    }
                }
            });

        }
    }

    public interface ClickListener {

        void onItemClicked(int position);

    }

    public ColorAdapter(Context context, List<Integer> generateColorList) {
        this.context = context;
        this.generateColorList = generateColorList;
    }
}
