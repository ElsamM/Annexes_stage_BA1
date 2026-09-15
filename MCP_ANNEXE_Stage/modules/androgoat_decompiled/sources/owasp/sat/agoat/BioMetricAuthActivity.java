package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import owasp.sat.agoat.BioMetricAuthActivity;

/* JADX INFO: compiled from: BioMetricAuthActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lowasp/sat/agoat/BioMetricAuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "biometricPrompt", "Landroidx/biometric/BiometricPrompt;", "executor", "Ljava/util/concurrent/Executor;", "promptInfo", "Landroidx/biometric/BiometricPrompt$PromptInfo;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class BioMetricAuthActivity extends AppCompatActivity {
    private BiometricPrompt biometricPrompt;
    private Executor executor;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric_auth);
        Button bioAuth = (Button) findViewById(R.id.bioauth);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Biometric login");
        Executor mainExecutor = ContextCompat.getMainExecutor(this);
        Intrinsics.checkNotNullExpressionValue(mainExecutor, "getMainExecutor(this)");
        this.executor = mainExecutor;
        BioMetricAuthActivity bioMetricAuthActivity = this;
        Executor executor = this.executor;
        if (executor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("executor");
            executor = null;
        }
        this.biometricPrompt = new BiometricPrompt(bioMetricAuthActivity, executor, new AnonymousClass1(builder, this));
        BiometricPrompt.PromptInfo promptInfoBuild = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Login").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Cancel").build();
        Intrinsics.checkNotNullExpressionValue(promptInfoBuild, "Builder()\n            .s…el\")\n            .build()");
        this.promptInfo = promptInfoBuild;
        bioAuth.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.BioMetricAuthActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BioMetricAuthActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: renamed from: owasp.sat.agoat.BioMetricAuthActivity$onCreate$1, reason: invalid class name */
    /* JADX INFO: compiled from: BioMetricAuthActivity.kt */
    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0003H\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"owasp/sat/agoat/BioMetricAuthActivity$onCreate$1", "Landroidx/biometric/BiometricPrompt$AuthenticationCallback;", "onAuthenticationError", "", "errorCode", "", "errString", "", "onAuthenticationFailed", "onAuthenticationSucceeded", "result", "Landroidx/biometric/BiometricPrompt$AuthenticationResult;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class AnonymousClass1 extends BiometricPrompt.AuthenticationCallback {
        final /* synthetic */ AlertDialog.Builder $builder;
        final /* synthetic */ BioMetricAuthActivity this$0;

        AnonymousClass1(AlertDialog.Builder $builder, BioMetricAuthActivity $receiver) {
            this.$builder = $builder;
            this.this$0 = $receiver;
        }

        @Override // androidx.biometric.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            Intrinsics.checkNotNullParameter(errString, "errString");
            super.onAuthenticationError(errorCode, errString);
            this.$builder.setMessage("Authentication error: " + ((Object) errString));
            Toast.makeText(this.this$0.getApplicationContext(), "Authentication error: " + ((Object) errString), 0).show();
        }

        @Override // androidx.biometric.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            Intrinsics.checkNotNullParameter(result, "result");
            super.onAuthenticationSucceeded(result);
            this.$builder.setMessage("Authentication succeeded!");
            Toast.makeText(this.this$0.getApplicationContext(), "Authentication succeeded! ", 0).show();
            this.$builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.BioMetricAuthActivity$onCreate$1$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = this.$builder.create();
            Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
            dialog.show();
        }

        @Override // androidx.biometric.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            this.$builder.setMessage("Authentication failed");
            Toast.makeText(this.this$0.getApplicationContext(), "Authentication failed", 0).show();
            this.$builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.BioMetricAuthActivity$onCreate$1$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            final AlertDialog dialog = this.$builder.create();
            Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
            dialog.show();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: owasp.sat.agoat.BioMetricAuthActivity$onCreate$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BioMetricAuthActivity.AnonymousClass1.onAuthenticationFailed$lambda$2(dialog);
                }
            }, 3000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onAuthenticationFailed$lambda$2(AlertDialog dialog) {
            Intrinsics.checkNotNullParameter(dialog, "$dialog");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(BioMetricAuthActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BiometricManager biometricManager = BiometricManager.from(this$0);
        Intrinsics.checkNotNullExpressionValue(biometricManager, "from(this)");
        switch (biometricManager.canAuthenticate(255)) {
            case 0:
                BiometricPrompt biometricPrompt = this$0.biometricPrompt;
                BiometricPrompt.PromptInfo promptInfo = null;
                if (biometricPrompt == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("biometricPrompt");
                    biometricPrompt = null;
                }
                BiometricPrompt.PromptInfo promptInfo2 = this$0.promptInfo;
                if (promptInfo2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("promptInfo");
                } else {
                    promptInfo = promptInfo2;
                }
                biometricPrompt.authenticate(promptInfo);
                break;
            case 1:
                Toast.makeText(this$0, "Biometric features are currently unavailable.", 1).show();
                break;
            case 11:
                Toast.makeText(this$0, "The user hasn't associated any biometric credentials.", 1).show();
                break;
            case 12:
                Toast.makeText(this$0, "No biometric features available on this device.", 1).show();
                break;
        }
    }
}
