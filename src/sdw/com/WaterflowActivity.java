package sdw.com;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import com.widget.time.JudgeDate;
import com.widget.time.ScreenInfo;
import com.widget.time.WheelMain;
import sdw.com.MyChartView.Mstyle;
import work.sdw.R;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WaterflowActivity extends Activity
{

	TextView title;
	String station;
	HorizontalScrollView scroll;
	MyChartView tu0;
	HashMap<Double, Double> map;
	WheelMain wheelMain;
	Button button1;
	Button button2;
	EditText txttime;
	Handler h;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String jsonData1 = null;
	public JSONObject jsonObject1;
	public JSONArray resultArray1 = new JSONArray();

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();// get search
		station = bundle.getString("key_station");

		setContentView(R.layout.waterflow);
		scroll = (HorizontalScrollView) findViewById(R.id.flowscroll);
		float width;
		float dp10 = getResources().getDimension(R.dimen.dp10);
		width = 800 - 2 * dp10;
		title = (TextView) findViewById(R.id.flowtitle);
		title.setText(station + "日处理水量");

		tu0 = (MyChartView) findViewById(R.id.flowlist);

		HashMap<Double, Double> tmp = new HashMap<Double, Double>();
		tu0.SetTuView(tmp, 60.0, 0.0, "", "", false);

		LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams((int) width, LinearLayout.LayoutParams.MATCH_PARENT);
		tu0.setLayoutParams(params0);
		tu0.setMargint(20);
		tu0.setMarginb(50);
		tu0.setMstyle(Mstyle.Line);

		this.h = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if (msg.what >= 100 && msg.what < 105)
					tu0.invalidate();
			}

		};
        button1=(Button)findViewById(R.id.yearWater);
        button2=(Button)findViewById(R.id.monthWater);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
		txttime = (EditText) findViewById(R.id.txttime);
		txttime.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(WaterflowActivity.this);
				final View timepickerview = inflater.inflate(R.layout.timepicker, null);
				ScreenInfo screenInfo = new ScreenInfo(WaterflowActivity.this);
				wheelMain = new WheelMain(timepickerview);
				wheelMain.screenheight = screenInfo.getHeight();
				String time = txttime.getText().toString();
				Calendar calendar = Calendar.getInstance();
				if (JudgeDate.isDate(time, "yyyy-MM-dd"))
				{
					try
					{
						calendar.setTime(dateFormat.parse(time));
					} catch (ParseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				wheelMain.initDateTimePicker(year, month, day);
				new AlertDialog.Builder(WaterflowActivity.this).setTitle("选择时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						txttime.setText(wheelMain.getTime());
						Log.e("121", "" + (wheelMain.wv_year.getCurrentItem() + 1990));
						Log.e("122", "" + (wheelMain.wv_month.getCurrentItem() + 1));
						Log.e("123", "" + (wheelMain.wv_day.getCurrentItem() + 1));
						new Thread()
						{
							public void run()
							{
								super.run();
								Looper.prepare();
								// 配置ip和端口号
								String urllll = LoginActivity.dia;
								String url;
								Log.e("inside thread", "thread~~~");
								if (urllll == null)
								{
									url = "http://58.215.202.186:8082/APPS/servlet/GetWaterFlowInfo";
								} else
								{
									url = "http://58.215.202.186:8082/APPS/servlet/GetWaterFlowInfo";
								}

								List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
								paramList.add(HttpKeyValue.create("stationName", station));
								paramList.add(HttpKeyValue.create("Flag", "Day"));
								paramList.add(HttpKeyValue.create("Year", "" + (wheelMain.wv_year.getCurrentItem() + 1990)));
								paramList.add(HttpKeyValue.create("Month", "" + (wheelMain.wv_month.getCurrentItem() + 1)));
								paramList.add(HttpKeyValue.create("Date", "" + (wheelMain.wv_day.getCurrentItem() + 1)));
								try
								{
									jsonData1 = HttpUtil.post(url, paramList);
									// jsonData1 = HttpSocket.HttpGet(url);

								} catch (Exception e)
								{
									e.printStackTrace();
								}
								Log.e("jsondate11111222111", jsonData1);
								if (jsonData1.equals("error"))
								{
									Toast.makeText(WaterflowActivity.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
									return;
								} else
								{
									try
									{
										jsonObject1 = new JSONObject(jsonData1);
										Log.e("resultarraylisrt1", jsonObject1.toString());
										resultArray1 = jsonObject1.getJSONArray("result");
									} catch (JSONException e)
									{
										e.printStackTrace();
									}
									if (resultArray1.length() != 0 && resultArray1.opt(0) != null)
									{
										JSONObject json00 = (JSONObject) resultArray1.opt(0);
										Log.e("json00", json00.toString());
										HashMap<Double, Double> map = new HashMap<Double, Double>();

										double max = 0.0;
										double minD = 0.0;
										try
										{
											max = json00.getDouble("water");
											minD = json00.getDouble("water");
											Log.e("max", "" + max);
										} catch (JSONException e)
										{
											e.printStackTrace();
										}
										for (int j = 0; j < resultArray1.length(); ++j)
										{
											JSONObject json = (JSONObject) resultArray1.opt(j);
											double time = 0;
											double value = 0.0;
											try
											{
												time = json.getDouble("hour");
												Log.e("time", "" + time);
												value = json.getDouble("water");
											} catch (JSONException e)
											{
												e.printStackTrace();
											}

											map.put(time, value);
											Log.e("map", map.toString());

											if (max < value)
												max = value;
											if (minD > value)
												minD = value;
										}

										tu0.SetTuView(map, max, minD, "", "", false);
										h.sendEmptyMessage(100);
									}
									Looper.loop();
								}
							}
						}.start();

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).show();
			}
		});

		Log.e("edittext date", txttime.getText().length() + "");
	}
	OnClickListener onClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			Log.e("View", v.toString());

			int id = v.getId();
			Log.e("id", "" + id);
			if (id == R.id.yearWater)
			{// 年
				Intent intent = new Intent(WaterflowActivity.this, sdw.com.WaterFlowMY.class);
				Bundle bundle = new Bundle();
				bundle.putString("year", "1");
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (id == R.id.monthWater)				
			{// 月份
				Intent intent = new Intent(WaterflowActivity.this, sdw.com.WaterFlowMY.class);
				Bundle bundle = new Bundle();
				bundle.putString("year", "2");
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} 
		
		}
	};
}
