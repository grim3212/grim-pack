package com.grim3212.mc.pack.core.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class GrimLog {

	public static Logger log;

	public static void error(String modName, String data) {
		log.error("{" + modName + "}" + ": " + data);
	}

	public static void fatal(String modName, String data) {
		log.fatal("{" + modName + "}" + ": " + data);
	}

	public static void info(String modName, String data) {
		log.info("{" + modName + "}" + ": " + data);
	}

	public static void warn(String modName, String data) {
		log.warn("{" + modName + "}" + ": " + data);
	}

	public static void log(Level level, String modName, String data) {
		log.log(level, "{" + modName + "}" + ": " + data);
	}
}
