package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Pipe.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\"2\u0006\u0010\u0017\u001a\u00020\u0010J\r\u0010\u0017\u001a\u00020\u0010H\u0007¢\u0006\u0002\b$J\r\u0010\u001b\u001a\u00020\u001cH\u0007¢\u0006\u0002\b%J&\u0010&\u001a\u00020\"*\u00020\u00102\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"0(¢\u0006\u0002\b)H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u00108G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0018\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u0013\u0010\u001b\u001a\u00020\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e¨\u0006*"}, d2 = {"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$okio", "()Lokio/Buffer;", "canceled", "", "getCanceled$okio", "()Z", "setCanceled$okio", "(Z)V", "foldedSink", "Lokio/Sink;", "getFoldedSink$okio", "()Lokio/Sink;", "setFoldedSink$okio", "(Lokio/Sink;)V", "getMaxBufferSize$okio", "()J", "sink", "sinkClosed", "getSinkClosed$okio", "setSinkClosed$okio", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$okio", "setSourceClosed$okio", "cancel", "", "fold", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "okio"}, k = 1, mv = {1, 4, 0})
public final class Pipe {
    private final Buffer buffer = new Buffer();
    private boolean canceled;
    private Sink foldedSink;
    private final long maxBufferSize;
    private final Sink sink;
    private boolean sinkClosed;
    private final Source source;
    private boolean sourceClosed;

