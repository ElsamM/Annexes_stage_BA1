package owasp.sat.agoat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InsecureStorageSharedPrefs1Activity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0002J\b\u0010\u000b\u001a\u00020\u0004H\u0002J\u0012\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0002¨\u0006\u0010"}, d2 = {"Lowasp/sat/agoat/InsecureStorageSharedPrefs1Activity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "calculateScore", "", "score", "checkScore", "", "displayScore", "", "level", "getScoreFromSP", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "updateScoreInSP", "app_debug"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InsecureStorageSharedPrefs1Activity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecure_storage_shared_prefs1);
        int currentScore = getScoreFromSP();
        SharedPreferences sharedPreferences = getSharedPreferences("score", 0);
        final int currentLevel = sharedPreferences.getInt("level", 1);
        displayScore(currentScore, currentLevel);
        final Button ScoreButton = (Button) findViewById(R.id.getScore);
        ScoreButton.setOnClickListener(new View.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSharedPrefs1Activity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InsecureStorageSharedPrefs1Activity.onCreate$lambda$1(this.f$0, currentLevel, ScoreButton, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(InsecureStorageSharedPrefs1Activity this$0, int $currentLevel, Button $ScoreButton, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int scoreValue = this$0.getScoreFromSP();
        int levelValue = $currentLevel;
        int newScore = this$0.calculateScore(scoreValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(this$0);
        builder.setTitle("Hurray!");
        if (this$0.checkScore(newScore)) {
            levelValue = 2;
            $ScoreButton.setClickable(false);
            builder.setMessage("You won the game");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: owasp.sat.agoat.InsecureStorageSharedPrefs1Activity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            Intrinsics.checkNotNullExpressionValue(dialog, "builder.create()");
            dialog.show();
            $ScoreButton.setText("You won the game");
            Toast.makeText(this$0.getApplicationContext(), "You won the game", 1).show();
        } else {
            Toast.makeText(this$0.getApplicationContext(), "Score is " + newScore + ". Try hard", 0).show();
        }
        this$0.displayScore(newScore, levelValue);
        this$0.updateScoreInSP(newScore, levelValue);
    }

    private final int calculateScore(int score) {
        return score + 1;
    }

    private final boolean checkScore(int score) {
        return score > 10000;
    }

    private final void displayScore(int score, int level) {
        TextView scoreTv = (TextView) findViewById(R.id.score);
        TextView levelTv = (TextView) findViewById(R.id.level);
        scoreTv.setText(String.valueOf(score));
        levelTv.setText(String.valueOf(level));
        System.out.println((Object) ("Score " + score));
        System.out.println((Object) ("Level " + level));
    }

    private final void updateScoreInSP(int score, int level) {
        SharedPreferences sharedPreference = getSharedPreferences("score", 0);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt("score", score);
        editor.putInt("level", level);
        editor.commit();
    }

    private final int getScoreFromSP() {
        int score = 0;
        int level = 1;
        SharedPreferences sharedPreferences = getSharedPreferences("score", 0);
        if (sharedPreferences.getInt("score", 0) != 0 && sharedPreferences.getInt("level", 0) != 0) {
            score = sharedPreferences.getInt("score", 0);
            level = sharedPreferences.getInt("level", 0);
        }
        System.out.println((Object) ("Score is " + score + " and Level is " + level));
        return score;
    }
}
