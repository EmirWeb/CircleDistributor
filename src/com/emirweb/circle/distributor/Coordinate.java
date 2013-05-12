package com.emirweb.circle.distributor;

public final class Coordinate {

	private final double mX;
	private final double mY;
	
	public Coordinate(final double x, final double y){
		mX = x;
		mY = y;
	}
	
	public final double getX(){
		return mX;
	}
	
	public final double getY(){
		return mY;
	}
	
	public final String toString(){
		return "(" + mX + ", " + mY + ")";
	}
}
