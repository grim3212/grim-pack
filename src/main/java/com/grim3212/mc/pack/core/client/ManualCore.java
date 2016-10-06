package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;

import net.minecraft.item.ItemStack;

public class ManualCore implements IManualPart {

	public static ManualCore INSTANCE = new ManualCore();

	public static Page info_page;
	public static Page instructionManual_page;

	@Override
	public void initPages() {
		info_page = new PageInfo("howto");
		instructionManual_page = new PageCrafting("instructionmanual", new ItemStack(CoreItems.instruction_manual));
	}

	@Override
	public void registerChapters(ManualPart modSection) {
		ManualRegistry.addChapter("im", modSection).addPages(info_page, instructionManual_page);
	}

}
