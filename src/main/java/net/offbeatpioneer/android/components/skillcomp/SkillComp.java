package net.offbeatpioneer.android.components.skillcomp;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
 * Horizontal progressbar with support for animation.
 * <p>
 * You can set the number of elements for this progressbar by adjusting the <b>maxLevels</b> attribute
 * with the {@link SkillComp#setMaxLevels(int)} method.
 * If more levels are defined than colors, the colors will be recycled. If no custom colors are
 * set the preset color array consisting of 5 colors will be used.
 * <p>
 * By setting <b>withAnimation</b> to {@code false} you can disable the filling animation for this
 * progressbar. The progressbar will be instantly visible. Set the duration of the animation with
 * <b>animationDuration</b> (default is 5000). Specify with <b>autoStartAnimation</b> whether the
 * animation should be started automatically after the view is created (defaults to {@code true}).
 * A margin for each element of the progressbar can be set by <b>elementMargin</b>.
 * <p>
 * If <b>autoStartAnimation</b> is set to {@code false} you can start the animation by yourself by
 * calling the {@link SkillComp#startAnimation()}. Add a component listener with {@link SkillComp#addComponentListener(ComponentListener)}
 * to listen when the animation finishes.
 *
 * @author Dominik Grzelak
 * @since 19.02.2018.
 */
public class SkillComp extends LinearLayout {
    /**
     * empty listener implementation if no listener is set
     */
    private static final ComponentListener INSTANCE = new ComponentListener() {
        @Override
        public void onAnimationComplete() {

        }
    };

    /**
     * Default duration for the animation
     */
    private static final int DEFAULT_DURATION = 5000;
    /**
     * Default margin in pixels
     */
    private static final int DEFAULT_MARGIN = 0;

    private static final int DEFAULT_LEVEL_COUNT = 5;

    private int maxLevels;
    private int elementMargin;
    private int elementHeight = -2;
    private boolean withAnimation = true;
    private boolean autoStartAnimation = true;
    private int animationDuration;
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

        maxLevels = a.getInt(R.styleable.SkillComp_ofpMaxLevels, DEFAULT_LEVEL_COUNT);
        elementMargin = a.getDimensionPixelSize(R.styleable.SkillComp_ofpElementMargin, DEFAULT_MARGIN);
        withAnimation = a.getBoolean(R.styleable.SkillComp_ofpWithAnimation, true);
        animationDuration = a.getInteger(R.styleable.SkillComp_ofpAnimationDuration, DEFAULT_DURATION);
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

    /**
     * Return the number of elements for the progressbar.
     *
     * @return number of elements of the progressbar
     */
    public int getMaxLevels() {
        return maxLevels;
    }

    /**
     * Set the number of elements for the progressbar
     *
     * @param maxLevels number of elements for the progressbar
     */
    public void setMaxLevels(int maxLevels) {
        this.maxLevels = maxLevels;
        ((LinearLayout) content).setWeightSum(this.maxLevels);
    }

    /**
     * Get the colors that are used for the elements
     *
     * @return colors for the progressbar
     */
    public int[] getColors() {
        return colors;
    }

    /**
     * Set the colors that should be used for the progressbar.
     * The colors are recycled if {@code colors.length < maxLevels}
     *
     * @param colors the colors for the progressbar
     */
    public void setColors(@NonNull int[] colors) {
        this.colors = colors;
    }

    public ComponentListener getListener() {
        return listener;
    }

    /**
     * Add a listener to listened for events
     *
     * @param listener the listener to add
     */
    public void addComponentListener(@NonNull ComponentListener listener) {
        this.listener = listener;
    }

    /**
     * Check if the progressbar should play the animation.
     *
     * @return true, if the animation is enabled, otherwise false
     */
    public boolean isWithAnimation() {
        return withAnimation;
    }

    /**
     * Specify if the progressbar should be play the animation. If {@code false} the elements
     * of the progressbar are instantly visible.
     *
     * @param withAnimation true for the animation for displaying the progressbar.
     */
    public void setWithAnimation(boolean withAnimation) {
        this.withAnimation = withAnimation;
    }

    /**
     * Start the animation for the progressbar. Works only if {@code withAnimation == true}.
     */
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
