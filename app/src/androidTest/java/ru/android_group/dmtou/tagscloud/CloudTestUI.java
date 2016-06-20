package ru.android_group.dmtou.tagscloud;


import com.squareup.spoon.Spoon;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CloudTestUI extends TestUI<CloudActivity> {

    /*
    * Загрузка капчи и закрытие экрана по кнопки back
    * */
    @Test
    public void testNewMind() {
        Spoon.screenshot(activity, "start");

        database.clear();

        int randomCount = 20;
        cloudPage.writeMind(RandomStringUtils.randomNumeric(randomCount));
        cloudPage.clickNewBtn();

        cloudPage.writeMind(RandomStringUtils.randomNumeric(randomCount));
        cloudPage.clickNewBtn();

        List<Mind> all = database.getAll(0);
        assertThat(all.size(), is(2));

        Spoon.screenshot(activity, "end");
    }
}