package owasp.sat.agoat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import kotlin.Metadata;

/* JADX INFO: compiled from: InputValidationsOSCMDInjectionMain2Activity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lowasp/sat/agoat/InputValidationsOSCMDInjectionMain2Activity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InputValidationsOSCMDInjectionMain2Activity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_validations_oscmdinjection_main2);
        Button runButton = (Button) findViewById(R.id.run);
        final TextView result = (TextView) findViewById(R.id.result);
        final EditText ip = (EditText) findViewById(R.id.ip);
        runButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InputValidationsOSCMDInjectionMain2Activity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                InputValidationsOSCMDInjectionMain2Activity.onCreate$lambda$0(ip, result, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(EditText $ip, TextView $result, View it) throws IOException {
        String ip1 = "ping " + ((Object) $ip.getText());
        StringBuffer output1 = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(ip1);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                output1.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        $result.setText(output1.toString());
    }
}
