package com.grim3212.mc.pack.world.items;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualBlockSpecial;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class WorldItems {

	public static final Item gunpowder_reed_item = (new ItemManualBlockSpecial("gunpowder_reed_item", WorldBlocks.gunpowder_reed_block)).setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	public static final Item fungicide = new ItemFungicide();
	public static final Item parabuzzy_shell = (new ItemManualPage("parabuzzy_shell", "world:8bit.parabuzzy")).setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	public static final Item bobomb = new ItemBobomb();

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(gunpowder_reed_item);
		r.register(fungicide);
		r.register(parabuzzy_shell);
		r.register(bobomb);
	}

	public static List<IRecipe> greed;

	public static void addRecipes() {
		CreateRecipes.addShapedRecipe(new ItemStack(Items.GUNPOWDER, 1), new Object[] { "X", 'X', gunpowder_reed_item });
		CreateRecipes.addShapedRecipe(new ItemStack(gunpowder_reed_item, 1), new Object[] { "XXX", "XWX", "XXX", 'X', "gunpowder", 'W', Items.REEDS });
		// greed = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(fungicide, 1, 0), new Object[] { "xkx", " x ", 'x', "ingotIron", 'k', "dyeBlack" });

		CreateRecipes.addShapedRecipe(new ItemStack(bobomb, 1), new Object[] { "X", "#", "@", 'X', "dustRedstone", '#', "gunpowder", '@', parabuzzy_shell });
	}

}
