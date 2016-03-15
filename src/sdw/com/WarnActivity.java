package sdw.com;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdw.com.others.MyHScrollView;
import sdw.com.others.MyHScrollView.ScrollViewObserver;

import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import work.sdw.R;

public class WarnActivity extends Activity {
	ListView mListView1;
	MyAdapter myAdapter;
	RelativeLayout mHead;
	LinearLayout main;
	TextView text1;
	String station;
	String requestFlag;//
	List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
	public JSONObject jsonObject;
	public JSONArray resultArray=new JSONArray();
	public int tempState;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();// get search
		requestFlag=bundle.getString("requestFlag");//
		station=bundle.getString("key_station");//
			setContentView(R.layout.warn);									
			mHead = (RelativeLayout) findViewById(R.id.head0);		
			mHead.setBackgroundResource(R.color.listHeadColor);
			mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
			mListView1 = (ListView) findViewById(R.id.listView0);	
			
			paramList.add(HttpKeyValue.create("RealName", "顾杨"));

		if (requestFlag.equals("RA"))
		{
			paramList.add(HttpKeyValue.create("requestFlag", requestFlag));
		} else if (requestFlag.equals("RC"))
		{
			paramList.add(HttpKeyValue.create("requestFlag", requestFlag));
			paramList.add(HttpKeyValue.create("countyName", station));
		} else
		{
			paramList.add(HttpKeyValue.create("requestFlag", requestFlag));
			paramList.add(HttpKeyValue.create("stationName", station));
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
					 url ="http://58.215.202.186:8082/APPS/servlet/AlertQuery";
				}
				else {
					 url = "http://"+urllll+"/APPS/servlet/AlertQuery";
				}

				/* get the httpResponse */
				String jsonData1 = null;
				try
				{
					jsonData1 = HttpUtil.post(url, paramList);
					Log.e("tag1111warndata", jsonData1.toString());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (jsonData1.equals("error"))
				{
					Toast.makeText(WarnActivity.this, "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
					return;
				}
				/* analyze jesonDate */
				try
				{
					jsonObject = new JSONObject(jsonData1);
					resultArray = jsonObject.getJSONArray("result");
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("warndata12121", resultArray.toString());
				System.out.println(resultArray.length());
			}

		}.start();
			
			myAdapter = new MyAdapter(this, R.layout.item3);
			mListView1.setAdapter(myAdapter);

			mListView1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent motionevent) {
					HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
							.findViewById(R.id.horizontalScrollView0);
					headSrcrollView.onTouchEvent(motionevent);
					
					return false;
				}
			});
			mListView1.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> listView, View view, int position,
						long arg3) {
					MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) view.getTag();
					holder.txt1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub						
						}
					});					
					myAdapter.setSelectedPosition(position);
					myAdapter.notifyDataSetInvalidated();   
				}
			});
			
		}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			//当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView0);
			headSrcrollView.onTouchEvent(arg1);
			return false;
		}
	}

	public class MyAdapter extends BaseAdapter {
		public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

		int id_row_layout;
		LayoutInflater mInflater;
		
		private int selectedPosition = -1;// 选中的位置

		public MyAdapter(Context context, int id_row_layout) {
			super();
			this.id_row_layout = id_row_layout;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 250;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public void setSelectedPosition(int position) {
			this.selectedPosition = position;
		}
	
		@SuppressWarnings("unused")
		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			ViewHolder holder = null;
			if (convertView == null) {
				synchronized (WarnActivity.this) {
					convertView = mInflater.inflate(id_row_layout, null);
					holder = new ViewHolder();

					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView0);
					holder.scrollView = scrollView1;
					holder.txt = (TextView) convertView
							.findViewById(R.id.colu);
					holder.txt1 = (TextView) convertView
							.findViewById(R.id.colu1);
					holder.txt2 = (TextView) convertView
							.findViewById(R.id.colu2);
					holder.txt3 = (TextView) convertView
							.findViewById(R.id.colu3);
					holder.txt4 = (TextView) convertView
							.findViewById(R.id.colu4);
					
					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView0);
					headSrcrollView
							.AddScrollViewObserver(new ScrollViewObserverImp(
									scrollView1));
					convertView.setTag(holder);
					mHolderList.add(holder);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
		
			if(resultArray.length()!=0 && resultArray.opt(position)!=null)
			{
				JSONObject json = (JSONObject) resultArray.opt(position);
				try
				{
					
					Log.e("alerttime", json.getString("alertTime"));
					String tim=json.getString("alertTime");
//					tim=tim.substring(tim.indexOf(' ', 1));
//					Log.e("time=====", tim);
					holder.txt.setText(tim);
					holder.txt.setTextSize(10);
					Log.e("shortTitle", json.getString("shortTitle"));
					holder.txt1.setText(json.getString("shortTitle"));
					Log.e("alertInfo", json.getString("alertInfo"));
					holder.txt2.setText(json.getString("alertInfo"));
					Log.e("state",""+json.getString("state"));
					int data=json.getInt("state");
					if(data<=0){
					holder.txt3.setText("未处理");}
					else{
						holder.txt3.setText("已处理");
					}
					holder.txt4.setText("顾杨");
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			final TextView t = holder.txt;
			final TextView t1 = holder.txt1;
			final TextView t2 = holder.txt2;
			final TextView t3 = holder.txt3;
			final TextView t4 = holder.txt4;
			t.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
			
			t1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
			
			t2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
			
			t3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
            t4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
			}else{
				holder.txt.setText(null);
				holder.txt1.setText(null);
				holder.txt2.setText(null);
				holder.txt3.setText(null);
				holder.txt4.setText(null);
			
				
				final TextView t = holder.txt;
				final TextView t1 = holder.txt1;
				final TextView t2 = holder.txt2;
				final TextView t3 = holder.txt3;
				final TextView t4 = holder.txt4;
			}
			int intervalColor1 = getResources().getColor(R.color.intervalColor1);
			int intervalColor2 = getResources().getColor(R.color.intervalColor2);
			int[] colors = { intervalColor1, intervalColor2};//RGB颜色 
			convertView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
			if (selectedPosition == position) {
				convertView.setBackgroundColor(Color.GREEN);

			}   

			return convertView;
		}

		class ScrollViewObserverImp implements ScrollViewObserver {
			MyHScrollView mScrollViewArg;

			public ScrollViewObserverImp(MyHScrollView scrollViewar) {
				mScrollViewArg = scrollViewar;
			}

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				mScrollViewArg.smoothScrollTo(l, t);
			}
		};

		class ViewHolder{
		
			TextView txt;
			TextView txt1;
			TextView txt2;
			TextView txt3;
			TextView txt4;
			HorizontalScrollView scrollView;
		}
	}// end class my Adapter
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action=ev.getAction();
        String actionName="";
        switch(action)
        {
        case MotionEvent.ACTION_DOWN:
            actionName="ACTION_DOWN";
            break;
        case MotionEvent.ACTION_MOVE:
            actionName="ACTION_MOVE";
            break;
        case MotionEvent.ACTION_UP:
            actionName="ACTION_UP";
            break;
        }
		return super.dispatchTouchEvent(ev);
	}

}
