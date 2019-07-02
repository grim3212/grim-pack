package com.grim3212.mc.pack.industry.block.storage;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
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
	public Page getPage(BlockState state) {
		return ManualIndustry.locksmithWorkbench_page;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.LOCKSMITH_WORKBENCH_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

}
