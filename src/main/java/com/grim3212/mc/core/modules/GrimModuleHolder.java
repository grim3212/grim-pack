package com.grim3212.mc.core.modules;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GrimModuleHolder {

	private Object module;
	private String id;
	private String name;
	private String version;
	private String[] requiredModules;
	private String[] requiredMods;
	private ModuleCreativeTab creativeTab;

	private Item creativeItem = Items.carrot_on_a_stick;

	public GrimModuleHolder(Object module, String id, String name, String version, String[] requiredModules, String[] requiredMods) {
		this.module = module;
		this.id = id;
		this.name = name;
		this.version = version;
		this.requiredModules = requiredModules;
		this.requiredMods = requiredMods;
		this.creativeTab = new ModuleCreativeTab(this);
	}

	public ModuleCreativeTab getCreativeTab() {
		return creativeTab;
	}

	public Object getModule() {
		return module;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String[] getRequiredModules() {
		return requiredModules;
	}

	public String[] getRequiredMods() {
		return requiredMods;
	}

	public Item getCreativeItem() {
		return creativeItem;
	}

	public void setCreativeItem(Item creativeItem) {
		this.creativeItem = creativeItem;
	}

}
