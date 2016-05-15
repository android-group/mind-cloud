package ru.android_group.dmtou.tagscloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int[] ID = { 0,1,2,3,4,5,6,7,8,9 };
    private Button[] mainSpisok = new Button[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0;i<10; i++) {
            mainSpisok[i] = new Button(this);
            mainSpisok[i].setId(ID[i]);
            mainSpisok[i].setText("Элемент " +String.valueOf(ID[i])+" главного списка");
            mainSpisok[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            mainSpisok[i].setOnClickListener(this);
            //mainSpisok[i].setVisibility(View.VISIBLE);
            layout.addView(mainSpisok[i]);
        }
        this.addContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View v){
        //mainSpisok[0].setText(v.getClass().getName());
        //if (v.getClass().getName().equals("android.widget.Button")) mainSpisok[1].setText("da");
        //mainSpisok[2].setText(String.valueOf(mainSpisok[2].getId()));
        switch (v.getClass().getName()) {
            case "android.widget.Button":
                mainSpisok[v.getId()].setText( "Нажата кнопка с индексом " + String.valueOf(v.getId()) );
            break;
        }
    }
}
