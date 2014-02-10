package com.cuuuurzel.fbs;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setEditTextFilters();
	}

	public void showMyApps(View v) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://developer?id=Cuuuurzel")));
		} catch (ActivityNotFoundException anfe) {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://play.google.com/store/apps/developer?id=Cuuuurzel")));
		}
	}

	public void share(View v) {
		String message = getResources().getString(R.string.share_msg);
		String link = getResources().getString(R.string.play_store_link);
		Intent share = new Intent(Intent.ACTION_SEND);
		share.putExtra(Intent.EXTRA_TEXT, message + "\n" + link);
		share.setType("text/plain");
		startActivity(Intent.createChooser(share,
				"Title of the dialog the system will open"));
	}
	
	public void showSimulationResult(View v) {
		EditText edtAtkT = (EditText) findViewById( R.id.edtAtkT );
		EditText edtDefT = (EditText) findViewById( R.id.edtDefT );
		EditText edtAtkA = (EditText) findViewById( R.id.edtAtkA );
		EditText edtDefA = (EditText) findViewById( R.id.edtDefA );
		EditText edtAtkS = (EditText) findViewById( R.id.edtAtkS );
		int atkT = Integer.parseInt( edtAtkT.getText().toString() );
		int defT = Integer.parseInt( edtDefT.getText().toString() );
		int atkA = Integer.parseInt( edtAtkA.getText().toString() );
		int defA = Integer.parseInt( edtDefA.getText().toString() );
		int atkS = Integer.parseInt( edtAtkS.getText().toString() );	
		
		Intent intentMain = new Intent( MainActivity.this, SimulationActivity.class );
		intentMain.putExtra( "atk", new int[]{ atkT, atkA, atkS } );
		intentMain.putExtra( "def", new int[]{ defT, defA, 0 } );
		MainActivity.this.startActivityForResult( intentMain, 1 );
	}	

	public void showFightResult(View v) {
		EditText edtAtkT = (EditText) findViewById( R.id.edtAtkT );
		EditText edtDefT = (EditText) findViewById( R.id.edtDefT );
		EditText edtAtkA = (EditText) findViewById( R.id.edtAtkA );
		EditText edtDefA = (EditText) findViewById( R.id.edtDefA );
		EditText edtAtkS = (EditText) findViewById( R.id.edtAtkS );
		int atkT = Integer.parseInt( edtAtkT.getText().toString() );
		int defT = Integer.parseInt( edtDefT.getText().toString() );
		int atkA = Integer.parseInt( edtAtkA.getText().toString() );
		int defA = Integer.parseInt( edtDefA.getText().toString() );
		int atkS = Integer.parseInt( edtAtkS.getText().toString() );		
		
		Intent intentMain = new Intent( MainActivity.this, BattleResultActivity.class );
		intentMain.putExtra( "atko", new int[]{ atkT, atkA, atkS } );
		intentMain.putExtra( "defo", new int[]{ defT, defA, 0 } );
		intentMain.putExtra( "atkr", new int[]{ atkT, atkA, atkS } );
		intentMain.putExtra( "defr", new int[]{ defT, defA, 0 } );
		
		MainActivity.this.startActivityForResult( intentMain, 1 );
	}
	
	public void fightStepByStep( View v ) {
		EditText edtAtkT = (EditText) findViewById( R.id.edtAtkT );
		EditText edtDefT = (EditText) findViewById( R.id.edtDefT );
		EditText edtAtkA = (EditText) findViewById( R.id.edtAtkA );
		EditText edtDefA = (EditText) findViewById( R.id.edtDefA );
		EditText edtAtkS = (EditText) findViewById( R.id.edtAtkS );
		int atkT = Integer.parseInt( edtAtkT.getText().toString() );
		int defT = Integer.parseInt( edtDefT.getText().toString() );
		int atkA = Integer.parseInt( edtAtkA.getText().toString() );
		int defA = Integer.parseInt( edtDefA.getText().toString() );
		int atkS = Integer.parseInt( edtAtkS.getText().toString() );		
		
		Intent intentMain = new Intent( MainActivity.this, StepByStepActivity.class );
		intentMain.putExtra( "atko", new int[]{ atkT, atkA, atkS } );
		intentMain.putExtra( "defo", new int[]{ defT, defA, 0 } );
		MainActivity.this.startActivityForResult( intentMain, 1 );		
	}
	
	private void setEditTextFilters() {
		InputFilter inf = new InputFilter() {
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				try {
					int input = Integer.parseInt(dest.toString()
							+ source.toString());
					if (input > 0)
						return null;
				} catch (NumberFormatException nfe) {
				}
				return "";
			}
		};
		InputFilter[] f = new InputFilter[] { inf };

		((EditText) findViewById(R.id.edtAtkT)).setFilters(f);
		((EditText) findViewById(R.id.edtDefT)).setFilters(f);
		((EditText) findViewById(R.id.edtAtkA)).setFilters(f);
		((EditText) findViewById(R.id.edtDefA)).setFilters(f);
		((EditText) findViewById(R.id.edtAtkS)).setFilters(f);
		((EditText) findViewById(R.id.edtDefS)).setFilters(f);
	}

	public void updateText(int id, int i) {
		EditText a = (EditText) findViewById(id);
		Integer n;
		try {
			n = i + Integer.parseInt(a.getText().toString());
			if (n < 1) {
				n = 1;
			}
		} catch (NumberFormatException e) {
			n = 1;
		}
		a.setText(n.toString());
	}
}






