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
import work.sdw.R.string;
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

public class WaterFlowMY extends Activity
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
	HashMap<Double, Double> tmp = new HashMap<Double, Double>();
	DateFormat dateFormat = new SimpleDateFormat("yyyy");
	String jsonData1 = null;
	public JSONObject jsonObject1;
	public JSONArray resultArray1 = new JSONArray();
	String flag;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();// get search
		flag = bundle.getString("year");
		station = bundle.getString("key_station");

		setContentView(R.layout.waterflowmy);
		scroll = (HorizontalScrollView) findViewById(R.id.flowscroll1);
		float width;
		float dp10 = getResources().getDimension(R.dimen.dp10);
		width = 800 - 2 * dp10;
		title = (TextView) findViewById(R.id.flowtitle1);
		if (flag.equals("1"))
		{
			title.setText(station + "年处理水量");
		}else {
			title.setText(station + "月处理水量");
		}
		

		tu0 = (MyChartView) findViewById(R.id.flowlist1);

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
		txttime = (EditText) findViewById(R.id.txttime1);
		txttime.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				LayoutInflater inflater = LayoutInflater.from(WaterFlowMY.this);
				if (flag.equals("1"))
				{
					final View timepickerview = inflater.inflate(R.layout.timepickery, null);
					ScreenInfo screenInfo = new ScreenInfo(WaterFlowMY.this);
					wheelMain = new WheelMain(timepickerview);
					wheelMain.screenheight = screenInfo.getHeight();
					String time = txttime.getText().toString();
					Calendar calendar = Calendar.getInstance();
					if (JudgeDate.isDate(time, "yyyy"))
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
					wheelMain.initDateTimePickerY(year);// ///////
					new AlertDialog.Builder(WaterFlowMY.this).setTitle("选择时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							txttime.setText(wheelMain.getTimeY());
							Log.e("121", "" + (wheelMain.wv_year.getCurrentItem() + 1990));

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
									paramList.add(HttpKeyValue.create("Flag", "Year"));
									paramList.add(HttpKeyValue.create("Year", "" + (wheelMain.wv_year.getCurrentItem() + 1990)));
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
										Toast.makeText(WaterFlowMY.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
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
													time = json.getDouble("month");
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
										}else{
											tu0.SetTuView(tmp, 60.0, 0.0, "", "", false);
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
				} else if (flag.equals("2"))
				{
					final View timepickerview = inflater.inflate(R.layout.timepickerm, null);
					ScreenInfo screenInfo = new ScreenInfo(WaterFlowMY.this);
					wheelMain = new WheelMain(timepickerview);
					wheelMain.screenheight = screenInfo.getHeight();
					String time = txttime.getText().toString();
					Calendar calendar = Calendar.getInstance();
					if (JudgeDate.isDate(time, "yyyy-mm"))
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
					wheelMain.initDateTimePickerM(year, month);// ///////
					new AlertDialog.Builder(WaterFlowMY.this).setTitle("选择时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							txttime.setText(wheelMain.getTimeM());
							Log.e("121", "" + (wheelMain.wv_year.getCurrentItem() + 1990));

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
									paramList.add(HttpKeyValue.create("Flag", "Month"));
									paramList.add(HttpKeyValue.create("Year", "" + (wheelMain.wv_year.getCurrentItem() + 1990)));
									paramList.add(HttpKeyValue.create("Month", "" + (wheelMain.wv_month.getCurrentItem() + 1)));
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
										Toast.makeText(WaterFlowMY.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
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
													time = json.getDouble("date");
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
										}else{
											tu0.SetTuView(tmp, 60.0, 0.0, "", "", false);
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

			}

		});

		Log.e("edittext date", txttime.getText().length() + "");
	}

}
