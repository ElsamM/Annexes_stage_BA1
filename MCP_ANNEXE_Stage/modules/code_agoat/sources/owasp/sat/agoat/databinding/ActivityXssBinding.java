package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityXssBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final WebView webview;

    private ActivityXssBinding(LinearLayout rootView, WebView webview) {
        this.rootView = rootView;
        this.webview = webview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityXssBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityXssBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_xss, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityXssBinding bind(View rootView) {
        int id = R.id.webview;
        WebView webview = (WebView) ViewBindings.findChildViewById(rootView, id);
        if (webview != null) {
            return new ActivityXssBinding((LinearLayout) rootView, webview);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
