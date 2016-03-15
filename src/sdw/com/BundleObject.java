package sdw.com;

import java.io.Serializable;

import org.json.JSONObject;

public class BundleObject implements Serializable
{
	
	private Object object;
	
	public BundleObject(Object obj) {
		this.object = obj;
	}
	
	public Object getObj() {
		return object;
	}
	
	public void setObj(Object obj) {
		this.object = obj;
	}
	
}
