package com.grim3212.mc.pack.cuisine.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemSodaBottle extends ItemManual {

	private final SodaType type;

	public ItemSodaBottle(SodaType type) {
		super(type.getRegistryName(), new Item.Properties().maxStackSize(16).group(GrimItemGroups.GRIM_CUISINE));
		this.type = type;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentTranslation("grimpack.cuisine.soda.tooltip." + type.getName()));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		if (type.getHealAmount() >= 0) {
			if (playerIn.shouldHeal()) {
				playerIn.heal(type.getHealAmount());
				itemStackIn.shrink(1);
			}
		} else {
			playerIn.attackEntityFrom(DamageSource.GENERIC, type.getHealAmount());
			itemStackIn.shrink(1);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (type == SodaType.CARBONATED_WATER)
			return ManualCuisine.carbon_page;

		return ManualCuisine.soda_page;
	}

	public static enum SodaType implements IStringSerializable {
		APPLE("apple", 10), SLURM("slurm", 3), GOLDEN_APPLE("golden_apple", 20), SPIKED_ORANGE("spiked_orange", -8), ROOT_BEER("root_beer", 6), CREAM_ORANGE("cream_orange", 10), DIAMOND("diamond", 20), ORANGE("orange", 8), COCOA("cocoa", 14), MUSHROOM("mushroom", 5), CARBONATED_WATER("carbonated_water", 2);

		private final String name;
		private final int healAmount;

		private SodaType(String name, int healAmount) {
			this.name = name;
			this.healAmount = healAmount;
		}

		public int getHealAmount() {
			return healAmount;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public String getRegistryName() {
			switch (this) {
			case APPLE:
				return CuisineNames.SODA_APPLE;
			case CARBONATED_WATER:
				return CuisineNames.SODA_CARBONATED_WATER;
			case COCOA:
				return CuisineNames.SODA_COCOA;
			case CREAM_ORANGE:
				return CuisineNames.SODA_CREAM_ORANGE;
			case DIAMOND:
				return CuisineNames.SODA_DIAMOND;
			case GOLDEN_APPLE:
				return CuisineNames.SODA_GOLDEN_APPLE;
			case MUSHROOM:
				return CuisineNames.SODA_MUSHROOM;
			case ORANGE:
				return CuisineNames.SODA_ORANGE;
			case ROOT_BEER:
				return CuisineNames.SODA_ROOT_BEER;
			case SLURM:
				return CuisineNames.SODA_SLURM;
			case SPIKED_ORANGE:
				return CuisineNames.SODA_SPIKED_ORANGE;
			}

			return null;
		}

	}
}
