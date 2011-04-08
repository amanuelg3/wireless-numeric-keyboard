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
	private Button buttonDel;
	private Button buttonEnter;
	private Button buttonDot;
	
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
		buttonDel = (Button) activity.findViewById(R.id.button_del);
		buttonEnter = (Button) activity.findViewById(R.id.button_enter);
		buttonDot = (Button) activity.findViewById(R.id.button_dot);
		
		//Vytvorenie Listenerev pre buttony
		button0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM00);
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM01);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM02);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM03);
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM04);
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM05);
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM06);
			}
		});
		button7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM07);
			}
		});
		button8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM08);
			}
		});
		button9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUM09);
			}
		});
		buttonPlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMPLUS);
			}
		});
		buttonMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMMINUS);
			}
		});
		buttonMul.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMMUL);
			}
		});
		buttonDiv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMDIV);
			}
		});
		buttonNum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMNUM);
			}
		});
		buttonDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMDEL);
			}
		});
		buttonEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMENTER);
			}
		});
		buttonDot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCommandService.write(BluetoothConnectionService.NUMDOT);
			}
		});
	}	
}
