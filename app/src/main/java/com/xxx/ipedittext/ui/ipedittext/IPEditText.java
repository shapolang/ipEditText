package com.xxx.ipedittext.ui.ipedittext;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;

/**
 * Created by idea on 2016/7/15.
 */
public class IPEditText extends AbsEditText {

    public IPEditText(Context context,int length) {
        this(context, null, 0,length);
    }


    public IPEditText(Context context, AttributeSet attrs, int defStyleAttr,int length) {
        super(context, attrs, defStyleAttr);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }





    @Override
    public char[] getInputFilterAcceptedChars() {
        return new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    @Override
    public boolean checkInputValue() {
        return getText().length()==0?false:true;
    }
}