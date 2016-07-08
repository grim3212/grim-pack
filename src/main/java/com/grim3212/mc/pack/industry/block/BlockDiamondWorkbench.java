package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BlockDiamondWorkbench extends Block {

	protected BlockDiamondWorkbench() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
	}

	@Override
	public boolean onBlockActivated(World worldIn, net.minecraft.util.math.BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.DIAMOND_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
