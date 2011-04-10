package barilik.app.bluetoothnumpad;

import android.view.View;
import android.view.View.OnClickListener;

public class CustomListener implements OnClickListener {
	
		private int keyCode;
		private BluetoothConnectionService mCommandService;
		
	public CustomListener(int code, BluetoothConnectionService command) {
		this.keyCode = code;
		this.mCommandService = command;
	}
			
	@Override
	public void onClick(View v) {
		mCommandService.write(keyCode);
		
	}

}
