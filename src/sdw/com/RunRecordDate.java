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
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import work.sdw.R;

public class RunRecordDate extends Activity {
	ListView mListView1;
	MyAdapter myAdapter;
	RelativeLayout mHead;
	LinearLayout main;
	TextView text1;
	String station;
    Button button1;
    Button button2;
	static String transferValue1;
	public JSONObject jsonObject;
	public JSONArray resultArray=new JSONArray();
	public int tempState;
    private static String TAG = "MainActivity";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		transferValue1 = bundle.getString("search");// get search
		station=bundle.getString("key_station");
		
			setContentView(R.layout.runrecord);
			button1=(Button)findViewById(R.id.RRsz);//add button  8.9
			button1.setOnClickListener(onClickListener);//
			button2=(Button)findViewById(R.id.RRt);//
			button2.setOnClickListener(onClickListener);//
			
			
			mHead = (RelativeLayout) findViewById(R.id.head);			
			mHead.setBackgroundResource(R.color.listHeadColor);
			mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
			mListView1 = (ListView) findViewById(R.id.listView1);
			//mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
			
					
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
						 url ="http://58.215.202.186:8082/APPS/servlet/EquipmentStateServlet";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/EquipmentStateServlet";
					}
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
					if(jsonData1=="error"){
						Toast.makeText(RunRecordDate.this, "访问服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
						return;
					}
					Log.e("RunRecordDate~~~",jsonData1 );
			 /* analyze jesonDate */
						try
						{
							jsonObject = new JSONObject(jsonData1);
							resultArray = jsonObject.getJSONArray("result");
							Log.e("RunRecordDate~~~1", resultArray.toString());
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					System.out.println(resultArray.length());
					Log.d("RunRecord1212121212", resultArray.toString());
				}			
			}.start();
			
			myAdapter = new MyAdapter(this, R.layout.item);
			mListView1.setAdapter(myAdapter);

			mListView1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent motionevent) {
					HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
							.findViewById(R.id.horizontalScrollView1);
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
					.findViewById(R.id.horizontalScrollView1);
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
				synchronized (RunRecordDate.this) {
					convertView = mInflater.inflate(id_row_layout, null);
					holder = new ViewHolder();

					MyHScrollView scrollView1 = (MyHScrollView) convertView
							.findViewById(R.id.horizontalScrollView1);
					holder.scrollView = scrollView1;
					holder.txt = (TextView) convertView
							.findViewById(R.id.column);
					holder.txt1 = (TextView) convertView
							.findViewById(R.id.column1);
					holder.txt2 = (TextView) convertView
							.findViewById(R.id.column2);
					holder.txt3 = (TextView) convertView
							.findViewById(R.id.column3);
					holder.txt4 = (TextView) convertView
							.findViewById(R.id.column4);
					holder.txt5 = (TextView) convertView
							.findViewById(R.id.column5);
					
					MyHScrollView headSrcrollView = (MyHScrollView) mHead
							.findViewById(R.id.horizontalScrollView1);
					headSrcrollView
							.AddScrollViewObserver(new ScrollViewObserverImp(
									scrollView1));
					convertView.setTag(holder);
					mHolderList.add(holder);
				}
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Log.e("RunRecord13333333", resultArray.toString());
			if(resultArray.length()!=0 && resultArray.opt(position)!=null)
			{
				JSONObject json = (JSONObject) resultArray.opt(position);
				try
				{	
					String temp=json.getString("testingtime");
					Log.e("runRecordtime", temp);
					holder.txt.setText(temp);
                holder.txt.setTextSize(10);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                holder.txt.setTextSize(10);//设置字体大小
				String tempString[] = new String[6];
				for(int j=1;j<=5;++j)
				{
					try
					{
						int tempState=json.getInt("equipment"+j+"state");
						if(j!=5){
							 switch(tempState){
	                         case 1:
	                             tempString[j] = "运行";
	                             break;
	                         case 2:
	                             tempString[j] = "停止";
	                             break;
	                         case 3:
	                             tempString[j] = "故障";
	                             break;
	                         default :
	                             tempString[j] = "未安装";
	                         }
	                     }else{
	                         switch (tempState) {
	                             case 0:
	                                 tempString[j] = "低";
	                                 break;
	                             case 1:
	                                 tempString[j] = "高";
	                                 break;
	                             default:
	                                 tempString[j] = "异常";
	                                 break;
	                         }
	                     }
					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			holder.txt1.setText(tempString[1]);
			holder.txt2.setText(tempString[2]);
			holder.txt3.setText(tempString[3]);
			holder.txt4.setText(tempString[4]);
			holder.txt5.setText(tempString[5]);

			final TextView t = holder.txt;
			final TextView t1 = holder.txt1;
			final TextView t2 = holder.txt2;
			final TextView t3 = holder.txt3;
			final TextView t4 = holder.txt4;
			final TextView t5 = holder.txt5;
			
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
            
			}else{
				holder.txt.setText(null);
				holder.txt1.setText(null);
				holder.txt2.setText(null);
				holder.txt3.setText(null);
				holder.txt4.setText(null);
				holder.txt5.setText(null);

				final TextView t = holder.txt;
				final TextView t1 = holder.txt1;
				final TextView t2 = holder.txt2;
				final TextView t3 = holder.txt3;
				final TextView t4 = holder.txt4;
				final TextView t5 = holder.txt5;
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
			if (id == R.id.RRsz)
			{// 水质监测记录
				Intent intent = new Intent(RunRecordDate.this, sdw.com.CheckRecordDate.class);
				Bundle bundle = new Bundle();
				bundle.putString("key_station",station);
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (id == R.id.RRt)
			{// 数据统计图
				Intent intent = new Intent(RunRecordDate.this, sdw.com.LineViewActivity.class);
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
