package net.offbeatpioneer.android.components.skillcomp;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import net.offbeatpioneer.android.components.R;

/**
 * Skill bar
 *
 * @author Dominik Grzelak
 * @since 19.02.2018.
 */
public class SkillComp extends LinearLayout {
    private static final ComponentListener INSTANCE = new ComponentListener() {
        @Override
        public void onAnimationComplete() {

        }
    };

    int maxLevels;
    int elementMargin;
    int elementHeight = -2;
    private boolean withAnimation = true;
    private boolean autoStartAnimation = true;
    private int animationDuration = 5000;
    private ViewGroup content;
    private ComponentListener listener = INSTANCE;

    private final int[] DEFAULT_COLORS = new int[]{Color.rgb(217, 83, 79),
            Color.rgb(230, 211, 133),
            Color.rgb(196, 214, 164),
            Color.rgb(50, 139, 196),
            Color.rgb(16, 35, 54)};

    private int[] colors;

    public interface ComponentListener {
        void onAnimationComplete();
    }

    public SkillComp(Context context) {
        super(context);
    }

    public SkillComp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(attrs);
    }

    public SkillComp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initLayout(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) throw new RuntimeException("LayoutInflater must not be null");
        content = (ViewGroup) inflater.inflate(R.layout.ofp_layout_skill_comp, this);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SkillComp, 0, 0);

        maxLevels = a.getInt(R.styleable.SkillComp_ofpMaxLevels, 5);
        elementMargin = a.getDimensionPixelSize(R.styleable.SkillComp_ofpElementMargin, 0);
        withAnimation = a.getBoolean(R.styleable.SkillComp_ofpWithAnimation, true);
        animationDuration = a.getInteger(R.styleable.SkillComp_ofpAnimationDuration, 5000);
        autoStartAnimation = a.getBoolean(R.styleable.SkillComp_ofpAutoStartAnimation, true);
        a.recycle();

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1.0f
        );

        setOrientation(LinearLayout.HORIZONTAL);
        String value = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        if (value.equalsIgnoreCase("-2")) {
            elementHeight = getResources().getDimensionPixelSize(R.dimen.ofp_skillcomp_default_height);
        }

        content.removeAllViews();
    }

    private void createLayout() {
        float startAlpha = isWithAnimation() ? 0f : 1f;
        for (int i = 0; i < maxLevels; i++) {
            content.addView(createElement(DEFAULT_COLORS[i % DEFAULT_COLORS.length], startAlpha));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int min = Math.min(width, height);
        if (elementHeight == -2)
            elementHeight = min;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createLayout();
        if (autoStartAnimation) startAnimation();
    }

    public int getMaxLevels() {
        return maxLevels;
    }

    public void setMaxLevels(int maxLevels) {
        this.maxLevels = maxLevels;
        ((LinearLayout) content).setWeightSum(this.maxLevels);
    }

    public int[] getColors() {
        return colors;
    }

    public ComponentListener getListener() {
        return listener;
    }

    public void addComponentListener(@NonNull ComponentListener listener) {
        this.listener = listener;
    }

    public void setColors(@NonNull int[] colors) {
        this.colors = colors;
    }

    public boolean isWithAnimation() {
        return withAnimation;
    }

    public void setWithAnimation(boolean withAnimation) {
        this.withAnimation = withAnimation;
    }

    public void startAnimation() {
        if (!withAnimation) return;
        long eachDuration = animationDuration / maxLevels;
        for (int i = 0, n = content.getChildCount(); i < n; i++) {
            ViewPropertyAnimator animator = content.getChildAt(i).animate();
            if (i == n - 1)
                animator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listener.onAnimationComplete();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            animator.alpha(1f).setDuration(eachDuration).setStartDelay(i * eachDuration / 2).start();
        }
    }

    private View createElement(int color, float alpha) {
        View elem = new View(getContext());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT,
                1.0f
        );
        param.weight = 1;
        param.height = elementHeight;
        param.setMargins(elementMargin, elementMargin, elementMargin, elementMargin);
        elem.setLayoutParams(param);
        elem.setBackgroundColor(color);
        elem.setAlpha(alpha);
        return elem;
    }
}
