package okio;

import java.io.IOException;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.AbstractList;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Options.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"}, k = 1, mv = {1, 4, 0})
public final class Options extends AbstractList<ByteString> implements RandomAccess {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    @JvmStatic
    public static final Options of(ByteString... byteStringArr) {
        return INSTANCE.of(byteStringArr);
    }

    private Options(ByteString[] byteStrings, int[] trie) {
        this.byteStrings = byteStrings;
        this.trie = trie;
    }

    public /* synthetic */ Options(ByteString[] byteStrings, int[] trie, DefaultConstructorMarker $constructor_marker) {
        this(byteStrings, trie);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains(byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf(byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf(byteString);
    }

    /* JADX INFO: renamed from: getByteStrings$okio, reason: from getter */
    public final ByteString[] getByteStrings() {
        return this.byteStrings;
    }

    /* JADX INFO: renamed from: getTrie$okio, reason: from getter */
    public final int[] getTrie() {
        return this.trie;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int index) {
        return this.byteStrings[index];
    }

    /* JADX INFO: compiled from: Options.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"}, k = 1, mv = {1, 4, 0})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:61:0x0115, code lost:
        
            continue;
         */
        @kotlin.jvm.JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final okio.Options of(okio.ByteString... r21) throws java.io.IOException {
            /*
                Method dump skipped, instruction units count: 374
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.of(okio.ByteString[]):okio.Options");
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) throws IOException {
            companion.buildTrieRecursive((i4 & 1) != 0 ? 0L : j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long nodeOffset, Buffer node, int byteStringOffset, List<? extends ByteString> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) throws IOException {
            int fromIndex2;
            ByteString from;
            int fromIndex3;
            int rangeEnd;
            int rangeEnd2;
            int prefixIndex;
            Buffer childNodes;
            long childNodesOffset;
            int rangeEnd3;
            int prefixIndex2 = byteStringOffset;
            if (!(fromIndex < toIndex)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i = fromIndex; i < toIndex; i++) {
                if (!(byteStrings.get(i).size() >= prefixIndex2)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ByteString from2 = byteStrings.get(fromIndex);
            ByteString to = byteStrings.get(toIndex - 1);
            if (prefixIndex2 != from2.size()) {
                fromIndex2 = fromIndex;
                from = from2;
                fromIndex3 = -1;
            } else {
                int prefixIndex3 = indexes.get(fromIndex).intValue();
                int fromIndex4 = fromIndex + 1;
                fromIndex2 = fromIndex4;
                from = byteStrings.get(fromIndex4);
                fromIndex3 = prefixIndex3;
            }
            if (from.getByte(prefixIndex2) != to.getByte(prefixIndex2)) {
                int selectChoiceCount = 1;
                for (int i2 = fromIndex2 + 1; i2 < toIndex; i2++) {
                    if (byteStrings.get(i2 - 1).getByte(prefixIndex2) != byteStrings.get(i2).getByte(prefixIndex2)) {
                        selectChoiceCount++;
                    }
                }
                long childNodesOffset2 = nodeOffset + getIntCount(node) + ((long) 2) + ((long) (selectChoiceCount * 2));
                node.writeInt(selectChoiceCount);
                node.writeInt(fromIndex3);
                for (int i3 = fromIndex2; i3 < toIndex; i3++) {
                    byte rangeByte = byteStrings.get(i3).getByte(prefixIndex2);
                    if (i3 == fromIndex2 || rangeByte != byteStrings.get(i3 - 1).getByte(prefixIndex2)) {
                        int other$iv = rangeByte & UByte.MAX_VALUE;
                        node.writeInt(other$iv);
                    }
                }
                Buffer childNodes2 = new Buffer();
                int rangeStart = fromIndex2;
                while (rangeStart < toIndex) {
                    byte rangeByte2 = byteStrings.get(rangeStart).getByte(prefixIndex2);
                    int selectChoiceCount2 = selectChoiceCount;
                    int selectChoiceCount3 = rangeStart + 1;
                    while (true) {
                        if (selectChoiceCount3 >= toIndex) {
                            rangeEnd = toIndex;
                            break;
                        }
                        int i4 = selectChoiceCount3;
                        if (rangeByte2 != byteStrings.get(selectChoiceCount3).getByte(prefixIndex2)) {
                            rangeEnd = i4;
                            break;
                        }
                        selectChoiceCount3 = i4 + 1;
                    }
                    if (rangeStart + 1 != rangeEnd) {
                        rangeEnd2 = rangeEnd;
                    } else {
                        int i5 = byteStringOffset + 1;
                        rangeEnd2 = rangeEnd;
                        int rangeEnd4 = byteStrings.get(rangeStart).size();
                        if (i5 == rangeEnd4) {
                            node.writeInt(indexes.get(rangeStart).intValue());
                            prefixIndex = fromIndex3;
                            rangeEnd3 = rangeEnd2;
                            long j = childNodesOffset2;
                            childNodes = childNodes2;
                            childNodesOffset = j;
                        }
                        rangeStart = rangeEnd3;
                        long j2 = childNodesOffset;
                        childNodes2 = childNodes;
                        childNodesOffset2 = j2;
                        fromIndex3 = prefixIndex;
                        selectChoiceCount = selectChoiceCount2;
                        prefixIndex2 = byteStringOffset;
                    }
                    node.writeInt(((int) (getIntCount(childNodes2) + childNodesOffset2)) * (-1));
                    prefixIndex = fromIndex3;
                    long j3 = childNodesOffset2;
                    childNodes = childNodes2;
                    childNodesOffset = j3;
                    rangeEnd3 = rangeEnd2;
                    buildTrieRecursive(childNodesOffset, childNodes, byteStringOffset + 1, byteStrings, rangeStart, rangeEnd3, indexes);
                    rangeStart = rangeEnd3;
                    long j22 = childNodesOffset;
                    childNodes2 = childNodes;
                    childNodesOffset2 = j22;
                    fromIndex3 = prefixIndex;
                    selectChoiceCount = selectChoiceCount2;
                    prefixIndex2 = byteStringOffset;
                }
                node.writeAll(childNodes2);
                return;
            }
            int prefixIndex4 = fromIndex3;
            int scanByteCount = 0;
            int iMin = Math.min(from.size(), to.size());
            for (int i6 = byteStringOffset; i6 < iMin && from.getByte(i6) == to.getByte(i6); i6++) {
                scanByteCount++;
            }
            long childNodesOffset3 = nodeOffset + getIntCount(node) + ((long) 2) + ((long) scanByteCount) + 1;
            node.writeInt(-scanByteCount);
            node.writeInt(prefixIndex4);
            int i7 = byteStringOffset + scanByteCount;
            for (int i8 = byteStringOffset; i8 < i7; i8++) {
                byte $this$and$iv = from.getByte(i8);
                node.writeInt($this$and$iv & UByte.MAX_VALUE);
            }
            if (fromIndex2 + 1 == toIndex) {
                if (byteStringOffset + scanByteCount == byteStrings.get(fromIndex2).size()) {
                    node.writeInt(indexes.get(fromIndex2).intValue());
                    return;
                }
                throw new IllegalStateException("Check failed.".toString());
            }
            Buffer childNodes3 = new Buffer();
            node.writeInt(((int) (getIntCount(childNodes3) + childNodesOffset3)) * (-1));
            int scanByteCount2 = fromIndex2;
            buildTrieRecursive(childNodesOffset3, childNodes3, byteStringOffset + scanByteCount, byteStrings, scanByteCount2, toIndex, indexes);
            node.writeAll(childNodes3);
        }

        private final long getIntCount(Buffer $this$intCount) {
            return $this$intCount.size() / ((long) 4);
        }
    }
}
