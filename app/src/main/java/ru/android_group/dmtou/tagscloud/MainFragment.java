package ru.android_group.dmtou.tagscloud;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "MainFragment";
    private static final String ARG_PARAM_INDEX_MIND = "ARG_PARAM_INDEX_MIND";

    private int indexMind = -1;

    @IdRes
    private int subIndexMind = 0;

    RelativeLayout cloudRelativeLayout;

    private static final String NEW_MIND_TITLE = "your mind";

    // массив из всех мыслей
    //private ArrayList tags = new ArrayList();
    TagsDBHelper tagsDBHelper;
    private EditText activeMindET;

    private Random random = new Random();

    /*
    * Размер экрана фрагмента
    * @TODO сделать более точные координаты
    * */
    private int cloudWidth;
    private int cloudHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tagsDBHelper = new TagsDBHelper(getContext());
        if (getArguments() != null)
            indexMind = getArguments().getInt(ARG_PARAM_INDEX_MIND);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsEditActiveView(false);
            }
        });
        return view;
    }

    GestureDetectorCompat gestureDetector;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gestureDetector = new GestureDetectorCompat(getActivity(), new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                openSubCloudByView(activeMindET);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                setIsEditActiveView(true);
                return false;
            }
        });

        /*
        * Находим главный лайаут в xml, для добавления в него тегов
        * */
        cloudRelativeLayout = (RelativeLayout) view.findViewById(R.id.cloud_relative_layout);

        /*
        * Определяем размер экрана
        * */
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        cloudHeight = (size.y / 3) * 2;
        cloudWidth = (size.x / 3) * 2;
        Log.i(TAG, "Открыли новое облако cloud_relative_layout: width (" + cloudWidth + ") ,height (" + cloudHeight + ")");

        /*
        * Добавляем слушательна кнопку New
        * */
        Button newMindBtn = (Button) view.findViewById(R.id.new_mind_btn);
        newMindBtn.setOnClickListener(this);

        SQLiteDatabase db = tagsDBHelper.getWritableDatabase();
        Cursor c = db.query("TagsTable",null,null,null,null,null,null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int tag_nameColIndex = c.getColumnIndex("tag_name");
            int printColIndex = c.getColumnIndex("print");
            int sizeColIndex = c.getColumnIndex("size");
            int bold_italicColIndex = c.getColumnIndex("bold_italic");
            int parent_idColIndex = c.getColumnIndex("parent_id");

            do {
                int parent_id = c.getInt(parent_idColIndex);
                //subIndexMind++;
                if (parent_id == indexMind) {
                    subIndexMind = c.getInt(idColIndex);
                    String tag_name = c.getString(tag_nameColIndex);
                    String print = c.getString(printColIndex);
                    double size_text = c.getFloat(sizeColIndex);
                    String bold_italic = c.getString(bold_italicColIndex);
                    int leftMargin = random.nextInt(cloudWidth);
                    int topMargin = random.nextInt(cloudHeight);
                    addMind(subIndexMind, leftMargin, topMargin, tag_name, print, size_text, bold_italic, 3);
                } else subIndexMind++;

            } while (c.moveToNext());
            subIndexMind++;
        }

        /*
        * @TODO
        * Если этот фрагмент открылся с параметром - старый фрагмент,
        * тогда добавляем 'мысль' по середине экрана
        * */
        /*if(indexMind != -1) {
            addMind(indexMind, 0, 0,"","",-1.0,"");
        }*/
    }



    private void setIsEditActiveView(boolean focusableInTouchMode) {
        if(activeMindET == null) {
            return;
        }

        activeMindET.setFocusableInTouchMode(focusableInTouchMode);
        activeMindET.setFocusable(focusableInTouchMode);
        if(!focusableInTouchMode) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activeMindET.getWindowToken(), 0);
        }
    }

    /*
    * Добавление 'мысли' в рандомное место на экран (но не больше чем cloudHeight, cloudWidth)
    * */


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_mind_btn) {
            Log.i(TAG, "Клик по кнопке New");
            int leftMargin = random.nextInt(cloudWidth);
            int topMargin = random.nextInt(cloudHeight);
            addMind(subIndexMind, leftMargin, topMargin, "", "", -1.0f ,"",0);
        }
    }

    private void openSubCloudByView(View v) {
        Log.i(TAG, "Клик по мысли с id: " + v.getId());

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(ARG_PARAM_INDEX_MIND, v.getId());
        startActivity(intent);
    }

    private void addMind(int newId, int leftMargin, int topMargin, String loaded_tag_name, String loaded_print, double loaded_size, String loaded_state, int flag) {
        Log.i(TAG, "Добавляем новую мысль с id: " + newId);
        EditText newMindEditText = new EditText(getContext());
        newMindEditText.setId(newId);
        newMindEditText.setGravity(Gravity.CENTER);
        String title = "";
        if (loaded_tag_name.equals("")) title = String.format("%s %d", NEW_MIND_TITLE, newId); else title = String.format("%s", loaded_tag_name);
        newMindEditText.setText(title);
        newMindEditText.setFocusableInTouchMode(false);
        newMindEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        /*
           * @TODO
           * load print
        */
        if (loaded_size == -1.0f) newMindEditText.setTextSize(40); else newMindEditText.setTextSize((float)(loaded_size/10));
        newMindEditText.setTextColor(Color.BLACK);
        if (loaded_state.equals("")) newMindEditText.setTypeface(null, Typeface.BOLD_ITALIC);
        else {
            switch (loaded_state) {
                case "00": newMindEditText.setTypeface(null, Typeface.NORMAL); break;
                case "01": newMindEditText.setTypeface(null, Typeface.ITALIC); break;
                case "10": newMindEditText.setTypeface(null, Typeface.BOLD); break;
                case "11": newMindEditText.setTypeface(null, Typeface.BOLD_ITALIC); break;
            }
        }

        // Добавить 'мысль' на страницу в рандомное место
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(leftMargin, topMargin, 0, 0);
        newMindEditText.setLayoutParams(params);
        cloudRelativeLayout.addView(newMindEditText);

        newMindEditText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                activeMindET = (EditText) v;
                return gestureDetector.onTouchEvent(event);
            }
        });

        // @TODO сохранение в БД
        //tags.add(newMindEditText);
        if (flag==0) {
            String tag_name = newMindEditText.getText().toString();
            String print = "";
            double size_text = newMindEditText.getTextSize();
            String bold_italic = "";
            if (newMindEditText.getTypeface().isBold()) bold_italic = bold_italic + "1";
            else bold_italic = bold_italic + "0";
            if (newMindEditText.getTypeface().isItalic()) bold_italic = bold_italic + "1";
            else bold_italic = bold_italic + "0";

            updateBD(newId, tag_name, print, size_text, bold_italic, flag);
            Log.i(TAG, "Добавили новую мысль на позицию: leftMargin" + params.leftMargin + ",topMargin" + params.topMargin);
        }
        subIndexMind++;
    }

    private void updateBD(int newId, String loaded_tag_name, String loaded_print, double loaded_size, String loaded_state, int flag) {
        // 0 - insert; // 1- update; //2 - delete;

        switch (flag){
            case 0:
                ContentValues cv = new ContentValues();
                cv.put("id", newId);
                cv.put("tag_name", loaded_tag_name);
                cv.put("print", loaded_print);
                cv.put("size", loaded_size);
                cv.put("bold_italic", loaded_state);
                cv.put("parent_id", indexMind);
                SQLiteDatabase db = tagsDBHelper.getWritableDatabase();
                db.insert("TagsTable", null, cv);
                db.close();
                Log.i(TAG, "добавлена запись в БД");
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
