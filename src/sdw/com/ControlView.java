package sdw.com;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ToggleButton;

public class ControlView extends RelativeLayout
{

	/** 曝气机 */
	private ToggleButton deviceset_baoqi;
	private TextView baoqiji_Control;
	/** 污水泵 */
	private ToggleButton deviceset_wushuib;
	private TextView wushuib_Control;
	/** 回流泵 */
	private ToggleButton deviceset_huiliub;
	private TextView huiliub_Control;
	/* 提交 */
	private Button deviceset_submit;
	String station;
	String response;
	String tempString = null;
	String tempStates;
	public JSONObject jsonObject;

	public ControlView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ControlView(Context context, AttributeSet attr)
	{
		super(context, attr);
		// TODO Auto-generated constructor stub

	}

	public void viewInit()
	{
		baoqiji_Control = (TextView) findViewById(R.id.baoqiji_Control);
		deviceset_baoqi = (ToggleButton) findViewById(R.id.deviceset_baoqi);

		wushuib_Control = (TextView) findViewById(R.id.wushuib_Control);
		deviceset_wushuib = (ToggleButton) findViewById(R.id.deviceset_wushuib);

		huiliub_Control = (TextView) findViewById(R.id.huiliub_Control);
		deviceset_huiliub = (ToggleButton) findViewById(R.id.deviceset_huiliub);

		deviceset_submit = (Button) findViewById(R.id.deviceset_submit);
		deviceset_submit .setOnClickListener(onClickListener);

	}

	public void initData(JSONArray data)
	{
		if (data.equals("error"))
			{
			   Toast.makeText(getContext(), "连接服务器失败，请检查网络连接！", Toast.LENGTH_SHORT).show();    
			return;
			}
		// 获得数据
		JSONObject json = (JSONObject) data.opt(0);
		
		try
		{
			tempString = (json.getString("equipment1state") + json.getString("equipment2state") + json.getString("equipment3state") + json.getString("equipment4state"));
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char sta[] = tempString.toCharArray();// 把字符串转成字符数组
		switch (sta[0])
		{
		// 曝气机
		case '1':
			deviceset_baoqi.setChecked(true);
			baoqiji_Control.setText("正常运行");
			break;
		case '2':
			deviceset_baoqi.setChecked(false);
			baoqiji_Control.setText("正常停止");
			break;
		case '3':
			deviceset_baoqi.setEnabled(false);
			baoqiji_Control.setText("故障");
			break;
		case '4':
			deviceset_baoqi.setEnabled(false);
			baoqiji_Control.setText("未安装");
			break;
		default:
			break;
		}
		// 污水泵
		switch (sta[1])
		{
		case '1':
			deviceset_wushuib.setChecked(true);
			wushuib_Control.setText("正常运行");
			break;
		case '2':
			deviceset_wushuib.setChecked(false);
			wushuib_Control.setText("正常停止");
			break;
		case '3':
			deviceset_wushuib.setEnabled(false);
			wushuib_Control.setText("故障");
			break;
		case '4':
			deviceset_wushuib.setEnabled(false);
			wushuib_Control.setText("未安装");
			break;
		default:
			break;
		}
		// 回流泵
		switch (sta[2])
		{
		case '1':
			deviceset_huiliub.setChecked(true);
			huiliub_Control.setText("正常运行");
			break;
		case '2':
			deviceset_huiliub.setChecked(false);
			huiliub_Control.setText("正常停止");
			break;
		case '3':
			deviceset_huiliub.setEnabled(false);
			huiliub_Control.setText("故障");
			break;
		case '4':
			deviceset_huiliub.setEnabled(false);
			huiliub_Control.setText("未安装");
			break;
		default:
			break;
		}

	}

	OnClickListener onClickListener = new OnClickListener()
	{
          char temp[] = new char[4];
		public void onClick(View v)
		{
			
			char st[]=tempString.toCharArray();
			Log.e("View", v.toString());
			if (deviceset_baoqi.isEnabled())
			{
				if (deviceset_baoqi.isChecked())
				{
					// 曝气机
					temp[0] = '1';
				} else
				{
					temp[0] = '2';
				}
			} else
			{
				temp[0] = st[0];
			}
			// 污水泵
			if (deviceset_wushuib.isEnabled())
			{
				if (deviceset_wushuib.isChecked())
				{
					temp[1] = '1';
				} else
				{
					temp[1] = '2';
				}
			} else
			{
				temp[1] = st[1];
			}
			// 回流泵
			if (deviceset_huiliub.isEnabled())
			{
				if (deviceset_huiliub.isChecked())
				{
					temp[2] = '1';
				} else
				{
					temp[2] = '2';
				}
			} else
			{
				temp[2] = st[2];
			}
			//
			temp[3] = st[3];
			tempStates=""+temp[0]+temp[1]+temp[2]+temp[3];
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
						 url ="http://58.215.202.186:8082/APPS/servlet/EquipmentsRemoteControl";
					}
					else {
						 url = "http://"+urllll+"/APPS/servlet/EquipmentsRemoteControl";
					}
					//String url = "http://58.215.202.186:8082/APPS/servlet/EquipmentsRemoteControl";
					List<HttpKeyValue> paramList = new ArrayList<HttpKeyValue>();
					paramList.add(HttpKeyValue.create("username", "admin"));
					Log.e("tag",station);
					paramList.add(HttpKeyValue.create("stationName", station));
					paramList.add(HttpKeyValue.create("stateString", tempStates));
					paramList.add(HttpKeyValue.create("controlString", "submitRemoteControl"));
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
			System.out.println("tagRES: " + m.getData().getString("tagRES"));
			if(m.getData().getString("tagRES").equals("error")){
				Toast.makeText(getContext(), "访问服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
				return;
			}
			if(m.getData().getString("tagRES").equals("0"))
			{
				Toast.makeText(ControlView.this.getContext(), "设备运行状态设置失败，请重试！", Toast.LENGTH_SHORT).show();
				Log.w("hello", "hello");
			} else
			{
				
				Toast.makeText(ControlView.this.getContext(), "设备运行状态设置成功！", Toast.LENGTH_SHORT).show();
				Log.w("hello2", "hello2");
			}
		}
	};
}
