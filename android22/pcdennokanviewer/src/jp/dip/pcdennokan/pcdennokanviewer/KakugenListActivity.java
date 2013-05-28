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

public class KakugenListActivity extends Activity {

	protected List<String> itemlist = new ArrayList<String>();
	protected List<DictossKakugen> datalist = null;
	protected String apiurl_path = "api/rest/kakugen/find/";
	protected String postdata_base = "{\"control\": {\"version\": %d, \"user\": \"%s\", \"pass\": \"%s\"}, \"query\": {\"maxrecord\": %d, \"keyword\": []}}";
	protected int kakugen_version = 1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kakugen_list);

		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String postdata = String.format(this.postdata_base,
				this.kakugen_version,
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
		
		new AsyncKakugenHttpJsonTask<DictossKakugenList>(this) {
			protected void onPostExecute(DictossKakugenList result) {
				super.onPostExecute(result);

				KakugenListActivity.this.datalist = result.getResult();
				
				for(DictossKakugen d : result.getResult()){		
					KakugenListActivity.this.itemlist.add(d.getContent().substring(0, 20));
				}
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(KakugenListActivity.this, android.R.layout.simple_list_item_1);
				for(String s : KakugenListActivity.this.itemlist){
					adapter.add(s);
				}
				ListView listview = (ListView)findViewById(R.id.kakugen_listView1);
				listview.setAdapter(adapter);
				
		
				/*
				 * add click event.
				 */
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				        try{		                	
				        	Intent intent = new Intent(KakugenListActivity.this, KakugenDetailActivity.class);
				        	intent.putExtra("kakugen", KakugenListActivity.this.datalist.get(position));
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
