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
import android.widget.Toast;


public class devicecontrol extends Fragment {

	public JSONObject jsonObject;
	 static int sum = 0;
	static JSONArray resultArray1;
	static String station = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		resultArray1 = new JSONArray();
		Log.e("BundleTest0000", bundle.getString("key_station"));
		station = bundle.getString("key_station");
		devicecontrol b = new devicecontrol();
		Log.d("1","1");
		ControlView re1 = (ControlView) inflater.inflate(R.layout.test2, null);
		Log.d("1","2");
		re1.station=station;
		re1.viewInit();
		Log.d("1","3");
		b.dataResourse(re1);

		return re1;
	}
	 public  void dataResourse(ControlView re1)
		{			
			new AsyncTask<ControlView, Integer, String>()
			{
				private ControlView view = null;
				@Override
				protected String doInBackground(ControlView... params)
				{
					this.view = params[0];
					Log.d("2","1");
					String urllll=LoginActivity.dia;
					String url;
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/StationMonitorServlet";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/StationMonitorServlet";
					}
					
//					String url = "http://58.215.202.186:8082/APPS/servlet/StationMonitorServlet";
					List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
					Log.e("station2222", station);
					paramList.add(HttpKeyValue.create("stationName", station));
					/* get the httpResponse */
					String jsonData1 = null;
					try
					{
						jsonData1 = HttpUtil.post(url, paramList);
						Log.d("jsonData0", jsonData1.toString());
						Log.d("2","2");
						
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
					if (result.equals("error"))
					{
						this.view.initData(new JSONObject(result).getJSONArray(null));
					} else
					{
						this.view.initData(new JSONObject(result).getJSONArray("result"));
					}

				} catch (JSONException e)
				{
					e.printStackTrace();
				}
					
					Log.e("tag", "msg.....");
					Log.e("111111",resultArray1.toString());
			    } 
				
			}.execute(re1);
			
			
		}

}