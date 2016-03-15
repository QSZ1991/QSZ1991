package sdw.com;

import work.sdw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DeviceActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device);
		Intent intent=this.getIntent();
		Bundle bundle = intent.getExtras();
   	    
		final String  transferValue= bundle.getString("fun");

		Button btnRunRecord = (Button) findViewById(R.id.btnRunRecord);// �������Button����
		btnRunRecord.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeviceActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				 Bundle bundle = new Bundle(0);  
	             bundle.putString("fun1",transferValue);
	             Log.e("TAG",transferValue);
	             bundle.putString("search1","btnRunRecord");  
	             intent.putExtras(bundle);//can't 
				startActivity(intent);
//				finish();
			}
		});
		Button btnCheckRecord = (Button) findViewById(R.id.btnCheckRecord);// ��ü��Button����
		btnCheckRecord.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeviceActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				 Bundle bundle = new Bundle(0);  
	             bundle.putString("fun1",transferValue);  
	             bundle.putString("search1","btnCheckRecord");
	             intent.putExtras(bundle);//
				startActivity(intent);
				//finish();
			}
		});
		Button btnCheckMap = (Button) findViewById(R.id.btnCheckMap);// ��ü��Button����
		btnCheckMap.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeviceActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				 Bundle bundle = new Bundle(0);  
	             bundle.putString("fun1",transferValue);  
	             bundle.putString("search1","btnCheckMap");
	             intent.putExtras(bundle);//
				startActivity(intent);
				//finish();
			}
		});
		Button btnWaterFlow = (Button) findViewById(R.id.btnWaterFlow);// ��ü��Button����
		btnWaterFlow.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeviceActivity.this, sdw.com.CountyActivity.class);// ����������Activity
				 Bundle bundle = new Bundle(0);  
	             bundle.putString("fun1",transferValue);  
	             bundle.putString("search1","btnWaterFlow");
	             intent.putExtras(bundle);//
				startActivity(intent);
				//finish();
			}
		});
    }
}