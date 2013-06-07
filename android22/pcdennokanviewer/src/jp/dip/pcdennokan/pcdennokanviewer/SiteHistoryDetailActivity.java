package jp.dip.pcdennokan.pcdennokanviewer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SiteHistoryDetailActivity extends Activity {

	private DictossSiteHistory _data;

	public DictossSiteHistory getDictossSiteHistory() { return this._data; }
	public void setDictossSiteHistory(DictossSiteHistory o) { this._data = o; } 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sitehistory_detail);
		
		this._data = (DictossSiteHistory)getIntent().getSerializableExtra("sitehistory");
		
		TextView tvId = (TextView)findViewById(R.id.textViewId);
		tvId.setText("ID: " + Long.toString(this._data.getId()));

		TextView tvCreateDate = (TextView)findViewById(R.id.textViewCreateDate);
		tvCreateDate.setText("çÏê¨ì˙éû: " + this._data.getCreateDate());

		TextView tvUpdateDate = (TextView)findViewById(R.id.textViewUpdateDate);
		tvUpdateDate.setText("çXêVì˙éû: " + this._data.getUpdateDate());

		TextView tvContent = (TextView)findViewById(R.id.textViewContent);
		tvContent.setText(this._data.getContent());
	}
}
