package sdw.com;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import work.sdw.R;
import work.sdw.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class FunActivity extends Activity
{

	boolean threadflag = true;
	Thread thread =null;
	public static int flag = 0;
	String jsonData1;
	String urllll=null;
	public JSONObject jsonObject;
	public JSONArray resultArray = new JSONArray();

	// private Handler handler=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// 创建属于主线程的handler
		// handler = new Handler();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funct);
		Button btnSerch = (Button) findViewById(R.id.btnSerch);// get button
																// references
		btnSerch.setOnClickListener(new View.OnClickListener()// add a listener												// for button
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("fun", "");
				Intent intent = new Intent(FunActivity.this, sdw.com.DeviceActivity.class);//
				intent.putExtras(bundle);//
				startActivity(intent);
				/* finish(); */
			}
		});

		Button btnMoniter = (Button) findViewById(R.id.btnMoniter);
		btnMoniter.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("fun1", "btnMonitor");
				bundle.putString("search1", "");
				Intent intent = new Intent(FunActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				intent.putExtras(bundle);
				startActivity(intent);
				// finish();
			}
		});
		Button btnWarn = (Button) findViewById(R.id.btnWarn);
		btnWarn.setText("预警查询");
		btnWarn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("fun1", "btnWarn");
				bundle.putString("search1", "");
				Intent intent = new Intent(FunActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		Button btnConfig = (Button) findViewById(R.id.btnConfig);// get button
																	// references
		btnConfig.setOnClickListener(new View.OnClickListener()// add a listener
																// for button
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						Intent intent = new Intent(FunActivity.this, sdw.com.SysconfigActivity.class);//
						startActivity(intent);
						/* finish(); */
					}
				});

		// 创建属于主线程的handler
		final Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String alert = data.getString("aas");
				Log.e("alert1111111```11", alert);
//				Toast.makeText(getApplicationContext(), alert,Toast.LENGTH_SHORT).show();
				Dialog dialog = new AlertDialog.Builder(getApplicationContext()).setTitle("警报").setMessage(alert).setPositiveButton("确定", new OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
						flag = 0;
					}
				}).create();
				dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				dialog.show();

			}
		};

		// 定义一个计时器，周期性的执行任务
		thread = new Thread()
		{
			@Override
			public void run()
			{
				
				Looper.prepare();
				while(threadflag) {
				Log.i("aaaa", "aaaa");
				if (flag == 0)
				{
					Log.e("sasasa2222", "sassasasasa");
					// TODO Auto-generated method stub

					urllll=LoginActivity.dia;
					String url;
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/AlertQuery";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/AlertQuery";
					}
                    Log.e("urllll", url);
					List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
					try
					{
						paramList.add(HttpKeyValue.create("requestFlag", "RT"));

					} catch (Exception e)
					{
					}
					Log.e("sasasa3333", "sassasasasa");
					/* get the httpResponse */
					try
					{
						jsonData1 = HttpUtil.post(url, paramList);
					} catch (Exception e)
					{
						// TODO: handle exception
						Log.e("json11111111", jsonData1);
					}
					if (jsonData1.equals("error")){
						Toast.makeText(getApplicationContext(), "链接连接问题", Toast.LENGTH_SHORT).show();
						return;
					}
					//Log.e("json11111111", jsonData1);
					Log.e("sasasa4444", "sassasasasa");
					try
					{
						jsonObject = new JSONObject(jsonData1);
						resultArray = jsonObject.getJSONArray("result");
						if(resultArray==null){
//							Toast.makeText(getApplicationContext(), "链接连接问题", Toast.LENGTH_SHORT).show();
							return;}
//						if( resultArray.opt(0).toString()==null)							
//							return;				
//						Log.e("json2222222", resultArray.opt(0).toString());
					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					Log.e("sasasa55555", "sassasasasa");
					int sum = resultArray.length();
//					Log.e("sum````", sum + "");
					String alertStationInfo = null;
					if (sum != 0)
					{
						int s = 1;
						//Log.e("json````", resultArray.opt(0).toString());
						JSONObject json = (JSONObject) resultArray.opt(0);
						try
						{
							alertStationInfo = json.getString("shortTitle");
							++s;
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 1; i < sum; i++)
						{
							if (s > 5)
								break;
							json = (JSONObject) resultArray.opt(i);
							String tempTitle = null;
							try
							{
								tempTitle = json.getString("shortTitle");
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (alertStationInfo.indexOf(tempTitle) != -1)
							{
								continue;
							} else
							{
								alertStationInfo = (alertStationInfo + "," + tempTitle);
								++s;
							}
						}
						
						alertStationInfo = alertStationInfo + "等站点发生异常共" + sum + "个，请及时处理";
						flag = 1;
						Message msg = new Message();
						Bundle dataBundle = new Bundle();
						dataBundle.putString("aas", alertStationInfo);
						msg.setData(dataBundle);
						handler.sendMessage(msg);
					}else
					{
						Log.e("baojing", "暂时无报警");
					}
				}
				else
					Log.i("没弹出", "没弹出");
				
				try
				{
					Thread.sleep(80000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				}

			}
		};
		thread.start();		
	}
	
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onPause();
		
		this.threadflag = false;
		Log.e("destroy", "destroy");
	}

}
