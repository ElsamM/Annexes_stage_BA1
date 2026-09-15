package owasp.sat.agoat;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: QRCodeXSSActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014J\b\u0010\t\u001a\u00020\u0006H\u0014J\b\u0010\n\u001a\u00020\u0006H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lowasp/sat/agoat/QRCodeXSSActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "codeScanner", "Lcom/budiyev/android/codescanner/CodeScanner;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class QRCodeXSSActivity extends AppCompatActivity {
    private CodeScanner codeScanner;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_xssactivity);
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, TypedValues.TYPE_TARGET);
        }
        CodeScannerView scannerView = (CodeScannerView) findViewById(R.id.scanner_view);
        final WebView webView = (WebView) findViewById(R.id.webviewxss);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        this.codeScanner = new CodeScanner(this, scannerView);
        CodeScanner codeScanner = this.codeScanner;
        if (codeScanner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("codeScanner");
            codeScanner = null;
        }
        codeScanner.setDecodeCallback(new DecodeCallback() { // from class: owasp.sat.agoat.QRCodeXSSActivity$$ExternalSyntheticLambda0
            @Override // com.budiyev.android.codescanner.DecodeCallback
            public final void onDecoded(Result result) {
                QRCodeXSSActivity.onCreate$lambda$1(this.f$0, webView, result);
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.QRCodeXSSActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QRCodeXSSActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(final QRCodeXSSActivity this$0, final WebView $webView, final Result it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.runOnUiThread(new Runnable() { // from class: owasp.sat.agoat.QRCodeXSSActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                QRCodeXSSActivity.onCreate$lambda$1$lambda$0(it, $webView, this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1$lambda$0(Result it, WebView $webView, QRCodeXSSActivity this$0) {
        Intrinsics.checkNotNullParameter(it, "$it");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String scannedText = it.getText();
        String htmlData = StringsKt.trimIndent("\n                    <html>\n                    <body>\n                        <h2>Scanned Product Details</h2>\n                        <h3>Product Found!</h3>\n                        <p>Product ID: <b>" + scannedText + "</b></p>\n                        <p>Status: Available</p>\n                    </body>\n                    </html>\n                ");
        $webView.loadData(htmlData, "text/html", "UTF-8");
        Toast.makeText(this$0, "Scan Complete", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(QRCodeXSSActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CodeScanner codeScanner = this$0.codeScanner;
        if (codeScanner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("codeScanner");
            codeScanner = null;
        }
        codeScanner.startPreview();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.codeScanner != null) {
            CodeScanner codeScanner = this.codeScanner;
            if (codeScanner == null) {
                Intrinsics.throwUninitializedPropertyAccessException("codeScanner");
                codeScanner = null;
            }
            codeScanner.startPreview();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        CodeScanner codeScanner = this.codeScanner;
        if (codeScanner == null) {
            Intrinsics.throwUninitializedPropertyAccessException("codeScanner");
            codeScanner = null;
        }
        codeScanner.releaseResources();
        super.onPause();
    }
}
