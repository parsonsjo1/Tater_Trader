package com.joshwa.tater_trader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Feed2 extends AppCompatActivity{
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private FloatingActionButton mScannerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_feed2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(Feed2.this, Details.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", getBitmapFromURL(item.getImage()));

                //Start details activity
                startActivity(intent);

                gridAdapter = new GridViewAdapter(Feed2.this, R.layout.grid_item_layout, getData());
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
            }
        });

        mScannerButton = (FloatingActionButton) findViewById(R.id.abScanner);
        mScannerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Feed2.this, BarcodeScanner.class);
                startActivity(intent);
                gridAdapter = new GridViewAdapter(Feed2.this, R.layout.grid_item_layout, getData());
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
            }
        });
    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        //TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        ServerProxy server = ServerProxy.getInstance();
        List<UPCInfo> allItems = server.getUPCInfo();
        //String[] titles = {"Nature Valley Sweet & Salty Nut - Peanut Granola Bar (16 Bars)", "second cool image"};
        //String[] imgs = {"http://ecx.images-amazon.com/images/I/51GlnQ64EAL._SL160_.jpg", "https://www.gravatar.com/avatar/5ffb191914754e0c194211417999ca90?s=32&d=identicon&r=PG"};
        //double[] prices = {8.97, 98.98};
        //for (int i = 0; i < allItems.size(); i++) {
        for(UPCInfo item : allItems) {
            //Bitmap prebitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            //Bitmap bitmap = scaleDownBitmap(prebitmap, 100, this);
            if (item.getImageURL().length() > 4) {
                String bitmap = item.getImageURL();
                String title = item.getProductName();
                //double price = prices[i];
                imageItems.add(new ImageItem(bitmap, title));
            }
        }

        return imageItems;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}

