package net.offbeatpioneer.android.components.discretevalue;

import android.view.View;

/**
 * Abstract class that defines the visual appearance for elements of the {@link DiscreteValueBar}.
 *
 * @author Dominik Grzelak
 * @since 20.02.2018.
 */
public abstract class AbstractElementAppearanceStrategy {
    protected DiscreteValueBar discreteValueBar;

    public AbstractElementAppearanceStrategy(DiscreteValueBar discreteValueBar) {
        this.discreteValueBar = discreteValueBar;
    }

    /**
     * Implement this method to change the appearance of an element for the {@link DiscreteValueBar}
     *
     * @param view         the current view
     * @param currentIndex the index of the view in the layout
     */
    public abstract void deactivatedElement(View view, int currentIndex);

    /**
     * If the progressbar is animated, implement this method to define the behaviour of the animation.
     * <p>
     * Use the member variable {@code skillComp} to access its parameters.
     *
     * @param view         the current element
     * @param currentIndex the current index of the element in the layout
     * @param n            the maximum number of elements
     */
    public abstract void animatedElement(View view, int currentIndex, int n);
}
