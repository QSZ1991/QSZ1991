package sdw.com;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author 
 *
 */
@SuppressLint("ViewConstructor")
class MyChartView extends View{
	
    public static final int RECT_SIZE = 10;  
    private Point mSelectedPoint;  
    
    //枚举实现坐标桌面的样式风格
    public static enum Mstyle
    {
    	Line,Curve
    }

    private Mstyle mstyle=Mstyle.Line;
    private Point[] mPoints = new Point[100];  
      
    Context context;
    int bheight=0;
    HashMap<Double, Double> map;
    ArrayList<Double> dlk;
    double totalvalue=10.0;
    double minvalue=2.0;
//    int pjvalue=1;
    String xstr,ystr="";//横纵坐标的属性
    int margint=15;
    int marginb=40;
    int c=0;
    int resid=0;
    Boolean isylineshow;
   
    /**
    * @param map 需要的数据，虽然key是double，但是只用于排序和显示，与横向距离无关
    * @param totalvalue Y轴的最大值
    * @param pjvalue Y平均值
    * @param xstr X轴的单位
    * @param ystr Y轴的单位
    * @param isylineshow 是否显示纵向网格
     * @return 
    */
    public void SetTuView(HashMap<Double, Double> map,double totalvalue,double minvalue,String xstr,String ystr,Boolean isylineshow) 
    {
        this.map = map;
        this.totalvalue = totalvalue;
this.minvalue=minvalue;
//        this.pjvalue = pjvalue;
        this.xstr = xstr;
        this.ystr = ystr;
        this.isylineshow = isylineshow;
        
        //屏幕横向
//        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }  

    public MyChartView(Context ct)
    {
    	super(ct);
		this.context = ct;
    }
    
    public MyChartView(Context ct, AttributeSet attrs)
	{
		super( ct, attrs );
		this.context = ct;
	}
    
    public MyChartView(Context ct, AttributeSet attrs, int defStyle) 
	{		 
		super( ct, attrs, defStyle );
		this.context = ct;
	}
    
    @SuppressLint("DrawAllocation")
	@Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        if(c!=0)
        	this.setbg(c);
        if(resid!=0)
        	this.setBackgroundResource(resid);
        dlk=getintfrommap(map);
        int height=getHeight();
        if(bheight==0)
        	bheight=height-marginb;

        int width=getWidth();
        int blwidh=dip2px(context,50);
int pjsize=9;           //totalvalue/pjvalue;界面布局的尺寸的比例
        // set up paint  
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        paint.setColor(Color.GRAY);  
        paint.setStrokeWidth(1);  
        paint.setStyle(Style.STROKE);
        for(int i=0;i<pjsize+1;i++)//将顶点的线变为红色的  警戒线
        {
        	
        	if(i==pjsize)
        		paint.setColor(Color.RED);
        	canvas.drawLine(blwidh,bheight-(bheight/pjsize)*i+margint,width,bheight-(bheight/pjsize)*i+margint, paint);//Y坐标
        	BigDecimal bd =new BigDecimal((((totalvalue-minvalue)/pjsize)*i));   
        	  bd   =   bd.setScale(3,BigDecimal.ROUND_HALF_UP);
        	  System.out.println(bd);
        	  BigDecimal b = BigDecimal.valueOf(minvalue);
        	  System.out.println(b);
        	  bd= bd.add(b);
        	drawline(bd+"", blwidh/2, bheight-(bheight/pjsize)*i+margint, canvas);
        }
        ArrayList<Integer> xlist=new ArrayList<Integer>();//记录每个x的值
        //画直线（纵向）
        paint.setColor(Color.GRAY);
        if(dlk==null)
        	return;
        for(int i=0;i<dlk.size();i++)
        {
        	xlist.add(blwidh+(width-blwidh)/dlk.size()*i);
        	if(isylineshow)
        	{
        		canvas.drawLine(blwidh+(width-blwidh)/dlk.size()*i,margint,blwidh+(width-blwidh)/dlk.size()*i,bheight+margint, paint);
        	}
        	System.out.println(dlk.get(i));
                 int a= ((int)(dlk.get(i)*10))/10;
        	drawline(a+xstr, blwidh+(width-blwidh)/dlk.size()*i, bheight+40, canvas);//X坐标
        }

        //点的操作设置
        mPoints=getpoints(dlk, map, xlist, totalvalue,minvalue, bheight);
        
        paint.setColor(Color.GREEN);  
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(0);
        
    		drawline(mPoints, canvas, paint);
    	
        paint.setColor(Color.RED);  
        paint.setStyle(Style.FILL);  
        for (int i=0; i<mPoints.length; i++)
        {  
            canvas.drawRect(pointToRect(mPoints[i]),paint);  
        }  
    }  

