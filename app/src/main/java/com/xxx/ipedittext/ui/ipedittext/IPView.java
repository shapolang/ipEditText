package com.xxx.ipedittext.ui.ipedittext;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.xxx.ipedittext.R;


/**
 * Created by idea on 2016/7/15.
 */
public class IPView extends AbsEditTextGroup {


    public IPView(Context context) {
        super(context);
    }

    public IPView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getChildCount() {
        return 9;
    }

    @Override
    public AbsEditText getAbsEditText(int length) {
        return new IPEditText(getContext(),length);
    }

    @Override
    public String getSemicolomText() {
            return ".";
    }

    @Override
    public int getDelMaxLength() {
        return 3;
    }

    @Override
    public void applySemicolonTextViewTheme(TextView semicolonTextView) {
        semicolonTextView.setPadding(0,0,0,5);
        semicolonTextView.getPaint().setFakeBoldText(true);
        semicolonTextView.setBackgroundColor(getResources().getColor(R.color.ipBg));
        semicolonTextView.setGravity(Gravity.CENTER);
    }

    @Override
    public void applyEditTextTheme(AbsEditText absEditText) {

    }
}
