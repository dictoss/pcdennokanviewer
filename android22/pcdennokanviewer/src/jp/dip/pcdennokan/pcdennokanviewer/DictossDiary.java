package jp.dip.pcdennokan.pcdennokanviewer;

import java.io.Serializable;


import org.codehaus.jackson.annotate.JsonProperty;

public class DictossDiary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6244057428826401509L;
	
	protected long _id;
	
	protected String _create_date;
	protected String _update_date;
	//@JsonSerialize(using=DateTimeSerializer.class)
	//protected Date _create_date;

	//@JsonSerialize(using=DateTimeSerializer.class)
	//protected Date _update_date;

	protected String _subject;
	protected String _content;
	
	
	public long getId() { return this._id; }
	public void setId(long id) { this._id = id; }

	@JsonProperty("create_date")
	public String getCreateDate() { return this._create_date; }
	//public Date getCreateDate() { return this._create_date; }
	@JsonProperty("create_date")
	public void setCreateDate(String d) { this._create_date = d; }
	//public void setCreateDate(Date d) { this._create_date = d; }

	@JsonProperty("update_date")
	public String getUpdateDate() { return this._update_date; }
	//public Date getUpdateDate() { return this._update_date; }
	@JsonProperty("update_date")
	public void setUpdateDate(String d) { this._update_date = d; }
	//public void setUpdateDate(Date d) { this._update_date = d; }

	public String getSubject() { return this._subject; }
	public void setSubject(String s) { this._subject = s; }

	public String getContent() { return this._content; }
	public void setContent(String s) { this._content = s; }
}
