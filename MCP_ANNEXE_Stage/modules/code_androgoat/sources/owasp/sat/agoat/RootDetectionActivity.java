package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RootDetectionActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014¨\u0006\n"}, d2 = {"Lowasp/sat/agoat/RootDetectionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "isRooted", "", "isRooted1", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class RootDetectionActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_detection);
        Button rootBt = (Button) findViewById(R.id.rootCheck);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Root Detection");
        rootBt.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.RootDetectionActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RootDetectionActivity.onCreate$lambda$1(this.f$0, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(RootDetectionActivity this$0, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        if (this$0.isRooted()) {
            builder.setMessage("Device is rooted");
            Toast.makeText(this$0.getApplicationContext(), "Device is rooted", 1).show();
        } else {
            Toast.makeText(this$0.getApplicationContext(), "Device is not rooted", 1).show();
            builder.setMessage("Device is not rooted");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.RootDetectionActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
    }

    public final boolean isRooted() {
        String[] file = {"/system/app/Superuser/Superuser.apk", "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su", "re.robv.android.xposed.installer-1.apk", "/data/app/eu.chainfire.supersu-1/base.apk"};
        boolean result = false;
        for (String files : file) {
            File f = new File(files);
            result = f.exists();
            if (result) {
                break;
            }
        }
        return result;
    }

    public final boolean isRooted1() {
        try {
            Runtime.getRuntime().exec(new String[]{"su", "ls /data/data/"});
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
