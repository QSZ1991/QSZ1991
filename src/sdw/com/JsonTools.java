package sdw.com;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonTools
{
	public JsonTools()
	{

	}

	public static List<String> getList(String key, String jsonString)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				String msg = jsonArray.getString(i);
				list.add(msg);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	

}
