package com.example.tomk.yomike;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class YoActivity extends Activity {

    private static final String PACKAGE_NAME_TWITTER = "com.twitter.android.composer.ComposerActivity";

    @InjectView(R.id.activity_yo_image) ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yo);
        ButterKnife.inject(this);
        mImageView.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.yo));

            final PackageManager pm = v.getContext().getPackageManager();
            final List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
            for (final ResolveInfo app : activityList) {
                if (PACKAGE_NAME_TWITTER.equals(app.activityInfo.name)) {
                    final ActivityInfo activityInfo = app.activityInfo;
                    final ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setComponent(componentName);
                    startActivity(shareIntent);
                    finish();
                }
            }
        }
    };

}