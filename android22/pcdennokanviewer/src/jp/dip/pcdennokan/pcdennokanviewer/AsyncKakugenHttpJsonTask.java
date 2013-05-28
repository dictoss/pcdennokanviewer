package jp.dip.pcdennokan.pcdennokanviewer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;

public class AsyncKakugenHttpJsonTask<T> extends AsyncHttpTask<T> {

	public AsyncKakugenHttpJsonTask(Activity a) {
		super(a);
	}

	protected String _contenttype = "application/json; charset=UTF-8";
		
	@Override
	protected HttpEntity convertRequest(Object o) throws UnsupportedEncodingException
	{
		String s = (String)o;
		return new StringEntity(s, "UTF-8");
	}
	
	@Override
	protected T convertResponse(InputStream is) throws UnsupportedEncodingException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		DictossKakugenList data = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try{
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			
			data = mapper.readValue(br, DictossKakugenList.class);
		}
		catch(Exception e){
			String s = e.getMessage();
		}
		finally{
			try{
				if(null != br){
					br.close();
				}
			}
			catch(Exception e){}

			try{
				if(null != isr){
					isr.close();
				}
			}
			catch(Exception e){}
		}
		
		return (T)data;
	}
}
