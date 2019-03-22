package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFrame extends ItemManual {

	private final EnumFrameType type;

	public ItemFrame(EnumFrameType type) {
		super(type.getRegistryName(), new Item.Properties().group(GrimItemGroups.GRIM_CUISINE));
		this.type = type;
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext context) {
		EnumFacing facing = context.getFace();
		EntityPlayer playerIn = context.getPlayer();
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();

		if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, context.getItem())) {
			EntityFrame frame = new EntityFrame(worldIn, pos.offset(facing), facing, this.type);

			if (frame != null && frame.onValidSurface()) {
				if (!worldIn.isRemote) {
					frame.playPlaceSound();
					worldIn.spawnEntity(frame);
				}

				context.getItem().shrink(1);
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.frames_page;
	}

	public static enum EnumFrameType {
		WOOD, IRON;

		public String getRegistryName() {
			if (this == WOOD) {
				return DecorNames.FRAME_WOOD;
			}

			return DecorNames.FRAME_IRON;
		}
	}
}