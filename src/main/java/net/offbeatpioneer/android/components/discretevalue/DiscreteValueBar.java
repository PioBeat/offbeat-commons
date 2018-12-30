package net.offbeatpioneer.android.components.discretevalue;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.offbeatpioneer.android.components.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A progressbar for discrete values with a horizontal and vertical layout.
 * <p>
 * You can set the number of elements for this progressbar by adjusting the <b>maxLevels</b> attribute
 * with the {@link DiscreteValueBar#setMaxLevels(int)} method.
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
 * calling the {@link DiscreteValueBar#startAnimation()}. Add a component listener with {@link DiscreteValueBar#addComponentListener(ComponentListener)}
 * to listen when the animation finishes.
 *
 * @author Dominik Grzelak
 * @since 19.02.2018.
 */
public class DiscreteValueBar extends LinearLayout {
    /**
     * empty listener implementation if no listener is set
     */
    private static final ComponentListener INSTANCE = new ComponentListener() {
        @Override
        public void onAnimationComplete() {

        }
    };

    private final AbstractElementAppearanceStrategy STRATEGY = new OpaqueAppearanceStrategy(this);

    /**
     * Default duration for the animation
     */
    private static final int DEFAULT_DURATION = 5000;
    /**
     * Default margin in pixels
     */
    private static final int DEFAULT_MARGIN = 0;

    /**
     * Default number of maximum levels
     */
    private static final int DEFAULT_LEVEL_COUNT = 5;

    /**
     * Default value of elements that aren't "selected"
     */
    public static final float DEFAULT_OPAQUE_ELEMENT = 0.10f;

    public static final int ACTIVATE_ALL_ELEMENTS = -1;
    public static final int DEACTIVATE_ALL_ELEMENTS = -2;

    private AbstractElementAppearanceStrategy elementAppearanceStrategy = STRATEGY;

    private int maxLevels;
    private int elementMargin;
    private int elementHeight = -2;
    private int elementWidth = -2;
    private int selectedElement = ACTIVATE_ALL_ELEMENTS;
    private boolean withAnimation = true;
    private boolean autoStartAnimation = true;
    private int borderWidth = 0;
    private int borderRadius = 0;
    private int borderColor = Color.rgb(0, 0, 0);
    private int animationDuration;
    private ViewGroup content;
    private ComponentListener listener = INSTANCE;
    private String layout_height;
    private String layout_width;

    private final int[] DEFAULT_COLORS = new int[]{Color.rgb(217, 83, 79),
            Color.rgb(230, 211, 133),
            Color.rgb(196, 214, 164),
            Color.rgb(50, 139, 196),
            Color.rgb(16, 35, 54)};

    private int[] colors = DEFAULT_COLORS;

    public interface ComponentListener {
        void onAnimationComplete();
    }

    public DiscreteValueBar(Context context) {
        super(context);
    }

    public DiscreteValueBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(attrs);
    }

    public DiscreteValueBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initLayout(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) throw new RuntimeException("LayoutInflater must not be null");
        content = (ViewGroup) inflater.inflate(R.layout.ofp_layout_skill_comp, this);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DiscreteValueBar, 0, 0);

        maxLevels = a.getInt(R.styleable.DiscreteValueBar_ofpMaxLevels, DEFAULT_LEVEL_COUNT);
        selectedElement = a.getInt(R.styleable.DiscreteValueBar_ofpSelectedIndex, ACTIVATE_ALL_ELEMENTS);
        elementMargin = a.getDimensionPixelSize(R.styleable.DiscreteValueBar_ofpElementMargin, DEFAULT_MARGIN);
        withAnimation = a.getBoolean(R.styleable.DiscreteValueBar_ofpWithAnimation, true);
        animationDuration = a.getInteger(R.styleable.DiscreteValueBar_ofpAnimationDuration, DEFAULT_DURATION);
        autoStartAnimation = a.getBoolean(R.styleable.DiscreteValueBar_ofpAutoStartAnimation, true);

        borderColor = a.getColor(R.styleable.DiscreteValueBar_ofpBorderColor, Color.rgb(33, 33, 33));
        borderWidth = a.getDimensionPixelSize(R.styleable.DiscreteValueBar_ofpBorderWidth, 0);
        borderRadius = a.getDimensionPixelSize(R.styleable.DiscreteValueBar_ofpBorderRadius, 0);

        a.recycle();

        setMaxLevels(maxLevels);

        layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
        content.removeAllViews();
    }

    private void createLayout() {
        for (int i = 0; i < maxLevels; i++) {
            content.addView(createElement(colors[i % colors.length]));
            elementAppearanceStrategy.showElement(content.getChildAt(i), i);
        }
    }

    private View createElement(int color) {
        View elem = new View(getContext());
        LinearLayout.LayoutParams param;
        if (getOrientation() == VERTICAL) {
            param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
            );
            param.width = elementWidth;
            param.height = elementHeight;
        } else {
            param = new LinearLayout.LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT,
                    1.0f
            );
            param.height = elementHeight;
        }
        param.weight = 1;
        param.setMargins(elementMargin, elementMargin, elementMargin, elementMargin);
        elem.setLayoutParams(param);
