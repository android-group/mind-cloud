package ru.android_group.dmtou.tagscloud;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Stack;

import static ru.android_group.dmtou.tagscloud.DatabaseHelper.*;
import static ru.android_group.dmtou.tagscloud.DatabaseHelper.EventDB.*;

public class CloudFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "CloudFragment";
    private static final String ARG_PARAM_INDEX_MIND = "ARG_PARAM_INDEX_MIND";
    private RelativeLayout cloudRelativeLayout;
    DatabaseHelper databaseHelper;
    GestureDetectorCompat gestureDetector;
    private int indexMind = 0;
    @IdRes
    private int subIndexMind = 0;
    private EditText activeMindET;
    private EditText newMindET;
    /*
    * Размер экрана фрагмента
    * @TODO сделать более точные координаты
    * */
    private int cloudX;
    private int cloudY;
    private static float LOADED_SIZE = -1.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        if (getArguments() != null) {
            indexMind = getArguments().getInt(ARG_PARAM_INDEX_MIND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsEditActiveView(false);
            }
        });
        Toast.makeText(getContext(), "just start to type your mind", Toast.LENGTH_SHORT).show();
        
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gestureDetector = new GestureDetectorCompat(getActivity(), new GestureDetector.SimpleOnGestureListener() {

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
        cloudX = size.x;
        cloudY = size.y;
        Log.i(TAG, "Открыли новое облако cloud_relative_layout: width (" + cloudX + ") ,height (" + cloudY + ")");
        generateAvailablePlacesFromMaxSize();

        /*
        * Добавляем слушательна кнопку New
        * */
        newMindET = (EditText) view.findViewById(R.id.new_mind_et);
        newMindET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.i(TAG, "Добавили по KEYCODE_ENTER");
                    addNewMindInRandomPlace();
                }
                return false;
            }
        });

        Button newMindBtn = (Button) view.findViewById(R.id.new_mind_btn);
        newMindBtn.setOnClickListener(this);

        for (Mind mind : databaseHelper.getAll(indexMind)) {
            if(!availablePlaces.isEmpty()) {
                AvailablePlace place = availablePlaces.pop();
                place.setName(mind.getTagName());
                addMind(mind.getSubIndexMind(), place, mind.getSizeText(), mind.getTypeface(), NOTHING);
            }
        }
    }

    private Stack<AvailablePlace> availablePlaces = new Stack<>();

    private void generateAvailablePlacesFromMaxSize() {
        if(cloudX > 0 && cloudY > 0) {
            addStillSecondCycleTag();
            addSecondCycleTag();
            addFirstCycleTag();
            addPlaceForMainTag();
        }
    }

    private void addStillSecondCycleTag() {
        AvailablePlace availablePlaceRight = new AvailablePlace(cloudX * 7 / 6, cloudY / 4, "Still Right");
        availablePlaces.push(availablePlaceRight);

        AvailablePlace availablePlaceLeft = new AvailablePlace(cloudX * 5 / 6, cloudY / 4, "Still Left");
        availablePlaces.push(availablePlaceLeft);

        AvailablePlace availablePlaceTop = new AvailablePlace(cloudX * 6 / 6, cloudY / 6, "Still Top");
        availablePlaces.push(availablePlaceTop);

        AvailablePlace availablePlaceBottom = new AvailablePlace(cloudX * 6 / 6, cloudY * 2 / 6, "Still Bottom");
        availablePlaces.push(availablePlaceBottom);
    }

    private void addSecondCycleTag() {
        AvailablePlace availablePlaceRight = new AvailablePlace(cloudX * 2 / 6, cloudY / 4, "SecondCycle Right");
        availablePlaces.push(availablePlaceRight);

        AvailablePlace availablePlaceLeft = new AvailablePlace(cloudX / 6, cloudY /4, "SecondCycle Left");
        availablePlaces.push(availablePlaceLeft);

        AvailablePlace availablePlaceTop = new AvailablePlace(cloudX / 4, cloudY / 6, "SecondCycle Top");
        availablePlaces.push(availablePlaceTop);

        AvailablePlace availablePlaceBottom = new AvailablePlace(cloudX / 4, cloudY * 2 / 6, "SecondCycle Bottom");
        availablePlaces.push(availablePlaceBottom);
    }

    private void addFirstCycleTag() {
        AvailablePlace availablePlaceRight = new AvailablePlace(cloudX * 2 / 3, cloudY / 2, "Right");
        availablePlaces.push(availablePlaceRight);

        AvailablePlace availablePlaceLeft = new AvailablePlace(cloudX / 3, cloudY / 2, "Left");
        availablePlaces.push(availablePlaceLeft);

        AvailablePlace availablePlaceTop = new AvailablePlace(cloudX / 2, cloudY / 3, "Top");
        availablePlaces.push(availablePlaceTop);

        AvailablePlace availablePlaceBottom = new AvailablePlace(cloudX / 2, cloudY * 2 / 3, "Bottom");
        availablePlaces.push(availablePlaceBottom);
    }

    private void addPlaceForMainTag() {
        AvailablePlace availablePlaceCenter = new AvailablePlace(cloudX / 2, cloudY / 2, "Center");
        availablePlaces.push(availablePlaceCenter);
    }


    private void setIsEditActiveView(boolean focusableInTouchMode) {
        if (activeMindET == null) {
            return;
        }

        activeMindET.setFocusableInTouchMode(focusableInTouchMode);
        activeMindET.setFocusable(focusableInTouchMode);
        if (!focusableInTouchMode) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activeMindET.getWindowToken(), 0);
        }
    }

    /*
    * Добавление 'мысли' в рандомное место на экран (но не больше чем cloudY, cloudX)
    * */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_mind_btn) {
            hideKeyBoard();
            Log.i(TAG, "Добавили по NEW");
            addNewMindInRandomPlace();
        }
    }

    private void hideKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void addNewMindInRandomPlace() {
        hideKeyBoard();
        if(!availablePlaces.isEmpty() && newMindET.getText().length() > 0) {
            AvailablePlace availablePlace = availablePlaces.pop();
            availablePlace.setName(newMindET.getText().toString());

            addMind(subIndexMind, availablePlace , LOADED_SIZE, Typeface.NORMAL, INSERT);
        }
        newMindET.setText("");
    }

    private void openSubCloudByView(View v) {
        Log.i(TAG, "Клик по мысли с id: " + v.getId());

        Intent intent = new Intent(getContext(), CloudActivity.class);
        intent.putExtra(ARG_PARAM_INDEX_MIND, v.getId());
        startActivity(intent);
    }

    private void update(int newId, String loadedTagName, String loadedPrint, double loadedSize, int typeface, EventDB eventDB) {
        Toast.makeText(getContext(), eventDB.name(), Toast.LENGTH_LONG).show();
        try {
            switch (eventDB) {
                case INSERT:
                    databaseHelper.insert(newId, loadedTagName, loadedPrint, loadedSize, typeface, indexMind);
                    break;
                case UPDATE:
                    // nothing @TODO
                    break;
                case DELETE:
                    // nothing @TODO
                    break;
            }
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getContext(), "Нельзя добавить мысль с таким же ID", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addMind(int newId, AvailablePlace place, double loadedSize, int typeface, EventDB eventDB) {
        Log.i(TAG, "Добавляем новую мысль с id: " + newId);

        EditText newMindEditText = new EditText(getActivity());
        newMindEditText.setId(newId);
        newMindEditText.setText(place.getName());
        newMindEditText.setFocusableInTouchMode(false);
        newMindEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        if (loadedSize == LOADED_SIZE) {
            int defaultTextSize = 20;
            newMindEditText.setTextSize(defaultTextSize);
        } else {
            newMindEditText.setTextSize((float) (loadedSize));
        }

        newMindEditText.setTextColor(Color.BLACK);
        newMindEditText.setTypeface(null, Typeface.BOLD_ITALIC);

        newMindEditText.setX(place.getX());
        newMindEditText.setY(place.getY());

        cloudRelativeLayout.addView(newMindEditText);

        newMindEditText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                activeMindET = (EditText) v;
                return gestureDetector.onTouchEvent(event);
            }
        });

        if (eventDB == INSERT) {
            String tagName = newMindEditText.getText().toString();
            String print = "";
            double sizeText = newMindEditText.getTextSize();

            update(newId, tagName, print, sizeText, newMindEditText.getTypeface().getStyle(), eventDB);
            Log.i(TAG, "Добавили новую мысль на позицию: x" + place.getX() + ",topMargin" + place.getY());
        }
        subIndexMind++;
    }
}