package com.jiang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiang.common.R;

/**
 * Created by jiang on 2017/3/1.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context mContext;
        private CustomDialog dialog;
        private CharSequence strTitle;
        private CharSequence strSubtitle;
        private CharSequence strMessage;
        private CharSequence strPositive;
        private CharSequence strNegative;
        private View.OnClickListener positiveListener;
        private View.OnClickListener negativeListener;
        private int themeId;

        private Boolean canceledOnTouchOutside = true;

        private TextView tvTitle;
        private TextView tvSubtitle;
        private TextView tvMessage;
        private Button btnPositive;
        private Button btnNegative;
        private LinearLayout layoutContent;
        private View viewContent;

        public Builder(Context context) {
            this(context, 0);
        }

        public Builder(Context context, int themeResId) {
            this.mContext = context;
            this.themeId = themeResId;
            dialog = new CustomDialog(mContext, themeId);
            dialog.setContentView(R.layout.dialog_custom);
            tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
            tvSubtitle = (TextView) dialog.findViewById(R.id.tv_dialog_subtitle);
            tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_message);
            btnPositive = (Button) dialog.findViewById(R.id.btn_dialog_positive);
            btnNegative = (Button) dialog.findViewById(R.id.btn_dialog_negative);
            layoutContent = (LinearLayout) dialog.findViewById(R.id.ly_dialog_content);
        }

        public Builder setCanceledOnTouchOutside(boolean can) {
            canceledOnTouchOutside = can;
            return this;
        }

        public Builder setTitle(@StringRes int resId) {
            setTitle(mContext.getText(resId));
            return this;
        }

        public Builder setTitle(CharSequence title) {
            if (!TextUtils.isEmpty(title)) {
                strTitle = title;
            }
            return this;
        }

        public Builder setSubtitle(@StringRes int resId) {
            setSubtitle(mContext.getText(resId));
            return this;
        }

        public Builder setSubtitle(CharSequence title) {
            if (!TextUtils.isEmpty(title)) {
                strSubtitle = title;
            }
            return this;
        }


        public Builder setMessage(@StringRes int resId) {
            setMessage(mContext.getText(resId));
            return this;
        }

        public Builder setMessage(CharSequence title) {
            if (!TextUtils.isEmpty(title)) {
                strMessage = title;
            }
            return this;
        }

        public Builder setPositiveButton(int textId, View.OnClickListener listener) {
            setPositiveButton(mContext.getText(textId), listener);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, View.OnClickListener listener) {
            strPositive = text;
            positiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, View.OnClickListener listener) {
            setNegativeButton(mContext.getText(textId), listener);
            return this;
        }

        public Builder setNegativeButton(CharSequence text, View.OnClickListener listener) {
            strNegative = text;
            negativeListener = listener;
            return this;
        }

        public Builder setView(View view) {
            viewContent = view;
            return this;
        }

        public CustomDialog create() {

            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            layoutContent.removeAllViews();
            if (!TextUtils.isEmpty(strMessage)) {
                layoutContent.addView(tvMessage);
                ViewGroup.LayoutParams params = tvMessage.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                if (viewContent != null) {
                    layoutContent.addView(viewContent);
                    ViewGroup.LayoutParams params = viewContent.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
            }
            if (TextUtils.isEmpty(strTitle)) {
                tvTitle.setVisibility(View.GONE);
            } else
                tvTitle.setText(strTitle);
            if (TextUtils.isEmpty(strSubtitle))
                tvSubtitle.setVisibility(View.GONE);
            else
                tvSubtitle.setText(strSubtitle);
            if (TextUtils.isEmpty(strMessage))
                tvMessage.setVisibility(View.GONE);
            else
                tvMessage.setText(strMessage);

            if (!TextUtils.isEmpty(strPositive)) {
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setText(strPositive);
                btnPositive.setOnClickListener(positiveListener);
            } else {
                btnPositive.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(strNegative)) {
                btnNegative.setVisibility(View.VISIBLE);
                btnNegative.setText(strNegative);
                btnNegative.setOnClickListener(negativeListener);
            } else {
                btnNegative.setVisibility(View.GONE);
            }

            return dialog;
        }

        public void dismiss() {
            dialog.dismiss();
        }

        public void show() {
            dialog.show();
        }
    }
}
