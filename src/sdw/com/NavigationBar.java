package sdw.com;

import work.sdw.R;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;


public class  NavigationBar extends LinearLayout {  
	  
    private Button btn_left;   
    private TextView tv_title;  
  
    public NavigationBar(Context context) {  
        super(context);  
        // TODO Auto-generated constructor stub  
    }  
  
    public NavigationBar(Context context, AttributeSet attrs) {  
        super(context, attrs);  
  
        LayoutInflater layoutInflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        layoutInflater.inflate(R.layout.title_bar, this);  
        btn_left = (Button) findViewById(R.id.btn_left);   
  
    }  
  
    public void setBtnLeftBacground(int resId) {  
  
        if (btn_left != null) {  
            btn_left.setBackgroundResource(resId);  
        }  
    }  
    public void setTvTitle(int resId) {  
        if (tv_title != null) {  
            tv_title.setText(resId);  
        }  
    }  
  
    public Button getBtn_left() {  
        return btn_left;  
    }  

    public TextView getTv_title() {  
        return tv_title;  
    }  
  
} 