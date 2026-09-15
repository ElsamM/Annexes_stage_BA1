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
public final class ActivityMainBinding implements ViewBinding {
    public final Button AccessControl1;
    public final Button BinaryPatching;
    public final Button EmulatorButton;
    public final Button Hardcode;
    public final Button InputValidations;
    public final Button InsecureStorage;
    public final Button SideChannelLeakage;
    public final Button Traffic;
    public final Button bioauth1;
    public final Button rootButton1;
    private final LinearLayout rootView;

    private ActivityMainBinding(LinearLayout rootView, Button AccessControl1, Button BinaryPatching, Button EmulatorButton, Button Hardcode, Button InputValidations, Button InsecureStorage, Button SideChannelLeakage, Button Traffic, Button bioauth1, Button rootButton1) {
        this.rootView = rootView;
        this.AccessControl1 = AccessControl1;
        this.BinaryPatching = BinaryPatching;
        this.EmulatorButton = EmulatorButton;
        this.Hardcode = Hardcode;
        this.InputValidations = InputValidations;
        this.InsecureStorage = InsecureStorage;
        this.SideChannelLeakage = SideChannelLeakage;
        this.Traffic = Traffic;
        this.bioauth1 = bioauth1;
        this.rootButton1 = rootButton1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_main, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityMainBinding bind(View rootView) {
        int id = R.id.AccessControl1;
        Button AccessControl1 = (Button) ViewBindings.findChildViewById(rootView, id);
        if (AccessControl1 != null) {
            id = R.id.BinaryPatching;
            Button BinaryPatching = (Button) ViewBindings.findChildViewById(rootView, id);
            if (BinaryPatching != null) {
                id = R.id.EmulatorButton;
                Button EmulatorButton = (Button) ViewBindings.findChildViewById(rootView, id);
                if (EmulatorButton != null) {
                    id = R.id.Hardcode;
                    Button Hardcode = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (Hardcode != null) {
                        id = R.id.InputValidations;
                        Button InputValidations = (Button) ViewBindings.findChildViewById(rootView, id);
                        if (InputValidations != null) {
                            id = R.id.InsecureStorage;
                            Button InsecureStorage = (Button) ViewBindings.findChildViewById(rootView, id);
                            if (InsecureStorage != null) {
                                id = R.id.SideChannelLeakage;
                                Button SideChannelLeakage = (Button) ViewBindings.findChildViewById(rootView, id);
                                if (SideChannelLeakage != null) {
                                    id = R.id.Traffic;
                                    Button Traffic = (Button) ViewBindings.findChildViewById(rootView, id);
                                    if (Traffic != null) {
                                        id = R.id.bioauth1;
                                        Button bioauth1 = (Button) ViewBindings.findChildViewById(rootView, id);
                                        if (bioauth1 != null) {
                                            id = R.id.rootButton1;
                                            Button rootButton1 = (Button) ViewBindings.findChildViewById(rootView, id);
                                            if (rootButton1 != null) {
                                                return new ActivityMainBinding((LinearLayout) rootView, AccessControl1, BinaryPatching, EmulatorButton, Hardcode, InputValidations, InsecureStorage, SideChannelLeakage, Traffic, bioauth1, rootButton1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
