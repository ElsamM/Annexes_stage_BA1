package com.google.zxing.oned;

import com.budiyev.android.codescanner.BarcodeUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public abstract class OneDReader implements Reader {
    public abstract Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException;

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
        return decode(image, null);
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
        try {
            return doDecode(image, hints);
        } catch (NotFoundException nfe) {
            if (!(hints != null && hints.containsKey(DecodeHintType.TRY_HARDER)) || !image.isRotateSupported()) {
                throw nfe;
            }
            BinaryBitmap rotatedImage = image.rotateCounterClockwise();
            Result result = doDecode(rotatedImage, hints);
            Map<ResultMetadataType, ?> metadata = result.getResultMetadata();
            int orientation = BarcodeUtils.ROTATION_270;
            if (metadata != null && metadata.containsKey(ResultMetadataType.ORIENTATION)) {
                orientation = (((Integer) metadata.get(ResultMetadataType.ORIENTATION)).intValue() + BarcodeUtils.ROTATION_270) % 360;
            }
            result.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(orientation));
            ResultPoint[] points = result.getResultPoints();
            if (points != null) {
                int height = rotatedImage.getHeight();
                for (int i = 0; i < points.length; i++) {
                    points[i] = new ResultPoint((height - points[i].getY()) - 1.0f, points[i].getX());
                }
            }
            return result;
        }
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }

    private Result doDecode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        int i;
        int i2;
        Result resultDecodeRow;
        int width = binaryBitmap.getWidth();
        int height = binaryBitmap.getHeight();
        BitArray bitArray = new BitArray(width);
        int i3 = 1;
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        int iMax = Math.max(1, height >> (z ? 8 : 5));
        if (z) {
            i = height;
        } else {
            i = 15;
        }
        int i4 = height / 2;
        BitArray blackRow = bitArray;
        Map<DecodeHintType, ?> map2 = map;
        int i5 = 0;
        while (i5 < i) {
            int i6 = i5 + 1;
            int i7 = i6 / 2;
            if (!((i5 & 1) == 0)) {
                i7 = -i7;
            }
            int i8 = (i7 * iMax) + i4;
            if (i8 < 0 || i8 >= height) {
                break;
            }
            try {
                blackRow = binaryBitmap.getBlackRow(i8, blackRow);
                int i9 = 0;
                while (i9 < 2) {
                    if (i9 == i3) {
                        blackRow.reverse();
                        if (map2 != null && map2.containsKey(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                            EnumMap enumMap = new EnumMap(DecodeHintType.class);
                            enumMap.putAll(map2);
                            enumMap.remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                            map2 = enumMap;
                        }
                    }
                    try {
                        resultDecodeRow = decodeRow(i8, blackRow, map2);
                    } catch (ReaderException e) {
                        i2 = width;
                    }
                    if (i9 != i3) {
                        return resultDecodeRow;
                    }
                    try {
                        resultDecodeRow.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(BarcodeUtils.ROTATION_180));
                        ResultPoint[] resultPoints = resultDecodeRow.getResultPoints();
                        if (resultPoints == null) {
                            return resultDecodeRow;
                        }
                        float f = width;
                        try {
                            i2 = width;
                            try {
                                resultPoints[0] = new ResultPoint((f - resultPoints[0].getX()) - 1.0f, resultPoints[0].getY());
                                resultPoints[1] = new ResultPoint((f - resultPoints[1].getX()) - 1.0f, resultPoints[1].getY());
                                return resultDecodeRow;
                            } catch (ReaderException e2) {
                            }
                        } catch (ReaderException e3) {
                            i2 = width;
                        }
                    } catch (ReaderException e4) {
                        i2 = width;
                    }
                    i9++;
                    width = i2;
                    i3 = 1;
                }
            } catch (NotFoundException e5) {
            }
            i5 = i6;
            width = width;
            i3 = 1;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected static void recordPattern(BitArray row, int start, int[] counters) throws NotFoundException {
        int numCounters = counters.length;
        Arrays.fill(counters, 0, numCounters, 0);
        int end = row.getSize();
        if (start >= end) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean isWhite = !row.get(start);
        int counterPosition = 0;
        int i = start;
        while (i < end) {
            if (row.get(i) != isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                counterPosition++;
                if (counterPosition == numCounters) {
                    break;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
            i++;
        }
        if (counterPosition != numCounters) {
            if (counterPosition != numCounters - 1 || i != end) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    protected static void recordPatternInReverse(BitArray row, int start, int[] counters) throws NotFoundException {
        int numTransitionsLeft = counters.length;
        boolean last = row.get(start);
        while (start > 0 && numTransitionsLeft >= 0) {
            start--;
            if (row.get(start) != last) {
                numTransitionsLeft--;
                last = !last;
            }
        }
        if (numTransitionsLeft >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(row, start + 1, counters);
    }

    protected static float patternMatchVariance(int[] counters, int[] pattern, float maxIndividualVariance) {
        int numCounters = counters.length;
        int total = 0;
        int patternLength = 0;
        for (int i = 0; i < numCounters; i++) {
            total += counters[i];
            patternLength += pattern[i];
        }
        if (total < patternLength) {
            return Float.POSITIVE_INFINITY;
        }
        float unitBarWidth = total / patternLength;
        float maxIndividualVariance2 = maxIndividualVariance * unitBarWidth;
        float totalVariance = 0.0f;
        for (int x = 0; x < numCounters; x++) {
            int counter = counters[x];
            float scaledPattern = pattern[x] * unitBarWidth;
            float f = ((float) counter) > scaledPattern ? counter - scaledPattern : scaledPattern - counter;
            float variance = f;
            if (f > maxIndividualVariance2) {
                return Float.POSITIVE_INFINITY;
            }
            totalVariance += variance;
        }
        return totalVariance / total;
    }
}
