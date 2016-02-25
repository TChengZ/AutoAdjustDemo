package com.jc.demo;


import com.jc.demo.recylerview.AutoAdjustRecylerView;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TestAdapter extends Adapter<TestViewHolder>{

	private int[] mDatas;
	private AutoAdjustRecylerView mAutoAdjustRecylerView = null;

    public TestAdapter(AutoAdjustRecylerView autoAdjustRecylerView, int[] datas) {
        this.mAutoAdjustRecylerView = autoAdjustRecylerView;
    	mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

	@Override
	public void onBindViewHolder(TestViewHolder holder, int position) {
		// TODO Auto-generated method stub
		holder.mImageView.setImageResource(mDatas[position]);
	}

	@Override
	public TestViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(mAutoAdjustRecylerView, itemView);
		return testViewHolder;
	}

}
