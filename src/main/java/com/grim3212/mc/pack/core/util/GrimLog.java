package com.grim3212.mc.pack.core.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class GrimLog {

	// TODO: Work on logger
	public static Logger log;

	public static void error(String modName, String data) {
		log.log(Level.ERROR, modName + ": " + data);
	}

	public static void fatal(String modName, String data) {
		log.log(Level.FATAL, modName + ": " + data);
	}

	public static void info(String modName, String data) {
		log.log(Level.INFO, modName + ": " + data);
	}

	public static void warn(String modName, String data) {
		log.log(Level.WARN, modName + ": " + data);
	}

	public static void log(String modName, String data) {
		log.log(Level.WARN, modName + ": " + data);
	}
}
