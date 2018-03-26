package net.offbeatpioneer.android.components.discretevalue;

import android.animation.Animator;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * The default implementation of {@link AbstractElementAppearanceStrategy} to animate elements of the
 * component.<br>
 * Makes deactivated elements slightly transparent. That means if {@link DiscreteValueBar#getSelectedElement()}
 * isn't <i>-1</i>.
 * <p>
 * If the animation starts, the elements will get opaque one by one.
 *
 * @author Dominik Grzelak
 * @since 20.02.2018.
 */
public class OpaqueAppearanceStrategy extends AbstractElementAppearanceStrategy {

    public OpaqueAppearanceStrategy(DiscreteValueBar discreteValueBar) {
        super(discreteValueBar);
    }

    @Override
    public void showElement(View view, int currentIndex) {
        if (discreteValueBar.isWithAnimation()) {
            float startAlpha = DiscreteValueBar.DEFAULT_OPAQUE_ELEMENT;
            if (currentIndex > discreteValueBar.getSelectedElement()) {
                startAlpha = discreteValueBar.isWithAnimation() ? 0f : DiscreteValueBar.DEFAULT_OPAQUE_ELEMENT;
            }
            view.setAlpha(startAlpha);
        }
    }

    @Override
    public void animatedElement(View view, int currentIndex, int n) {
        long eachDuration = discreteValueBar.getAnimationDuration() / discreteValueBar.getMaxLevels();
        ViewPropertyAnimator animator = view.animate();
        if (currentIndex == n - 1)
            animator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    discreteValueBar.getListener().onAnimationComplete();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        animator.alpha(1f);
        if (currentIndex > discreteValueBar.getSelectedElement()) {
            animator.alpha(DiscreteValueBar.DEFAULT_OPAQUE_ELEMENT);
        }
        animator.setDuration(eachDuration).setStartDelay(currentIndex * eachDuration / 2).start();
    }
}
