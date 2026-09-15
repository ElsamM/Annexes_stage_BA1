package androidx.biometric;

import android.os.CancellationSignal;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
class CancellationSignalProvider {
    private static final String TAG = "CancelSignalProvider";
    private CancellationSignal mBiometricCancellationSignal;
    private androidx.core.os.CancellationSignal mFingerprintCancellationSignal;
    private final Injector mInjector;

    interface Injector {
        CancellationSignal getBiometricCancellationSignal();

        androidx.core.os.CancellationSignal getFingerprintCancellationSignal();
    }

    CancellationSignalProvider() {
        this.mInjector = new Injector() { // from class: androidx.biometric.CancellationSignalProvider.1
            @Override // androidx.biometric.CancellationSignalProvider.Injector
            public CancellationSignal getBiometricCancellationSignal() {
                return Api16Impl.create();
            }

            @Override // androidx.biometric.CancellationSignalProvider.Injector
            public androidx.core.os.CancellationSignal getFingerprintCancellationSignal() {
                return new androidx.core.os.CancellationSignal();
            }
        };
    }

    CancellationSignalProvider(Injector injector) {
        this.mInjector = injector;
    }

    CancellationSignal getBiometricCancellationSignal() {
        if (this.mBiometricCancellationSignal == null) {
            this.mBiometricCancellationSignal = this.mInjector.getBiometricCancellationSignal();
        }
        return this.mBiometricCancellationSignal;
    }

    androidx.core.os.CancellationSignal getFingerprintCancellationSignal() {
        if (this.mFingerprintCancellationSignal == null) {
            this.mFingerprintCancellationSignal = this.mInjector.getFingerprintCancellationSignal();
        }
        return this.mFingerprintCancellationSignal;
    }

    void cancel() {
        if (this.mBiometricCancellationSignal != null) {
            try {
                Api16Impl.cancel(this.mBiometricCancellationSignal);
            } catch (NullPointerException e) {
                Log.e(TAG, "Got NPE while canceling biometric authentication.", e);
            }
            this.mBiometricCancellationSignal = null;
        }
        if (this.mFingerprintCancellationSignal != null) {
            try {
                this.mFingerprintCancellationSignal.cancel();
            } catch (NullPointerException e2) {
                Log.e(TAG, "Got NPE while canceling fingerprint authentication.", e2);
            }
            this.mFingerprintCancellationSignal = null;
        }
    }

    private static class Api16Impl {
        private Api16Impl() {
        }

        static CancellationSignal create() {
            return new CancellationSignal();
        }

        static void cancel(CancellationSignal cancellationSignal) {
            cancellationSignal.cancel();
        }
    }
}
