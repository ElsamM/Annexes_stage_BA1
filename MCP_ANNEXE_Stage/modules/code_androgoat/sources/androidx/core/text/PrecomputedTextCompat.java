package androidx.core.text;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes2.dex */
public class PrecomputedTextCompat implements Spannable {
    private static final char LINE_FEED = '\n';
    private final int[] mParagraphEnds;
    private final Params mParams;
    private final Spannable mText;
    private final PrecomputedText mWrapped;
    private static final Object sLock = new Object();
    private static Executor sExecutor = null;

    public static final class Params {
        private final int mBreakStrategy;
        private final int mHyphenationFrequency;
        private final TextPaint mPaint;
        private final TextDirectionHeuristic mTextDir;
        final PrecomputedText.Params mWrapped;

        public static class Builder {
            private int mBreakStrategy;
            private int mHyphenationFrequency;
            private final TextPaint mPaint;
            private TextDirectionHeuristic mTextDir;

            public Builder(TextPaint paint) {
                this.mPaint = paint;
                if (Build.VERSION.SDK_INT >= 23) {
                    this.mBreakStrategy = 1;
                    this.mHyphenationFrequency = 1;
                } else {
                    this.mHyphenationFrequency = 0;
                    this.mBreakStrategy = 0;
                }
                this.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }

            public Builder setBreakStrategy(int strategy) {
                this.mBreakStrategy = strategy;
                return this;
            }

            public Builder setHyphenationFrequency(int frequency) {
                this.mHyphenationFrequency = frequency;
                return this;
            }

            public Builder setTextDirection(TextDirectionHeuristic textDir) {
                this.mTextDir = textDir;
                return this;
            }

            public Params build() {
                return new Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
            }
        }

