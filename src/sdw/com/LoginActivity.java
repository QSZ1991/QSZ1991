package sdw.com;


import work.sdw.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
public class LoginActivity extends Activity
{
	
	String urllll=null;
	static SharedPreferences preference;
	static SharedPreferences.Editor editor;
	EditText a1,a2;
	private Button btnLogin;//
	String infos,portos;
	static  String dia;
	public void seting(View source)
	{    
	final LinearLayout setinfo=(LinearLayout) getLayoutInflater().inflate(R.layout.diaview,null);	
	//a1=(EditText) findViewById(R.id.ipset);
	//a2=(EditText) findViewById(R.id.portset);
    new AlertDialog.Builder(this)
	.setTitle("IP和端口配置")
	.setView(setinfo).
    setPositiveButton("更新配置" , new OnClickListener()
	{
			@Override
		public void onClick(DialogInterface dialog,
	    int which)
		 {	
			preference=getSharedPreferences("setinfos",1);// MODE_WORLD_READABLE);
			editor=preference.edit();
			a1=(EditText)setinfo.findViewById(R.id.ipset);
			a2=(EditText)setinfo.findViewById(R.id.portset);
//			a1.setText("58.215.202.186");
//		    a2.setText("8082");
			Log.e("aaaaaaa", a1.toString());
			infos=a1.getText().toString();
		    portos=a2.getText().toString();
	        dia=infos+":"+portos;
		    Toast.makeText(LoginActivity.this, dia,Toast.LENGTH_LONG).show();
			editor.putString("ipport", dia);
			editor.commit();
				
			}
		})
		//builder.setPositiveButton();
		.setNegativeButton("取消配置", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog,
					int which)
			{	
			}
		})
		.create()
		.show();
}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		preference=getSharedPreferences("setinfos", MODE_WORLD_READABLE);
	
		//加载主界面
		setContentView(R.layout.login);
		btnLogin = (Button) findViewById(R.id.btnLogin);// 获取button组件引用
		btnLogin.setOnClickListener(new Button.OnClickListener()//为button添加listener
		{

			public void onClick(View v)
			{
				new Thread()
				{
					public void run()
					{
						super.run();
						Looper.prepare();
						// TODO Auto-generated method stub
						// login();
						EditText etUserN = (EditText) findViewById(R.id.etUserN);

						EditText etPwd = (EditText) findViewById(R.id.etPwd);

						String username = etUserN.getEditableText().toString().trim();

						String password = etPwd.getEditableText().toString().trim();

						Intent intent = new Intent(LoginActivity.this, sdw.com.FunActivity.class);
						
														startActivity(intent);
//														finish();
//						if (username.equals("") || password.equals(""))
//						{
//							Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
//
//						} else
//						{// 172.18.139.146:8080
														
//				urllll=LoginActivity.dia;
//			String url;
//			if(urllll==null){
//       url ="http://58.215.202.186:8082/APPS/servlet/LoginServlet?username=";
//			}
//			else {
//			 url = "http://"+urllll+"/APPS/servlet/LoginServlet?username=";
//		    }								
//							String url = "http://172.18.139.146:8082/APPS/servlet/LoginServlet?username=";
//							url += username;
//							url += "&password=";
//							url += password;
//							if (HttpSocket.getJsonContent(url).equals("0"))
//
//								Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
//							
//							else
//							{
//								Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
//								
//								Intent intent = new Intent(LoginActivity.this, work.sdw.FunActivity.class);
//
//								startActivity(intent);
//
//								finish();
//							}
//						}
						Looper.loop();
					}
				}.start();
			}

		});
		Button btnReg = (Button) findViewById(R.id.btnBack);// »ñµÃ·µ»ØButton¶ÔÏó

		btnReg.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sdw, menu);
		return true;
	}

}