//        elem.setBackgroundColor(color);
//        elem.setBackgroundResource(R.drawable.round);
//        Drawable background = elem.getBackground();
        Drawable background = getResources().getDrawable(R.drawable.round);
        if (background instanceof GradientDrawable) {
            // cast to 'GradientDrawable'
            GradientDrawable gradientDrawable = (GradientDrawable) background.mutate();
            gradientDrawable.setColor(color);
            gradientDrawable.setStroke(borderWidth, borderColor);
            gradientDrawable.setCornerRadius(borderRadius);
            elem.setBackground(gradientDrawable);
        }
        return elem;
    }

    /**
     * Start the animation for the progressbar. Works only if {@code withAnimation == true}.
     */
    public void startAnimation() {
        if (!withAnimation) return;
        for (int i = 0, n = content.getChildCount(); i < n; i++) {
            elementAppearanceStrategy.animatedElement(content.getChildAt(i), i, n);
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
        if (elementWidth == -2)
            elementWidth = min;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getOrientation() == HORIZONTAL) {
            if (layout_height.equalsIgnoreCase("-2")) {
                elementHeight = getResources().getDimensionPixelSize(R.dimen.ofp_skillcomp_default_height);
            }
        } else {
            if (layout_width.equalsIgnoreCase("-2")) {
                elementWidth = getResources().getDimensionPixelSize(R.dimen.ofp_skillcomp_default_height);
            }
        }
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
        if (getSelectedElement() == ACTIVATE_ALL_ELEMENTS) {
            setSelectedElement(maxLevels - 1);
        }
    }

    public int getSelectedElement() {
        return selectedElement;
    }

    /**
     * Specify until which element should be activated.
     * If the specified index is less than zero and higher than {@code maxLevels - 1}
     * all elements are enabled.
     *
     * @param selectedElement the index of the elements
     */
    public void setSelectedElement(int selectedElement) {
        if (selectedElement >= 0 && selectedElement < getMaxLevels()) {
            this.selectedElement = selectedElement;
        } else if (selectedElement == ACTIVATE_ALL_ELEMENTS) {
            this.selectedElement = ACTIVATE_ALL_ELEMENTS;
        } else {
            this.selectedElement = DEACTIVATE_ALL_ELEMENTS;
        }
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

    public AbstractElementAppearanceStrategy getElementAppearanceStrategy() {
        return elementAppearanceStrategy;
    }

    public void setElementAppearanceStrategy(@NonNull AbstractElementAppearanceStrategy elementAppearanceStrategy) {
        if (elementAppearanceStrategy == null) return;
        this.elementAppearanceStrategy = elementAppearanceStrategy;
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
     * Get the duration of the animation
     *
     * @return duration of the animation in milliseconds
     */
    public int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * Set the duration of the animation in milliseconds. Animation has to be enabled with
     * {@link DiscreteValueBar#setWithAnimation(boolean)}.
     *
     * @param animationDuration in milliseconds
     */
    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }
}
