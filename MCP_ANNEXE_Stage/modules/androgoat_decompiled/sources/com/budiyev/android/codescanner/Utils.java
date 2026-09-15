package com.budiyev.android.codescanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.view.WindowManager;
import androidx.core.app.NotificationManagerCompat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.UByte;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes2.dex */
final class Utils {
    private static final float DISTORTION_STEP = 0.1f;
    private static final float MAX_DISTORTION = 3.0f;
    private static final int MAX_FPS = 30000;
    private static final float MIN_DISTORTION = 0.3f;
    private static final int MIN_FPS = 10000;
    private static final int MIN_PREVIEW_PIXELS = 589824;

    private Utils() {
    }

    public static Point findSuitableImageSize(Camera.Parameters parameters, int frameWidth, int frameHeight) {
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        if (sizes != null && !sizes.isEmpty()) {
            Collections.sort(sizes, new CameraSizeComparator());
            float frameRatio = frameWidth / frameHeight;
            for (float distortion = MIN_DISTORTION; distortion <= MAX_DISTORTION; distortion += 0.1f) {
                for (Camera.Size size : sizes) {
                    int width = size.width;
                    int height = size.height;
                    if (width * height >= MIN_PREVIEW_PIXELS && Math.abs(frameRatio - (width / height)) <= distortion) {
                        return new Point(width, height);
                    }
                }
            }
        }
        Camera.Size defaultSize = parameters.getPreviewSize();
        if (defaultSize == null) {
            throw new CodeScannerException("Unable to configure camera preview size");
        }
        return new Point(defaultSize.width, defaultSize.height);
    }

    public static void configureFpsRange(Camera.Parameters parameters) {
        List<int[]> supportedFpsRanges = parameters.getSupportedPreviewFpsRange();
        if (supportedFpsRanges == null || supportedFpsRanges.isEmpty()) {
            return;
        }
        Collections.sort(supportedFpsRanges, new FpsRangeComparator());
        for (int[] fpsRange : supportedFpsRanges) {
            if (fpsRange[0] >= MIN_FPS && fpsRange[1] <= MAX_FPS) {
                parameters.setPreviewFpsRange(fpsRange[0], fpsRange[1]);
                return;
            }
        }
    }

    public static void configureSceneMode(Camera.Parameters parameters) {
        List<String> supportedSceneModes;
        if (!"barcode".equals(parameters.getSceneMode()) && (supportedSceneModes = parameters.getSupportedSceneModes()) != null && supportedSceneModes.contains("barcode")) {
            parameters.setSceneMode("barcode");
        }
    }

    public static void configureVideoStabilization(Camera.Parameters parameters) {
        if (parameters.isVideoStabilizationSupported() && !parameters.getVideoStabilization()) {
            parameters.setVideoStabilization(true);
        }
    }

    public static void configureFocusArea(Camera.Parameters parameters, Rect area, int width, int height, int orientation) {
        List<Camera.Area> areas = new ArrayList<>(1);
        Rect rotatedArea = area.rotate(-orientation, width / 2.0f, height / 2.0f).bound(0, 0, width, height);
        areas.add(new Camera.Area(new android.graphics.Rect(mapCoordinate(rotatedArea.getLeft(), width), mapCoordinate(rotatedArea.getTop(), height), mapCoordinate(rotatedArea.getRight(), width), mapCoordinate(rotatedArea.getBottom(), height)), 1000));
        if (parameters.getMaxNumFocusAreas() > 0) {
            parameters.setFocusAreas(areas);
        }
        if (parameters.getMaxNumMeteringAreas() > 0) {
            parameters.setMeteringAreas(areas);
        }
    }

    public static void configureDefaultFocusArea(Camera.Parameters parameters, Rect frameRect, Point previewSize, Point viewSize, int width, int height, int orientation) {
        boolean portrait = isPortrait(orientation);
        int rotatedWidth = portrait ? height : width;
        int rotatedHeight = portrait ? width : height;
        configureFocusArea(parameters, getImageFrameRect(rotatedWidth, rotatedHeight, frameRect, previewSize, viewSize), rotatedWidth, rotatedHeight, orientation);
    }

    public static void configureDefaultFocusArea(Camera.Parameters parameters, DecoderWrapper decoderWrapper, Rect frameRect) {
        Point imageSize = decoderWrapper.getImageSize();
        configureDefaultFocusArea(parameters, frameRect, decoderWrapper.getPreviewSize(), decoderWrapper.getViewSize(), imageSize.getX(), imageSize.getY(), decoderWrapper.getDisplayOrientation());
    }

    public static void configureFocusModeForTouch(Camera.Parameters parameters) {
        List<String> focusModes;
        if (!DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(parameters.getFocusMode()) && (focusModes = parameters.getSupportedFocusModes()) != null && focusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO)) {
            parameters.setFocusMode(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        }
    }

