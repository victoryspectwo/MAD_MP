package com.sp.madproject.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sp.madproject.R;

import java.util.ArrayList;

public class VolunActiveEventAdapter extends RecyclerView.Adapter<VolunActiveEventAdapter.MyViewHolder2> {
    Context context;
    ArrayList<VolunActiveEvent> volunActiveEventArrayList;

    public VolunActiveEventAdapter(Context context, ArrayList<VolunActiveEvent> volunActiveEventArrayList, VolunteerEventFragment volunteerEventFragment) {
        this.context = context;
        this.volunActiveEventArrayList = volunActiveEventArrayList;
    }

    @NonNull
    @Override
    public VolunActiveEventAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.volunteer_event_row, parent, false);

        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunActiveEventAdapter.MyViewHolder2 holder, int position) {
        VolunActiveEvent volunActiveEvent = volunActiveEventArrayList.get(position);

        holder.volunEventName.setText(volunActiveEvent.event_title);
        holder.volunEventDesc.setText(volunActiveEvent.event_desc);
        holder.volunEventLoc.setText(volunActiveEvent.event_location);

        // Load image using Glide
        Glide.with(context)
                .load(volunActiveEvent.event_img)
                .apply(new RequestOptions().placeholder(R.drawable.organiser_icon))
                .into(holder.volunIcon);

    }

    @Override
    public int getItemCount() {
        return volunActiveEventArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView volunEventName, volunEventDesc, volunEventLoc;
        ImageView volunIcon;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            volunEventName = itemView.findViewById(R.id.volunEventName);
            volunEventDesc = itemView.findViewById(R.id.volunEventDescription);
            volunEventLoc = itemView.findViewById(R.id.volunEventLoc);
            volunIcon = itemView.findViewById(R.id.volun_icon_row);
        }
    }
}
