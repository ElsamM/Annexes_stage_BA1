package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* JADX INFO: compiled from: UnsignedUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class UnsignedKt {
    public static final int uintCompare(int v1, int v2) {
        return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, Integer.MIN_VALUE ^ v2);
    }

    public static final int ulongCompare(long v1, long v2) {
        return Intrinsics.compare(v1 ^ Long.MIN_VALUE, Long.MIN_VALUE ^ v2);
    }

    /* JADX INFO: renamed from: uintDivide-J1ME1BU, reason: not valid java name */
    public static final int m481uintDivideJ1ME1BU(int v1, int v2) {
        return UInt.m225constructorimpl((int) ((((long) v1) & 4294967295L) / (4294967295L & ((long) v2))));
    }

    /* JADX INFO: renamed from: uintRemainder-J1ME1BU, reason: not valid java name */
    public static final int m482uintRemainderJ1ME1BU(int v1, int v2) {
        return UInt.m225constructorimpl((int) ((((long) v1) & 4294967295L) % (4294967295L & ((long) v2))));
    }

    /* JADX INFO: renamed from: ulongDivide-eb3DHEI, reason: not valid java name */
    public static final long m483ulongDivideeb3DHEI(long v1, long v2) {
        if (v2 < 0) {
            return ULong.m304constructorimpl(Long.compare(v1 ^ Long.MIN_VALUE, v2 ^ Long.MIN_VALUE) >= 0 ? 1L : 0L);
        }
        if (v1 >= 0) {
            return ULong.m304constructorimpl(v1 / v2);
        }
        long quotient = ((v1 >>> 1) / v2) << 1;
        long rem = v1 - (quotient * v2);
        return ULong.m304constructorimpl(((long) (Long.compare(ULong.m304constructorimpl(rem) ^ Long.MIN_VALUE, ULong.m304constructorimpl(v2) ^ Long.MIN_VALUE) < 0 ? 0 : 1)) + quotient);
    }

    /* JADX INFO: renamed from: ulongRemainder-eb3DHEI, reason: not valid java name */
    public static final long m484ulongRemaindereb3DHEI(long v1, long v2) {
        long j = 0;
        if (v2 < 0) {
            if (Long.compare(v1 ^ Long.MIN_VALUE, v2 ^ Long.MIN_VALUE) < 0) {
                return v1;
            }
            return ULong.m304constructorimpl(v1 - v2);
        }
        if (v1 >= 0) {
            return ULong.m304constructorimpl(v1 % v2);
        }
        long quotient = ((v1 >>> 1) / v2) << 1;
        long rem = v1 - (quotient * v2);
        if (Long.compare(ULong.m304constructorimpl(rem) ^ Long.MIN_VALUE, ULong.m304constructorimpl(v2) ^ Long.MIN_VALUE) >= 0) {
            j = v2;
        }
        return ULong.m304constructorimpl(rem - j);
    }

    public static final int doubleToUInt(double v) {
        if (Double.isNaN(v) || v <= uintToDouble(0)) {
            return 0;
        }
        if (v >= uintToDouble(-1)) {
            return -1;
        }
        if (v <= 2.147483647E9d) {
            return UInt.m225constructorimpl((int) v);
        }
        double d = Integer.MAX_VALUE;
        Double.isNaN(d);
        return UInt.m225constructorimpl(UInt.m225constructorimpl((int) (v - d)) + UInt.m225constructorimpl(Integer.MAX_VALUE));
    }

    public static final long doubleToULong(double v) {
        if (Double.isNaN(v) || v <= ulongToDouble(0L)) {
            return 0L;
        }
        if (v >= ulongToDouble(-1L)) {
            return -1L;
        }
        return v < 9.223372036854776E18d ? ULong.m304constructorimpl((long) v) : ULong.m304constructorimpl(ULong.m304constructorimpl((long) (v - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    public static final double uintToDouble(int v) {
        double d = Integer.MAX_VALUE & v;
        double d2 = (v >>> 31) << 30;
        double d3 = 2;
        Double.isNaN(d2);
        Double.isNaN(d3);
        Double.isNaN(d);
        return d + (d2 * d3);
    }

    public static final double ulongToDouble(long v) {
        double d = v >>> 11;
        double d2 = 2048;
        Double.isNaN(d);
        Double.isNaN(d2);
        double d3 = d * d2;
        double d4 = 2047 & v;
        Double.isNaN(d4);
        return d3 + d4;
    }

    public static final String ulongToString(long v) {
        return ulongToString(v, 10);
    }

    public static final String ulongToString(long v, int base) {
        if (v >= 0) {
            String string = Long.toString(v, CharsKt.checkRadix(base));
            Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
            return string;
        }
        long quotient = ((v >>> 1) / ((long) base)) << 1;
        long rem = v - (((long) base) * quotient);
        if (rem >= base) {
            rem -= (long) base;
            quotient++;
        }
        StringBuilder sb = new StringBuilder();
        String string2 = Long.toString(quotient, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(string2, "toString(this, checkRadix(radix))");
        StringBuilder sbAppend = sb.append(string2);
        String string3 = Long.toString(rem, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(string3, "toString(this, checkRadix(radix))");
        return sbAppend.append(string3).toString();
    }
}
