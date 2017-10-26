package net.offbeatpioneer.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * LabeledTextView component
 */
public class LabeledTextView extends LinearLayout {


    private TextView labelTextView;
    private TextView textView;


    public LabeledTextView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabeledTextView, 0, 0);
        String titleText = a.getString(R.styleable.LabeledTextView_ofpLabel);
        String text = a.getString(R.styleable.LabeledTextView_ofpText);
        float labelFontsize = a.getDimensionPixelSize(R.styleable.LabeledTextView_ofpLabelFontSize, 14);
        float textFontsize = a.getDimensionPixelSize(R.styleable.LabeledTextView_ofpTextFontSize, 12);
        @SuppressWarnings("ResourceAsColor")
        int labelColor = a.getColor(R.styleable.LabeledTextView_ofpLabelColor,
                Color.parseColor("#ff444444"));
        @SuppressWarnings("ResourceAsColor")
        int textColor = a.getColor(R.styleable.LabeledTextView_ofpTextColor,
                Color.parseColor("#ff444444"));
//        a.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.labeled_textview, this, true);

        labelTextView = (TextView) getChildAt(0);
        textView = (TextView) getChildAt(2);

        labelTextView.setTextColor(labelColor);
        textView.setTextColor(textColor);
        labelTextView.setText(titleText);
        textView.setText(text);

        labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelFontsize);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textFontsize);

        if (attrs != null) {
            try {
                String fontName = a.getString(R.styleable.LabeledTextView_ofpTextFont);
                String fontNameTitle = a.getString(R.styleable.LabeledTextView_ofpTitleFont);


                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                    textView.setTypeface(myTypeface);
                }
                if (fontNameTitle != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontNameTitle);
                    labelTextView.setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        a.recycle();
    }

    public void setLabelText(CharSequence text) {
        labelTextView.setText(text);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public LabeledTextView(Context context) {
        this(context, null);
    }

}
