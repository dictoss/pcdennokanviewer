package jp.dip.pcdennokan.pcdennokanviewer;

import java.util.List;


public class DictossSiteHistoryList {
	protected String _code;
	protected String _message;	
	protected List<DictossSiteHistory> _result;
	
	
	public String getCode() { return this._code; }
	public void setCode(String s) { this._code = s; }

	public String getMessage() { return this._message; }
	public void setMessage(String s) { this._message = s; }

	public List<DictossSiteHistory> getResult() { return this._result; }
	public void setResult(List<DictossSiteHistory> o) { this._result = o; }
}
