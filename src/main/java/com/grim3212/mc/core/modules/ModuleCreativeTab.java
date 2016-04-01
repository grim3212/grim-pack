package com.grim3212.mc.core.modules;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModuleCreativeTab extends CreativeTabs {

	private GrimModuleHolder module;

	public ModuleCreativeTab(GrimModuleHolder module) {
		super(CreativeTabs.getNextID(), module.getId());
		this.module = module;
	}

	@Override
	public String getTranslatedTabLabel() {
		return "grim.creative." + this.getTabLabel();
	}

	@Override
	public Item getTabIconItem() {
		return this.module.getCreativeItem();
	}

}
