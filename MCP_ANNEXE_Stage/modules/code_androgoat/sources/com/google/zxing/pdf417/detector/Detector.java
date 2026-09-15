package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    public static PDF417DetectorResult detect(BinaryBitmap image, Map<DecodeHintType, ?> hints, boolean multiple) throws NotFoundException {
        BitMatrix bitMatrix = image.getBlackMatrix();
        List<ResultPoint[]> listDetect = detect(multiple, bitMatrix);
        List<ResultPoint[]> barcodeCoordinates = listDetect;
        if (listDetect.isEmpty()) {
            BitMatrix bitMatrixM125clone = bitMatrix.m125clone();
            bitMatrix = bitMatrixM125clone;
            bitMatrixM125clone.rotate180();
            barcodeCoordinates = detect(multiple, bitMatrix);
        }
        return new PDF417DetectorResult(bitMatrix, barcodeCoordinates);
    }

    private static List<ResultPoint[]> detect(boolean multiple, BitMatrix bitMatrix) {
        List<ResultPoint[]> barcodeCoordinates = new ArrayList<>();
        int row = 0;
        int column = 0;
        boolean foundBarcodeInRow = false;
        while (row < bitMatrix.getHeight()) {
            ResultPoint[] vertices = findVertices(bitMatrix, row, column);
            if (vertices[0] == null && vertices[3] == null) {
                if (!foundBarcodeInRow) {
                    break;
                }
                foundBarcodeInRow = false;
                column = 0;
                for (ResultPoint[] barcodeCoordinate : barcodeCoordinates) {
                    if (barcodeCoordinate[1] != null) {
                        row = (int) Math.max(row, barcodeCoordinate[1].getY());
                    }
                    if (barcodeCoordinate[3] != null) {
                        row = Math.max(row, (int) barcodeCoordinate[3].getY());
                    }
                }
                row += 5;
            } else {
                foundBarcodeInRow = true;
                barcodeCoordinates.add(vertices);
                if (!multiple) {
                    break;
                }
                if (vertices[2] == null) {
                    column = (int) vertices[4].getX();
                    row = (int) vertices[4].getY();
                } else {
                    column = (int) vertices[2].getX();
                    row = (int) vertices[2].getY();
                }
            }
        }
        return barcodeCoordinates;
    }

    private static ResultPoint[] findVertices(BitMatrix matrix, int startRow, int startColumn) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        ResultPoint[] result = new ResultPoint[8];
        int startRow2 = startRow;
        int startColumn2 = startColumn;
        copyToResult(result, findRowsWithPattern(matrix, height, width, startRow2, startColumn2, START_PATTERN), INDEXES_START_PATTERN);
        if (result[4] != null) {
            int startColumn3 = (int) result[4].getX();
            startRow2 = (int) result[4].getY();
            startColumn2 = startColumn3;
        }
        copyToResult(result, findRowsWithPattern(matrix, height, width, startRow2, startColumn2, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return result;
    }

    private static void copyToResult(ResultPoint[] result, ResultPoint[] tmpResult, int[] destinationIndexes) {
        for (int i = 0; i < destinationIndexes.length; i++) {
            result[destinationIndexes[i]] = tmpResult[i];
        }
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int[] iArr) {
        boolean z;
        int i5;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = iArr;
        int[] iArr3 = new int[iArr2.length];
        int i6 = i3;
        while (true) {
            if (i6 >= i) {
                z = false;
                i5 = i6;
                break;
            }
            int[] iArrFindGuardPattern = findGuardPattern(bitMatrix, i4, i6, i2, false, iArr2, iArr3);
            if (iArrFindGuardPattern == null) {
                i6 += 5;
                iArr2 = iArr;
            } else {
                int[] iArr4 = iArrFindGuardPattern;
                while (true) {
                    if (i6 > 0) {
                        i6--;
                        int[] iArrFindGuardPattern2 = findGuardPattern(bitMatrix, i4, i6, i2, false, iArr, iArr3);
                        if (iArrFindGuardPattern2 != null) {
                            iArr4 = iArrFindGuardPattern2;
                        } else {
                            i6++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                float f = i6;
                resultPointArr[0] = new ResultPoint(iArr4[0], f);
                resultPointArr[1] = new ResultPoint(iArr4[1], f);
                z = true;
                i5 = i6;
            }
        }
        int i7 = i5 + 1;
        if (z) {
            int[] iArr5 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i8 = i7;
            int i9 = 0;
            while (i8 < i) {
                int[] iArrFindGuardPattern3 = findGuardPattern(bitMatrix, iArr5[0], i8, i2, false, iArr, iArr3);
                if (iArrFindGuardPattern3 != null && Math.abs(iArr5[0] - iArrFindGuardPattern3[0]) < 5 && Math.abs(iArr5[1] - iArrFindGuardPattern3[1]) < 5) {
                    iArr5 = iArrFindGuardPattern3;
                    i9 = 0;
                } else {
                    if (i9 > 25) {
                        break;
                    }
                    i9++;
                }
                i8++;
            }
            i7 = i8 - (i9 + 1);
            float f2 = i7;
            resultPointArr[2] = new ResultPoint(iArr5[0], f2);
            resultPointArr[3] = new ResultPoint(iArr5[1], f2);
        }
        if (i7 - i5 < 10) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    private static int[] findGuardPattern(BitMatrix matrix, int column, int row, int width, boolean whiteFirst, int[] pattern, int[] counters) {
        Arrays.fill(counters, 0, counters.length, 0);
        int patternStart = column;
        int pixelDrift = 0;
        while (matrix.get(patternStart, row) && patternStart > 0) {
            int pixelDrift2 = pixelDrift + 1;
            if (pixelDrift >= 3) {
                break;
            }
            patternStart--;
            pixelDrift = pixelDrift2;
        }
        int x = patternStart;
        int counterPosition = 0;
        int patternLength = pattern.length;
        boolean isWhite = whiteFirst;
        while (x < width) {
            if (matrix.get(x, row) != isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition != patternLength - 1) {
                    counterPosition++;
                } else if (patternMatchVariance(counters, pattern, MAX_INDIVIDUAL_VARIANCE) >= MAX_AVG_VARIANCE) {
                    patternStart += counters[0] + counters[1];
                    System.arraycopy(counters, 2, counters, 0, counterPosition - 1);
                    counters[counterPosition - 1] = 0;
                    counters[counterPosition] = 0;
                    counterPosition--;
                } else {
                    return new int[]{patternStart, x};
                }
                counters[counterPosition] = 1;
                isWhite = isWhite ? false : true;
            }
            x++;
        }
        if (counterPosition == patternLength - 1 && patternMatchVariance(counters, pattern, MAX_INDIVIDUAL_VARIANCE) < MAX_AVG_VARIANCE) {
            return new int[]{patternStart, x - 1};
        }
        return null;
    }

    private static float patternMatchVariance(int[] counters, int[] pattern, float maxIndividualVariance) {
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
