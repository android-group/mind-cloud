package ru.android_group.dmtou.tagscloud;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import ru.android_group.dmtou.tagscloud.FragmentMain;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int[] ID = { 0,1,2,3,4,5,6,7,8,9 };  //ID тегов
    private Button[] mainSpisok = new Button[10];              //список тегов (заменить на label)
    private int k = 1;                                       //начальный номер ID тегов (0 под кнопку добавить)
    private static final int[] IDLAYOUTS = {10000,10001,10002};
    FragmentMain fragmentMain;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //                    //MAIN LAYOUT
        LinearLayout layoutContainer = new LinearLayout(this);
        LinearLayout.LayoutParams lllp0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutContainer.setOrientation(LinearLayout.VERTICAL);
        layoutContainer.setLayoutParams(lllp0);
        layoutContainer.setId(IDLAYOUTS[0]);
        this.addContentView(layoutContainer,lllp0);

        fragmentManager = getFragmentManager();
        fragmentMain = new FragmentMain();
        transaction = fragmentManager.beginTransaction();
        transaction.add(IDLAYOUTS[0],fragmentMain);
        transaction.commit();
    }

    @Override
    public void onClick(View v){

    }
}
