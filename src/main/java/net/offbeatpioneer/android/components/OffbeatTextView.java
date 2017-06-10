package net.offbeatpioneer.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Dominik Grzelak
 * @since 06.10.2016.
 */
public class OffbeatTextView extends TextView {
    private static final String TAG = OffbeatTextView.class.getSimpleName();

    Handler completeCallbackHandler;
    private Handler handler = new Handler();
    private CharSequence text;
    private int index;
    private long delay = 250;
    private boolean loopTyperwriter = false;

    public OffbeatTextView(Context context) {
        super(context);
    }

    public OffbeatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OffbeatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public OffbeatTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OffbeatTextView);

            this.delay = a.getInteger(R.styleable.OffbeatTextView_characterDelay, (int) this.delay);
            this.loopTyperwriter = a.getBoolean(R.styleable.OffbeatTextView_loopTypewriter, this.loopTyperwriter);

            String fontName = a.getString(R.styleable.OffbeatTextView_font);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }

    private Runnable characterAdder = new Runnable() {

        @Override
        public void run() {
            setText(text.subSequence(0, index++));
            if (index <= text.length()) {
                handler.postDelayed(characterAdder, delay);
            } else {
                if (null != completeCallbackHandler) {
                    completeCallbackHandler.sendMessage(new Message());
                    if (loopTyperwriter) {
                        index = 0;
                        animateText();
                    }
                } else
                    Log.d(TAG, "Complete callback handler not set. Cannot send message.");
            }
        }
    };

    /**
     *
     * @param fontName filename path of the font file in the assets folder
     */
    public void setFont(String fontName) {
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
        setTypeface(myTypeface);
    }

    public void animateText() {
        this.animateText(getText());
    }

    public void animateText(CharSequence txt) {
        text = txt;
        index = 0;

        setText("");
        handler.removeCallbacks(characterAdder);
        handler.postDelayed(characterAdder, delay);
    }

    /**
     * Delay in milliseconds
     *
     * @param delay milliseconds
     */
    public void setCharacterDelay(long delay) {
        this.delay = delay;
    }

    public Handler getCompleteCallbackHandler() {
        return completeCallbackHandler;
    }

    public void setCompleteCallbackHandler(Handler completeCallbackHandler) {
        this.completeCallbackHandler = completeCallbackHandler;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isLoopTyperwriter() {
        return loopTyperwriter;
    }

    public void setLoopTyperwriter(boolean loopTyperwriter) {
        this.loopTyperwriter = loopTyperwriter;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
