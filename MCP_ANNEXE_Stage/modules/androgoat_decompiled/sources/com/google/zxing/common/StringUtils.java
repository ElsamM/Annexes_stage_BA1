package com.google.zxing.common;

import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes2.dex */
public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        ASSUME_SHIFT_JIS = SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING);
    }

    private StringUtils() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:112:0x0123, code lost:
    
        if (r15 < 3) goto L115;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0085 A[PHI: r6 r20
  0x0085: PHI (r6v6 'utf8BytesLeft' int) = (r6v1 'utf8BytesLeft' int), (r6v5 'utf8BytesLeft' int), (r6v1 'utf8BytesLeft' int) binds: [B:31:0x0068, B:39:0x0080, B:26:0x005b] A[DONT_GENERATE, DONT_INLINE]
  0x0085: PHI (r20v8 'i' int) = (r20v7 'i' int), (r20v7 'i' int), (r20v9 'i' int) binds: [B:31:0x0068, B:39:0x0080, B:26:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String guessEncoding(byte[] r21, java.util.Map<com.google.zxing.DecodeHintType, ?> r22) {
        /*
            Method dump skipped, instruction units count: 329
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.StringUtils.guessEncoding(byte[], java.util.Map):java.lang.String");
    }
}
