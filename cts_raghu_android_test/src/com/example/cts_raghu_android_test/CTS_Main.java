package com.example.cts_raghu_android_test;

import java.util.ArrayList;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.content.Context;

public class CTS_Main extends Activity implements AsycTaskJsonParser.Update_ActivityTitle_Interface{

	/** Elements in activity_main.xml*/
	private ListView lstView;
	private Button refreshButton;

	/** Array list to capture JSON data*/
	private ArrayList<DataModelRow> arrNews ;

	/** Layout inflater instance*/
	private LayoutInflater lf;
	private Context mContext = this;

	/** Adapter to populate array list in Listview*/
	private DataAdapter va;

	/** Volley Library Variables*/
	private RequestQueue mRequestQueue;
	ImageLoader.ImageCache imageCache;
	ImageLoader imageLoader;

	/** www.dropbox.com is replaced by dl.dropboxusercontent.com to download JSON content*/
	private static final String JSON_URL = "https://dl.dropboxusercontent.com/s/g41ldl6t0afw9dv/facts.json";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cts_main);


		lf = LayoutInflater.from(this);

		/** Using Memory Cache(RAM) of Volley Library only
		 * If images are in high number you need to implement Disk Cache also
		 **/
		imageCache = new BitmapLruCache();
		mRequestQueue = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(mRequestQueue, imageCache);

		/** Initializing Arraylist and Adapter*/
		arrNews = new ArrayList<DataModelRow>();
		va = new DataAdapter(lf, arrNews, imageLoader);

		lstView = (ListView) findViewById(R.id.listView);


		/** Once refresh button is pressed clear all the data in arraylist
		 * and start JSON query again
		 **/
		refreshButton = (Button) findViewById(R.id.refresh);
		refreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrNews.clear();
				new AsycTaskJsonParser(va, arrNews, mContext).execute(JSON_URL);
				lstView.invalidate();
			}
		});

		new AsycTaskJsonParser(va, arrNews, mContext).execute(JSON_URL);
		lstView.setAdapter(va);
	}


	@Override
	public void update_Activitytitle_Method(String result) {
		// TODO Auto-generated method stub
		setTitle(result);
	}

}
