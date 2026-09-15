package owasp.sat.agoat;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DownloadInvoiceService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\"\u0010\u000b\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0016¨\u0006\u000f"}, d2 = {"Lowasp/sat/agoat/DownloadInvoiceService;", "Landroid/app/Service;", "()V", "downloadFile", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class DownloadInvoiceService extends Service {
    @Override // android.app.Service
    public void onCreate() {
        Log.i("DOWNLOAD", "Service onCreate");
        Toast.makeText(getApplicationContext(), "Service Created", 1).show();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Log.i("DOWNLOAD", "Service onBind");
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DOWNLOAD", "Invoice is being downloaded");
        downloadFile();
        Toast.makeText(getApplicationContext(), "Invoice is being downloaded", 1).show();
        stopSelf();
        return 2;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.i("DOWNLOAD", "Service onDestroy");
    }

    private final void downloadFile() {
        new Thread(new Runnable() { // from class: owasp.sat.agoat.DownloadInvoiceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DownloadInvoiceService.downloadFile$lambda$1(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadFile$lambda$1(DownloadInvoiceService this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Uri url = Uri.parse("https://raw.githubusercontent.com/satishpatnayak/MyTest/master/AndroGoatInvoice.txt");
        Intrinsics.checkNotNullExpressionValue(url, "parse(url1)");
        DownloadManager.Request request = new DownloadManager.Request(url);
        String fileName = url.getLastPathSegment();
        if (fileName == null) {
            fileName = "AndroGoatInvoice.txt";
        }
        request.setAllowedNetworkTypes(3);
        request.setTitle(fileName);
        request.setDescription("The File is downloading...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, System.currentTimeMillis() + "_invoice.txt");
        Object systemService = this$0.getSystemService("download");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
        DownloadManager manager = (DownloadManager) systemService;
        manager.enqueue(request);
    }
}
