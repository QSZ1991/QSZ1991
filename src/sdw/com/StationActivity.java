package sdw.com;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class StationActivity extends Activity
{

	private Button btnS = null;
	private ListView lv1 = null;
	private List<String> list1 = null;
	private Handler handler = null;
	static String transferVale;
	static String transferVale1;
	static String county;
    private Button btnTotalC;
    private LinearLayout layout1;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		handler = new Handler();  
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		
		transferVale = bundle.getString("fun2");// get fun
		Log.d("fun11111111", transferVale);
		transferVale1 = bundle.getString("search2");// get search
		Log.d("SEARCH11111111", transferVale1);
		county = bundle.getString("key_county");// get county\
		
		setContentView(R.layout.station);
		if (transferVale.equals("btnWarn"))
		{
			layout1 = (LinearLayout) findViewById(R.id.linStation);
			btnTotalC = new Button(this);
			btnTotalC.setText(county + "所有预警");
			layout1.addView(btnTotalC);

			btnTotalC.setOnClickListener(new View.OnClickListener()
			{

				public void onClick(View v)
				{
					Intent intent = new Intent(StationActivity.this, sdw.com.WarnActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("requestFlag", "RC");
					bundle.putString("key_station", county);
					intent.putExtras(bundle);
					startActivity(intent);
				}

			});
		}
		
		new Thread()
		{
			public void run()
			{
				super.run();
				Looper.prepare();
				
				//配置ip和端口号
				String urllll=LoginActivity.dia;
				String url;
				if(urllll==null){
					 url ="http://58.215.202.186:8082/APPS/servlet/StationServlet";
				}
				else {
					 url = "http://"+urllll+"/APPS/servlet/StationServlet";
				}
//				http://172.18.139.146:8080/APPS/servlet/
//				String url = "http://58.215.202.186:8082/APPS/servlet/StationServlet";
				Log.d("URL", url);
				Log.d("cccc", county);
				List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
				try
				{
					paramList.add(HttpKeyValue.create("county",county));
					
				} catch (Exception e)
				{
				}
				/* get the httpResponse */
				String jsonData1 = null;
				try
				{
					jsonData1 = HttpUtil.post(url, paramList); 
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(jsonData1.equals("error")){
					Toast.makeText(StationActivity.this, "访问服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("jsondata11111111111111", jsonData1);
				/* analyze jesonDate */
				list1 = JsonTools.getList("result", jsonData1);				
				
			    if(list1==null){
			    	Toast.makeText(StationActivity.this, "该区县暂时无站点", Toast.LENGTH_SHORT).show();
			    	return;
			    }
				handler.post(runnableUi);
				
				lv1 = (ListView) findViewById(R.id.listS); 
				lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
					{
						// TODO Auto-generated method stub	
//						Log.e("transferVale1111111111", transferVale);
//						Log.e("transferVale2222222222", transferVale1);
						if (transferVale.equals("btnMonitor"))
						{
							Intent intent = new Intent(StationActivity.this, sdw.com.StationmoniterActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("key_station", list1.get(arg2));
							intent.putExtras(bundle);
							startActivity(intent);
						} else if (transferVale.equals("btnWarn"))
						{
							Intent intent = new Intent(StationActivity.this, sdw.com.WarnActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("requestFlag", "RP");
							bundle.putString("key_station", list1.get(arg2));
							intent.putExtras(bundle);
							startActivity(intent);
						} else
						{
							if (transferVale1.equals("btnRunRecord"))
							{
								Log.d("btnRUn", "btnRunRecord111111111");
								Intent intent = new Intent(StationActivity.this, sdw.com.RunRecordDate.class);
								Bundle bundle = new Bundle();
								bundle.putString("key_station", list1.get(arg2));
								intent.putExtras(bundle);
								startActivity(intent);
							} else if (transferVale1.equals("btnCheckRecord"))
							{
								Log.d("btnCHeck", "btnCheckRecord111111111");
								Intent intent = new Intent(StationActivity.this, sdw.com.CheckRecordDate.class);
								Bundle bundle = new Bundle();
								bundle.putString("key_station", list1.get(arg2));
								intent.putExtras(bundle);
								startActivity(intent);
							}else if (transferVale1.equals("btnCheckMap"))
							{
								Log.d("btnCHeck", "btnCheckMap111111111");
								Intent intent = new Intent(StationActivity.this, sdw.com.LineViewActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("key_station", list1.get(arg2));
								intent.putExtras(bundle);
								startActivity(intent);
							}else{
								Log.d("btnCHeck", "btnWaterFlow");
								Intent intent = new Intent(StationActivity.this, sdw.com.WaterflowActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("key_station", list1.get(arg2));
								intent.putExtras(bundle);
								startActivity(intent);
							}							
						}
						}
				});
				btnS = (Button) findViewById(R.id.btnS);// 获得button对象，并添加监听
				btnS.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						/* 获得输入的站点 */
						EditText edtStation = (EditText) findViewById(R.id.edtStation);
						String station = edtStation.getEditableText().toString().trim();
						int check = 0;
						for (int i = 0; i < list1.size(); i++)
						{
							if (station.equals(list1.get(i)))
							{
								check++;
								if (transferVale.equals("btnMonitor"))
								{
									Intent intent = new Intent(StationActivity.this, sdw.com.StationmoniterActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("key_station", station);
									intent.putExtras(bundle);
									startActivity(intent);
								} else if (transferVale.equals("btnWarn"))
								{
									Intent intent = new Intent(StationActivity.this, sdw.com.WarnActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("requestFlag", "RP");
									bundle.putString("key_station", station);
									intent.putExtras(bundle);
									startActivity(intent);
								} else
								{
									if (transferVale1.equals("btnRunRecord") )
									{
										Log.d("btnRUn", "btnRunRecord111111111");
										Intent intent = new Intent(StationActivity.this, sdw.com.RunRecordDate.class);
										Bundle bundle = new Bundle();
										bundle.putString("key_station", station);
										intent.putExtras(bundle);
										startActivity(intent);
									} else if(transferVale1.equals("btnCheckRecord")){
										Log.d("btnCHeck", "btnCheckRecord111111111");
										Intent intent = new Intent(StationActivity.this, sdw.com.CheckRecordDate.class);
										Bundle bundle = new Bundle();
										bundle.putString("key_station", station);
										intent.putExtras(bundle);
										startActivity(intent);
									} else{
										Log.d("btnCHeck", "btnCheckMap111111111");
										Intent intent = new Intent(StationActivity.this, sdw.com.LineViewActivity.class);
										Bundle bundle = new Bundle();
										bundle.putString("key_station", station);
										intent.putExtras(bundle);
										startActivity(intent);
									}
								}
								break;
							}
						}
						if (check == 0)
						{
							Toast.makeText(StationActivity.this, "输入站点有误！", Toast.LENGTH_SHORT).show();
						}

					}
				});
			}
		
		}.start();
	
	}

	// 构建Runnable对象，在runnable中更新界面
	Runnable runnableUi = new Runnable(){
		public void run(){
			// 更新界面
			/* Find listView */
			lv1 = (ListView) findViewById(R.id.listS); 
			/* 这个是数组string类型的数组 */
			System.out.println("Hello world!");
			Log.d("run", list1.toString());
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(StationActivity.this, android.R.layout.simple_list_item_1, list1);
			/* add and display */
			lv1.setAdapter(adapter);
			/* add click */;
			
		}
	};

}