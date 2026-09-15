package com.google.android.material.textfield;

/* JADX INFO: loaded from: classes2.dex */
class CustomEndIconDelegate extends EndIconDelegate {
    CustomEndIconDelegate(EndCompoundLayout endLayout) {
        super(endLayout);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    void setUp() {
        this.endLayout.setEndIconOnLongClickListener(null);
    }
}
