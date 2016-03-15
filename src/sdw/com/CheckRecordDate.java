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

import android.R.integer;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import work.sdw.R;
import work.sdw.R.string;

public class CheckRecordDate extends Activity {
	ListView mListView1;
	MyAdapter myAdapter;
	RelativeLayout mHead;
	LinearLayout main;
	TextView text1;
	String station;
	Button button1;
	Button button2;
	public JSONObject jsonObject;
	public JSONArray resultArray=new JSONArray();
	public int tempState;
    private static String TAG = "MainActivity";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();// get search
		station=bundle.getString("key_station");
			setContentView(R.layout.checkrecord);
			button1=(Button)findViewById(R.id.CRyx);//add button  8.9
			button1.setOnClickListener(onClickListener);//
			button2=(Button)findViewById(R.id.CRt);//
			button2.setOnClickListener(onClickListener);//
			mHead = (RelativeLayout) findViewById(R.id.head1);		
			mHead.setBackgroundResource(R.color.listHeadColor);
			mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
			mListView1 = (ListView) findViewById(R.id.listView11);
			//mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
			
			new Thread()
			{
				public void run()
				{
					super.run();
					Looper.prepare();
//					DetectionServlet
					//配置ip和端口号
					String urllll=LoginActivity.dia;
					String url;
					if(urllll==null){
						 url ="http://58.215.202.186:8082/APPS/servlet/DetectionServlet";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/DetectionServlet";
					}
					//String url = "http://58.215.202.186:8082/APPS/servlet/DetectionServlet";
					List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
						paramList.add(HttpKeyValue.create("stationName",station));
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
						Toast.makeText(CheckRecordDate.this, "访问服务器失败，检查网络", Toast.LENGTH_SHORT).show();
						return;
					}
					Log.e("CheckRecordDate~~~",jsonData1 );
					/* analyze jesonDate */
						try
						{
							jsonObject = new JSONObject(jsonData1);
							resultArray = jsonObject.getJSONArray("result");
							Log.e("CheckRecordDate~~~1", resultArray.toString());
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					System.out.println(resultArray.length());
					Log.d("resul21212121212", resultArray.toString());
				}
			
			}.start();
			
			myAdapter = new MyAdapter(this, R.layout.item1);
			mListView1.setAdapter(myAdapter);

			mListView1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent motionevent) {
					HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
							.findViewById(R.id.horizontalScrollView11);
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
					.findViewById(R.id.horizontalScrollView11);
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
				synchronized (CheckRecordDate.this) {
					convertView = mInflater.inflate(id_row_layout, null);
					holder = new ViewHolder();

					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView11);
					holder.scrollView = scrollView1;
					holder.txt = (TextView) convertView
							.findViewById(R.id.colum);
					holder.txt1 = (TextView) convertView
							.findViewById(R.id.colum1);
					holder.txt2 = (TextView) convertView
							.findViewById(R.id.colum2);
					holder.txt3 = (TextView) convertView
							.findViewById(R.id.colum3);
					holder.txt4 = (TextView) convertView
							.findViewById(R.id.colum4);
					holder.txt5 = (TextView) convertView
							.findViewById(R.id.colum5);
					holder.txt6 = (TextView) convertView
							.findViewById(R.id.colum6);
					
					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView11);
					headSrcrollView
							.AddScrollViewObserver(new ScrollViewObserverImp(
									scrollView1));
					convertView.setTag(holder);
					mHolderList.add(holder);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Log.e("CHECKRecord13333333", resultArray.toString());
			if(resultArray.length()!=0 && resultArray.opt(position)!=null)
			{
				JSONObject json = (JSONObject) resultArray.opt(position);
				try
				{	
					String tim=json.getString("testingtime");
					Log.e("time=====", tim);
					holder.txt.setText(tim);
                holder.txt.setTextSize(10);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double tempint[] = new double[7];
				for(int j=1;j<=6;++j)
				{
						try
						{
							tempint[j]=json.getDouble("detection"+j);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			
			holder.txt1.setText(""+tempint[1]);
			holder.txt2.setText(""+tempint[2]);
			holder.txt3.setText(""+tempint[3]);
			holder.txt4.setText(""+tempint[4]);
			holder.txt5.setText(""+tempint[5]);
			holder.txt6.setText(""+tempint[6]);
			
			final TextView t = holder.txt;
			final TextView t1 = holder.txt1;
			final TextView t2 = holder.txt2;
			final TextView t3 = holder.txt3;
			final TextView t4 = holder.txt4;
			final TextView t5 = holder.txt5;
			final TextView t6 = holder.txt6;
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
            t5.setOnClickListener(new OnClickListener() {
	
	            @Override
	            public void onClick(View v) {
		             // TODO Auto-generated method stub
	           }
            });
            t6.setOnClickListener(new OnClickListener() {
            	
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
				holder.txt5.setText(null);
				holder.txt6.setText(null);
				
				final TextView t = holder.txt;
				final TextView t1 = holder.txt1;
				final TextView t2 = holder.txt2;
				final TextView t3 = holder.txt3;
				final TextView t4 = holder.txt4;
				final TextView t5 = holder.txt5;
				final TextView t6 = holder.txt6;
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
			TextView txt5;
			TextView txt6;
			HorizontalScrollView scrollView;
		}
	}// end class my Adapter
	OnClickListener onClickListener = new OnClickListener()
	{

		public void onClick(View v)
		{
			Log.e("View", v.toString());

			int id = v.getId();
			Log.e("id", "" + id);
			if (id == R.id.CRyx)
			{// 设备运行记录
				Intent intent = new Intent(CheckRecordDate.this, sdw.com.RunRecordDate.class);
				Bundle bundle = new Bundle();
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (id == R.id.CRt)
			{// 数据统计图
				Intent intent = new Intent(CheckRecordDate.this, sdw.com.LineViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} 
		
		}
	};
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
