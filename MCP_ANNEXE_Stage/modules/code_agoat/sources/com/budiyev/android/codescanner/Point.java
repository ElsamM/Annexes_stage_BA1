package com.budiyev.android.codescanner;

/* JADX INFO: loaded from: classes2.dex */
final class Point {
    private final int mX;
    private final int mY;

    public Point(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int hashCode() {
        return this.mX ^ ((this.mY << 16) | (this.mY >>> 16));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        return this.mX == other.mX && this.mY == other.mY;
    }

    public String toString() {
        return "(" + this.mX + "; " + this.mY + ")";
    }
}
