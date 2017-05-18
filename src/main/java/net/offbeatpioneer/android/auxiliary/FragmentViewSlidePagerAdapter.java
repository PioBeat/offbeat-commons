package net.offbeatpioneer.android.auxiliary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

/**
 * A simple pager adapter that represents Fragment objects, in sequence.
 *
 * @author Dominik Grzelak
 */
public class FragmentViewSlidePagerAdapter extends FragmentStatePagerAdapter {
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    //    private List<Fragment> fragmentList = new ArrayList<>();
    private int numPages = -1;

    public FragmentViewSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
//        Fragment frag = null;
//        switch (position) {
//            case 0:
//                frag = IntroAddServer.newInstance(null);
//                break;
//            case 1:
//                frag = IntroAddServer.newInstance("page2");
//                break;
//        }
//        registeredFragments.put(position, frag);

        return registeredFragments.get(position);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void addFragmet(Fragment fragment) {
//        fragmentList.addAll(Arrays.asList(fragments));
        registeredFragments.put(registeredFragments.size(), fragment);
    }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//            registeredFragments.put(position, fragment);
//            return fragment;
//        }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    @Override
    public int getCount() {
        if (numPages == -1) {
            return registeredFragments.size();
        }
        return numPages;
    }
}