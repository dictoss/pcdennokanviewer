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

public class SiteHistoryListActivity extends Activity {

	protected List<String> itemlist = new ArrayList<String>();
	protected List<DictossSiteHistory> datalist = null;
	protected String apiurl_path = "api/rest/history/find/";
	protected String postdata_base = "{\"control\": {\"version\": %d, \"user\": \"%s\", \"pass\": \"%s\"}, \"query\": {\"maxrecord\": %d, \"keyword\": []}}";
	protected int sitehistory_version = 1;
	protected int liststr_limit = 20;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sitehistory_list);

		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String postdata = String.format(this.postdata_base,
				this.sitehistory_version,
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
		
		new AsyncSiteHistoryHttpJsonTask<DictossSiteHistoryList>(this) {
			protected void onPostExecute(DictossSiteHistoryList result) {
				super.onPostExecute(result);

				SiteHistoryListActivity.this.datalist = result.getResult();
				
				for(DictossSiteHistory d : result.getResult()){
					String s = null;
					
					if (SiteHistoryListActivity.this.liststr_limit < d.getContent().length()){
						s = d.getContent().substring(0, SiteHistoryListActivity.this.liststr_limit);
					}
					else{
						s = d.getContent();
					}
					
					SiteHistoryListActivity.this.itemlist.add(s);
				}
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(SiteHistoryListActivity.this, android.R.layout.simple_list_item_1);
				for(String s : SiteHistoryListActivity.this.itemlist){
					adapter.add(s);
				}
				ListView listview = (ListView)findViewById(R.id.sitehistory_listView1);
				listview.setAdapter(adapter);
				
		
				/*
				 * add click event.
				 */
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				        try{		                	
				        	Intent intent = new Intent(SiteHistoryListActivity.this, SiteHistoryDetailActivity.class);
				        	intent.putExtra("sitehistory", SiteHistoryListActivity.this.datalist.get(position));
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
