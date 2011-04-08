package barilik.app.bluetoothnumpad.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.barilik.BluetoothNumpad.R;

public class myAboutDialog extends Dialog{
	
	private Button buttonOk;
	String realCodeLink = "http://code.google.com/p/wireless-numeric-keyboard/downloads/list";
	String visibleCodeLink = "code.google.com";
	String visibleMail = "Martin Barilík";
	String mailtoMail = "mailto:martin.barilik@student.tuke.sk";
	
	public myAboutDialog(Context context){
		super(context);
		setContentView(R.layout.about_dialog);
	    setTitle("Bluetooth NumPad");
	    
	    ImageView image = (ImageView) findViewById(R.id.image_about);
	    image.setImageResource(R.drawable.tuke);
	    
	    TextView textVers = (TextView) findViewById(R.id.text_title);
	    textVers.setText("Verzia 2.1beta");
	    TextView text1 = (TextView) findViewById(R.id.text1);
	    text1.setText("Zdrojový kód:");
	    TextView textLink = (TextView) findViewById(R.id.text_code);
	    textLink.setText(Html.fromHtml("<a href=" + realCodeLink + "\">" + visibleCodeLink + "</a>"));		    
	    textLink.setMovementMethod(LinkMovementMethod.getInstance());
	    TextView text2 = (TextView) findViewById(R.id.text2);
	    text2.setText("Kontakt: ");
	    TextView textMail = (TextView) findViewById(R.id.text_mail);
	    textMail.setText(Html.fromHtml("<a href=" + mailtoMail + "\">" + visibleMail + "</a>"));
	    textMail.setMovementMethod(LinkMovementMethod.getInstance());
	    buttonOk = (Button) findViewById(R.id.button_about_close);
	    buttonOk.setOnClickListener(new Listener());
	}
	
	private class Listener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			cancel();			
		}
		
	}
}
