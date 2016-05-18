package ru.android_group.dmtou.tagscloud;

/**
 * Created by DmTou on 19.05.2016.
 */

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentMain extends Fragment implements View.OnClickListener{

    private static final int[] IDLAYOUTS = {10000,10001,10002};
    private static final int[] ID = { 0,1,2,3,4,5,6,7,8,9 };
    private Button[] mainSpisok = new Button[10];
    private int k = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();

        LinearLayout layoutMain = new LinearLayout(context);
        LinearLayout.LayoutParams lllp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutMain.setOrientation(LinearLayout.VERTICAL);
        layoutMain.setLayoutParams(lllp0);
        //context.addContentView(layoutMain,lllp0);

        LinearLayout layoutAdd = new LinearLayout(context);
        layoutAdd.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllp1.weight = 10;
        layoutAdd.setLayoutParams(lllp1);
        layoutAdd.setId(IDLAYOUTS[1]);
        layoutMain.addView(layoutAdd,lllp1);
        //                    //Кнопка ДОБАВИТЬ
        Button addTag = new Button(context);
        addTag.setId(ID[0]);
        addTag.setText("Добавить тег");
        LinearLayout.LayoutParams lllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lllp2.gravity = Gravity.RIGHT;
        addTag.setLayoutParams(lllp2);
        addTag.setOnClickListener(this);
        layoutAdd.addView(addTag,lllp2);
        //                    //LAYOUT с тегами
        LinearLayout layoutTags = new LinearLayout(context);
        layoutTags.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllp3.weight = 1;
        layoutTags.setId(IDLAYOUTS[2]);
        layoutMain.addView(layoutTags,lllp3);

        return layoutMain;
    }

    @Override
    public void onClick(View v){
        //                  //Обработчик нажатия на кнопку добавить (максимум 10 тегов)
        switch (v.getId()) {
            case 0:{
                if (k<10) {
                    Context context = getActivity().getApplicationContext();
                    LinearLayout layoutTags = (LinearLayout) getView().findViewById(IDLAYOUTS[2]);
                    mainSpisok[k] = new Button(context);
                    mainSpisok[k].setId(ID[k]);
                    mainSpisok[k].setText("Элемент " + String.valueOf(ID[k]) + " главного списка");
                    mainSpisok[k].setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    mainSpisok[k].setOnClickListener(this);
                    layoutTags.addView(mainSpisok[k]);
                    k++;
                }
            }
            break;
        }

    }
}