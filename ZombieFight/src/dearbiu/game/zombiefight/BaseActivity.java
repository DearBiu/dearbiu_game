package dearbiu.game.zombiefight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public Context mContext;
	public Intent mIntent;
	public String mAction;

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		mContext = this;
		mIntent = getIntent();
		mAction = mIntent.getAction();
	}

	
}
