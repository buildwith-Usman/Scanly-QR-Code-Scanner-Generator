package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;


public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.ColorViewHolder> {

    public int selectedPosition = -1;
    Context context;
    List<Integer> generateLogoList;

    private ClickListener clickListener;

    public LogoAdapter(Context context, List<Integer> generateColorList) {
        this.context = context;
        this.generateLogoList = generateColorList;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logo_recycler, parent, false);
        return new ColorViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {

        holder.logoSelect.setImageResource(generateLogoList.get(position));


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return generateLogoList.size();
    }



    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface ClickListener {

        void onItemClicked(int position);

    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout logoRel;
        ImageView logoSelect;


        public ColorViewHolder(View itemView, int viewType) {
            super(itemView);


            logoRel = itemView.findViewById(R.id.logo_rel_1);
            logoSelect = itemView.findViewById(R.id.default_logo);

            logoRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getLayoutPosition();
                    notifyItemChanged(getLayoutPosition());
                    if (clickListener != null) {
                        clickListener.onItemClicked(getLayoutPosition());
                    }
                }
            });

        }
    }
}
