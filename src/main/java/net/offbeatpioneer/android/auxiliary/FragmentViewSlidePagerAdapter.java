package net.offbeatpioneer.android.auxiliary;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * A simple and convenient to use pager adapter that represents Fragment objects, in sequence.
 * <p>
 * In this basic implementation of {@link FragmentStatePagerAdapter} you have to instantiate
 * your {@link Fragment}s first and add it with {@code FragmentViewSlidePagerAdapter#addFragmet}.
 * <p>
 * Another possibility is to extend this class and overwrite the {@link FragmentViewSlidePagerAdapter#createItem(int)}
 * method to instantiate the Fragment at the moment when {@link FragmentStatePagerAdapter#getItem(int)}
 * is normally called. {@link FragmentViewSlidePagerAdapter#createItem(int)} is a hook method which
 * is called inside {@link FragmentViewSlidePagerAdapter#getItem(int)} when {@code registeredFragments}
 * is {@code null}.
 *
 * @author Dominik Grzelak
 * @see <a href="https://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter.html">FragmentStatePagerAdapter</a>
 */
public class FragmentViewSlidePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> registeredFragments = new ArrayList<>();
    private int numPages = 0;

    public FragmentViewSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * Get the fragment at the specified {@code position}.
     * <p>
     * If {@code registeredFragments} is null or empty the method
     * {@link FragmentViewSlidePagerAdapter#createItem(int)} is called to instantiate a fragment.
     * You have to override this convenient method to use it here. The
     * default return value is {@code null}.
     *
     * @param position position of the fragment in {@code registeredFragments}
     * @return the fragment, or {@code null}
     */
    @Override
    public Fragment getItem(int position) {
        if (registeredFragments == null || registeredFragments.size() == 0) {
            return createItem(position);
        }
        return registeredFragments.get(position);
    }

    /**
     * Instantiates a fragment at the specified position.
     * The default implementation is empty and returning {@code null}.
     * Override this method to implement your own logic for creating different {@link Fragment}s.
     *
     * @param position position of the fragment
     * @return the {@link Fragment} or {@code null}
     */
    public Fragment createItem(int position) {
        return null;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    /**
     * Adds a fragment to {@code registeredFragments} at the end of the list.
     *
     * @param fragment fragment to add
     */
    public void addFragmet(Fragment fragment) {
        registeredFragments.add(fragment);
    }

    public int getNumPages() {
        return numPages;
    }

    /**
     * Set a different page count e.g. to limit the accessible fragments.
     * Value should be negative to use the size of {@code registeredFragments}, or
     * positive to use the specified count.
     *
     * @param numPages number of pages to display, -1 if the real registered fragments size should
     *                 be used
     */
    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    @Override
    public int getCount() {
        if (numPages < 0) {
            return registeredFragments.size();
        }
        return numPages;
    }
}