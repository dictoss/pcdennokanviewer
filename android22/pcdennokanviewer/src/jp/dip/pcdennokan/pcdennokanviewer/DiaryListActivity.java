package jp.dip.pcdennokan.pcdennokanviewer;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiaryListActivity extends Activity {

	protected List<String> itemlist = new ArrayList<String>();
	protected List<DictossDiary> datalist = null;
	protected String apiurl_path = "api/rest/diary/find/";
	protected String postdata_base = "{\"control\": {\"version\": %d, \"user\": \"%s\", \"pass\": \"%s\"}, \"query\": {\"maxrecord\": %d, \"keyword\": []}}";
	protected int diary_version = 1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary_list);

		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String postdata = String.format(this.postdata_base,
				this.diary_version,
				myPrefs.getString("edittext_apiuser_key", ""),
				myPrefs.getString("edittext_apipass_key", ""),
				Integer.parseInt(myPrefs.getString("edittext_fetchrow_key", "30")));
		
		String apiurl_prefix = myPrefs.getString("edittext_apiurl_key", "http://0.0.0.0/");
		String apiurl = null;
		if (true == apiurl_prefix.endsWith("/")) {
			apiurl = apiurl_prefix + this.apiurl_path;
		}
		else{
			apiurl = apiurl_prefix + "/" +this.apiurl_path;			
		}
		
		new AsyncDiaryHttpJsonTask<DictossDiaryList>(this) {
			protected void onPostExecute(DictossDiaryList result) {
				super.onPostExecute(result);

				DiaryListActivity.this.datalist = result.getResult();
				
				for(DictossDiary d : result.getResult()){
					DiaryListActivity.this.itemlist.add(d.getSubject());					
				}
		
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiaryListActivity.this, android.R.layout.simple_list_item_1);
				for(String s : DiaryListActivity.this.itemlist){
					adapter.add(s);
				}		
				ListView listview = (ListView)findViewById(R.id.diary_listView1);
				listview.setAdapter(adapter);
				
				/*
				 * add click event.
				 */
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				        
				        try{		                	
				        	Intent intent = new Intent(DiaryListActivity.this, DiaryDetailActivity.class);
				        	intent.putExtra("diary", DiaryListActivity.this.datalist.get(position));
				        	startActivity(intent);
				        }
				        catch (Exception e){
				        	String s = e.getMessage();
				        	System.out.println(s);
				        }
				    }
				});
			}
		}.execute(apiurl, "POST", postdata);
	}
}
