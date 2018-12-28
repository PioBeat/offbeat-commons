package net.offbeatpioneer.android.components.pinpad;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.offbeatpioneer.android.auxiliary.FragmentViewSlidePagerAdapter;
import net.offbeatpioneer.android.components.R;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;


/**
 * @author Dominik Grzelak
 * @since 18.05.2017
 */

public class PinPadView extends LinearLayout implements PinPadFragment.PinPadInteraction {
    ViewPager pager;
    FragmentViewSlidePagerAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private PinData pinData = new PinData("", 4);
    ImageView imageView;
    int goodPinResId = R.drawable.animated_vector_good_pin;
    int badPinResId = R.drawable.animated_vector_bad_pin;

    public PinPadView(Context context) {
        super(context);
        init(context);
    }

    public PinPadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init(context);
    }

    public PinPadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PinPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_pinpad, this, true);

        pager = (ViewPager) findViewById(R.id.pager);
        if (context instanceof FragmentActivity) {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            pagerAdapter = new FragmentViewSlidePagerAdapter(fragmentManager);
            PinPadFragment frag1 = PinPadFragment.newInstance(pinData, false);
            frag1.setPinPadListener(this);
            pagerAdapter.addFragmet(frag1);
            pagerAdapter.addFragmet(PinPadFragment.newInstance(pinData, false));
            pager.setAdapter(pagerAdapter);

            imageView = (ImageView) findViewById(R.id.image_loading_check_pin);
            reset();
        }
    }

    public void setGoodPinImageType(int resId) {
        goodPinResId = resId;
    }

    public void setBadPin(int resId) {
        badPinResId = resId;
    }

    public String getPinHash() {
        return pinData.getPinHash();
    }

    public PinPadView setPinHash(String pinHash) {
        pinData.setPinHash(pinHash);
        createBundleForFragment();
        return this;
    }

    public PinPadView setPinLength(int pinLength) {
        pinData.setPinLength(pinLength);
        createBundleForFragment();
        return this;
    }

    public void reset() {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
    }

    public void startOkAnimation() {
        AnimatedVectorDrawableCompat drawable = //(AnimatedVectorDrawableCompat) getContext().getDrawable(R.drawable.animvector);
                AnimatedVectorDrawableCompat.create(getContext(), goodPinResId);
        imageView.setImageDrawable(drawable);
        drawable.start();
    }

    public void startBadPinAnimation() {
        AnimatedVectorDrawableCompat drawable = //(AnimatedVectorDrawableCompat) getContext().getDrawable(R.drawable.animvector);
                AnimatedVectorDrawableCompat.create(getContext(), badPinResId);
        imageView.setImageDrawable(drawable);
        drawable.start();
    }

    public int getPinLength() {
        return pinData.getPinLength();
    }

    private void createBundleForFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PinPadFragment.ARG_PARAM_1, pinData);
        pagerAdapter.getItem(0).setArguments(bundle);
    }

    private void initAttrs(AttributeSet attrs) {
    }

    @Override
    public void interaction() {
        reset();
    }
}
