package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityInputValidationsWebViewUrlBinding implements ViewBinding {
    public final Button load;
    private final LinearLayout rootView;
    public final EditText url;
    public final WebView webview1;

    private ActivityInputValidationsWebViewUrlBinding(LinearLayout rootView, Button load, EditText url, WebView webview1) {
        this.rootView = rootView;
        this.load = load;
        this.url = url;
        this.webview1 = webview1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInputValidationsWebViewUrlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInputValidationsWebViewUrlBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_input_validations_web_view_url, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInputValidationsWebViewUrlBinding bind(View rootView) {
        int id = R.id.load;
        Button load = (Button) ViewBindings.findChildViewById(rootView, id);
        if (load != null) {
            id = R.id.url;
            EditText url = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (url != null) {
                id = R.id.webview1;
                WebView webview1 = (WebView) ViewBindings.findChildViewById(rootView, id);
                if (webview1 != null) {
                    return new ActivityInputValidationsWebViewUrlBinding((LinearLayout) rootView, load, url, webview1);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
