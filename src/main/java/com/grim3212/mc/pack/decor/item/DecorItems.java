package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.item.ItemFrame.EnumFrameType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class DecorItems {

	@ObjectHolder(DecorNames.GLASS_SHARD)
	public static Item glass_shard;
	@ObjectHolder(DecorNames.FRAME_WOOD)
	public static Item frame_wood;
	@ObjectHolder(DecorNames.FRAME_IRON)
	public static Item frame_iron;
	@ObjectHolder(DecorNames.WALLPAPER)
	public static Item wallpaper;
	@ObjectHolder(DecorNames.UNFIRED_CRAFT)
	public static Item unfired_craft;
	@ObjectHolder(DecorNames.UNFIRED_POT)
	public static Item unfired_pot;
	@ObjectHolder(DecorNames.LAMP_ITEM)
	public static Item lamp_item;
	@ObjectHolder(DecorNames.BRUSH)
	public static Item brush;
	@ObjectHolder(DecorNames.PRUNERS)
	public static Item pruners;
	@ObjectHolder(DecorNames.FLAT_ITEM_FRAME)
	public static Item flat_item_frame;
	@ObjectHolder(DecorNames.NEON_SIGN)
	public static Item neon_sign;

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (DecorConfig.subpartNeonSign.get())
			r.register(new ItemNeonSign());

		if (DecorConfig.subpartLightBulbs.get())
			r.register(new ItemManualPage(DecorNames.GLASS_SHARD, "decor:deco.lights", new Item.Properties().group(GrimItemGroups.GRIM_DECOR)));

		if (DecorConfig.subpartFrames.get()) {
			r.register(new ItemFrame(EnumFrameType.WOOD));
			r.register(new ItemFrame(EnumFrameType.IRON));
		}

		if (DecorConfig.subpartWallpaper.get())
			r.register(new ItemWallpaper());

		if (DecorConfig.subpartDecorations.get()) {
			r.register(new ItemManualPage(DecorNames.UNFIRED_CRAFT, "decor:deco.crafts", new Item.Properties().group(GrimItemGroups.GRIM_DECOR)));
			r.register(new ItemManualPage(DecorNames.UNFIRED_POT, "decor:deco.crafts", new Item.Properties().group(GrimItemGroups.GRIM_DECOR)));
		}

		if (DecorConfig.subpartFlatItemFrame.get())
			r.register(new ItemFlatItemFrame());

		if (DecorConfig.subpartColorizer.get()) {
			r.register(new ItemBrush());

			if (DecorConfig.subpartLampPosts.get())
				r.register(new ItemLampPost());

			if (DecorConfig.subpartSlopes.get())
				r.register(new ItemPruners());
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (DecorConfig.subpartDecorations) {
			GameRegistry.addSmelting(unfired_craft, new ItemStack(DecorBlocks.craft_clay, 1), 0.35F);
			GameRegistry.addSmelting(unfired_pot, new ItemStack(DecorBlocks.pot, 1), 0.35F);
		}
	}
}