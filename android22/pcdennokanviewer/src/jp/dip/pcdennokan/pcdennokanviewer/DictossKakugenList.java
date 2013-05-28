package jp.dip.pcdennokan.pcdennokanviewer;

import java.util.List;


public class DictossKakugenList {
	protected String _code;
	protected String _message;	
	protected List<DictossKakugen> _result;
	
	
	public String getCode() { return this._code; }
	public void setCode(String s) { this._code = s; }

	public String getMessage() { return this._message; }
	public void setMessage(String s) { this._message = s; }

	public List<DictossKakugen> getResult() { return this._result; }
	public void setResult(List<DictossKakugen> o) { this._result = o; }
}
