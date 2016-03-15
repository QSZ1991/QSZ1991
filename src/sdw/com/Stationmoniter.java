package sdw.com;



import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import work.sdw.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Stationmoniter extends Fragment
{
	    public JSONObject jsonObject;
	    static int sum = 0;
		static JSONArray resultArray1;
		JSONObject jsonObject1;
		static String station = null;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
//		resultArray1= new JSONArray();
		Log.e("Bundle22222", bundle.getString("key_station"));		
		station = bundle.getString("key_station");
		Log.e("station!!!!", station);
		Stationmoniter a = new Stationmoniter();
		MoniterView ret = (MoniterView) inflater.inflate(R.layout.test1, null);
		ret.viewInit();
		a.dataResourse(ret);
		return ret;
		
	}
	public  void dataResourse(MoniterView view)
	{
		
		new AsyncTask<MoniterView, Integer, String>()
		{
			private MoniterView view = null;
			@Override
			protected String doInBackground(MoniterView... params)
			{
				this.view = params[0];
				
//配置ip和端口号
				String urllll=LoginActivity.dia;
				String url;
				if(urllll==null){
					 url ="http://58.215.202.186:8082/APPS/servlet/StationMonitorServlet";
				}
				else {
					 url = "http://"+urllll+"/APPS/servlet/StationMonitorServlet";
				}
//				String url = "http://58.215.202.186:8082/APPS/servlet/StationMonitorServlet";
				List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
				Log.e("station2222", station);
				paramList.add(HttpKeyValue.create("stationName", station));
				/* get the httpResponse */
				String jsonData1 = null;
				try
				{
					jsonData1 = HttpUtil.post(url, paramList);
					Log.d("jsonData1", jsonData1.toString());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				
				return jsonData1;
			}
			
			@Override  
		    protected void onPostExecute(String result) {  
				try
				{
					 jsonObject1 = new JSONObject(result);
					resultArray1 = jsonObject1.getJSONArray("result");
					if (resultArray1.isNull(0))
					{
						return;//this.view.initData(new JSONObject(result).getJSONArray(null));
					} else
					{						
						this.view.initData(resultArray1);
					}
					
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
				
				Log.e("tag1212", "msg.....");
				Log.e("resultArray12121212222222",resultArray1.toString());
		    } 
			
			@Override  
		    protected void onPreExecute() {  
		        
		    } 
			
		}.execute(view);
		
	}
	
	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

	}

}
