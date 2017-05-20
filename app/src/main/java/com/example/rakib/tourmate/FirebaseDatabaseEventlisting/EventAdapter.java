package com.example.rakib.tourmate.FirebaseDatabaseEventlisting;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakib.tourmate.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Masum on 19-May-17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private ArrayList<EventModel> list;

    public EventAdapter(ArrayList<EventModel> list) {
        this.list = list;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false));
    }
    EventModel event;
    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {



            event=list.get(position);


                holder.eventPlacesName.setText(event.eventPlacesName);
                holder.customFromDate.setText(event.customFromDate);
                holder.customToDate.setText(event.customToDate);
                holder.customBudget.setText(event.customBudget);

                holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(holder.getAdapterPosition(), 0, 0, "Update");
                        menu.add(holder.getAdapterPosition(), 1, 0, "Delete");

                    }
                });


    }

    @Override
    public int getItemCount() {

        return list.size()-list.size()/2;

    }




    class EventViewHolder extends RecyclerView.ViewHolder{
        private TextView eventPlacesName,customFromDate,customToDate,customBudget;
        public EventViewHolder(View itemView) {
            super(itemView);

            eventPlacesName= (TextView) itemView.findViewById(R.id.eventPlacesName);
            customFromDate= (TextView) itemView.findViewById(R.id.customFromDate);
            customToDate= (TextView) itemView.findViewById(R.id.customToDate);
            customBudget= (TextView) itemView.findViewById(R.id.customBudget);

        }
    }
}
