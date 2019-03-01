package com.grim3212.mc.pack.core.part;

import com.grim3212.mc.pack.core.item.CoreItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GrimCreativeTabs {

	public static ItemGroup GRIM_CORE;
	public static ItemGroup GRIM_CUISINE;
	public static ItemGroup GRIM_DECOR;
	public static ItemGroup GRIM_INDUSTRY;
	public static ItemGroup GRIM_TOOLS;
	public static ItemGroup GRIM_WORLD;

	public static void initTabs() {
		GRIM_CORE = new ItemGroup("core") {

			@Override
			@OnlyIn(Dist.CLIENT)
			public String getTranslationKey() {
				return "grimpack.creative." + this.getTabLabel();
			}

			@Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack createIcon() {
				return new ItemStack(CoreItems.instruction_manual);
			}
		};

		/*if (CoreConfig.useCuisine)
			GRIM_CUISINE = new CreativeTabs(CreativeTabs.getNextID(), "cuisine") {

				@Override
				@OnlyIn(Dist.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(CuisineItems.cheese);
				}
			};

		if (CoreConfig.useDecor)
			GRIM_DECOR = new CreativeTabs(CreativeTabs.getNextID(), "decor") {

				@Override
				@OnlyIn(Dist.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(DecorBlocks.calendar);
				}
			};

		if (CoreConfig.useIndustry)
			GRIM_INDUSTRY = new CreativeTabs(CreativeTabs.getNextID(), "industry") {

				@Override
				@OnlyIn(Dist.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(IndustryBlocks.togglerack);
				}
			};

		if (CoreConfig.useTools)
			GRIM_TOOLS = new CreativeTabs(CreativeTabs.getNextID(), "tools") {

				@Override
				@OnlyIn(Dist.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(ToolsItems.backpack);
				}
			};

		if (CoreConfig.useWorld)
			GRIM_WORLD = new CreativeTabs(CreativeTabs.getNextID(), "world") {

				@Override
				@OnlyIn(Dist.CLIENT)
				public String getTranslatedTabLabel() {
					return "grimpack.creative." + this.getTabLabel();
				}

				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack getTabIconItem() {
					return new ItemStack(WorldItems.gunpowder_reed_item);
				}
			};*/
	}
}
