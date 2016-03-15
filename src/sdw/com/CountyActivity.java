package sdw.com;

import java.util.List;

import work.sdw.R;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CountyActivity extends Activity /*implements OnClickListener*/
{
	private Button btnC=null;  
    private ListView lv=null;  
    private List<String> list =null;
    private Handler handler=null; 
    static String  transferValu;
    static String  transferValu1;
    private Button btnTotal ;
    private LinearLayout layout;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		 //创建属于主线程的handler  
         handler = new Handler();  
		super.onCreate(savedInstanceState);
		Intent intent=this.getIntent();
		Bundle bundle = intent.getExtras();

		transferValu = bundle.getString("fun1");// get date
		Log.e("TAGDATE", transferValu);
		transferValu1 = bundle.getString("search1");// get date1
		Log.e("TAGDATE1", transferValu1);
		
		setContentView(R.layout.county);
		if (transferValu.equals("btnWarn"))
		{
			layout = (LinearLayout) findViewById(R.id.LinCounty);
			btnTotal = new Button(this);
			btnTotal.setText("所有区县预警");
			layout.addView(btnTotal);
			
			
			btnTotal.setOnClickListener(new View.OnClickListener() {  
		           
		        public void onClick(View v) {  
		        	Intent intent = new Intent(CountyActivity.this, sdw.com.WarnActivity.class);
					 Bundle bundle = new Bundle();  
		             bundle.putString("requestFlag","RA");
		             bundle.putString("key_station","");  
		             intent.putExtras(bundle);
					 startActivity(intent);		       
		        }  
		           
		        });  		
		}
		
		
//		setContentView(R.layout.county);
//		layout=(LinearLayout)findViewById(R.id.LinCounty);
//		 btnTotal = new Button(this);
//	        btnTotal.setText("所有区县预警");
//	        layout.addView(btnTotal);
//		((Button) findViewById(R.id.btn_left)).setOnClickListener(this);
		
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
					 url ="http://58.215.202.186:8082/APPS/servlet/AreaServlet";
				}
				else {
					 url = "http://"+urllll+"/APPS/servlet/AreaServlet";
				}
				
//				现场服务器http://58.215.202.186:8081/APPS/servlet/AreaServlet
//				String url = "http://58.215.202.186:8082/APPS/servlet/AreaServlet";
				/*get the httpResponse*/
				String jsonData1 = HttpSocket.HttpGet(url);	
				
				if (jsonData1.equals("error"))
				{
					Log.e("jsondataRUNRECORD", jsonData1);
					Toast.makeText(getApplicationContext(), jsonData1, Toast.LENGTH_SHORT).show();
					//Toast.makeText(CountyActivity.this, "连接失败,检查网络是否可用", Toast.LENGTH_SHORT).show();
					return;
				}
				else{
					/*analyze jesonDate*/
					Log.e("jsondata2", jsonData1);
					list =JsonTools.getList("result", jsonData1);
					Log.e("aaa", list.toString());
					handler.post(runnableUi);
				}
				lv=(ListView) findViewById(R.id.listC); 
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
					{
						// TODO Auto-generated method stub
//						"北塘","滨湖","崇安","惠山","锡山"
							 Intent intent = new Intent(CountyActivity.this, sdw.com.StationActivity.class);
							 Bundle bundle = new Bundle();  
				             bundle.putString("fun2",transferValu);
				             bundle.putString("search2",transferValu1);
				             bundle.putString("key_county", list.get(arg2));  
				             intent.putExtras(bundle);
							 startActivity(intent);
//							 finish();
					}
				});
			     
				btnC = (Button) findViewById(R.id.btnC);// 获得button对象，并添加监听
				btnC.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						/* 获得输入的区县 */
						EditText edtCounty = (EditText) findViewById(R.id.edtCounty);
						String county = edtCounty.getEditableText().toString().trim();
						int check=0;
						for (int i = 0; i < list.size(); i++)
						{
							if (county.equals(list.get(i)))
							{
								check++;
								Intent intent = new Intent(CountyActivity.this, sdw.com.StationActivity.class);// 
								 Bundle bundle = new Bundle();  
					             bundle.putString("fun2",transferValu);  
					             bundle.putString("search2", transferValu1); 
					             bundle.putString("key_county",county); 
					             intent.putExtras(bundle);
								startActivity(intent);
//								finish();
								break;
							} 
						}
						if (check==0){
							Toast.makeText(CountyActivity.this, "输入区县有误！", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
			}
		}.start();
		

	}

	   // 构建Runnable对象，在runnable中更新界面  
	    Runnable   runnableUi=new  Runnable(){  
	        @Override  
	        public void run() {  
	            //更新界面  
				/*Find listView*/
				 lv=(ListView) findViewById(R.id.listC); 
				/* 这个是数组string类型的数组 */
				ArrayAdapter<String> adapter = new ArrayAdapter<String>( CountyActivity.this,android.R.layout.simple_list_item_1, list);
				/*add and display*/
				lv.setAdapter(adapter);
				/*add click*/; 
	        }  
	          
	    };  
}
