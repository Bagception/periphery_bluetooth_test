package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import de.uulm.mi.ubicom.proximity.lib.Reactor;

public interface BluetoothServiceReactor extends Reactor{
	public void onServiceDiscovered();
	public void onServiceDiscoveryFinished();
	public void onServiceDiscoveryStarted();
	
}
