package ru.android_group.dmtou.tagscloud;

import com.squareup.spoon.Spoon;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Главное окно с облаком тегов
 */
public class CloudPage {
    private final MainActivity activity;

    public CloudPage(MainActivity activity) {
        this.activity = activity;
    }

    /**
     * Написать мысль
     * */
    public void writeMind(String mind) {
        Spoon.screenshot(activity, "before_series");

        onView(withId(R.id.new_mind_et))
                .perform(actionWithAssertions(typeText(mind)), closeSoftKeyboard());

        Spoon.screenshot(activity, "after_series");
    }

    /*
    * Добавить новую мысль
    * */
    public void clickNewBtn() {
        Spoon.screenshot(activity, "before_click_new_btn");

        onView(withId(R.id.new_mind_btn))
                .perform(actionWithAssertions(click()));

        Spoon.screenshot(activity, "after_click_new_btn");
    }
}