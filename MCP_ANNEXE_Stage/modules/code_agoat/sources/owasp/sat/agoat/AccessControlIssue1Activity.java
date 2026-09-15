package owasp.sat.agoat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: AccessControlIssue1Activity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"Lowasp/sat/agoat/AccessControlIssue1Activity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "createPIN", "", "pinValue", "", "hashPIN", "isPinCorrect", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "verifyIfPinSet", "verifyPINView", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AccessControlIssue1Activity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (verifyIfPinSet()) {
            verifyPINView();
            return;
        }
        setContentView(R.layout.activity_access_control_issue1);
        Button setPINButton = (Button) findViewById(R.id.setPIN);
        setPINButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.AccessControlIssue1Activity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessControlIssue1Activity.onCreate$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(AccessControlIssue1Activity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText pinValue = (EditText) this$0.findViewById(R.id.setpin);
        if (this$0.createPIN(pinValue.getText().toString())) {
            Toast.makeText(this$0.getApplicationContext(), "PIN created. Please Login", 1).show();
            this$0.verifyPINView();
        } else {
            Toast.makeText(this$0.getApplicationContext(), "PIN not created", 1).show();
        }
    }

    private final boolean verifyIfPinSet() {
        SharedPreferences sharedPreferences = getSharedPreferences("pinDetails", 0);
        return sharedPreferences.getBoolean("pinSet", false);
    }

    private final boolean createPIN(String pinValue) {
        SharedPreferences sharedPreferences = getSharedPreferences("pinDetails", 0);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putBoolean("pinSet", true);
        editor1.putString(ContentProviderActivity.PIN, hashPIN(pinValue));
        return editor1.commit();
    }

    private final boolean isPinCorrect(String pinValue) {
        SharedPreferences sharedPreferences = getSharedPreferences("pinDetails", 0);
        String storedPin = sharedPreferences.getString(ContentProviderActivity.PIN, null);
        return storedPin != null && Intrinsics.areEqual(storedPin, hashPIN(pinValue));
    }

    private final void verifyPINView() {
        setContentView(R.layout.activity_access_verify_pin);
        Button verifyPINButton = (Button) findViewById(R.id.verifyPIN);
        verifyPINButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.AccessControlIssue1Activity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessControlIssue1Activity.verifyPINView$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void verifyPINView$lambda$1(AccessControlIssue1Activity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText pinValue = (EditText) this$0.findViewById(R.id.pinValue);
        if (this$0.isPinCorrect(pinValue.getText().toString())) {
            Toast.makeText(this$0.getApplicationContext(), "PIN Verified", 1).show();
            Intent myIntent = new Intent(this$0, (Class<?>) AccessControl1ViewActivity.class);
            this$0.startActivity(myIntent);
            return;
        }
        Toast.makeText(this$0.getApplicationContext(), "Incorrect PIN entered", 1).show();
    }

    private final String hashPIN(String pinValue) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = pinValue.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        byte[] bArrDigest = messageDigest.digest(bytes);
        Intrinsics.checkNotNullExpressionValue(bArrDigest, "getInstance(\"MD5\").digest(pinValue.toByteArray())");
        String md = ArraysKt.joinToString$default(bArrDigest, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: owasp.sat.agoat.AccessControlIssue1Activity$hashPIN$md$1
            public final CharSequence invoke(byte it) {
                String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(this, *args)");
                return str;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
        return md;
    }
}
