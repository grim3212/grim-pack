package com.grim3212.mc.pack.core.part;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimCreativeTabs {

	public static CreativeTabs GRIM_CORE;
	public static CreativeTabs GRIM_CUISINE;
	public static CreativeTabs GRIM_DECOR;
	public static CreativeTabs GRIM_INDUSTRY;
	public static CreativeTabs GRIM_TOOLS;
	public static CreativeTabs GRIM_WORLD;

	public static void initTabs() {
		GRIM_CORE = new CreativeTabs(CreativeTabs.getNextID(), "core") {

			@Override
			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return "grimpack.creative." + this.getTabLabel();
			}

			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(CoreItems.instruction_manual);
			}
		};

		if (CoreConfig.useCuisine)
			GRIM_CUISINE = new CreativeTabs(CreativeTabs.getNextID(), "cuisine") {

				@Override
				@SideOnly(Side.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@SideOnly(Side.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(CuisineItems.cheese);
				}
			};

		if (CoreConfig.useDecor)
			GRIM_DECOR = new CreativeTabs(CreativeTabs.getNextID(), "decor") {

				@Override
				@SideOnly(Side.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@SideOnly(Side.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(DecorBlocks.calendar);
				}
			};

		if (CoreConfig.useIndustry)
			GRIM_INDUSTRY = new CreativeTabs(CreativeTabs.getNextID(), "industry") {

				@Override
				@SideOnly(Side.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@SideOnly(Side.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(IndustryBlocks.togglerack);
				}
			};

		if (CoreConfig.useTools)
			GRIM_TOOLS = new CreativeTabs(CreativeTabs.getNextID(), "tools") {

				@Override
				@SideOnly(Side.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@SideOnly(Side.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(ToolsItems.backpack);
				}
			};

		if (CoreConfig.useWorld)
			GRIM_WORLD = new CreativeTabs(CreativeTabs.getNextID(), "world") {

				@Override
				@SideOnly(Side.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@SideOnly(Side.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(WorldItems.gunpowder_reed_item);
				}
			};
	}
}
