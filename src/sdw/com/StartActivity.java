package sdw.com;



import work.sdw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class StartActivity extends Activity{
	 private final int SPLASH_DISPLAY_LENGHT = 2000; 
	    @Override  
	 protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.start);  
	        new Handler().postDelayed(new Runnable() {  
	            public void run() {  
	                Intent mainIntent = new Intent(StartActivity.this, LoginActivity.class);  
	                StartActivity.this.startActivity(mainIntent);  
	                StartActivity.this.finish();  
	            }  
	        }, SPLASH_DISPLAY_LENGHT);  
}}
