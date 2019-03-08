package com.grim3212.mc.pack.core.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Stopwatch;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;

public class GrimLog {

	public static final Logger log = LogManager.getLogger(GrimPack.modID);

	public static void error(String modName, String data) {
		log.error("{" + modName + "}" + ": " + data);
	}

	public static void fatal(String modName, String data) {
		log.fatal("{" + modName + "}" + ": " + data);
	}

	public static void info(String modName, String data) {
		log.info("{" + modName + "}" + ": " + data);
	}

	public static void debug(String modName, String data) {
		log.debug("{" + modName + "}" + ": " + data);
	}

	public static void debugInfo(String modName, String data) {
		if (CoreConfig.showDebugInfo.get())
			log.info("{" + modName + "}" + ": " + data);
	}

	public static void warn(String modName, String data) {
		log.warn("{" + modName + "}" + ": " + data);
	}

	public static void log(Level level, String modName, String data) {
		log.log(level, "{" + modName + "}" + ": " + data);
	}

	public static class LogTimer {
		// Based off of JEI LoggedTimer
		private static final Stopwatch stopWatch = Stopwatch.createUnstarted();
		private static String message = "";

		public static void start(String message) {
			LogTimer.message = message;
			GrimLog.info(GrimPack.modName, message + " starting...");
			stopWatch.reset();
			stopWatch.start();
		}

		public static void stop() {
			stopWatch.stop();
			GrimLog.info(GrimPack.modName, message + " completed in " + stopWatch);
		}
	}
}
