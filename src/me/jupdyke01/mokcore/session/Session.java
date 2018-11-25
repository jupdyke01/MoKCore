package me.jupdyke01.mokcore.session;

public class Session {

	long startTime;
	long endTime;
	
	public Session(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public long getSessionLength() {
		return endTime - startTime;
	}
	
}
