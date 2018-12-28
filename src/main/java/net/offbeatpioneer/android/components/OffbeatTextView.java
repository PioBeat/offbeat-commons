package net.offbeatpioneer.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Dominik Grzelak
 * @since 06.10.2016.
 */
public class OffbeatTextView extends AppCompatTextView {
    private static final String TAG = OffbeatTextView.class.getSimpleName();

    Handler completeCallbackHandler;
    private Handler handler = new Handler();
    private CharSequence text;
    private int index;
    private long delay = 250;
    private boolean loopTyperwriter = false;
    private Runnable characterAdder;

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

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OffbeatTextView);

            this.delay = a.getInteger(R.styleable.OffbeatTextView_ofpCharacterDelay, (int) this.delay);
            setLoopTyperwriter(a.getBoolean(R.styleable.OffbeatTextView_ofpLoopTypewriter, this.loopTyperwriter));

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

    private void initCharacterAdder() {
        if (characterAdder != null) return;
        characterAdder = new Runnable() {

            @Override
            public void run() {
                setText(text.subSequence(0, index++));
                if (index <= text.length()) {
                    handler.postDelayed(characterAdder, delay);
                } else {
                    if (loopTyperwriter) {
                        index = 0;
                        animateText();
                    }
                    if (null != completeCallbackHandler) {
                        completeCallbackHandler.sendEmptyMessage(0);
                    } else
                        Log.d(TAG, "Complete callback handler not set. Cannot send message.");
                }
            }
        };
    }

    /**
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
        initCharacterAdder();
        handler.removeCallbacks(characterAdder);
        handler.postDelayed(characterAdder, delay);
    }

    /**
     * Delay in milliseconds
     *
     * @param delay milliseconds
     */
    @SuppressWarnings("unused")
    public void setCharacterDelay(long delay) {
        this.delay = delay;
    }

    @SuppressWarnings("unused")
    public Handler getCompleteCallbackHandler() {
        return completeCallbackHandler;
    }

    @SuppressWarnings("unused")
    public void setCompleteCallbackHandler(Handler completeCallbackHandler) {
        this.completeCallbackHandler = completeCallbackHandler;
    }

    @SuppressWarnings("unused")
    public long getDelay() {
        return delay;
    }

    @SuppressWarnings("unused")
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @SuppressWarnings("unused")
    public boolean isLoopTyperwriter() {
        return loopTyperwriter;
    }

    public void setLoopTyperwriter(boolean loopTyperwriter) {
        this.loopTyperwriter = loopTyperwriter;
        if (this.loopTyperwriter) {
            initCharacterAdder();
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
