package com.itheima.googleplay.holder;

import android.view.View;

import com.itheima.googleplay.tools.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

public abstract class BaseHolder<Data>  {
	private View contentView;
	private Data data;
	protected BitmapUtils bitmapUtils;
	public BaseHolder(){
		bitmapUtils = BitmapHelper.getBitmapUtils();
		contentView=initView();
		contentView.setTag(this);
	}
	/** 创建界面*/
	public  abstract View initView();
	public View getContentView() {
		return contentView;
	}
	public void setData(Data data){
		this.data=data;
		refreshView(data);
	}
	/** 根据数据刷新界面*/
	public abstract void refreshView(Data data);
}
