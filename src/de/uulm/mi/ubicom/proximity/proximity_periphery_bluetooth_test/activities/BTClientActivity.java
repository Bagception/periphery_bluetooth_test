package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.activities;

import java.io.IOException;

import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.layout;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.R.menu;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.bluetooth.BTClient;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
 
public class BTClientActivity extends Activity {

	private BTClient btclient;
	private BluetoothDevice device;
	private ParcelUuid uuid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_btclient);
		
		Intent parent = getIntent();
		device = parent.getParcelableExtra("device");
		uuid = parent.getParcelableExtra("uuid");
		Log.d("bt","device "+device.getName() + " ("+device.toString()+")");
		Log.d("bt","uuid "+uuid.toString());
		TextView deviceNameTextView = (TextView) findViewById(R.id.deviceName);
		TextView uuidTextView  = (TextView) findViewById(R.id.uuidTextView);
	   	
		
		deviceNameTextView.setText(device.getName());
		uuidTextView.setText(uuid.toString());
		btclient = null;
		if (BluetoothAdapter.getDefaultAdapter().isDiscovering()){
			Log.d("bt", "cancel discovery now");
			boolean success = BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			Log.d("bt", "bt discovery off:"+success);
		}
		
		startAndConnectToServer();
		
		
		
	
	}
	

	private void startAndConnectToServer(){
		Log.d("bt", "starting client now!");
		try {
			if (btclient != null){
				Log.d("bt", "another client instance is active");
				btclient.cancel();
			}
			btclient = new BTClient(device, uuid);
			btclient.startListeningForIncomingBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void onSend(View v){
		try {
			btclient.write("31:{\"payload\":\"test\",\"cmd\":\"test\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.btclient, menu);
		return true;
	}

}
