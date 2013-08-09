package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class BTClient implements Runnable{
	private Thread recvThread;
	private volatile boolean listening=true;
	private BluetoothSocket clientSocket;
	private final BluetoothDevice device;
	private final ParcelUuid service;
    private final InputStream clientSocketInStream;
    private final OutputStream clientSocketOutStream;
	
	public BTClient(BluetoothDevice device, ParcelUuid service) throws IOException {
		this.device = device;
		this.service = service;
		clientSocketInStream = clientSocket.getInputStream();
		clientSocketOutStream = clientSocket.getOutputStream();
	}
	
	//executes itself in another thread and listens
	public void startListeningForIncomingBytes(){
		recvThread = new Thread(this);
		recvThread.start();
	}
	

	
    public void run() {
    	 byte[] buffer = new byte[1024];  // buffer store for the stream
        // Cancel discovery because it will slow down the connection.
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try {//cannot throw here of course (the method who called start will already be somewhere else because of the thread
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception.
            clientSocket = device.createRfcommSocketToServiceRecord(service.getUuid());
            clientSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out.
            try {
            	clientSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // manage the connection 
        int bytes = 0;
		while (listening){
			try {
                // Read from the InputStream
                bytes = clientSocketInStream.read(buffer);
                if (bytes == -1){
                	//connection remotely closed
                	listening = false;
                	break;
                }
                // Send the obtained bytes to the UI Activity.
                
                //send to UI
                /*mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget();
                 */
                Log.d("input",((char)bytes)+"");
            } catch (IOException e) {
                break;
            }
		}
    }

    /** Will cancel an in-progress connection, and close the socket. 
     * @throws IOException */
    public void cancel() throws IOException {

        clientSocket.close();

    }	
	
    
    public void write(String s) throws IOException{
    	clientSocketOutStream.write(s.getBytes());
    }
}