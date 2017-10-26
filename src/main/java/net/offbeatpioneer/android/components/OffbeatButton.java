package net.offbeatpioneer.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * @author Dominik Grzelak
 * @since 10.10.2016.
 */
public class OffbeatButton extends AppCompatButton {

    public OffbeatButton(Context context) {
        super(context);
    }

    public OffbeatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OffbeatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OffbeatTextView);
            String fontPath = a.getString(R.styleable.OffbeatTextView_ofpFont);

            try {
                if (fontPath != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
