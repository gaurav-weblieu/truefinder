package com.weblieu.findtrue.utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class TextDrawable extends Drawable implements TextWatcher {
    private WeakReference<TextView> ref;
    private String mText;
    private Paint mPaint;
    private Rect mHeightBounds;
    private boolean mBindToViewPaint = false;
    private float mPrevTextSize = 0;
    private boolean mInitFitText = false;
    private boolean mFitTextEnabled = false;


    public TextDrawable(Paint paint, String s) {
        mText = s;
        mPaint = new Paint(paint);
        mHeightBounds = new Rect();
        init();
    }

    public TextDrawable(TextView tv, String initialText, boolean bindToViewsText, boolean bindToViewsPaint) {
        this(tv.getPaint(), initialText);
        ref = new WeakReference<>(tv);
        if (bindToViewsText || bindToViewsPaint) {
            if (bindToViewsText) {
                tv.addTextChangedListener(this);
            }
            mBindToViewPaint = bindToViewsPaint;
        }
    }

    public TextDrawable(TextView tv, boolean bindToViewsText, boolean bindToViewsPaint) {
        this(tv, tv.getText().toString(), false, false);
    }

    public TextDrawable(TextView tv) {
        this(tv, false, false);
    }

    public TextDrawable(TextView tv, String s) {
        this(tv, s, false, false);
    }
    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mBindToViewPaint && ref.get() != null) {
            Paint p = ref.get().getPaint();
            canvas.drawText(mText, 0, getBounds().height(), p);
        } else {
            if (mInitFitText) {
                fitTextAndInit();
            }
            canvas.drawText(mText, 0, getBounds().height(), mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else if (alpha == 255) {
            return PixelFormat.OPAQUE;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }

    private void init() {
        Rect bounds = getBounds();
        //We want to use some character to determine the max height of the text.
        //Otherwise if we draw something like "..." they will appear centered
        //Here I'm just going to use the entire alphabet to determine max height.
        mPaint.getTextBounds("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", 0, 1, mHeightBounds);
        //This doesn't account for leading or training white spaces.
        //mPaint.getTextBounds(mText, 0, mText.length(), bounds);
        float width = mPaint.measureText(mText);
        bounds.top = mHeightBounds.top;
        bounds.bottom = mHeightBounds.bottom;
        bounds.right = (int) width;
        bounds.left = 0;
        setBounds(bounds);
    }


    public void setPaint(Paint paint) {
        mPaint = new Paint(paint);
        //Since this can change the font used, we need to recalculate bounds.
        if (mFitTextEnabled && !mInitFitText) {
            fitTextAndInit();
        } else {
            init();
        }
        invalidateSelf();
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setText(String text) {
        mText = text;
        //Since this can change the bounds of the text, we need to recalculate.
        if (mFitTextEnabled && !mInitFitText) {
            fitTextAndInit();
        } else {
            init();
        }
        invalidateSelf();
    }

    public String getText() {
        return mText;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setText(s.toString());
    }

    public void setFillText(boolean fitText) {
        mFitTextEnabled = fitText;
        if (fitText) {
            mPrevTextSize = mPaint.getTextSize();
            if (ref.get() != null) {
                if (ref.get().getWidth() > 0) {
                    fitTextAndInit();
                } else {
                    mInitFitText = true;
                }
            }
        } else {
            if (mPrevTextSize > 0) {
                mPaint.setTextSize(mPrevTextSize);
            }
            init();
        }
    }

    private void fitTextAndInit() {
        float fitWidth = ref.get().getWidth();
        float textWidth = mPaint.measureText(mText);
        float multi = fitWidth / textWidth;
        mPaint.setTextSize(mPaint.getTextSize() * multi);
        mInitFitText = false;
        init();
    }
}