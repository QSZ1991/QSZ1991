package sdw.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdw.com.bundle.AdminBundleObject;
import sdw.com.bundle.CountyBundleObject;
import sdw.com.bundle.StationBundleObject;
import work.sdw.R;
import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListConfigActivity extends Activity
{
	private TextView title;
	/** 警报操作 */
	private Button subm;
	static String config;
	private Handler handler = null;
	public JSONObject jsonObject;
	public JSONArray resultArray = new JSONArray();
	private List<Map<String, Object>> list1;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// 创建属于主线程的handler
		handler = new Handler();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_list);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();

		config = bundle.getString("config");// get date
		Log.e("TAGDATE", config);
		this.viewInit();
		// listView = (ListView) findViewById(R.id.listC);
		// 关键：SimpleAdapter 可以使listview中的item形式多样
		// initListView();
		listView = (ListView) findViewById(R.id.list0);
		new Thread()
		{
			public void run()
			{
				String url = null;
				Looper.prepare();
				
				//配置ip和端口号  5.25
				String urllll=LoginActivity.dia;
				
				if (config.equals("county"))
				{
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/AreaConfig";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/AreaConfig";
					}
				} else if (config.equals("admin"))
				{
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/AdministratorConfig";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/AdministratorConfig";
					}
				} else if (config.equals("station"))
				{
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/SewageConfig";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/SewageConfig";
					}
				}
				/* get the httpResponse */
				List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
				paramList.add(HttpKeyValue.create("requestFlag", "R"));
				/* get the httpResponse */
				String jsonData1 = null;
				jsonData1 = HttpUtil.post(url, paramList);
				Log.i("JSONData++++++++++++", jsonData1);
				/* analyze jesonDate */

				if(jsonData1.equals("error"))
				{
					Toast.makeText(ListConfigActivity.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (jsonData1 == null || jsonData1.isEmpty())
				{
					Log.e("ERROR++++++++++++", "jsonData1 ERROR");
					return;
				}

				try
				{
					jsonObject = new JSONObject(jsonData1);

					if (jsonObject == null)
					{
						Log.e("ERROR++++++++++++", "jsonObject ERROR");
						return;
					}

					resultArray = jsonObject.getJSONArray("result");

                     list1 = new ArrayList<Map<String, Object>>();
					if (resultArray == null || resultArray.length() == 0)
					{
						Log.e("ERROR++++++++++++", "result Array ERROR");
						return;
					}
					for (int i = 0; i < resultArray.length(); i++)
					{
						Map<String, Object> map = new HashMap<String, Object>();
						JSONObject json = (JSONObject) resultArray.opt(i);
//						Log.e("JSONOBJECT", json.toString());
						String title = null;
						String info = null;
						if (config.equals("county"))
						{
							try
							{
								title = json.getString("name");
								info = json.getString("principal");
								Log.d("title", title);
								Log.d("info", info);
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (config.equals("admin"))
						{
							try
							{
								title = json.getString("name");
								info = json.getString("countyName");
								Log.d("title", title);
								Log.d("info", info);
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (config.equals("station"))
						{
							try
							{
								title = json.getString("countyName");
								info = json.getString("name");
								Log.d("title", title);
								Log.d("info", info);
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						map.put("title", title);
						map.put("info", info);
						list1.add(map);
					}
					Log.d("list111111111", list1.toString());
					handler.post(runnableUi);

				} catch (JSONException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.start();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				if (config.equals("county"))
				{
					// TODO Auto-generated method stub
					Intent intent = new Intent(ListConfigActivity.this, sdw.com.CountyConfig.class);
					Bundle bundle = new Bundle();
					JSONObject json1 = (JSONObject) resultArray.opt(position);
					Log.e("json``````county", resultArray.opt(position).toString());
					CountyBundleObject bo = new CountyBundleObject(json1);
					Log.e("bo", bo.toString());
					bundle.putSerializable("listitem", bo);
					bundle.putString("RequestFlag", "U");
					intent.putExtras(bundle);
					startActivity(intent);
				} 
				else if (config.equals("admin"))
				{
					Intent intent = new Intent(ListConfigActivity.this, sdw.com.AdminConfig.class);
					Bundle bundle = new Bundle();
					JSONObject json1 = (JSONObject) resultArray.opt(position);
					Log.e("json``````admin", resultArray.opt(position).toString());
					AdminBundleObject boi= new AdminBundleObject(json1);
					bundle.putSerializable("listitem", boi);
					bundle.putString("RequestFlag", "U");
					intent.putExtras(bundle);
					startActivity(intent);
				} else if (config.equals("station"))
				{
					Intent intent = new Intent(ListConfigActivity.this, sdw.com.StationConfig.class);
					Bundle bundle = new Bundle();
					JSONObject json1 = (JSONObject) resultArray.opt(position);
					Log.e("json``````station", resultArray.opt(position).toString());
					
					StationBundleObject bos= new StationBundleObject(json1);
					bundle.putSerializable("listitem", bos);
					bundle.putString("RequestFlag", "U");
					intent.putExtras(bundle);
					startActivity(intent);
				}
				
			}

		});
	}

	// 构建Runnable对象，在runnable中更新界面
	Runnable runnableUi = new Runnable()
	{
		@Override
		public void run()
		{
			// 更新界面
			/* Find listView */
			if (list1 == null)
			{
				Toast.makeText(ListConfigActivity.this, "返回数据为空，请检查数据或网络", Toast.LENGTH_SHORT).show();
				return;
			}
			SimpleAdapter adapter = new SimpleAdapter(ListConfigActivity.this, list1, R.layout.config_itemstyle, new String[]
			{ "title", "info" }, new int[]
			{ R.id.configOne, R.id.configTwo });

			// System.out.println(adapter.getCount());
			listView.setAdapter(adapter);
		}

	};

	public void viewInit()
	{
		title = (TextView) findViewById(R.id.textC);
		if (config.equals("county"))
		{
			title.setText("单击选项查看区县配置详情");
		} else if (config.equals("admin"))
		{
			title.setText("单击选项查看管理员配置详情");
		} else if (config.equals("station"))
		{
			title.setText("单击选项查看站点配置详情");
		}
		subm = (Button) findViewById(R.id.btnAd);
		subm.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			if (config.equals("county"))
			{
				Intent intent = new Intent(ListConfigActivity.this, sdw.com.CountyConfig.class);
				Bundle bundle = new Bundle();
				bundle.putString("RequestFlag", "C");
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (config.equals("admin"))
			{
				Intent intent = new Intent(ListConfigActivity.this, sdw.com.AdminConfig.class);
				Bundle bundle = new Bundle();
				bundle.putString("RequestFlag", "C");
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (config.equals("station"))
			{
				Intent intent = new Intent(ListConfigActivity.this, sdw.com.StationConfig.class);
				Bundle bundle = new Bundle();
				bundle.putString("RequestFlag", "C");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	};

	public final class ViewHolder
	{
		public TextView title;
		public TextView info;
	}
}
