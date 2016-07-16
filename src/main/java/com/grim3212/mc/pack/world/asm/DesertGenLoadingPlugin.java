package com.grim3212.mc.pack.world.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@SortingIndex(1001)
@DependsOn("forge")
@MCVersion("1.8.9")
@TransformerExclusions({ "com.grim3212.mc.pack.world.asm" })
public class DesertGenLoadingPlugin implements IFMLLoadingPlugin {

	public String[] getASMTransformerClass() {
		return new String[] { DesertGenClassTransformer.class.getName() };
	}

	public String getModContainerClass() {
		return null;
	}

	public String getSetupClass() {
		return null;
	}

	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}