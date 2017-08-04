package dearbiu.game.zombiefight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends BaseActivity {

	private final static int SWITCH_TITLEACTIVITY = 1000;
	private final static int SWITCH_GUIDACTIVITY = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean mFirst = isFirstEnter(this, getClass().getName());
		if (mFirst)
			mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY, 1000);
		else
			mHandler.sendEmptyMessageDelayed(SWITCH_TITLEACTIVITY, 500);
	}

	private boolean isFirstEnter(Context context, String className) {
		if (context == null || className == null || "".equalsIgnoreCase(className))
			return false;
		String mResultStr = context
				.getSharedPreferences(getString(R.string.config_default_sharedpreferences_database),
						Activity.MODE_PRIVATE)
				.getString(getString(R.string.config_default_sharedpreferences_key_guideactivity),
						"");
		if (mResultStr.equalsIgnoreCase(getString(R.string.config_default_app_version)))
			return false;
		else
			return true;
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_TITLEACTIVITY:
				Intent mIntent = new Intent();
				mIntent.setClass(SplashActivity.this, ZombieFightActivity.class);
				SplashActivity.this.startActivity(mIntent);
				SplashActivity.this.finish();
				break;
			case SWITCH_GUIDACTIVITY:
				mIntent = new Intent();
				mIntent.setClass(SplashActivity.this, GuideActivity.class);
				SplashActivity.this.startActivity(mIntent);
				SplashActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
}
