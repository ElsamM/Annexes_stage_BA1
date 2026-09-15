package owasp.sat.agoat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ClipboardActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lowasp/sat/agoat/ClipboardActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ClipboardActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard);
        final EditText ccValue = (EditText) findViewById(R.id.cc);
        Button verifyCC = (Button) findViewById(R.id.verifyCC);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        verifyCC.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.ClipboardActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardActivity.onCreate$lambda$1(ccValue, this, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(EditText $ccValue, ClipboardActivity this$0, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        String ccValue1 = StringsKt.trim((CharSequence) $ccValue.getText().toString()).toString();
        if (ccValue1.length() > 0) {
            int otp = ((Number) CollectionsKt.first(CollectionsKt.shuffled(new IntRange(1000, 9999)))).intValue();
            Object systemService = this$0.getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ClipboardManager clipboard = (ClipboardManager) systemService;
            ClipData clip = ClipData.newPlainText("CC Card", String.valueOf(otp));
            clipboard.setPrimaryClip(clip);
            builder.setMessage("OTP Generated and Copied: " + otp);
            Toast.makeText(this$0, "OTP Generated and Copied: " + otp, 1).show();
        } else {
            builder.setMessage("Credit Card shouldn't be blank");
            Toast.makeText(this$0, "Credit Card shouldn't be blank", 1).show();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.ClipboardActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
    }
}
