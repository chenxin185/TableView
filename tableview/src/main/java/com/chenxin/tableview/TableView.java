package com.chenxin.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chenxin on 2017/7/14.
 * O(∩_∩)O~
 */


/**
 * 目前这个控件包含三个部分，顶部的HeaderView，中间的RecyclerView，底部的四个Button。
 */
public class TableView extends ViewGroup implements View.OnClickListener {

    public static final String TAG = "TableView";
    Button[] mButtons = new Button[4];//四个Button(首页，上一页，下一页，尾页)
    RecyclerView mRecyclerView;
    View headView;
    AdapterWrapper mAdapter;
    int buttonHeight = 0;//四个Button的高度
    int buttonWidth = 0;//四个Button夹起来的宽度
    int recyclerViewHeight = 0;
    int recyclerViewItemCount = 0;
    onItemClickListener listener;
    boolean move = false;
    int position;
    NoScrollLayoutManager noScrollLayoutManager;

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        switch (tag) {
            case 0:
                mAdapter.goToFirstPage();
                break;
            case 1:
                mAdapter.goToLastPage();
                break;
            case 2:
                mAdapter.goToNextPage();
                break;
            case 3:
                mAdapter.goToEndPage();
                break;
        }

        if (listener == null) {
            //Log.e(TAG, "onItemClickListener == null");
            return;
        }
        listener.onClick(view);
    }

    public interface onItemClickListener {
        void onClick(View v);
    }

    public TableView(Context context) {
        super(context);
        init(context, null, -1);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setWillNotDraw(false);
        setClickable(true);
        LayoutParams l;
        //设置Button样式
        for (int i = 0; i < mButtons.length; i++) {
            mButtons[i] = new Button(context);
            l = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mButtons[i].setLayoutParams(l);
        }
        mButtons[0].setText("首页");
        mButtons[1].setText("上一页");
        mButtons[2].setText("下一页");
        mButtons[3].setText("尾页");
        for (int i = 0; i < mButtons.length; i++) {
            addView(mButtons[i]);
            mButtons[i].setTag(i);
            mButtons[i].setOnClickListener(this);
        }

        //设置RecyclerView样式
        mRecyclerView = new RecyclerView(context);
        l = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(l);
        noScrollLayoutManager = new NoScrollLayoutManager(getContext());
        noScrollLayoutManager.setScroll(false);
        mRecyclerView.setLayoutManager(noScrollLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("infoo", "onScrolled");
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    Log.e(TAG, "能看见的第一个item为 -->> " + manager.findFirstVisibleItemPosition());
                    int n = position - manager.findFirstVisibleItemPosition();
                    Log.e("infoo", "n = " + n);
                    if (0 <= n && n < mRecyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = mRecyclerView.getChildAt(n).getTop();
                        //int top = n * recyclerView.getChildAt(0).getHeight();
                        Log.e("infoo", "top = " + top);
                        //最后的移动
                        mRecyclerView.scrollBy(0, top);
                        noScrollLayoutManager.setScroll(false);
                        Log.e(TAG, "能看见的第一个item为 -->> " + manager.findFirstVisibleItemPosition());
                    }
                }
            }
        });
        addView(mRecyclerView);
        initStyle(context, attrs, defStyleAttr);
    }

    /**
     * 设置自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TableView, defStyleAttr, 0);

        for (Button b : mButtons) {
            Drawable d = a.getDrawable(R.styleable.TableView_ButtonBackground);
            if (d != null) {
                b.setBackground(d);
            }
        }

        float textSize = a.getDimensionPixelSize(R.styleable.TableView_ButtonTextSize, -1);
        int padding = a.getDimensionPixelSize(R.styleable.TableView_ButtonPadding, -1);
        int minWidth = a.getDimensionPixelSize(R.styleable.TableView_ButtonMinWidth, -1);
        int minHeight = a.getDimensionPixelSize(R.styleable.TableView_ButtonMinHeight, -1);
        int maxHeight = a.getDimensionPixelSize(R.styleable.TableView_ButtonMaxHeight, -1);
        int maxWidth = a.getDimensionPixelSize(R.styleable.TableView_ButtonMaxWidth, -1);
        int paddingLeft = a.getDimensionPixelSize(R.styleable.TableView_ButtonLeftPadding, -1);
        int paddingRight = a.getDimensionPixelSize(R.styleable.TableView_ButtonRightPadding, -1);
        int paddingTop = a.getDimensionPixelSize(R.styleable.TableView_ButtonTopPadding, -1);
        int paddingBottom = a.getDimensionPixelSize(R.styleable.TableView_ButtonBottomPadding, -1);

        for (Button b : mButtons) {
            if (textSize != -1) {
                b.setTextSize(textSize);
            }

            if (padding != -1) {
                b.setPadding(padding, padding, padding, padding);
            }

            if (minWidth != -1) {
                b.setMinWidth(minWidth);
                b.setMinimumWidth(minWidth);
            }

            if (minHeight != -1) {
                b.setMinHeight(minHeight);
                b.setMinimumHeight(minHeight);
            }

            if (maxHeight != -1) {
                b.setMaxHeight(maxHeight);
            }

            if (maxWidth != -1) {
                b.setMaxWidth(maxWidth);
            }

            if (paddingLeft != -1 || paddingRight != -1 || paddingTop != -1 || paddingBottom != -1) {
                b.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }
        }

        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeadView(widthMeasureSpec, heightMeasureSpec);
        measureButton(widthMeasureSpec, heightMeasureSpec);
        measureRecyclerView(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * measure HeadView的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void measureHeadView(int widthMeasureSpec, int heightMeasureSpec) {
        if (headView != null) {
            LayoutParams lp = headView.getLayoutParams();
            if (lp == null) {
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            final int hMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            final int wMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            headView.measure(wMeasureSpec, hMeasureSpec);
        }
    }

    /**
     * measureButton的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void measureButton(int widthMeasureSpec, int heightMeasureSpec) {
        //计算Button的长宽
        buttonWidth = 0;
        for (Button b : mButtons) {
            final LayoutParams lp = b.getLayoutParams();
            final int bHMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            final int bWMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            b.measure(bWMeasureSpec, bHMeasureSpec);
            buttonHeight = b.getMeasuredHeight();
            buttonWidth += b.getMeasuredWidth();
        }
    }

    /**
     * 计算RecyclerView的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void measureRecyclerView(int widthMeasureSpec, int heightMeasureSpec) {
        //计算recyclerView的长宽
        final LayoutParams rclp = mRecyclerView.getLayoutParams();
        final int rcHSpec = getChildMeasureSpec(heightMeasureSpec, 0, rclp.height);
        final int rcWSpec = getChildMeasureSpec(widthMeasureSpec, 0, rclp.width);
        mRecyclerView.measure(rcWSpec, rcHSpec);
        recyclerViewHeight = mRecyclerView.getMeasuredHeight();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
/*        layoutRecyclerView(changed, r, height);
        layoutHeadView(changed, r);
        layoutButtons(changed, l, r);*/
        int height = getHeight();
        int width = getWidth();
        layoutRecyclerView(changed, width, height);
        layoutHeadView(changed, width);
        layoutButtons(changed, 0, width);
    }


    /**
     * layout RecyclerView
     *
     * @param changed
     * @param r
     * @param b
     */
    private void layoutRecyclerView(boolean changed, int r, int b) {
        //在recyclerView没有layout()之前，获取到的recyclerView的宽高不是准确的值，所以先layout()一次，以便后面的计算
        mRecyclerView.layout(0, 0, r, b);
        int headViewHeight = 0;
        int itemHeight = 0;
        if (headView != null) {
            headViewHeight = headView.getMeasuredHeight();
        }
        View itemView = mRecyclerView.getLayoutManager().getChildAt(0);
        if (itemView != null) {
            itemHeight = itemView.getHeight();
        }

        if ((b - recyclerViewHeight) < (buttonHeight + headViewHeight)) {
            recyclerViewHeight = b - buttonHeight - headViewHeight;
            recyclerViewItemCount = recyclerViewHeight / itemHeight;
            if (itemHeight >= recyclerViewHeight) {
                recyclerViewItemCount = 1;
            } else {
                recyclerViewHeight = recyclerViewItemCount * itemHeight;
            }
        } else {
            recyclerViewItemCount = mRecyclerView.getChildCount();
            recyclerViewHeight = recyclerViewItemCount * itemHeight;
        }
        mRecyclerView.layout(0, headViewHeight, r, recyclerViewHeight + headViewHeight);
    }

    /**
     * layout HeadView
     *
     * @param changed
     * @param r
     */
    private void layoutHeadView(boolean changed, int r) {
        if (headView == null) {
            return;
        }

        int height = headView.getMeasuredHeight();
        headView.layout(0, 0, r, height);
    }

    /**
     * layout Button
     *
     * @param changed
     * @param l
     * @param r
     */
    private void layoutButtons(boolean changed, int l, int r) {
        int headViewHeight = 0;
        if (headView != null) {
            headViewHeight = headView.getHeight();
        }
        int left = (r - l - buttonWidth) / 2;
        for (Button bt : mButtons) {
            bt.layout(left, recyclerViewHeight + headViewHeight, left + bt.getMeasuredWidth(), recyclerViewHeight + buttonHeight + headViewHeight);
            left += bt.getMeasuredWidth();
        }
    }

    /**
     * 自定义的Adapter，对传入进来的Adapter进行包装
     */
    private class AdapterWrapper extends RecyclerView.Adapter {

        RecyclerView.Adapter adapter;
        int currentPage = 1;

        public AdapterWrapper(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            adapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount();
        }

        private int getTotalPage() {
            int count = adapter.getItemCount();
            if (count < recyclerViewItemCount) {
                return 1;
            } else {
                int totalPage = count / recyclerViewItemCount;
                return count % recyclerViewItemCount == 0 ? totalPage : totalPage + 1;
            }
        }

        private void goToFirstPage() {
            if (currentPage != 1) {
                currentPage = 1;
                freshRecyclerView();
            }
        }

        private void goToLastPage() {
            if (currentPage != 1) {
                currentPage--;
                freshRecyclerView();
            }
        }

        private void goToNextPage() {
            int page = getTotalPage();
            if (currentPage < page) {
                currentPage++;
                freshRecyclerView();
            }
        }

        private void goToEndPage() {
            currentPage = getTotalPage();
            freshRecyclerView();
        }

        private void freshRecyclerView() {
            position = (currentPage - 1) * recyclerViewItemCount;
            if ((position + recyclerViewItemCount) >= (adapter.getItemCount())) {
                Log.e("infoo", "大于 -->>");
                position = adapter.getItemCount() - recyclerViewItemCount;
            }
            Log.e("infoo", "移动到  posiont -> " + position + "     itemCount = " + recyclerViewItemCount);
            moveToPosition(position);
        }


        private void moveToPosition(int n) {
            //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
            LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            int firstItem = manager.findFirstVisibleItemPosition();
            int lastItem = manager.findLastVisibleItemPosition();
            //然后区分情况
            if (n <= firstItem) {
                Log.e("infoo", "--- 1");
                //当要置顶的项在当前显示的第一个项的前面时
                move = true;
                noScrollLayoutManager.setScroll(true);
                mRecyclerView.scrollToPosition(n);
                // manager.scrollToPositionWithOffset(n, 0);
            } else if (n <= lastItem) {
                Log.e("infoo", "--- 2");
                //当要置顶的项已经在屏幕上显示时
                int top = mRecyclerView.getChildAt(n - firstItem).getTop();
                noScrollLayoutManager.setScroll(true);
                mRecyclerView.scrollBy(0, top);
                noScrollLayoutManager.setScroll(false);
            } else {
                Log.e("infoo", "--- 3");
                //当要置顶的项在当前显示的最后一项的后面时
                // manager.scrollToPositionWithOffset(n, 0);
                move = true;
                noScrollLayoutManager.setScroll(true);
                mRecyclerView.scrollToPosition(n);
                //这里这个变量是用在RecyclerView滚动监听里面的
            }

        }

    }


    /**
     * 自定义的LayoutManager，主要是为了控制RecyclerView的滑动
     */
    private class NoScrollLayoutManager extends LinearLayoutManager {

        boolean canScroll = true;

        private NoScrollLayoutManager(Context context) {
            super(context);
        }

        private void setScroll(boolean flag) {
            canScroll = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //让RecyclerView不能滑动
            // return true && super.canScrollVertically();
            return canScroll;
        }

    }

    /**
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = new AdapterWrapper(adapter);
        this.mRecyclerView.setAdapter(mAdapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        this.mRecyclerView.setLayoutManager(manager);
    }

    public void addHeadView(View v) {
        if (v == null) {
            return;
        }
        if (headView != null) {
            removeView(headView);
        }

        headView = v;
        addView(headView);
    }

    public void setOnItemClickListener(onItemClickListener l) {
        this.listener = l;
    }

}