        Params(TextPaint paint, TextDirectionHeuristic textDir, int strategy, int frequency) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mWrapped = new PrecomputedText.Params.Builder(paint).setBreakStrategy(strategy).setHyphenationFrequency(frequency).setTextDirection(textDir).build();
            } else {
                this.mWrapped = null;
            }
            this.mPaint = paint;
            this.mTextDir = textDir;
            this.mBreakStrategy = strategy;
            this.mHyphenationFrequency = frequency;
        }

        public Params(PrecomputedText.Params wrapped) {
            this.mPaint = wrapped.getTextPaint();
            this.mTextDir = wrapped.getTextDirection();
            this.mBreakStrategy = wrapped.getBreakStrategy();
            this.mHyphenationFrequency = wrapped.getHyphenationFrequency();
            this.mWrapped = Build.VERSION.SDK_INT >= 29 ? wrapped : null;
        }

        public TextPaint getTextPaint() {
            return this.mPaint;
        }

        public TextDirectionHeuristic getTextDirection() {
            return this.mTextDir;
        }

        public int getBreakStrategy() {
            return this.mBreakStrategy;
        }

        public int getHyphenationFrequency() {
            return this.mHyphenationFrequency;
        }

        public boolean equalsWithoutTextDirection(Params other) {
            if ((Build.VERSION.SDK_INT >= 23 && (this.mBreakStrategy != other.getBreakStrategy() || this.mHyphenationFrequency != other.getHyphenationFrequency())) || this.mPaint.getTextSize() != other.getTextPaint().getTextSize() || this.mPaint.getTextScaleX() != other.getTextPaint().getTextScaleX() || this.mPaint.getTextSkewX() != other.getTextPaint().getTextSkewX()) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 21 && (this.mPaint.getLetterSpacing() != other.getTextPaint().getLetterSpacing() || !TextUtils.equals(this.mPaint.getFontFeatureSettings(), other.getTextPaint().getFontFeatureSettings()))) || this.mPaint.getFlags() != other.getTextPaint().getFlags()) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                if (!this.mPaint.getTextLocales().equals(other.getTextPaint().getTextLocales())) {
                    return false;
                }
            } else if (!this.mPaint.getTextLocale().equals(other.getTextPaint().getTextLocale())) {
                return false;
            }
            return this.mPaint.getTypeface() == null ? other.getTextPaint().getTypeface() == null : this.mPaint.getTypeface().equals(other.getTextPaint().getTypeface());
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Params)) {
                return false;
            }
            Params other = (Params) o;
            return equalsWithoutTextDirection(other) && this.mTextDir == other.getTextDirection();
        }

        public int hashCode() {
            if (Build.VERSION.SDK_INT >= 24) {
                return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency));
            }
            if (Build.VERSION.SDK_INT >= 21) {
                return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency));
            }
            return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocale(), this.mPaint.getTypeface(), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("textSize=" + this.mPaint.getTextSize());
            sb.append(", textScaleX=" + this.mPaint.getTextScaleX());
            sb.append(", textSkewX=" + this.mPaint.getTextSkewX());
            if (Build.VERSION.SDK_INT >= 21) {
                sb.append(", letterSpacing=" + this.mPaint.getLetterSpacing());
                sb.append(", elegantTextHeight=" + this.mPaint.isElegantTextHeight());
            }
            if (Build.VERSION.SDK_INT >= 24) {
                sb.append(", textLocale=" + this.mPaint.getTextLocales());
            } else {
                sb.append(", textLocale=" + this.mPaint.getTextLocale());
            }
            sb.append(", typeface=" + this.mPaint.getTypeface());
            if (Build.VERSION.SDK_INT >= 26) {
                sb.append(", variationSettings=" + this.mPaint.getFontVariationSettings());
            }
            sb.append(", textDir=" + this.mTextDir);
            sb.append(", breakStrategy=" + this.mBreakStrategy);
            sb.append(", hyphenationFrequency=" + this.mHyphenationFrequency);
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0058 A[Catch: all -> 0x0024, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0024, blocks: (B:6:0x0011, B:8:0x0015, B:17:0x0037, B:21:0x0042, B:20:0x003f, B:26:0x0058, B:31:0x006d), top: B:51:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006d A[Catch: all -> 0x0024, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0024, blocks: (B:6:0x0011, B:8:0x0015, B:17:0x0037, B:21:0x0042, B:20:0x003f, B:26:0x0058, B:31:0x006d), top: B:51:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009a A[Catch: all -> 0x00c0, TRY_ENTER, TryCatch #0 {all -> 0x00c0, blocks: (B:3:0x0006, B:13:0x0028, B:23:0x004b, B:24:0x0052, B:28:0x0067, B:33:0x009a, B:35:0x00a0), top: B:49:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static androidx.core.text.PrecomputedTextCompat create(java.lang.CharSequence r14, androidx.core.text.PrecomputedTextCompat.Params r15) throws java.lang.Throwable {
        /*
            androidx.core.util.Preconditions.checkNotNull(r14)
            androidx.core.util.Preconditions.checkNotNull(r15)
            java.lang.String r0 = "PrecomputedText"
            androidx.core.os.TraceCompat.beginSection(r0)     // Catch: java.lang.Throwable -> Lc0
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> Lc0
            r1 = 29
            if (r0 < r1) goto L28
            android.text.PrecomputedText$Params r0 = r15.mWrapped     // Catch: java.lang.Throwable -> L24
            if (r0 == 0) goto L28
            androidx.core.text.PrecomputedTextCompat r0 = new androidx.core.text.PrecomputedTextCompat     // Catch: java.lang.Throwable -> L24
            android.text.PrecomputedText$Params r1 = r15.mWrapped     // Catch: java.lang.Throwable -> L24
            android.text.PrecomputedText r1 = android.text.PrecomputedText.create(r14, r1)     // Catch: java.lang.Throwable -> L24
            r0.<init>(r1, r15)     // Catch: java.lang.Throwable -> L24
            androidx.core.os.TraceCompat.endSection()
            return r0
        L24:
            r0 = move-exception
            r7 = r14
            goto Lc2
        L28:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lc0
            r0.<init>()     // Catch: java.lang.Throwable -> Lc0
            r1 = 0
            int r2 = r14.length()     // Catch: java.lang.Throwable -> Lc0
            r3 = 0
        L33:
            if (r3 >= r2) goto L4b
            r4 = 10
            int r4 = android.text.TextUtils.indexOf(r14, r4, r3, r2)     // Catch: java.lang.Throwable -> L24
            if (r4 >= 0) goto L3f
            r1 = r2
            goto L42
        L3f:
            int r4 = r4 + 1
            r1 = r4
        L42:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> L24
            r0.add(r4)     // Catch: java.lang.Throwable -> L24
            r3 = r1
            goto L33
        L4b:
            int r3 = r0.size()     // Catch: java.lang.Throwable -> Lc0
            int[] r3 = new int[r3]     // Catch: java.lang.Throwable -> Lc0
            r4 = 0
        L52:
            int r5 = r0.size()     // Catch: java.lang.Throwable -> Lc0
            if (r4 >= r5) goto L67
            java.lang.Object r5 = r0.get(r4)     // Catch: java.lang.Throwable -> L24
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.lang.Throwable -> L24
            int r5 = r5.intValue()     // Catch: java.lang.Throwable -> L24
            r3[r4] = r5     // Catch: java.lang.Throwable -> L24
            int r4 = r4 + 1
            goto L52
        L67:
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> Lc0
            r5 = 23
            if (r4 < r5) goto L9a
            int r4 = r14.length()     // Catch: java.lang.Throwable -> L24
            android.text.TextPaint r5 = r15.getTextPaint()     // Catch: java.lang.Throwable -> L24
            r6 = 2147483647(0x7fffffff, float:NaN)
            r7 = 0
            android.text.StaticLayout$Builder r4 = android.text.StaticLayout.Builder.obtain(r14, r7, r4, r5, r6)     // Catch: java.lang.Throwable -> L24
            int r5 = r15.getBreakStrategy()     // Catch: java.lang.Throwable -> L24
            android.text.StaticLayout$Builder r4 = r4.setBreakStrategy(r5)     // Catch: java.lang.Throwable -> L24
            int r5 = r15.getHyphenationFrequency()     // Catch: java.lang.Throwable -> L24
            android.text.StaticLayout$Builder r4 = r4.setHyphenationFrequency(r5)     // Catch: java.lang.Throwable -> L24
            android.text.TextDirectionHeuristic r5 = r15.getTextDirection()     // Catch: java.lang.Throwable -> L24
            android.text.StaticLayout$Builder r4 = r4.setTextDirection(r5)     // Catch: java.lang.Throwable -> L24
            r4.build()     // Catch: java.lang.Throwable -> L24
            r7 = r14
            goto Lb5
        L9a:
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> Lc0
            r5 = 21
            if (r4 < r5) goto Lb4
            android.text.StaticLayout r6 = new android.text.StaticLayout     // Catch: java.lang.Throwable -> Lc0
            android.text.TextPaint r8 = r15.getTextPaint()     // Catch: java.lang.Throwable -> Lc0
            android.text.Layout$Alignment r10 = android.text.Layout.Alignment.ALIGN_NORMAL     // Catch: java.lang.Throwable -> Lc0
            r12 = 0
            r13 = 0
            r9 = 2147483647(0x7fffffff, float:NaN)
            r11 = 1065353216(0x3f800000, float:1.0)
            r7 = r14
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> Lbe
            goto Lb5
        Lb4:
            r7 = r14
        Lb5:
            androidx.core.text.PrecomputedTextCompat r14 = new androidx.core.text.PrecomputedTextCompat     // Catch: java.lang.Throwable -> Lbe
            r14.<init>(r7, r15, r3)     // Catch: java.lang.Throwable -> Lbe
            androidx.core.os.TraceCompat.endSection()
            return r14
        Lbe:
            r0 = move-exception
            goto Lc2
        Lc0:
            r0 = move-exception
            r7 = r14
        Lc2:
            androidx.core.os.TraceCompat.endSection()
            goto Lc7
        Lc6:
            throw r0
        Lc7:
            goto Lc6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.text.PrecomputedTextCompat.create(java.lang.CharSequence, androidx.core.text.PrecomputedTextCompat$Params):androidx.core.text.PrecomputedTextCompat");
    }

    private PrecomputedTextCompat(CharSequence text, Params params, int[] paraEnds) {
        this.mText = new SpannableString(text);
        this.mParams = params;
        this.mParagraphEnds = paraEnds;
        this.mWrapped = null;
    }

    private PrecomputedTextCompat(PrecomputedText precomputed, Params params) {
        this.mText = precomputed;
        this.mParams = params;
        this.mParagraphEnds = null;
        this.mWrapped = Build.VERSION.SDK_INT >= 29 ? precomputed : null;
    }

    public PrecomputedText getPrecomputedText() {
        if (this.mText instanceof PrecomputedText) {
            return (PrecomputedText) this.mText;
        }
        return null;
    }

    public Params getParams() {
        return this.mParams;
    }

    public int getParagraphCount() {
        if (Build.VERSION.SDK_INT >= 29) {
            return this.mWrapped.getParagraphCount();
        }
        return this.mParagraphEnds.length;
    }

    public int getParagraphStart(int paraIndex) {
        Preconditions.checkArgumentInRange(paraIndex, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT >= 29) {
            return this.mWrapped.getParagraphStart(paraIndex);
        }
        if (paraIndex == 0) {
            return 0;
        }
        return this.mParagraphEnds[paraIndex - 1];
    }

    public int getParagraphEnd(int paraIndex) {
        Preconditions.checkArgumentInRange(paraIndex, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT >= 29) {
            return this.mWrapped.getParagraphEnd(paraIndex);
        }
        return this.mParagraphEnds[paraIndex];
    }

    private static class PrecomputedTextFutureTask extends FutureTask<PrecomputedTextCompat> {

        private static class PrecomputedTextCallback implements Callable<PrecomputedTextCompat> {
            private Params mParams;
            private CharSequence mText;

            PrecomputedTextCallback(Params params, CharSequence cs) {
                this.mParams = params;
                this.mText = cs;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public PrecomputedTextCompat call() throws Exception {
                return PrecomputedTextCompat.create(this.mText, this.mParams);
            }
        }

        PrecomputedTextFutureTask(Params params, CharSequence text) {
            super(new PrecomputedTextCallback(params, text));
        }
    }

    public static Future<PrecomputedTextCompat> getTextFuture(CharSequence charSequence, Params params, Executor executor) {
        PrecomputedTextFutureTask task = new PrecomputedTextFutureTask(params, charSequence);
        if (executor == null) {
            synchronized (sLock) {
                if (sExecutor == null) {
                    sExecutor = Executors.newFixedThreadPool(1);
                }
                executor = sExecutor;
            }
        }
        executor.execute(task);
        return task;
    }

    @Override // android.text.Spannable
    public void setSpan(Object what, int start, int end, int flags) {
        if (what instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mWrapped.setSpan(what, start, end, flags);
        } else {
            this.mText.setSpan(what, start, end, flags);
        }
    }

    @Override // android.text.Spannable
    public void removeSpan(Object what) {
        if (what instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mWrapped.removeSpan(what);
        } else {
            this.mText.removeSpan(what);
        }
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        if (Build.VERSION.SDK_INT >= 29) {
            return (T[]) this.mWrapped.getSpans(i, i2, cls);
        }
        return (T[]) this.mText.getSpans(i, i2, cls);
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object tag) {
        return this.mText.getSpanStart(tag);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object tag) {
        return this.mText.getSpanEnd(tag);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object tag) {
        return this.mText.getSpanFlags(tag);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int start, int limit, Class type) {
        return this.mText.nextSpanTransition(start, limit, type);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int index) {
        return this.mText.charAt(index);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return this.mText.subSequence(start, end);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.mText.toString();
    }
}
