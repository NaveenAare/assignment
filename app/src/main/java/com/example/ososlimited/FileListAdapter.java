package com.example.ososlimited;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaiselrahman.filepicker.model.MediaFile;


import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private ArrayList<MediaFile> mediaFiles;

    public FileListAdapter(ArrayList<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaFile mediaFile = mediaFiles.get(position);
        Context context = holder.itemView.getContext();

        if (mediaFile.getMediaType() == MediaFile.TYPE_IMAGE
                || mediaFile.getMediaType() == MediaFile.TYPE_VIDEO) {
            Glide.with(context)
                    .load(mediaFile.getUri())
                    .into(holder.fileThumbnail);
        } else if (mediaFile.getMediaType() == MediaFile.TYPE_AUDIO) {
            Glide.with(context)
                    .load(mediaFile.getThumbnail())
                    .placeholder(R.drawable.ic_audio)
                    .into(holder.fileThumbnail);
        } else {
            holder.fileThumbnail.setImageResource(R.drawable.ic_file);
        }
    }

    @Override
    public int getItemCount() {
        return mediaFiles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fileThumbnail;
        private TextView filePath, fileSize, fileMime, fileBucketName;

        ViewHolder(View view) {
            super(view);
            fileThumbnail = view.findViewById(R.id.inside_image);

        }
    }
}