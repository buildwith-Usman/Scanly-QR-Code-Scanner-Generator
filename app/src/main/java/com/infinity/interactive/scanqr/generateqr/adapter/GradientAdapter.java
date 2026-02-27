package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;


public class GradientAdapter extends RecyclerView.Adapter<GradientAdapter.GradientViewHolder> {
    
    Context context;
    List<Integer> gradientColorList;
    public int selectedPosition  = -1;

    private ClickListener clickListener;


    @NonNull
    @Override
    public GradientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gradient_recycler, parent, false);
        return new GradientViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(GradientViewHolder holder, int position) {



        Drawable drawable= ResourcesCompat.getDrawable(context.getResources(), gradientColorList.get(position), context.getTheme());
        holder.gradientColor.setBackground(drawable);


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
        return gradientColorList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class GradientViewHolder extends RecyclerView.ViewHolder{

        CardView colorCard;
        RelativeLayout gradientColor;
        ImageView colorTick;

        public GradientViewHolder(View itemView,int viewType) {
            super(itemView);


            colorCard=itemView.findViewById(R.id.color_card_1);
            colorTick=itemView.findViewById(R.id.color_tick);
            gradientColor=itemView.findViewById(R.id.gradient);

            colorCard.setOnClickListener(v -> {
                selectedPosition = getLayoutPosition();
                notifyDataSetChanged();

                if (clickListener != null) {
                    clickListener.onItemClicked(getLayoutPosition());
                }
            });

        }
    }

    public interface ClickListener {

        void onItemClicked(int position);

    }

    public GradientAdapter(Context context, List<Integer> generateColorList) {
        this.context = context;
        this.gradientColorList = generateColorList;
    }
}
