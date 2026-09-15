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
public final class ActivityAccessVerifyPinBinding implements ViewBinding {
    public final EditText pinValue;
    private final LinearLayout rootView;
    public final Button verifyPIN;

    private ActivityAccessVerifyPinBinding(LinearLayout rootView, EditText pinValue, Button verifyPIN) {
        this.rootView = rootView;
        this.pinValue = pinValue;
        this.verifyPIN = verifyPIN;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAccessVerifyPinBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAccessVerifyPinBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_access_verify_pin, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAccessVerifyPinBinding bind(View rootView) {
        int id = R.id.pinValue;
        EditText pinValue = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (pinValue != null) {
            id = R.id.verifyPIN;
            Button verifyPIN = (Button) ViewBindings.findChildViewById(rootView, id);
            if (verifyPIN != null) {
                return new ActivityAccessVerifyPinBinding((LinearLayout) rootView, pinValue, verifyPIN);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
