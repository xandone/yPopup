package com.ypopue.xandone.ypopup;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

/**
 * author: xandone
 * created on: 2017/8/16 13:55
 */

public class BasePopup extends PopupWindow {

    private Activity mAct;
    private int mEmptyW, mEmptyH;

    private final float mWidthPersent = 0.8f;
    private final float mHeighPersent = 1f;

    private Animation mIn;
    private Animation mOut;
    private Animation mBgIn;
    private Animation mBgOut;

    private View mContentView;
    private View mBgView;


    public BasePopup(Activity activity) {
        this.mAct = activity;
        init();
    }

    @Override
    public void setContentView(View contentView) {
        this.mContentView = contentView;
        if (mEmptyW <= 0 && mEmptyH <= 0) {
            super.setContentView(contentView);
            return;
        }
    }

    public void init() {
        setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable();
        setBackgroundDrawable(colorDrawable);
    }

    public int getAnimStyle() {
        return R.style.yPopupAnimFromeRight;
    }

    public float getmWidthPersent() {
        return mWidthPersent;
    }

    public float getmWeighPersent() {
        return mHeighPersent;
    }

    public void initEmpty() {
        Point size = new Point();
        mAct.getWindowManager().getDefaultDisplay().getSize(size);
        mEmptyW = (int) (size.x - size.x * mWidthPersent);
        mEmptyH = (int) (size.y - size.y * mHeighPersent);
    }

    public void initAnim(Context context) {
        int[] attrs = new int[]{android.R.attr.windowEnterAnimation, android.R.attr.windowExitAnimation};
        TypedArray typedArray = context.obtainStyledAttributes(getAnimStyle(), attrs);
        int anim_in = typedArray.getResourceId(0, R.anim.slide_right_in);
        int anim_out = typedArray.getResourceId(1, R.anim.slide_right_out);

        mIn = AnimationUtils.loadAnimation(context, anim_in);
        mOut = AnimationUtils.loadAnimation(context, anim_out);
        mBgIn = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        mBgOut = AnimationUtils.loadAnimation(context, R.anim.alpha_out);

    }

    public void show() {

    }

}
