package com.budiyev.android.codescanner;

import com.budiyev.android.codescanner.Utils;

/* JADX INFO: loaded from: classes2.dex */
public interface ErrorCallback {
    public static final ErrorCallback SUPPRESS = new Utils.SuppressErrorCallback();

    void onError(Throwable th);
}
