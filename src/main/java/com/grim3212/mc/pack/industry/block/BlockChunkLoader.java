package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockChunkLoader extends BlockManual implements ITileEntityProvider {

	public BlockChunkLoader() {
		super("chunk_loader", Material.IRON, SoundType.METAL);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
		setHardness(5f);
		setResistance(5f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChunkLoader();
	}

    @Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if(!worldIn.isRemote)
		{
			TileEntityChunkLoader tileEntity = (TileEntityChunkLoader)worldIn.getTileEntity(pos);
			if(tileEntity != null)
			{
			    tileEntity.setOwner(placer.getUniqueID());
			    tileEntity.chunkLoad();
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

    @Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if(!worldIn.isRemote)
		{
            TileEntityChunkLoader tileEntity = (TileEntityChunkLoader)worldIn.getTileEntity(pos);
			if(tileEntity != null)
			{
                tileEntity.unChunkLoad();
			}
		}
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, BlockState state) {
		if(!worldIn.isRemote)
		{
            TileEntityChunkLoader tileEntity = (TileEntityChunkLoader)worldIn.getTileEntity(pos);
			if(tileEntity != null)
			{
                tileEntity.unChunkLoad();
			}
		}
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!worldIn.isRemote)
        {
            TileEntityChunkLoader tileEntity = (TileEntityChunkLoader)worldIn.getTileEntity(pos);
            if(tileEntity != null)
            {
                tileEntity.unChunkLoad();
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
