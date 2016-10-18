package com.xxx.ipedittext.ui.ipedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xxx.ipedittext.R;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Created by idea on 2016/7/18.
 */
public abstract class AbsEditTextGroup extends LinearLayout implements TextWatcher {

    protected float sp16 = 16.0f;
    protected int dp4 = 4;
    private ArrayList<AbsEditText> editTexts = new ArrayList<AbsEditText>();

    public AbsEditTextGroup(Context context) {
        this(context, null, 0);
    }

    public AbsEditTextGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsEditTextGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addViews();
        buildListener();
        try {
            onFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addViews() {
        for (int i = 0; i < getChildCount(); i++) {
            if (i % 2 == 0) {
                if (i < 7) {
                    AbsEditText absEditText = createAbsEditText(getDelMaxLength());
                    editTexts.add(absEditText);
                    addView(absEditText);
                } else {
                    AbsEditText absEditText = createAbsEditText(5);
                    editTexts.add(absEditText);
                    addView(absEditText);
                }

            } else {
                if (i < 7) {
                    addView(createSemicolonTextView(null));
                } else {
                    addView(createSemicolonTextView(":"));
                }
            }
        }
    }

    protected AbsEditText createAbsEditText(int length) {

        AbsEditText absEditText = getAbsEditText(length);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        absEditText.setLayoutParams(params);
        absEditText.setTextSize(sp16);//sp
        absEditText.setTextColor(0xFF35322f);
        absEditText.setGravity(Gravity.CENTER);
        absEditText.setPadding(dp4, dp4, dp4, dp4);
        absEditText.setSingleLine();
        absEditText.setFocusableInTouchMode(true);
        absEditText.setBackgroundColor(getResources().getColor(R.color.ipBg));
        applyEditTextTheme(absEditText);
        return absEditText;
    }

    protected TextView createSemicolonTextView(String str) {
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setTextSize(sp16);//sp
        textView.setTextColor(0xFF35322f);
        textView.setBackgroundColor(getResources().getColor(R.color.ipBg));
        if (str == null) {
            textView.setText(getSemicolomText());
        } else {
            textView.setText(str);
        }
        applySemicolonTextViewTheme(textView);
        return textView;
    }

    protected void buildListener() {
        for (int i = 0; i < editTexts.size(); i++) {
            editTexts.get(i).addTextChangedListener(this);
            if (i != 0) {
                editTexts.get(i).setOnKeyListener(new OnDelKeyListener(editTexts.get(i - 1), editTexts.get(i)));
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        editTexts.get(0).setSelection(editTexts.get(0).getText().length());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    @Override
    public void afterTextChanged(Editable s) {
        //对ip和端口跳转
        try {
            edittextJump(s);
            limitIp();
            limitPort();
        } catch (Exception e) {
            Log.i("Aries", "清空ip框");
        }
    }

    //光标移动
    public void edittextJump(Editable s){
        if (Integer.parseInt(s.toString()) > 25) {
            for (int i = 0; i < editTexts.size() - 1; i++) {
                if (editTexts.get(i).hasFocus()) { //hasFocus √ & isFocus ×
                    editTexts.get(i).clearFocus();
                    editTexts.get(i + 1).requestFocus();
                    editTexts.get(i+1).setSelection(editTexts.get(i + 1).getText().length());
                    break;
                }
            }
        }
    }


    char [] a={};
    char [] b={'0', '1', '2', '3', '4', '5'};
    char [] c={'0', '1', '2', '3', '4', '5','6','7','8','9'};
    char [] temp=c;


    /**
     * 当得到焦点的时候触发
     */
    public  void onFocus(){
            for (int i = 0; i < editTexts.size() - 1; i++) {
                editTexts.get(i).setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            temp = c;//这句一定要有
                            String str=((EditText)v).getText().toString().trim();
                            if (!TextUtils.isEmpty(str)) {
                                if (Integer.parseInt(str) > 25) {
                                    temp = a;
                                } else if (Integer.parseInt(str) == 25) {
                                    temp = b;
                                } else {
                                    temp = c;
                                }
                            }
                            ((EditText)v).setKeyListener(new NumberKeyListener() {
                                    @Override
                                    protected char[] getAcceptedChars() {
                                        return temp;
                                    }

                                    @Override
                                    public int getInputType() {
                                        return InputType.TYPE_CLASS_NUMBER;
                                    }
                                });
                        }
                    }
                });
            }
    }



