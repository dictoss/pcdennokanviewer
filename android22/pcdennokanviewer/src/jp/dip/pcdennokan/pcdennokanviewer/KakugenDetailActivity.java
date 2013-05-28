package jp.dip.pcdennokan.pcdennokanviewer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class KakugenDetailActivity extends Activity {

	private DictossKakugen _data;

	public DictossKakugen getDictossKakugen() { return this._data; }
	public void setDictossKakugen(DictossKakugen o) { this._data = o; } 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kakugen_detail);
		
		this._data = (DictossKakugen)getIntent().getSerializableExtra("kakugen");
		
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
