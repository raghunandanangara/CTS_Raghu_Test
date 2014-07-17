package com.example.cts_raghu_android_test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsycTaskJsonParser extends AsyncTask<String, Void, String>{
	private Context mainActivityContext;
	private String mResponse;
	private DataAdapter adapter;
	private List<DataModelRow> atm_list;
	private ProgressDialog pd;
	private String txtTitle;

	public String getTxtTitle() {
		return txtTitle;
	}

	public void setTxtTitle(String txtTitle) {
		this.txtTitle = txtTitle;
	}

	List<DataModelRow> tempitems; //Using a templist as we should not use items directly to avoid following error
	/*The content of the adapter has changed but ListView did not receive a notification” exception */

	public AsycTaskJsonParser(DataAdapter adapter, List<DataModelRow> atm_list,
			Context mainActivityContext) {
		super();
		this.mainActivityContext = mainActivityContext;
		this.adapter = adapter;
		this.atm_list = atm_list;
		tempitems = new ArrayList<DataModelRow>();
		pd = new ProgressDialog(this.mainActivityContext);
	}

	public String getmResponse() {
		return mResponse;
	}

	public void setmResponse(String mResponse) {
		this.mResponse = mResponse;
	}

	/** Parsing according to JSON response Keys */
	private void parseJSON(String mRespoString){

		try{
			JSONObject mJSONResponse = new JSONObject(mRespoString);
			setTxtTitle(mJSONResponse.getString("title"));

			//Get JSON Array values with Key="rows"
			JSONArray items = mJSONResponse.getJSONArray("rows");
			for(int i=0;i<items.length();i++){
				JSONObject item = items.getJSONObject(i);
				DataModelRow nm = new DataModelRow();

				String tempTitle = (!item.isNull("title")) ? item.getString("title") : null;
				String tempDesc = (!item.isNull("description")) ? item.getString("description") : null;
				String tempImageHref = (!item.isNull("imageHref")) ? item.getString("imageHref") : null;

				if(tempTitle != null)
				{
					nm.setTitle(tempTitle);
					nm.setDescription(tempDesc);
					nm.setImageHref(tempImageHref);

					tempitems.add(nm);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		JSONParser jParser = new JSONParser();
		//JSONArray jsonObject = jParser.getJSONFromUrl(url);
		setmResponse(jParser.getJSONFromUrl(url));
		parseJSON(getmResponse());
		Log.i("Raghu", "Doing in Backgroud");
		return getTxtTitle();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (pd!=null) {
			pd.dismiss();
		}
		atm_list.addAll(tempitems); //Copying all the contents back on UI thread and notifying adapter of changes immediately
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd.setTitle("Processing...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}
}