package com.example.ososlimited;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;
import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<MediaFile> adaptermedia = new ArrayList<>();
    private ArrayList<ArrayList<MediaFile>> tryingmedia = new ArrayList<>();
    private FileListAdapter fileListAdapter;
    private Context mcontext;


    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data,ArrayList<ArrayList<MediaFile>> newmedia ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.tryingmedia=newmedia;
        this.mcontext=context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item1, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        if (tryingmedia!=null){

            try {
                adaptermedia= tryingmedia.get(position);

                LinearLayoutManager mLayoutManager;
                GridLayoutManager gridLayoutManager;
                mLayoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);

                gridLayoutManager = new GridLayoutManager(mcontext, 2, GridLayoutManager.HORIZONTAL, false);
                holder.recyclerView3.setLayoutManager(gridLayoutManager);;
                holder.recyclerView3.setItemAnimator(new DefaultItemAnimator());

                fileListAdapter = new FileListAdapter(adaptermedia);
                holder.recyclerView3.setAdapter(fileListAdapter);


            }catch (Exception e){

            }

        }





    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        RecyclerView recyclerView3;


        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item_title);
            recyclerView3=(RecyclerView)itemView.findViewById(R.id.inside_recycular);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private void setMediaFiles(List<MediaFile> mediaFiles) {
        this.adaptermedia.clear();
        this.adaptermedia.addAll(mediaFiles);
        fileListAdapter.notifyDataSetChanged();
    }

}