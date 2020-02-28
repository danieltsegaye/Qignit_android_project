package com.example.tabmusic;


import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllMusic extends Fragment {

    ListView allMusicList;
    ArrayAdapter<String> musicArrayAdabter;
    ArrayList<File> musics;
    String songs[];

    public AllMusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_music, container, false);
        allMusicList = view.findViewById(R.id.MusicList);

        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                //display list of musics
                musics = findMusicFiles(Environment.getExternalStorageDirectory());
                songs = new String[musics.size()];

                for (int i=0; i<musics.size(); i++ ){
                    songs[i] = musics.get(i).getName();
                }

                musicArrayAdabter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,songs);
                allMusicList.setAdapter(musicArrayAdabter);

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
               //keep asking permission app until granted
                token.continuePermissionRequest();

            }
        }).check();

        return view;
    }
    private ArrayList<File> findMusicFiles(File file){
        ArrayList<File> allMusicFileObjects = new ArrayList<>();
        File [] files = file.listFiles();

        for(File currentFile: files){
            if(currentFile.isDirectory() && !currentFile.isHidden()){
                allMusicFileObjects.addAll(findMusicFiles(currentFile));
            }else {
                //selects only music files
                if (currentFile.getName().endsWith(".mp3") || currentFile.getName().endsWith(".mp4a") || currentFile.getName().endsWith(".wav")){
                    allMusicFileObjects.add(currentFile);
                }

            }
        }
        return allMusicFileObjects;

    }

}
