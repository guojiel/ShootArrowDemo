package com.guojiel.shootarrowdemo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private ArrowLayout mArrowLayout;
    private CheckBox mCheckBox;
    private RadioGroup mLeftRadioGroup;
    private RadioGroup mRightRadioGroup;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_main);
        mArrowLayout = findViewById(R.id.mArrowLayout);
        mCheckBox = findViewById(R.id.mCheckBox);
        mLeftRadioGroup = findViewById(R.id.mLeftRadioGroup);
        mRightRadioGroup = findViewById(R.id.mRightRadioGroup);
        mBtn = findViewById(R.id.mBtn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int from = Integer.parseInt(((RadioButton)mLeftRadioGroup.findViewById(mLeftRadioGroup.getCheckedRadioButtonId())).getText().toString());
                int to = Integer.parseInt(((RadioButton)mRightRadioGroup.findViewById(mRightRadioGroup.getCheckedRadioButtonId())).getText().toString());
                if(mCheckBox.isChecked()){
                    mArrowLayout.leftElection(from, to);
                }else{
                    mArrowLayout.rightElection(from, to);
                }
            }
        });
    }



    private void setupTheme() {
        Window window = getWindow();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(visibility);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
