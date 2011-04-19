package barilik.app.bluetoothnumpad;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.barilik.BluetoothNumpad.R;
/*
 * Metoda, ktora sa stará o prácu s buttonami z layout-u, a prenos cez Bluetooth
*/
public class KeyListeners {
	
	private Activity activity = null;
	private Button button0;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button buttonPlus;
	private Button buttonMinus;
	private Button buttonMul;
	private Button buttonDiv;
	private Button buttonNum;
	private Button buttonBackspace;
	private Button buttonEnter;
	private Button buttonDot;
	//main_2nd
	private Button buttonLeft;
	
	private BluetoothConnectionService mCommandService = null;
	public static Boolean initialized = false;
	
	public KeyListeners(Activity parent, BluetoothConnectionService command) {
		this.mCommandService = command;
		this.activity = parent;
		button0 = (Button) activity.findViewById(R.id.button_0);
		button1 = (Button) activity.findViewById(R.id.button_1);
		button2 = (Button) activity.findViewById(R.id.button_2);
		button3 = (Button) activity.findViewById(R.id.button_3);
		button4 = (Button) activity.findViewById(R.id.button_4);
		button5 = (Button) activity.findViewById(R.id.button_5);
		button6 = (Button) activity.findViewById(R.id.button_6);
		button7 = (Button) activity.findViewById(R.id.button_7);
		button8 = (Button) activity.findViewById(R.id.button_8);
		button9 = (Button) activity.findViewById(R.id.button_9);
		buttonPlus = (Button) activity.findViewById(R.id.button_plus);
		buttonMinus = (Button) activity.findViewById(R.id.button_minus);
		buttonMul = (Button) activity.findViewById(R.id.button_mul);
		buttonDiv = (Button) activity.findViewById(R.id.button_div);
		buttonNum = (Button) activity.findViewById(R.id.button_numlock);
		buttonBackspace = (Button) activity.findViewById(R.id.button_backspace);
		buttonEnter = (Button) activity.findViewById(R.id.button_enter);
		buttonDot = (Button) activity.findViewById(R.id.button_dot);
		
		//Vytvorenie Listenerev pre buttony
		button0.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM00, mCommandService));

		button1.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM01, mCommandService));
		button2.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM02, mCommandService));
		button3.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM03, mCommandService));
		button4.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM04, mCommandService));
		button5.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM05, mCommandService));
		button6.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM06, mCommandService));
		button7.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM07, mCommandService));
		button8.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM08, mCommandService));
		button9.setOnClickListener(new CustomListener(BluetoothConnectionService.NUM09, mCommandService));
		buttonPlus.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMPLUS, mCommandService));
		buttonMinus.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMMINUS, mCommandService));
		buttonMul.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMMUL, mCommandService));
		buttonDiv.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMDIV, mCommandService));
		buttonNum.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMNUM, mCommandService));
		buttonBackspace.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMBACKSPACE, mCommandService));
		buttonEnter.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMENTER, mCommandService));
		buttonDot.setOnClickListener(new CustomListener(BluetoothConnectionService.NUMDOT, mCommandService));		
	}	
}
