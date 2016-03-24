package com.grim3212.mc.decor.block;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.decor.item.GrimDecor;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DecorBlocks implements IPartItems {

	public static Block calendar;

	@Override
	public void initItems() {
		calendar = (new BlockCalendar()).setHardness(1.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("calendar").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());

		GameRegistry.registerBlock(calendar, "calendar");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(calendar, 1), new Object[] { "##", "##", "##", '#', Items.paper }));
	}

	@Override
	public void renderItems() {
		RenderHelper.renderBlock(calendar);
	}

}
