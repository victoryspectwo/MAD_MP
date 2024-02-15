package com.sp.madproject.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.sp.madproject.R;

import java.util.ArrayList;

public class VolunAvailableEventAdapter extends RecyclerView.Adapter<VolunAvailableEventAdapter.MyViewHolder> {
    private Context context;
    private static ArrayList<VolunAvailableEvent> volunAvailableEventArrayList;
    private static OnItemClickListener listener;

    // Define the OnItemClickListener interface
    public interface OnItemClickListener {
        void onItemClick(VolunAvailableEvent item);
    }

    // Constructor
    public VolunAvailableEventAdapter(Context context, ArrayList<VolunAvailableEvent> volunAvailableEventArrayList, OnItemClickListener listener) {
        this.context = context;
        VolunAvailableEventAdapter.volunAvailableEventArrayList = volunAvailableEventArrayList;
        VolunAvailableEventAdapter.listener = listener;
    }

    @NonNull
    @Override
    public VolunAvailableEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.volunteer_event_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunAvailableEventAdapter.MyViewHolder holder, int position) {
        VolunAvailableEvent volunAvailableEvent = volunAvailableEventArrayList.get(position);

        holder.volunEventName.setText(volunAvailableEvent.event_title);
        holder.volunEventDesc.setText(volunAvailableEvent.event_desc);
        holder.volunEventLoc.setText(volunAvailableEvent.event_location);

        // Load image using Glide
        Glide.with(context)
                .load(volunAvailableEvent.event_img)
                .apply(new RequestOptions().placeholder(R.drawable.organiser_icon))
                .into(holder.volunIcon);
    }

    @Override
    public int getItemCount() {
        return volunAvailableEventArrayList != null ? volunAvailableEventArrayList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView volunEventName, volunEventDesc, volunEventLoc;
        ImageView volunIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            volunEventName = itemView.findViewById(R.id.volunEventName);
            volunEventDesc = itemView.findViewById(R.id.volunEventDescription);
            volunEventLoc = itemView.findViewById(R.id.volunEventLoc);
            volunIcon = itemView.findViewById(R.id.volun_icon_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(volunAvailableEventArrayList.get(position));
                    }
                }
            });
        }
    }
}
