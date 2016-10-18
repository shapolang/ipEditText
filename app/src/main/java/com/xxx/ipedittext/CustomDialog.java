package com.xxx.ipedittext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class CustomDialog extends Dialog {

	Activity mContext;

	public CustomDialog(Activity context) {
		super(context);
		this.mContext=context;
	}

	public CustomDialog(Activity context, int theme) {
		super(context, theme);
		this.mContext=context;
	}




	public void setDialogSize(Double widthPercent,Double heightPercent) {
		WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		if (widthPercent!=null){
			int width=(int) ((d.getWidth()) * widthPercent);;
			lp.width = width;
		}
		if (heightPercent!=null){
			int height=(int) ((d.getHeight()) * heightPercent);;
			lp.height =height;
		}
//		lp.width = lp.width>width?width:lp.width;
//		lp.height =lp.height> height?height:lp.height;
		dialogWindow.setAttributes(lp);
	}

	public void setDialogPosition(int x,int y) {
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
//		dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//		dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);//显示的坐标
		lp.x = x;
		lp.y = y;
		dialogWindow.setAttributes(lp);
	}


	public static class Builder {
		private Activity context;
		private String title;
		private LinearLayout llDialog;
		private int bg=-1;
		private int bottomBg=-1;
		private int titleBg=-1;
		private int titleTextColor=-1;
		private int positionBtnBg=-1;
		private int negativeBtnBg=-1;
		private View bottomLine;
		private boolean bottomLineVisiable;
		private TextView tvTitle;
		private String positiveButtonText;
		private Button positiveButton;
		private String negativeButtonText;
		private Button negativeButton;
		private LinearLayout llBottom;
		private View contentView;
		private String content;
		private TextView tvContent;
		private ImageView ivTitleRight;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;
		private OnClickListener titleRightClickListener;
		private int res=-1;

		public Builder(Activity context) {
			this.context = context;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}


		public Builder setTitleBg(int titleColor) {
			this.titleBg=titleColor;
			return this;
		}
		public Builder setDialogBg(int bg) {
			this.bg=bg;
			return this;
		}

		public Builder setDialogBottomBg(int bottomBg) {
			this.bottomBg=bottomBg;
			return this;
		}
		public Builder setPositionBtnBg(int positionBtnBg) {
			this.positionBtnBg=positionBtnBg;
			return this;
		}
		public Builder setNegativeBtnBg(int negativeBtnBg) {
			this.negativeBtnBg=negativeBtnBg;
			return this;
		}



		public Builder setTitleTextColor(int setTitleTextColor) {
			this.titleTextColor=setTitleTextColor;
			return this;
		}


		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		public Builder setContent(String content) {
			this.content = content;
			return this;
		}
		public Builder setTitleRight(int res,OnClickListener titleRightClickListener) {
			this.res = res;
			this.titleRightClickListener = titleRightClickListener;
			return this;
		}


        public void setBottomLine(boolean bottomLineVisiable ){
			this.bottomLineVisiable=bottomLineVisiable;
		}



		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// set the dialog title
			tvTitle=((TextView) layout.findViewById(R.id.title));
			llDialog=((LinearLayout) layout.findViewById(R.id.llDialog));
			tvTitle.setText(title);
			if (titleTextColor!=-1){
				tvTitle.setTextColor(titleTextColor);
			}
			if (titleBg!=-1){
				tvTitle.setBackgroundColor(titleBg);
			}
			if (bg!=-1){
				llDialog.setBackgroundResource(bg);
			}

			// set the confirm button
			if (positiveButtonText != null) {
				positiveButton=((Button) layout.findViewById(R.id.positiveButton));
				positiveButton.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
				if(positionBtnBg!=-1)
					positiveButton.setBackgroundResource(positionBtnBg);
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				negativeButton=((Button) layout.findViewById(R.id.negativeButton));
				negativeButton.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
				if(negativeBtnBg!=-1)
					negativeButton.setBackgroundResource(negativeBtnBg);
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (titleRightClickListener!=null){
				ivTitleRight=(ImageView)layout.findViewById(R.id.ivTitleRight);
				ivTitleRight.setVisibility(View.VISIBLE);
				ivTitleRight.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						titleRightClickListener.onClick(dialog, DialogInterface.BUTTON1);
					}
				});
			}
			if (res!=-1){
				ivTitleRight.setBackgroundResource(res);
			}
			llBottom=(LinearLayout)layout.findViewById(R.id.llBottom);
			if (bottomBg!=-1){
				llBottom.setBackgroundResource(bottomBg);
			}
			bottomLine=layout.findViewById(R.id.bottomLine);
			if (bottomLineVisiable){
				bottomLine.setVisibility(View.VISIBLE);
			}else {
				bottomLine.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(content)){
				tvContent=(TextView)layout.findViewById(R.id.tvContent);
				tvContent.setVisibility(View.VISIBLE);
				tvContent.setText(content);
			}
			if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
			}else{
				layout.findViewById(R.id.content).setVisibility(View.GONE);
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}
}
