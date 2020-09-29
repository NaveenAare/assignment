package com.example.ososlimited;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private FloatingActionButton fa;
    private String m_Text = "";
    MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<String> animalNames;
    ArrayList<ArrayList<MediaFile> >trying = new ArrayList<>();


    private final static int FILE_REQUEST_CODE =1;

    private String name;

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();




    private ImageView im1;


    private int id;
    Button button;
    private FileListAdapter fileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        animalNames = new ArrayList<>();

        trying = new ArrayList<>();

        fa=(FloatingActionButton)findViewById(R.id.fab);
        recyclerView=(RecyclerView)findViewById(R.id.ososrecycular);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));














        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Title");

                final EditText input = new EditText(MainActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();


                        animalNames.add(m_Text);
                        adapter = new MyRecyclerViewAdapter(MainActivity.this, animalNames,trying);
                        adapter.setClickListener(MainActivity.this);
                        recyclerView.setAdapter(adapter);






                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });



    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true).setShowFiles(false).setShowAudios(false).setShowVideos(false)
                .setSkipZeroSizeFiles(true)
                .build());
        startActivityForResult(intent, FILE_REQUEST_CODE);

        Toast.makeText(this, "Opening Gallery Please Wait...", Toast.LENGTH_SHORT).show();
        id=position;
        name=adapter.getItem(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if(mediaFiles != null) {
                Toast.makeText(MainActivity.this, String.valueOf( mediaFiles.get(1)), Toast.LENGTH_SHORT).show();


                animalNames.set(id,name);

                trying.add(id,mediaFiles);


                adapter.notifyItemChanged(id);



            }

        } else {
            Toast.makeText(MainActivity.this, "no items selected", Toast.LENGTH_SHORT).show();

        }

//            case PICK_DIALOG_OTHER:
//                //Do what you want here
//                break;

    }
    private void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles.clear();
        this.mediaFiles.addAll(mediaFiles);
        fileListAdapter.notifyDataSetChanged();
    }



}