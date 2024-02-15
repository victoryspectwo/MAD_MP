package com.sp.madproject.organiser;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.madproject.R;
import com.sp.madproject.organiser.OrgEvents;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

    Context context;
    ArrayList<OrgEvents> eventArrayList;

    public EventAdapter(Context context, ArrayList<OrgEvents> eventArrayList){
        this.context = context;
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public EventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.events_row, parent, false);

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventHolder holder, int position) {

        OrgEvents events = eventArrayList.get(position);

        holder.eventTitle.setText(events.event_title);
        holder.eventLoc.setText(events.event_location);
        holder.eventDesc.setText(events.event_desc);
        holder.icon.setImageResource(R.drawable.icon_addphoto);

        /*
        if (events.getEvent_img() != null && !events.getEvent_img().isEmpty()) {
            // Load the image URI into the ImageView
            holder.icon.setImageURI(Uri.parse(events.getEvent_img()));
        } else {
            // If the image URI is null or empty, set a placeholder image or handle it as desired
            holder.icon.setImageResource(R.drawable.icon_addphoto); // Placeholder image resource
        }*/

    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }
    public class EventHolder extends RecyclerView.ViewHolder{

        TextView eventTitle, eventDesc, eventLoc;
        ImageView icon;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon_row);
            eventTitle = itemView.findViewById(R.id.eventName);
            eventDesc = itemView.findViewById(R.id.eventDescription);
            eventLoc = itemView.findViewById(R.id.eventLoc);
        }


    }
}