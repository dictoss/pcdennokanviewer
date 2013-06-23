package jp.dip.pcdennokan.pcdennokanviewer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {
		 
	protected List<String> itemlist = new ArrayList<String>();
	
	protected DictossWebSocketClient websocketclient = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.itemlist.add(getString(R.string.menu_list_diary));
		this.itemlist.add(getString(R.string.menu_list_proverbs));
		this.itemlist.add(getString(R.string.menu_list_history));
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.id.main_listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for(String s : this.itemlist){
			adapter.add(s);
		}
		
		ListView listview = (ListView)findViewById(R.id.main_listView1);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                String item = (String) listView.getItemAtPosition(position);
                
                try{
	                if(item.equals(getString(R.string.menu_list_diary))){
	                	Intent intent = new Intent(MainActivity.this, DiaryListActivity.class);
	                	startActivity(intent);
	                }
	                else if(item.equals(getString(R.string.menu_list_proverbs))){
	                	Intent intent = new Intent(MainActivity.this, KakugenListActivity.class);
	                	startActivity(intent);
	                }
	                else if(item.equals(getString(R.string.menu_list_history))){
	                	Intent intent = new Intent(MainActivity.this, SiteHistoryListActivity.class);
	                	startActivity(intent);
	                }
                }
                catch (Exception e){
                	String s = e.getMessage();
                	System.out.println(s);
                }
            }
        });
		
		
		// create web socket
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		String apiurl = myPrefs.getString("edittext_pushserverurl_key", "ws://0.0.0.0:8888/");
		if (false == apiurl.endsWith("/")) {
			apiurl = apiurl + "/";			
		}
		
		String apipass = myPrefs.getString("edittext_pushserverpass_key", "");
		
		try{
			URI uri = new URI(apiurl);
			this.websocketclient = new DictossWebSocketClient(uri, this);
			this.websocketclient.setPassword(apipass);
			this.websocketclient.connect();
		}
		catch (Exception e){
			String s = e.getMessage();
			System.out.println(s);
		}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
		
		menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.action_settings);
		return super.onCreateOptionsMenu(menu);
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case Menu.FIRST:
            Intent intent = new Intent(this, AppPreferencesActivity.class);
            startActivity(intent);
	 
            return true;
        }
        return false;
    }
}
