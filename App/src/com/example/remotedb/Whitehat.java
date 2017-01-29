package com.example.remotedb;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;




public class Whitehat extends ListActivity {
	//public static final String KEY_121 = "http://http://www.nu-tech.in/rfnx/data.php";
/** Called when the activity is first created. */
   ArrayList<String> results=new ArrayList<String>();
   TextView txt;
   ListView lv1;
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    StrictMode.setThreadPolicy(policy); 
    // Create a crude view - this should really be set via the layout resources  
    // but since its an example saves declaring them in the XML.  
     lv1=(ListView)findViewById(R.id.list12); 
    //LinearLayout rootLayout = new LinearLayout(getApplicationContext());  
    //txt = new TextView(getApplicationContext());  
    //rootLayout.addView(txt);  
    
    //setContentView(rootLayout);  


    // Set the text and call the connect function.  
    //txt.setText("Connecting..."); 
  //call the method to run the data retreival
    getServerData("http://www.nu-tech.in/rfnx/data.php"); 






}
 //i use my real ip here






private void getServerData(String returnString) {
    
   InputStream is = null;
    
   String result = "";
    //the year data to send
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("year","1970"));


    //http post
    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();


    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            Toast.makeText(getApplicationContext(),"No Internet Connection",
        	          Toast.LENGTH_LONG).show();
    }


    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
    //parse json data
    setTheme(R.drawable.list_selector);
    try{
            JSONArray jArray = new JSONArray(result);
            results.clear();
            for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                   /* Log.i("log_tag","id: "+json_data.getInt("id")+
                            ", name: "+json_data.getString("name")+
                            ", sex: "+json_data.getInt("sex")++
                            ", birthyear: "+json_data.getInt("birthyear")
                    );*/
                    //Get an output to the screen
                    results.add(json_data.getString("name"));
                    returnString += "\n\t" + jArray.getJSONObject(i); 
            }
            
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }
    setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, results));
    getListView().setTextFilterEnabled(true);
    getListView().setChoiceMode(lv1.CHOICE_MODE_MULTIPLE);
    getListView().setOnItemLongClickListener (new OnItemLongClickListener() {
    	  public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
    		  setListAdapter(new ArrayAdapter<String>(Whitehat.this,android.R.layout.simple_list_item_single_choice, results));
    		  //lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    		  
    		 return true;
    	    //do your stuff here
    	  }
    	});
    
}    
    
}
