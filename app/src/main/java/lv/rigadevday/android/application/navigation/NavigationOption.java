package lv.rigadevday.android.application.navigation;

import lv.rigadevday.android.v2.ui.base.BaseFragment;

public class NavigationOption {

    private final int title;
    private final int image;
    private final Class<? extends BaseFragment> fragmentClass;

    public NavigationOption(int title, int image, Class<? extends BaseFragment> fragmentClass) {
        this.title = title;
        this.image = image;
        this.fragmentClass = fragmentClass;
    }

    public int getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public Class<? extends BaseFragment> getFragmentClass() {
        return fragmentClass;
    }
}
