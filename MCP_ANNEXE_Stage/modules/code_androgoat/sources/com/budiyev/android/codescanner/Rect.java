package com.budiyev.android.codescanner;

import android.graphics.Matrix;

/* JADX INFO: loaded from: classes2.dex */
final class Rect {
    private final int mBottom;
    private final int mLeft;
    private final int mRight;
    private final int mTop;

    public Rect(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getTop() {
        return this.mTop;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getBottom() {
        return this.mBottom;
    }

    public int getWidth() {
        return this.mRight - this.mLeft;
    }

    public int getHeight() {
        return this.mBottom - this.mTop;
    }

    public boolean isPointInside(int x, int y) {
        return this.mLeft < x && this.mTop < y && this.mRight > x && this.mBottom > y;
    }

    public Rect sort() {
        int left = this.mLeft;
        int top = this.mTop;
        int right = this.mRight;
        int bottom = this.mBottom;
        if (left <= right && top <= bottom) {
            return this;
        }
        if (left > right) {
            left = right;
            right = left;
        }
        if (top > bottom) {
            top = bottom;
            bottom = top;
        }
        return new Rect(left, top, right, bottom);
    }

    public Rect bound(int left, int top, int right, int bottom) {
        int l = this.mLeft;
        int t = this.mTop;
        int r = this.mRight;
        int b = this.mBottom;
        if (l >= left && t >= top && r <= right && b <= bottom) {
            return this;
        }
        return new Rect(Math.max(l, left), Math.max(t, top), Math.min(r, right), Math.min(b, bottom));
    }

    public Rect rotate(float angle, float x, float y) {
        Matrix matrix = new Matrix();
        float[] rect = {this.mLeft, this.mTop, this.mRight, this.mBottom};
        matrix.postRotate(angle, x, y);
        matrix.mapPoints(rect);
        int left = (int) rect[0];
        int top = (int) rect[1];
        int right = (int) rect[2];
        int bottom = (int) rect[3];
        if (left > right) {
            left = right;
            right = left;
        }
        if (top > bottom) {
            top = bottom;
            bottom = top;
        }
        return new Rect(left, top, right, bottom);
    }

    public Rect fitIn(Rect area) {
        int left = this.mLeft;
        int top = this.mTop;
        int right = this.mRight;
        int bottom = this.mBottom;
        int width = getWidth();
        int height = getHeight();
        int areaLeft = area.mLeft;
        int areaTop = area.mTop;
        int areaRight = area.mRight;
        int areaBottom = area.mBottom;
        int areaWidth = area.getWidth();
        int areaHeight = area.getHeight();
        if (left >= areaLeft && top >= areaTop && right <= areaRight && bottom <= areaBottom) {
            return this;
        }
        int fitWidth = Math.min(width, areaWidth);
        int fitHeight = Math.min(height, areaHeight);
        if (left < areaLeft) {
            left = areaLeft;
            right = left + fitWidth;
        } else if (right > areaRight) {
            right = areaRight;
            left = right - fitWidth;
        }
        if (top < areaTop) {
            top = areaTop;
            bottom = top + fitHeight;
        } else if (bottom > areaBottom) {
            bottom = areaBottom;
            top = bottom - fitHeight;
        }
        return new Rect(left, top, right, bottom);
    }

    public int hashCode() {
        return (((((this.mLeft * 31) + this.mTop) * 31) + this.mRight) * 31) + this.mBottom;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rect)) {
            return false;
        }
        Rect other = (Rect) obj;
        return this.mLeft == other.mLeft && this.mTop == other.mTop && this.mRight == other.mRight && this.mBottom == other.mBottom;
    }

    public String toString() {
        return "[(" + this.mLeft + "; " + this.mTop + ") - (" + this.mRight + "; " + this.mBottom + ")]";
    }
}