    public Pipe(long maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
        if (!(this.maxBufferSize >= 1)) {
            throw new IllegalArgumentException(("maxBufferSize < 1: " + this.maxBufferSize).toString());
        }
        this.sink = new Sink() { // from class: okio.Pipe.sink.1
            private final Timeout timeout = new Timeout();

            /* JADX WARN: Code restructure failed: missing block: B:30:0x00a4, code lost:
            
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x00a8, code lost:
            
                if (r5 == null) goto L64;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x00aa, code lost:
            
                r0 = r18.this$0;
                r6 = r5;
                r9 = r6.getTimeout();
                r0 = r0.sink().getTimeout();
                r12 = r9.getTimeoutNanos();
                r9.timeout(okio.Timeout.INSTANCE.minTimeout(r0.getTimeoutNanos(), r9.getTimeoutNanos()), java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x00dc, code lost:
            
                if (r9.getHasDeadline() == false) goto L49;
             */
            /* JADX WARN: Code restructure failed: missing block: B:35:0x00de, code lost:
            
                r5 = r9.deadlineNanoTime();
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x00e6, code lost:
            
                if (r0.getHasDeadline() == false) goto L38;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x00e8, code lost:
            
                r9.deadlineNanoTime(java.lang.Math.min(r9.deadlineNanoTime(), r0.deadlineNanoTime()));
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x00fc, code lost:
            
                r6.write(r19, r3);
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x010e, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x010f, code lost:
            
                r9.timeout(r12, java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x0118, code lost:
            
                if (r0.getHasDeadline() != false) goto L47;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x011a, code lost:
            
                r9.deadlineNanoTime(r5);
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x011d, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x0122, code lost:
            
                if (r0.getHasDeadline() == false) goto L52;
             */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x0124, code lost:
            
                r9.deadlineNanoTime(r0.deadlineNanoTime());
             */
            /* JADX WARN: Code restructure failed: missing block: B:53:0x0130, code lost:
            
                r6.write(r19, r3);
             */
            /* JADX WARN: Code restructure failed: missing block: B:59:0x0146, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:60:0x0147, code lost:
            
                r9.timeout(r12, java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x0150, code lost:
            
                if (r0.getHasDeadline() != false) goto L62;
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x0152, code lost:
            
                r9.clearDeadline();
             */
            /* JADX WARN: Code restructure failed: missing block: B:63:0x0155, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:65:0x0158, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:66:0x0159, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:75:0x0179, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:93:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:94:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
            
                return;
             */
            @Override // okio.Sink
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void write(okio.Buffer r19, long r20) throws java.lang.Throwable {
                /*
                    Method dump skipped, instruction units count: 379
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.AnonymousClass1.write(okio.Buffer, long):void");
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() {
                boolean hasDeadline;
                Sink sink = (Sink) null;
                Object lock$iv = Pipe.this.getBuffer();
                synchronized (lock$iv) {
                    if (Pipe.this.getSinkClosed()) {
                        throw new IllegalStateException("closed".toString());
                    }
                    if (Pipe.this.getCanceled()) {
                        throw new IOException("canceled");
                    }
                    Sink it = Pipe.this.getFoldedSink();
                    if (it != null) {
                        sink = it;
                    } else if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink == null) {
                    return;
                }
                Pipe this_$iv = Pipe.this;
                Sink $this$forward$iv = sink;
                Timeout this_$iv$iv = $this$forward$iv.getTimeout();
                Timeout other$iv$iv = this_$iv.sink().getTimeout();
                long originalTimeout$iv$iv = this_$iv$iv.getTimeoutNanos();
                this_$iv$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv$iv.getTimeoutNanos(), this_$iv$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                if (this_$iv$iv.getHasDeadline()) {
                    long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                    if (other$iv$iv.getHasDeadline()) {
                        this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                    }
                    try {
                        $this$forward$iv.flush();
                        if (hasDeadline) {
                            return;
                        } else {
                            return;
                        }
                    } finally {
                        this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                        if (other$iv$iv.getHasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                        }
                    }
                }
                if (other$iv$iv.getHasDeadline()) {
                    this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                }
                try {
                    $this$forward$iv.flush();
                } finally {
                    this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                    if (other$iv$iv.getHasDeadline()) {
                        this_$iv$iv.clearDeadline();
                    }
                }
            }

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                boolean hasDeadline;
                Sink sink = (Sink) null;
                Object lock$iv = Pipe.this.getBuffer();
                synchronized (lock$iv) {
                    if (Pipe.this.getSinkClosed()) {
                        return;
                    }
                    Sink it = Pipe.this.getFoldedSink();
                    if (it != null) {
                        sink = it;
                    } else {
                        if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe.this.setSinkClosed$okio(true);
                        Buffer buffer = Pipe.this.getBuffer();
                        if (buffer == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                        }
                        buffer.notifyAll();
                    }
                    Unit unit = Unit.INSTANCE;
                    if (sink == null) {
                        return;
                    }
                    Pipe this_$iv = Pipe.this;
                    Sink $this$forward$iv = sink;
                    Timeout this_$iv$iv = $this$forward$iv.getTimeout();
                    Timeout other$iv$iv = this_$iv.sink().getTimeout();
                    long originalTimeout$iv$iv = this_$iv$iv.getTimeoutNanos();
                    this_$iv$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv$iv.getTimeoutNanos(), this_$iv$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                    if (this_$iv$iv.getHasDeadline()) {
                        long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                        if (other$iv$iv.getHasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                        }
                        try {
                            $this$forward$iv.close();
                            if (hasDeadline) {
                                return;
                            } else {
                                return;
                            }
                        } finally {
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (other$iv$iv.getHasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                            }
                        }
                    }
                    if (other$iv$iv.getHasDeadline()) {
                        this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                    }
                    try {
                        $this$forward$iv.close();
                    } finally {
                        this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                        if (other$iv$iv.getHasDeadline()) {
                            this_$iv$iv.clearDeadline();
                        }
                    }
                }
            }

            @Override // okio.Sink
            /* JADX INFO: renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }
        };
        this.source = new Source() { // from class: okio.Pipe.source.1
            private final Timeout timeout = new Timeout();

            @Override // okio.Source
            public long read(Buffer sink, long byteCount) {
                Intrinsics.checkNotNullParameter(sink, "sink");
                Object lock$iv = Pipe.this.getBuffer();
                synchronized (lock$iv) {
                    if (Pipe.this.getSourceClosed()) {
                        throw new IllegalStateException("closed".toString());
                    }
                    if (Pipe.this.getCanceled()) {
                        throw new IOException("canceled");
                    }
                    while (Pipe.this.getBuffer().size() == 0) {
                        if (Pipe.this.getSinkClosed()) {
                            return -1L;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.getBuffer());
                        if (Pipe.this.getCanceled()) {
                            throw new IOException("canceled");
                        }
                    }
                    long j = Pipe.this.getBuffer().read(sink, byteCount);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    return j;
                }
            }

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                Object lock$iv = Pipe.this.getBuffer();
                synchronized (lock$iv) {
                    Pipe.this.setSourceClosed$okio(true);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // okio.Source
            /* JADX INFO: renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }
        };
    }

    /* JADX INFO: renamed from: getMaxBufferSize$okio, reason: from getter */
    public final long getMaxBufferSize() {
        return this.maxBufferSize;
    }

    /* JADX INFO: renamed from: getBuffer$okio, reason: from getter */
    public final Buffer getBuffer() {
        return this.buffer;
    }

    /* JADX INFO: renamed from: getCanceled$okio, reason: from getter */
    public final boolean getCanceled() {
        return this.canceled;
    }

    public final void setCanceled$okio(boolean z) {
        this.canceled = z;
    }

    /* JADX INFO: renamed from: getSinkClosed$okio, reason: from getter */
    public final boolean getSinkClosed() {
        return this.sinkClosed;
    }

    public final void setSinkClosed$okio(boolean z) {
        this.sinkClosed = z;
    }

    /* JADX INFO: renamed from: getSourceClosed$okio, reason: from getter */
    public final boolean getSourceClosed() {
        return this.sourceClosed;
    }

    public final void setSourceClosed$okio(boolean z) {
        this.sourceClosed = z;
    }

    /* JADX INFO: renamed from: getFoldedSink$okio, reason: from getter */
    public final Sink getFoldedSink() {
        return this.foldedSink;
    }

    public final void setFoldedSink$okio(Sink sink) {
        this.foldedSink = sink;
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }

    public final void fold(Sink sink) throws IOException {
        boolean closed;
        Buffer buffer;
        Intrinsics.checkNotNullParameter(sink, "sink");
        while (true) {
            Object lock$iv = this.buffer;
            synchronized (lock$iv) {
                if (!(this.foldedSink == null)) {
                    throw new IllegalStateException("sink already folded".toString());
                }
                if (this.canceled) {
                    this.foldedSink = sink;
                    throw new IOException("canceled");
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = sink;
                    return;
                }
                closed = this.sinkClosed;
                buffer = new Buffer();
                buffer.write(this.buffer, this.buffer.size());
                Buffer buffer2 = this.buffer;
                if (buffer2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                }
                buffer2.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            try {
                sink.write(buffer, buffer.size());
                if (closed) {
                    sink.close();
                } else {
                    sink.flush();
                }
            } catch (Throwable th) {
                synchronized (this.buffer) {
                    this.sourceClosed = true;
                    Buffer buffer3 = this.buffer;
                    if (buffer3 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer3.notifyAll();
                    Unit unit2 = Unit.INSTANCE;
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forward(Sink $this$forward, Function1<? super Sink, Unit> function1) {
        boolean hasDeadline;
        Timeout this_$iv = $this$forward.getTimeout();
        Timeout other$iv = sink().getTimeout();
        long originalTimeout$iv = this_$iv.getTimeoutNanos();
        this_$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv.getTimeoutNanos(), this_$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
        if (this_$iv.getHasDeadline()) {
            long originalDeadline$iv = this_$iv.deadlineNanoTime();
            if (other$iv.getHasDeadline()) {
                this_$iv.deadlineNanoTime(Math.min(this_$iv.deadlineNanoTime(), other$iv.deadlineNanoTime()));
            }
            try {
                function1.invoke($this$forward);
                if (hasDeadline) {
                    return;
                } else {
                    return;
                }
            } finally {
                this_$iv.timeout(originalTimeout$iv, TimeUnit.NANOSECONDS);
                if (other$iv.getHasDeadline()) {
                    this_$iv.deadlineNanoTime(originalDeadline$iv);
                }
            }
        }
        if (other$iv.getHasDeadline()) {
            this_$iv.deadlineNanoTime(other$iv.deadlineNanoTime());
        }
        try {
            function1.invoke($this$forward);
        } finally {
            this_$iv.timeout(originalTimeout$iv, TimeUnit.NANOSECONDS);
            if (other$iv.getHasDeadline()) {
                this_$iv.clearDeadline();
            }
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sink", imports = {}))
    /* JADX INFO: renamed from: -deprecated_sink, reason: not valid java name and from getter */
    public final Sink getSink() {
        return this.sink;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "source", imports = {}))
    /* JADX INFO: renamed from: -deprecated_source, reason: not valid java name and from getter */
    public final Source getSource() {
        return this.source;
    }

    public final void cancel() {
        Object lock$iv = this.buffer;
        synchronized (lock$iv) {
            this.canceled = true;
            this.buffer.clear();
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
            }
            buffer.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }
}