    public static void disableAutoFocus(Camera.Parameters parameters) {
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes == null || focusModes.isEmpty()) {
            return;
        }
        String focusMode = parameters.getFocusMode();
        if (focusModes.contains("fixed")) {
            if ("fixed".equals(focusMode)) {
                return;
            }
            parameters.setFocusMode("fixed");
        } else if (focusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO) && !DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(focusMode)) {
            parameters.setFocusMode(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        }
    }

    public static void setAutoFocusMode(Camera.Parameters parameters, AutoFocusMode autoFocusMode) {
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes == null || focusModes.isEmpty()) {
            return;
        }
        if (autoFocusMode == AutoFocusMode.CONTINUOUS) {
            if ("continuous-picture".equals(parameters.getFocusMode())) {
                return;
            }
            if (focusModes.contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
                return;
            }
        }
        if (!DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(parameters.getFocusMode()) && focusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO)) {
            parameters.setFocusMode(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        }
    }

    public static void setFlashMode(Camera.Parameters parameters, String flashMode) {
        List<String> flashModes;
        if (!flashMode.equals(parameters.getFlashMode()) && (flashModes = parameters.getSupportedFlashModes()) != null && flashModes.contains(flashMode)) {
            parameters.setFlashMode(flashMode);
        }
    }

    public static void setZoom(Camera.Parameters parameters, int zoom) {
        if (parameters.isZoomSupported() && parameters.getZoom() != zoom) {
            int maxZoom = parameters.getMaxZoom();
            parameters.setZoom(Math.min(zoom, maxZoom));
        }
    }

    public static int getDisplayOrientation(Context context, Camera.CameraInfo cameraInfo) {
        int degrees;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            throw new CodeScannerException("Unable to access window manager");
        }
        int rotation = windowManager.getDefaultDisplay().getRotation();
        switch (rotation) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = BarcodeUtils.ROTATION_180;
                break;
            case 3:
                degrees = BarcodeUtils.ROTATION_270;
                break;
            default:
                if (rotation % 90 == 0) {
                    degrees = (rotation + 360) % 360;
                } else {
                    throw new CodeScannerException("Invalid display rotation");
                }
                break;
        }
        return (((cameraInfo.facing == 1 ? BarcodeUtils.ROTATION_180 : 360) + cameraInfo.orientation) - degrees) % 360;
    }

    public static boolean isPortrait(int orientation) {
        return orientation == 90 || orientation == 270;
    }

    public static Point getPreviewSize(int imageWidth, int imageHeight, int frameWidth, int frameHeight) {
        if (imageWidth == frameWidth && imageHeight == frameHeight) {
            return new Point(frameWidth, frameHeight);
        }
        int resultWidth = (imageWidth * frameHeight) / imageHeight;
        if (resultWidth < frameWidth) {
            return new Point(frameWidth, (imageHeight * frameWidth) / imageWidth);
        }
        return new Point(resultWidth, frameHeight);
    }

    public static Rect getImageFrameRect(int imageWidth, int imageHeight, Rect viewFrameRect, Point previewSize, Point viewSize) {
        int previewWidth = previewSize.getX();
        int previewHeight = previewSize.getY();
        int viewWidth = viewSize.getX();
        int viewHeight = viewSize.getY();
        int wD = (previewWidth - viewWidth) / 2;
        int hD = (previewHeight - viewHeight) / 2;
        float wR = imageWidth / previewWidth;
        float hR = imageHeight / previewHeight;
        return new Rect(Math.max(Math.round((viewFrameRect.getLeft() + wD) * wR), 0), Math.max(Math.round((viewFrameRect.getTop() + hD) * hR), 0), Math.min(Math.round((viewFrameRect.getRight() + wD) * wR), imageWidth), Math.min(Math.round((viewFrameRect.getBottom() + hD) * hR), imageHeight));
    }

    public static byte[] rotateYuv(byte[] source, int width, int height, int rotation) {
        if (rotation == 0 || rotation == 360) {
            return source;
        }
        if (rotation % 90 != 0 || rotation < 0 || rotation > 270) {
            throw new IllegalArgumentException("Invalid rotation (valid: 0, 90, 180, 270)");
        }
        byte[] output = new byte[source.length];
        int frameSize = width * height;
        boolean swap = rotation % BarcodeUtils.ROTATION_180 != 0;
        boolean flipX = rotation % BarcodeUtils.ROTATION_270 != 0;
        boolean flipY = rotation >= 180;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int yIn = (j * width) + i;
                int uIn = ((j >> 1) * width) + frameSize + (i & (-2));
                int vIn = uIn + 1;
                int wOut = swap ? height : width;
                int hOut = swap ? width : height;
                int iSwapped = swap ? j : i;
                int jSwapped = swap ? i : j;
                int iOut = flipX ? (wOut - iSwapped) - 1 : iSwapped;
                int jOut = flipY ? (hOut - jSwapped) - 1 : jSwapped;
                int yOut = (jOut * wOut) + iOut;
                int uOut = frameSize + ((jOut >> 1) * wOut) + (iOut & (-2));
                int vOut = uOut + 1;
                output[yOut] = (byte) (source[yIn] & UByte.MAX_VALUE);
                output[uOut] = (byte) (source[uIn] & UByte.MAX_VALUE);
                output[vOut] = (byte) (source[vIn] & UByte.MAX_VALUE);
            }
        }
        return output;
    }

    public static Result decodeLuminanceSource(MultiFormatReader reader, LuminanceSource luminanceSource) throws ReaderException {
        try {
            return reader.decodeWithState(new BinaryBitmap(new HybridBinarizer(luminanceSource)));
        } catch (NotFoundException e) {
            return reader.decodeWithState(new BinaryBitmap(new HybridBinarizer(luminanceSource.invert())));
        } finally {
            reader.reset();
        }
    }

    public static final class SuppressErrorCallback implements ErrorCallback {
        @Override // com.budiyev.android.codescanner.ErrorCallback
        public void onError(Throwable thrown) {
        }
    }

    public static Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(resId);
        }
        return context.getResources().getDrawable(resId);
    }

    private static int mapCoordinate(int value, int size) {
        return ((value * 2000) / size) + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
    }

    private static final class CameraSizeComparator implements Comparator<Camera.Size> {
        private CameraSizeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Camera.Size a, Camera.Size b) {
            return Integer.compare(b.height * b.width, a.height * a.width);
        }
    }

    private static final class FpsRangeComparator implements Comparator<int[]> {
        private FpsRangeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(int[] a, int[] b) {
            int comparison = Integer.compare(b[1], a[1]);
            if (comparison == 0) {
                return Integer.compare(b[0], a[0]);
            }
            return comparison;
        }
    }
}
