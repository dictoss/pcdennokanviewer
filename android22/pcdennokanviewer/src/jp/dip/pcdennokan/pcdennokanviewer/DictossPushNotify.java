package jp.dip.pcdennokan.pcdennokanviewer;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class DictossPushNotify implements Serializable {
	private static final long serialVersionUID = 6244057428826401512L;
	
	protected String _url;
	protected String _message;
	protected String _update_date;

	public String getUrl() { return this._url; }
	public void setUrl(String s) { this._url = s; }
	
	public String getMessage() { return this._message; }
	public void setMessage(String s) { this._message = s; }
	
	@JsonProperty("update_date")
	public String getUpdateDate() { return this._update_date; }
	//public Date getUpdateDate() { return this._update_date; }
	@JsonProperty("update_date")
	public void setUpdateDate(String d) { this._update_date = d; }
	//public void setUpdateDate(Date d) { this._update_date = d; }

}
