package me.jupdyke01.mokcore;

import java.util.Random;

public class Util {
	
	public static int roll(int number) {
		Random r = new Random();
		return r.nextInt(number) + 1;
	}
	
}
