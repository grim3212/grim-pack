package com.grim3212.mc.core.modules;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.grim3212.mc.core.GrimCore;
import com.grim3212.mc.core.util.GrimLog;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleLoader {

	public static List<GrimModuleHolder> loadedModules = new ArrayList<GrimModuleHolder>();

	public static List<GrimModuleHolder> loadModules(Object caller) {
		List<Class<?>> classes = getAnnotatedClasses("com.grim3212.mc", GrimModule.class, caller);

		for (Class<?> clazz : classes) {
			GrimModule annotation = clazz.getAnnotation(GrimModule.class);
			try {
				GrimModuleHolder module = new GrimModuleHolder(clazz.newInstance(), annotation.id(), annotation.name(), annotation.version(), annotation.requiredModules(), annotation.requiredMods());
				setCreative(clazz, module);
				loadedModules.add(module);
			} catch (Exception e) {
				GrimLog.error(GrimCore.modName, "Module " + clazz.getName() + " errored when creating the module holder.");
				e.printStackTrace();
			}
		}

		return loadedModules;
	}

	private static void setCreative(Class<?> clazz, GrimModuleHolder module) {
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(GrimModule.CreativeItem.class)) {
				try {
					module.setCreativeItem((Item) field.get(clazz));
				} catch (Exception e) {
					GrimLog.error(GrimCore.modName, "Module " + clazz.getCanonicalName() + " had an error when setting the creative tab item.");
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public static void preInit(FMLPreInitializationEvent event) {
		for (int i = 0; i < loadedModules.size(); i++) {
			Method[] methods = loadedModules.get(i).getModule().getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(GrimModule.PreInit.class)) {
					try {
						method.invoke(loadedModules.get(i).getModule(), event);
					} catch (Exception e) {
						GrimLog.error(GrimCore.modName, "Module " + loadedModules.get(i).getModule().getClass().getCanonicalName() + " had an error when invoking preInit.");
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	public static void init(FMLInitializationEvent event) {
		for (int i = 0; i < loadedModules.size(); i++) {
			Method[] methods = loadedModules.get(i).getModule().getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(GrimModule.Init.class)) {
					try {
						method.invoke(loadedModules.get(i).getModule(), event);
					} catch (Exception e) {
						GrimLog.error(GrimCore.modName, "Module " + loadedModules.get(i).getModule().getClass().getCanonicalName() + " had an error when invoking init.");
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	public static void postInit(FMLPostInitializationEvent event) {
		for (int i = 0; i < loadedModules.size(); i++) {
			Method[] methods = loadedModules.get(i).getModule().getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(GrimModule.PostInit.class)) {
					try {
						method.invoke(loadedModules.get(i).getModule(), event);
					} catch (Exception e) {
						GrimLog.error(GrimCore.modName, "Module " + loadedModules.get(i).getModule().getClass().getCanonicalName() + " had an error when invoking postInit.");
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	public static List<Class<?>> getAnnotatedClasses(String packageName, Class<? extends Annotation> annotation, Object caller) {
		GrimLog.info(GrimCore.modID, "Searching for classes in " + packageName + " with annotation " + annotation.getName() + ".");
		List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();

		try {
			ImmutableSet<ClassInfo> set = ClassPath.from(caller.getClass().getClassLoader()).getTopLevelClassesRecursive(packageName);

			for (ClassInfo cinfo : set) {
				Class<?> clazz = Class.forName(cinfo.getName());
				if (clazz.isAnnotationPresent(annotation)) {
					annotatedClasses.add(clazz);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return annotatedClasses;
	}

}
