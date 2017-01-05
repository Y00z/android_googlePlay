package com.itheima.googleplay.adapter;

import android.widget.ListView;

import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.holder.BaseHolder;
import com.itheima.googleplay.holder.ListBaseHolder;
import com.itheima.googleplay.manager.DownloadManager;

import java.util.List;

public abstract class ListBaseAdapter extends DefaultAdapter<App> {
	public ListBaseAdapter(List<App> datas , ListView listview) {
		super(datas,listview);
	}

	@Override
	protected BaseHolder<App> getHolder() {
		ListBaseHolder listBaseHolder = new ListBaseHolder();
		DownloadManager.getInstance().addObserver(listBaseHolder);
		return listBaseHolder;
	}

	@Override
	protected abstract List<App> onload();
	
}
