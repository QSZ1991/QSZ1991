package sdw.com;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;

import sdw.com.MyChartView;
import sdw.com.MyChartView.Mstyle;
import sdw.com.others.Tools;
import work.sdw.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class LineViewActivity extends Activity {

	TextView title;
	MyChartView tu0;
	MyChartView tu1;
	MyChartView tu2;
	MyChartView tu3;
	MyChartView tu4;
	TextView time;
	TextView detection1;
	TextView detection2;
	TextView detection3;
	TextView detection4;
	TextView detection5;
	TextView m3m3;
	TextView codcod;
	TextView nh3hnh3h;
	TextView pp;
	List<MyChartView> views = new ArrayList<MyChartView>();
	List<TextView> lastview = new ArrayList<TextView>();
	LinearLayout layout;
	HorizontalScrollView scroll;
	Button button1;
	Button button2;
	
	Handler h;
	Handler handler;
	Button BT_Add;
	Timer mTimer =new Timer();
	HashMap<Double, Double> map;
	Double key=8.0;
	Double value=0.0;
	String station;
	public JSONObject jsonObject1;
	public JSONArray resultArray1=new JSONArray();
	public JSONObject jsonObject2;
	public JSONArray resultArray2=new JSONArray();
	Tools tool=new Tools();
	String[] lastRe=new String[10];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();  
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();// get search
		station = bundle.getString("key_station");
		//Log.e("station11111", station);
		
		// dingjun
		layout = (LinearLayout)findViewById(R.id.layout);
		scroll = (HorizontalScrollView)findViewById(R.id.scroll);
		WindowManager wm = this.getWindowManager();
	    float width = wm.getDefaultDisplay().getWidth();
	    float dp10 = getResources().getDimension(R.dimen.dp10);
	    width = width - 2*dp10;
	    title = (TextView) findViewById(R.id.titleS);
		title.setText(station);

		button1=(Button)findViewById(R.id.Tyx);
		button1.setOnClickListener(onClickListener);////		
		button2=(Button)findViewById(R.id.Tsz);
		button2.setOnClickListener(onClickListener);//
		
		tu0 = (MyChartView)findViewById(R.id.menulist0);
		tu1 = (MyChartView)findViewById(R.id.menulist1);
		tu2 = (MyChartView)findViewById(R.id.menulist2);
		tu3 = (MyChartView)findViewById(R.id.menulist3);
		tu4 = (MyChartView)findViewById(R.id.menulist4);
               
    	views.add(tu0);
		views.add(tu1);
		views.add(tu2);
		views.add(tu3);
		views.add(tu4);
		
		
		
		
		for(int i=0; i<5; i++) {
			HashMap<Double, Double> tmp = new HashMap<Double, Double>();
			double hour = Calendar.getInstance().get(Calendar.HOUR);
			for(int j=0; j<hour+1; j++) {
				tmp.put((double)j, 0d);
			}
			
			views.get(i).SetTuView(tmp,60.0,20.0,"", "", false);
		}
		
		
		LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT);
	    tu0.setLayoutParams(params0);
		tu0.setMargint(20);
		tu0.setMarginb(50);
		tu0.setMstyle(Mstyle.Line);
		
		
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT);
		tu1.setLayoutParams(params1);
    	tu1.setMargint(20);
    	tu1.setMarginb(50);
    	tu1.setMstyle(Mstyle.Line);
    	
    	
    	
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT);
		tu2.setLayoutParams(params2);
    	tu2.setMargint(20);
    	tu2.setMarginb(50);
    	tu2.setMstyle(Mstyle.Line);
    	
    	
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT);
		tu3.setLayoutParams(params3);
    	tu3.setMargint(20);
    	tu3.setMarginb(50);
    	tu3.setMstyle(Mstyle.Line);
    	
    	
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams((int)width, LinearLayout.LayoutParams.MATCH_PARENT);
		tu4.setLayoutParams(params4);
    	tu4.setMargint(20);
    	tu4.setMarginb(50);
    	tu4.setMstyle(Mstyle.Line); 
	
    	this.h = new Handler() {
    		public void handleMessage(Message msg) {
    			if(msg.what >= 100 && msg.what < 105)
    				views.get(msg.what-100).invalidate();
    		}
    		
    	};
    	
		new Thread()
		{
			public void run()
			{
				
				super.run();
				Looper.prepare();
				// 配置ip和端口号
				String urllll = LoginActivity.dia;
				String url, url1;
				if (urllll == null)
				{
					url = "http://58.215.202.186:8082/APPS/servlet/ChartDrawing";
					url1 = "http://58.215.202.186:8082/APPS/servlet/GetStatistic";
				} else
				{
					url = "http://" + urllll + "/APPS/servlet/ChartDrawing";
					url1 = "http://" + urllll + "/APPS/servlet/GetStatistic";
				}

				//String url = "http://58.215.202.186:8082/APPS/servlet/ChartDrawing";
				List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
				//Log.e("station", station);
				paramList.add(HttpKeyValue.create("stationName",station));

				String jsonData1 = null;
				String jsonData2 = null;
				try {
					jsonData1 = HttpUtil.post(url, paramList);
					jsonData2 = HttpUtil.post(url1, paramList);
					//Log.e("jsondate2",jsonData2 );
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (jsonData1.equals("error"))
				{
					Toast.makeText(LineViewActivity.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
					return;
				} else
				{
					try
					{
						jsonObject1 = new JSONObject(jsonData1);
						resultArray1 = jsonObject1.getJSONArray("result");
						jsonObject2 = new JSONObject(jsonData2);
						resultArray2 = jsonObject2.getJSONArray("result");
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
					if (resultArray1.isNull(0))
					{
						Toast.makeText(LineViewActivity.this, "该站点无最近数据！", Toast.LENGTH_SHORT).show();
						return;
					}
					//System.out.println(resultArray1.length());
					//System.out.println(resultArray1.toString());
					JSONObject json01 = (JSONObject) resultArray1.opt(0);
					JSONObject json02 = (JSONObject)resultArray2.opt(0);
					//Log.e("json01010101", json01.toString());
					try
					{
						lastRe[0] = json01.getString("testingtime");
					} catch (JSONException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (int i = 1; i < 10; i++)
					{
						try
						{
							if(i<6)
							{
                                if(i==4)
                                {
                                	if (json01.getDouble("detection" + i) > 0)
									{
										lastRe[i] = "高";
									} else
									{
										lastRe[i] = "低";
									}
                                }else
								{
									lastRe[i] = "" + json01.getDouble("detection" + i);
								}								
							} else
							{
								switch (i)
								{
								case 6:
									lastRe[i] = "" + json02.getString("waterFlow");
									break;
								case 7:
									lastRe[i] = "" + json02.getString("reduceCOD");
									break;
								case 8:
									lastRe[i] = "" + json02.getString("reduceNH3N");
									break;
								case 9:
									lastRe[i] = "" + json02.getString("reduceP");
									break;
								default:
									break;
								}
							}
							//lastRe[0] = json01.getString("testingtime");
							//lastRe[i] = "" + json01.getDouble("detection" + i);
						} catch (JSONException e)
						{
							e.printStackTrace();
						}

					}					
					// 创建属于主线程的handler

					handler.post(runnableUi);
					JSONObject json00 = (JSONObject) resultArray1.opt(1);
				//	Log.e("json01111", json00.toString());
					for (int i = 1; i < 6; ++i)
					{
						HashMap<Double, Double> map = new HashMap<Double, Double>();

						double max = 0.0;
						double minD = 0.0;

//						JSONObject json00 = (JSONObject) resultArray1.opt(1);
//						Log.e("json01111", json00.toString());
						try
						{
							max = json00.getDouble("detection" + i);
							minD = json00.getDouble("detection" + i);
						} catch (JSONException e)
						{
							e.printStackTrace();
						}
						for (int j = 1; j < resultArray1.length(); ++j)
						{
							JSONObject json = (JSONObject) resultArray1.opt(j);
							String tempkey = "detection" + i;
							double time = 0;
							double value = 0.0;
							try
							{
								time = json.getDouble("testingtime");
								value = json.getDouble(tempkey);
							} catch (JSONException e)
							{
								e.printStackTrace();
							}

							map.put(time, value);

							if (max < value)
								max = value;
							if (minD > value)
								minD = value;
						}

//						Log.e("max" + i, "" + max);
//						Log.e("min" + i, "" + minD);
						Log.e("map" + i, map.toString());
						views.get(i - 1).SetTuView(map, max, minD, "", "", false);
						h.sendEmptyMessage(100 + i - 1);
					//	Log.i("char", "char" + (i - 1));
					}
				}
			}
		
		}.start();

	}
	OnClickListener onClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			Log.e("View", v.toString());

			int id = v.getId();
			Log.e("id", "" + id);
			if (id == R.id.Tsz)
			{// 设备运行记录
				Intent intent = new Intent(LineViewActivity.this, sdw.com.CheckRecordDate.class);
				Bundle bundle = new Bundle();
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (id == R.id.Tyx)
			{// 数据统计图
				Intent intent = new Intent(LineViewActivity.this,sdw.com.RunRecordDate.class);
				Bundle bundle = new Bundle();
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} 
		
		}
	};
	// 构建Runnable对象，在runnable中更新界面  
    Runnable   runnableUi=new  Runnable(){  
        @Override  
        public void run() {  
            //更新界面  
			/*Find View*/
    		//最新的一组数据
    		time =(TextView)findViewById(R.id.timetim);
    		detection1=(TextView)findViewById(R.id.tt);
    		detection2=(TextView)findViewById(R.id.phph);
    		detection3=(TextView)findViewById(R.id.orporp);
    		detection4=(TextView)findViewById(R.id.lsls);
    		detection5=(TextView)findViewById(R.id.dodo);
    		m3m3 = (TextView) findViewById(R.id.m3m3);
			codcod = (TextView) findViewById(R.id.codcod);
			nh3hnh3h = (TextView) findViewById(R.id.nh3hnh3h);
			pp = (TextView) findViewById(R.id.pp);

    	lastview.add(detection1);
    	lastview.add(detection2);
    	lastview.add(detection3);
    	lastview.add(detection4);
    	lastview.add(detection5);
    	lastview.add(m3m3);
		lastview.add(codcod);
		lastview.add(nh3hnh3h);
		lastview.add(pp);

			/* 这个是数组string类型的数组 */
    	System.out.println("数据数据"+lastRe);
    	System.out.println(lastRe[0]+lastRe[1]+lastRe[2]+lastRe[3]+lastRe[4]+lastRe[5]);
			time.setText(lastRe[0]);
		    for(int i=1;i<10;i++){
		    	lastview.get(i-1).setText(lastRe[i]);
		    }
        }  
          
    };  
}
