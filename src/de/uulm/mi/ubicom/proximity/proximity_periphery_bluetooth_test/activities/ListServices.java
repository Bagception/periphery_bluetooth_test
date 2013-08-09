package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.activities;

import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.layout;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListServices extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_services);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_services, menu);
		return true;
	}

}
