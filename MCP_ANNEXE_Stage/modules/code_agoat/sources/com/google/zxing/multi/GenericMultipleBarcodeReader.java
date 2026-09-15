package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader delegate) {
        this.delegate = delegate;
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
        return decodeMultiple(image, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
        List<Result> results = new ArrayList<>();
        doDecodeMultiple(image, hints, results, 0, 0, 0);
        if (results.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (Result[]) results.toArray(new Result[results.size()]);
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        boolean z;
        if (i3 <= 4) {
            try {
                Result resultDecode = this.delegate.decode(binaryBitmap, map);
                Iterator<Result> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (it.next().getText().equals(resultDecode.getText())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    list.add(translateResultPoints(resultDecode, i, i2));
                }
                ResultPoint[] resultPoints = resultDecode.getResultPoints();
                if (resultPoints == null || resultPoints.length == 0) {
                    return;
                }
                int width = binaryBitmap.getWidth();
                int height = binaryBitmap.getHeight();
                float f = width;
                float f2 = height;
                float f3 = 0.0f;
                float f4 = 0.0f;
                for (ResultPoint resultPoint : resultPoints) {
                    if (resultPoint != null) {
                        float x = resultPoint.getX();
                        float y = resultPoint.getY();
                        if (x < f) {
                            f = x;
                        }
                        if (y < f2) {
                            f2 = y;
                        }
                        if (x > f3) {
                            f3 = x;
                        }
                        if (y > f4) {
                            f4 = y;
                        }
                    }
                }
                if (f > 100.0f) {
                    doDecodeMultiple(binaryBitmap.crop(0, 0, (int) f, height), map, list, i, i2, i3 + 1);
                }
                if (f2 > 100.0f) {
                    doDecodeMultiple(binaryBitmap.crop(0, 0, width, (int) f2), map, list, i, i2, i3 + 1);
                }
                if (f3 < width - 100) {
                    int i4 = (int) f3;
                    doDecodeMultiple(binaryBitmap.crop(i4, 0, width - i4, height), map, list, i + i4, i2, i3 + 1);
                }
                if (f4 < height - 100) {
                    int i5 = (int) f4;
                    doDecodeMultiple(binaryBitmap.crop(0, i5, width, height - i5), map, list, i, i2 + i5, i3 + 1);
                }
            } catch (ReaderException e) {
            }
        }
    }

    private static Result translateResultPoints(Result result, int xOffset, int yOffset) {
        ResultPoint[] oldResultPoints = result.getResultPoints();
        if (oldResultPoints == null) {
            return result;
        }
        ResultPoint[] newResultPoints = new ResultPoint[oldResultPoints.length];
        for (int i = 0; i < oldResultPoints.length; i++) {
            ResultPoint oldPoint = oldResultPoints[i];
            if (oldPoint != null) {
                newResultPoints[i] = new ResultPoint(oldPoint.getX() + xOffset, oldPoint.getY() + yOffset);
            }
        }
        Result newResult = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), newResultPoints, result.getBarcodeFormat(), result.getTimestamp());
        newResult.putAllMetadata(result.getResultMetadata());
        return newResult;
    }
}
