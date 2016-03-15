package sdw.com;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import sdw.com.bundle.AdminBundleObject;
import sdw.com.bundle.CountyBundleObject;
import work.sdw.R;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;


public class AdminConfig extends Activity 
{

		/**编辑模式 */
		private ToggleButton toggleb;
		/**区县名称 */
		private EditText countyming;
		/**姓名 */
		private EditText name;
		/** 电话*/
		private EditText phone1;
		/**关于*/
		private EditText address;
		/**邮箱*/
		private EditText mailming;
		/** 提交 */
		private Button submit;
		static String  _requestFlag;
	    static String  _dataDic;
	    JSONObject textFieldGroup = new JSONObject();
	    public String tempkey;
	    public String[] b ;
	    String response ;
	    JSONObject json ;
	    AdminBundleObject object ;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent=this.getIntent();
		Bundle bundle = intent.getExtras();
		_requestFlag = bundle.getString("RequestFlag");// get FLAG
		setContentView(R.layout.managerconfig);
		Log.e("TAGDATE", _requestFlag);     
		Log.e("TAGDATE", _requestFlag);     
		if (_requestFlag.equals("U"))
		{
			object = (AdminBundleObject) getIntent().getSerializableExtra("listitem");// get
			json=new JSONObject();
			try
			{
				json.put("countyName",object.getCountyName());
				json.put("name",object.getName());
				json.put("tel",object.getTel());
				json.put("administratorId",object.getAdministratorId());
				json.put("adr",object.getAdr());
				json.put("email",object.getEmail());
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    vInit();
    }
	public void vInit()
	{
		toggleb=(ToggleButton)findViewById(R.id.togglemod);
		
		 /*因为ToggleButton组件继承自CompoundButton，在代码中可以通过实现CompoundButton.OnCheckedChangeListener接口，并 
        实现其内部类的onCheckedChanged来监听状态变化。*/  
      toggleb.setOnCheckedChangeListener(listener);
  
		
		countyming=(EditText)findViewById(R.id.countyming);
		name=(EditText)findViewById(R.id.nameming);
		phone1=(EditText)findViewById(R.id.phone1);
		address=(EditText)findViewById(R.id.addres);
		mailming=(EditText)findViewById(R.id.mailming);
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(onClickListener);
		try
		{
			textFieldGroup.put("countyName", countyming);
			textFieldGroup.put("name", name);
			textFieldGroup.put("tel",phone1);
			textFieldGroup.put("adr", address);
			textFieldGroup.put("email", mailming);
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         b = new String[]{"countyName","name","tel","adr","email"};
         String tempvalue =null;
		if(_requestFlag.equals("U"))
		{
			for(int i = 0; i < b.length; i++)
			{
				 tempkey=b[i];
				try
				{
					((EditText) textFieldGroup.get(tempkey)).setEnabled(false);					
						tempvalue = json.getString(tempkey);
						((EditText) textFieldGroup.get(tempkey)).setText(tempvalue);
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if (_requestFlag.equals("C"))
		{
			toggleb.setChecked(true);
			for(int i = 0; i < b.length; i++)
			{	
				tempkey=b[i];
				try
				{
					((EditText) textFieldGroup.get(tempkey)).setEnabled(true);
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }	
	}
		
	
	OnCheckedChangeListener listener = new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			if (isChecked)
			{
				for(int i = 0; i < b.length; i++)
				{
					 tempkey=b[i];
						try
						{
							((EditText) textFieldGroup.get(tempkey)).setEnabled(true);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}																		
				}				

				Toast.makeText(getApplicationContext(), "编辑模式开", Toast.LENGTH_SHORT).show();
			} else
			{

				for(int i = 0; i < b.length; i++)
				{
					 tempkey=b[i];
						try
						{
							((EditText) textFieldGroup.get(tempkey)).setEnabled(false);
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}																		
				}

				Toast.makeText(getApplicationContext(), "编辑模式关", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	

	OnClickListener onClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Log.e("View", v.toString());
			if(toggleb.isChecked())
			{
				if(countyming.getText().toString().length()==0||name.getText().toString().length()==0)
				{
					Toast.makeText(AdminConfig.this, "所属地区、管理员姓名不可为空！", Toast.LENGTH_SHORT).show();
				}else{
					   new Thread()
						{
							public void run()
							{
								super.run();
								//配置ip和端口号
								String urllll=LoginActivity.dia;
								String url;
								if(urllll==null){
									 url ="http://58.215.202.186:8082/APPS/servlet/AdministratorConfig";
								}
								else {
									 url = "http://"+urllll+"/APPS/servlet/AdministratorConfig";
								}
								//String url="http://58.215.202.186:8082/APPS/servlet/AdministratorConfig";
								List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
								paramList.add(HttpKeyValue.create("requestFlag", _requestFlag));
								for(int i = 0; i < b.length; i++)
								{
									String tempkey=b[i];
									try
									{
										paramList.add(HttpKeyValue.create(tempkey,((EditText) textFieldGroup.get(tempkey)).getText().toString()));
									} catch (JSONException e)
									{
										// TODO Auto-generated catch block
										e.printStackTrace();
									}	
								}
								if(_requestFlag.equals("U")) {
									    try
										{
											paramList.add(HttpKeyValue.create("administratorId",json.getString("administratorId")));
										} catch (JSONException e)
										{
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
							        }else if(_requestFlag.equals("C")){
							        	paramList.add(HttpKeyValue.create("administratorId", "0"));		            
							        }
								
								/* get the httpResponse */					
							    response = HttpUtil.post(url, paramList);
								 Log.e("response1111", response);
								 Message message = handler.obtainMessage();
									Bundle b = new Bundle();
									b.putString("tagRES", response);
									message.setData(b);
									handler.sendMessage(message);
							}
						}.start();
				}						
			}else{
				Toast.makeText(AdminConfig.this, "请切换至编辑模式进行提交", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	Handler handler = new Handler()
	{
		public void handleMessage(Message m)
		{
			System.out.println("tagRES: " + m.getData().getString("tagRES"));
			if( m.getData().getString("tagRES").equals("error")){
				Toast.makeText(AdminConfig.this, "访问服务器出错，请检查网络连接！", Toast.LENGTH_SHORT).show();					
			}else {
				if ( m.getData().getString("tagRES").equals("Update successfully")) {
			    	Toast.makeText(AdminConfig.this,"更新成功!", Toast.LENGTH_SHORT).show();  
	             ;
	            }
	            if ( m.getData().getString("tagRES").equals("Add successfully")) {
	            	Toast.makeText(AdminConfig.this,"更新成功!", Toast.LENGTH_SHORT).show();  
	            }
	            if ( m.getData().getString("tagRES").equals("Failed")) {
	            	Toast.makeText(AdminConfig.this, "远程服务器故障，更新失败!" , Toast.LENGTH_SHORT).show(); 
	            }
	            if ( m.getData().getString("tagRES").equals("Unavailable key update")) {
	            	Toast.makeText(AdminConfig.this,"字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
	            }
	            if ( m.getData().getString("tagRES").equals("Unavailable key add")) {
	            	Toast.makeText(AdminConfig.this,"字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
	            }
	            if ( m.getData().getString("tagRES").equals("Have already existed")) {
	            	Toast.makeText(AdminConfig.this,"该记录已经存在，请检查!", Toast.LENGTH_SHORT).show();    
	            }
			}
		    
		}
	};
	
}
