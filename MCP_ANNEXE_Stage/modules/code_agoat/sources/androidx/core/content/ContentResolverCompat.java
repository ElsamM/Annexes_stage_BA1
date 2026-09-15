package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;

/* JADX INFO: loaded from: classes2.dex */
public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.database.Cursor query(android.content.ContentResolver r8, android.net.Uri r9, java.lang.String[] r10, java.lang.String r11, java.lang.String[] r12, java.lang.String r13, androidx.core.os.CancellationSignal r14) throws java.lang.Exception {
        /*
            if (r14 == 0) goto L10
            java.lang.Object r0 = r14.getCancellationSignalObject()     // Catch: java.lang.Exception -> L8
            goto L11
        L8:
            r0 = move-exception
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            goto L28
        L10:
            r0 = 0
        L11:
            android.os.CancellationSignal r0 = (android.os.CancellationSignal) r0     // Catch: java.lang.Exception -> L21
            r7 = r0
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            android.database.Cursor r8 = androidx.core.content.ContentResolverCompat.Api16Impl.query(r1, r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L1f
            return r8
        L1f:
            r0 = move-exception
            goto L28
        L21:
            r0 = move-exception
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
        L28:
            boolean r8 = r0 instanceof android.os.OperationCanceledException
            if (r8 == 0) goto L32
            androidx.core.os.OperationCanceledException r8 = new androidx.core.os.OperationCanceledException
            r8.<init>()
            throw r8
        L32:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.ContentResolverCompat.query(android.content.ContentResolver, android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, androidx.core.os.CancellationSignal):android.database.Cursor");
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static Cursor query(ContentResolver contentResolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        }
    }
}
