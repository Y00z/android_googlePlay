package com.itheima.googleplay.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.itheima.googleplay.R;
import com.itheima.googleplay.adapter.DefaultAdapter;
import com.itheima.googleplay.tools.UiUtils;

public class MoreHolder extends BaseHolder<Integer> {
    public static final int HAS_NO_MORE = 0;  // 没有额外数据了
    public static final int LOAD_ERROR = 1;// 加载失败
    public static final int HAS_MORE = 2;//  有额外数据

    private RelativeLayout rl_more_loading, rl_more_error;
    private boolean hashMore;

    /**
     * 当Holder显示的时候 显示什么样子
     */
    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.load_more);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    private DefaultAdapter adapter;

    public MoreHolder(DefaultAdapter adapter, boolean hashMore) {
        super();
        this.adapter = adapter;
        this.hashMore = hashMore;
        if (!hashMore) {      //如果没有更多数据，就隐藏加载更多的图标。
            setData(0);
        }
    }


    @Override
    public View getContentView() {
        if (hashMore) {
            loadMore();
        }
        return super.getContentView();
    }


    private void loadMore() {
        // 请求服务器   加载下一批数据
        //  交给Adapter  让Adapter  加载更多数据
        adapter.loadMore();
    }

    /**
     * 根据数据做界面的修改
     */
    @Override
    public void refreshView(Integer data) {
        rl_more_error.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
        rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);

    }
}
