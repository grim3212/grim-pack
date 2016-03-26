package com.grim3212.mc.decor.item;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.decor.GrimDecor;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DecorItems implements IPartItems {

	public static Item glass_shard;

	@Override
	public void initItems() {
		glass_shard = (new Item()).setUnlocalizedName("glass_shard").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());

		GameRegistry.registerItem(glass_shard, "glass_shard");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.glass, 1), new Object[] { "##", "##", '#', glass_shard }));
	}

	@Override
	public void renderItems() {
		RenderHelper.renderItem(glass_shard);
	}

}
