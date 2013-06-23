package jp.dip.pcdennokan.pcdennokanviewer;

import java.net.URI;

import org.codehaus.jackson.map.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class DictossWebSocketClient extends WebSocketClient {

	protected String password = ""; 
	protected NotificationManager notificationManager = null;
	protected Activity activity = null;
	
	public DictossWebSocketClient(URI serverURI, Activity a) {
		super(serverURI);
		
		this.activity = a;
		this.notificationManager = (NotificationManager)a.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		System.out.println("onOpen()");

		//send password
		String senddata = String.format("{\"func\": \"auth\", \"data\": {\"token\": \"%s\"}}", this.password);
		
		this.send(senddata);
	}
	
	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// no task.
		System.out.println("onClose()");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onMessage(String arg0) {
		System.out.println("onMessage()");		
		System.out.println(arg0);
		
		ObjectMapper mapper = new ObjectMapper();		
		DictossPushNotify data = null;
		
		try{
			data = mapper.readValue(arg0, DictossPushNotify.class);
		}
		catch(Exception e){
			String s = e.getMessage();
			System.out.println(s);
		}
		finally{
		}
		
		try{
			String updatedt = data.getUpdateDate();
			String message = data.getMessage();
			String url = data.getUrl();
			
			@SuppressWarnings("deprecation")
			Notification notification = new Notification(
					android.R.drawable.btn_default,
					message,
					System.currentTimeMillis());
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			PendingIntent contentIntent = PendingIntent.getActivity(this.activity, 0, intent, 0);
			
			notification.setLatestEventInfo(
					this.activity.getApplicationContext(),
					"pcdennokanviewer",
					message,
					contentIntent);
			
			notificationManager.notify(R.string.app_name, notification);
		}
		catch(Exception e){
			String s = e.getMessage();
			System.out.println(s);
		}
		finally{
		}
	}

	@Override
	public void onError(Exception arg0) {
		System.out.println("onError()");
	}
	
	public String getPassword(){ return this.password;}
	public void setPassword(String s){ this.password = s;}
}
