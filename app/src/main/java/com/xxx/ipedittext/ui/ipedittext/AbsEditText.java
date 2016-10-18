package com.xxx.ipedittext.ui.ipedittext;

import android.content.Context;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by idea on 2016/7/15.
 */
public abstract class AbsEditText extends EditText {
    public AbsEditText(Context context) {
        this(context, null, 0);
    }

    public AbsEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addInputFilter();
    }


    protected void addInputFilter(){
        setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return getInputFilterAcceptedChars();
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_NUMBER;
            }
        });
    }


    /**
     * 输入内容过滤
     * @return
     */
    public abstract char[] getInputFilterAcceptedChars();

    /**
     * 输入内容检查是否ok
     * @return
     */
    public abstract boolean checkInputValue();


}
