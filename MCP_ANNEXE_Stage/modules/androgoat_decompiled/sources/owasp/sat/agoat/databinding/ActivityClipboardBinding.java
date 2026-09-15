package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityClipboardBinding implements ViewBinding {
    public final EditText cc;
    private final LinearLayout rootView;
    public final Button verifyCC;

    private ActivityClipboardBinding(LinearLayout rootView, EditText cc, Button verifyCC) {
        this.rootView = rootView;
        this.cc = cc;
        this.verifyCC = verifyCC;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityClipboardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityClipboardBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_clipboard, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityClipboardBinding bind(View rootView) {
        int id = R.id.cc;
        EditText cc = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (cc != null) {
            id = R.id.verifyCC;
            Button verifyCC = (Button) ViewBindings.findChildViewById(rootView, id);
            if (verifyCC != null) {
                return new ActivityClipboardBinding((LinearLayout) rootView, cc, verifyCC);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
