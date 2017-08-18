package com.grim3212.mc.pack.world.items;

import com.grim3212.mc.pack.core.item.ItemManualBlockSpecial;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class WorldItems {

	public static final Item gunpowder_reed_item = (new ItemManualBlockSpecial("gunpowder_reed_item", WorldBlocks.gunpowder_reed_block)).setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	public static final Item fungicide = new ItemFungicide();
	public static final Item parabuzzy_shell = (new ItemManualPage("parabuzzy_shell", "world:8bit.parabuzzy")).setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	public static final Item bobomb = new ItemBobomb();

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (WorldConfig.subpartGunpowderReeds)
			r.register(gunpowder_reed_item);

		if (WorldConfig.subpartGunpowderReeds)
			r.register(fungicide);

		if (WorldConfig.subpart8BitMobs) {
			r.register(parabuzzy_shell);
			r.register(bobomb);
		}
	}
}