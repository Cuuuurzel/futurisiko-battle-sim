package com.cuuuurzel.fbs.risiko;

public class Battle {

	int[] atko, atkr, defo, defr, tatkl, tdefl, catkl, cdefl;
	int turns, latkr, ldefr;
	boolean isDefTurn, defStarted, atkWon;
	

	public Battle( int[] atko, int[] defo, int[] atkr, int[] defr, boolean isDefTurn, int turns ) {
		this.atko = atko;
		this.defo = defo;
		this.atkr = atkr;
		this.defr = defr;
		this.turns = turns;
		tatkl = new int[]{ atko[0]-atkr[0], atko[1]-atkr[1], atko[2]-atkr[2] };
		tdefl = new int[]{ defo[0]-defr[0], defo[1]-defr[1] };
		if ( defo[1] >= atko[1] ) defStarted = true;
		if ( turns != 0 ) { this.isDefTurn=isDefTurn; } else { this.isDefTurn=defStarted; }
	}

	public Battle( int[] atko, int[] defo, int[] atkr, int[] defr, int turns ) {
		this( atko, defo, atkr, defr, false, turns );
	}

	public Battle( int[] atko, int[] defo ) {
		this( atko, defo, atko, defo, 0 );
	}
	
	public void fastFight() {
		while ( !attackerWon() && !defenderWon() ) {
			this.step();
		}
	}
	
	public void step() {
		catkl = new int[]{0,0,0};
		cdefl = new int[]{0,0,0};
		if ( isDefTurn ) {
			catkl = getLosses( defr, atkr, 2 );
		} else {
			cdefl = getLosses( atkr, defr, 0 );			
		}
		isDefTurn = !isDefTurn;
	}
	
	private int[] getLosses( int[] a, int[] d, int cheat ) {	
		int t = 0;
		for ( int i=0; i<a[0]+a[1]+a[2]+cheat; i++ ) {
			if ( Math.random() <= 1d/3d ) t++;
		}
		if ( cheat != 0 ) { latkr=0; ldefr=t; } else { latkr=t; ldefr=0; }
		int[] l = new int[]{0,0};
		
		if ( d[1] >= t ) {
			l[1] = t;
			d[1] -= t;
			t = 0;
		} else {
			l[1] = d[1];
			t -= d[1];
			l[0] = Math.min( t,  d[0] );
			d[0] -= l[0];
			
			if ( t > 0 && cheat != 0 ) { atkWon = true; }
		}
		return l;
	}
	
	public int[] getTotalAtkLosses() {
		return tatkl;
	}
	
	public int[] getTurnAtkLosses() {
		return catkl;
	}
	
	public int[] getPercentageAtkLosses() {
		return new int[] {
			100 * tatkl[0] / atko[0],
			100 * tatkl[1] / atko[1],
			100 * tatkl[2] / atko[2]
		};
	}
	
	public int[] getTotalDefLosses() {
		return tdefl;
	}
	
	public int[] getTurnDefLosses() {
		return cdefl;
	}
	
	public int[] getPercentageDefLosses() {
		return new int[] {
			100 * tdefl[0] / defo[0],
			100 * tdefl[1] / defo[1],
			100 * tdefl[2] / defo[2]
		};
	}
	
	public boolean defStarted() {
		return this.defStarted;
	}
	
	public boolean isDefTurn() {
		return !this.isDefTurn;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public boolean attackerWon() {
		return atkWon;
	}
	
	public boolean defenderWon() {
		return atkr[0] + atkr[1] == 0;
	}

	public int[] getAtk() {
		return atko;
	}

	public int[] getDef() {
		return defo;
	}

	public int[] getAtkRemaining() {
		return atkr;
	}

	public int[] getDefRemaining() {
		return defr;
	}

	public int getAtkResult() {
		return latkr;
	}

	public int getDefResult() {
		return ldefr;
	}
}




