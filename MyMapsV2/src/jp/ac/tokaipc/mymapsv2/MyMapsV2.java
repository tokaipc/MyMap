package jp.ac.tokaipc.mymapsv2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyMapsV2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_maps_v2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_maps_v2, menu);
		return true;
	}

}
