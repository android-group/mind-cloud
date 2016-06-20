package ru.android_group.dmtou.tagscloud;

import android.graphics.Typeface;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/*
* Тестирования взаимодействия с БД
* */
public class DatabaseHelperTestUI extends TestUI<CloudActivity> {

    /**
     * Добавить одну строку в БД
     */
    @Test
    public void testInsert() throws Exception {
        database.clear();
        int indexMind = 1;

        database.insert(1, "loadedTagName", "loadedPrint", 1.2, Typeface.NORMAL, indexMind);
        List<Mind> list = database.getAll(indexMind);

        assertThat(list.size(), is(1));
    }

    /**
     * Добавить несколько строк в БД
     */
    @Test
    public void testInsertFewRow() throws Exception {
        database.clear();
        int indexMind = 1;

        database.insert(1, "loadedTagName", "loadedPrint", 1.2, Typeface.NORMAL, indexMind);
        database.insert(2, "loadedTagName", "loadedPrint", 5.4, Typeface.NORMAL, indexMind);
        database.insert(3, "loadedTagName", "loadedPrint", 5.4, Typeface.NORMAL, indexMind);
        database.insert(4, "loadedTagName", "loadedPrint", 5.4, Typeface.NORMAL, indexMind);
        List<Mind> list = database.getAll(indexMind);

        assertThat(list.size(), is(4));
    }
}
