package me.arkanayan.buieconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.uncod.android.bypass.Bypass;
import me.arkanayan.buieconnect.BuildConfig;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.models.Notice;

public class NoticeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTICE = BuildConfig.APPLICATION_ID + ".noticeExtra";

    private Notice mNotice;

    @Bind(R.id.edit_text_notice_detail)
    TextView noticeDetailEditText;

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNotice = getIntent().getParcelableExtra(EXTRA_NOTICE);

        // analytics
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Notice Details Screen")
                .putContentType("Screen")
                .putContentId("screen-notice-details")
                .putCustomAttribute("Notice id", String.valueOf(mNotice.getId()))
                .putCustomAttribute("Notice title",mNotice.getTitle())
        );

        collapsingToolbarLayout.setTitle(mNotice.getTitle());

        Bypass bypass = new Bypass(this);
        String markdownString = mNotice.getMessage();
        CharSequence string = bypass.markdownToSpannable(markdownString);

        noticeDetailEditText.setText(string);
        noticeDetailEditText.setMovementMethod(LinkMovementMethod.getInstance());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getInstance(Context context, Notice notice) {

        Intent noticeIntent = new Intent(context, NoticeDetailActivity.class);
        noticeIntent.putExtra(EXTRA_NOTICE, notice);
        return noticeIntent;
    }
}
