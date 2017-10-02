package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.network.MessageNeonOpen;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemNeonSign extends ItemManual {

	public ItemNeonSign() {
		super("neon_sign");
		this.maxStackSize = 16;
		this.setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.neonSign_page;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);

		if (facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP)) {
			pos = pos.offset(facing);
			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && DecorBlocks.neon_sign_standing.canPlaceBlockAt(worldIn, pos)) {
				pos = flag ? pos.down() : pos;
				if (facing == EnumFacing.UP) {
					int i = MathHelper.floor((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
					worldIn.setBlockState(pos, DecorBlocks.neon_sign_standing.getDefaultState().withProperty(BlockStandingSign.ROTATION, i), 11);
				} else {
					worldIn.setBlockState(pos, DecorBlocks.neon_sign_wall.getDefaultState().withProperty(BlockWallSign.FACING, facing), 11);
				}

				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity instanceof TileEntityNeonSign && !ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack)) {
					if (!worldIn.isRemote) {
						((TileEntityNeonSign) tileentity).setPlayer(player);
						PacketDispatcher.sendTo(new MessageNeonOpen(pos), (EntityPlayerMP) player);
					}
				}

				if (player instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
				}

				itemstack.shrink(1);
				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.FAIL;
			}
		} else {
			return EnumActionResult.FAIL;
		}
	}
}