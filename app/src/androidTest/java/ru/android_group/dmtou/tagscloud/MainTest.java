package ru.android_group.dmtou.tagscloud;

import android.graphics.Point;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by y.andreev on 19.06.2016.
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    protected MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    protected DatabaseHelper database;

    @Before
    public void setUp() {
        System.out.println("Before");
        wakeUpDevice();
        load();
    }

    protected CloudPage cloudPage;

    /*
    * Оживляем телефон прежде чем запустить тест
    * */
    private void load() {
        activity = activityActivityTestRule.getActivity();

        // init Page objects
        activity.deleteDatabase(DatabaseHelper.DB_NAME);
        cloudPage = new CloudPage(activity);

        database = new DatabaseHelper(activity);

    }

    private void wakeUpDevice() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Point[] coordinates = new Point[4];
        coordinates[0] = new Point(248, 1520);
        coordinates[1] = new Point(248, 929);
        coordinates[2] = new Point(796, 1520);
        coordinates[3] = new Point(796, 929);
        try {
            if (!uiDevice.isScreenOn()) {
                uiDevice.wakeUp();
                uiDevice.swipe(coordinates, 10);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        database.clear();
        database.close();
    }
}
