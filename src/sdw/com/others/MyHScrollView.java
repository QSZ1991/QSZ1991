package sdw.com.others;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/*
  * 自定义的 滚动控件
 * 重载了 onScrollChanged（滚动条变化）,监听每次的变化通知给 观察(此变化的)观察者
 * 可使用 AddOnScrollChangedListener 来订阅本控件的 滚动条变化
 * */
public class MyHScrollView extends HorizontalScrollView {
	ScrollViewSubject mScrollViewSubject = new ScrollViewSubject();

	public MyHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
//		Log.i("pdwy","MyHScrollView dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//return super.onInterceptTouchEvent(ev);
//		Log.i("pdwy","MyHScrollView onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
		
		//return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		Log.i("pdwy","MyHScrollView onTouchEvent");
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		/*
		 * 当滚动条移动后，引发 滚动事件。通知给观察者，观察者会传达给其他的
		 */
		if (mScrollViewSubject != null /*&& (l != oldl || t != oldt)*/) {
			mScrollViewSubject.NotifyOnScrollChanged(l, t, oldl, oldt);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/*
	 * 订阅 本控件 的 滚动条变化事件
	 * */
	public void AddScrollViewObserver(ScrollViewObserver observer) {
		mScrollViewSubject.AddScrollViewObserver(observer);
	}

	/*
	 * 取消订阅 本控件 的 滚动条变化事件
	 * */
	public void RemoveScrollViewObserver(ScrollViewObserver observer) {
		mScrollViewSubject.RemoveScrollViewObserver(observer);
	}

	/*
	 * 当发生了滚动事件时
	 */
	public static interface ScrollViewObserver {
		public void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	/*
	 * 观察者
	 */
	public static class ScrollViewSubject {
		List<ScrollViewObserver> mList;

		public ScrollViewSubject() {
			//super();
			mList = new ArrayList<ScrollViewObserver>();
		}
		
		//增加观察者
		public void AddScrollViewObserver(ScrollViewObserver observer) {
			mList.add(observer);
		}
		
		//取消观察者
		public void RemoveScrollViewObserver(
				ScrollViewObserver observer) {
			mList.remove(observer);
		}

		//提醒
		public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
			if (mList == null || mList.size() == 0) {
				return;
			}
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i) != null) {
					mList.get(i).onScrollChanged(l, t, oldl, oldt);
				}
			}
		}
	}
}
