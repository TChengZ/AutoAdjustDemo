package com.jc.demo;

import android.view.View;
import android.widget.ImageView;

import com.jc.demo.recylerview.AutoAdjustRecylerView;
import com.jc.demo.recylerview.AutoAdjustRecylerView.AbstractAutoAdjustViewHolder;


public class TestViewHolder extends AbstractAutoAdjustViewHolder{
	
	public ImageView mImageView;
	
	public TestViewHolder(AutoAdjustRecylerView autoAdjustRecylerView, View view) {
		autoAdjustRecylerView.super(view);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView(View view) {
		// TODO Auto-generated method stub
		mImageView = (ImageView) view.findViewById(R.id.item_iv);
	}
	
	
}
