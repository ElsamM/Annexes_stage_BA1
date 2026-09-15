package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.FileWriter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InsecureStorageTempActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lowasp/sat/agoat/InsecureStorageTempActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InsecureStorageTempActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecure_storage_temp);
        Button savebutton = (Button) findViewById(R.id.TempViewButton);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");
        savebutton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageTempActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageTempActivity.onCreate$lambda$1(this.f$0, username, password, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(InsecureStorageTempActivity this$0, EditText $username, EditText $password, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        try {
            File userinfo = File.createTempFile("users", "tmp", new File(this$0.getApplicationInfo().dataDir));
            userinfo.setReadable(true);
            userinfo.setWritable(true);
            FileWriter fw = new FileWriter(userinfo);
            fw.write("username is " + ((Object) $username.getText()) + "\n");
            fw.write("password is " + ((Object) $password.getText()) + "\n");
            fw.close();
            builder.setMessage("Username and Password are verified");
            Toast.makeText(this$0.getApplicationContext(), "Username and Password are verified", 1).show();
        } catch (Exception e) {
            builder.setMessage("There is an issue while verifying the username and password");
            Toast.makeText(this$0.getApplicationContext(), "There is an issue while verifying the username and password", 1).show();
            e.printStackTrace();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageTempActivity$$ExternalSyntheticLambda0
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
