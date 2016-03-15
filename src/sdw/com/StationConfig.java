package sdw.com;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import sdw.com.bundle.StationBundleObject;
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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.es.core.util.HttpKeyValue;
import com.es.core.util.HttpUtil;

public class StationConfig extends Activity
{

	private ToggleButton toggleb;
	private EditText countyName;
	private EditText administratorName;
	private EditText controlId;
	private EditText shortTitle;
	private EditText name;
	private EditText address;
	private EditText coordinateX;
	private EditText coordinateY;
	private EditText detection1dl;
	private EditText detection1ul;
	private EditText detection2dl;
	private EditText detection2ul;
	private EditText detection3dl;
	private EditText detection3ul;
	private EditText detection4dl;
	private EditText detection4ul;
	private EditText detection5dl;
	private EditText detection5ul;
	private EditText detection6dl;
	private EditText detection6ul;
	private EditText gratingDays;
	private EditText waterflow;
	private EditText reduceCOD;
	private EditText reduceNH3N;
	private EditText reduceP;
	/** 提交 */
	private Button submit;
	static String _requestFlag;
	static String _dataDic;
	JSONObject textFieldGroup = new JSONObject();
	public String tempkey;
	List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
	private String[] b = new String[]
			{ "countyName", "administratorName", "controlId", "shortTitle", "name", "address", "coordinateX", "coordinateY", "detection1dl", "detection1ul", "detection2dl", "detection2ul", "detection3dl", "detection3ul", "detection4dl", "detection4ul", "detection5dl", "detection5ul", "detection6dl", "detection6ul", "gratingDays", "waterflow", "reduceCOD", "reduceNH3N", "reduceP" };
	static String response = null;
	JSONObject json;
	StationBundleObject object;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		_requestFlag = bundle.getString("RequestFlag");// get FLAG
		setContentView(R.layout.stationconfig);
		Log.e("TAGDATE", _requestFlag);
		if (_requestFlag.equals("U"))
		{
			object = (StationBundleObject) getIntent().getSerializableExtra("listitem");// get
			Log.d("county````", object.getCountyName());
			Log.d("getAdministratorName````", object.getAdministratorName());
			Log.d("getControlId()````", object.getControlId());
			json = new JSONObject();
			try
			{
				json.put("sewageId", object.getSewageId());
				json.put("countyName", object.getCountyName());
				json.put("administratorName", object.getAdministratorName());
				json.put("controlId", object.getControlId());
				json.put("shortTitle", object.getAreaId());
				json.put("name", object.getName());
				json.put("address", object.getAddress());
				json.put("coordinateX", object.getCoordinateX());
				json.put("coordinateY", object.getCoordinateY());
				json.put("detection1dl", object.getDetection1dl());
				json.put("detection1ul", object.getDetection1ul());
				json.put("detection2dl", object.getDetection2dl());
				json.put("detection2ul", object.getDetection2ul());
				json.put("detection3dl", object.getDetection3dl());
				json.put("detection3ul", object.getDetection3ul());
				json.put("detection4dl", object.getDetection4dl());
				json.put("detection4ul", object.getDetection4ul());
				json.put("detection5dl", object.getDetection5dl());
				json.put("detection5ul", object.getDetection5ul());
				json.put("detection6dl", object.getDetection6dl());
				json.put("detection6ul", object.getDetection6ul());
				json.put("gratingDays", object.getGratingDays());
				json.put("waterflow", object.getWaterflow());
				json.put("reduceCOD", object.getReduceCOD());
				json.put("reduceNH3N", object.getReduceNH3N());
				json.put("reduceP", object.getReduceP());
				Log.e("jsonDate`````", json.toString());
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Log.e("TAGDATE1", json.toString());
		}
		vInit();
		// modeChange();
	}

