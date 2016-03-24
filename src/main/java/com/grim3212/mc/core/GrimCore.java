package com.grim3212.mc.core;

import com.grim3212.mc.core.client.CoreGuiHandler;
import com.grim3212.mc.core.config.CoreConfig;
import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.item.CoreItems;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.event.LoginEvent;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.part.IPartItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = GrimCore.modID, name = GrimCore.modName, version = GrimCore.modVersion, dependencies = "required-after:Forge", guiFactory = "com.grim3212.mc.core.config.ConfigGuiFactory")
public class GrimCore extends GrimPart {

	@Instance(GrimCore.modID)
	public static GrimCore INSTANCE;

	public static final String modID = "grimcore";
	public static final String modName = "Grim Core";
	public static final String modVersion = "1.0.0";

	public GrimCore() {
		super(GrimCore.modID, GrimCore.modName, GrimCore.modVersion);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "The core module that all of grims mods require.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-core/";
		data.credits = "Thanks to all the mod authors of mods that I have updated. Thanks to the Forge team and everyone who has helped contribute or request mods.";

		// Register GUI handler for the Instruction Manual
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new CoreGuiHandler());

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new LoginEvent());
	}

	@Override
	protected Item getCreativeTabIcon() {
		return CoreItems.instruction_manual;
	}

	@Override
	public GrimConfig setConfig() {
		return new CoreConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("im", modSection).addSubSectionPages(new PageCrafting("instructionmanual", new ItemStack(CoreItems.instruction_manual)));
	}

	@Override
	protected IPartItems[] setItemParts() {
		return new IPartItems[] { new CoreItems() };
	}
}