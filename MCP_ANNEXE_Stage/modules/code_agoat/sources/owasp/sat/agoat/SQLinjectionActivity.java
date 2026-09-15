package owasp.sat.agoat;

import android.content.DialogInterface;
import android.database.Cursor;
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

/* JADX INFO: compiled from: SQLinjectionActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lowasp/sat/agoat/SQLinjectionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mDB", "Landroid/database/sqlite/SQLiteDatabase;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class SQLinjectionActivity extends AppCompatActivity {
    private SQLiteDatabase mDB;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlinjection);
        Button sqliButton = (Button) findViewById(R.id.SQLiButton);
        final EditText username = (EditText) findViewById(R.id.userName);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search Users");
        sqliButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.SQLinjectionActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SQLinjectionActivity.onCreate$lambda$1(username, this, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(EditText $username, SQLinjectionActivity this$0, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        String qry = "SELECT * FROM users WHERE username='" + ((Object) $username.getText()) + "'";
        try {
            this$0.mDB = this$0.openOrCreateDatabase("aGoat", 0, null);
            SQLiteDatabase sQLiteDatabase = this$0.mDB;
            Cursor qryResult = sQLiteDatabase != null ? sQLiteDatabase.rawQuery(qry, null) : null;
            StringBuilder strb = new StringBuilder("");
            if (qryResult == null || qryResult.getCount() <= 0) {
                strb.append("User: (" + ((Object) $username.getText()) + ") not found");
            } else {
                qryResult.moveToFirst();
                do {
                    strb.append("Username: (" + qryResult.getString(1) + ") password: (" + qryResult.getString(2) + ")\n");
                } while (qryResult.moveToNext());
                qryResult.close();
            }
            builder.setMessage("Users Found:\n " + ((Object) strb));
            Toast.makeText(this$0, strb.toString(), 1).show();
            Log.e("QueryResult", strb.toString());
        } catch (Exception e) {
            Log.d("Error", "Error occurred when querying database: " + e.getMessage());
            builder.setMessage("Error occurred:\n " + e.getMessage());
            Toast.makeText(this$0.getApplicationContext(), "Error occurred: " + e.getMessage(), 1).show();
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.SQLinjectionActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
        dialog.show();
        Log.v("Query", qry);
        SQLiteDatabase sQLiteDatabase2 = this$0.mDB;
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.close();
        }
    }
}
