package com.grim3212.mc.pack.util.client.event;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.network.MessageAutoTorch;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoTorchTickHandler {

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
		if (guiscreen != null) {
			return;
		} else {
			onTickInGame();
		}
	}

	public void onTickInGame() {
		if (UtilConfig.atEnabled.getBoolean()) {
			Minecraft mc = Minecraft.getMinecraft();

			EntityPlayerSP entityplayersp = mc.player;
			World world = mc.world;

			EnumFacing facing = entityplayersp.getAdjustedHorizontalFacing();

			BlockPos pos = new BlockPos(entityplayersp.posX - (double) entityplayersp.width * 0.35D, entityplayersp.posY + 0.5D, entityplayersp.posZ + (double) entityplayersp.width * 0.35D);

			// Get brightness before we affect the position
			int brightness = (int) ((world.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos) / 15f) * 100F);
			// int brightness = (int) (entityplayersp.getBrightness() * 100F);

			if (UtilConfig.torchDistance > 0) {
				pos = pos.offset(facing, UtilConfig.torchDistance);
			}

			IBlockState down = world.getBlockState(pos.down());
			if (brightness <= UtilConfig.lightTolerance) {
				if (down.getBlock().canPlaceTorchOnTop(down, world, pos.down()) && !(world.getBlockState(pos).getBlock() instanceof BlockLiquid || world.getBlockState(pos).getBlock() instanceof BlockFluidBase) && (world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos))) {
					// Make sure player has a torch
					if (Utils.consumePlayerItem(entityplayersp, new ItemStack(Blocks.TORCH)) != ItemStack.EMPTY) {
						PacketDispatcher.sendToServer(new MessageAutoTorch(pos));
					}
				}
			}
		}
	}
}
