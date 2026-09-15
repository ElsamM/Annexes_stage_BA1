package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.location.LocationRequestCompat;
import java.util.ArrayList;
import kotlin.io.encoding.Base64;

/* JADX INFO: loaded from: classes2.dex */
public class PathParser {
    private static final String LOGTAG = "PathParser";

    static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int copyLength = Math.min(resultLength, originalLength - start);
        float[] result = new float[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }

    public static Path createPathFromPathData(String pathData) {
        Path path = new Path();
        PathDataNode[] nodes = createNodesFromPathData(pathData);
        if (nodes != null) {
            try {
                PathDataNode.nodesToPath(nodes, path);
                return path;
            } catch (RuntimeException e) {
                throw new RuntimeException("Error in parsing " + pathData, e);
            }
        }
        return null;
    }

    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        int start = 0;
        int end = 1;
        ArrayList<PathDataNode> list = new ArrayList<>();
        while (end < pathData.length()) {
            int end2 = nextStart(pathData, end);
            String s = pathData.substring(start, end2).trim();
            if (s.length() > 0) {
                float[] val = getFloats(s);
                addNode(list, s.charAt(0), val);
            }
            start = end2;
            end = end2 + 1;
        }
        if (end - start == 1 && start < pathData.length()) {
            addNode(list, pathData.charAt(start), new float[0]);
        }
        return (PathDataNode[]) list.toArray(new PathDataNode[list.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] source) {
        if (source == null) {
            return null;
        }
        PathDataNode[] copy = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = new PathDataNode(source[i]);
        }
        return copy;
    }

    public static boolean canMorph(PathDataNode[] nodesFrom, PathDataNode[] nodesTo) {
        if (nodesFrom == null || nodesTo == null || nodesFrom.length != nodesTo.length) {
            return false;
        }
        for (int i = 0; i < nodesFrom.length; i++) {
            if (nodesFrom[i].mType != nodesTo[i].mType || nodesFrom[i].mParams.length != nodesTo[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] target, PathDataNode[] source) {
        for (int i = 0; i < source.length; i++) {
            target[i].mType = source[i].mType;
            for (int j = 0; j < source[i].mParams.length; j++) {
                target[i].mParams[j] = source[i].mParams[j];
            }
        }
    }

    private static int nextStart(String s, int end) {
        while (end < s.length()) {
            char c = s.charAt(end);
            if (((c - 'A') * (c - 'Z') <= 0 || (c - 'a') * (c - 'z') <= 0) && c != 'e' && c != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    private static void addNode(ArrayList<PathDataNode> list, char cmd, float[] val) {
        list.add(new PathDataNode(cmd, val));
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int count = 0;
            int startPosition = 1;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            while (startPosition < totalLength) {
                extract(s, startPosition, result);
                int endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    results[count] = Float.parseFloat(s.substring(startPosition, endPosition));
                    count++;
                }
                if (result.mEndWithNegOrDot) {
                    startPosition = endPosition;
                } else {
                    startPosition = endPosition + 1;
                }
            }
            return copyOfRange(results, 0, count);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    private static void extract(String s, int start, ExtractFloatResult result) {
        boolean foundSeparator = false;
        result.mEndWithNegOrDot = false;
        boolean secondDot = false;
        boolean isExponential = false;
        for (int currentIndex = start; currentIndex < s.length(); currentIndex++) {
            boolean isPrevExponential = isExponential;
            isExponential = false;
            char currentChar = s.charAt(currentIndex);
            switch (currentChar) {
                case ' ':
                case ',':
                    foundSeparator = true;
                    break;
                case '-':
                    if (currentIndex != start && !isPrevExponential) {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                    }
                    break;
                case '.':
                    if (!secondDot) {
                        secondDot = true;
                    } else {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                    }
                    break;
                case 'E':
                case TypedValues.TYPE_TARGET /* 101 */:
                    isExponential = true;
                    break;
            }
            if (foundSeparator) {
                result.mEndPosition = currentIndex;
            }
        }
        result.mEndPosition = currentIndex;
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] target, PathDataNode[] from, PathDataNode[] to, float fraction) {
        if (target == null || from == null || to == null) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        }
        if (target.length != from.length || from.length != to.length) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        }
        if (!canMorph(from, to)) {
            return false;
        }
        for (int i = 0; i < target.length; i++) {
            target[i].interpolatePathDataNode(from[i], to[i], fraction);
        }
        return true;
    }

    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char type, float[] params) {
            this.mType = type;
            this.mParams = params;
        }

        PathDataNode(PathDataNode n) {
            this.mType = n.mType;
            this.mParams = PathParser.copyOfRange(n.mParams, 0, n.mParams.length);
        }

        public static void nodesToPath(PathDataNode[] node, Path path) {
            float[] current = new float[6];
            char previousCommand = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path, current, previousCommand, node[i].mType, node[i].mParams);
                previousCommand = node[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode nodeFrom, PathDataNode nodeTo, float fraction) {
            this.mType = nodeFrom.mType;
            for (int i = 0; i < nodeFrom.mParams.length; i++) {
                this.mParams[i] = (nodeFrom.mParams[i] * (1.0f - fraction)) + (nodeTo.mParams[i] * fraction);
            }
        }

        private static void addCommand(Path path, float[] current, char previousCmd, char cmd, float[] val) {
            int incr;
            int k;
            float currentX;
            float reflectiveCtrlPointY;
            int k2;
            boolean z;
            int k3;
            float currentX2;
            boolean z2;
            float reflectiveCtrlPointY2;
            Path path2 = path;
            float currentX3 = current[0];
            float currentY = current[1];
            float ctrlPointX = current[2];
            float ctrlPointY = current[3];
            float currentSegmentStartX = current[4];
            float currentSegmentStartY = current[5];
            switch (cmd) {
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT /* 65 */:
                case 'a':
                    incr = 7;
                    break;
                case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL /* 67 */:
                case 'c':
                    incr = 6;
                    break;
                case 'H':
                case 'V':
                case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                case 'v':
                    incr = 1;
                    break;
                case Base64.mimeLineLength /* 76 */:
                case 'M':
                case 'T':
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                case 't':
                    incr = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    incr = 4;
                    break;
                case 'Z':
                case 'z':
                    path2.close();
                    currentX3 = currentSegmentStartX;
                    currentY = currentSegmentStartY;
                    ctrlPointX = currentSegmentStartX;
                    ctrlPointY = currentSegmentStartY;
                    path2.moveTo(currentX3, currentY);
                    incr = 2;
                    break;
                default:
                    incr = 2;
                    break;
            }
            int k4 = 0;
            float currentX4 = currentX3;
            float currentY2 = currentY;
            float ctrlPointX2 = ctrlPointX;
            float ctrlPointY2 = ctrlPointY;
            float currentSegmentStartX2 = currentSegmentStartX;
            float currentSegmentStartY2 = currentSegmentStartY;
            char previousCmd2 = previousCmd;
            while (k4 < val.length) {
                switch (cmd) {
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT /* 65 */:
                        float currentY3 = currentY2;
                        k = k4;
                        drawArc(path, currentX4, currentY3, val[k + 5], val[k + 6], val[k + 0], val[k + 1], val[k + 2], val[k + 3] != 0.0f, val[k + 4] != 0.0f);
                        float currentX5 = val[k + 5];
                        currentX = val[k + 6];
                        currentX4 = currentX5;
                        ctrlPointX2 = currentX5;
                        ctrlPointY2 = currentX;
                        break;
                    case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL /* 67 */:
                        k = k4;
                        path2.cubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                        float currentX6 = val[k + 4];
                        currentX = val[k + 5];
                        currentX4 = currentX6;
                        ctrlPointX2 = val[k + 2];
                        ctrlPointY2 = val[k + 3];
                        break;
                    case 'H':
                        float currentY4 = currentY2;
                        k = k4;
                        path2.lineTo(val[k + 0], currentY4);
                        currentX4 = val[k + 0];
                        currentX = currentY4;
                        break;
                    case Base64.mimeLineLength /* 76 */:
                        k = k4;
                        path2.lineTo(val[k + 0], val[k + 1]);
                        currentX4 = val[k + 0];
                        currentX = val[k + 1];
                        break;
                    case 'M':
                        k = k4;
                        float currentX7 = val[k + 0];
                        float currentY5 = val[k + 1];
                        if (k > 0) {
                            path2.lineTo(val[k + 0], val[k + 1]);
                            currentX4 = currentX7;
                            currentX = currentY5;
                        } else {
                            path2.moveTo(val[k + 0], val[k + 1]);
                            currentX4 = currentX7;
                            currentX = currentY5;
                            currentSegmentStartX2 = currentX7;
                            currentSegmentStartY2 = currentY5;
                        }
                        break;
                    case 'Q':
                        k = k4;
                        path2.quadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX2 = val[k + 0];
                        ctrlPointY2 = val[k + 1];
                        currentX4 = val[k + 2];
                        currentX = val[k + 3];
                        break;
                    case 'S':
                        float currentY6 = currentY2;
                        k = k4;
                        char previousCmd3 = previousCmd2;
                        float currentX8 = currentX4;
                        float reflectiveCtrlPointX = currentX8;
                        if (previousCmd3 != 'c' && previousCmd3 != 's' && previousCmd3 != 'C' && previousCmd3 != 'S') {
                            reflectiveCtrlPointY = currentY6;
                        } else {
                            reflectiveCtrlPointX = (currentX8 * 2.0f) - ctrlPointX2;
                            float reflectiveCtrlPointY3 = (currentY6 * 2.0f) - ctrlPointY2;
                            reflectiveCtrlPointY = reflectiveCtrlPointY3;
                        }
                        path2.cubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX2 = val[k + 0];
                        ctrlPointY2 = val[k + 1];
                        currentX4 = val[k + 2];
                        currentX = val[k + 3];
                        break;
                    case 'T':
                        float currentY7 = currentY2;
                        k = k4;
                        char previousCmd4 = previousCmd2;
                        float currentX9 = currentX4;
                        float reflectiveCtrlPointX2 = currentX9;
                        float reflectiveCtrlPointY4 = currentY7;
                        if (previousCmd4 == 'q' || previousCmd4 == 't' || previousCmd4 == 'Q' || previousCmd4 == 'T') {
                            reflectiveCtrlPointX2 = (currentX9 * 2.0f) - ctrlPointX2;
                            reflectiveCtrlPointY4 = (currentY7 * 2.0f) - ctrlPointY2;
                        }
                        path2.quadTo(reflectiveCtrlPointX2, reflectiveCtrlPointY4, val[k + 0], val[k + 1]);
                        ctrlPointX2 = reflectiveCtrlPointX2;
                        ctrlPointY2 = reflectiveCtrlPointY4;
                        currentX4 = val[k + 0];
                        currentX = val[k + 1];
                        break;
                    case 'V':
                        k = k4;
                        path2.lineTo(currentX4, val[k + 0]);
                        currentX = val[k + 0];
                        break;
                    case 'a':
                        float f = val[k4 + 5] + currentX4;
                        float f2 = val[k4 + 6] + currentY2;
                        float f3 = val[k4 + 0];
                        float f4 = val[k4 + 1];
                        float f5 = val[k4 + 2];
                        if (val[k4 + 3] != 0.0f) {
                            k2 = k4;
                            z = true;
                        } else {
                            k2 = k4;
                            z = false;
                        }
                        if (val[k2 + 4] != 0.0f) {
                            k3 = k2;
                            currentX2 = currentX4;
                            z2 = true;
                        } else {
                            k3 = k2;
                            currentX2 = currentX4;
                            z2 = false;
                        }
                        float currentY8 = currentY2;
                        k = k3;
                        drawArc(path, currentX2, currentY8, f, f2, f3, f4, f5, z, z2);
                        currentX4 = currentX2 + val[k + 5];
                        currentX = val[k + 6] + currentY8;
                        ctrlPointX2 = currentX4;
                        ctrlPointY2 = currentX;
                        break;
                    case 'c':
                        path2.rCubicTo(val[k4 + 0], val[k4 + 1], val[k4 + 2], val[k4 + 3], val[k4 + 4], val[k4 + 5]);
                        float ctrlPointX3 = val[k4 + 2] + currentX4;
                        float ctrlPointY3 = currentY2 + val[k4 + 3];
                        currentX4 += val[k4 + 4];
                        ctrlPointX2 = ctrlPointX3;
                        ctrlPointY2 = ctrlPointY3;
                        currentX = currentY2 + val[k4 + 5];
                        k = k4;
                        break;
                    case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                        path2.rLineTo(val[k4 + 0], 0.0f);
                        currentX4 += val[k4 + 0];
                        currentX = currentY2;
                        k = k4;
                        break;
                    case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                        path2.rLineTo(val[k4 + 0], val[k4 + 1]);
                        currentX4 += val[k4 + 0];
                        currentX = currentY2 + val[k4 + 1];
                        k = k4;
                        break;
                    case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                        currentX4 += val[k4 + 0];
                        float currentY9 = currentY2 + val[k4 + 1];
                        if (k4 > 0) {
                            path2.rLineTo(val[k4 + 0], val[k4 + 1]);
                            currentX = currentY9;
                            k = k4;
                        } else {
                            path2.rMoveTo(val[k4 + 0], val[k4 + 1]);
                            currentSegmentStartX2 = currentX4;
                            currentSegmentStartY2 = currentY9;
                            currentX = currentY9;
                            k = k4;
                        }
                        break;
                    case 'q':
                        path2.rQuadTo(val[k4 + 0], val[k4 + 1], val[k4 + 2], val[k4 + 3]);
                        float ctrlPointX4 = val[k4 + 0] + currentX4;
                        float ctrlPointY4 = currentY2 + val[k4 + 1];
                        currentX4 += val[k4 + 2];
                        ctrlPointX2 = ctrlPointX4;
                        ctrlPointY2 = ctrlPointY4;
                        currentX = currentY2 + val[k4 + 3];
                        k = k4;
                        break;
                    case 's':
                        float reflectiveCtrlPointX3 = 0.0f;
                        if (previousCmd2 != 'c' && previousCmd2 != 's' && previousCmd2 != 'C' && previousCmd2 != 'S') {
                            reflectiveCtrlPointY2 = 0.0f;
                        } else {
                            reflectiveCtrlPointX3 = currentX4 - ctrlPointX2;
                            float reflectiveCtrlPointY5 = currentY2 - ctrlPointY2;
                            reflectiveCtrlPointY2 = reflectiveCtrlPointY5;
                        }
                        path2.rCubicTo(reflectiveCtrlPointX3, reflectiveCtrlPointY2, val[k4 + 0], val[k4 + 1], val[k4 + 2], val[k4 + 3]);
                        float ctrlPointX5 = val[k4 + 0] + currentX4;
                        float ctrlPointY5 = currentY2 + val[k4 + 1];
                        currentX4 += val[k4 + 2];
                        ctrlPointX2 = ctrlPointX5;
                        ctrlPointY2 = ctrlPointY5;
                        currentX = currentY2 + val[k4 + 3];
                        k = k4;
                        break;
                    case 't':
                        float reflectiveCtrlPointX4 = 0.0f;
                        float reflectiveCtrlPointY6 = 0.0f;
                        if (previousCmd2 == 'q' || previousCmd2 == 't' || previousCmd2 == 'Q' || previousCmd2 == 'T') {
                            reflectiveCtrlPointX4 = currentX4 - ctrlPointX2;
                            reflectiveCtrlPointY6 = currentY2 - ctrlPointY2;
                        }
                        path2.rQuadTo(reflectiveCtrlPointX4, reflectiveCtrlPointY6, val[k4 + 0], val[k4 + 1]);
                        float ctrlPointX6 = currentX4 + reflectiveCtrlPointX4;
                        currentX4 += val[k4 + 0];
                        ctrlPointX2 = ctrlPointX6;
                        ctrlPointY2 = currentY2 + reflectiveCtrlPointY6;
                        currentX = currentY2 + val[k4 + 1];
                        k = k4;
                        break;
                    case 'v':
                        path2.rLineTo(0.0f, val[k4 + 0]);
                        currentX = currentY2 + val[k4 + 0];
                        k = k4;
                        break;
                    default:
                        float currentY10 = currentY2;
                        k = k4;
                        currentX = currentY10;
                        break;
                }
                previousCmd2 = cmd;
                k4 = k + incr;
                currentY2 = currentX;
                path2 = path;
            }
            current[0] = currentX4;
            current[1] = currentY2;
            current[2] = ctrlPointX2;
            current[3] = ctrlPointY2;
            current[4] = currentSegmentStartX2;
            current[5] = currentSegmentStartY2;
        }

        private static void drawArc(Path p, float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
            double cx;
            double cy;
            double sweep;
            double thetaD = Math.toRadians(theta);
            double cosTheta = Math.cos(thetaD);
            double sinTheta = Math.sin(thetaD);
            double d = x0;
            Double.isNaN(d);
            double d2 = y0;
            Double.isNaN(d2);
            double d3 = (d * cosTheta) + (d2 * sinTheta);
            double d4 = a;
            Double.isNaN(d4);
            double x0p = d3 / d4;
            double d5 = -x0;
            Double.isNaN(d5);
            double d6 = y0;
            Double.isNaN(d6);
            double d7 = (d5 * sinTheta) + (d6 * cosTheta);
            double d8 = b;
            Double.isNaN(d8);
            double y0p = d7 / d8;
            double d9 = x1;
            Double.isNaN(d9);
            double d10 = y1;
            Double.isNaN(d10);
            double d11 = (d9 * cosTheta) + (d10 * sinTheta);
            double d12 = a;
            Double.isNaN(d12);
            double x1p = d11 / d12;
            double d13 = -x1;
            Double.isNaN(d13);
            double d14 = y1;
            Double.isNaN(d14);
            double d15 = (d13 * sinTheta) + (d14 * cosTheta);
            double d16 = b;
            Double.isNaN(d16);
            double y1p = d15 / d16;
            double dx = x0p - x1p;
            double dy = y0p - y1p;
            double xm = (x0p + x1p) / 2.0d;
            double ym = (y0p + y1p) / 2.0d;
            double dsq = (dx * dx) + (dy * dy);
            if (dsq == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double disc = (1.0d / dsq) - 0.25d;
            if (disc < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + dsq);
                float adjust = (float) (Math.sqrt(dsq) / 1.99999d);
                drawArc(p, x0, y0, x1, y1, a * adjust, b * adjust, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            double s = Math.sqrt(disc);
            double sdx = s * dx;
            double sdy = s * dy;
            if (isMoreThanHalf == isPositiveArc) {
                cx = xm - sdy;
                cy = ym + sdx;
            } else {
                cx = xm + sdy;
                cy = ym - sdx;
            }
            double eta0 = Math.atan2(y0p - cy, x0p - cx);
            double eta1 = Math.atan2(y1p - cy, x1p - cx);
            double sweep2 = eta1 - eta0;
            if (isPositiveArc == (sweep2 >= 0.0d)) {
                sweep = sweep2;
            } else if (sweep2 > 0.0d) {
                sweep = sweep2 - 6.283185307179586d;
            } else {
                sweep = sweep2 + 6.283185307179586d;
            }
            double sweep3 = a;
            Double.isNaN(sweep3);
            double cx2 = cx * sweep3;
            double d17 = b;
            Double.isNaN(d17);
            double cy2 = cy * d17;
            double eta12 = a;
            arcToBezier(p, (cx2 * cosTheta) - (cy2 * sinTheta), (cx2 * sinTheta) + (cy2 * cosTheta), eta12, b, x0, y0, thetaD, eta0, sweep);
        }

        private static void arcToBezier(Path p, double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
            double e1x2 = a;
            int numSegments = (int) Math.ceil(Math.abs((sweep * 4.0d) / 3.141592653589793d));
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            double cosEta1 = Math.cos(start);
            double sinEta1 = Math.sin(start);
            double ep1x = (((-e1x2) * cosTheta) * sinEta1) - ((b * sinTheta) * cosEta1);
            double ep1x2 = -e1x2;
            double ep1y = (ep1x2 * sinTheta * sinEta1) + (b * cosTheta * cosEta1);
            double ep1y2 = ep1y;
            double ep1y3 = numSegments;
            Double.isNaN(ep1y3);
            double anglePerSegment = sweep / ep1y3;
            double eta1 = start;
            int i = 0;
            double eta12 = e1x;
            double ep1x3 = ep1x;
            double e1y2 = e1y;
            while (i < numSegments) {
                double eta2 = eta1 + anglePerSegment;
                double sinEta2 = Math.sin(eta2);
                double cosEta2 = Math.cos(eta2);
                double anglePerSegment2 = anglePerSegment;
                double anglePerSegment3 = (cx + ((e1x2 * cosTheta) * cosEta2)) - ((b * sinTheta) * sinEta2);
                int numSegments2 = numSegments;
                double e1x3 = eta12;
                double e2y = cy + (e1x2 * sinTheta * cosEta2) + (b * cosTheta * sinEta2);
                double cosTheta2 = cosTheta;
                double ep2x = (((-e1x2) * cosTheta2) * sinEta2) - ((b * sinTheta) * cosEta2);
                double ep2y = ((-e1x2) * sinTheta * sinEta2) + (b * cosTheta2 * cosEta2);
                double tanDiff2 = Math.tan((eta2 - eta1) / 2.0d);
                double alpha = (Math.sin(eta2 - eta1) * (Math.sqrt(((tanDiff2 * 3.0d) * tanDiff2) + 4.0d) - 1.0d)) / 3.0d;
                double q1x = e1x3 + (alpha * ep1x3);
                double q2x = anglePerSegment3 - (alpha * ep2x);
                double q2y = e2y - (alpha * ep2y);
                p.rLineTo(0.0f, 0.0f);
                p.cubicTo((float) q1x, (float) (e1y2 + (alpha * ep1y2)), (float) q2x, (float) q2y, (float) anglePerSegment3, (float) e2y);
                eta1 = eta2;
                e1y2 = e2y;
                ep1x3 = ep2x;
                ep1y2 = ep2y;
                i++;
                eta12 = anglePerSegment3;
                numSegments = numSegments2;
                cosTheta = cosTheta2;
                anglePerSegment = anglePerSegment2;
                sinEta1 = sinEta1;
                sinTheta = sinTheta;
                cosEta1 = cosEta1;
                e1x2 = a;
            }
        }
    }

    private PathParser() {
    }
}
