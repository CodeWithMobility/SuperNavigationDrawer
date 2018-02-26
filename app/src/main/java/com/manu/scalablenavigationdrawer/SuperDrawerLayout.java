package com.manu.scalablenavigationdrawer;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by manu on 2/25/2018.
 */

public class SuperDrawerLayout extends DrawerLayout {

    private FrameLayout frameLayout;
    public View drawerView;
    CardView cardView;
    private int scrimColor = Color.TRANSPARENT;
    private float elevation = 0.0f;
    private float scalefactor = 0.0f;
    private float adjustfactor = 10.0f;
    private float radius = 0.0f;

    public SuperDrawerLayout(Context context) {
        super(context);
        init(context);
    }

    public SuperDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SuperDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                SuperDrawerLayout.this.drawerView = drawerView;
                updateSlideOffset(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        frameLayout = new FrameLayout(context);
        super.addView(frameLayout);
    }


    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        child.setLayoutParams(params);
        addView(child);
    }

    @Override
    public void addView(View child) {
        if (child instanceof NavigationView) {
            super.addView(child);
        }
        else {
            cardView  = new CardView(getContext());
            cardView.setRadius(0);
            cardView.addView(child);
            cardView.setCardElevation(0);
            frameLayout.addView(cardView);
        }
    }


    @Override
    public void openDrawer(final View drawerView, boolean animate) {
        super.openDrawer(drawerView, animate);
        post(() -> updateSlideOffset(drawerView, isDrawerOpen(drawerView) ? 1f : 0f));
    }


    public void customAdjustment(float radius, float scalefactor, float adjustfactor, float elevation){
        this.radius = radius;
        this.scalefactor = scalefactor;
        this.adjustfactor = adjustfactor;
        this.elevation = elevation;
    }

    private void updateSlideOffset(View drawerView, float slideOffset) {
        final int absHorizGravity = getDrawerViewAbsoluteGravity(Gravity.START);
        final int childAbsGravity = getDrawerViewAbsoluteGravity(drawerView);
        cardView.setRadius((int) (radius * slideOffset));
        super.setScrimColor(scrimColor);
        super.setDrawerElevation(2);
        float percentage = 1f - scalefactor;
        float reduceHeight = getHeight() * percentage * slideOffset;
        FrameLayout.LayoutParams params
                        = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        params.topMargin = (int) (reduceHeight / 2);
        params.bottomMargin = (int) (reduceHeight / 2);
        cardView.setLayoutParams(params);
        cardView.setCardElevation(elevation * slideOffset);
        float width = childAbsGravity == absHorizGravity ?
                        drawerView.getWidth() + adjustfactor : -drawerView.getWidth() - adjustfactor;
        cardView.setX(width * slideOffset);
    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerView != null)
            updateSlideOffset(drawerView, isDrawerOpen(drawerView) ? 1f : 0f);
    }

    int getDrawerViewAbsoluteGravity(int gravity) {
        return GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & Gravity.HORIZONTAL_GRAVITY_MASK;
    }

    int getDrawerViewAbsoluteGravity(View drawerView) {
        final int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;
        return getDrawerViewAbsoluteGravity(gravity);
    }
}
