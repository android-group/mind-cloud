package ru.android_group.dmtou.tagscloud.pageObject;

import android.app.Activity;

import com.squareup.spoon.Spoon;

import ru.android_group.dmtou.tagscloud.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Главное окно с облаком тегов
 */
public class CloudPage extends AbstractPage {

    @Override
    protected int getId() {
        return R.id.fragment_cloud;
    }

    public CloudPage(Activity activity) {
        super(activity);
    }

    /**
     * Написать мысль
     * */
    public void writeMind(String mind) {
        Spoon.screenshot(getActivity(), "before_series");

        onView(withId(R.id.new_mind_et))
                .perform(actionWithAssertions(typeText(mind)), closeSoftKeyboard());

        Spoon.screenshot(getActivity(), "after_series");
    }

    /*
    * Добавить новую мысль
    * */
    public void clickNewBtn() {
        Spoon.screenshot(getActivity(), "before_click_new_btn");

        onView(withId(R.id.new_mind_btn))
                .perform(actionWithAssertions(click()));

        Spoon.screenshot(getActivity(), "after_click_new_btn");
    }
}