    @Override  
    public boolean onTouchEvent(MotionEvent event) 
    {  
        switch (event.getAction()) 
        {  
        case MotionEvent.ACTION_DOWN:  
            for (int i=0; i<mPoints.length; i++)
            {  
                if (pointToRect(mPoints[i]).contains(event.getX(),event.getY()))
                {  
                	System.out.println("-yes-"+i);
                    mSelectedPoint = mPoints[i];  
                }  
            }  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if ( null != mSelectedPoint)
            {   
//                mSelectedPoint.x = (int) event.getX();  
                mSelectedPoint.y = (int) event.getY();  
//                invalidate();  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
            mSelectedPoint = null;  
            break;  
        default:  
            break;  
        }         
        return true;  
          
    }  
     
   
    private RectF pointToRect(Point p)
    {  
        return new RectF(p.x -RECT_SIZE/2, p.y - RECT_SIZE/2,p.x + RECT_SIZE/2, p.y + RECT_SIZE/2);             
    }  


    
    private void drawline(Point[] ps,Canvas canvas,Paint paint)
    {
    	Point startp=new Point();
    	Point endp=new Point();
    	for(int i=0;i<ps.length-1;i++)
    	{	
    	startp=ps[i];
    	endp=ps[i+1];
    	canvas.drawLine(startp.x,startp.y,endp.x,endp.y, paint);
    	}
    }
    
  
    private Point[] getpoints(ArrayList<Double> dlk2,HashMap<Double, Double> map2,ArrayList<Integer> xlist,double max,double min,int h)
    {
    	Point[] points=new Point[dlk2.size()];
    	for(int i=0;i<dlk2.size();i++)
    	{   int ph=0;
    		if(max==min)
    			{ph=0;}
    		else{
    		  ph=h-(int)(h*(((map2.get(dlk2.get(i)))-min)*1000/((max-min)*1000)));
    		}
    		points[i]=new Point(xlist.get(i),ph+margint);
    	}
    	return points;
    }
    
   
    private void drawline(String text,int x,int y,Canvas canvas)
    {
    	Paint p = new Paint();
    	p.setAlpha(0x0000ff);   
    	p.setTextSize(15);   
    	String familyName = "宋体";   
    	Typeface font = Typeface.create(familyName,Typeface.ITALIC);   
    	p.setTypeface(font);   
    	p.setTextAlign(Paint.Align.CENTER);     
    	canvas.drawText(text, x, y, p);
    }


    public  int dip2px(Context context, float dpValue) 
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
   
    public  int px2dip(Context context, float pxValue) 
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    
    @SuppressWarnings("rawtypes")
	public ArrayList<Double> getintfrommap(HashMap<Double, Double> map2)
    {
    	ArrayList<Double> dlk=new ArrayList<Double>();
    	int position=0;
    	if(map2==null)
    		return null;
		Set set= map2.entrySet();   
		Iterator iterator = set.iterator();
 
		 while(iterator.hasNext())
		{   
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry  = (Map.Entry)iterator.next();   
			dlk.add((Double)mapentry.getKey());
		} 
		 for(int i=0;i<dlk.size();i++)
		 {
			 int j=i+1;  
	            position=i;  
	           Double temp=dlk.get(i);	         
	            for(;j<dlk.size();j++)
	            {  
	            	if((dlk.get(j))<temp)
	            	{  
	            		temp=dlk.get(j);  
	            		position=j;  
	            	}  
	            }  
	            
	            dlk.set(position,dlk.get(i)); 
	            dlk.set(i,temp);  
		 }
		return dlk;
    	
    }

    
    
    
    public void setbg(int c)
    {
    	this.setBackgroundColor(c);
    }
    
	public HashMap<Double, Double> getMap() {
		return map;
	}

	public void setMap(HashMap<Double, Double> map2) {
		this.map = map2;
	}

	public double getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(double totalvalue) {
		this.totalvalue = totalvalue;
	}

	public double getMinvalue() {
		return minvalue;
	}

	public void setMinvalue(double minvalue) {
		this.minvalue = minvalue;
	}



	public String getXstr() {
		return xstr;
	}

	public void setXstr(String xstr) {
		this.xstr = xstr;
	}

	public String getYstr() {
		return ystr;
	}

	public void setYstr(String ystr) {
		this.ystr = ystr;
	}

	public int getMargint() {
		return margint;
	}

	public void setMargint(int margint) {
		this.margint = margint;
	}

	public Boolean getIsylineshow() {
		return isylineshow;
	}

	public void setIsylineshow(Boolean isylineshow) {
		this.isylineshow = isylineshow;
	}

	public int getMarginb() {
		return marginb;
	}

	public void setMarginb(int marginb) {
		this.marginb = marginb;
	}

	public Mstyle getMstyle() {
		return mstyle;
	}

	public void setMstyle(Mstyle mstyle) {
		this.mstyle = mstyle;
	}

	public int getBheight() {
		return bheight;
	}

	public void setBheight(int bheight) {
		this.bheight = bheight;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

 
    
    
}  
