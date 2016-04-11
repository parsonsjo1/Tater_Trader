package com.joshwa.tater_trader;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Feed extends AppCompatActivity {
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private FloatingActionButton mScannerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_feed);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Feed.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                System.out.println("Feed Click");
//                Intent intent = new Intent(, Detail.class);
//                startActivity(intent);
            }
        });

        mScannerButton = (FloatingActionButton) findViewById(R.id.abScanner);
        mScannerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Feed.this, BarcodeScanner.class);
                startActivity(intent);
            }
        });
    }
}