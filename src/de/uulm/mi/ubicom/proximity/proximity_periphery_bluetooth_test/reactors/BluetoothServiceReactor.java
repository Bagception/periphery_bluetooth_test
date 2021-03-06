package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.reactors;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;
import de.uulm.mi.ubicom.proximity.lib.Reactor;

public interface BluetoothServiceReactor extends Reactor{
	public void onDeviceFound(BluetoothDevice device);
	public void onDeviceDiscoveryFinished(BluetoothDevice[] devices,ConcurrentHashMap<String,BluetoothDevice> devicesAsMap);
	public void onServicesDiscovered(BluetoothDevice device);
	public void onServiceDiscoveryStarted();
	
}
