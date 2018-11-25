package me.jupdyke01.mokcore;

import org.bukkit.World;

public class TimeControl {
	
	public void runClock(final World w, final Main main) {
		boolean nightFlag = true;
		boolean dayFlag = true;
		double nightMultiplier = 2;
		double dayMultiplier = 2;
		
		boolean nightTime = (w.getTime() > 13800L) && (w.getTime() < 22000L);
		boolean dayTime = (w.getTime() <= 13800L) || (w.getTime() >= 22000L);
		if (nightTime) {
			if (nightFlag) {
				if (nightMultiplier >= 1.0D) {
					w.setFullTime(w.getFullTime() + 1L);
				}
				else if (nightMultiplier < 1.0D)
				{
					if (Math.pow(nightMultiplier, -1.0D) > 8200.0D) {
						w.setTime(22001L);
					} else {
						w.setFullTime((long) (w.getFullTime() + Math.pow(nightMultiplier, -1.0D)));
					}
				}
			}
			else {
				w.setFullTime(w.getFullTime() + 1L);
			}
		}
		else if (dayTime) {
			if (dayFlag) {
				if (dayMultiplier >= 1.0D) {
					w.setFullTime(w.getFullTime() + 1L);
				}
				else if (dayMultiplier < 1.0D) {
					if (Math.pow(dayMultiplier, -1.0D) > 8200.0D) {
						w.setTime(13801L);
					} else {
						w.setFullTime((long) (w.getFullTime() + Math.pow(dayMultiplier, -1.0D)));
					}
				}
			}
			else {
				w.setFullTime(w.getFullTime() + 1L);
			}
		}
		main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
		{
			public void run()
			{
				runClock(w, main);
			}
		}, (long) (((nightTime) && (nightFlag) ? 1.0D : nightMultiplier >= 1.0D ? nightMultiplier : dayTime ? 1.0D : (dayTime) && (dayFlag) ? 1.0D : dayMultiplier >= 1.0D ? dayMultiplier : 1.0D) * 1.0D));
	}
}
