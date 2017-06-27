package com.yyx.scrollupanddown;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNotice = (RecyclerView) findViewById(R.id.rv_notice);
        requestGetNotice();
    }

    @Override
    public void onStart() {
        isStop = false;
        super.onStart();
    }

    @Override
    public void onStop() {
        isStop = true;
        super.onStop();
    }







    void requestGetNotice() {
        List<Notice> notices = new ArrayList<>();
        Notice notice1 = new Notice("1","兴兴超市","我的测试信息1","2016-07-19","");
        Notice notice2 = new Notice("2","花花超市","我的测试信息2","2016-07-19","");
        Notice notice3 = new Notice("3","伟哥超市","我的测试信息3","2016-07-19","");
        Notice notice4 = new Notice("4","大白超市","我的测试信息4","2016-07-19","");
        Notice notice5 = new Notice("5","哈哈超市","我的测试信息5","2016-07-19","");
        notices.add(notice1);
        notices.add(notice2);
        notices.add(notice3);
        notices.add(notice4);
        notices.add(notice5);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(MainActivity.this) {
                    private static final float SPEED = 200f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED * (position == 0 ? 0.05F : 1) / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvNotice.setLayoutManager(layoutManager);
        rvNotice.setHasFixedSize(true);
        rvNotice.setItemViewCacheSize(10);
        rvNotice.setDrawingCacheEnabled(true);
        rvNotice.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        rvNotice.setAdapter(new CommonAdapterBase<Notice>(MainActivity.this, R.layout.item_gonggao, notices) {
            @Override
            protected void convertA(ViewHolder holder, Notice notice, int position) {
                holder.setText(R.id.notice_content, notice.getNoticeContent());
                holder.setText(R.id.notice_title, notice.getNoticeTitle());
                holder.setText(R.id.notice_createTime, notice.getCreateTime());
            }
        });
        autoScroll();
    }




    //*******************************公告滚动************************************
    private boolean isStop = true;

    public void autoScroll() {
        isStop = false;
        final int speedScroll = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == rvNotice.getAdapter().getItemCount())
                    count = 0;
                if (count < rvNotice.getAdapter().getItemCount()) {
                    try {
                        if (!isStop) { //暂停时,不滚动
                            rvNotice.smoothScrollToPosition(count);
                            count++;
                        }
                    } catch (Exception e) {
                        //界面未准备好,第一次可能异常
                    }
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
