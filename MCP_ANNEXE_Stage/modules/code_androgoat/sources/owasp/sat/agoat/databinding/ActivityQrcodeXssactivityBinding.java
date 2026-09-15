package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.budiyev.android.codescanner.CodeScannerView;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityQrcodeXssactivityBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final CodeScannerView scannerView;
    public final WebView webviewxss;

    private ActivityQrcodeXssactivityBinding(LinearLayout rootView, CodeScannerView scannerView, WebView webviewxss) {
        this.rootView = rootView;
        this.scannerView = scannerView;
        this.webviewxss = webviewxss;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityQrcodeXssactivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityQrcodeXssactivityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_qrcode_xssactivity, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityQrcodeXssactivityBinding bind(View rootView) {
        int id = R.id.scanner_view;
        CodeScannerView scannerView = (CodeScannerView) ViewBindings.findChildViewById(rootView, id);
        if (scannerView != null) {
            id = R.id.webviewxss;
            WebView webviewxss = (WebView) ViewBindings.findChildViewById(rootView, id);
            if (webviewxss != null) {
                return new ActivityQrcodeXssactivityBinding((LinearLayout) rootView, scannerView, webviewxss);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
