package sdw.com.bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class CountyBundleObject extends BaseBundleObject
{
	public CountyBundleObject(JSONObject jsonObject) {
		
		try
		{
			principal=jsonObject.getString("principal");
			superiorArea=jsonObject.getString("superiorArea");
			intro=jsonObject.getString("intro");
			areaId=jsonObject.getString("areaId");
			tel=jsonObject.getString("tel");
			name=jsonObject.getString("name");
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private String principal;
	private String superiorArea;
	private String intro;
	private String areaId;
	private String tel;
	private String name;
	
	public String getPrincipal()
	{
		return principal;
	}
	public void setPrincipal(String principal)
	{
		this.principal = principal;
	}
	public String getSuperiorArea()
	{
		return superiorArea;
	}
	public void setSuperiorArea(String superiorArea)
	{
		this.superiorArea = superiorArea;
	}
	public String getIntro()
	{
		return intro;
	}
	public void setintro(String intro)
	{
		this.intro = intro;
	}public String getAreaId()
	{
		return areaId;
	}
	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}public String getName()
	{
		return name;
	}
	public void setName(String  name)
	{
		this.name = name;
	}
	
}
