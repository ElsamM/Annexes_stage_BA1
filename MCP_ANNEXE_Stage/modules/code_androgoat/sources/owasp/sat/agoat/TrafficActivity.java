package owasp.sat.agoat;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: TrafficActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u0012\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\n¨\u0006\u0018"}, d2 = {"Lowasp/sat/agoat/TrafficActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "client", "Lokhttp3/OkHttpClient;", "httpsurl", "", "getHttpsurl", "()Ljava/lang/String;", "setHttpsurl", "(Ljava/lang/String;)V", "httpsurl1", "getHttpsurl1", "setHttpsurl1", "httpurl", "getHttpurl", "setHttpurl", "doPinning", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "run", "url", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TrafficActivity extends AppCompatActivity {
    private String httpurl = "http://demo.testfire.net";
    private String httpsurl = "https://owasp.org";
    private String httpsurl1 = "https://cve.org";
    private final OkHttpClient client = new OkHttpClient();

    public final String getHttpurl() {
        return this.httpurl;
    }

    public final void setHttpurl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.httpurl = str;
    }

    public final String getHttpsurl() {
        return this.httpsurl;
    }

    public final void setHttpsurl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.httpsurl = str;
    }

    public final String getHttpsurl1() {
        return this.httpsurl1;
    }

    public final void setHttpsurl1(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.httpsurl1 = str;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button HttpButton = (Button) findViewById(R.id.httpButton);
        Button HttpsButton = (Button) findViewById(R.id.httpsButton);
        Button PinningButton = (Button) findViewById(R.id.PinningButton);
        Button PinningButton1 = (Button) findViewById(R.id.PinningButton1);
        HttpButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.TrafficActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrafficActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        HttpsButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.TrafficActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrafficActivity.onCreate$lambda$1(this.f$0, view);
            }
        });
        PinningButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.TrafficActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrafficActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
        PinningButton1.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.TrafficActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TrafficActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(TrafficActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.run(this$0.httpurl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(TrafficActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.run(this$0.httpsurl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(TrafficActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doPinning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(TrafficActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.run(this$0.httpsurl1);
    }

    public final void run(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        try {
            Request request = new Request.Builder().url(url).build();
            Toast.makeText(this, "Request sent to " + url + " Please intercept using Proxy", 1).show();
            this.client.newCall(request).enqueue(new Callback() { // from class: owasp.sat.agoat.TrafficActivity.run.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException e) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    Intrinsics.checkNotNullParameter(e, "e");
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    Intrinsics.checkNotNullParameter(response, "response");
                    ResponseBody responseBodyBody = response.body();
                    System.out.println((Object) (responseBodyBody != null ? responseBodyBody.string() : null));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: owasp.sat.agoat.TrafficActivity$doPinning$1, reason: invalid class name */
    /* JADX INFO: compiled from: TrafficActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "owasp.sat.agoat.TrafficActivity$doPinning$1", f = "TrafficActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    try {
                        CertificatePinner pinner1 = new CertificatePinner.Builder().add("owasp.org", "sha256/5gsjyidrmWjcLRClfCk+Dd6O0nx1CyFrVUW5wVkwEx0=").add("owasp.org", "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=").add("owasp.org", "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=").build();
                        OkHttpClient client = new OkHttpClient.Builder().certificatePinner(pinner1).build();
                        Request request = new Request.Builder().url("https://owasp.org").build();
                        Response response = client.newCall(request).execute();
                        ResponseBody responseBodyBody = response.body();
                        Log.v("Response", String.valueOf(responseBodyBody != null ? responseBodyBody.string() : null));
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final void doPinning() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(null), 3, null);
    }
}
