package com.sp.madproject.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.madproject.R;

import java.util.ArrayList;

public class VolunAvailableEventAdapter extends RecyclerView.Adapter<VolunAvailableEventAdapter.MyViewHolder> {

    Context context;
    ArrayList<VolunAvailableEvent> VolunAvailableEventArrayList;

    public VolunAvailableEventAdapter(Context context, ArrayList<VolunAvailableEvent> volunAvailableEventArrayList) {
        this.context = context;
        this.VolunAvailableEventArrayList = volunAvailableEventArrayList;
    }

    @NonNull
    @Override
    public VolunAvailableEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.volunteer_event_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunAvailableEventAdapter.MyViewHolder holder, int position) {
        VolunAvailableEvent volunAvailableEvent = VolunAvailableEventArrayList.get(position);

        holder.volunEventName.setText(volunAvailableEvent.event_title);
        holder.volunEventDesc.setText(volunAvailableEvent.event_desc);
        holder.volunEventLoc.setText(volunAvailableEvent.event_location);
    }

    @Override
    public int getItemCount() {
        return VolunAvailableEventArrayList.size();
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
        }
    }
}
