package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.activities;

import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.layout;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.menu;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListServices extends Activity {
	private BluetoothDevice device;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_services);
		Intent parent = getIntent();
		device = parent.getParcelableExtra("device");
		
		ListView v = (ListView) findViewById(R.id.servicesListView);
		v.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					uuidSelected(device, device.getUuids()[arg2]);
				
			}
			
		});
		v.setAdapter(new ArrayAdapter<Object>(this,android.R.layout.simple_list_item_1,device.getUuids()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_services, menu);
		return true;
	}
	
	private void uuidSelected(BluetoothDevice device, ParcelUuid uuid){
    	Intent intent = new Intent(this,BTClientActivity.class);
    	intent.putExtra("device",device);
    	intent.putExtra("uuid",uuid);

    	startActivity(intent);
	}

}
