package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityInputValidationsOscmdinjectionMain2Binding implements ViewBinding {
    public final EditText ip;
    public final TextView result;
    private final LinearLayout rootView;
    public final Button run;

    private ActivityInputValidationsOscmdinjectionMain2Binding(LinearLayout rootView, EditText ip, TextView result, Button run) {
        this.rootView = rootView;
        this.ip = ip;
        this.result = result;
        this.run = run;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInputValidationsOscmdinjectionMain2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInputValidationsOscmdinjectionMain2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_input_validations_oscmdinjection_main2, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInputValidationsOscmdinjectionMain2Binding bind(View rootView) {
        int id = R.id.ip;
        EditText ip = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (ip != null) {
            id = R.id.result;
            TextView result = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (result != null) {
                id = R.id.run;
                Button run = (Button) ViewBindings.findChildViewById(rootView, id);
                if (run != null) {
                    return new ActivityInputValidationsOscmdinjectionMain2Binding((LinearLayout) rootView, ip, result, run);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
