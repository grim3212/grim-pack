package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemBowlChocolate extends ItemManual {

	public ItemBowlChocolate(String name, int stacksize) {
		super(name, new Item.Properties().maxStackSize(stacksize).group(GrimItemGroups.GRIM_CUISINE));
	}

	public ItemBowlChocolate(String name, int stacksize, Item container) {
		super(name, new Item.Properties().maxStackSize(stacksize).group(GrimItemGroups.GRIM_CUISINE).containerItem(container));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity) {
		if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.isCreativeMode) {
			stack.shrink(1);
		}

		if (!worldIn.isRemote) {
			entity.heal(4f);
		}

		return stack.getCount() <= 0 ? new ItemStack(Items.BOWL) : stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		playerIn.setActiveHand(hand);
		return ActionResult.newResult(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public int getUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack par1ItemStack) {
		return UseAction.DRINK;
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == CuisineItems.chocolate_bowl) {
			return ManualCuisine.chocolateBowl_page;
		} else if (stack.getItem() == CuisineItems.chocolate_bowl_hot) {
			return ManualCuisine.hotChocolate_page;
		}

		return null;
	}
}
