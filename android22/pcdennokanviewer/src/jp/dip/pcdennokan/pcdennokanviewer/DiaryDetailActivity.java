package jp.dip.pcdennokan.pcdennokanviewer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DiaryDetailActivity extends Activity {

	private DictossDiary _data;

	public DictossDiary getDictossDiary() { return this._data; }
	public void setDictossDiary(DictossDiary o) { this._data = o; } 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary_detail);
		
		this._data = (DictossDiary)getIntent().getSerializableExtra("diary");
		
		TextView tvId = (TextView)findViewById(R.id.textViewId);
		tvId.setText("ID: " + Long.toString(this._data.getId()));

		TextView tvCreateDate = (TextView)findViewById(R.id.textViewCreateDate);
		tvCreateDate.setText("çÏê¨ì˙éû: " + this._data.getCreateDate());

		TextView tvUpdateDate = (TextView)findViewById(R.id.textViewUpdateDate);
		tvUpdateDate.setText("çXêVì˙éû: " + this._data.getUpdateDate());

		TextView tvSubject = (TextView)findViewById(R.id.textViewSubject);
		tvSubject.setText("åèñº: " + this._data.getSubject());

		TextView tvContent = (TextView)findViewById(R.id.textViewContent);
		tvContent.setText(this._data.getContent());
	}
}
