package com.cuuuurzel.fbs;

import com.cuuuurzel.fbs.risiko.Battle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BattleResultActivity extends Activity {

	Battle b;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.battle_result );
		int[] atko = getIntent().getIntArrayExtra( "atko" );
		int[] defo = getIntent().getIntArrayExtra( "defo" );
		int[] atkr = getIntent().getIntArrayExtra( "atkr" );
		int[] defr = getIntent().getIntArrayExtra( "defr" );
		int turns = getIntent().getIntExtra( "turns", 0 );
		this.b = new Battle( atko, defo, atkr, defr, turns );
		this.b.fastFight();
		this.setResult();
	}
	
	private void setResult() {
		//TextView txvStats = (TextView) findViewById( R.id.txvBattleStats );
		//txvStats.setText( getStringStats() );
		TextView txvWinner = (TextView) findViewById( R.id.txvWinner );
		txvWinner.setText( getStringResult() );
	}
	
	private String getStringResult() {
		if ( b.attackerWon() ) {
			return "Attacker won!";
		} else {
			return "Defender won!";
		}
	}
}
