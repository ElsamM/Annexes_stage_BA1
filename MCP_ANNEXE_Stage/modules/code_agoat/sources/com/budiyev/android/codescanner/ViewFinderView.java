package com.budiyev.android.codescanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
final class ViewFinderView extends View {
    private int mFrameCornersRadius;
    private int mFrameCornersSize;
    private final Paint mFramePaint;
    private float mFrameRatioHeight;
    private float mFrameRatioWidth;
    private Rect mFrameRect;
    private float mFrameSize;
    private float mFrameVerticalBias;
    private final Paint mMaskPaint;
    private final Path mPath;

    public ViewFinderView(Context context) {
        super(context);
        this.mFrameCornersSize = 0;
        this.mFrameCornersRadius = 0;
        this.mFrameRatioWidth = 1.0f;
        this.mFrameRatioHeight = 1.0f;
        this.mFrameSize = 0.75f;
        this.mFrameVerticalBias = 0.5f;
        this.mMaskPaint = new Paint(1);
        this.mMaskPaint.setStyle(Paint.Style.FILL);
        this.mFramePaint = new Paint(1);
        this.mFramePaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        this.mPath = path;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        invalidateFrameRect(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        invalidateFrameRect(right - left, bottom - top);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Rect frame = this.mFrameRect;
        if (frame == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float top = frame.getTop();
        float left = frame.getLeft();
        float right = frame.getRight();
        float bottom = frame.getBottom();
        float frameCornersSize = this.mFrameCornersSize;
        float frameCornersRadius = this.mFrameCornersRadius;
        Path path = this.mPath;
        if (frameCornersRadius > 0.0f) {
            float normalizedRadius = Math.min(frameCornersRadius, Math.max(frameCornersSize - 1.0f, 0.0f));
            path.reset();
            path.moveTo(left, top + normalizedRadius);
            path.quadTo(left, top, left + normalizedRadius, top);
            path.lineTo(right - normalizedRadius, top);
            path.quadTo(right, top, right, top + normalizedRadius);
            path.lineTo(right, bottom - normalizedRadius);
            path.quadTo(right, bottom, right - normalizedRadius, bottom);
            path.lineTo(left + normalizedRadius, bottom);
            path.quadTo(left, bottom, left, bottom - normalizedRadius);
            path.lineTo(left, top + normalizedRadius);
            path.moveTo(0.0f, 0.0f);
            path.lineTo(width, 0.0f);
            path.lineTo(width, height);
            path.lineTo(0.0f, height);
            path.lineTo(0.0f, 0.0f);
            canvas.drawPath(path, this.mMaskPaint);
            path.reset();
            path.moveTo(left, top + frameCornersSize);
            path.lineTo(left, top + normalizedRadius);
            path.quadTo(left, top, left + normalizedRadius, top);
            path.lineTo(left + frameCornersSize, top);
            path.moveTo(right - frameCornersSize, top);
            path.lineTo(right - normalizedRadius, top);
            path.quadTo(right, top, right, top + normalizedRadius);
            path.lineTo(right, top + frameCornersSize);
            path.moveTo(right, bottom - frameCornersSize);
            path.lineTo(right, bottom - normalizedRadius);
            path.quadTo(right, bottom, right - normalizedRadius, bottom);
            path.lineTo(right - frameCornersSize, bottom);
            path.moveTo(left + frameCornersSize, bottom);
            path.lineTo(left + normalizedRadius, bottom);
            path.quadTo(left, bottom, left, bottom - normalizedRadius);
            path.lineTo(left, bottom - frameCornersSize);
            canvas.drawPath(path, this.mFramePaint);
            return;
        }
        path.reset();
        path.moveTo(left, top);
        path.lineTo(right, top);
        path.lineTo(right, bottom);
        path.lineTo(left, bottom);
        path.lineTo(left, top);
        path.moveTo(0.0f, 0.0f);
        path.lineTo(width, 0.0f);
        path.lineTo(width, height);
        path.lineTo(0.0f, height);
        path.lineTo(0.0f, 0.0f);
        canvas.drawPath(path, this.mMaskPaint);
        path.reset();
        path.moveTo(left, top + frameCornersSize);
        path.lineTo(left, top);
        path.lineTo(left + frameCornersSize, top);
        path.moveTo(right - frameCornersSize, top);
        path.lineTo(right, top);
        path.lineTo(right, top + frameCornersSize);
        path.moveTo(right, bottom - frameCornersSize);
        path.lineTo(right, bottom);
        path.lineTo(right - frameCornersSize, bottom);
        path.moveTo(left + frameCornersSize, bottom);
        path.lineTo(left, bottom);
        path.lineTo(left, bottom - frameCornersSize);
        canvas.drawPath(path, this.mFramePaint);
    }

    Rect getFrameRect() {
        return this.mFrameRect;
    }

    void setFrameAspectRatio(float ratioWidth, float ratioHeight) {
        this.mFrameRatioWidth = ratioWidth;
        this.mFrameRatioHeight = ratioHeight;
        invalidateFrameRect();
        if (isLaidOut()) {
            invalidate();
        }
    }

    float getFrameAspectRatioWidth() {
        return this.mFrameRatioWidth;
    }

    void setFrameAspectRatioWidth(float ratioWidth) {
        this.mFrameRatioWidth = ratioWidth;
        invalidateFrameRect();
        if (isLaidOut()) {
            invalidate();
        }
    }

    float getFrameAspectRatioHeight() {
        return this.mFrameRatioHeight;
    }

    void setFrameAspectRatioHeight(float ratioHeight) {
        this.mFrameRatioHeight = ratioHeight;
        invalidateFrameRect();
        if (isLaidOut()) {
            invalidate();
        }
    }

    int getMaskColor() {
        return this.mMaskPaint.getColor();
    }

    void setMaskColor(int color) {
        this.mMaskPaint.setColor(color);
        if (isLaidOut()) {
            invalidate();
        }
    }

    int getFrameColor() {
        return this.mFramePaint.getColor();
    }

    void setFrameColor(int color) {
        this.mFramePaint.setColor(color);
        if (isLaidOut()) {
            invalidate();
        }
    }

    int getFrameThickness() {
        return (int) this.mFramePaint.getStrokeWidth();
    }

    void setFrameThickness(int thickness) {
        this.mFramePaint.setStrokeWidth(thickness);
        if (isLaidOut()) {
            invalidate();
        }
    }

    int getFrameCornersSize() {
        return this.mFrameCornersSize;
    }

    void setFrameCornersSize(int size) {
        this.mFrameCornersSize = size;
        if (isLaidOut()) {
            invalidate();
        }
    }

    int getFrameCornersRadius() {
        return this.mFrameCornersRadius;
    }

    void setFrameCornersRadius(int radius) {
        this.mFrameCornersRadius = radius;
        if (isLaidOut()) {
            invalidate();
        }
    }

    public float getFrameSize() {
        return this.mFrameSize;
    }

    void setFrameSize(float size) {
        this.mFrameSize = size;
        invalidateFrameRect();
        if (isLaidOut()) {
            invalidate();
        }
    }

    public float getFrameVerticalBias() {
        return this.mFrameVerticalBias;
    }

    public void setFrameVerticalBias(float bias) {
        this.mFrameVerticalBias = bias;
        invalidateFrameRect();
        if (isLaidOut()) {
            invalidate();
        }
    }

    private void invalidateFrameRect() {
        invalidateFrameRect(getWidth(), getHeight());
    }

    private void invalidateFrameRect(int width, int height) {
        int frameHeight;
        int frameWidth;
        if (width > 0 && height > 0) {
            float viewAR = width / height;
            float frameAR = this.mFrameRatioWidth / this.mFrameRatioHeight;
            float frameSize = this.mFrameSize;
            if (viewAR <= frameAR) {
                frameWidth = Math.round(width * frameSize);
                frameHeight = Math.round(frameWidth / frameAR);
            } else {
                frameHeight = Math.round(height * frameSize);
                frameWidth = Math.round(frameHeight * frameAR);
            }
            int frameLeft = (width - frameWidth) / 2;
            int frameTop = Math.round((height - frameHeight) * this.mFrameVerticalBias);
            this.mFrameRect = new Rect(frameLeft, frameTop, frameLeft + frameWidth, frameTop + frameHeight);
        }
    }
}
