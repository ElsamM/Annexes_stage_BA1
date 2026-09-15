package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CloudServicesActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000f"}, d2 = {"Lowasp/sat/agoat/CloudServicesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "aws_access_key_id", "", "aws_secret_access_key", "output", "getOutput", "()Ljava/lang/String;", "region", "getRegion", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CloudServicesActivity extends AppCompatActivity {
    private final String aws_access_key_id = "AKIAX56QKKOLPQ7G7ABC";
    private final String aws_secret_access_key = "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";
    private final String output = "json";
    private final String region = "ap-south-2";

    public final String getOutput() {
        return this.output;
    }

    public final String getRegion() {
        return this.region;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable$default(this, null, null, 3, null);
        setContentView(R.layout.activity_cloud_services);
        Button cloudServices = (Button) findViewById(R.id.viewCloudServices);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");
        cloudServices.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.CloudServicesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CloudServicesActivity.onCreate$lambda$1(builder, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(AlertDialog.Builder builder, CloudServicesActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(builder, "$builder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        builder.setMessage("Connected to AWS account using Access key " + this$0.aws_access_key_id + " and secret key " + this$0.aws_secret_access_key);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.CloudServicesActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
        Log.d("[Info]", "Connected to AWS account using Access key " + this$0.aws_access_key_id + " and secret key " + this$0.aws_secret_access_key);
        this$0.setContentView(R.layout.activity_cloud_services_view);
    }
}
