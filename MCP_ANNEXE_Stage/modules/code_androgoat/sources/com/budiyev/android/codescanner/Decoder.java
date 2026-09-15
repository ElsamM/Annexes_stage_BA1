package com.budiyev.android.codescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import java.lang.Thread;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
final class Decoder {
    private volatile DecodeCallback mCallback;
    private final Map<DecodeHintType, Object> mHints;
    private volatile State mState;
    private final StateListener mStateListener;
    private volatile DecodeTask mTask;
    private final Object mTaskLock = new Object();
    private final MultiFormatReader mReader = new MultiFormatReader();
    private final DecoderThread mDecoderThread = new DecoderThread();

    public enum State {
        INITIALIZED,
        IDLE,
        DECODING,
        DECODED,
        STOPPED
    }

    public interface StateListener {
        boolean onStateChanged(State state);
    }

    public Decoder(StateListener stateListener, Thread.UncaughtExceptionHandler exceptionHandler, List<BarcodeFormat> formats, DecodeCallback callback) {
        this.mDecoderThread.setUncaughtExceptionHandler(exceptionHandler);
        this.mHints = new EnumMap(DecodeHintType.class);
        this.mHints.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        this.mReader.setHints(this.mHints);
        this.mCallback = callback;
        this.mStateListener = stateListener;
        this.mState = State.INITIALIZED;
    }

    public void setFormats(List<BarcodeFormat> formats) {
        this.mHints.put(DecodeHintType.POSSIBLE_FORMATS, formats);
        this.mReader.setHints(this.mHints);
    }

    public void setCallback(DecodeCallback callback) {
        this.mCallback = callback;
    }

    public void decode(DecodeTask task) {
        synchronized (this.mTaskLock) {
            if (this.mState != State.STOPPED) {
                this.mTask = task;
                this.mTaskLock.notify();
            }
        }
    }

    public void start() {
        if (this.mState != State.INITIALIZED) {
            throw new IllegalStateException("Illegal decoder state");
        }
        this.mDecoderThread.start();
    }

    public void shutdown() {
        this.mDecoderThread.interrupt();
        this.mTask = null;
    }

