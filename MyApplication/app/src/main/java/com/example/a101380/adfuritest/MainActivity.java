package com.example.a101380.adfuritest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieReward;
import jp.tjkapp.adfurikunsdk.moviereward.AdfurikunMovieRewardListener;
import jp.tjkapp.adfurikunsdk.moviereward.MovieRewardData;

/**
 * 動画リワードメディエーションのシンプルな例Main Activity
 */
public class MainActivity extends Activity {
    // 表示したい広告枠ID
    private static final String ADFURIKUN_APPID = "5aaf764942f084e133000005";

    // 動画リワード広告インスタンス
    private AdfurikunMovieReward mReward;
    private LinearLayout mTestModeLayout;

    private TextView mMessageTxt;
    private static final String mTestModeText = "テストモードで配信しています<br/>詳しくは<a href=\"http://adfurikun.jp/adfurikun/adfully/\">こちら</a>をご覧ください";

    private AdfurikunMovieRewardListener mListener = new AdfurikunMovieRewardListener() {
        @Override
        public void onPrepareSuccess() {
            addLog("動画リワード広告の準備が完了しました。");
            // テストモードを通知
            mTestModeLayout.setVisibility(mReward.isTestMode() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onStartPlaying(MovieRewardData data) {
            Toast.makeText(getApplicationContext(), "動画リワード広告の再生を開始しました。(" +
                    data.adnetworkKey + ":" + data.adnetworkName + ")", Toast.LENGTH_LONG).show();
            // 動画の再生開始直後に実行する処理を記述してください。
            addLog("動画リワード広告の再生を開始しました。(" + data.adnetworkKey + ":" + data.adnetworkName + ")");
        }

        @Override
        public void onFinishedPlaying(MovieRewardData data) {
            // 動画の再生が完了したら実行する処理を記述してください。
            addLog("動画リワード広告の再生が完了しました。(" + data.adnetworkKey + ":" + data.adnetworkName + ")");
        }

        @Override
        public void onFailedPlaying(MovieRewardData data) {
            // 動画の再生に失敗した時に実行する処理を記述してください。
            addLog("動画リワード広告の再生が中断しました。(" + data.adnetworkKey + ":" + data.adnetworkName + ")");
        }

        @Override
        public void onAdClose(MovieRewardData data) {
            // 動画の画面を閉じた時に実行する処理を記述してください。
            addLog("動画リワード広告を閉じました。(" + data.adnetworkKey + ":" + data.adnetworkName + ")");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SimpleMovieReward");

        // テストモードの通知を準備
        mTestModeLayout = (LinearLayout)findViewById(R.id.test_mode_layout);
        TextView testMode = (TextView)findViewById(R.id.test_mode_text);

        Spanned text = Html.fromHtml(mTestModeText);
        testMode.setText(text);
        testMode.setMovementMethod(LinkMovementMethod.getInstance());

        // 動画リワード広告のインスタンスを生成します。
        // 広告枠IDとActivityを引数で渡してください。
        mReward = new AdfurikunMovieReward(ADFURIKUN_APPID, this);
        mReward.setAdfurikunMovieRewardListener(mListener);

        // ボタンを押すと動画広告を表示します。
        Button showAdBtn = (Button)findViewById(R.id.show_ad_btn);
        showAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovieReward();
            }
        });

        mMessageTxt = (TextView)findViewById(R.id.message);

        addLog("動画リワード広告の準備を開始します。");
    }

    // 動画広告を表示します。
    private void showMovieReward() {
        // テストモードを通知
        mTestModeLayout.setVisibility(mReward.isTestMode() ? View.VISIBLE : View.GONE);
//		mTestModeDescription.setText(mReward.getTestModeDescription());

        // 再生の前に、動画の読み込みが完了しているか確認してください
        if (mReward.isPrepared()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("動画の再生確認")
                    .setMessage("動画を再生しますか？")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 再生を開始します。
                            mReward.play();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addLog("動画リワード広告の再生をキャンセルしました。");
                        }
                    })
                    .show();
        } else {
            addLog("動画リワード広告の準備中です。");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // onStart内で動画リワード広告インスタンスのonStartメソッドを呼び出してください
        if (mReward != null) {
            mReward.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onPause内で動画リワード広告インスタンスのonResumeメソッドを呼び出してください
        if (mReward != null) {
            mReward.onResume();
        }
    }

    @Override
    protected void onPause() {
        // onPause内で動画リワード広告インスタンスのonPauseメソッドを呼び出してください
        if (mReward != null) {
            mReward.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        // onStop内で動画リワード広告インスタンスのonStopメソッドを呼び出してください
        if (mReward != null) {
            mReward.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // onDestroy内で動画リワード広告インスタンスのonDestroyメソッドを呼び出してください
        if (mReward != null) {
            mReward.onDestroy();
        }
        super.onDestroy();
    }

    private static final SimpleDateFormat mLogDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.JAPAN);
    private synchronized void addLog(String msg) {
        String log = mLogDateFormat.format(new Date()) + " : " + msg;
        mMessageTxt.setText(log + "\n" + mMessageTxt.getText());
    }
}
