package sdw.com;

import work.sdw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SysconfigActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysconfig);
		Button btncountycfg = (Button) findViewById(R.id.countycfg);// county cofig
		btncountycfg.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("config", "county");			
				Intent intent = new Intent(SysconfigActivity.this, sdw.com.ListConfigActivity.class);//
				intent.putExtras(bundle);
				startActivity(intent);
//				finish();
			}
		});
		Button btnadmtorcfg = (Button) findViewById(R.id.admtorcfg);// admin config
		btnadmtorcfg.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("config", "admin");
				Intent intent = new Intent(SysconfigActivity.this, sdw.com.ListConfigActivity.class);//
				intent.putExtras(bundle);
				startActivity(intent);
				//finish();
			}
		});
		Button btnstationcfg = (Button) findViewById(R.id.stationcfg);// station conifg
		btnstationcfg.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle(0);
				bundle.putString("config", "station");
				Intent intent = new Intent(SysconfigActivity.this, sdw.com.ListConfigActivity.class);//
				intent.putExtras(bundle);
				startActivity(intent);
//				finish();
			}
		});
    }
}