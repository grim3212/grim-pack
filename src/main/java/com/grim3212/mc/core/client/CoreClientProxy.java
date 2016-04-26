package com.grim3212.mc.core.client;

import com.grim3212.mc.core.item.CoreItems;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.proxy.ClientProxy;

import net.minecraft.item.ItemStack;

public class CoreClientProxy extends ClientProxy {

	@Override
	protected void registerModels() {
		RenderHelper.renderItem(CoreItems.instruction_manual);
	}

	@Override
	protected void registerManual(ModSection modSection) {
		ManualRegistry.addSection("im", modSection).addSubSectionPages(new PageCrafting("instructionmanual", new ItemStack(CoreItems.instruction_manual)));
	}

}
