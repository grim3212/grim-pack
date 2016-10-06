package com.grim3212.mc.pack.core.util;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

public class GrimLog {

	public static void error(String modName, String data) {
		FMLLog.log(modName, Level.ERROR, data);
	}

	public static void fatal(String modName, String data) {
		FMLLog.log(modName, Level.FATAL, data);
	}

	public static void info(String modName, String data) {
		FMLLog.log(modName, Level.INFO, data);
	}

	public static void warn(String modName, String data) {
		FMLLog.log(modName, Level.WARN, data);
	}
}
