package owasp.sat.agoat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: HardCodeActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lowasp/sat/agoat/HardCodeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "promoCode", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class HardCodeActivity extends AppCompatActivity {
    private final String promoCode = "NEW2019";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_code);
        Button verifyButton = (Button) findViewById(R.id.hardcode1);
        final TextView priceValue = (TextView) findViewById(R.id.price);
        final EditText promoCodeValue = (EditText) findViewById(R.id.promocode);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        verifyButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.HardCodeActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HardCodeActivity.onCreate$lambda$1(promoCodeValue, this, priceValue, builder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(EditText $promoCodeValue, HardCodeActivity this$0, TextView $priceValue, AlertDialog.Builder builder, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(builder, "$builder");
        if (StringsKt.equals($promoCodeValue.getText().toString(), this$0.promoCode, true)) {
            $priceValue.setText("0");
            builder.setMessage("Congratulations! You got this product for free");
            Toast.makeText(this$0, "Congratulations! You got this product for free", 1).show();
        } else {
            builder.setMessage("Sorry! Incorrect Promocode was entered");
            Toast.makeText(this$0, "Sorry! Incorrect Promocode was entered", 1).show();
            $priceValue.setText("2000");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.HardCodeActivity$$ExternalSyntheticLambda1
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
