package ru.android_group.dmtou.tagscloud;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "MainFragment";
    private static final String ARG_PARAM_INDEX_MIND = "ARG_PARAM_INDEX_MIND";

    private int indexMind = -1;

    @IdRes
    private int subIndexMind = 0;

    RelativeLayout cloudRelativeLayout;

    private static final String NEW_MIND_TITLE = "your mind ";

    private static final String FRAGMENTS_STACK = "FRAGMENTS_STACK";

    // массив из всех мыслей
    private ArrayList tags = new ArrayList();

    /*
    * Размер экрана фрагмента
    * @TODO сделать более точные координаты
    * */
    private int cloudWidth;
    private int cloudHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            indexMind = getArguments().getInt(ARG_PARAM_INDEX_MIND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        * Находим главный лайаут в xml, для добавления в него тегов
        * */
        cloudRelativeLayout = (RelativeLayout) view.findViewById(R.id.cloud_relative_layout);

        /*
        * Определяем размер экрана
        * */
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        cloudHeight = size.y / 2;
        cloudWidth = size.x / 2;
        Log.i(TAG, "Открыли новое облако cloud_relative_layout: width (" + cloudWidth + ") ,height (" + cloudHeight + ")");

        /*
        * Добавляем слушательна кнопку New
        * */
        Button newMindBtn = (Button) view.findViewById(R.id.new_mind_btn);
        newMindBtn.setOnClickListener(this);

        /*
        * @TODO
        * Если этот фрагмент открылся с параметром - старый фрагмент,
        * тогда добавляем 'мысль' по середине экрана
        * */
        if(indexMind != -1) {
            addMind(indexMind);
        }
    }

    /*
    * Добавление 'мысли' в рандомное место на экран (но не больше чем cloudHeight, cloudWidth)
    * */
    private void addMind(int mainIndexMind) {
        Log.i(TAG, "Добавляем новую мысль с id: " + mainIndexMind);
        TextView newMind = new TextView(getContext());
        newMind.setId(mainIndexMind);

        String title = String.format("%s%d", NEW_MIND_TITLE, mainIndexMind);
        newMind.setText(title);

        newMind.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        newMind.setOnClickListener(this);
        newMind.setTextSize(40);
        newMind.setTextColor(Color.BLACK);
        newMind.setTypeface(null, Typeface.BOLD_ITALIC);

        // Добавить 'мысль' на страницу в рандомное место
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Random random = new Random();
        params.leftMargin = random.nextInt(cloudWidth);
        params.topMargin = random.nextInt(cloudHeight);
        newMind.setLayoutParams(params);

        cloudRelativeLayout.addView(newMind);

        // @TODO сохранение в БД
        tags.add(newMind);
        Log.i(TAG, "Добавили новую мысль на позицию: leftMargin" + params.leftMargin + ",topMargin" + params.topMargin);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_mind_btn) {
            Log.i(TAG, "Клик по кнопке New");
            addMind(subIndexMind);
            subIndexMind++;
        } else {
            Log.i(TAG, "Клик по мысли с id: " + v.getId());
            if (v.getClass().getName().equals("android.widget.TextView")) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra(ARG_PARAM_INDEX_MIND, v.getId());
                startActivity(intent);
            }
        }
    }
}
