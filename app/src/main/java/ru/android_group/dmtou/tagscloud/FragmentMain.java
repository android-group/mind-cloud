package ru.android_group.dmtou.tagscloud;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class FragmentMain extends Fragment implements View.OnClickListener {

    private LinearLayout layoutMainTags,layoutAddTags,layoutTags;
    private static final int IDMAL = 1_000_000_000; //ID Main Activity Layout
    private static final int[] IDLAYOUTSF1 = {IDMAL+1,IDMAL+2,IDMAL+3}; //ID Fragment Layouts
    private Button addTag;
    private static final int IDADD = 2_000_000_000; //ID BUTTON "ADD" TAGS
    private ArrayList tags = new ArrayList();
    private int k = 1_000_000;
    private final int indexk = k;

    FragmentTag fragmentTag;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private String fragmentsStack = "frStack";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();
        //                  //FRAGMENT TAGS MAIN LAYOUT
        layoutMainTags = new LinearLayout(context);
        LinearLayout.LayoutParams lllpMainTags = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutMainTags.setOrientation(LinearLayout.VERTICAL);
        layoutMainTags.setId(IDLAYOUTSF1[0]);
        layoutMainTags.setLayoutParams(lllpMainTags);
        //                  //FRAGMENT TAGS LAYOUT ADD
        layoutAddTags = new LinearLayout(context);
        layoutAddTags.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllpADD= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllpADD.weight = 10;
        layoutAddTags.setLayoutParams(lllpADD);
        layoutAddTags.setId(IDLAYOUTSF1[1]);
        layoutMainTags.addView(layoutAddTags,lllpADD);
        //                    //BUTTON ADD TAGS
        addTag = new Button(context);
        addTag.setId(IDADD);
        addTag.setText("Добавить тег");
        LinearLayout.LayoutParams lllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lllp2.gravity = Gravity.RIGHT;
        addTag.setLayoutParams(lllp2);
        addTag.setOnClickListener(this);
        layoutAddTags.addView(addTag,lllp2);
        //                    //TAGS LAYOUT
        layoutTags = new LinearLayout(context);
        layoutTags.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lllp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lllp3.weight = 1;
        layoutTags.setId(IDLAYOUTSF1[2]);
        layoutMainTags.addView(layoutTags,lllp3);

        return layoutMainTags;
    }

    @Override
    public void onClick(View v){
        Context context = getActivity().getApplicationContext();
        if (v.getId() == IDADD) {
            TextView obj1 = new TextView(context);
            obj1.setId(k);
            obj1.setText("TAG " + String.valueOf(k-indexk));
            obj1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            obj1.setOnClickListener(this);
            obj1.setTextSize(40);
            obj1.setTextColor(Color.BLACK);
            obj1.setTypeface(null, Typeface.BOLD_ITALIC);
            layoutTags.addView(obj1);
            tags.add(k-indexk,obj1);
            k++;
        }
        else {
            if (v.getClass().getName().equals("android.widget.TextView")) {
                fragmentManager = getFragmentManager();
                fragmentTag = new FragmentTag();
                transaction = fragmentManager.beginTransaction();
                transaction.hide(this);
                transaction.add(IDMAL,fragmentTag);
                transaction.addToBackStack(fragmentsStack);
                transaction.commit();
            }
        }

    }
}