package barilik.app.bluetoothnumpad.dialog;

import com.barilik.BluetoothNumpad.R;

import barilik.app.bluetoothnumpad.BluetoothConnectionService;
import barilik.app.bluetoothnumpad.RemoteBluetooth;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class myAlertDialog extends AlertDialog {
	
	public myAlertDialog(final RemoteBluetooth context, final BluetoothConnectionService mCommandService) {
		super(context);
		Builder builder = new Builder(context);
    	builder.setTitle("UkonËiù?").setIcon(R.drawable.question).setMessage("Ste si ist˝, ûe chcete ukonËiù aplik·ciu?").setCancelable(false).setPositiveButton("¡no", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	              context.finishApp();
    	              //mCommandService.stop();
    	              
    	           }
    	       })
    	       .setNegativeButton("Zruö", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}

}
