package com.budiyev.android.codescanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class CodeScannerView extends ViewGroup {
    private static final int DEFAULT_AUTO_FOCUS_BUTTON_COLOR = -1;
    private static final int DEFAULT_AUTO_FOCUS_BUTTON_VISIBILITY = 0;
    private static final boolean DEFAULT_AUTO_FOCUS_BUTTON_VISIBLE = true;
    private static final float DEFAULT_BUTTON_PADDING_DP = 16.0f;
    private static final int DEFAULT_FLASH_BUTTON_COLOR = -1;
    private static final int DEFAULT_FLASH_BUTTON_VISIBILITY = 0;
    private static final boolean DEFAULT_FLASH_BUTTON_VISIBLE = true;
    private static final float DEFAULT_FRAME_ASPECT_RATIO_HEIGHT = 1.0f;
    private static final float DEFAULT_FRAME_ASPECT_RATIO_WIDTH = 1.0f;
    private static final int DEFAULT_FRAME_COLOR = -1;
    private static final float DEFAULT_FRAME_CORNERS_RADIUS_DP = 0.0f;
    private static final float DEFAULT_FRAME_CORNER_SIZE_DP = 50.0f;
    private static final float DEFAULT_FRAME_SIZE = 0.75f;
    private static final float DEFAULT_FRAME_THICKNESS_DP = 2.0f;
    private static final float DEFAULT_FRAME_VERTICAL_BIAS = 0.5f;
    private static final int DEFAULT_MASK_COLOR = 1996488704;
    private static final float FOCUS_AREA_SIZE_DP = 20.0f;
    private static final int HINT_VIEW_INDEX = 4;
    private static final int MAX_CHILD_COUNT = 5;
    private ImageView mAutoFocusButton;
    private int mAutoFocusButtonColor;
    private Drawable mAutoFocusButtonOffIcon;
    private Drawable mAutoFocusButtonOnIcon;
    private int mAutoFocusButtonPaddingHorizontal;
    private int mAutoFocusButtonPaddingVertical;
    private ButtonPosition mAutoFocusButtonPosition;
    private CodeScanner mCodeScanner;
    private ImageView mFlashButton;
    private int mFlashButtonColor;
    private Drawable mFlashButtonOffIcon;
    private Drawable mFlashButtonOnIcon;
    private int mFlashButtonPaddingHorizontal;
    private int mFlashButtonPaddingVertical;
    private ButtonPosition mFlashButtonPosition;
    private int mFocusAreaSize;
    private Point mPreviewSize;
    private SurfaceView mPreviewView;
    private SizeListener mSizeListener;
    private ViewFinderView mViewFinderView;
    private static final ButtonPosition DEFAULT_AUTO_FOCUS_BUTTON_POSITION = ButtonPosition.TOP_START;
    private static final ButtonPosition DEFAULT_FLASH_BUTTON_POSITION = ButtonPosition.TOP_END;

    interface SizeListener {
        void onSizeChanged(int i, int i2);
    }

    public CodeScannerView(Context context) throws Throwable {
        super(context);
        initialize(context, null, 0, 0);
    }

    public CodeScannerView(Context context, AttributeSet attrs) throws Throwable {
        super(context, attrs);
        initialize(context, attrs, 0, 0);
    }

    public CodeScannerView(Context context, AttributeSet attrs, int defStyleAttr) throws Throwable {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr, 0);
    }

    public CodeScannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws Throwable {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) throws Throwable {
        this.mPreviewView = new SurfaceView(context);
        this.mViewFinderView = new ViewFinderView(context);
        float density = context.getResources().getDisplayMetrics().density;
        int defaultButtonPadding = Math.round(DEFAULT_BUTTON_PADDING_DP * density);
        this.mFocusAreaSize = Math.round(FOCUS_AREA_SIZE_DP * density);
        this.mAutoFocusButton = new ImageView(context);
        this.mAutoFocusButton.setScaleType(ImageView.ScaleType.CENTER);
        this.mAutoFocusButton.setOnClickListener(new AutoFocusClickListener());
        this.mFlashButton = new ImageView(context);
        this.mFlashButton.setScaleType(ImageView.ScaleType.CENTER);
        this.mFlashButton.setOnClickListener(new FlashClickListener());
        if (attrs == null) {
            this.mViewFinderView.setFrameAspectRatio(1.0f, 1.0f);
            this.mViewFinderView.setMaskColor(DEFAULT_MASK_COLOR);
            this.mViewFinderView.setFrameColor(-1);
            this.mViewFinderView.setFrameThickness(Math.round(DEFAULT_FRAME_THICKNESS_DP * density));
            this.mViewFinderView.setFrameCornersSize(Math.round(50.0f * density));
            this.mViewFinderView.setFrameCornersRadius(Math.round(0.0f * density));
            this.mViewFinderView.setFrameSize(0.75f);
            this.mViewFinderView.setFrameVerticalBias(0.5f);
            this.mAutoFocusButton.setColorFilter(-1);
            this.mFlashButton.setColorFilter(-1);
            this.mAutoFocusButton.setVisibility(0);
            this.mAutoFocusButtonPosition = DEFAULT_AUTO_FOCUS_BUTTON_POSITION;
            this.mFlashButton.setVisibility(0);
            this.mFlashButtonPosition = DEFAULT_FLASH_BUTTON_POSITION;
            this.mAutoFocusButtonPaddingHorizontal = defaultButtonPadding;
            this.mAutoFocusButtonPaddingVertical = defaultButtonPadding;
            this.mFlashButtonPaddingHorizontal = defaultButtonPadding;
            this.mFlashButtonPaddingVertical = defaultButtonPadding;
            this.mAutoFocusButton.setPadding(defaultButtonPadding, defaultButtonPadding, defaultButtonPadding, defaultButtonPadding);
            this.mFlashButton.setPadding(defaultButtonPadding, defaultButtonPadding, defaultButtonPadding, defaultButtonPadding);
            this.mAutoFocusButtonOnIcon = Utils.getDrawable(context, R.drawable.ic_code_scanner_auto_focus_on);
            this.mAutoFocusButtonOffIcon = Utils.getDrawable(context, R.drawable.ic_code_scanner_auto_focus_off);
            this.mFlashButtonOnIcon = Utils.getDrawable(context, R.drawable.ic_code_scanner_flash_on);
            this.mFlashButtonOffIcon = Utils.getDrawable(context, R.drawable.ic_code_scanner_flash_off);
        } else {
            TypedArray a = null;
            try {
                try {
                    a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CodeScannerView, defStyleAttr, defStyleRes);
                    setMaskColor(a.getColor(R.styleable.CodeScannerView_maskColor, DEFAULT_MASK_COLOR));
                    setFrameColor(a.getColor(R.styleable.CodeScannerView_frameColor, -1));
                    setFrameThickness(a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameThickness, Math.round(density * DEFAULT_FRAME_THICKNESS_DP)));
                    setFrameCornersSize(a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameCornersSize, Math.round(density * 50.0f)));
                    setFrameCornersRadius(a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameCornersRadius, Math.round(density * 0.0f)));
                    setFrameAspectRatio(a.getFloat(R.styleable.CodeScannerView_frameAspectRatioWidth, 1.0f), a.getFloat(R.styleable.CodeScannerView_frameAspectRatioHeight, 1.0f));
                    setFrameSize(a.getFloat(R.styleable.CodeScannerView_frameSize, 0.75f));
                    setFrameVerticalBias(a.getFloat(R.styleable.CodeScannerView_frameVerticalBias, 0.5f));
                    setAutoFocusButtonVisible(a.getBoolean(R.styleable.CodeScannerView_autoFocusButtonVisible, true));
                    setAutoFocusButtonColor(a.getColor(R.styleable.CodeScannerView_autoFocusButtonColor, -1));
                    setAutoFocusButtonPosition(buttonPositionFromAttr(a.getInt(R.styleable.CodeScannerView_autoFocusButtonPosition, indexOfButtonPosition(DEFAULT_AUTO_FOCUS_BUTTON_POSITION))));
                    setAutoFocusButtonPaddingHorizontal(a.getDimensionPixelOffset(R.styleable.CodeScannerView_autoFocusButtonPaddingHorizontal, defaultButtonPadding));
                    setAutoFocusButtonPaddingVertical(a.getDimensionPixelOffset(R.styleable.CodeScannerView_autoFocusButtonPaddingVertical, defaultButtonPadding));
                    Drawable autoFocusButtonOnIcon = a.getDrawable(R.styleable.CodeScannerView_autoFocusButtonOnIcon);
                    setAutoFocusButtonOnIcon(autoFocusButtonOnIcon != null ? autoFocusButtonOnIcon : Utils.getDrawable(context, R.drawable.ic_code_scanner_auto_focus_on));
                    Drawable autoFocusButtonOffIcon = a.getDrawable(R.styleable.CodeScannerView_autoFocusButtonOffIcon);
                    setAutoFocusButtonOffIcon(autoFocusButtonOffIcon != null ? autoFocusButtonOffIcon : Utils.getDrawable(context, R.drawable.ic_code_scanner_auto_focus_off));
                    setFlashButtonVisible(a.getBoolean(R.styleable.CodeScannerView_flashButtonVisible, true));
                    setFlashButtonColor(a.getColor(R.styleable.CodeScannerView_flashButtonColor, -1));
                    setFlashButtonPosition(buttonPositionFromAttr(a.getInt(R.styleable.CodeScannerView_flashButtonPosition, indexOfButtonPosition(DEFAULT_FLASH_BUTTON_POSITION))));
                    setFlashButtonPaddingHorizontal(a.getDimensionPixelOffset(R.styleable.CodeScannerView_flashButtonPaddingHorizontal, defaultButtonPadding));
                    setFlashButtonPaddingVertical(a.getDimensionPixelOffset(R.styleable.CodeScannerView_flashButtonPaddingVertical, defaultButtonPadding));
                    Drawable flashButtonOnIcon = a.getDrawable(R.styleable.CodeScannerView_flashButtonOnIcon);
                    setFlashButtonOnIcon(flashButtonOnIcon != null ? flashButtonOnIcon : Utils.getDrawable(context, R.drawable.ic_code_scanner_flash_on));
                    Drawable flashButtonOffIcon = a.getDrawable(R.styleable.CodeScannerView_flashButtonOffIcon);
                    setFlashButtonOffIcon(flashButtonOffIcon != null ? flashButtonOffIcon : Utils.getDrawable(context, R.drawable.ic_code_scanner_flash_off));
                    if (a != null) {
                        a.recycle();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (a != null) {
                        a.recycle();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (isInEditMode()) {
            setAutoFocusEnabled(true);
            setFlashEnabled(true);
        }
        addView(this.mPreviewView, new LayoutParams(-1, -1));
        addView(this.mViewFinderView, new LayoutParams(-1, -1));
        addView(this.mAutoFocusButton, new LayoutParams(-2, -2));
        addView(this.mFlashButton, new LayoutParams(-2, -2));
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        if (childCount > 5) {
            throw new IllegalStateException("CodeScannerView can have zero or one child");
        }
        measureChildWithMargins(this.mPreviewView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(this.mViewFinderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(this.mAutoFocusButton, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(this.mFlashButton, widthMeasureSpec, 0, heightMeasureSpec, 0);
        if (childCount == 5) {
            Rect frameRect = this.mViewFinderView.getFrameRect();
            measureChildWithMargins(getChildAt(4), widthMeasureSpec, 0, heightMeasureSpec, frameRect != null ? frameRect.getBottom() : 0);
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        if (childCount > 5) {
            throw new IllegalStateException("CodeScannerView can have zero or one child");
        }
        int width = right - left;
        int height = bottom - top;
        Point previewSize = this.mPreviewSize;
        if (previewSize == null) {
            this.mPreviewView.layout(0, 0, width, height);
        } else {
            int frameLeft = 0;
            int frameTop = 0;
            int frameRight = width;
            int frameBottom = height;
            int previewWidth = previewSize.getX();
            if (previewWidth > width) {
                int d = (previewWidth - width) / 2;
                frameLeft = 0 - d;
                frameRight += d;
            }
            int previewHeight = previewSize.getY();
            if (previewHeight > height) {
                int d2 = (previewHeight - height) / 2;
                frameTop = 0 - d2;
                frameBottom += d2;
            }
            this.mPreviewView.layout(frameLeft, frameTop, frameRight, frameBottom);
        }
        this.mViewFinderView.layout(0, 0, width, height);
        layoutButton(this.mAutoFocusButton, this.mAutoFocusButtonPosition, width, height);
        layoutButton(this.mFlashButton, this.mFlashButtonPosition, width, height);
        if (childCount == 5) {
            Rect frameRect = this.mViewFinderView.getFrameRect();
            int viewTop = frameRect != null ? frameRect.getBottom() : 0;
            View hintView = getChildAt(4);
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            if (hintView.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) hintView.getLayoutParams();
                int childLeft = lp.leftMargin + paddingLeft;
                int childTop = lp.topMargin + paddingTop + viewTop;
                hintView.layout(childLeft, childTop, hintView.getMeasuredWidth() + childLeft, hintView.getMeasuredHeight() + childTop);
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        SizeListener listener = this.mSizeListener;
        if (listener != null) {
            listener.onSizeChanged(width, height);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        CodeScanner codeScanner = this.mCodeScanner;
        Rect frameRect = getFrameRect();
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (codeScanner != null && frameRect != null && codeScanner.isAutoFocusSupportedOrUnknown() && codeScanner.isTouchFocusEnabled() && event.getAction() == 0 && frameRect.isPointInside(x, y)) {
            int areaSize = this.mFocusAreaSize;
            codeScanner.performTouchFocus(new Rect(x - areaSize, y - areaSize, x + areaSize, y + areaSize).fitIn(frameRect));
        }
        return super.onTouchEvent(event);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public int getMaskColor() {
        return this.mViewFinderView.getMaskColor();
    }

    public void setMaskColor(int color) {
        this.mViewFinderView.setMaskColor(color);
    }

    public int getFrameColor() {
        return this.mViewFinderView.getFrameColor();
    }

    public void setFrameColor(int color) {
        this.mViewFinderView.setFrameColor(color);
    }

    public int getFrameThickness() {
        return this.mViewFinderView.getFrameThickness();
    }

    public void setFrameThickness(int thickness) {
        if (thickness < 0) {
            throw new IllegalArgumentException("Frame thickness can't be negative");
        }
        this.mViewFinderView.setFrameThickness(thickness);
    }

    public int getFrameCornersSize() {
        return this.mViewFinderView.getFrameCornersSize();
    }

    public void setFrameCornersSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Frame corners size can't be negative");
        }
        this.mViewFinderView.setFrameCornersSize(size);
    }

    public int getFrameCornersRadius() {
        return this.mViewFinderView.getFrameCornersRadius();
    }

    public void setFrameCornersRadius(int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Frame corners radius can't be negative");
        }
        this.mViewFinderView.setFrameCornersRadius(radius);
    }

    public float getFrameSize() {
        return this.mViewFinderView.getFrameSize();
    }

    public void setFrameSize(float size) {
        if (size < 0.1d || size > 1.0f) {
            throw new IllegalArgumentException("Max frame size value should be between 0.1 and 1, inclusive");
        }
        this.mViewFinderView.setFrameSize(size);
    }

    public float getFrameVerticalBias() {
        return this.mViewFinderView.getFrameVerticalBias();
    }

    public void setFrameVerticalBias(float bias) {
        if (bias < 0.0f || bias > 1.0f) {
            throw new IllegalArgumentException("Max frame size value should be between 0 and 1, inclusive");
        }
        this.mViewFinderView.setFrameVerticalBias(bias);
    }

    public float getFrameAspectRatioWidth() {
        return this.mViewFinderView.getFrameAspectRatioWidth();
    }

    public void setFrameAspectRatioWidth(float ratioWidth) {
        if (ratioWidth <= 0.0f) {
            throw new IllegalArgumentException("Frame aspect ratio values should be greater than zero");
        }
        this.mViewFinderView.setFrameAspectRatioWidth(ratioWidth);
    }

    public float getFrameAspectRatioHeight() {
        return this.mViewFinderView.getFrameAspectRatioHeight();
    }

    public void setFrameAspectRatioHeight(float ratioHeight) {
        if (ratioHeight <= 0.0f) {
            throw new IllegalArgumentException("Frame aspect ratio values should be greater than zero");
        }
        this.mViewFinderView.setFrameAspectRatioHeight(ratioHeight);
    }

    public void setFrameAspectRatio(float ratioWidth, float ratioHeight) {
        if (ratioWidth <= 0.0f || ratioHeight <= 0.0f) {
            throw new IllegalArgumentException("Frame aspect ratio values should be greater than zero");
        }
        this.mViewFinderView.setFrameAspectRatio(ratioWidth, ratioHeight);
    }

    public boolean isMaskVisible() {
        return this.mViewFinderView.getVisibility() == 0;
    }

    public void setMaskVisible(boolean visible) {
        this.mViewFinderView.setVisibility(visible ? 0 : 4);
    }

    public boolean isAutoFocusButtonVisible() {
        return this.mAutoFocusButton.getVisibility() == 0;
    }

    public void setAutoFocusButtonVisible(boolean visible) {
        this.mAutoFocusButton.setVisibility(visible ? 0 : 4);
    }

    public int getAutoFocusButtonColor() {
        return this.mAutoFocusButtonColor;
    }

    public void setAutoFocusButtonColor(int color) {
        this.mAutoFocusButtonColor = color;
        this.mAutoFocusButton.setColorFilter(color);
    }

    public ButtonPosition getAutoFocusButtonPosition() {
        return this.mAutoFocusButtonPosition;
    }

    public void setAutoFocusButtonPosition(ButtonPosition position) {
        Objects.requireNonNull(position);
        boolean changed = position != this.mAutoFocusButtonPosition;
        this.mAutoFocusButtonPosition = position;
        if (changed && isLaidOut()) {
            requestLayout();
        }
    }

    public int getAutoFocusButtonPaddingHorizontal() {
        return this.mAutoFocusButtonPaddingHorizontal;
    }

    public void setAutoFocusButtonPaddingHorizontal(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding should be equal to or grater then zero");
        }
        boolean changed = padding != this.mAutoFocusButtonPaddingHorizontal;
        this.mAutoFocusButtonPaddingHorizontal = padding;
        if (changed) {
            invalidateAutoFocusButtonPadding();
        }
    }

    public int getAutoFocusButtonPaddingVertical() {
        return this.mAutoFocusButtonPaddingVertical;
    }

    public void setAutoFocusButtonPaddingVertical(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding should be equal to or grater then zero");
        }
        boolean changed = padding != this.mAutoFocusButtonPaddingVertical;
        this.mAutoFocusButtonPaddingVertical = padding;
        if (changed) {
            invalidateAutoFocusButtonPadding();
        }
    }

    public boolean isFlashButtonVisible() {
        return this.mFlashButton.getVisibility() == 0;
    }

    public Drawable getAutoFocusButtonOnIcon() {
        return this.mAutoFocusButtonOnIcon;
    }

    public void setAutoFocusButtonOnIcon(Drawable icon) {
        Objects.requireNonNull(icon);
        boolean changed = icon != this.mAutoFocusButtonOnIcon;
        this.mAutoFocusButtonOnIcon = icon;
        CodeScanner codeScanner = this.mCodeScanner;
        if (changed && codeScanner != null) {
            setAutoFocusEnabled(codeScanner.isAutoFocusEnabled());
        }
    }

    public Drawable getAutoFocusButtonOffIcon() {
        return this.mAutoFocusButtonOffIcon;
    }

    public void setAutoFocusButtonOffIcon(Drawable icon) {
        Objects.requireNonNull(icon);
        boolean changed = icon != this.mAutoFocusButtonOffIcon;
        this.mAutoFocusButtonOffIcon = icon;
        CodeScanner codeScanner = this.mCodeScanner;
        if (changed && codeScanner != null) {
            setAutoFocusEnabled(codeScanner.isAutoFocusEnabled());
        }
    }

    public void setFlashButtonVisible(boolean visible) {
        this.mFlashButton.setVisibility(visible ? 0 : 4);
    }

    public int getFlashButtonColor() {
        return this.mFlashButtonColor;
    }

    public void setFlashButtonColor(int color) {
        this.mFlashButtonColor = color;
        this.mFlashButton.setColorFilter(color);
    }

    public ButtonPosition getFlashButtonPosition() {
        return this.mFlashButtonPosition;
    }

    public void setFlashButtonPosition(ButtonPosition position) {
        Objects.requireNonNull(position);
        boolean changed = position != this.mFlashButtonPosition;
        this.mFlashButtonPosition = position;
        if (changed) {
            requestLayout();
        }
    }

    public int getFlashButtonPaddingHorizontal() {
        return this.mFlashButtonPaddingHorizontal;
    }

    public void setFlashButtonPaddingHorizontal(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding should be equal to or grater then zero");
        }
        boolean changed = padding != this.mFlashButtonPaddingHorizontal;
        this.mFlashButtonPaddingHorizontal = padding;
        if (changed) {
            invalidateFlashButtonPadding();
        }
    }

    public int getFlashButtonPaddingVertical() {
        return this.mFlashButtonPaddingVertical;
    }

    public void setFlashButtonPaddingVertical(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding should be equal to or grater then zero");
        }
        boolean changed = padding != this.mFlashButtonPaddingVertical;
        this.mFlashButtonPaddingVertical = padding;
        if (changed) {
            invalidateFlashButtonPadding();
        }
    }

    public Drawable getFlashButtonOnIcon() {
        return this.mFlashButtonOnIcon;
    }

    public void setFlashButtonOnIcon(Drawable icon) {
        Objects.requireNonNull(icon);
        boolean changed = icon != this.mFlashButtonOnIcon;
        this.mFlashButtonOnIcon = icon;
        CodeScanner codeScanner = this.mCodeScanner;
        if (changed && codeScanner != null) {
            setFlashEnabled(codeScanner.isFlashEnabled());
        }
    }

    public Drawable getFlashButtonOffIcon() {
        return this.mFlashButtonOffIcon;
    }

    public void setFlashButtonOffIcon(Drawable icon) {
        Objects.requireNonNull(icon);
        boolean changed = icon != this.mFlashButtonOffIcon;
        this.mFlashButtonOffIcon = icon;
        CodeScanner codeScanner = this.mCodeScanner;
        if (changed && codeScanner != null) {
            setFlashEnabled(codeScanner.isFlashEnabled());
        }
    }

    SurfaceView getPreviewView() {
        return this.mPreviewView;
    }

    ViewFinderView getViewFinderView() {
        return this.mViewFinderView;
    }

    Rect getFrameRect() {
        return this.mViewFinderView.getFrameRect();
    }

    void setPreviewSize(Point previewSize) {
        this.mPreviewSize = previewSize;
        requestLayout();
    }

    void setSizeListener(SizeListener sizeListener) {
        this.mSizeListener = sizeListener;
    }

    void setCodeScanner(CodeScanner codeScanner) {
        if (this.mCodeScanner != null) {
            throw new IllegalStateException("Code scanner has already been set");
        }
        this.mCodeScanner = codeScanner;
        setAutoFocusEnabled(codeScanner.isAutoFocusEnabled());
        setFlashEnabled(codeScanner.isFlashEnabled());
    }

    void setAutoFocusEnabled(boolean enabled) {
        this.mAutoFocusButton.setImageDrawable(enabled ? this.mAutoFocusButtonOnIcon : this.mAutoFocusButtonOffIcon);
    }

    void setFlashEnabled(boolean enabled) {
        this.mFlashButton.setImageDrawable(enabled ? this.mFlashButtonOnIcon : this.mFlashButtonOffIcon);
    }

    private void layoutButton(View button, ButtonPosition position, int parentWidth, int parentHeight) {
        int width = button.getMeasuredWidth();
        int height = button.getMeasuredHeight();
        int layoutDirection = getLayoutDirection();
        switch (position) {
            case TOP_START:
                if (layoutDirection == 1) {
                    button.layout(parentWidth - width, 0, parentWidth, height);
                } else {
                    button.layout(0, 0, width, height);
                }
                break;
            case TOP_END:
                if (layoutDirection == 1) {
                    button.layout(0, 0, width, height);
                } else {
                    button.layout(parentWidth - width, 0, parentWidth, height);
                }
                break;
            case BOTTOM_START:
                if (layoutDirection == 1) {
                    button.layout(parentWidth - width, parentHeight - height, parentWidth, parentHeight);
                } else {
                    button.layout(0, parentHeight - height, width, parentHeight);
                }
                break;
            case BOTTOM_END:
                if (layoutDirection == 1) {
                    button.layout(0, parentHeight - height, width, parentHeight);
                } else {
                    button.layout(parentWidth - width, parentHeight - height, parentWidth, parentHeight);
                }
                break;
        }
    }

    private void invalidateAutoFocusButtonPadding() {
        int autoFocusButtonHorizontalPadding = this.mAutoFocusButtonPaddingHorizontal;
        int autoFocusButtonVerticalPadding = this.mAutoFocusButtonPaddingVertical;
        this.mAutoFocusButton.setPadding(autoFocusButtonHorizontalPadding, autoFocusButtonVerticalPadding, autoFocusButtonHorizontalPadding, autoFocusButtonVerticalPadding);
    }

    private void invalidateFlashButtonPadding() {
        int flashButtonHorizontalPadding = this.mFlashButtonPaddingHorizontal;
        int flashButtonVerticalPadding = this.mFlashButtonPaddingVertical;
        this.mFlashButton.setPadding(flashButtonHorizontalPadding, flashButtonVerticalPadding, flashButtonHorizontalPadding, flashButtonVerticalPadding);
    }

    private static ButtonPosition buttonPositionFromAttr(int value) {
        switch (value) {
            case 1:
                return ButtonPosition.TOP_END;
            case 2:
                return ButtonPosition.BOTTOM_START;
            case 3:
                return ButtonPosition.BOTTOM_END;
            default:
                return ButtonPosition.TOP_START;
        }
    }

    private static int indexOfButtonPosition(ButtonPosition value) {
        switch (value) {
            case TOP_END:
                return 1;
            case BOTTOM_START:
                return 2;
            case BOTTOM_END:
                return 3;
            default:
                return 0;
        }
    }

    public static final class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    private final class AutoFocusClickListener implements View.OnClickListener {
        private AutoFocusClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CodeScanner scanner = CodeScannerView.this.mCodeScanner;
            if (scanner == null || !scanner.isAutoFocusSupportedOrUnknown()) {
                return;
            }
            boolean enabled = !scanner.isAutoFocusEnabled();
            scanner.setAutoFocusEnabled(enabled);
            CodeScannerView.this.setAutoFocusEnabled(enabled);
        }
    }

    private final class FlashClickListener implements View.OnClickListener {
        private FlashClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CodeScanner scanner = CodeScannerView.this.mCodeScanner;
            if (scanner == null || !scanner.isFlashSupportedOrUnknown()) {
                return;
            }
            boolean enabled = !scanner.isFlashEnabled();
            scanner.setFlashEnabled(enabled);
            CodeScannerView.this.setFlashEnabled(enabled);
        }
    }
}
