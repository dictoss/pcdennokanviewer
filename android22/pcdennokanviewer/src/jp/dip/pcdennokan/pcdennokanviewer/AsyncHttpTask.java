package jp.dip.pcdennokan.pcdennokanviewer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AsyncHttpTask<T> extends AsyncTask<String, Object, T> {
	
	protected Class<T> _type;
	
	protected String _url;
	protected String _authUser;
	protected String _password;
	protected String _method;
	protected String _resultEncoding = "UTF-8";
	protected String _contenttype = "text/html; charset=UTF-8";
	
	protected ProgressDialog progressDialog = null;
	protected Activity activity = null;
	
	public Activity getActivity() { return this.activity; }
	public void setActivity(Activity o) { this.activity = o; }
	
	public AsyncHttpTask(Activity a) {
		activity = a;
	}
	
	@Override
    protected void onPreExecute() {
		this.progressDialog = new ProgressDialog(this.activity);
		this.progressDialog.setMessage(
        		 //getResources().getText(R.string.data_loading),
				this.activity.getString(R.string.data_loading)
        		 );
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.show();
	}
	
    @Override
    protected void onPostExecute(T result) {
    	this.progressDialog.dismiss();
    }
	
	@Override
	protected T doInBackground(String... params) {
		this._url = params[0];
		this._method = params[1];
		String postdata = params[2];
		T conv_resdata = null;
		
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = null;

			if(this._method.equals("GET")){
				HttpGet httpget = new HttpGet(this._url);
				// querystringÇÃèàóù
				response = httpclient.execute(httpget);
			}
			else if(this._method.equals("POST")){
				HttpPost httppost = new HttpPost(this._url);
				/* post parameterÇÃèàóù */
				httppost.setHeader("Content-Type", this._contenttype);
				
				httppost.setEntity(this.convertRequest(postdata));
				
				response = httpclient.execute(httppost);
			}
			else if(this._method.equals("HEAD")){
				HttpHead httphead = new HttpHead(this._url);
				
				response = httpclient.execute(httphead);
			}
			else{
				throw new UnsupportedOperationException("not support method.");
			}
			
			InputStream is = response.getEntity().getContent();			
			conv_resdata = this.convertResponse(is);
			is.close();
		}
		catch(Exception e){
			
			return null;
		}
		
		return conv_resdata;
	}

	protected HttpEntity convertRequest(Object o) throws UnsupportedEncodingException
	{
		@SuppressWarnings("unchecked")
		List<NameValuePair> nvp = (ArrayList<NameValuePair>)o;
		return new UrlEncodedFormEntity(nvp, HTTP.UTF_8);
	}

	@SuppressWarnings("unchecked")
	protected T convertResponse(InputStream is) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is, this._resultEncoding);
		BufferedReader br = new BufferedReader(isr);
		
		try{
			while(true){
				String buf = br.readLine();
				
				if(null == buf){
					break;
				}
				else{
					sb.append(buf);
				}
			}
		}
		catch(Exception e){
			return null;
		}
		
		return (T)sb.toString();
	}
}
