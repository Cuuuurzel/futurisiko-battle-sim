package com.cuuuurzel.fbs;

import com.cuuuurzel.fbs.risiko.Battle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StepByStepActivity extends Activity {

	Battle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_by_step);
		int[] atk = getIntent().getIntArrayExtra( "atko" );
		int[] def = getIntent().getIntArrayExtra( "defo" );
		this.b = new Battle( atk, def );
	}

	public void showResult( View v ) {
		Intent intentMain = new Intent( StepByStepActivity.this, BattleResultActivity.class );
		intentMain.putExtra( "atko", b.getAtk() );
		intentMain.putExtra( "defo", b.getDef() );
		intentMain.putExtra( "atkr", b.getAtkRemaining() );
		intentMain.putExtra( "defr", b.getAtkRemaining() );
		intentMain.putExtra( "turns", b.getTurns() );
		StepByStepActivity.this.startActivityForResult( intentMain, 1 );
	}	
	
	public void updateGui( View v ) {
		this.b.step();
		this.setRounds();
		this.setTroops();
		this.setResults();
		this.setLosses();
		this.battleEnd();
	}
	
	public void battleEnd() {
		if ( b.attackerWon() || b.defenderWon() ) {
			int[] l;
			String st, sl;
			String saw = getResources().getString( R.string.attacker_won );
			String sdw = getResources().getString( R.string.defender_won );
			
			TextView txvDetails = (TextView) findViewById( R.id.txvDetails );
			Button btnStep = (Button) findViewById( R.id.btnStep );
			btnStep.setEnabled( false );
			
			String msg;
			String cr = getResources().getString( R.string.click_result );
			if ( b.attackerWon() ) {
				msg = "\n" + saw;
				l = b.getAtkRemaining();
			} else {
				msg = "\n" + sdw;
				l = b.getDefRemaining();
			}
			
			if ( l[0]+l[1]+l[2] != 1 ) {
				st = getResources().getString( R.string.troops );
				sl = getResources().getString( R.string.lasted_plural );
			} else {
				st = getResources().getString( R.string.troop );
				sl = getResources().getString( R.string.lasted );			
			}
			msg += "\n" + l[0] + ", " + l[1] + ", " + l[2] + " " + st + " " + sl;
			msg += "\n" + cr + ".";
			txvDetails.setText( msg );
		}
	}
	
	private void setLosses() {
		setLosses( R.id.txvAtkLosses, b.getTurnAtkLosses() );
		setLosses( R.id.txvDefLosses, b.getTurnDefLosses() );
	}
	
	private void setLosses( int txvId, int[] l ) {
		TextView txv = (TextView) findViewById( txvId );
		txv.setText( l[0] + ", " + l[1] + ", " + l[2] );		
	}
	
	private void setResults() {
		setResults( R.id.txvAtkResult, b.getAtkResult() );
		setResults( R.id.txvDefResult, b.getDefResult()  );
	}
	
	private void setResults( int txvId, Integer r ) {
		TextView txv = (TextView) findViewById( txvId );
		txv.setText( r.toString() );		
	}
	
	private void setTroops() {
		setTroops( R.id.txvAtk, b.getAtk(), b.getTotalAtkLosses() );
		setTroops( R.id.txvDef, b.getDef(), b.getTotalDefLosses() );
	}
	
	private void setTroops( int txvId, int[] t, int[] l ) {
		String st;
		String oo = getResources().getString( R.string.out_of );
		if ( t[0]+t[1]+t[2]-l[0]-l[1]-l[2] != 1 ) {
			st = getResources().getString( R.string.troops );
		} else {
			st = getResources().getString( R.string.troop );			
		}
		
		TextView txv = (TextView) findViewById( txvId );
		String s = (t[0]+t[1]+t[2]-l[0]-l[1]-l[2]) + " " + st + " " + oo + " " + t + " ( ";
		s += ( 100 - 100 * ( t[0]+t[1]+t[2] ) / ( l[0]+l[1]+l[2] ) ) + "% )";
		txv.setText( s );
	}
	
	private void setRounds() {
		TextView txvRound = (TextView) findViewById( R.id.txvRound );
		String suffix;
		String rr = getResources().getString( R.string.round_result );
		switch ( b.getTurns() ) {
			case 1 :  { suffix = getResources().getString( R.string.st ); break; }
			case 2 :  { suffix = getResources().getString( R.string.nd ); break; }
			case 3 :  { suffix = getResources().getString( R.string.rd ); break; }
			default : { suffix = getResources().getString( R.string.th ); break; }
		}
		txvRound.setText( b.getTurns() + suffix + " " + rr );
	}
}
