package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.EnvironmentCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: EmulatorDetectionActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014¨\u0006\t"}, d2 = {"Lowasp/sat/agoat/EmulatorDetectionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "isEmulator", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class EmulatorDetectionActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator_detection);
        Button emuButton = (Button) findViewById(R.id.EmulatorCheck);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Emulator Detection");
        emuButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.EmulatorDetectionActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EmulatorDetectionActivity.onCreate$lambda$1(this.f$0, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(EmulatorDetectionActivity this$0, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        if (this$0.isEmulator()) {
            builder.setMessage("This device is an Emulator");
            Toast.makeText(this$0.getApplicationContext(), "This device is an Emulator", 1).show();
        } else {
            builder.setMessage("This device is not an Emulator");
            Toast.makeText(this$0.getApplicationContext(), "This device is not an Emulator", 1).show();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.EmulatorDetectionActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
    }

    private final boolean isEmulator() {
        String buildDetails = (Build.FINGERPRINT + Build.DEVICE + Build.MODEL + Build.BRAND + Build.PRODUCT + Build.MANUFACTURER + Build.HARDWARE).toLowerCase();
        Intrinsics.checkNotNullExpressionValue(buildDetails, "this as java.lang.String).toLowerCase()");
        return StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "generic", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) EnvironmentCompat.MEDIA_UNKNOWN, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "emulator", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "sdk", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "vbox", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "genymotion", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "x86", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "goldfish", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) buildDetails, (CharSequence) "test-keys", false, 2, (Object) null);
    }
}
