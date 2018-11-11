package com.grim3212.mc.pack.tools.magic;

import com.grim3212.mc.pack.core.util.Utils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicIce extends BaseMagic {

    public MagicIce() {
        super("ice");
    }

    @Override
    public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float rangeF) {
        BlockPos pos = playerIn.getPosition();

        int count = 0;
        int range = (int) rangeF;

        for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
                    if (Utils.getDistance(x, z, pos.getX(), pos.getZ()) > range || Utils.getDistance(x, y, pos.getX(), pos.getY()) > range) {
                        continue;
                    }

                    BlockPos newPos = new BlockPos(x, y, z);
                    IBlockState state = world.getBlockState(newPos);
                    
                    if(!(count <= dmgLeft))
                    	return count;

                    if (state.getBlock() == Blocks.WATER && state.getValue(BlockLiquid.LEVEL) == 0 || state.getBlock() == Blocks.FLOWING_WATER && state.getValue(BlockLiquid.LEVEL) == 0) {
                        world.setBlockState(newPos, Blocks.ICE.getDefaultState());
                        count++;
                    } else if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, newPos) && world.isAirBlock(newPos)) {
                        world.setBlockState(newPos, Blocks.SNOW_LAYER.getDefaultState());
                        count++;
                    }
                }
            }
        }

        return count;
    }
}