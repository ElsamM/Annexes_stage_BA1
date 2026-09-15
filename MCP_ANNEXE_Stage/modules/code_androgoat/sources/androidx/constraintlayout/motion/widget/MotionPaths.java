package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes;
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    int mDrawPath;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mProgress;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;
    float x;
    float y;

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    void initCartesian(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float position = c.mFramePosition / 100.0f;
        this.time = position;
        this.mDrawPath = c.mDrawPath;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position : c.mPercentHeight;
        float scaleX = endTimePoint.width - startTimePoint.width;
        float scaleY = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        float startCenterX = startTimePoint.x + (startTimePoint.width / 2.0f);
        float startCenterY = startTimePoint.y + (startTimePoint.height / 2.0f);
        float endCenterX = endTimePoint.x + (endTimePoint.width / 2.0f);
        float endCenterY = endTimePoint.y + (endTimePoint.height / 2.0f);
        float pathVectorX = endCenterX - startCenterX;
        float pathVectorY = endCenterY - startCenterY;
        this.x = (int) ((startTimePoint.x + (pathVectorX * position)) - ((scaleX * scaleWidth) / 2.0f));
        this.y = (int) ((startTimePoint.y + (pathVectorY * position)) - ((scaleY * scaleHeight) / 2.0f));
        this.width = (int) (startTimePoint.width + (scaleX * scaleWidth));
        this.height = (int) (startTimePoint.height + (scaleY * scaleHeight));
        float dxdx = Float.isNaN(c.mPercentX) ? position : c.mPercentX;
        float dxdx2 = dxdx;
        float dxdx3 = c.mAltPercentY;
        float dydx = Float.isNaN(dxdx3) ? 0.0f : c.mAltPercentY;
        float dydx2 = dydx;
        float dydx3 = c.mPercentY;
        float dydy = Float.isNaN(dydx3) ? position : c.mPercentY;
        float dydy2 = dydy;
        float dydy3 = c.mAltPercentX;
        float dxdy = Float.isNaN(dydy3) ? 0.0f : c.mAltPercentX;
        this.mMode = 0;
        this.x = (int) (((startTimePoint.x + (pathVectorX * dxdx2)) + (pathVectorY * dxdy)) - ((scaleX * scaleWidth) / 2.0f));
        this.y = (int) (((startTimePoint.y + (pathVectorX * dydx2)) + (pathVectorY * dydy2)) - ((scaleY * scaleHeight) / 2.0f));
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public MotionPaths(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (startTimePoint.mAnimateRelativeTo != Key.UNSET) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
        }
        switch (c.mPositionType) {
            case 1:
                initPath(c, startTimePoint, endTimePoint);
                break;
            case 2:
                initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
                break;
            default:
                initCartesian(c, startTimePoint, endTimePoint);
                break;
        }
    }

    void initPolar(int parentWidth, int parentHeight, KeyPosition c, MotionPaths s, MotionPaths e) {
        float position = c.mFramePosition / 100.0f;
        this.time = position;
        this.mDrawPath = c.mDrawPath;
        this.mMode = c.mPositionType;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position : c.mPercentHeight;
        float scaleX = e.width - s.width;
        float scaleY = e.height - s.height;
        this.position = this.time;
        this.width = (int) (s.width + (scaleX * scaleWidth));
        this.height = (int) (s.height + (scaleY * scaleHeight));
        float f = 1.0f - position;
        switch (c.mPositionType) {
            case 1:
                this.x = ((Float.isNaN(c.mPercentX) ? position : c.mPercentX) * (e.x - s.x)) + s.x;
                this.y = ((Float.isNaN(c.mPercentY) ? position : c.mPercentY) * (e.y - s.y)) + s.y;
                break;
            case 2:
                this.x = Float.isNaN(c.mPercentX) ? ((e.x - s.x) * position) + s.x : c.mPercentX * Math.min(scaleHeight, scaleWidth);
                this.y = Float.isNaN(c.mPercentY) ? ((e.y - s.y) * position) + s.y : c.mPercentY;
                break;
            default:
                this.x = ((Float.isNaN(c.mPercentX) ? position : c.mPercentX) * (e.x - s.x)) + s.x;
                this.y = ((Float.isNaN(c.mPercentY) ? position : c.mPercentY) * (e.y - s.y)) + s.y;
                break;
        }
        this.mAnimateRelativeTo = s.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public void setupRelative(MotionController mc, MotionPaths relative) {
        double dx = ((this.x + (this.width / 2.0f)) - relative.x) - (relative.width / 2.0f);
        double dy = ((this.y + (this.height / 2.0f)) - relative.y) - (relative.height / 2.0f);
        this.mRelativeToController = mc;
        this.x = (float) Math.hypot(dy, dx);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.y = (float) (Math.atan2(dy, dx) + 1.5707963267948966d);
        } else {
            this.y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    void initScreen(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        int parentWidth2;
        float position = c.mFramePosition / 100.0f;
        this.time = position;
        this.mDrawPath = c.mDrawPath;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position : c.mPercentHeight;
        float scaleX = endTimePoint.width - startTimePoint.width;
        float scaleY = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        float startCenterX = startTimePoint.x + (startTimePoint.width / 2.0f);
        float startCenterY = startTimePoint.y + (startTimePoint.height / 2.0f);
        float endCenterX = endTimePoint.x + (endTimePoint.width / 2.0f);
        float endCenterY = endTimePoint.y + (endTimePoint.height / 2.0f);
        float pathVectorX = endCenterX - startCenterX;
        float pathVectorY = endCenterY - startCenterY;
        this.x = (int) ((startTimePoint.x + (pathVectorX * position)) - ((scaleX * scaleWidth) / 2.0f));
        this.y = (int) ((startTimePoint.y + (pathVectorY * position)) - ((scaleY * scaleHeight) / 2.0f));
        this.width = (int) (startTimePoint.width + (scaleX * scaleWidth));
        this.height = (int) (startTimePoint.height + (scaleY * scaleHeight));
        this.mMode = 2;
        if (!Float.isNaN(c.mPercentX)) {
            parentWidth2 = (int) (parentWidth - this.width);
            this.x = (int) (parentWidth2 * c.mPercentX);
        } else {
            parentWidth2 = parentWidth;
        }
        if (!Float.isNaN(c.mPercentY)) {
            this.y = (int) (((int) (parentHeight - this.height)) * c.mPercentY);
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    void initPath(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float position = c.mFramePosition / 100.0f;
        this.time = position;
        this.mDrawPath = c.mDrawPath;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position : c.mPercentHeight;
        float scaleX = endTimePoint.width - startTimePoint.width;
        float scaleY = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        float path = Float.isNaN(c.mPercentX) ? position : c.mPercentX;
        float startCenterX = startTimePoint.x + (startTimePoint.width / 2.0f);
        float startCenterY = startTimePoint.y + (startTimePoint.height / 2.0f);
        float endCenterX = endTimePoint.x + (endTimePoint.width / 2.0f);
        float endCenterY = endTimePoint.y + (endTimePoint.height / 2.0f);
        float pathVectorX = endCenterX - startCenterX;
        float pathVectorY = endCenterY - startCenterY;
        this.x = (int) ((startTimePoint.x + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f));
        this.y = (int) ((startTimePoint.y + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f));
        this.width = (int) (startTimePoint.width + (scaleX * scaleWidth));
        this.height = (int) (startTimePoint.height + (scaleY * scaleHeight));
        float perpendicular = Float.isNaN(c.mPercentY) ? 0.0f : c.mPercentY;
        float normalX = (-pathVectorY) * perpendicular;
        float normalY = pathVectorX * perpendicular;
        this.mMode = 1;
        this.x = (int) ((startTimePoint.x + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f));
        this.y = (int) ((startTimePoint.y + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f));
        this.x += normalX;
        this.y += normalY;
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    private static final float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((x - cx) * sin) + ((y - cy) * cos) + cy;
    }

    private boolean diff(float a, float b) {
        return (Float.isNaN(a) || Float.isNaN(b)) ? Float.isNaN(a) != Float.isNaN(b) : Math.abs(a - b) > 1.0E-6f;
    }

    void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean diffx = diff(this.x, points.x);
        boolean diffy = diff(this.y, points.y);
        int c = 0 + 1;
        mask[0] = mask[0] | diff(this.position, points.position);
        int c2 = c + 1;
        mask[c] = mask[c] | diffx | diffy | arcMode;
        int c3 = c2 + 1;
        mask[c2] = mask[c2] | diffx | diffy | arcMode;
        int c4 = c3 + 1;
        mask[c3] = mask[c3] | diff(this.width, points.width);
        int i = c4 + 1;
        mask[c4] = mask[c4] | diff(this.height, points.height);
    }

    void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        float v_x = this.x;
        float v_y = this.y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            this.mRelativeToController.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float angle = v_y;
            f = 2.0f;
            double d = rx;
            double d2 = radius;
            double dSin = Math.sin(angle);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (dSin * d2);
            double d4 = v_width / 2.0f;
            Double.isNaN(d4);
            float v_x2 = (float) (d3 - d4);
            double d5 = ry;
            double d6 = radius;
            double dCos = Math.cos(angle);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = d5 - (dCos * d6);
            double d8 = v_height / 2.0f;
            Double.isNaN(d8);
            v_y = (float) (d7 - d8);
            v_x = v_x2;
        } else {
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    void getCenter(double p, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float v_width;
        float v_height;
        float f;
        float v_x = this.x;
        float v_y = this.y;
        float v_width2 = this.width;
        float v_height2 = this.height;
        float dv_x = 0.0f;
        float dv_y = 0.0f;
        float dv_width = 0.0f;
        float dv_height = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            float dvalue = (float) vdata[i];
            switch (toUse[i]) {
                case 1:
                    v_x = value;
                    dv_x = dvalue;
                    break;
                case 2:
                    v_y = value;
                    dv_y = dvalue;
                    break;
                case 3:
                    v_width2 = value;
                    dv_width = dvalue;
                    break;
                case 4:
                    v_height2 = value;
                    dv_height = dvalue;
                    break;
            }
        }
        float dpos_x = (dv_width / 2.0f) + dv_x;
        float dpos_y = (dv_height / 2.0f) + dv_y;
        if (this.mRelativeToController != null) {
            f = 2.0f;
            float[] pos = new float[2];
            float[] vel = new float[2];
            float v_x2 = v_x;
            float v_y2 = v_y;
            this.mRelativeToController.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float dradius = dv_x;
            float dangle = dv_y;
            float drx = vel[0];
            float dry = vel[1];
            v_width = v_width2;
            v_height = v_height2;
            double d = rx;
            double d2 = v_x2;
            double dSin = Math.sin(v_y2);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (dSin * d2);
            double d4 = v_width / 2.0f;
            Double.isNaN(d4);
            float v_x3 = (float) (d3 - d4);
            double d5 = ry;
            double d6 = v_x2;
            double dCos = Math.cos(v_y2);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = d5 - (d6 * dCos);
            double d8 = v_height / 2.0f;
            Double.isNaN(d8);
            float v_y3 = (float) (d7 - d8);
            double d9 = drx;
            double d10 = dradius;
            double dSin2 = Math.sin(v_y2);
            Double.isNaN(d10);
            Double.isNaN(d9);
            double d11 = d9 + (dSin2 * d10);
            double dCos2 = Math.cos(v_y2);
            double d12 = dangle;
            Double.isNaN(d12);
            dpos_x = (float) (d11 + (d12 * dCos2));
            double d13 = dry;
            double d14 = dradius;
            double dCos3 = Math.cos(v_y2);
            Double.isNaN(d14);
            Double.isNaN(d13);
            double d15 = d13 - (d14 * dCos3);
            double dSin3 = Math.sin(v_y2);
            double d16 = dangle;
            Double.isNaN(d16);
            dpos_y = (float) (d15 + (d16 * dSin3));
            v_x = v_x3;
            v_y = v_y3;
        } else {
            v_width = v_width2;
            v_height = v_height2;
            f = 2.0f;
        }
        point[0] = (v_width / f) + v_x + 0.0f;
        point[1] = (v_height / f) + v_y + 0.0f;
        velocity[0] = dpos_x;
        velocity[1] = dpos_y;
    }

    void getCenterVelocity(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        float v_x = this.x;
        float v_y = this.y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            this.mRelativeToController.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float angle = v_y;
            f = 2.0f;
            double d = rx;
            double d2 = radius;
            double dSin = Math.sin(angle);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (dSin * d2);
            double d4 = v_width / 2.0f;
            Double.isNaN(d4);
            float v_x2 = (float) (d3 - d4);
            double d5 = ry;
            double d6 = radius;
            double dCos = Math.cos(angle);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = d5 - (dCos * d6);
            double d8 = v_height / 2.0f;
            Double.isNaN(d8);
            v_y = (float) (d7 - d8);
            v_x = v_x2;
        } else {
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f = this.x;
        float f2 = this.y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            switch (toUse[i]) {
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        point[offset] = v_width;
        point[offset + 1] = v_height;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void setView(float position, View view, int[] toUse, double[] data, double[] slope, double[] cycle, boolean mForceMeasure) {
        char c;
        float v_width;
        float v_height;
        float dv_x;
        float v_y;
        int i;
        float v_y2;
        double d;
        float v_x = this.x;
        float v_y3 = this.y;
        float v_width2 = this.width;
        float v_height2 = this.height;
        float dv_x2 = 0.0f;
        float dv_y = 0.0f;
        float dv_width = 0.0f;
        float dv_height = 0.0f;
        float path_rotate = Float.NaN;
        if (toUse.length != 0) {
            c = 1;
            if (this.mTempValue.length <= toUse[toUse.length - 1]) {
                int scratch_data_length = toUse[toUse.length - 1] + 1;
                this.mTempValue = new double[scratch_data_length];
                this.mTempDelta = new double[scratch_data_length];
            }
        } else {
            c = 1;
        }
        float v_x2 = v_x;
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i2 = 0; i2 < toUse.length; i2++) {
            this.mTempValue[toUse[i2]] = data[i2];
            this.mTempDelta[toUse[i2]] = slope[i2];
        }
        int i3 = 0;
        float v_y4 = v_y3;
        while (i3 < this.mTempValue.length) {
            if (Double.isNaN(this.mTempValue[i3]) && (cycle == null || cycle[i3] == 0.0d)) {
                i = i3;
                v_y2 = v_y4;
            } else {
                double deltaCycle = cycle != null ? cycle[i3] : 0.0d;
                if (Double.isNaN(this.mTempValue[i3])) {
                    i = i3;
                    v_y2 = v_y4;
                    d = deltaCycle;
                } else {
                    i = i3;
                    v_y2 = v_y4;
                    d = this.mTempValue[i3] + deltaCycle;
                }
                float value = (float) d;
                float dvalue = (float) this.mTempDelta[i];
                switch (i) {
                    case 0:
                        v_y4 = v_y2;
                        break;
                    case 1:
                        dv_x2 = dvalue;
                        v_x2 = value;
                        v_y4 = v_y2;
                        break;
                    case 2:
                        v_y4 = value;
                        dv_y = dvalue;
                        break;
                    case 3:
                        dv_width = dvalue;
                        v_width2 = value;
                        v_y4 = v_y2;
                        break;
                    case 4:
                        dv_height = dvalue;
                        v_height2 = value;
                        v_y4 = v_y2;
                        break;
                    case 5:
                        path_rotate = value;
                        v_y4 = v_y2;
                        break;
                }
                i3 = i + 1;
            }
            v_y4 = v_y2;
            i3 = i + 1;
        }
        float v_y5 = v_y4;
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            v_width = v_width2;
            v_height = v_height2;
            this.mRelativeToController.getCenter(position, pos, vel);
            float rx = pos[0];
            float ry = pos[c];
            float radius = v_x2;
            float dradius = dv_x2;
            float dangle = dv_y;
            float drx = vel[0];
            float dry = vel[c];
            double d2 = rx;
            double d3 = radius;
            double dSin = Math.sin(v_y5);
            Double.isNaN(d3);
            Double.isNaN(d2);
            double d4 = d2 + (dSin * d3);
            double d5 = v_width / 2.0f;
            Double.isNaN(d5);
            float pos_x = (float) (d4 - d5);
            double d6 = ry;
            double d7 = radius;
            double dCos = Math.cos(v_y5);
            Double.isNaN(d7);
            Double.isNaN(d6);
            double d8 = d6 - (dCos * d7);
            double d9 = v_height / 2.0f;
            Double.isNaN(d9);
            float pos_y = (float) (d8 - d9);
            double d10 = drx;
            double d11 = dradius;
            double dSin2 = Math.sin(v_y5);
            Double.isNaN(d11);
            Double.isNaN(d10);
            double d12 = d10 + (dSin2 * d11);
            double d13 = radius;
            double dCos2 = Math.cos(v_y5);
            Double.isNaN(d13);
            double d14 = dCos2 * d13;
            double d15 = dangle;
            Double.isNaN(d15);
            float dpos_x = (float) (d12 + (d15 * d14));
            double d16 = dry;
            double d17 = dradius;
            double dCos3 = Math.cos(v_y5);
            Double.isNaN(d17);
            Double.isNaN(d16);
            double d18 = d16 - (d17 * dCos3);
            double d19 = radius;
            double dSin3 = Math.sin(v_y5);
            Double.isNaN(d19);
            double d20 = dangle;
            Double.isNaN(d20);
            float dpos_y = (float) (d18 + (dSin3 * d19 * d20));
            v_x2 = pos_x;
            if (slope.length >= 2) {
                slope[0] = dpos_x;
                slope[c] = dpos_y;
            }
            if (!Float.isNaN(path_rotate)) {
                double d21 = path_rotate;
                v_y = pos_y;
                double degrees = Math.toDegrees(Math.atan2(dpos_y, dpos_x));
                Double.isNaN(d21);
                float rot = (float) (d21 + degrees);
                view.setRotation(rot);
            } else {
                v_y = pos_y;
            }
            dv_x = v_y;
        } else {
            v_width = v_width2;
            v_height = v_height2;
            float dv_x3 = dv_x2;
            float dv_y2 = dv_y;
            float dv_width2 = dv_width;
            if (!Float.isNaN(path_rotate)) {
                float dx = dv_x3 + (dv_width2 / 2.0f);
                float dy = dv_y2 + (dv_height / 2.0f);
                double d22 = 0.0f;
                double d23 = path_rotate;
                double degrees2 = Math.toDegrees(Math.atan2(dy, dx));
                Double.isNaN(d23);
                Double.isNaN(d22);
                float rot2 = (float) (d22 + d23 + degrees2);
                view.setRotation(rot2);
            }
            dv_x = v_y5;
        }
        if (view instanceof FloatLayout) {
            ((FloatLayout) view).layout(v_x2, dv_x, v_x2 + v_width, dv_x + v_height);
            return;
        }
        int l = (int) (v_x2 + 0.5f);
        int t = (int) (dv_x + 0.5f);
        int r = (int) (v_x2 + 0.5f + v_width);
        int b = (int) (0.5f + dv_x + v_height);
        int i_width = r - l;
        int i_height = b - t;
        if (i_width == view.getMeasuredWidth() && i_height == view.getMeasuredHeight()) {
            c = 0;
        }
        if (c != 0 || mForceMeasure) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(i_width, BasicMeasure.EXACTLY);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i_height, BasicMeasure.EXACTLY);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        view.layout(l, t, r, b);
    }

    void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float ry;
        float v_height;
        float f;
        float angle;
        float v_x;
        float cx;
        float cy;
        float x1;
        float y1;
        float x4;
        float y4;
        int[] iArr = toUse;
        float v_x2 = this.x;
        float v_y = this.y;
        float v_width = this.width;
        float v_height2 = this.height;
        float v_x3 = v_x2;
        int i = 0;
        while (true) {
            float v_y2 = v_y;
            if (i < iArr.length) {
                int i2 = i;
                float value = (float) data[i2];
                switch (toUse[i2]) {
                    case 1:
                        v_x3 = value;
                        break;
                    case 2:
                        v_y2 = value;
                        break;
                    case 3:
                        v_width = value;
                        break;
                    case 4:
                        v_height2 = value;
                        break;
                }
                i = i2 + 1;
                iArr = toUse;
                v_y = v_y2;
            } else {
                if (this.mRelativeToController == null) {
                    ry = v_width;
                    v_height = v_height2;
                    f = 2.0f;
                    angle = v_y2;
                    v_x = v_x3;
                } else {
                    float rx = this.mRelativeToController.getCenterX();
                    float ry2 = this.mRelativeToController.getCenterY();
                    float radius = v_x3;
                    f = 2.0f;
                    double d = rx;
                    double d2 = radius;
                    double dSin = Math.sin(v_y2);
                    Double.isNaN(d2);
                    Double.isNaN(d);
                    double d3 = d + (dSin * d2);
                    double d4 = v_width / 2.0f;
                    Double.isNaN(d4);
                    float v_x4 = (float) (d3 - d4);
                    v_height = v_height2;
                    ry = v_width;
                    double d5 = ry2;
                    double d6 = radius;
                    double dCos = Math.cos(v_y2);
                    Double.isNaN(d6);
                    Double.isNaN(d5);
                    double d7 = d5 - (d6 * dCos);
                    double d8 = v_height / 2.0f;
                    Double.isNaN(d8);
                    angle = (float) (d7 - d8);
                    v_x = v_x4;
                }
                float x12 = v_x;
                float y12 = angle;
                float x2 = v_x + ry;
                float y2 = y12;
                float x3 = x2;
                float y3 = angle + v_height;
                float x42 = x12;
                float y42 = y3;
                float cx2 = x12 + (ry / f);
                float cy2 = y12 + (v_height / f);
                if (!Float.isNaN(Float.NaN)) {
                    float cx3 = x12 + ((x2 - x12) * Float.NaN);
                    cx = cx3;
                } else {
                    cx = cx2;
                }
                if (!Float.isNaN(Float.NaN)) {
                    float cy3 = y12 + ((y3 - y12) * Float.NaN);
                    cy = cy3;
                } else {
                    cy = cy2;
                }
                if (1.0f == 1.0f) {
                    x1 = x12;
                } else {
                    float midx = (x12 + x2) / f;
                    x2 = ((x2 - midx) * 1.0f) + midx;
                    x3 = ((x3 - midx) * 1.0f) + midx;
                    x42 = ((x42 - midx) * 1.0f) + midx;
                    x1 = ((x12 - midx) * 1.0f) + midx;
                }
                if (1.0f == 1.0f) {
                    y1 = y12;
                } else {
                    float midy = (y12 + y3) / f;
                    y2 = ((y2 - midy) * 1.0f) + midy;
                    y3 = ((y3 - midy) * 1.0f) + midy;
                    y42 = ((y42 - midy) * 1.0f) + midy;
                    y1 = ((y12 - midy) * 1.0f) + midy;
                }
                if (0.0f != 0.0f) {
                    float sin = (float) Math.sin(Math.toRadians(0.0f));
                    float cos = (float) Math.cos(Math.toRadians(0.0f));
                    float tx1 = xRotate(sin, cos, cx, cy, x1, y1);
                    float ty1 = yRotate(sin, cos, cx, cy, x1, y1);
                    float x13 = x2;
                    float y22 = y2;
                    x2 = xRotate(sin, cos, cx, cy, x13, y22);
                    y2 = yRotate(sin, cos, cx, cy, x13, y22);
                    float x22 = x3;
                    float y32 = y3;
                    x3 = xRotate(sin, cos, cx, cy, x22, y32);
                    y3 = yRotate(sin, cos, cx, cy, x22, y32);
                    float x32 = x42;
                    float y43 = y42;
                    x42 = xRotate(sin, cos, cx, cy, x32, y43);
                    y42 = yRotate(sin, cos, cx, cy, x32, y43);
                    x4 = tx1;
                    y4 = ty1;
                } else {
                    x4 = x1;
                    y4 = y1;
                }
                int offset2 = offset + 1;
                path[offset] = x4 + 0.0f;
                int offset3 = offset2 + 1;
                path[offset2] = y4 + 0.0f;
                int offset4 = offset3 + 1;
                path[offset3] = x2 + 0.0f;
                int offset5 = offset4 + 1;
                path[offset4] = y2 + 0.0f;
                int offset6 = offset5 + 1;
                path[offset5] = x3 + 0.0f;
                int offset7 = offset6 + 1;
                path[offset6] = y3 + 0.0f;
                int offset8 = offset7 + 1;
                path[offset7] = x42 + 0.0f;
                int i3 = offset8 + 1;
                path[offset8] = y42 + 0.0f;
                return;
            }
        }
    }

    void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        float d_x = 0.0f;
        float d_y = 0.0f;
        float d_width = 0.0f;
        float d_height = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float deltaV = (float) deltaData[i];
            switch (toUse[i]) {
                case 1:
                    d_x = deltaV;
                    break;
                case 2:
                    d_y = deltaV;
                    break;
                case 3:
                    d_width = deltaV;
                    break;
                case 4:
                    d_height = deltaV;
                    break;
            }
        }
        float deltaX = d_x - ((0.0f * d_width) / 2.0f);
        float deltaY = d_y - ((0.0f * d_height) / 2.0f);
        float deltaWidth = (0.0f + 1.0f) * d_width;
        float deltaHeight = (0.0f + 1.0f) * d_height;
        float deltaRight = deltaX + deltaWidth;
        float deltaBottom = deltaY + deltaHeight;
        mAnchorDpDt[0] = ((1.0f - locationX) * deltaX) + (deltaRight * locationX) + 0.0f;
        mAnchorDpDt[1] = ((1.0f - locationY) * deltaY) + (deltaBottom * locationY) + 0.0f;
    }

    void fillStandard(double[] data, int[] toUse) {
        float[] set = {this.position, this.x, this.y, this.width, this.height, this.mPathRotate};
        int c = 0;
        for (int i = 0; i < toUse.length; i++) {
            if (toUse[i] < set.length) {
                data[c] = set[toUse[i]];
                c++;
            }
        }
    }

    boolean hasCustomData(String name) {
        return this.attributes.containsKey(name);
    }

    int getCustomDataCount(String name) {
        ConstraintAttribute a = this.attributes.get(name);
        if (a == null) {
            return 0;
        }
        return a.numberOfInterpolatedValues();
    }

    int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute a = this.attributes.get(name);
        if (a == null) {
            return 0;
        }
        if (a.numberOfInterpolatedValues() == 1) {
            value[offset] = a.getValueToInterpolate();
            return 1;
        }
        int N = a.numberOfInterpolatedValues();
        float[] f = new float[N];
        a.getValuesToInterpolate(f);
        int i = 0;
        while (i < N) {
            value[offset] = f[i];
            i++;
            offset++;
        }
        return N;
    }

    void setBounds(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths o) {
        return Float.compare(this.position, o.position);
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathMotionArc = c.motion.mPathMotionArc;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateCircleAngleTo = c.motion.mAnimateCircleAngleTo;
        this.mProgress = c.propertySet.mProgress;
        this.mRelativeAngle = c.layout.circleAngle;
        Set<String> at = c.mCustomConstraints.keySet();
        for (String s : at) {
            ConstraintAttribute attr = c.mCustomConstraints.get(s);
            if (attr != null && attr.isContinuous()) {
                this.attributes.put(s, attr);
            }
        }
    }

    public void configureRelativeTo(MotionController toOrbit) {
        toOrbit.getPos(this.mProgress);
    }
}
