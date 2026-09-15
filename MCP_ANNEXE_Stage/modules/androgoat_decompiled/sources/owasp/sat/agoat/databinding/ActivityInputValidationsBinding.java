package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityInputValidationsBinding implements ViewBinding {
    public final Button qrcode;
    private final LinearLayout rootView;
    public final Button sqliButton;
    public final Button wbUrl;
    public final Button xssBtn;

    private ActivityInputValidationsBinding(LinearLayout rootView, Button qrcode, Button sqliButton, Button wbUrl, Button xssBtn) {
        this.rootView = rootView;
        this.qrcode = qrcode;
        this.sqliButton = sqliButton;
        this.wbUrl = wbUrl;
        this.xssBtn = xssBtn;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInputValidationsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInputValidationsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_input_validations, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInputValidationsBinding bind(View rootView) {
        int id = R.id.qrcode;
        Button qrcode = (Button) ViewBindings.findChildViewById(rootView, id);
        if (qrcode != null) {
            id = R.id.sqliButton;
            Button sqliButton = (Button) ViewBindings.findChildViewById(rootView, id);
            if (sqliButton != null) {
                id = R.id.wbUrl;
                Button wbUrl = (Button) ViewBindings.findChildViewById(rootView, id);
                if (wbUrl != null) {
                    id = R.id.xssBtn;
                    Button xssBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (xssBtn != null) {
                        return new ActivityInputValidationsBinding((LinearLayout) rootView, qrcode, sqliButton, wbUrl, xssBtn);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
