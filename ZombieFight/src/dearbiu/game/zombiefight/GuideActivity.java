package dearbiu.game.zombiefight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class GuideActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initCurrentView();
		initDatabase();
		doBusiness();
	}

	private void initCurrentView() {

	}

	private void initDatabase() {

	}

	private void doBusiness() {
		setGuided();
		Intent mIntent = new Intent();
		mIntent.setClass(this, ZombieFightActivity.class);
		startActivity(mIntent);
		finish();
	}

	private void setGuided() {
		SharedPreferences settings = getSharedPreferences(
				getString(R.string.config_default_sharedpreferences_database),
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(
				getString(
						R.string.config_default_sharedpreferences_key_guideactivity),
				getString(R.string.config_default_app_version));
		editor.commit();
	}

}
