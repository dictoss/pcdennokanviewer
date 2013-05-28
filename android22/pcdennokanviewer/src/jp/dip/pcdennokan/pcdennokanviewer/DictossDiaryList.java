package jp.dip.pcdennokan.pcdennokanviewer;

import java.util.List;


public class DictossDiaryList {
	protected String _code;
	protected String _message;	
	protected List<DictossDiary> _result;
	
	
	public String getCode() { return this._code; }
	public void setCode(String s) { this._code = s; }

	public String getMessage() { return this._message; }
	public void setMessage(String s) { this._message = s; }

	public List<DictossDiary> getResult() { return this._result; }
	public void setResult(List<DictossDiary> o) { this._result = o; }
}
