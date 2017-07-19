package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLocksmithWorkbench extends BlockManual {

	public BlockLocksmithWorkbench() {
		super("locksmith_workbench", Material.WOOD, SoundType.WOOD);
		setHardness(3.0F);
		setResistance(5.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.locksmithWorkbench_page;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.LOCKSMITH_WORKBENCH_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

}
