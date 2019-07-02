package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

public class BlockMetalMesh extends BlockManual {

	protected static final VoxelShape COLLISION = Block.makeCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 15.968F, 16.0F);

	public BlockMetalMesh() {
		super(IndustryNames.METAL_MESH, Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f, 4.0f));
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
		return true;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return COLLISION;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof ItemEntity && (worldIn.isAirBlock(pos.down()) || worldIn.getBlockState(pos.down()).getBlock() == IndustryBlocks.metal_mesh || !Block.hasSolidSide(worldIn.getBlockState(pos.down()), worldIn, pos.down(), Direction.UP))) {
			ItemEntity item = (ItemEntity) entityIn;
			item.setMotion(0d, item.getMotion().y, 0d);
			item.setPosition(item.posX, item.posY - 1.3D, item.posZ);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.metalMesh_page;
	}

}
