package sdw.com;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import sdw.com.bundle.CountyBundleObject;

import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;
import work.sdw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CountyConfig extends Activity
{

	/** 编辑模式 */
	private ToggleButton toggleb;
	/** 区县名称 */
	private EditText countyming;
	/** 负责人 */
	private EditText fuzeren;
	/** 警报操作 */
	private EditText fuzephone;
	/** 关于 */
	private EditText about;
	/** 提交 */
	private Button submit;
	static String _requestFlag;
	JSONObject textFieldGroup = new JSONObject();
	public String tempkey;
	public String[] b;
	List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
	static String response = null;
	JSONObject json;
	CountyBundleObject object;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		_requestFlag = bundle.getString("RequestFlag");// get FLAG
		setContentView(R.layout.countyconfig);
		Log.e("TAGDATE", _requestFlag);
		if (_requestFlag.equals("U"))
		{
			object = (CountyBundleObject) getIntent().getSerializableExtra("listitem");// get
			Log.e("object1111", object.toString());// jsondate
			Log.e("Principal()1111111111111111", object.getPrincipal());
			object.getSuperiorArea();
			object.getIntro();
			json = new JSONObject();
			try
			{
				json.put("areaId",object.getAreaId());
				json.put("name", object.getName());
				json.put("principal", object.getPrincipal());
				json.put("tel", object.getTel());
				json.put("intro", object.getIntro());
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Log.e("TAGDATE1", json.toString());
		}
		vInit();
	}

	public void vInit()
	{
		toggleb = (ToggleButton) findViewById(R.id.togglemod);
		/*
		 * 因为ToggleButton组件继承自CompoundButton，在代码中可以通过实现CompoundButton.
		 * OnCheckedChangeListener接口，并 实现其内部类的onCheckedChanged来监听状态变化。
		 */
		toggleb.setOnCheckedChangeListener(listener);
		countyming = (EditText) findViewById(R.id.countyming);
		fuzeren = (EditText) findViewById(R.id.fuzeren);
		fuzephone = (EditText) findViewById(R.id.Rphone);
		about = (EditText) findViewById(R.id.about);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(onClickListener);
		try
		{
			textFieldGroup.put("name", countyming);
			textFieldGroup.put("principal", fuzeren);
			textFieldGroup.put("tel", fuzephone);
			textFieldGroup.put("intro", about);
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b = new String[]
		{ "name", "principal", "tel", "intro" };
		if (_requestFlag.equals("U"))
		{
			for (int i = 0; i < b.length; i++)
			{
				tempkey = b[i];
				try
				{
					((EditText) textFieldGroup.get(tempkey)).setEnabled(false);
					String tempvalue = json.getString(tempkey);
					((EditText) textFieldGroup.get(tempkey)).setText(tempvalue);
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (_requestFlag.equals("C"))
		{
			toggleb.setChecked(true);
			for (int i = 0; i < b.length; i++)
			{
				tempkey = b[i];
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
			LinearLayout myLayOut = (LinearLayout) getLayoutInflater().inflate(R.layout.managerconfig, null);
			if (isChecked)
			{
				for (int i = 0; i < b.length; i++)
				{
					tempkey = b[i];
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

				for (int i = 0; i < b.length; i++)
				{
					tempkey = b[i];
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

			if (toggleb.isChecked())
			{
				if (countyming.getText().toString().length() == 0)
				{
					Toast.makeText(CountyConfig.this, "地区名称不能为空", Toast.LENGTH_SHORT).show();
				}else {

				new Thread()
				{
					public void run()
					{
						super.run();
						String urllll = LoginActivity.dia;
						String url;
						if (urllll == null)
						{
							url = "http://58.215.202.186:8082/APPS/servlet/AreaConfig";
						} else
						{
							url = "http://" + urllll + "/APPS/servlet/AreaConfig";
						}

						// String
						// url="http://58.215.202.186:8082/APPS/servlet/AreaConfig";
						
						paramList.add(HttpKeyValue.create("requestFlag", _requestFlag));
						// paramList.add(HttpKeyValue.create("name",
						// toggleb.getText().toString()));
						for (int i = 0; i < b.length; i++)
						{
							String tempkey = b[i];
							try
							{
								
								paramList.add(HttpKeyValue.create(tempkey, ((EditText) textFieldGroup.get(tempkey)).getText().toString()));
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (_requestFlag.equals("U"))
						{
							try
							{
								Log.e("areaid", json.getString("areaId"));
								paramList.add(HttpKeyValue.create("areaId", json.getString("areaId")));
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (_requestFlag.equals("C"))
						{
							paramList.add(HttpKeyValue.create("areaId", "0"));
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
			} else
			{
				Toast.makeText(CountyConfig.this, "请切换至编辑模式进行提交", Toast.LENGTH_SHORT).show();
			}
		}
	};
	Handler handler = new Handler()
	{
		public void handleMessage(Message m)
		{
			System.out.println("tagRES: " + m.getData().getString("tagRES"));
			if (m.getData().getString("tagRES").equals("error"))
			{
				Toast.makeText(CountyConfig.this, "访问服务器出错，请检查网络连接！", Toast.LENGTH_SHORT).show();
			} else
			{
				if (m.getData().getString("tagRES").equals("Update successfully"))
				{
					Toast.makeText(CountyConfig.this, "更新成功!", Toast.LENGTH_SHORT).show();
					;
				}
				if (m.getData().getString("tagRES").equals("Add successfully"))
				{
					Toast.makeText(CountyConfig.this, "更新成功!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Failed"))
				{
					Toast.makeText(CountyConfig.this, "远程服务器故障，更新失败!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Unavailable key update"))
				{
					Toast.makeText(CountyConfig.this, "字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Unavailable key add"))
				{
					Toast.makeText(CountyConfig.this, "字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Have already existed"))
				{
					Toast.makeText(CountyConfig.this, "该记录已经存在，请检查!", Toast.LENGTH_SHORT).show();
				}
			}

		}
	};
}
