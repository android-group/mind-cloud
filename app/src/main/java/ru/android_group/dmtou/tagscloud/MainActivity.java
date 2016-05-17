package ru.android_group.dmtou.tagscloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int[] ID = { 0,1,2,3,4,5,6,7,8,9 };  //ID тегов
    private Button[] mainSpisok = new Button[10];              //список тегов (заменить на label)
    private int k = 1;                                       //начальный номер ID тегов (0 под кнопку добавить)
    private static final int[] IDLAYOUTS = {10001,10002};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //                    //MAIN LAYOUT
        LinearLayout layoutMain = new LinearLayout(this);
        LinearLayout.LayoutParams lllp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutMain.setOrientation(LinearLayout.VERTICAL);
        layoutMain.setLayoutParams(lllp0);
        this.addContentView(layoutMain,lllp0);
        //                    //LAYOUT с кнопкой добавить
        LinearLayout layoutAdd = new LinearLayout(this);
        layoutAdd.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllp1.weight = 10;
        layoutAdd.setLayoutParams(lllp1);
        layoutAdd.setId(IDLAYOUTS[0]);
        /*for (int i = 0;i<10; i++) {
            mainSpisok[i] = new Button(this);
            mainSpisok[i].setId(ID[i]);
            mainSpisok[i].setText("Элемент " +String.valueOf(ID[i])+" главного списка");
            mainSpisok[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            mainSpisok[i].setOnClickListener(this);
            //mainSpisok[i].setVisibility(View.VISIBLE);
            layout.addView(mainSpisok[i]);
        }*/
        //                    //Кнопка ДОБАВИТЬ
        Button addTag = new Button(this);
        addTag.setId(ID[0]);
        addTag.setText("Добавить тег");
        LinearLayout.LayoutParams lllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lllp2.gravity = Gravity.RIGHT;
        addTag.setLayoutParams(lllp2);
        addTag.setOnClickListener(this);
        layoutAdd.addView(addTag,lllp2);
        layoutMain.addView(layoutAdd,lllp1);
        //                    //LAYOUT с тегами
        LinearLayout layoutTags = new LinearLayout(this);
        layoutTags.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllp3.weight = 1;
        layoutTags.setId(IDLAYOUTS[1]);
        /*Button b1 = new Button(this);
        b1.setLayoutParams(lllp2);
        layoutTags.addView(b1,lllp2);*/
        layoutMain.addView(layoutTags,lllp3);
    }

    @Override
    public void onClick(View v){
        //mainSpisok[0].setText(v.getClass().getName());
        //if (v.getClass().getName().equals("android.widget.Button")) mainSpisok[1].setText("da");
        //mainSpisok[2].setText(String.valueOf(mainSpisok[2].getId()));
        /*switch (v.getClass().getName()) {
            case "android.widget.Button":
                mainSpisok[v.getId()].setText( "Нажата кнопка с индексом " + String.valueOf(v.getId()) );
            break;
        }*/
        //                  //Обработчик нажатия на кнопку добавить (максимум 10 тегов)
        switch (v.getId()) {
            case 0:{
                if (k<10) {
                    LinearLayout layoutTags = (LinearLayout) findViewById(IDLAYOUTS[1]);
                    mainSpisok[k] = new Button(this);
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
