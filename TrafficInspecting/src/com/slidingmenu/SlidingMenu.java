package com.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
	
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	private int mMenuWidth;
	private int menuRightPadding = 100;
	private boolean isopen = false;

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//获取手机屏幕大小
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
	    mScreenWidth = outMetrics.widthPixels;
		//把dp转化为px 如果COMPLEX_UNIT_SP的话就是把sp转化为px 可以封装
	    //设置菜单右边距
	    menuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,context.getResources().getDisplayMetrics());
		 
	}

	/*自定义ViewGroup:
	 * 1.onMeasure:决定内部(子view)的宽和高，以及自己的宽和高
	 *2.onLayout:决定子view的放置的位置
	 *3.onTouchEvent:判断手指的滑动状态
	 */
	
	 @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWapper = (LinearLayout) getChildAt(0);
		mMenu = (ViewGroup) mWapper.getChildAt(0);
		mContent = (ViewGroup) mWapper.getChildAt(1);
		mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - menuRightPadding;
		mContent.getLayoutParams().width = mScreenWidth;
		mWapper.getLayoutParams().width = mScreenWidth;
	}
	 
	 @Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			// 通过设置偏移量将menu隐藏
			
			super.onLayout(changed, l, t, r, b);
			if(changed){
				this.scrollTo(mMenuWidth,0);
			}
		}
	 
	 public boolean onTouchEvent(MotionEvent ev) {
			int action = ev.getAction();
			switch(action){
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();//内容区域左侧隐藏部分
				if(scrollX>=mMenuWidth/2){
					this.smoothScrollTo(mMenuWidth, 0);//smoothScrollTo有个滑动效果
					isopen = false;
				}
				else{
					this.smoothScrollTo(0,0);
					isopen=true;
				}
				return true;
			}
			return super.onTouchEvent(ev);
			
		}
	//打开菜单
		public void openMenu(){
			if(isopen)
				return;
			else{
				this.smoothScrollTo(0, 0);
				isopen = true;
			}
		}
		//关闭菜单
		public void closeMenu(){
			if(isopen){
				this.smoothScrollTo(mMenuWidth, 0);
				isopen=false;
			}
			else return;
		}
		//切换菜单
		public void toggle(){
			if(isopen)
				closeMenu();
			else openMenu();
		}
	}

