package owasp.sat.agoat;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InsecureStorageSQLiteActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lowasp/sat/agoat/InsecureStorageSQLiteActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mDB", "Landroid/database/sqlite/SQLiteDatabase;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InsecureStorageSQLiteActivity extends AppCompatActivity {
    private SQLiteDatabase mDB;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mDB = openOrCreateDatabase("aGoat", 0, null);
            SQLiteDatabase sQLiteDatabase = this.mDB;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR)");
            }
        } catch (Exception e) {
            Log.e("Error:", "Error occurred while creating database: " + e.getMessage());
        }
        setContentView(R.layout.activity_insecure_storage_sqlite);
        Button saveButton = (Button) findViewById(R.id.SQLButton);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);
        saveButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSQLiteActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageSQLiteActivity.onCreate$lambda$1(this.f$0, username, password, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(InsecureStorageSQLiteActivity this$0, EditText $username, EditText $password, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog.Builder builder = new AlertDialog.Builder(this$0);
        builder.setTitle("Login");
        String qry = "INSERT INTO users (username, password) VALUES('" + ((Object) $username.getText()) + "','" + ((Object) $password.getText()) + "')";
        try {
            SQLiteDatabase sQLiteDatabase = this$0.mDB;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.execSQL(qry);
            }
            builder.setMessage("Username and Password are verified");
            Toast.makeText(this$0.getApplicationContext(), "Username and Password are verified", 1).show();
        } catch (Exception e) {
            Log.d("Error", "Error occurred when inserting data into database: " + e.getMessage());
            builder.setMessage("There is an issue while verifying the username and password- " + e.getMessage());
            Toast.makeText(this$0.getApplicationContext(), "There is an issue while verifying the username and password - " + e.getMessage(), 1).show();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSQLiteActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
        Log.v("Query", qry);
    }
}
