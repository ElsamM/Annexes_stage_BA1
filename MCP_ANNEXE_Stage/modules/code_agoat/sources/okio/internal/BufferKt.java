package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.EOFException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
import okhttp3.internal.connection.RealConnection;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Platform;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio.Utf8;
import okio.Util;

/* JADX INFO: compiled from: Buffer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0000\u001a\r\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0080\b\u001a\r\u0010\u0014\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\u0013*\u00020\u0013H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\n*\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u001e*\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0005H\u0080\b\u001a\r\u0010 \u001a\u00020\b*\u00020\u0013H\u0080\b\u001a%\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010&\u001a\u00020\u0005*\u00020\u00132\u0006\u0010'\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010(\u001a\u00020\n*\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a%\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010)\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010+\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020,H\u0080\b\u001a\r\u0010-\u001a\u00020\u001e*\u00020\u0013H\u0080\b\u001a\r\u0010.\u001a\u00020\u0001*\u00020\u0013H\u0080\b\u001a\u0015\u0010.\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010/\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00100\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a\u001d\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00102\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00103\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\r\u00104\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0013H\u0080\b\u001a\u0015\u00107\u001a\u000208*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00109\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\u000f\u0010:\u001a\u0004\u0018\u000108*\u00020\u0013H\u0080\b\u001a\u0015\u0010;\u001a\u000208*\u00020\u00132\u0006\u0010<\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010=\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?H\u0080\b\u001a\u0015\u0010@\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010A\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010A\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010B\u001a\u00020\f*\u00020\u00132\u0006\u0010C\u001a\u00020\bH\u0080\b\u001a\u0015\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u0001H\u0080\b\u001a%\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0012*\u00020\u00132\u0006\u0010E\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a)\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010F\u001a\u00020%2\b\b\u0002\u0010\u0018\u001a\u00020\b2\b\b\u0002\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020G2\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010H\u001a\u00020\u0005*\u00020\u00132\u0006\u0010E\u001a\u00020GH\u0080\b\u001a\u0015\u0010I\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\"\u001a\u00020\bH\u0080\b\u001a\u0015\u0010J\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010L\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010M\u001a\u00020\u0013*\u00020\u00132\u0006\u0010N\u001a\u00020\bH\u0080\b\u001a\u0015\u0010O\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010P\u001a\u00020\u0013*\u00020\u00132\u0006\u0010Q\u001a\u00020\bH\u0080\b\u001a%\u0010R\u001a\u00020\u0013*\u00020\u00132\u0006\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020\b2\u0006\u0010U\u001a\u00020\bH\u0080\b\u001a\u0015\u0010V\u001a\u00020\u0013*\u00020\u00132\u0006\u0010W\u001a\u00020\bH\u0080\b\u001a\u0014\u0010X\u001a\u000208*\u00020\u00132\u0006\u0010Y\u001a\u00020\u0005H\u0000\u001a?\u0010Z\u001a\u0002H[\"\u0004\b\u0000\u0010[*\u00020\u00132\u0006\u0010#\u001a\u00020\u00052\u001a\u0010\\\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H[0]H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010^\u001a\u001e\u0010_\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010`\u001a\u00020\nH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006a"}, d2 = {"HEX_DIGIT_BYTES", "", "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", "", "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", "", "rangeEquals", "", "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", "", "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", TypedValues.CycleType.S_WAVE_OFFSET, "byteCount", "commonEquals", "other", "", "commonGet", "", "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", "", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", TypedValues.Custom.S_STRING, "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"}, k = 2, mv = {1, 4, 0})
public final class BufferKt {
    private static final byte[] HEX_DIGIT_BYTES = Platform.asUtf8ToByteArray("0123456789abcdef");
    public static final long OVERFLOW_DIGIT_START = -7;
    public static final long OVERFLOW_ZONE = -922337203685477580L;
    public static final int SEGMENTING_THRESHOLD = 4096;

    public static final byte[] getHEX_DIGIT_BYTES() {
        return HEX_DIGIT_BYTES;
    }

    public static final boolean rangeEquals(Segment segment, int segmentPos, byte[] bytes, int bytesOffset, int bytesLimit) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Segment segment2 = segment;
        int segmentPos2 = segmentPos;
        int segmentLimit = segment2.limit;
        byte[] data = segment2.data;
        for (int i = bytesOffset; i < bytesLimit; i++) {
            if (segmentPos2 == segmentLimit) {
                Segment segment3 = segment2.next;
                Intrinsics.checkNotNull(segment3);
                byte[] data2 = segment3.data;
                segmentPos2 = segment3.pos;
                segmentLimit = segment3.limit;
                data = data2;
                segment2 = segment3;
            }
            if (data[segmentPos2] != bytes[i]) {
                return false;
            }
            segmentPos2++;
        }
        return true;
    }

    public static final String readUtf8Line(Buffer readUtf8Line, long newline) throws EOFException {
        Intrinsics.checkNotNullParameter(readUtf8Line, "$this$readUtf8Line");
        if (newline > 0 && readUtf8Line.getByte(newline - 1) == ((byte) 13)) {
            String result = readUtf8Line.readUtf8(newline - 1);
            readUtf8Line.skip(2L);
            return result;
        }
        String result2 = readUtf8Line.readUtf8(newline);
        readUtf8Line.skip(1L);
        return result2;
    }

    public static final <T> T seek(Buffer seek, long fromIndex, Function2<? super Segment, ? super Long, ? extends T> lambda) {
        Intrinsics.checkNotNullParameter(seek, "$this$seek");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Segment s = seek.head;
        if (s == null) {
            return lambda.invoke(null, -1L);
        }
        if (seek.size() - fromIndex < fromIndex) {
            long offset = seek.size();
            while (offset > fromIndex) {
                Segment segment = s.prev;
                Intrinsics.checkNotNull(segment);
                s = segment;
                offset -= (long) (s.limit - s.pos);
            }
            return lambda.invoke(s, Long.valueOf(offset));
        }
        long offset2 = 0;
        while (true) {
            long nextOffset = ((long) (s.limit - s.pos)) + offset2;
            if (nextOffset <= fromIndex) {
                Segment segment2 = s.next;
                Intrinsics.checkNotNull(segment2);
                s = segment2;
                offset2 = nextOffset;
            } else {
                return lambda.invoke(s, Long.valueOf(offset2));
            }
        }
    }

    public static /* synthetic */ int selectPrefix$default(Buffer buffer, Options options, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return selectPrefix(buffer, options, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006c, code lost:
    
        if (r22 == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:
    
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int selectPrefix(okio.Buffer r20, okio.Options r21, boolean r22) {
        /*
            Method dump skipped, instruction units count: 218
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.selectPrefix(okio.Buffer, okio.Options, boolean):int");
    }

    public static final Buffer commonCopyTo(Buffer commonCopyTo, Buffer out, long offset, long byteCount) {
        Intrinsics.checkNotNullParameter(commonCopyTo, "$this$commonCopyTo");
        Intrinsics.checkNotNullParameter(out, "out");
        long offset2 = offset;
        long byteCount2 = byteCount;
        Util.checkOffsetAndCount(commonCopyTo.size(), offset2, byteCount2);
        if (byteCount2 == 0) {
            return commonCopyTo;
        }
        out.setSize$okio(out.size() + byteCount2);
        Segment s = commonCopyTo.head;
        while (true) {
            Intrinsics.checkNotNull(s);
            if (offset2 < s.limit - s.pos) {
                break;
            }
            offset2 -= (long) (s.limit - s.pos);
            s = s.next;
        }
        while (byteCount2 > 0) {
            Intrinsics.checkNotNull(s);
            Segment copy = s.sharedCopy();
            copy.pos += (int) offset2;
            copy.limit = Math.min(copy.pos + ((int) byteCount2), copy.limit);
            if (out.head == null) {
                copy.prev = copy;
                copy.next = copy.prev;
                out.head = copy.next;
            } else {
                Segment segment = out.head;
                Intrinsics.checkNotNull(segment);
                Segment segment2 = segment.prev;
                Intrinsics.checkNotNull(segment2);
                segment2.push(copy);
            }
            byteCount2 -= (long) (copy.limit - copy.pos);
            offset2 = 0;
            s = s.next;
        }
        return commonCopyTo;
    }

    public static final long commonCompleteSegmentByteCount(Buffer commonCompleteSegmentByteCount) {
        Intrinsics.checkNotNullParameter(commonCompleteSegmentByteCount, "$this$commonCompleteSegmentByteCount");
        long result = commonCompleteSegmentByteCount.size();
        if (result == 0) {
            return 0L;
        }
        Segment segment = commonCompleteSegmentByteCount.head;
        Intrinsics.checkNotNull(segment);
        Segment tail = segment.prev;
        Intrinsics.checkNotNull(tail);
        if (tail.limit < 8192 && tail.owner) {
            return result - ((long) (tail.limit - tail.pos));
        }
        return result;
    }

    public static final byte commonReadByte(Buffer commonReadByte) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByte, "$this$commonReadByte");
        if (commonReadByte.size() == 0) {
            throw new EOFException();
        }
        Segment segment = commonReadByte.head;
        Intrinsics.checkNotNull(segment);
        int pos = segment.pos;
        int limit = segment.limit;
        byte[] data = segment.data;
        int pos2 = pos + 1;
        byte b = data[pos];
        commonReadByte.setSize$okio(commonReadByte.size() - 1);
        if (pos2 == limit) {
            commonReadByte.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos2;
        }
        return b;
    }

    public static final short commonReadShort(Buffer commonReadShort) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadShort, "$this$commonReadShort");
        if (commonReadShort.size() < 2) {
            throw new EOFException();
        }
        Segment segment = commonReadShort.head;
        Intrinsics.checkNotNull(segment);
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 2) {
            byte $this$and$iv = commonReadShort.readByte();
            int i = ($this$and$iv & UByte.MAX_VALUE) << 8;
            byte $this$and$iv2 = commonReadShort.readByte();
            int s = i | ($this$and$iv2 & UByte.MAX_VALUE);
            return (short) s;
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        byte $this$and$iv3 = data[pos];
        int i2 = ($this$and$iv3 & UByte.MAX_VALUE) << 8;
        int pos3 = pos2 + 1;
        byte $this$and$iv4 = data[pos2];
        int s2 = i2 | ($this$and$iv4 & UByte.MAX_VALUE);
        commonReadShort.setSize$okio(commonReadShort.size() - 2);
        if (pos3 == limit) {
            commonReadShort.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos3;
        }
        return (short) s2;
    }

    public static final int commonReadInt(Buffer commonReadInt) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadInt, "$this$commonReadInt");
        if (commonReadInt.size() < 4) {
            throw new EOFException();
        }
        Segment segment = commonReadInt.head;
        Intrinsics.checkNotNull(segment);
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 4) {
            byte $this$and$iv = commonReadInt.readByte();
            int i = ($this$and$iv & UByte.MAX_VALUE) << 24;
            byte $this$and$iv2 = commonReadInt.readByte();
            int i2 = i | (($this$and$iv2 & UByte.MAX_VALUE) << 16);
            byte $this$and$iv3 = commonReadInt.readByte();
            int i3 = i2 | (($this$and$iv3 & UByte.MAX_VALUE) << 8);
            byte $this$and$iv4 = commonReadInt.readByte();
            return i3 | ($this$and$iv4 & UByte.MAX_VALUE);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        byte $this$and$iv5 = data[pos];
        int i4 = ($this$and$iv5 & UByte.MAX_VALUE) << 24;
        int pos3 = pos2 + 1;
        byte $this$and$iv6 = data[pos2];
        int i5 = i4 | (($this$and$iv6 & UByte.MAX_VALUE) << 16);
        int pos4 = pos3 + 1;
        byte $this$and$iv7 = data[pos3];
        int i6 = i5 | (($this$and$iv7 & UByte.MAX_VALUE) << 8);
        int pos5 = pos4 + 1;
        byte $this$and$iv8 = data[pos4];
        int i7 = i6 | ($this$and$iv8 & UByte.MAX_VALUE);
        commonReadInt.setSize$okio(commonReadInt.size() - 4);
        if (pos5 == limit) {
            commonReadInt.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos5;
        }
        return i7;
    }

    public static final long commonReadLong(Buffer commonReadLong) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadLong, "$this$commonReadLong");
        if (commonReadLong.size() < 8) {
            throw new EOFException();
        }
        Segment segment = commonReadLong.head;
        Intrinsics.checkNotNull(segment);
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 8) {
            int $this$and$iv = commonReadLong.readInt();
            int $this$and$iv2 = commonReadLong.readInt();
            long other$iv = 4294967295L & ((long) $this$and$iv2);
            return ((((long) $this$and$iv) & 4294967295L) << 32) | other$iv;
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        byte $this$and$iv3 = data[pos];
        long other$iv2 = 255 & ((long) $this$and$iv3);
        int pos3 = pos2 + 1;
        byte $this$and$iv4 = data[pos2];
        long j = ((((long) $this$and$iv4) & 255) << 48) | (other$iv2 << 56);
        int pos4 = pos3 + 1;
        byte $this$and$iv5 = data[pos3];
        long other$iv3 = 255 & ((long) $this$and$iv5);
        long j2 = j | (other$iv3 << 40);
        int pos5 = pos4 + 1;
        byte $this$and$iv6 = data[pos4];
        long other$iv4 = 255 & ((long) $this$and$iv6);
        int pos6 = pos5 + 1;
        byte $this$and$iv7 = data[pos5];
        long other$iv5 = 255 & ((long) $this$and$iv7);
        long j3 = j2 | (other$iv4 << 32) | (other$iv5 << 24);
        int pos7 = pos6 + 1;
        byte $this$and$iv8 = data[pos6];
        long other$iv6 = 255 & ((long) $this$and$iv8);
        long j4 = j3 | (other$iv6 << 16);
        int pos8 = pos7 + 1;
        byte $this$and$iv9 = data[pos7];
        long other$iv7 = 255 & ((long) $this$and$iv9);
        long j5 = j4 | (other$iv7 << 8);
        int pos9 = pos8 + 1;
        byte $this$and$iv10 = data[pos8];
        long other$iv8 = 255 & ((long) $this$and$iv10);
        long v = j5 | other$iv8;
        commonReadLong.setSize$okio(commonReadLong.size() - 8);
        if (pos9 == limit) {
            commonReadLong.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos9;
        }
        return v;
    }

    public static final byte commonGet(Buffer commonGet, long pos) {
        Intrinsics.checkNotNullParameter(commonGet, "$this$commonGet");
        Util.checkOffsetAndCount(commonGet.size(), pos, 1L);
        Segment s$iv = commonGet.head;
        if (s$iv == null) {
            Segment s = (Segment) null;
            Intrinsics.checkNotNull(s);
            return s.data[(int) ((((long) s.pos) + pos) - (-1))];
        }
        if (commonGet.size() - pos < pos) {
            long offset$iv = commonGet.size();
            while (offset$iv > pos) {
                Segment segment = s$iv.prev;
                Intrinsics.checkNotNull(segment);
                s$iv = segment;
                offset$iv -= (long) (s$iv.limit - s$iv.pos);
            }
            Segment s2 = s$iv;
            long offset = offset$iv;
            Intrinsics.checkNotNull(s2);
            return s2.data[(int) ((((long) s2.pos) + pos) - offset)];
        }
        long offset$iv2 = 0;
        while (true) {
            long nextOffset$iv = ((long) (s$iv.limit - s$iv.pos)) + offset$iv2;
            if (nextOffset$iv > pos) {
                Segment s3 = s$iv;
                long offset2 = offset$iv2;
                Intrinsics.checkNotNull(s3);
                return s3.data[(int) ((((long) s3.pos) + pos) - offset2)];
            }
            Segment segment2 = s$iv.next;
            Intrinsics.checkNotNull(segment2);
            s$iv = segment2;
            offset$iv2 = nextOffset$iv;
        }
    }

    public static final void commonClear(Buffer commonClear) throws EOFException {
        Intrinsics.checkNotNullParameter(commonClear, "$this$commonClear");
        commonClear.skip(commonClear.size());
    }

    public static final void commonSkip(Buffer commonSkip, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(commonSkip, "$this$commonSkip");
        long byteCount2 = byteCount;
        while (byteCount2 > 0) {
            Segment head = commonSkip.head;
            if (head == null) {
                throw new EOFException();
            }
            int b$iv = (int) Math.min(byteCount2, head.limit - head.pos);
            commonSkip.setSize$okio(commonSkip.size() - ((long) b$iv));
            byteCount2 -= (long) b$iv;
            head.pos += b$iv;
            if (head.pos == head.limit) {
                commonSkip.head = head.pop();
                SegmentPool.recycle(head);
            }
        }
    }

    public static /* synthetic */ Buffer commonWrite$default(Buffer commonWrite, ByteString byteString, int offset, int byteCount, int i, Object obj) {
        if ((i & 2) != 0) {
            offset = 0;
        }
        if ((i & 4) != 0) {
            byteCount = byteString.size();
        }
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(byteString, "byteString");
        byteString.write$okio(commonWrite, offset, byteCount);
        return commonWrite;
    }

    public static final Buffer commonWrite(Buffer commonWrite, ByteString byteString, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(byteString, "byteString");
        byteString.write$okio(commonWrite, offset, byteCount);
        return commonWrite;
    }

    public static final Buffer commonWriteDecimalLong(Buffer commonWriteDecimalLong, long v) {
        int width;
        Intrinsics.checkNotNullParameter(commonWriteDecimalLong, "$this$commonWriteDecimalLong");
        long v2 = v;
        if (v2 == 0) {
            return commonWriteDecimalLong.writeByte(48);
        }
        boolean negative = false;
        if (v2 < 0) {
            v2 = -v2;
            if (v2 < 0) {
                return commonWriteDecimalLong.writeUtf8("-9223372036854775808");
            }
            negative = true;
        }
        if (v2 < 100000000) {
            if (v2 < 10000) {
                if (v2 < 100) {
                    width = v2 < 10 ? 1 : 2;
                } else {
                    width = v2 < 1000 ? 3 : 4;
                }
            } else if (v2 < 1000000) {
                width = v2 < 100000 ? 5 : 6;
            } else {
                width = v2 < 10000000 ? 7 : 8;
            }
        } else if (v2 < 1000000000000L) {
            if (v2 < RealConnection.IDLE_CONNECTION_HEALTHY_NS) {
                width = v2 < 1000000000 ? 9 : 10;
            } else {
                width = v2 < 100000000000L ? 11 : 12;
            }
        } else if (v2 < 1000000000000000L) {
            if (v2 < 10000000000000L) {
                width = 13;
            } else {
                width = v2 < 100000000000000L ? 14 : 15;
            }
        } else if (v2 < 100000000000000000L) {
            width = v2 < 10000000000000000L ? 16 : 17;
        } else {
            width = v2 < 1000000000000000000L ? 18 : 19;
        }
        if (negative) {
            width++;
        }
        Segment tail = commonWriteDecimalLong.writableSegment$okio(width);
        byte[] data = tail.data;
        int pos = tail.limit + width;
        while (v2 != 0) {
            long j = 10;
            int digit = (int) (v2 % j);
            pos--;
            data[pos] = getHEX_DIGIT_BYTES()[digit];
            v2 /= j;
        }
        if (negative) {
            data[pos - 1] = (byte) 45;
        }
        tail.limit += width;
        commonWriteDecimalLong.setSize$okio(commonWriteDecimalLong.size() + ((long) width));
        return commonWriteDecimalLong;
    }

    public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer commonWriteHexadecimalUnsignedLong, long v) {
        Intrinsics.checkNotNullParameter(commonWriteHexadecimalUnsignedLong, "$this$commonWriteHexadecimalUnsignedLong");
        long v2 = v;
        if (v2 == 0) {
            return commonWriteHexadecimalUnsignedLong.writeByte(48);
        }
        long x = v2 | (v2 >>> 1);
        long x2 = x | (x >>> 2);
        long x3 = x2 | (x2 >>> 4);
        long x4 = x3 | (x3 >>> 8);
        long x5 = x4 | (x4 >>> 16);
        long x6 = x5 | (x5 >>> 32);
        long x7 = x6 - ((x6 >>> 1) & 6148914691236517205L);
        long x8 = ((x7 >>> 2) & 3689348814741910323L) + (3689348814741910323L & x7);
        long x9 = ((x8 >>> 4) + x8) & 1085102592571150095L;
        long x10 = x9 + (x9 >>> 8);
        long x11 = x10 + (x10 >>> 16);
        int width = (int) ((((long) 3) + ((x11 & 63) + (63 & (x11 >>> 32)))) / ((long) 4));
        Segment tail = commonWriteHexadecimalUnsignedLong.writableSegment$okio(width);
        byte[] data = tail.data;
        int start = tail.limit;
        for (int pos = (tail.limit + width) - 1; pos >= start; pos--) {
            data[pos] = getHEX_DIGIT_BYTES()[(int) (15 & v2)];
            v2 >>>= 4;
        }
        tail.limit += width;
        commonWriteHexadecimalUnsignedLong.setSize$okio(commonWriteHexadecimalUnsignedLong.size() + ((long) width));
        return commonWriteHexadecimalUnsignedLong;
    }

    public static final Segment commonWritableSegment(Buffer commonWritableSegment, int minimumCapacity) {
        Intrinsics.checkNotNullParameter(commonWritableSegment, "$this$commonWritableSegment");
        if (!(minimumCapacity >= 1 && minimumCapacity <= 8192)) {
            throw new IllegalArgumentException("unexpected capacity".toString());
        }
        if (commonWritableSegment.head == null) {
            Segment result = SegmentPool.take();
            commonWritableSegment.head = result;
            result.prev = result;
            result.next = result;
            return result;
        }
        Segment segment = commonWritableSegment.head;
        Intrinsics.checkNotNull(segment);
        Segment tail = segment.prev;
        Intrinsics.checkNotNull(tail);
        if (tail.limit + minimumCapacity > 8192 || !tail.owner) {
            return tail.push(SegmentPool.take());
        }
        return tail;
    }

    public static final Buffer commonWrite(Buffer commonWrite, byte[] source) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        return commonWrite.write(source, 0, source.length);
    }

    public static final Buffer commonWrite(Buffer commonWrite, byte[] source, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        int offset2 = offset;
        Util.checkOffsetAndCount(source.length, offset2, byteCount);
        int limit = offset2 + byteCount;
        while (offset2 < limit) {
            Segment tail = commonWrite.writableSegment$okio(1);
            int toCopy = Math.min(limit - offset2, 8192 - tail.limit);
            ArraysKt.copyInto(source, tail.data, tail.limit, offset2, offset2 + toCopy);
            offset2 += toCopy;
            tail.limit += toCopy;
        }
        commonWrite.setSize$okio(commonWrite.size() + ((long) byteCount));
        return commonWrite;
    }

    public static final byte[] commonReadByteArray(Buffer commonReadByteArray) {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        return commonReadByteArray.readByteArray(commonReadByteArray.size());
    }

    public static final byte[] commonReadByteArray(Buffer commonReadByteArray, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        }
        if (commonReadByteArray.size() < byteCount) {
            throw new EOFException();
        }
        byte[] result = new byte[(int) byteCount];
        commonReadByteArray.readFully(result);
        return result;
    }

    public static final int commonRead(Buffer commonRead, byte[] sink) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        return commonRead.read(sink, 0, sink.length);
    }

    public static final void commonReadFully(Buffer commonReadFully, byte[] sink) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        int offset = 0;
        while (offset < sink.length) {
            int read = commonReadFully.read(sink, offset, sink.length - offset);
            if (read == -1) {
                throw new EOFException();
            }
            offset += read;
        }
    }

    public static final int commonRead(Buffer commonRead, byte[] sink, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Util.checkOffsetAndCount(sink.length, offset, byteCount);
        Segment s = commonRead.head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        ArraysKt.copyInto(s.data, sink, offset, s.pos, s.pos + toCopy);
        s.pos += toCopy;
        commonRead.setSize$okio(commonRead.size() - ((long) toCopy));
        if (s.pos == s.limit) {
            commonRead.head = s.pop();
            SegmentPool.recycle(s);
        }
        return toCopy;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
    
        r5 = new okio.Buffer().writeDecimalLong(r2).writeByte((int) r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0068, code lost:
    
        if (r16 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
    
        r5.readByte();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008f, code lost:
    
        throw new java.lang.NumberFormatException("Number too large: " + r5.readUtf8());
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long commonReadDecimalLong(okio.Buffer r20) throws java.io.EOFException {
        /*
            Method dump skipped, instruction units count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.commonReadDecimalLong(okio.Buffer):long");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c7 A[EDGE_INSN: B:45:0x00c7->B:38:0x00c7 BREAK  A[LOOP:0: B:5:0x0016->B:47:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long commonReadHexadecimalUnsignedLong(okio.Buffer r17) throws java.io.EOFException {
        /*
            Method dump skipped, instruction units count: 219
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.commonReadHexadecimalUnsignedLong(okio.Buffer):long");
    }

    public static final ByteString commonReadByteString(Buffer commonReadByteString) {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        return commonReadByteString.readByteString(commonReadByteString.size());
    }

    public static final ByteString commonReadByteString(Buffer commonReadByteString, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        }
        if (commonReadByteString.size() < byteCount) {
            throw new EOFException();
        }
        if (byteCount >= 4096) {
            ByteString byteStringSnapshot = commonReadByteString.snapshot((int) byteCount);
            commonReadByteString.skip(byteCount);
            return byteStringSnapshot;
        }
        return new ByteString(commonReadByteString.readByteArray(byteCount));
    }

    public static final int commonSelect(Buffer commonSelect, Options options) throws EOFException {
        Intrinsics.checkNotNullParameter(commonSelect, "$this$commonSelect");
        Intrinsics.checkNotNullParameter(options, "options");
        int index = selectPrefix$default(commonSelect, options, false, 2, null);
        if (index == -1) {
            return -1;
        }
        int selectedSize = options.getByteStrings()[index].size();
        commonSelect.skip(selectedSize);
        return index;
    }

    public static final void commonReadFully(Buffer commonReadFully, Buffer sink, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (commonReadFully.size() < byteCount) {
            sink.write(commonReadFully, commonReadFully.size());
            throw new EOFException();
        }
        sink.write(commonReadFully, byteCount);
    }

    public static final long commonReadAll(Buffer commonReadAll, Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(commonReadAll, "$this$commonReadAll");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long byteCount = commonReadAll.size();
        if (byteCount > 0) {
            sink.write(commonReadAll, byteCount);
        }
        return byteCount;
    }

    public static final String commonReadUtf8(Buffer commonReadUtf8, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadUtf8, "$this$commonReadUtf8");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        }
        if (commonReadUtf8.size() < byteCount) {
            throw new EOFException();
        }
        if (byteCount == 0) {
            return "";
        }
        Segment s = commonReadUtf8.head;
        Intrinsics.checkNotNull(s);
        if (((long) s.pos) + byteCount > s.limit) {
            return _Utf8Kt.commonToUtf8String$default(commonReadUtf8.readByteArray(byteCount), 0, 0, 3, null);
        }
        String result = _Utf8Kt.commonToUtf8String(s.data, s.pos, s.pos + ((int) byteCount));
        s.pos += (int) byteCount;
        commonReadUtf8.setSize$okio(commonReadUtf8.size() - byteCount);
        if (s.pos == s.limit) {
            commonReadUtf8.head = s.pop();
            SegmentPool.recycle(s);
        }
        return result;
    }

    public static final String commonReadUtf8Line(Buffer commonReadUtf8Line) {
        Intrinsics.checkNotNullParameter(commonReadUtf8Line, "$this$commonReadUtf8Line");
        long newline = commonReadUtf8Line.indexOf((byte) 10);
        if (newline != -1) {
            return readUtf8Line(commonReadUtf8Line, newline);
        }
        if (commonReadUtf8Line.size() != 0) {
            return commonReadUtf8Line.readUtf8(commonReadUtf8Line.size());
        }
        return null;
    }

    public static final String commonReadUtf8LineStrict(Buffer commonReadUtf8LineStrict, long limit) throws EOFException {
        Intrinsics.checkNotNullParameter(commonReadUtf8LineStrict, "$this$commonReadUtf8LineStrict");
        if (!(limit >= 0)) {
            throw new IllegalArgumentException(("limit < 0: " + limit).toString());
        }
        long scanLength = limit != Long.MAX_VALUE ? limit + 1 : Long.MAX_VALUE;
        byte b = (byte) 10;
        long newline = commonReadUtf8LineStrict.indexOf(b, 0L, scanLength);
        if (newline != -1) {
            return readUtf8Line(commonReadUtf8LineStrict, newline);
        }
        if (scanLength < commonReadUtf8LineStrict.size() && commonReadUtf8LineStrict.getByte(scanLength - 1) == ((byte) 13) && commonReadUtf8LineStrict.getByte(scanLength) == b) {
            return readUtf8Line(commonReadUtf8LineStrict, scanLength);
        }
        Buffer data = new Buffer();
        long b$iv = commonReadUtf8LineStrict.size();
        commonReadUtf8LineStrict.copyTo(data, 0L, Math.min(32, b$iv));
        throw new EOFException("\\n not found: limit=" + Math.min(commonReadUtf8LineStrict.size(), limit) + " content=" + data.readByteString().hex() + Typography.ellipsis);
    }

    public static final int commonReadUtf8CodePoint(Buffer commonReadUtf8CodePoint) throws EOFException {
        int codePoint;
        int byteCount;
        int min;
        Intrinsics.checkNotNullParameter(commonReadUtf8CodePoint, "$this$commonReadUtf8CodePoint");
        if (commonReadUtf8CodePoint.size() == 0) {
            throw new EOFException();
        }
        byte b0 = commonReadUtf8CodePoint.getByte(0L);
        int other$iv = 128 & b0;
        if (other$iv == 0) {
            codePoint = b0 & ByteCompanionObject.MAX_VALUE;
            byteCount = 1;
            min = 0;
        } else {
            int other$iv2 = 224 & b0;
            if (other$iv2 == 192) {
                codePoint = b0 & 31;
                byteCount = 2;
                min = 128;
            } else {
                int other$iv3 = 240 & b0;
                if (other$iv3 == 224) {
                    codePoint = b0 & 15;
                    byteCount = 3;
                    min = 2048;
                } else {
                    int other$iv4 = 248 & b0;
                    if (other$iv4 != 240) {
                        commonReadUtf8CodePoint.skip(1L);
                        return Utf8.REPLACEMENT_CODE_POINT;
                    }
                    codePoint = b0 & 7;
                    byteCount = 4;
                    min = 65536;
                }
            }
        }
        if (commonReadUtf8CodePoint.size() < byteCount) {
            throw new EOFException("size < " + byteCount + ": " + commonReadUtf8CodePoint.size() + " (to read code point prefixed 0x" + Util.toHexString(b0) + ')');
        }
        for (int i = 1; i < byteCount; i++) {
            int b = commonReadUtf8CodePoint.getByte(i);
            int other$iv5 = 192 & b;
            if (other$iv5 != 128) {
                commonReadUtf8CodePoint.skip(i);
                return Utf8.REPLACEMENT_CODE_POINT;
            }
            int other$iv6 = 63 & b;
            codePoint = (codePoint << 6) | other$iv6;
        }
        commonReadUtf8CodePoint.skip(byteCount);
        return codePoint > 1114111 ? Utf8.REPLACEMENT_CODE_POINT : ((55296 <= codePoint && 57343 >= codePoint) || codePoint < min) ? Utf8.REPLACEMENT_CODE_POINT : codePoint;
    }

    public static final Buffer commonWriteUtf8(Buffer commonWriteUtf8, String string, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(commonWriteUtf8, "$this$commonWriteUtf8");
        Intrinsics.checkNotNullParameter(string, "string");
        int i = 1;
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
        }
        if (!(endIndex >= beginIndex)) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
        }
        if (!(endIndex <= string.length())) {
            throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + string.length()).toString());
        }
        int runSize = beginIndex;
        while (runSize < endIndex) {
            int c = string.charAt(runSize);
            if (c < 128) {
                Segment tail = commonWriteUtf8.writableSegment$okio(i);
                byte[] data = tail.data;
                int segmentOffset = tail.limit - runSize;
                int runLimit = Math.min(endIndex, 8192 - segmentOffset);
                int i2 = runSize + 1;
                int i3 = runSize + segmentOffset;
                data[i3] = (byte) c;
                while (i2 < runLimit) {
                    int c2 = string.charAt(i2);
                    if (c2 >= 128) {
                        break;
                    }
                    data[i2 + segmentOffset] = (byte) c2;
                    i2++;
                }
                int runSize2 = (i2 + segmentOffset) - tail.limit;
                tail.limit += runSize2;
                commonWriteUtf8.setSize$okio(((long) runSize2) + commonWriteUtf8.size());
                runSize = i2;
            } else if (c < 2048) {
                Segment tail2 = commonWriteUtf8.writableSegment$okio(2);
                tail2.data[tail2.limit] = (byte) ((c >> 6) | 192);
                tail2.data[tail2.limit + 1] = (byte) (128 | (c & 63));
                tail2.limit += 2;
                commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 2);
                runSize++;
            } else if (c < 55296 || c > 57343) {
                Segment tail3 = commonWriteUtf8.writableSegment$okio(3);
                tail3.data[tail3.limit] = (byte) ((c >> 12) | 224);
                tail3.data[tail3.limit + 1] = (byte) ((63 & (c >> 6)) | 128);
                tail3.data[tail3.limit + 2] = (byte) (128 | (c & 63));
                tail3.limit += 3;
                commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 3);
                runSize++;
            } else {
                int low = runSize + 1 < endIndex ? string.charAt(runSize + 1) : 0;
                if (c > 56319 || 56320 > low || 57343 < low) {
                    commonWriteUtf8.writeByte(63);
                    runSize++;
                } else {
                    int codePoint = (((c & 1023) << 10) | (low & 1023)) + 65536;
                    Segment tail4 = commonWriteUtf8.writableSegment$okio(4);
                    tail4.data[tail4.limit] = (byte) ((codePoint >> 18) | 240);
                    tail4.data[tail4.limit + 1] = (byte) (((codePoint >> 12) & 63) | 128);
                    tail4.data[tail4.limit + 2] = (byte) ((63 & (codePoint >> 6)) | 128);
                    tail4.data[tail4.limit + 3] = (byte) (128 | (codePoint & 63));
                    tail4.limit += 4;
                    commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 4);
                    runSize += 2;
                }
            }
            i = 1;
        }
        return commonWriteUtf8;
    }

    public static final Buffer commonWriteUtf8CodePoint(Buffer commonWriteUtf8CodePoint, int codePoint) {
        Intrinsics.checkNotNullParameter(commonWriteUtf8CodePoint, "$this$commonWriteUtf8CodePoint");
        if (codePoint < 128) {
            commonWriteUtf8CodePoint.writeByte(codePoint);
        } else if (codePoint < 2048) {
            Segment tail = commonWriteUtf8CodePoint.writableSegment$okio(2);
            tail.data[tail.limit] = (byte) ((codePoint >> 6) | 192);
            tail.data[tail.limit + 1] = (byte) (128 | (codePoint & 63));
            tail.limit += 2;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 2);
        } else if (55296 <= codePoint && 57343 >= codePoint) {
            commonWriteUtf8CodePoint.writeByte(63);
        } else if (codePoint < 65536) {
            Segment tail2 = commonWriteUtf8CodePoint.writableSegment$okio(3);
            tail2.data[tail2.limit] = (byte) ((codePoint >> 12) | 224);
            tail2.data[tail2.limit + 1] = (byte) ((63 & (codePoint >> 6)) | 128);
            tail2.data[tail2.limit + 2] = (byte) (128 | (codePoint & 63));
            tail2.limit += 3;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 3);
        } else if (codePoint <= 1114111) {
            Segment tail3 = commonWriteUtf8CodePoint.writableSegment$okio(4);
            tail3.data[tail3.limit] = (byte) ((codePoint >> 18) | 240);
            tail3.data[tail3.limit + 1] = (byte) (((codePoint >> 12) & 63) | 128);
            tail3.data[tail3.limit + 2] = (byte) (((codePoint >> 6) & 63) | 128);
            tail3.data[tail3.limit + 3] = (byte) (128 | (codePoint & 63));
            tail3.limit += 4;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 4);
        } else {
            throw new IllegalArgumentException("Unexpected code point: 0x" + Util.toHexString(codePoint));
        }
        return commonWriteUtf8CodePoint;
    }

    public static final long commonWriteAll(Buffer commonWriteAll, Source source) throws IOException {
        Intrinsics.checkNotNullParameter(commonWriteAll, "$this$commonWriteAll");
        Intrinsics.checkNotNullParameter(source, "source");
        long totalBytesRead = 0;
        while (true) {
            long readCount = source.read(commonWriteAll, 8192);
            if (readCount != -1) {
                totalBytesRead += readCount;
            } else {
                return totalBytesRead;
            }
        }
    }

    public static final Buffer commonWrite(Buffer commonWrite, Source source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        long byteCount2 = byteCount;
        while (byteCount2 > 0) {
            long read = source.read(commonWrite, byteCount2);
            if (read == -1) {
                throw new EOFException();
            }
            byteCount2 -= read;
        }
        return commonWrite;
    }

    public static final Buffer commonWriteByte(Buffer commonWriteByte, int b) {
        Intrinsics.checkNotNullParameter(commonWriteByte, "$this$commonWriteByte");
        Segment tail = commonWriteByte.writableSegment$okio(1);
        byte[] bArr = tail.data;
        int i = tail.limit;
        tail.limit = i + 1;
        bArr[i] = (byte) b;
        commonWriteByte.setSize$okio(commonWriteByte.size() + 1);
        return commonWriteByte;
    }

    public static final Buffer commonWriteShort(Buffer commonWriteShort, int s) {
        Intrinsics.checkNotNullParameter(commonWriteShort, "$this$commonWriteShort");
        Segment tail = commonWriteShort.writableSegment$okio(2);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((s >>> 8) & 255);
        data[limit2] = (byte) (s & 255);
        tail.limit = limit2 + 1;
        commonWriteShort.setSize$okio(commonWriteShort.size() + 2);
        return commonWriteShort;
    }

    public static final Buffer commonWriteInt(Buffer commonWriteInt, int i) {
        Intrinsics.checkNotNullParameter(commonWriteInt, "$this$commonWriteInt");
        Segment tail = commonWriteInt.writableSegment$okio(4);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((i >>> 24) & 255);
        int limit3 = limit2 + 1;
        data[limit2] = (byte) ((i >>> 16) & 255);
        int limit4 = limit3 + 1;
        data[limit3] = (byte) ((i >>> 8) & 255);
        data[limit4] = (byte) (i & 255);
        tail.limit = limit4 + 1;
        commonWriteInt.setSize$okio(commonWriteInt.size() + 4);
        return commonWriteInt;
    }

    public static final Buffer commonWriteLong(Buffer commonWriteLong, long v) {
        Intrinsics.checkNotNullParameter(commonWriteLong, "$this$commonWriteLong");
        Segment tail = commonWriteLong.writableSegment$okio(8);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((v >>> 56) & 255);
        int limit3 = limit2 + 1;
        data[limit2] = (byte) ((v >>> 48) & 255);
        int limit4 = limit3 + 1;
        data[limit3] = (byte) ((v >>> 40) & 255);
        int limit5 = limit4 + 1;
        data[limit4] = (byte) ((v >>> 32) & 255);
        int limit6 = limit5 + 1;
        data[limit5] = (byte) ((v >>> 24) & 255);
        int limit7 = limit6 + 1;
        data[limit6] = (byte) ((v >>> 16) & 255);
        int limit8 = limit7 + 1;
        data[limit7] = (byte) ((v >>> 8) & 255);
        data[limit8] = (byte) (v & 255);
        tail.limit = limit8 + 1;
        commonWriteLong.setSize$okio(commonWriteLong.size() + 8);
        return commonWriteLong;
    }

    public static final void commonWrite(Buffer commonWrite, Buffer source, long byteCount) {
        Segment tail;
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        long byteCount2 = byteCount;
        if (!(source != commonWrite)) {
            throw new IllegalArgumentException("source == this".toString());
        }
        Util.checkOffsetAndCount(source.size(), 0L, byteCount2);
        while (byteCount2 > 0) {
            Segment segment = source.head;
            Intrinsics.checkNotNull(segment);
            int i = segment.limit;
            Intrinsics.checkNotNull(source.head);
            if (byteCount2 < i - r3.pos) {
                if (commonWrite.head != null) {
                    Segment segment2 = commonWrite.head;
                    Intrinsics.checkNotNull(segment2);
                    tail = segment2.prev;
                } else {
                    tail = null;
                }
                if (tail != null && tail.owner) {
                    if ((((long) tail.limit) + byteCount2) - ((long) (tail.shared ? 0 : tail.pos)) <= 8192) {
                        Segment segment3 = source.head;
                        Intrinsics.checkNotNull(segment3);
                        segment3.writeTo(tail, (int) byteCount2);
                        source.setSize$okio(source.size() - byteCount2);
                        commonWrite.setSize$okio(commonWrite.size() + byteCount2);
                        return;
                    }
                }
                Segment segment4 = source.head;
                Intrinsics.checkNotNull(segment4);
                source.head = segment4.split((int) byteCount2);
            }
            Segment segmentToMove = source.head;
            Intrinsics.checkNotNull(segmentToMove);
            long movedByteCount = segmentToMove.limit - segmentToMove.pos;
            source.head = segmentToMove.pop();
            if (commonWrite.head == null) {
                commonWrite.head = segmentToMove;
                segmentToMove.prev = segmentToMove;
                segmentToMove.next = segmentToMove.prev;
            } else {
                Segment segment5 = commonWrite.head;
                Intrinsics.checkNotNull(segment5);
                Segment tail2 = segment5.prev;
                Intrinsics.checkNotNull(tail2);
                tail2.push(segmentToMove).compact();
            }
            source.setSize$okio(source.size() - movedByteCount);
            commonWrite.setSize$okio(commonWrite.size() + movedByteCount);
            byteCount2 -= movedByteCount;
        }
    }

    public static final long commonRead(Buffer commonRead, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long byteCount2 = byteCount;
        if (!(byteCount2 >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount2).toString());
        }
        if (commonRead.size() == 0) {
            return -1L;
        }
        if (byteCount2 > commonRead.size()) {
            byteCount2 = commonRead.size();
        }
        sink.write(commonRead, byteCount2);
        return byteCount2;
    }

    public static final long commonIndexOf(Buffer commonIndexOf, byte b, long fromIndex, long toIndex) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        long fromIndex2 = fromIndex;
        long toIndex2 = toIndex;
        if (!(0 <= fromIndex2 && toIndex2 >= fromIndex2)) {
            throw new IllegalArgumentException(("size=" + commonIndexOf.size() + " fromIndex=" + fromIndex2 + " toIndex=" + toIndex2).toString());
        }
        if (toIndex2 > commonIndexOf.size()) {
            toIndex2 = commonIndexOf.size();
        }
        long j = -1;
        if (fromIndex2 == toIndex2) {
            return -1L;
        }
        Buffer $this$seek$iv = commonIndexOf;
        Segment s$iv = $this$seek$iv.head;
        if (s$iv == null) {
            return -1L;
        }
        if ($this$seek$iv.size() - fromIndex2 < fromIndex2) {
            long offset$iv = $this$seek$iv.size();
            while (offset$iv > fromIndex2) {
                long j2 = j;
                Segment segment = s$iv.prev;
                Intrinsics.checkNotNull(segment);
                s$iv = segment;
                offset$iv -= (long) (s$iv.limit - s$iv.pos);
                j = j2;
            }
            long j3 = j;
            Segment s = s$iv;
            long offset = offset$iv;
            if (s == null) {
                return j3;
            }
            long offset2 = offset;
            Segment s2 = s;
            while (offset2 < toIndex2) {
                Buffer $this$seek$iv2 = $this$seek$iv;
                byte[] data = s2.data;
                int limit = (int) Math.min(s2.limit, (((long) s2.pos) + toIndex2) - offset2);
                for (int pos = (int) ((((long) s2.pos) + fromIndex2) - offset2); pos < limit; pos++) {
                    if (data[pos] == b) {
                        return ((long) (pos - s2.pos)) + offset2;
                    }
                }
                offset2 += (long) (s2.limit - s2.pos);
                fromIndex2 = offset2;
                Segment segment2 = s2.next;
                Intrinsics.checkNotNull(segment2);
                s2 = segment2;
                $this$seek$iv = $this$seek$iv2;
            }
            return j3;
        }
        long offset$iv2 = 0;
        while (true) {
            long nextOffset$iv = ((long) (s$iv.limit - s$iv.pos)) + offset$iv2;
            if (nextOffset$iv > fromIndex2) {
                break;
            }
            Segment segment3 = s$iv.next;
            Intrinsics.checkNotNull(segment3);
            s$iv = segment3;
            offset$iv2 = nextOffset$iv;
        }
        Segment s3 = s$iv;
        long offset3 = offset$iv2;
        if (s3 == null) {
            return -1L;
        }
        Segment s4 = s3;
        long offset4 = offset3;
        while (offset4 < toIndex2) {
            long offset$iv3 = offset$iv2;
            byte[] data2 = s4.data;
            Segment s5 = s3;
            int limit2 = (int) Math.min(s4.limit, (((long) s4.pos) + toIndex2) - offset4);
            for (int pos2 = (int) ((((long) s4.pos) + fromIndex2) - offset4); pos2 < limit2; pos2++) {
                if (data2[pos2] == b) {
                    return ((long) (pos2 - s4.pos)) + offset4;
                }
            }
            offset4 += (long) (s4.limit - s4.pos);
            fromIndex2 = offset4;
            Segment segment4 = s4.next;
            Intrinsics.checkNotNull(segment4);
            s4 = segment4;
            offset$iv2 = offset$iv3;
            s3 = s5;
        }
        return -1L;
    }

    public static final long commonIndexOf(Buffer commonIndexOf, ByteString bytes, long fromIndex) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        long fromIndex2 = fromIndex;
        if (!(bytes.size() > 0)) {
            throw new IllegalArgumentException("bytes is empty".toString());
        }
        if (!(fromIndex2 >= 0)) {
            throw new IllegalArgumentException(("fromIndex < 0: " + fromIndex2).toString());
        }
        long fromIndex$iv = fromIndex2;
        Segment s$iv = commonIndexOf.head;
        if (s$iv == null) {
            return -1L;
        }
        if (commonIndexOf.size() - fromIndex$iv < fromIndex$iv) {
            long offset$iv = commonIndexOf.size();
            while (offset$iv > fromIndex$iv) {
                Segment segment = s$iv.prev;
                Intrinsics.checkNotNull(segment);
                s$iv = segment;
                offset$iv -= (long) (s$iv.limit - s$iv.pos);
            }
            Segment s = s$iv;
            long offset = offset$iv;
            if (s == null) {
                return -1L;
            }
            long offset2 = offset;
            byte[] targetByteArray = bytes.internalArray$okio();
            int $i$f$commonIndexOf = targetByteArray[0];
            int bytesSize = bytes.size();
            long resultLimit = (commonIndexOf.size() - ((long) bytesSize)) + 1;
            Segment s2 = s;
            while (offset2 < resultLimit) {
                byte[] data = s2.data;
                int a$iv = s2.limit;
                long fromIndex3 = fromIndex2;
                long b$iv = (((long) s2.pos) + resultLimit) - offset2;
                long fromIndex$iv2 = fromIndex$iv;
                int a$iv2 = (int) Math.min(a$iv, b$iv);
                for (int pos = (int) ((((long) s2.pos) + fromIndex3) - offset2); pos < a$iv2; pos++) {
                    if (data[pos] == $i$f$commonIndexOf && rangeEquals(s2, pos + 1, targetByteArray, 1, bytesSize)) {
                        return ((long) (pos - s2.pos)) + offset2;
                    }
                }
                offset2 += (long) (s2.limit - s2.pos);
                fromIndex2 = offset2;
                Segment segment2 = s2.next;
                Intrinsics.checkNotNull(segment2);
                s2 = segment2;
                fromIndex$iv = fromIndex$iv2;
            }
            return -1L;
        }
        long offset$iv2 = 0;
        while (true) {
            long nextOffset$iv = ((long) (s$iv.limit - s$iv.pos)) + offset$iv2;
            if (nextOffset$iv > fromIndex$iv) {
                break;
            }
            Segment segment3 = s$iv.next;
            Intrinsics.checkNotNull(segment3);
            s$iv = segment3;
            offset$iv2 = nextOffset$iv;
        }
        Segment s3 = s$iv;
        long offset3 = offset$iv2;
        int i = 0;
        if (s3 == null) {
            return -1L;
        }
        Segment s4 = s3;
        long offset4 = offset3;
        byte[] targetByteArray2 = bytes.internalArray$okio();
        byte b0 = targetByteArray2[0];
        int bytesSize2 = bytes.size();
        long offset$iv3 = bytesSize2;
        long resultLimit2 = (commonIndexOf.size() - offset$iv3) + 1;
        while (offset4 < resultLimit2) {
            byte[] data2 = s4.data;
            int a$iv3 = s4.limit;
            Segment s5 = s3;
            int i2 = i;
            long b$iv2 = (((long) s4.pos) + resultLimit2) - offset4;
            long fromIndex4 = fromIndex2;
            long fromIndex5 = a$iv3;
            int $i$f$minOf = (int) Math.min(fromIndex5, b$iv2);
            for (int pos2 = (int) ((((long) s4.pos) + fromIndex4) - offset4); pos2 < $i$f$minOf; pos2++) {
                if (data2[pos2] == b0 && rangeEquals(s4, pos2 + 1, targetByteArray2, 1, bytesSize2)) {
                    return ((long) (pos2 - s4.pos)) + offset4;
                }
            }
            offset4 += (long) (s4.limit - s4.pos);
            fromIndex2 = offset4;
            Segment segment4 = s4.next;
            Intrinsics.checkNotNull(segment4);
            s4 = segment4;
            s3 = s5;
            i = i2;
        }
        return -1L;
    }

    public static final long commonIndexOfElement(Buffer commonIndexOfElement, ByteString targetBytes, long fromIndex) {
        ByteString targetBytes2 = targetBytes;
        int $i$f$commonIndexOfElement = 0;
        Intrinsics.checkNotNullParameter(commonIndexOfElement, "$this$commonIndexOfElement");
        Intrinsics.checkNotNullParameter(targetBytes2, "targetBytes");
        long fromIndex2 = fromIndex;
        if (!(fromIndex2 >= 0)) {
            throw new IllegalArgumentException(("fromIndex < 0: " + fromIndex2).toString());
        }
        Buffer $this$seek$iv = commonIndexOfElement;
        Segment s$iv = $this$seek$iv.head;
        if (s$iv == null) {
            return -1L;
        }
        if ($this$seek$iv.size() - fromIndex2 >= fromIndex2) {
            long offset$iv = 0;
            while (true) {
                long nextOffset$iv = ((long) (s$iv.limit - s$iv.pos)) + offset$iv;
                if (nextOffset$iv > fromIndex2) {
                    break;
                }
                Segment segment = s$iv.next;
                Intrinsics.checkNotNull(segment);
                s$iv = segment;
                offset$iv = nextOffset$iv;
                targetBytes2 = targetBytes;
            }
            Segment s = s$iv;
            long offset = offset$iv;
            if (s == null) {
                return -1L;
            }
            Segment s2 = s;
            long offset2 = offset;
            if (targetBytes2.size() == 2) {
                int b0 = targetBytes2.getByte(0);
                int b1 = targetBytes2.getByte(1);
                while (offset2 < commonIndexOfElement.size()) {
                    byte[] data = s2.data;
                    long offset$iv2 = offset$iv;
                    int limit = s2.limit;
                    for (int pos = (int) ((((long) s2.pos) + fromIndex2) - offset2); pos < limit; pos++) {
                        int b = data[pos];
                        if (b == b0 || b == b1) {
                            int limit2 = s2.pos;
                            return ((long) (pos - limit2)) + offset2;
                        }
                    }
                    int limit3 = s2.limit;
                    offset2 += (long) (limit3 - s2.pos);
                    fromIndex2 = offset2;
                    Segment segment2 = s2.next;
                    Intrinsics.checkNotNull(segment2);
                    s2 = segment2;
                    offset$iv = offset$iv2;
                }
            } else {
                byte[] targetByteArray = targetBytes.internalArray$okio();
                while (offset2 < commonIndexOfElement.size()) {
                    byte[] data2 = s2.data;
                    int pos2 = (int) ((((long) s2.pos) + fromIndex2) - offset2);
                    int limit4 = s2.limit;
                    while (pos2 < limit4) {
                        byte b2 = data2[pos2];
                        int limit5 = limit4;
                        int limit6 = targetByteArray.length;
                        byte[] targetByteArray2 = targetByteArray;
                        int i = 0;
                        while (i < limit6) {
                            int i2 = i;
                            byte t = targetByteArray2[i2];
                            if (b2 == t) {
                                return ((long) (pos2 - s2.pos)) + offset2;
                            }
                            i = i2 + 1;
                        }
                        pos2++;
                        limit4 = limit5;
                        targetByteArray = targetByteArray2;
                    }
                    byte[] targetByteArray3 = targetByteArray;
                    offset2 += (long) (s2.limit - s2.pos);
                    fromIndex2 = offset2;
                    Segment segment3 = s2.next;
                    Intrinsics.checkNotNull(segment3);
                    s2 = segment3;
                    targetByteArray = targetByteArray3;
                }
            }
            return -1L;
        }
        long offset$iv3 = $this$seek$iv.size();
        while (offset$iv3 > fromIndex2) {
            Segment segment4 = s$iv.prev;
            Intrinsics.checkNotNull(segment4);
            s$iv = segment4;
            offset$iv3 -= (long) (s$iv.limit - s$iv.pos);
        }
        Segment s3 = s$iv;
        long offset3 = offset$iv3;
        if (s3 == null) {
            return -1L;
        }
        Segment s4 = s3;
        long offset4 = offset3;
        if (targetBytes2.size() == 2) {
            int b02 = targetBytes2.getByte(0);
            int b12 = targetBytes2.getByte(1);
            while (offset4 < commonIndexOfElement.size()) {
                int $i$f$commonIndexOfElement2 = $i$f$commonIndexOfElement;
                byte[] data3 = s4.data;
                Segment s5 = s3;
                int pos3 = (int) ((((long) s4.pos) + fromIndex2) - offset4);
                int limit7 = s4.limit;
                while (pos3 < limit7) {
                    int limit8 = limit7;
                    int limit9 = data3[pos3];
                    if (limit9 == b02 || limit9 == b12) {
                        int b3 = s4.pos;
                        return ((long) (pos3 - b3)) + offset4;
                    }
                    pos3++;
                    limit7 = limit8;
                }
                int limit10 = s4.limit;
                offset4 += (long) (limit10 - s4.pos);
                fromIndex2 = offset4;
                Segment segment5 = s4.next;
                Intrinsics.checkNotNull(segment5);
                s4 = segment5;
                $i$f$commonIndexOfElement = $i$f$commonIndexOfElement2;
                s3 = s5;
            }
        } else {
            byte[] targetByteArray4 = targetBytes2.internalArray$okio();
            while (offset4 < commonIndexOfElement.size()) {
                byte[] data4 = s4.data;
                Buffer $this$seek$iv2 = $this$seek$iv;
                int pos4 = (int) ((((long) s4.pos) + fromIndex2) - offset4);
                int limit11 = s4.limit;
                while (pos4 < limit11) {
                    byte b4 = data4[pos4];
                    byte[] data5 = data4;
                    int length = targetByteArray4.length;
                    byte[] targetByteArray5 = targetByteArray4;
                    int i3 = 0;
                    while (i3 < length) {
                        int i4 = i3;
                        byte t2 = targetByteArray5[i4];
                        if (b4 == t2) {
                            return ((long) (pos4 - s4.pos)) + offset4;
                        }
                        i3 = i4 + 1;
                    }
                    pos4++;
                    data4 = data5;
                    targetByteArray4 = targetByteArray5;
                }
                byte[] targetByteArray6 = targetByteArray4;
                offset4 += (long) (s4.limit - s4.pos);
                fromIndex2 = offset4;
                Segment segment6 = s4.next;
                Intrinsics.checkNotNull(segment6);
                s4 = segment6;
                $this$seek$iv = $this$seek$iv2;
                targetByteArray4 = targetByteArray6;
            }
        }
        return -1L;
    }

    public static final boolean commonRangeEquals(Buffer commonRangeEquals, long offset, ByteString bytes, int bytesOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (offset < 0 || bytesOffset < 0 || byteCount < 0 || commonRangeEquals.size() - offset < byteCount || bytes.size() - bytesOffset < byteCount) {
            return false;
        }
        for (int i = 0; i < byteCount; i++) {
            if (commonRangeEquals.getByte(((long) i) + offset) != bytes.getByte(bytesOffset + i)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean commonEquals(Buffer commonEquals, Object other) {
        Intrinsics.checkNotNullParameter(commonEquals, "$this$commonEquals");
        if (commonEquals == other) {
            return true;
        }
        if (!(other instanceof Buffer) || commonEquals.size() != ((Buffer) other).size()) {
            return false;
        }
        if (commonEquals.size() == 0) {
            return true;
        }
        Segment sa = commonEquals.head;
        Intrinsics.checkNotNull(sa);
        Segment sb = ((Buffer) other).head;
        Intrinsics.checkNotNull(sb);
        int posA = sa.pos;
        int posB = sb.pos;
        long pos = 0;
        while (pos < commonEquals.size()) {
            long count = Math.min(sa.limit - posA, sb.limit - posB);
            for (long i = 0; i < count; i++) {
                int posA2 = posA + 1;
                int posB2 = posB + 1;
                if (sa.data[posA] != sb.data[posB]) {
                    return false;
                }
                posA = posA2;
                posB = posB2;
            }
            if (posA == sa.limit) {
                Segment sa2 = sa.next;
                Intrinsics.checkNotNull(sa2);
                posA = sa2.pos;
                sa = sa2;
            }
            if (posB == sb.limit) {
                Segment sb2 = sb.next;
                Intrinsics.checkNotNull(sb2);
                posB = sb2.pos;
                sb = sb2;
            }
            pos += count;
        }
        return true;
    }

    public static final int commonHashCode(Buffer commonHashCode) {
        Intrinsics.checkNotNullParameter(commonHashCode, "$this$commonHashCode");
        Segment s = commonHashCode.head;
        if (s == null) {
            return 0;
        }
        int result = 1;
        do {
            int limit = s.limit;
            for (int pos = s.pos; pos < limit; pos++) {
                result = (result * 31) + s.data[pos];
            }
            Segment segment = s.next;
            Intrinsics.checkNotNull(segment);
            s = segment;
        } while (s != commonHashCode.head);
        return result;
    }

    public static final Buffer commonCopy(Buffer commonCopy) {
        Intrinsics.checkNotNullParameter(commonCopy, "$this$commonCopy");
        Buffer result = new Buffer();
        if (commonCopy.size() == 0) {
            return result;
        }
        Segment head = commonCopy.head;
        Intrinsics.checkNotNull(head);
        Segment headCopy = head.sharedCopy();
        result.head = headCopy;
        headCopy.prev = result.head;
        headCopy.next = headCopy.prev;
        for (Segment s = head.next; s != head; s = s.next) {
            Segment segment = headCopy.prev;
            Intrinsics.checkNotNull(segment);
            Intrinsics.checkNotNull(s);
            segment.push(s.sharedCopy());
        }
        result.setSize$okio(commonCopy.size());
        return result;
    }

    public static final ByteString commonSnapshot(Buffer commonSnapshot) {
        Intrinsics.checkNotNullParameter(commonSnapshot, "$this$commonSnapshot");
        if (!(commonSnapshot.size() <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalStateException(("size > Int.MAX_VALUE: " + commonSnapshot.size()).toString());
        }
        return commonSnapshot.snapshot((int) commonSnapshot.size());
    }

    public static final ByteString commonSnapshot(Buffer commonSnapshot, int byteCount) {
        Intrinsics.checkNotNullParameter(commonSnapshot, "$this$commonSnapshot");
        if (byteCount == 0) {
            return ByteString.EMPTY;
        }
        Util.checkOffsetAndCount(commonSnapshot.size(), 0L, byteCount);
        int offset = 0;
        int segmentCount = 0;
        Segment s = commonSnapshot.head;
        while (offset < byteCount) {
            Intrinsics.checkNotNull(s);
            if (s.limit == s.pos) {
                throw new AssertionError("s.limit == s.pos");
            }
            offset += s.limit - s.pos;
            segmentCount++;
            s = s.next;
        }
        byte[][] segments = new byte[segmentCount][];
        int[] directory = new int[segmentCount * 2];
        int offset2 = 0;
        int segmentCount2 = 0;
        Segment s2 = commonSnapshot.head;
        while (offset2 < byteCount) {
            Intrinsics.checkNotNull(s2);
            segments[segmentCount2] = s2.data;
            offset2 += s2.limit - s2.pos;
            directory[segmentCount2] = Math.min(offset2, byteCount);
            directory[segments.length + segmentCount2] = s2.pos;
            s2.shared = true;
            segmentCount2++;
            s2 = s2.next;
        }
        return new SegmentedByteString(segments, directory);
    }
}
