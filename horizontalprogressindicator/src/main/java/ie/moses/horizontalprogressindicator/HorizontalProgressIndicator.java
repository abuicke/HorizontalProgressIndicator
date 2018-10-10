package ie.moses.horizontalprogressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import ie.moses.caimito.draw.MeasurementUtils;

public class HorizontalProgressIndicator extends View {

    private static final int DEFAULT_COLOR = Color.rgb(189, 117, 153);
    private static final int DEFAULT_HEIGHT_DP = 6;

    private final Paint _paint = new Paint();
    private final RectF _leftRect = new RectF();
    private final RectF _rightRect = new RectF();

    private float _defaultHeightPixels;
    private float _offset;

    public HorizontalProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalProgressIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        _defaultHeightPixels = MeasurementUtils.dpiToPixels(context, DEFAULT_HEIGHT_DP);
        TypedArray attrValues = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressIndicator);
        int colour = attrValues.getColor(R.styleable.HorizontalProgressIndicator_color, DEFAULT_COLOR);
        attrValues.recycle();
        _paint.setColor(colour);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = resolveSize((int) _defaultHeightPixels, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = (float) canvas.getWidth();
        float height = (float) canvas.getHeight();

        _leftRect.left = _offset;
        _leftRect.right = (width * 0.66F) + _offset;
        _leftRect.bottom = height;
        canvas.drawRect(_leftRect, _paint);

        float lag = width * 1.33F;
        _rightRect.left = _leftRect.left - lag;
        _rightRect.right = _leftRect.right - lag;
        _rightRect.bottom = height;
        canvas.drawRect(_rightRect, _paint);

        _offset += 15;

        if (_leftRect.left >= width) {
            _offset = 0 - (width * 0.33F);
        }

        invalidate();
    }

}
