package ru.android_group.dmtou.tagscloud;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FragmentMain fragmentMain;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private static final int IDMAL = 1_000_000_000; //ID Main Activity Layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //                    //MAIN ACTIVITY LAYOUT
        LinearLayout layoutContainer = new LinearLayout(this);
        LinearLayout.LayoutParams lllpMA = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        layoutContainer.setOrientation(LinearLayout.VERTICAL);
        layoutContainer.setLayoutParams(lllpMA);
        layoutContainer.setId(IDMAL);
        this.addContentView(layoutContainer,lllpMA);

        fragmentManager = getFragmentManager();
        fragmentMain = new FragmentMain();
        transaction = fragmentManager.beginTransaction();
        transaction.add(IDMAL,fragmentMain);
        transaction.commit();
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