	public void vInit()
	{
		toggleb = (ToggleButton) findViewById(R.id.togglemod);
		/*
		 * 因为ToggleButton组件继承自CompoundButton，在代码中可以通过实现CompoundButton.
		 * OnCheckedChangeListener接口，并 实现其内部类的onCheckedChanged来监听状态变化。
		 */
		toggleb.setOnCheckedChangeListener(listener);

		countyName = (EditText) findViewById(R.id.countyName);
		administratorName = (EditText) findViewById(R.id.administratorName);
		controlId = (EditText) findViewById(R.id.controlId);
		shortTitle = (EditText) findViewById(R.id.shortTitle);
		name = (EditText) findViewById(R.id.name);
		address = (EditText) findViewById(R.id.address);
		coordinateX = (EditText) findViewById(R.id.coordinateX);
		coordinateY = (EditText) findViewById(R.id.coordinateY);
		detection1dl = (EditText) findViewById(R.id.detection1dl);
		detection1ul = (EditText) findViewById(R.id.detection1ul);
		detection2dl = (EditText) findViewById(R.id.detection2dl);
		detection2ul = (EditText) findViewById(R.id.detection2ul);
		detection3dl = (EditText) findViewById(R.id.detection3dl);
		detection3ul = (EditText) findViewById(R.id.detection3ul);
		detection4dl = (EditText) findViewById(R.id.detection4dl);
		detection4ul = (EditText) findViewById(R.id.detection4ul);
		detection5dl = (EditText) findViewById(R.id.detection5dl);
		detection5ul = (EditText) findViewById(R.id.detection5ul);
		detection6dl = (EditText) findViewById(R.id.detection6dl);
		detection6ul = (EditText) findViewById(R.id.detection6ul);
		gratingDays = (EditText) findViewById(R.id.gratingDays);
		waterflow = (EditText) findViewById(R.id.waterflow);
		reduceCOD = (EditText) findViewById(R.id.reduceCOD);
		reduceNH3N = (EditText) findViewById(R.id.reduceNH3N);
		reduceP = (EditText) findViewById(R.id.reduceP);

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(onClickListener);
		try
		{
			textFieldGroup.put("countyName", countyName);
			textFieldGroup.put("administratorName", administratorName);
			textFieldGroup.put("controlId", controlId);
			textFieldGroup.put("shortTitle", shortTitle);
			textFieldGroup.put("name", name);
			textFieldGroup.put("address", address);
			textFieldGroup.put("coordinateX", coordinateX);
			textFieldGroup.put("coordinateY", coordinateY);
			textFieldGroup.put("detection1dl", detection1dl);
			textFieldGroup.put("detection1ul", detection1ul);
			textFieldGroup.put("detection2dl", detection2dl);
			textFieldGroup.put("detection2ul", detection2ul);
			textFieldGroup.put("detection3dl", detection3dl);
			textFieldGroup.put("detection3ul", detection3ul);
			textFieldGroup.put("detection4dl", detection4dl);
			textFieldGroup.put("detection4ul", detection4ul);
			textFieldGroup.put("detection5dl", detection5dl);
			textFieldGroup.put("detection5ul", detection5ul);
			textFieldGroup.put("detection6dl", detection6dl);
			textFieldGroup.put("detection6ul", detection6ul);
			textFieldGroup.put("gratingDays", gratingDays);
			textFieldGroup.put("waterflow", waterflow);
			textFieldGroup.put("reduceCOD", reduceCOD);
			textFieldGroup.put("reduceNH3N", reduceNH3N);
			textFieldGroup.put("reduceP", reduceP);

		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String tempvalue =null;
		if (_requestFlag.equals("U"))
		{
			for (int i = 0; i < b.length; i++)
			{
				tempkey = b[i];
				try
				{
					((EditText) textFieldGroup.get(tempkey)).setEnabled(false);
					//tempvalue = json.getString(tempkey);
					((EditText) textFieldGroup.get(tempkey)).setText(json.getString(tempkey));
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
			defualtDate();
		}
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
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

	public void defualtDate()
	{
		controlId.setText("0");
		coordinateX.setText("0.0");
		coordinateY.setText("0.0");
		detection1dl.setText("0.0");
		detection1ul.setText("30.0");
		detection2dl.setText("0.0");
		detection2ul.setText("9.0");
		detection3dl.setText("-2000.0");
		detection3ul.setText("600.0");
		detection4dl.setText("0.0");
		detection4ul.setText("0.0");
		detection5dl.setText("0.0");
		detection5ul.setText("15.0");
		detection6dl.setText("0.0");
		detection6ul.setText("6.5");
		gratingDays.setText("7");
		waterflow.setText("0.0");
		reduceCOD.setText("200.0");
		reduceNH3N.setText("30.0");
		reduceP.setText("8.0");
	}

	OnClickListener onClickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Log.e("View", v.toString());
			if (toggleb.isChecked())
			{
				if (countyName.getText().toString().length() == 0 || administratorName.getText().toString().length() == 0 || shortTitle.getText().toString().length() == 0 || name.getText().toString().length() == 0)
				{
					Toast.makeText(StationConfig.this, "所属地区、管理员姓名、简介、名称不可为空！", Toast.LENGTH_SHORT).show();
				}else
				{
					new Thread()
					{
						public void run()
						{
							super.run();
							// 配置ip和端口号
							String urllll = LoginActivity.dia;
							String url;
							if (urllll == null)
							{								
								url = "http://58.215.202.186:8082/APPS/servlet/SewageConfig";
							} else
							{
								url = "http://" + urllll + "/APPS/servlet/SewageConfig";
							}
							// String url =
							// "http://58.215.202.186:8082/APPS/servlet/SewageConfig";
							//List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
							paramList.add(HttpKeyValue.create("requestFlag", _requestFlag));
							for (int i = 0; i < b.length; i++)
							{
								 tempkey = b[i];
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
									paramList.add(HttpKeyValue.create("sewageId", json.getString("sewageId")));
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if(_requestFlag.equals("C"))
							{
								paramList.add(HttpKeyValue.create("sewageId", ""+0));
							}
							/* get the httpResponse */
							Log.e("传值", paramList.toString());
							Log.e("url传递", url);
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
				Toast.makeText(StationConfig.this, "请切换至编辑模式进行提交", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(StationConfig.this, "访问服务器出错，请检查网络连接！", Toast.LENGTH_SHORT).show();
			} else
			{
				if (m.getData().getString("tagRES").equals("Update successfully"))
				{
					Toast.makeText(StationConfig.this, "更新成功!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Add successfully"))
				{
					Toast.makeText(StationConfig.this, "更新成功!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Failed"))
				{
					Toast.makeText(StationConfig.this, "远程服务器故障，更新失败!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Unavailable key update"))
				{
					Toast.makeText(StationConfig.this, "字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Unavailable key add"))
				{
					Toast.makeText(StationConfig.this, "字段信息填写错误,请修改!", Toast.LENGTH_SHORT).show();
				}
				if (m.getData().getString("tagRES").equals("Have already existed"))
				{
					Toast.makeText(StationConfig.this, "该记录已经存在，请检查!", Toast.LENGTH_SHORT).show();
				}
			}

		}
	};
}
