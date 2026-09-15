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
public final class ActivityInsecureStorageBinding implements ViewBinding {
    public final Button SDcardButton;
    public final Button SP1Button;
    public final Button SPButton;
    public final Button SQLButton;
    private final LinearLayout rootView;
    public final Button tempButton;

    private ActivityInsecureStorageBinding(LinearLayout rootView, Button SDcardButton, Button SP1Button, Button SPButton, Button SQLButton, Button tempButton) {
        this.rootView = rootView;
        this.SDcardButton = SDcardButton;
        this.SP1Button = SP1Button;
        this.SPButton = SPButton;
        this.SQLButton = SQLButton;
        this.tempButton = tempButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInsecureStorageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInsecureStorageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_insecure_storage, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInsecureStorageBinding bind(View rootView) {
        int id = R.id.SDcardButton;
        Button SDcardButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (SDcardButton != null) {
            id = R.id.SP1Button;
            Button SP1Button = (Button) ViewBindings.findChildViewById(rootView, id);
            if (SP1Button != null) {
                id = R.id.SPButton;
                Button SPButton = (Button) ViewBindings.findChildViewById(rootView, id);
                if (SPButton != null) {
                    id = R.id.SQLButton;
                    Button SQLButton = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (SQLButton != null) {
                        id = R.id.tempButton;
                        Button tempButton = (Button) ViewBindings.findChildViewById(rootView, id);
                        if (tempButton != null) {
                            return new ActivityInsecureStorageBinding((LinearLayout) rootView, SDcardButton, SP1Button, SPButton, SQLButton, tempButton);
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
