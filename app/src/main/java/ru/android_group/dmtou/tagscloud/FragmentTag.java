package ru.android_group.dmtou.tagscloud;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentTag extends Fragment implements View.OnClickListener{

    LinearLayout layoutMainThoughts,layoutAddThoughts,layoutThouths;
    private static final int IDMAL = 1_000_000_000; //ID Main Activity Layout
    private static final int[] IDLAYOUTSF2 = {IDMAL+4,IDMAL+5,IDMAL+6}; //ID Fragment Layouts
    private Button addThoughts;
    private static final int IDADDTH = 2_000_000_001; //ID BUTTON "ADD" THOUGHTS
    private int k = 2_000_000;
    private final int indexk = k;
    private ArrayList thoughts = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();
        //                  //FRAGMENT THOUGHTS MAIN LAYOUT
        layoutMainThoughts = new LinearLayout(context);
        LinearLayout.LayoutParams lllpMainThoughts = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutMainThoughts.setOrientation(LinearLayout.VERTICAL);
        layoutMainThoughts.setId(IDLAYOUTSF2[0]);
        layoutMainThoughts.setLayoutParams(lllpMainThoughts);
        //                   //FRAGMENT TAGS LAYOUT ADD
        layoutAddThoughts = new LinearLayout(context);
        layoutAddThoughts.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllpAddTh = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllpAddTh.weight = 10;
        layoutAddThoughts.setLayoutParams(lllpAddTh);
        layoutAddThoughts.setId(IDLAYOUTSF2[1]);
        layoutMainThoughts.addView(layoutAddThoughts,lllpAddTh);
        //                    //BUTTON ADD THOUGHTS
        addThoughts = new Button(context);
        addThoughts.setId(IDADDTH);
        addThoughts.setText("Добавить дочерний тег");
        LinearLayout.LayoutParams lllpAddThB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lllpAddThB.gravity = Gravity.RIGHT;
        addThoughts.setLayoutParams(lllpAddThB);
        addThoughts.setOnClickListener(this);
        layoutAddThoughts.addView(addThoughts,lllpAddThB);
        //                    //LAYOUT with Thoughts
        layoutThouths = new LinearLayout(context);
        layoutThouths.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllpThoughts = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllpThoughts.weight = 1;
        layoutThouths.setId(IDLAYOUTSF2[2]);
        layoutMainThoughts.addView(layoutThouths,lllpThoughts);

        return layoutMainThoughts;
    }

    @Override
    public void onClick(View v){
        Context context = getActivity().getApplicationContext();
        if (v.getId() == IDADDTH) {
            TextView obj1 = new TextView(context);
            obj1.setId(k);
            obj1.setText("THOUGHT " + String.valueOf(k-indexk));
            obj1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            obj1.setOnClickListener(this);
            obj1.setTextSize(20);
            obj1.setTextColor(Color.BLACK);
            obj1.setTypeface(null, Typeface.ITALIC);
            layoutThouths.addView(obj1);
            thoughts.add(k-indexk,obj1);
            k++;
        }
    }

}