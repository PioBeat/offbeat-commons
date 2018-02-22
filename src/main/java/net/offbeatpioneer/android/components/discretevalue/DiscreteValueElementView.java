package net.offbeatpioneer.android.components.discretevalue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dome on 20.02.2018.
 */

public class DiscreteValueElementView extends View {
    Path path;

    public DiscreteValueElementView(Context context) {
        super(context);
    }

    public DiscreteValueElementView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscreteValueElementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        float cornerRadius = 54;
        this.path = new Path();
        this.path.addRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, Path.Direction.CW);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.path != null) {
            canvas.clipPath(this.path);
        }
        super.dispatchDraw(canvas);
    }
}
