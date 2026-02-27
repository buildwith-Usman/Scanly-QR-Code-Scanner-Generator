package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils;
import com.infinity.interactive.scanqr.generateqr.utility.ResultOfTypeAndValue;


/**
 * Created by Bezruk on 16/10/18.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    final Context mContext;
    private final ArrayList<String> mList;
    private final ArrayList<String> mDateList;
    String reformattedStr;
    String currentDate;
    private ClickListener clickListener;

    public HistoryAdapter(Context context, ArrayList<String> list, ArrayList<String> dateList) {
        this.mContext = context;
        this.mList = list;
        this.mDateList = dateList;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {

        return position;
//            return arraylist.get(position).getViewType();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String result = mList.get(position);
        String resultDate = mDateList.get(position);


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());
        currentDate = formatter.format((date));


        SimpleDateFormat fromUser = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {

            reformattedStr = myFormat.format(fromUser.parse(resultDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // int type = AppUtils.getResourceType(result);
        ResultOfTypeAndValue resultValues = AppUtils.getResourceType(result);
        String finalResult = resultValues.getValue();
        String[] titleOfResult = finalResult.split("\n");
        if (titleOfResult[0].length() > 26) {
            finalResult = titleOfResult[0].substring(0, 25) + "...";
        } else {
            finalResult = titleOfResult[0];
        }


        if (resultValues.getType() == Constants.TYPE_TEXT) {
            holder.actionIcon.setImageResource(R.drawable.text_scanner);
        } else if (resultValues.getType() == Constants.TYPE_WEB) {
            holder.actionIcon.setImageResource(R.drawable.url_scanner);
        } else if (resultValues.getType() == Constants.TYPE_YOUTUBE) {
//            holder.actionIcon.setImageResource(R.drawable.ic_video);
        } else if (resultValues.getType() == Constants.TYPE_PHONE) {
            holder.actionIcon.setImageResource(R.drawable.call_scanner);
        } else if (resultValues.getType() == Constants.TYPE_EMAIL) {
            holder.actionIcon.setImageResource(R.drawable.email_scanner);
        } else if (resultValues.getType() == Constants.TYPE_BARCODE) {
            holder.actionIcon.setImageResource(R.drawable.ic_barcode_icon);
        } else if (resultValues.getType() == Constants.TYPE_WIFI) {
            holder.actionIcon.setImageResource(R.drawable.wifi_scanner);
        } else if (resultValues.getType() == Constants.TYPE_SMS) {
            holder.actionIcon.setImageResource(R.drawable.sms_scanner);
        } else if (resultValues.getType() == Constants.TYPE_VCARD) {
            holder.actionIcon.setImageResource(R.drawable.contacts_scanner);
        } else if (resultValues.getType() == Constants.TYPE_GEO) {
            holder.actionIcon.setImageResource(R.drawable.location_scanner);
        } else if (resultValues.getType() == Constants.TYPE_FACEBOOK) {
            holder.actionIcon.setImageResource(R.drawable.facebook_icon);
        } else if (resultValues.getType() == Constants.TYPE_TWITTER) {
            holder.actionIcon.setImageResource(R.drawable.twitter_icon);
        } else if (resultValues.getType() == Constants.TYPE_INSTAGRAM) {
            holder.actionIcon.setImageResource(R.drawable.instagram_icon);
        } else if (resultValues.getType() == Constants.TYPE_LINKDEIN) {
            holder.actionIcon.setImageResource(R.drawable.linkdein_icon);
        } else if (resultValues.getType() == Constants.TYPE_WHATSAPP) {
            holder.actionIcon.setImageResource(R.drawable.whatsapp_icon);
        } else {
            holder.actionIcon.setImageResource(R.drawable.text_scanner);
        }

        holder.result.setText(finalResult);
        holder.date.setText(reformattedStr);

        if (reformattedStr.contains(currentDate)) {
            holder.dateCurrent.setText("Today");
        } else {
            String[] onlyDate = reformattedStr.split(" ");
            holder.dateCurrent.setText(onlyDate[0]);
        }


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListener {
        void onDeleteClicked(int position);

        void onItemClicked(int position);

        void onItemLongClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageButton deleteButton;
        final LinearLayout item_history_layout;
        private final TextView result;
        private final TextView date;
        private final ImageView actionIcon;
        private final TextView dateCurrent;

        public ViewHolder(View v, int viewType) {
            super(v);
            result = v.findViewById(R.id.result);
            date = v.findViewById(R.id.date);
            deleteButton = v.findViewById(R.id.deleteButton);
            actionIcon = v.findViewById(R.id.actionIcon);
            item_history_layout = v.findViewById(R.id.item_history_layout);
            dateCurrent = v.findViewById(R.id.showDate);

            item_history_layout.setOnClickListener(v1 -> {
                if (clickListener != null) {
                    clickListener.onItemClicked(getLayoutPosition());
                }
            });

            deleteButton.setOnClickListener(v13 -> {
                if (clickListener != null) {
                    clickListener.onDeleteClicked(getLayoutPosition());
                }
            });

            item_history_layout.setOnLongClickListener(v12 -> {
                if (clickListener != null) {
                    clickListener.onItemLongClicked(getLayoutPosition());
                }
                return false;
            });
        }

    }
}