package neu.madcourse.divvyup.recap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import neu.madcourse.divvyup.R;

public class RecapActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressPercent;
    private int mProgressStatus = 0;


    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        progressPercent = findViewById(R.id.progress_percent);


        progressBar = (ProgressBar) findViewById(R.id.choreProgressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        progressPercent.setVisibility(View.VISIBLE);
//        mLoadingText = (TextView) findViewById(R.id.LoadingCompleteTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                while (mProgressStatus < 80) {
                    mProgressStatus++;
                    SystemClock.sleep(15);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                            progressPercent.setText(mProgressStatus + "%");
                            gradientPercent(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecapActivity.this, "Finished 80% of all chores!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();


    }

    @Override
    public void onBackPressed() {
//        Log.d("CDA", "onBackPressed Called");
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(setIntent);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        progressPercent.setVisibility(View.INVISIBLE);
        this.finish();
    }

    public void gradientPercent(int percent) {
        if (percent <= 25) {
            progressPercent.setTextColor(Color.parseColor("#DC143C"));
        } else if (percent <= 50) {
            progressPercent.setTextColor(Color.parseColor("#FFA500"));
        } else if (percent <= 75) {
            progressPercent.setTextColor(Color.parseColor("#DE970B"));
        } else {
            progressPercent.setTextColor(Color.parseColor("#08FF08"));
        }

    }
}