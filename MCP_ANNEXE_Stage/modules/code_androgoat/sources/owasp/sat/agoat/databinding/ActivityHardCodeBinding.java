package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityHardCodeBinding implements ViewBinding {
    public final Button hardcode1;
    public final ImageView imageView;
    public final TextView price;
    public final EditText promocode;
    private final LinearLayout rootView;

    private ActivityHardCodeBinding(LinearLayout rootView, Button hardcode1, ImageView imageView, TextView price, EditText promocode) {
        this.rootView = rootView;
        this.hardcode1 = hardcode1;
        this.imageView = imageView;
        this.price = price;
        this.promocode = promocode;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityHardCodeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityHardCodeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_hard_code, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityHardCodeBinding bind(View rootView) {
        int id = R.id.hardcode1;
        Button hardcode1 = (Button) ViewBindings.findChildViewById(rootView, id);
        if (hardcode1 != null) {
            id = R.id.imageView;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (imageView != null) {
                id = R.id.price;
                TextView price = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (price != null) {
                    id = R.id.promocode;
                    EditText promocode = (EditText) ViewBindings.findChildViewById(rootView, id);
                    if (promocode != null) {
                        return new ActivityHardCodeBinding((LinearLayout) rootView, hardcode1, imageView, price, promocode);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