    public State getState() {
        return this.mState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setState(State state) {
        this.mState = state;
        return this.mStateListener.onStateChanged(state);
    }

    private final class DecoderThread extends Thread {
        public DecoderThread() {
            super("cs-decoder");
        }

        /* JADX WARN: Removed duplicated region for block: B:59:0x0037 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:64:0x00aa A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x0005 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0005 A[SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r7 = this;
                r0 = 10
                android.os.Process.setThreadPriority(r0)
            L5:
                com.budiyev.android.codescanner.Decoder r0 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder$State r1 = com.budiyev.android.codescanner.Decoder.State.IDLE
                com.budiyev.android.codescanner.Decoder.access$000(r0, r1)
                r0 = 0
            Ld:
                r1 = 0
                com.budiyev.android.codescanner.Decoder r2 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                java.lang.Object r2 = com.budiyev.android.codescanner.Decoder.access$100(r2)     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.budiyev.android.codescanner.Decoder r3 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L86
                com.budiyev.android.codescanner.DecodeTask r3 = com.budiyev.android.codescanner.Decoder.access$200(r3)     // Catch: java.lang.Throwable -> L86
                if (r3 == 0) goto L53
                com.budiyev.android.codescanner.Decoder r4 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L86
                com.budiyev.android.codescanner.Decoder.access$202(r4, r1)     // Catch: java.lang.Throwable -> L86
                r4 = r3
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L86
                com.budiyev.android.codescanner.Decoder r2 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.budiyev.android.codescanner.Decoder$State r3 = com.budiyev.android.codescanner.Decoder.State.DECODING     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.budiyev.android.codescanner.Decoder.access$000(r2, r3)     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.budiyev.android.codescanner.Decoder r2 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.google.zxing.MultiFormatReader r2 = com.budiyev.android.codescanner.Decoder.access$300(r2)     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                com.google.zxing.Result r2 = r4.decode(r2)     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
                if (r2 == 0) goto Lc5
                com.budiyev.android.codescanner.Decoder r0 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder.access$202(r0, r1)
                com.budiyev.android.codescanner.Decoder r0 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder$State r1 = com.budiyev.android.codescanner.Decoder.State.DECODED
                boolean r0 = com.budiyev.android.codescanner.Decoder.access$000(r0, r1)
                if (r0 == 0) goto Lc5
                com.budiyev.android.codescanner.Decoder r0 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.DecodeCallback r0 = com.budiyev.android.codescanner.Decoder.access$400(r0)
                if (r0 == 0) goto L51
                r0.onDecoded(r2)
            L51:
                goto Lc5
            L53:
                com.budiyev.android.codescanner.Decoder r4 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.InterruptedException -> L5f java.lang.Throwable -> L86
                java.lang.Object r4 = com.budiyev.android.codescanner.Decoder.access$100(r4)     // Catch: java.lang.InterruptedException -> L5f java.lang.Throwable -> L86
                r4.wait()     // Catch: java.lang.InterruptedException -> L5f java.lang.Throwable -> L86
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L86
                goto Ld
            L5f:
                r4 = move-exception
                com.budiyev.android.codescanner.Decoder r5 = com.budiyev.android.codescanner.Decoder.this     // Catch: java.lang.Throwable -> L86
                com.budiyev.android.codescanner.Decoder$State r6 = com.budiyev.android.codescanner.Decoder.State.STOPPED     // Catch: java.lang.Throwable -> L86
                com.budiyev.android.codescanner.Decoder.access$000(r5, r6)     // Catch: java.lang.Throwable -> L86
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L86
                if (r0 == 0) goto L85
                com.budiyev.android.codescanner.Decoder r2 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder.access$202(r2, r1)
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder$State r2 = com.budiyev.android.codescanner.Decoder.State.DECODED
                boolean r1 = com.budiyev.android.codescanner.Decoder.access$000(r1, r2)
                if (r1 == 0) goto L85
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.DecodeCallback r1 = com.budiyev.android.codescanner.Decoder.access$400(r1)
                if (r1 == 0) goto L84
                r1.onDecoded(r0)
            L84:
            L85:
                return
            L86:
                r3 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L86
                throw r3     // Catch: java.lang.Throwable -> L89 com.google.zxing.ReaderException -> La7
            L89:
                r2 = move-exception
                if (r0 == 0) goto La6
                com.budiyev.android.codescanner.Decoder r3 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder.access$202(r3, r1)
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder$State r3 = com.budiyev.android.codescanner.Decoder.State.DECODED
                boolean r1 = com.budiyev.android.codescanner.Decoder.access$000(r1, r3)
                if (r1 == 0) goto La6
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.DecodeCallback r1 = com.budiyev.android.codescanner.Decoder.access$400(r1)
                if (r1 == 0) goto La6
                r1.onDecoded(r0)
            La6:
                throw r2
            La7:
                r2 = move-exception
                if (r0 == 0) goto Lc5
                com.budiyev.android.codescanner.Decoder r2 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder.access$202(r2, r1)
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.Decoder$State r2 = com.budiyev.android.codescanner.Decoder.State.DECODED
                boolean r1 = com.budiyev.android.codescanner.Decoder.access$000(r1, r2)
                if (r1 == 0) goto Lc5
                com.budiyev.android.codescanner.Decoder r1 = com.budiyev.android.codescanner.Decoder.this
                com.budiyev.android.codescanner.DecodeCallback r1 = com.budiyev.android.codescanner.Decoder.access$400(r1)
                if (r1 == 0) goto Lc4
                r1.onDecoded(r0)
            Lc4:
            Lc5:
                goto L5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.budiyev.android.codescanner.Decoder.DecoderThread.run():void");
        }
    }
}
