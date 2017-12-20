package com.grim3212.mc.pack.core.util;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.part.PartRegistry;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.ICrashCallable;

public class CrashHandler implements ICrashCallable {

	@Override
	public String call() throws Exception {
		String out = "\n";

		for (GrimPart part : PartRegistry.partsToLoad) {

			out += "\t= " + part.getPartName() + " { Loaded }\n";

			GrimConfig partConfig = part.getGrimConfig();

			for (IConfigElement ele : partConfig.getConfigItems()) {
				if (!ele.isProperty()) {

					if (ele.getQualifiedName().contains("SubPart")) {
						for (IConfigElement subs : ele.getChildElements()) {

							// It should always be but why not
							if (subs.getType() == ConfigGuiType.BOOLEAN) {
								boolean active = Boolean.valueOf((String) subs.get());

								out += "\t\t~ " + subs.getQualifiedName() + " { " + (active ? "Enabled" : "Disabled") + " }\n";
							}
						}
					}
				}
			}
		}

		return out;
	}

	@Override
	public String getLabel() {
		return GrimPack.modName + " loaded parts";
	}

}
