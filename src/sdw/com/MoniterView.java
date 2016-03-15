package sdw.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import work.sdw.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoniterView extends RelativeLayout
{
	// 人工格栅
	/** 上次确认格栅时间 */
	private TextView a1;
	/** 当前状态 */
	private TextView a2;
	/** 格栅间隔天数 */
	private TextView a3;
	/** 警报操作 */
	private Button alarm0;
	// 爆气机
	/** 设备当前控制状态 */
	private TextView b1;
	/** 当前状态 */
	private TextView b2;
	/** 警报操作 */
	private Button alarm;
	// 污水泵
	/** 设备当前控制状态 */
	private TextView c1;
	/** 当前状态 */
	private TextView c2;
	/** 警报操作 */
	private Button alarm1;
	// 回流泵
	/** 设备当前控制状态 */
	private TextView d1;
	/** 当前状态 */
	private TextView d2;
	/** 警报操作 */
	private Button alarm2;
	// T
	/** 仪表取值范围 */
	private TextView e1;
	/** 仪表当前值 */
	private TextView e2;
	/** 警报操作 */
	private Button meterTalarm;
	// PH
	/** 仪表取值范围 */
	private TextView f1;
	/** 仪表当前值 */
	private TextView f2;
	/** 警报操作 */
	private Button meterPHalarm;
	// ORP
	/** 仪表取值范围 */
	private TextView g1;
	/** 仪表当前值 */
	private TextView g2;
	/** 警报操作 */
	private Button meterORPalarm;
	// LS
	/** 仪表取值范围 */
	private TextView h1;
	/** 仪表当前值 */
	private TextView h2;
	/** 警报操作 */
	private Button meterLSalarm;
	// DO(MG)
	/** 仪表取值范围 */
	private TextView i1;
	/** 仪表当前值 */
	private TextView i2;
	/** 警报操作 */
	private Button meterDOalarm;
	// 流量
	/** 仪表取值范围 */
	private TextView j1;
	/** 仪表当前值 */
	private TextView j2;

	String station;
	String releaseWarning;
	String response;
	public JSONObject jsonObject;

	public MoniterView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MoniterView(Context context, AttributeSet attr)
	{
		super(context, attr);
		// TODO Auto-generated constructor stub

	}

	public void viewInit()
	{
		a1 = (TextView) findViewById(R.id.montiter_lastTime);
		a2 = (TextView) findViewById(R.id.montiter_currentstate);
		a3 = (TextView) findViewById(R.id.montiter_intervaldate);
		alarm0 = (Button) findViewById(R.id.montiter__alarm0);
		alarm0.setOnClickListener(onClickListener);
		b1 = (TextView) findViewById(R.id.montier_devicecontrol);
		b2 = (TextView) findViewById(R.id.moniter_devicestate);
		alarm = (Button) findViewById(R.id.montiter__alarm);
		alarm.setOnClickListener(onClickListener);
		c1 = (TextView) findViewById(R.id.montier_devicecontrol1);
		c2 = (TextView) findViewById(R.id.moniter_devicestate1);
		alarm1 = (Button) findViewById(R.id.montiter__alarm1);
		alarm1.setOnClickListener(onClickListener);
		d1 = (TextView) findViewById(R.id.montier_devicecontrol2);
		d2 = (TextView) findViewById(R.id.moniter_devicestate2);
		alarm2 = (Button) findViewById(R.id.montiter__alarm2);
		alarm2.setOnClickListener(onClickListener);
		e1 = (TextView) findViewById(R.id.montier_meterT);
		e2 = (TextView) findViewById(R.id.moniter_meterTstate);
		meterTalarm = (Button) findViewById(R.id.montiter__meterTalarm);
		meterTalarm.setOnClickListener(onClickListener);
		f1 = (TextView) findViewById(R.id.montier_meterph);
		f2 = (TextView) findViewById(R.id.moniter_meterPHstate);
		meterPHalarm = (Button) findViewById(R.id.montiter__meterPHalarm);
		meterPHalarm.setOnClickListener(onClickListener);
		g1 = (TextView) findViewById(R.id.montier_meterORP);
		g2 = (TextView) findViewById(R.id.moniter_meterORPstate);
		meterORPalarm = (Button) findViewById(R.id.montiter__meterORPalarm);
		meterORPalarm.setOnClickListener(onClickListener);
		h1 = (TextView) findViewById(R.id.montier_meterLS);
		h2 = (TextView) findViewById(R.id.moniter_meterLSstate);
		meterLSalarm = (Button) findViewById(R.id.montiter__meterLSalarm);
		meterLSalarm.setOnClickListener(onClickListener);
		i1 = (TextView) findViewById(R.id.montier_meterDO);
		i2 = (TextView) findViewById(R.id.moniter_meterDOstate);
		meterDOalarm = (Button) findViewById(R.id.montiter__meterDOalarm);
		meterDOalarm.setOnClickListener(onClickListener);
		j1 = (TextView) findViewById(R.id.montier_meterFlow);
		j2 = (TextView) findViewById(R.id.moniter_meterFlowstate);
	}

	public void initData(JSONArray data)
	{
		if (data.isNull(0))
		{
			// Toast.makeText(getContext(), "连接服务器失败，请检查网络连接！",
			// Toast.LENGTH_SHORT).show();
			return;
		}

		TextView a[] =
		{ a1, a2, a3 };
		TextView b[] =
		{ b1, b2 };
		TextView c[] =
		{ c1, c2 };
		TextView d[] =
		{ d1, d2 };
		TextView e[] =
		{ e1, e2 };
		TextView f[] =
		{ f1, f2 };
		TextView g[] =
		{ g1, g2 };
		TextView h[] =
		{ h1, h2 };
		TextView i[] =
		{ i1, i2 };
		TextView j[] =
		{ j1, j2 };

		ArrayList<TextView[]> list = new ArrayList<TextView[]>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		list.add(f);
		list.add(g);
		list.add(h);
		list.add(i);
		list.add(j);

		// 转换为二维数组
		TextView arryTwo[][] = (TextView[][]) list.toArray(new TextView[0][0]);

		// 获得数据
		JSONObject json = (JSONObject) data.opt(0);
		String gratingTime = null;
		int gratingDays = 0;
		try
		{
			gratingTime = json.getString("confirmGratingTime");
			gratingDays = json.getInt("gratingDays");
		} catch (JSONException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		Log.e("gratingTime-------", gratingTime);
		System.out.println(gratingDays);
		for (int m = 0; m < arryTwo.length; m++)
		{
			for (int n = 0; n < arryTwo[m].length; n++)
			{
				if (m == 0)
				{
					switch (n)
					{
					case 0:
						if (gratingTime == null || gratingTime.equals(""))
							a1.setText("1970-01-01 00:00:00(默认)");
						else
						{
							a1.setText(gratingTime);
						}
						break;
					case 1:
					{
						if (gratingTime == null || gratingTime.equals(""))
						{
							a2.setText("未初始化格栅时间");
						} else
						{

							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
							long end = System.currentTimeMillis();// 获取当前时间
							Date begin = null;
							try
							{
								begin = formatter.parse(gratingTime);
							} catch (ParseException e3)
							{
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							long beginT = begin.getTime();
							int dxday = ((int) (end - beginT)) / (1000 * 3600 * 24);
							if (dxday > gratingDays)
							{
								a2.setText("报警：超过设定格栅时间" + (dxday - gratingDays) + "天");
							} else
							{
								a2.setText("正常");
							}
						}
					}
						break;
					case 2:
						a3.setText("" + gratingDays);
						break;
					default:
						break;
					}
				} else if (m >= 1 && m <= 3)// 设备
				{
					String tempKey = ("equipment" + m + "state");
					String tempControl = null;
					String stata = null;
					try
					{
						tempControl = json.getString("control_strategy");

						stata = json.getString(tempKey);
					} catch (JSONException e3)
					{
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					String controlInfo = null;
					String stateInfo = null;
					char sta[] = stata.toCharArray();// 把字符串转成字符数组
					char tempC[] = tempControl.toCharArray();
					// 设备当前状态一栏取值分类
					switch (sta[0])
					{
					case '1':
						stateInfo = "设备正常［运行］";
						break;
					case '2':
						stateInfo = "设备正常［停止］";
						break;
					case '3':
						stateInfo = "设备故障 ";
						break;
					case '4':
						stateInfo = "未选择";
						break;
					}
					// 设备当前控制方式按设备分类：1 曝气机，2 污水泵，3 回流泵
					if (tempC.length != 5)
					{
						controlInfo = "未设置控制方式";
					} else
					{
						switch (m)
						{
						case 1:
							switch (tempC[m - 1])
							{
							case '1':
								controlInfo = "手动控制";
								break;
							case '2':
								controlInfo = "时间控制";
								break;
							case '3':
								controlInfo = "溶解氧控制";
								break;
							}
							break;
						case 2:
							switch (tempC[m - 1])
							{
							case '1':
								controlInfo = "手动控制";
								break;
							case '2':
								controlInfo = "时间控制";
								break;
							// case '3':
							// controlInfo = "溶解氧控制";
							// break;
							}
							break;
						case 3:
							switch (tempC[m - 1])
							{
							case '1':
								controlInfo = "手动控制";
								break;
							case '2':
								controlInfo = "自动控制";
								break;
							// case '3':
							// controlInfo = @"溶解氧控制";
							// break;
							}
							break;
						}
					}
					// 以下开始填表
					switch (n)
					{
					case 0:
						arryTwo[m][0].setText(controlInfo);
						break;
					case 1:
						arryTwo[m][1].setText(stateInfo);
						break;
					default:
						break;
					}
				}
				// 以下是4～9仪表传感器
				else
				{
					String tempdect = ("detection" + (m - 3));
					String tempdectdl = ("detection" + (m - 3) + "dl");
					String tempdectul = ("detection" + (m - 3) + "ul");
					String alertInfo = null;
					String dect = null;
					String dectdl = null;
					String dectul = null;
					String deviceAlert = null;
					String equipment5 = null;
					try
					{
						dect = json.getString(tempdect);
						dectdl = json.getString(tempdectdl);
						dectul = json.getString(tempdectul);
						deviceAlert = json.getString("device_alert");
						equipment5 = json.getString("equipment5state");
					} catch (JSONException e3)
					{
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					char deviceA[] = deviceAlert.toCharArray();// 把字符串转成字符数组
					char eq5[] = equipment5.toCharArray();//
					if (deviceAlert.length() != 5)
					{
						alertInfo = "(无警报信息)";
					} else
					{
						switch (n)
						{
						case 0:
							arryTwo[m][0].setText(dectdl + "~" + dectul);
							break;
						case 1:
						{
							switch (m)
							{
							case 4:
								switch (deviceA[m - 4])
								{
								case '1':
									alertInfo = "(正常)";
									break;
								case '2':
									alertInfo = "(警告：温度高于设定值)";
									break;
								case '3':
									alertInfo = "(警告：温度低于设定值)";
									break;
								}
								e2.setText(dect + alertInfo);
								break;
							case 5:
								switch (deviceA[m - 4])
								{
								case '1':
									alertInfo = "(正常)";
									break;
								case '2':
									alertInfo = "(警告：PH高于设定值)";
									break;
								case '3':
									alertInfo = "(警告：PH低于设定值)";
									break;
								}
								f2.setText(dect + alertInfo);
								break;
							case 6:
								switch (deviceA[m - 4])
								{
								case '1':
									alertInfo = "(正常)";
									break;
								case '2':
									alertInfo = "(警告：ORP高于设定值)";
									break;
								case '3':
									alertInfo = "(警告：ORP低于设定值)";
									break;
								}
								g2.setText(dect + alertInfo);
								break;
							case 7:
								switch (eq5[0])
								{
								case '0':
									h2.setText("低");
									break;
								case '1':
									h2.setText("高");
									break;
								default:
									h2.setText("异常");
									break;
								}
								break;
							case 8:
								switch (deviceA[m - 4])
								{
								case '1':
									alertInfo = "(正常)";
									break;
								case '2':
									alertInfo = "(警告：DO高于设定值)";
									break;
								case '3':
									alertInfo = "(警告：DO低于设定值)";
									break;
								}
								i2.setText(dect + alertInfo);
								break;
							case 9:
								j2.setText(dect);
								break;
							}
						}
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}

	OnClickListener onClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			Log.e("View", v.toString());

			int id = v.getId();
			Log.e("id", "" + id);
			if (id == R.id.montiter__alarm0)
			{// 人工格栅 响应“解除警报”操作
				releaseWarning = "confirmGrating";
			} else if (id == R.id.montiter__alarm)
			{// 曝气机 响应“解除警报”操作
				releaseWarning = "releaseAirWarning";
			} else if (id == R.id.montiter__alarm1)
			{// 污水泵 响应“解除警报”操作
				releaseWarning = "releaseElevatorWarning";
			} else if (id == R.id.montiter__alarm2)
			{// 回流泵 响应“解除警报”操作
				releaseWarning = "releaseMudWarning";
			} else if (id == R.id.montiter__meterTalarm)
			{// 温度传感器 响应“解除警报”操作
				releaseWarning = "releaseTemWarning";
			} else if (id == R.id.montiter__meterPHalarm)
			{// PH传感器 响应“解除警报”操作
				releaseWarning = "releasePHWarning";
			} else if (id == R.id.montiter__meterORPalarm)
			{// ORP传感器 响应“解除警报”操作
				releaseWarning = "releaseORPWarning";
			} else if (id == R.id.montiter__meterLSalarm)
			{// LS传感器 响应“解除警报”操作
				releaseWarning = "releaseLSWarning";
			} else if (id == R.id.montiter__meterDOalarm)
			{// DO传感器 响应“解除警报”操作
				releaseWarning = "releaseDOWarning";
			}
			Log.e("releaseWarning", releaseWarning);
			new Thread()
			{
				public void run()
				{
					super.run();
					Looper.prepare();

					// 配置ip和端口号
					String urllll = LoginActivity.dia;
					String url;
					if (urllll == null)
					{
						url = "http://58.215.202.186:8082/APPS/servlet/EquipmentsRemoteControl";
					} else
					{
						url = "http://" + urllll + "/APPS/servlet/EquipmentsRemoteControl";
					}
					// String url =
					// "http://58.215.202.186:8082/APPS/servlet/EquipmentsRemoteControl";
					List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
					paramList.add(HttpKeyValue.create("stationName", station));
					paramList.add(HttpKeyValue.create("controlString", releaseWarning));
					response = HttpUtil.post(url, paramList);
					Log.e("response.........", response);
					Message message = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("tagRES", response);
					message.setData(b);
					handler.sendMessage(message);
				}
			}.start();

		}
	};

	Handler handler = new Handler()
	{
		public void handleMessage(Message m)
		{
			if (m.getData().getString("tagRES").equals("error"))
			{
				Toast.makeText(getContext(), "访问服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
				return;
			}
			if (m.getData().getString("tagRES").equals("0"))
			{
				Toast.makeText(MoniterView.this.getContext(), "解除警报失败，请重试！", Toast.LENGTH_SHORT).show();
				Log.w("hello", "hello");
			} else
			{

				Toast.makeText(MoniterView.this.getContext(), "警报成功解除！", Toast.LENGTH_SHORT).show();
				Log.w("hello2", "hello2");
			}
		}
	};
}
