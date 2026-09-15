package owasp.sat.agoat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InsecureStorageActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lowasp/sat/agoat/InsecureStorageActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InsecureStorageActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecure_storage);
        Button spButton = (Button) findViewById(R.id.SPButton);
        spButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        Button sp1Button = (Button) findViewById(R.id.SP1Button);
        sp1Button.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageActivity.onCreate$lambda$1(this.f$0, view);
            }
        });
        Button sqlButton = (Button) findViewById(R.id.SQLButton);
        sqlButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
        Button tempButton = (Button) findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
        Button sDcardButton = (Button) findViewById(R.id.SDcardButton);
        sDcardButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageActivity.onCreate$lambda$4(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(InsecureStorageActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InsecureStorageSharedPrefs.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(InsecureStorageActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InsecureStorageSharedPrefs1Activity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(InsecureStorageActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InsecureStorageSQLiteActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(InsecureStorageActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InsecureStorageTempActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(InsecureStorageActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) InsecureStorageSDCardActivity.class));
    }
}
