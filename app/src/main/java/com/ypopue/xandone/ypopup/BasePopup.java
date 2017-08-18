package com.ypopue.xandone.ypopup;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * author: xandone
 * created on: 2017/8/16 13:55
 */

public abstract class BasePopup extends PopupWindow {

    private Activity mAct;
    private int mEmptyW, mEmptyH;

    private float mWidthPersent;
    private float mHeighPersent;

    private Animation mIn;
    private Animation mOut;
    private Animation mBgIn;
    private Animation mBgOut;

    private View mContentView;
    private View mBgView;
    private boolean isShow;

    public static final int DIRECT_RIGHT = 1;
    public static final int DIRECT_BOTTOM = 2;

    public BasePopup(Activity activity) {
        super(activity);
        this.mAct = activity;
        init();
    }

    @Override
    public void setContentView(View contentView) {
        if (contentView == null) {
            return;
        }
        this.mContentView = contentView;
        if (mEmptyW <= 0 && mEmptyH <= 0) {
            super.setContentView(contentView);
            return;
        }
//        父布局
        FrameLayout rootView = new FrameLayout(mAct);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);

//        背景图层
        mBgView = LayoutInflater.from(mAct).inflate(R.layout.bg_view_layout, rootView, false);
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(mBgView, bgParams);
        mBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        下弹框
        if (mEmptyH > 0) {
//            高度自适应
            FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            contentParams.gravity = Gravity.BOTTOM;
            contentParams.topMargin = mEmptyH;
            contentView.setLayoutParams(contentParams);
            rootView.addView(contentView, contentParams);
        } else {
            FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            contentParams.leftMargin = mEmptyW;
            contentView.setLayoutParams(contentParams);
            rootView.addView(contentView, contentParams);
        }
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        super.setContentView(rootView);

    }

    public abstract View setLayout();

    public void init() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable();
        setBackgroundDrawable(colorDrawable);
    }

    /**
     * @param w
     * @param h
     */
    public void initSlide(float w, float h, int style) {
        mWidthPersent = w;
        mHeighPersent = h;
        initEmpty();
        initAnim(mAct, style);
        setContentView(setLayout());
    }

    public float getmWidthPersent() {
        return mWidthPersent;
    }

    public float getmHeighPersent() {
        return mHeighPersent;
    }

    public void initEmpty() {
        Point size = new Point();
        mAct.getWindowManager().getDefaultDisplay().getSize(size);
        mEmptyW = (int) (size.x - size.x * mWidthPersent);
        mEmptyH = (int) (size.y - size.y * mHeighPersent);
    }

    public void initAnim(Context context, int style) {
        int[] attrs = new int[]{android.R.attr.windowEnterAnimation, android.R.attr.windowExitAnimation};
        TypedArray typedArray = context.obtainStyledAttributes(style, attrs);
        int anim_in = typedArray.getResourceId(0, R.anim.slide_right_in);
        int anim_out = typedArray.getResourceId(1, R.anim.slide_right_out);

        mIn = AnimationUtils.loadAnimation(context, anim_in);
        mOut = AnimationUtils.loadAnimation(context, anim_out);
        mBgIn = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        mBgOut = AnimationUtils.loadAnimation(context, R.anim.alpha_out);
    }

    public int getAnimStyleRight() {
        return R.style.yPopupAnimFromeRight;
    }

    public int getAnimStyleBottom() {
        return R.style.yPopupAnimFromeBottom;
    }


    public void show() {
        mBgView.startAnimation(mBgIn);
        mContentView.startAnimation(mIn);
        mContentView.setVisibility(View.VISIBLE);
        showAtLocation(mAct.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        isShow = true;
    }

    public void close() {
        mBgView.startAnimation(mBgOut);
        mContentView.startAnimation(mOut);
        mOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isShow = false;
                mContentView.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void dismiss() {
        if (isShow) {
            close();
            return;
        }
        super.dismiss();
    }

    public BasePopup setSlideDirect(int direct) {
        switch (direct) {
            case DIRECT_RIGHT:
                initSlide(0.6f, 1f, getAnimStyleRight());
                break;
            case DIRECT_BOTTOM:
                initSlide(1f, 0.4f, getAnimStyleBottom());
                break;
        }
        return this;
    }
}
