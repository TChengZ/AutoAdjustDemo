package com.jc.demo.recylerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class AutoAdjustRecylerView extends RecyclerView {
	private final String TAG = "AutoAdjustRecylerView";
	private Scroller mScroller = null;
	private int mLastx = 0;
	//用于设置自动平移时候的速度
	private float mPxPerMillsec = 0;
	private AutoAdjustItemClickListener mListener = null;
	
	public AutoAdjustRecylerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initData(context);
	}

	public AutoAdjustRecylerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initData(context);
	}

	public AutoAdjustRecylerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initData(context);
	}
	
	private void initData(Context context){
		mScroller = new Scroller(context, new Interpolator() {
			public float getInterpolation(float t) {
			      return t;
			  }
		});
	}
	
	public void setScroller(Scroller scroller){
		if(mScroller != scroller){
			mScroller = scroller; 
		}
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		super.computeScroll();
		if(mScroller != null){
			if (mScroller.computeScrollOffset())//如果mScroller没有调用startScroll，这里将会返回false。    
		    {      
		        Log.d(TAG, "getCurrX = " +  mScroller.getCurrX());    
		        scrollBy(mLastx - mScroller.getCurrX(), 0);
		        mLastx = mScroller.getCurrX();
		        //继续让系统重绘    
		        postInvalidate();  
		     } 
		}
	}
	
	public AutoAdjustItemClickListener getItemClickListener() {
		return mListener;
	}

	public void setItemClickListener(AutoAdjustItemClickListener listener) {
		this.mListener = listener;
	}
	
	public float getPxPerMillsec() {
		return mPxPerMillsec;
	}

	public void setPxPerMillsec(float pxPerMillsec) {
		this.mPxPerMillsec = pxPerMillsec;
	}
	
	public void checkAutoAdjust(int position){
		int childcount = getChildCount();
		//获取可视范围内的选项的头尾位置
		int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
		int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
		Log.d(TAG, "childcount:" + childcount + " position:"+ position +" firstvisiableposition:" + firstvisiableposition
				+ " lastvisiableposition" + lastvisiableposition);
		if(position == (firstvisiableposition + 1) || position == firstvisiableposition){
			//当前位置需要向右平移
			leftScrollBy(position, firstvisiableposition);
		}
		else if(position == (lastvisiableposition - 1) || position == lastvisiableposition){
			//当前位置需要向做平移
			rightScrollBy(position, lastvisiableposition);
		}
	}
	
	private void leftScrollBy(int position, int firstvisiableposition){
		View leftChild = getChildAt(0);
		if(leftChild != null){
			int leftx = leftChild.getLeft();
			Log.d(TAG, "leftChild left:" + leftx);
			int startleft = leftx;
			int endleft = position == firstvisiableposition?leftChild.getWidth():0;
			Log.d(TAG, "startleft:" + startleft + " endleft" + endleft);
			autoAdjustScroll(startleft, endleft);
		}
	}
	
	
	private void rightScrollBy(int position, int lastvisiableposition){
		int childcount = getChildCount();
		View rightChild = getChildAt(childcount - 1);
		if(rightChild != null){
			int rightx = rightChild.getRight();
			int dx = rightx - getWidth();
			Log.d(TAG, "rightChild right:" + rightx + " dx:" + dx);
			int startright = dx;
			int endright = position == lastvisiableposition?-1 * rightChild.getWidth():0;
			Log.d(TAG,"startright:" + startright + " endright:" + endright);
			autoAdjustScroll(startright, endright);
		}
	}
	
	/**
	 * 
	 * @param start 滑动起始位置
	 * @param end 滑动结束位置
	 */
	private void autoAdjustScroll(int start, int end){
		int duration = 0;
        if(mPxPerMillsec != 0){
        	duration = (int)((Math.abs(end - start)/mPxPerMillsec));
        }
        Log.d(TAG, "duration:" + duration);
		mLastx = start;
		mScroller.startScroll(start, 0, end - start, 0, duration);
		postInvalidate();
	}
	
	public abstract class AbstractAutoAdjustViewHolder extends ViewHolder implements OnClickListener{
		
		private final static String TAG = "AutoAdjustViewHolder";
		
		public AbstractAutoAdjustViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			initView(view);
		}
		
		protected abstract void initView(View view);
		
		/**
		 * 点击监听
		 */
		@Override
		public void onClick(View v) {
			//单击选项的时候判断是否需要移动
			checkAutoAdjust(getPosition());
			if(mListener != null){
				mListener.onItemClick(v,getPosition());
			}
		}
	}
}
