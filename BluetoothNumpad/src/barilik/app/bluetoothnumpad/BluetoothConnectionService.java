package barilik.app.bluetoothnumpad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BluetoothConnectionService {
	
	//premenne
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    
    //Unik·tne UUID aplik·cie
    private static final UUID MY_UUID = UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");
    
    //Konötanty reprezentuj˙ce stavy
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    
    /*   	
   		java.awt.event kÛdy jednotliv˝ch kl·vesov
 
   		public static final int VK_0 = 0x30;
		public static final int VK_1 = 0x31;
		public static final int VK_2 = 0x32;
		public static final int VK_3 = 0x33;
		public static final int VK_4 = 0x34;
		public static final int VK_5 = 0x35;
		public static final int VK_6 = 0x36;
		public static final int VK_7 = 0x37;
		public static final int VK_8 = 0x38;
		public static final int VK_9 = 0x39;
		public static final int VK_MULTIPLY = 0x6A;
		public static final int VK_MINUS = 0x2D;
		public static final int VK_ADD = 0x6B;
		public static final int VK_DIVIDE = 0x6F;
		public static final int VK_DELETE = 0x7F;
		public static final int VK_ENTER = '\n';
	*/  
   
    // Konötanty, ktorÈ bud˙ predstavovaù vstup pre BT server
    public static final int EXIT_CMD = 999;
    public static final int VOL_DOWN = 2;
    public static final int NUM00 = 0x30;
    public static final int NUM01 = 0x31;
    public static final int NUM02 = 0x32;
    public static final int NUM03 = 0x33;
    public static final int NUM04 = 0x34;
    public static final int NUM05 = 0x35;
    public static final int NUM06 = 0x36;
    public static final int NUM07 = 0x37;
    public static final int NUM08 = 0x38;
    public static final int NUM09 = 0x39;
    public static final int NUMPLUS = 0x6B;
    public static final int NUMMINUS = 0x2D;
    public static final int NUMMUL = 0x6A;
    public static final int NUMDIV = 0x6F;
    public static final int NUMNUM = 0x90;
    public static final int NUMBACKSPACE = '\b';
    public static final int NUMENTER = '\n';
    public static final int NUMDOT = 0x2E;
    
    /**
     * konötruktor, parametre context a handler
     * @param context  kontext aktivity
     * @param handler  
     */
    public BluetoothConnectionService(Context context, Handler handler) {
    	mAdapter = BluetoothAdapter.getDefaultAdapter();
    	mState = STATE_NONE;
    	//mConnectionLostCount = 0;
    	mHandler = handler;
    }
    
    /**
     * setter pre zÌskanie aktu·lneho stavu adapt.
     * @param state cislo reprezentuj˙ce stav aplik·cie
     */
    private synchronized void setState(int state) {
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(RemoteBluetooth.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    
    //Getter pre zÌskanie aktu·lneho stavu adaptera
    public synchronized int getState() {
        return mState;
    }
    
    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    public synchronized void start() {
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        setState(STATE_LISTEN);
    }
    
    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }
    
    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(RemoteBluetooth.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(RemoteBluetooth.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // save connected device
        //mSavedDevice = device;
        // reset connection lost count
        //mConnectionLostCount = 0;
        
        setState(STATE_CONNECTED);
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        
        setState(STATE_NONE);
    }
    
    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }
    
    public void write(int out) {
    	// Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }
    
    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(RemoteBluetooth.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(RemoteBluetooth.TOAST, "Nepodarilo sa pripojiù k zariadeniu.");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
//        mConnectionLostCount++;
//        if (mConnectionLostCount < 3) {
//        	// Send a reconnect message back to the Activity
//	        Message msg = mHandler.obtainMessage(RemoteBluetooth.MESSAGE_TOAST);
//	        Bundle bundle = new Bundle();
//	        bundle.putString(RemoteBluetooth.TOAST, "Device connection was lost. Reconnecting...");
//	        msg.setData(bundle);
//	        mHandler.sendMessage(msg);
//	        
//        	connect(mSavedDevice);   	
//        } else {
        	setState(STATE_LISTEN);
	        // Send a failure message back to the Activity
	        Message msg = mHandler.obtainMessage(RemoteBluetooth.MESSAGE_TOAST);
	        Bundle bundle = new Bundle();
	        bundle.putString(RemoteBluetooth.TOAST, "Spojenie bolo preruöenÈ.");
	        msg.setData(bundle);
	        mHandler.sendMessage(msg);
//        }
    }
    
    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                	//Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
                }
                // Start the service over to restart listening mode
                BluetoothConnectionService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothConnectionService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            	Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            	Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                	// Read from the InputStream
                    int bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(RemoteBluetooth.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                	//Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
                    connectionLost();
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
//                mHandler.obtainMessage(BluetoothChat.MESSAGE_WRITE, -1, -1, buffer)
//                        .sendToTarget();
            } catch (IOException e) {
            	Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }
        }
        
        public void write(int out) {
        	try {
                mmOutStream.write(out);

                // Share the sent message back to the UI Activity
//                mHandler.obtainMessage(BluetoothChat.MESSAGE_WRITE, -1, -1, buffer)
//                        .sendToTarget();
            } catch (IOException e) {
            	Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }
        }

        public void cancel() {
            try {
            	mmOutStream.write(EXIT_CMD);
                mmSocket.close();
            } catch (IOException e) {
            	Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }
}