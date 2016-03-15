package sdw.com.bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AdminBundleObject extends BaseBundleObject
{
	
	public AdminBundleObject(JSONObject jsonObject)
	{
		// TODO Auto-generated constructor stub
		try
		{
			countyName=jsonObject.getString("countyName");
			adr=jsonObject.getString("adr");
			email=jsonObject.getString("email");
			administratorId=jsonObject.getString("administratorId");
			areaId=jsonObject.getString("areaId");
			tel=jsonObject.getString("tel");
			Log.e("budle`````", tel);
			name=jsonObject.getString("name");
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String countyName;
	private String adr;
	private String email;
	private String administratorId;
	private String areaId;
	private String tel;
	private String name;

	public String getCountyName()
	{
		return countyName;
	}
	public void setCountyName(String countyName)
	{
		this.countyName = countyName;
	}
	public String getAdr()
	{
		return adr;
	}
	public void setAdr(String adr)
	{
		this.adr = adr;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getAdministratorId()
	{
		return administratorId;
	}
	public void setAdministratorId(String administratorId)
	{
		this.administratorId = administratorId;
	}
	public String getAreaId()
	{
		return areaId;
	}
	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
