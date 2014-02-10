package com.cuuuurzel.fbs;

import com.cuuuurzel.fbs.risiko.Battle;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SimulationActivity extends Activity {

	int[] atk, def;
	private float atkWinningChance;
	private float atkWinningWithoutLossesChange;
	private int avgTurnsForAtkWin;
	private float defWinningChance;
	private float defWinningWithoutLossesChange;
	private int avgTurnsForDefWin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulation);
		atk = getIntent().getIntArrayExtra( "atk" );
		def = getIntent().getIntArrayExtra( "def" );
		this.simulate();
		this.updateGui();
	}
	
	private void simulate() {
		Battle b;
		for ( int i=0; i<1000; i++ ) {
			b = new Battle( atk, def );
			b.fastFight();
			
			if ( b.attackerWon() ) { 
				atkWinningChance++;
				if ( b.getTotalAtkLosses()[0] + b.getTotalAtkLosses()[1] == 0 ) {
					atkWinningWithoutLossesChange++;
				}
				avgTurnsForAtkWin += b.getTurns();
			}
			if ( b.defenderWon() ) { 
				defWinningChance++;
				if ( b.getTotalDefLosses()[0] + b.getTotalDefLosses()[1] == 0 ) {
					defWinningWithoutLossesChange++;
				}
				avgTurnsForDefWin += b.getTurns();
			}
		}

		atkWinningChance = 100 * atkWinningChance / 1000;
		atkWinningWithoutLossesChange = 100 * atkWinningWithoutLossesChange / 1000;
		avgTurnsForAtkWin = 100 * avgTurnsForAtkWin / 1000;
		defWinningChance = 100 * defWinningChance / 1000;
		defWinningWithoutLossesChange = 100 * defWinningWithoutLossesChange / 1000;
		avgTurnsForDefWin = 100 * avgTurnsForDefWin / 1000;
	}
	
	public void updateGui() {
		String msg = "";
		msg += getMsg( 
			getResources().getString( R.string.attacker ), 
			atkWinningChance,
			atkWinningWithoutLossesChange, 
			avgTurnsForAtkWin
		);
		msg += "\n\n";
		msg += getMsg( 
			getResources().getString( R.string.defender ),
			defWinningChance,
			defWinningWithoutLossesChange,
			avgTurnsForDefWin
		);
		TextView txvResult = (TextView) findViewById( R.id.txvResult );
		txvResult.setText( msg );
	}

	private String getMsg( String p, float c, float cll, int t ) {
		String msg = "";
		String s1 = getResources().getString( R.string.chance );
		String s2 = getResources().getString( R.string.without_losses );
		String s3 = getResources().getString( R.string.avg_rounds );
		
		msg += "-- " + p + " --\n";
		msg += s1 + " : " + c + "%\n";
		msg += "\" \" \" " + s2 + " : " + cll + "%\n";
		msg += s3 + " : " + t + "\n";
		return msg;
	}
}
