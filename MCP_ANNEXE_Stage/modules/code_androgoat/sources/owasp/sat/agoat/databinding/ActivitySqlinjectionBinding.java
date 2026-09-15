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
public final class ActivitySqlinjectionBinding implements ViewBinding {
    public final Button SQLiButton;
    private final LinearLayout rootView;
    public final EditText userName;

    private ActivitySqlinjectionBinding(LinearLayout rootView, Button SQLiButton, EditText userName) {
        this.rootView = rootView;
        this.SQLiButton = SQLiButton;
        this.userName = userName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySqlinjectionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySqlinjectionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_sqlinjection, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivitySqlinjectionBinding bind(View rootView) {
        int id = R.id.SQLiButton;
        Button SQLiButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (SQLiButton != null) {
            id = R.id.userName;
            EditText userName = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (userName != null) {
                return new ActivitySqlinjectionBinding((LinearLayout) rootView, SQLiButton, userName);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
