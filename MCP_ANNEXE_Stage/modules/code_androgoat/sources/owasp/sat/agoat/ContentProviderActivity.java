package owasp.sat.agoat;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ContentProviderActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001b\u001cB\u0005¢\u0006\u0002\u0010\u0002J/\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016JK\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0007\u001a\u00020\b2\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\f2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u0018J9\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\t\u001a\u0004\u0018\u00010\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lowasp/sat/agoat/ContentProviderActivity;", "Landroid/content/ContentProvider;", "()V", "mDB", "Landroid/database/sqlite/SQLiteDatabase;", "delete", "", "uri", "Landroid/net/Uri;", "selection", "", "selectionArgs", "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "getType", "insert", "values", "Landroid/content/ContentValues;", "onCreate", "", "query", "Landroid/database/Cursor;", "projection", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "Companion", "DBHelper", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ContentProviderActivity extends ContentProvider {
    public static final String AUTHORITY = "owasp.sat.agoat.provider.userpinsprovider";
    private static final Uri CONTENT_URI;
    public static final String CREATE_TBL_QRY = "CREATE TABLE user_pins (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, pin TEXT NOT NULL);";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DBNAME = "androgoat_userpins.db";
    public static final String DROP_TBL_QRY = "DROP TABLE IF EXISTS user_pins";
    public static final String ID = "id";
    public static final int PATH_ID = 2;
    public static final int PATH_TABLE = 1;
    public static final String PIN = "pin";
    public static final String TABLE = "user_pins";
    public static final String USERNAME = "username";
    private static final UriMatcher urimatcher;
    private SQLiteDatabase mDB;

    /* JADX INFO: compiled from: ContentProviderActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lowasp/sat/agoat/ContentProviderActivity$Companion;", "", "()V", "AUTHORITY", "", "CONTENT_URI", "Landroid/net/Uri;", "getCONTENT_URI", "()Landroid/net/Uri;", "CREATE_TBL_QRY", "DBNAME", "DROP_TBL_QRY", "ID", "PATH_ID", "", "PATH_TABLE", "PIN", "TABLE", "USERNAME", "urimatcher", "Landroid/content/UriMatcher;", "getUrimatcher", "()Landroid/content/UriMatcher;", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Uri getCONTENT_URI() {
            return ContentProviderActivity.CONTENT_URI;
        }

        public final UriMatcher getUrimatcher() {
            return ContentProviderActivity.urimatcher;
        }
    }

    static {
        Uri uri = Uri.parse("content://owasp.sat.agoat.provider.userpinsprovider/user_pins");
        Intrinsics.checkNotNullExpressionValue(uri, "parse(\"content://$AUTHORITY/$TABLE\")");
        CONTENT_URI = uri;
        UriMatcher $this$urimatcher_u24lambda_u240 = new UriMatcher(-1);
        $this$urimatcher_u24lambda_u240.addURI(AUTHORITY, TABLE, 1);
        $this$urimatcher_u24lambda_u240.addURI(AUTHORITY, "user_pins/#", 2);
        urimatcher = $this$urimatcher_u24lambda_u240;
    }

    /* JADX INFO: compiled from: ContentProviderActivity.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016¨\u0006\r"}, d2 = {"Lowasp/sat/agoat/ContentProviderActivity$DBHelper;", "Landroid/database/sqlite/SQLiteOpenHelper;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "onCreate", "", "db", "Landroid/database/sqlite/SQLiteDatabase;", "onUpgrade", "oldVersion", "", "newVersion", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private static final class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, ContentProviderActivity.DBNAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            Intrinsics.checkNotNullParameter(db, "db");
            db.execSQL(ContentProviderActivity.DROP_TBL_QRY);
            db.execSQL(ContentProviderActivity.CREATE_TBL_QRY);
            db.execSQL("INSERT INTO user_pins (username, pin) VALUES ('AndroGoat', 'AndroGoat');");
            db.execSQL("INSERT INTO user_pins (username, pin) VALUES ('root', 'toor');");
            db.execSQL("INSERT INTO user_pins (username, pin) VALUES ('Admin', 'Admin');");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Intrinsics.checkNotNullParameter(db, "db");
            onCreate(db);
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Intrinsics.checkNotNullExpressionValue(writableDatabase, "dbHelper.writableDatabase");
        this.mDB = writableDatabase;
        if (this.mDB != null) {
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mDB");
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE);
        switch (urimatcher.match(uri)) {
            case 1:
                break;
            case 2:
                queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("AndroGoat UserPinsProvider(query): Unknown URI " + uri);
        }
        String orderBy = TextUtils.isEmpty(sortOrder) ? USERNAME : sortOrder;
        SQLiteDatabase sQLiteDatabase = this.mDB;
        if (sQLiteDatabase == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDB");
            sQLiteDatabase = null;
        }
        Cursor cursor = queryBuilder.query(sQLiteDatabase, projection, selection, selectionArgs, null, null, orderBy);
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        SQLiteDatabase sQLiteDatabase = this.mDB;
        if (sQLiteDatabase == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDB");
            sQLiteDatabase = null;
        }
        long row = sQLiteDatabase.insert(TABLE, "", values);
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            Intrinsics.checkNotNullExpressionValue(newUri, "withAppendedId(CONTENT_URI, row)");
            Context context = getContext();
            Intrinsics.checkNotNull(context);
            context.getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("AndroGoat UserPinsProvider: Fail to add a new record into " + uri);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        Intrinsics.checkNotNullParameter(uri, "uri");
        switch (urimatcher.match(uri)) {
            case 1:
                SQLiteDatabase sQLiteDatabase = this.mDB;
                if (sQLiteDatabase == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDB");
                    sQLiteDatabase = null;
                }
                count = sQLiteDatabase.delete(TABLE, selection, selectionArgs);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                String where = "id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                SQLiteDatabase sQLiteDatabase2 = this.mDB;
                if (sQLiteDatabase2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDB");
                    sQLiteDatabase2 = null;
                }
                count = sQLiteDatabase2.delete(TABLE, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("AndroGoat UserPinsProvider(delete): Unsupported URI " + uri);
        }
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        context.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        Intrinsics.checkNotNullParameter(uri, "uri");
        switch (urimatcher.match(uri)) {
            case 1:
                SQLiteDatabase sQLiteDatabase = this.mDB;
                if (sQLiteDatabase == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDB");
                    sQLiteDatabase = null;
                }
                count = sQLiteDatabase.update(TABLE, values, selection, selectionArgs);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                String where = "id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                SQLiteDatabase sQLiteDatabase2 = this.mDB;
                if (sQLiteDatabase2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDB");
                    sQLiteDatabase2 = null;
                }
                count = sQLiteDatabase2.update(TABLE, values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("AndroGoat UserPinsProvider(update): Unsupported URI " + uri);
        }
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        context.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        switch (urimatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/vnd.androgoat.pins";
            case 2:
                return "vnd.android.cursor.item/vnd.androgoat.pins";
            default:
                throw new IllegalArgumentException("AndroGoat UserPinsProvider: Unsupported URI: " + uri);
        }
    }
}
