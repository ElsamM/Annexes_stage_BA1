package owasp.sat.agoat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InsecureStorageSharedPrefs.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lowasp/sat/agoat/InsecureStorageSharedPrefs;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InsecureStorageSharedPrefs extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecure_storage_shared_prefs);
        Button savebutton = (Button) findViewById(R.id.SPViewButton);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);
        savebutton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSharedPrefs$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageSharedPrefs.onCreate$lambda$1(this.f$0, username, password, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(InsecureStorageSharedPrefs this$0, EditText $username, EditText $password, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog.Builder builder = new AlertDialog.Builder(this$0);
        builder.setTitle("Login");
        SharedPreferences sharedPreference = this$0.getSharedPreferences("users", 0);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(ContentProviderActivity.USERNAME, $username.getText().toString());
        editor.putString("password", $password.getText().toString());
        if (editor.commit()) {
            builder.setMessage("Username and Password are verified");
            Toast.makeText(this$0.getApplicationContext(), "Username and Password are verified", 1).show();
        } else {
            builder.setMessage("There is an issue while verifying the username and password");
            Toast.makeText(this$0.getApplicationContext(), "There is an issue while verifying the username and password", 1).show();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSharedPrefs$$ExternalSyntheticLambda0
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