    public void limitIp(){
        char [] d=c;
        for (int i = 0; i < editTexts.size() - 1; i++){
            if (editTexts.get(i).hasFocus()){
                String str=editTexts.get(i).getText().toString();
                    if (Integer.parseInt(str) > 25) {
                        d = a;
                    } else if (Integer.parseInt(str) == 25) {
                        d = b;
                    } else {
                        d = c;
                    }
                final char[] finalD = d;
                editTexts.get(i).setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return finalD;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_CLASS_NUMBER;
                    }
                });



            }
        }

    }


    public void limitPort(){
        char [] temp=c;
        String str=editTexts.get(editTexts.size()-1).getText().toString();
        //端口
        if (editTexts.get(editTexts.size()-1).hasFocus()){
                if (Integer.parseInt(str) > 6553) {
                    temp = a;
                } else if (Integer.parseInt(str) == 6553) {
                    temp = b;
                } else {
                    temp = c;
                }
            final char[] finalTemp = temp;
            editTexts.get(editTexts.size()-1).setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return finalTemp;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_CLASS_NUMBER;
                    }
                });
            }
    }


    // 判断输入的IP是否合法
    private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }

    //判断是否合法
    public boolean IsValid(){
        for (int i=0;i< editTexts.size() ;i++){
            if (TextUtils.isEmpty(editTexts.get(i).getText().toString().trim())){
                editTexts.get(i).setHint("不能为空");
                editTexts.get(i).requestFocus();
                return false;
            }
        }

//        if (!checkIP(getIp())){
//            editTexts.get(0).setHint("ip不合法");
//            return false;
//        }
        return true;
    }



    public boolean checkInputValue(AbsEditText... params) {
        boolean result = true;
        for (int i = 0; i < params.length - 1; i++) {
            if (!params[i].checkInputValue()) {
                result = false;
                break;
            }
        }

        return result;
    }

    public String getIp() {
        String result = "";
        for (int i = 0; i < editTexts.size()-1; i++) {
            result += editTexts.get(i).getText().toString().trim()+".";
        }
        return result.substring(0,result.length()-1);
    }
    public String getPort() {
        String result =editTexts.get(editTexts.size()-1).getText().toString().trim() ;
        return result;
    }


    public void setIp(String ip){

        String [] ips=ip.split("\\.");
        for (int i=0;i<ips.length;i++){
            editTexts.get(i).setText(ips[i]) ;
        }
    }
    public void setPort(String port){
        editTexts.get(editTexts.size()-1).setText(port);
    }

    class OnDelKeyListener implements OnKeyListener {

        private AbsEditText clearEditText;
        private AbsEditText requestEditText;

        public OnDelKeyListener(AbsEditText requestEditText, AbsEditText clearEditText) {
            this.requestEditText = requestEditText;
            this.clearEditText = clearEditText;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_DOWN && clearEditText.getSelectionStart() == 0) {
                clearEditText.clearFocus();
                requestEditText.requestFocus();
                return true;
            }
            return false;
        }
    }

    public abstract int getChildCount();

    public abstract AbsEditText getAbsEditText(int length);

    public abstract String getSemicolomText();

    public abstract int getDelMaxLength();

    public abstract void applySemicolonTextViewTheme(TextView semicolonTextView);

    public abstract void applyEditTextTheme(AbsEditText absEditText);


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        // 将边框设为黑色.
        paint.setColor(android.graphics.Color.RED);

        // 画TextView的4个边.
//        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
//        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
//        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
//        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
    }


}