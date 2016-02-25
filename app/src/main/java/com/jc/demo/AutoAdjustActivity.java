package com.jc.demo;


import com.jc.demo.recylerview.AutoAdjustItemClickListener;
import com.jc.demo.recylerview.AutoAdjustRecylerView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AutoAdjustActivity extends Activity implements AutoAdjustItemClickListener{
	private final String TAG = "AutoAdjustActivity";
	private AutoAdjustRecylerView mRecyclerView = null;
	final int[] mIds = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
	        R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
	        R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
	        R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
	        R.drawable.q};
	final int[] mIdsUpper = {R.drawable.aa, R.drawable.bb, R.drawable.cc, R.drawable.dd,
	        R.drawable.ee, R.drawable.ff, R.drawable.gg, R.drawable.hh,
	        R.drawable.ii, R.drawable.jj, R.drawable.kk, R.drawable.ll,
	        R.drawable.mm, R.drawable.nn, R.drawable.oo, R.drawable.pp,
	        R.drawable.qq};
	private TextView mTvText = null;
	private ImageView mImageView1 = null;
	private ImageView mImageView2 = null;
	private ImageView mImageView3 = null;
	private ImageView mImageView4 = null;
	private ImageView mImageView5 = null;
	private int mClickPosition = 0;
	private ScaleAnimation mScaleAni = null;
	private RotateAnimation mRotateAni = null;
	private AnimationSet mAnimationSet = null;
	private AlphaAnimation mAlphaAnimation = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvText = (TextView)findViewById(R.id.tv_text);
        mImageView1 = (ImageView)findViewById(R.id.iv_pic1);
        mImageView2 = (ImageView)findViewById(R.id.iv_pic2);
        mImageView3 = (ImageView)findViewById(R.id.iv_pic3);
        mImageView4 = (ImageView)findViewById(R.id.iv_pic4);
        mImageView5 = (ImageView)findViewById(R.id.iv_pic5);
        mRecyclerView = (AutoAdjustRecylerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemClickListener(this);
        mRecyclerView.setPxPerMillsec(0.3f);
        TestAdapter adapter = new TestAdapter(mRecyclerView, mIds);
        mRecyclerView.setAdapter(adapter);
        initData();
    }
    
    private void initData(){
    	mScaleAni = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,50,50); //Scale Animation  
		mScaleAni.setRepeatCount(0);  
		mScaleAni.setRepeatMode(Animation.REVERSE);  
		  
		mRotateAni = new RotateAnimation(0, 180, 50, 50); //Rotate Animation  
		mRotateAni.setRepeatMode(Animation.REVERSE);
		mRotateAni.setStartOffset(150);
		mAnimationSet = new AnimationSet(false);   //Animation Set  
		mAnimationSet.addAnimation(mScaleAni);  
		mAnimationSet.addAnimation(mRotateAni);
		mAnimationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				Log.d(TAG, "onAnimationStart");
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mImageView1.setBackgroundResource(mIds[mClickPosition]);
				mImageView2.setBackgroundResource(mIds[mClickPosition]);
				mImageView3.setBackgroundResource(mIds[mClickPosition]);
				mImageView4.setBackgroundResource(mIds[mClickPosition]);
				mImageView5.setBackgroundResource(mIds[mClickPosition]);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
		});
		mAnimationSet.setDuration(300);
		mAlphaAnimation = new AlphaAnimation(1.0f, 0f);
		mAlphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				mTvText.setText("TEST" + (mClickPosition + 1));
			}
		});
		mAlphaAnimation.setDuration(1000);
    }
    
	@Override
	public void onItemClick(View view, final int position) {
		Log.d(TAG, "position:" + position);
		mClickPosition = position;
		mAnimationSet.setStartOffset(0);
		mImageView1.startAnimation(mAnimationSet);
		mImageView2.startAnimation(mAnimationSet);
		mImageView3.startAnimation(mAnimationSet);
		mImageView4.startAnimation(mAnimationSet);
		mImageView5.startAnimation(mAnimationSet);
		mAlphaAnimation.setStartOffset(0);
		mTvText.startAnimation(mAlphaAnimation);
	}

}
