package com.budiyev.android.codescanner;

import android.graphics.Bitmap;
import androidx.core.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class BarcodeUtils {
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 180;
    public static final int ROTATION_270 = 270;
    public static final int ROTATION_90 = 90;

    /* JADX INFO: loaded from: classes.dex */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    private BarcodeUtils() {
    }

    public static Result decodeBitmap(Bitmap bitmap) {
        return decodeBitmap(bitmap, null);
    }

    public static Result decodeBitmap(Bitmap bitmap, Map<DecodeHintType, ?> hints) {
        Objects.requireNonNull(bitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        return decodeRgb(pixels, width, height, hints);
    }

    public static Result decodeRgb(int[] pixels, int width, int height) {
        return decodeRgb(pixels, width, height, null);
    }

    public static Result decodeRgb(int[] pixels, int width, int height, Map<DecodeHintType, ?> hints) {
        Objects.requireNonNull(pixels);
        MultiFormatReader reader = createReader(hints);
        try {
            return Utils.decodeLuminanceSource(reader, new RGBLuminanceSource(width, height, pixels));
        } catch (ReaderException e) {
            return null;
        }
    }

    public static Result decodeYuv(byte[] pixels, int width, int height) {
        return decodeYuv(pixels, width, height, 0, false, null);
    }

    public static Result decodeYuv(byte[] pixels, int width, int height, int rotation, boolean reverseHorizontal, Map<DecodeHintType, ?> hints) {
        int rotatedHeight;
        int rotatedWidth;
        Objects.requireNonNull(pixels);
        byte[] rotatedPixels = Utils.rotateYuv(pixels, width, height, rotation);
        if (rotation == 90 || rotation == 270) {
            rotatedHeight = width;
            rotatedWidth = height;
        } else {
            rotatedHeight = height;
            rotatedWidth = width;
        }
        MultiFormatReader reader = createReader(hints);
        try {
            try {
                return Utils.decodeLuminanceSource(reader, new PlanarYUVLuminanceSource(rotatedPixels, rotatedWidth, rotatedHeight, 0, 0, rotatedWidth, rotatedHeight, reverseHorizontal));
            } catch (ReaderException e) {
                return null;
            }
        } catch (ReaderException e2) {
            return null;
        }
    }

    public static BitMatrix encodeBitMatrix(String content, BarcodeFormat format, int width, int height) {
        return encodeBitMatrix(content, format, width, height, null);
    }

    public static BitMatrix encodeBitMatrix(String content, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(format);
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            if (hints != null) {
                return writer.encode(content, format, width, height, hints);
            }
            return writer.encode(content, format, width, height);
        } catch (WriterException e) {
            return null;
        }
    }

    public static Bitmap encodeBitmap(String content, BarcodeFormat format, int width, int height) {
        return encodeBitmap(content, format, width, height, null);
    }

    public static Bitmap encodeBitmap(String content, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        BitMatrix matrix = encodeBitMatrix(content, format, width, height, hints);
        if (matrix != null) {
            return createBitmap(matrix);
        }
        return null;
    }

    public static Bitmap createBitmap(BitMatrix matrix) {
        Objects.requireNonNull(matrix);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int length = width * height;
        int[] pixels = new int[length];
        for (int i = 0; i < length; i++) {
            pixels[i] = matrix.get(i % width, i / height) ? ViewCompat.MEASURED_STATE_MASK : -1;
        }
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

    private static MultiFormatReader createReader(Map<DecodeHintType, ?> hints) {
        MultiFormatReader reader = new MultiFormatReader();
        if (hints != null) {
            reader.setHints(hints);
        } else {
            reader.setHints(Collections.singletonMap(DecodeHintType.POSSIBLE_FORMATS, CodeScanner.ALL_FORMATS));
        }
        return reader;
    }
}
