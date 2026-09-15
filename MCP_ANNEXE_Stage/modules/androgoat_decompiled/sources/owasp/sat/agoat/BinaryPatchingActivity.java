package owasp.sat.agoat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BinaryPatchingActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lowasp/sat/agoat/BinaryPatchingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "isAdmin", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class BinaryPatchingActivity extends AppCompatActivity {
    private final boolean isAdmin;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_patching);
        TextView isAdminText = (TextView) findViewById(R.id.isAdminText);
        Button adminButton = (Button) findViewById(R.id.adminButton);
        if (this.isAdmin) {
            isAdminText.setText("You are Admin Now");
            adminButton.setBackgroundColor(Color.parseColor("#000000"));
            adminButton.setTextColor(Color.parseColor("#FFFFFF"));
            adminButton.setEnabled(true);
        }
        adminButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.BinaryPatchingActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BinaryPatchingActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(BinaryPatchingActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0, "You clicked on Administration button", 1).show();
    }
}
