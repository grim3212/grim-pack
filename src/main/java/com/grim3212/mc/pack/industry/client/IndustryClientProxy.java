package com.grim3212.mc.pack.industry.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.industry.block.BlockFountain;
import com.grim3212.mc.pack.industry.block.BlockModernDoor;
import com.grim3212.mc.pack.industry.block.BlockSiding;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.model.CamoPlateModel.CamoPlateModelLoader;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.TileEntityCamo;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class IndustryClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		// Register all custom models for camo plates
		ModelLoaderRegistry.registerLoader(CamoPlateModelLoader.instance);

		ModelLoader.setCustomStateMapper(IndustryBlocks.door_chain, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.door_glass, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.door_steel, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.fountain, new StateMap.Builder().ignore(BlockFountain.ACTIVE).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.horizontal_siding, new StateMap.Builder().ignore(BlockSiding.COLOR).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.vertical_siding, new StateMap.Builder().ignore(BlockSiding.COLOR).build());

		// ITEMS
		RenderHelper.renderItem(IndustryItems.iron_stick);
		RenderHelper.renderItem(IndustryItems.gravity_boots);
		RenderHelper.renderItem(IndustryItems.graphite);
		RenderHelper.renderItem(IndustryItems.graphite_rod);
		RenderHelper.renderItem(IndustryItems.anti_radiation_boots);
		RenderHelper.renderItem(IndustryItems.anti_radiation_legs);
		RenderHelper.renderItem(IndustryItems.anti_radiation_chest);
		RenderHelper.renderItem(IndustryItems.anti_radiation_helmet);
		RenderHelper.renderItem(IndustryItems.iron_parts);
		RenderHelper.renderItem(IndustryItems.plutonium_ingot);
		RenderHelper.renderItem(IndustryItems.reactor_core);
		RenderHelper.renderItem(IndustryItems.reactor_core_case);
		RenderHelper.renderItem(IndustryItems.refined_plutonium);
		RenderHelper.renderItem(IndustryItems.refined_uranium);
		RenderHelper.renderItem(IndustryItems.uranium_ingot);
		RenderHelper.renderItem(IndustryItems.low_gravity_controller);
		RenderHelper.renderItem(IndustryItems.gravity_controller);
		RenderHelper.renderItem(IndustryItems.aluminum_ingot);
		RenderHelper.renderItem(IndustryItems.garage_panel);
		RenderHelper.renderItem(IndustryItems.garage_remote);
		RenderHelper.renderItem(IndustryItems.gate_grating);
		RenderHelper.renderItem(IndustryItems.gate_trumpet);
		RenderHelper.renderItem(IndustryItems.paint_roller);
		RenderHelper.renderItem(IndustryItems.paint_roller_white);
		RenderHelper.renderItem(IndustryItems.paint_roller_red);
		RenderHelper.renderItem(IndustryItems.paint_roller_green);
		RenderHelper.renderItem(IndustryItems.paint_roller_blue);
		RenderHelper.renderItem(IndustryItems.tarball);
		RenderHelper.renderItem(IndustryItems.asphalt);
		RenderHelper.renderItem(IndustryItems.door_chain_item);
		RenderHelper.renderItem(IndustryItems.door_glass_item);
		RenderHelper.renderItem(IndustryItems.door_steel_item);
		RenderHelper.renderItem(IndustryItems.rubber);
		RenderHelper.renderItem(IndustryItems.aluminum_can);
		RenderHelper.renderItem(IndustryItems.aluminum_shaft);
		RenderHelper.renderItem(IndustryItems.oily_chunk);
		RenderHelper.renderItem(IndustryItems.coal_dust);
		RenderHelper.renderItem(IndustryItems.coal_iron_ingot);
		RenderHelper.renderItem(IndustryItems.crude_oil);
		RenderHelper.renderItem(IndustryItems.fuel);
		RenderHelper.renderItem(IndustryItems.super_crude_oil);
		RenderHelper.renderItem(IndustryItems.steel_ingot);
		RenderHelper.renderItem(IndustryItems.steel_shaft);
		RenderHelper.renderItem(IndustryItems.steel_axe);
		RenderHelper.renderItem(IndustryItems.steel_hoe);
		RenderHelper.renderItem(IndustryItems.steel_pickaxe);
		RenderHelper.renderItem(IndustryItems.steel_shovel);
		RenderHelper.renderItem(IndustryItems.steel_sword);

		// BLOCKS
		RenderHelper.renderBlock(IndustryBlocks.togglerack);
		RenderHelper.renderBlock(IndustryBlocks.iron_workbench);
		RenderHelper.renderBlock(IndustryBlocks.diamond_workbench);
		RenderHelper.renderBlock(IndustryBlocks.ice_maker);
		RenderHelper.renderBlock(IndustryBlocks.fire_block);
		RenderHelper.renderBlock(IndustryBlocks.lava_block);
		RenderHelper.renderBlock(IndustryBlocks.water_block);
		RenderHelper.renderBlock(IndustryBlocks.spike);
		RenderHelper.renderBlock(IndustryBlocks.wooden_sensor);
		RenderHelper.renderBlock(IndustryBlocks.stone_sensor);
		RenderHelper.renderBlock(IndustryBlocks.iron_sensor);
		RenderHelper.renderBlock(IndustryBlocks.netherrack_sensor);
		RenderHelper.renderBlock(IndustryBlocks.attractor);
		RenderHelper.renderBlock(IndustryBlocks.repulsor);
		RenderHelper.renderBlock(IndustryBlocks.gravitor);
		RenderHelper.renderBlock(IndustryBlocks.direction_attractor);
		RenderHelper.renderBlock(IndustryBlocks.direction_repulsor);
		RenderHelper.renderBlock(IndustryBlocks.direction_gravitor);
		RenderHelper.renderBlock(IndustryBlocks.bomb_shell);
		RenderHelper.renderBlock(IndustryBlocks.c4);
		RenderHelper.renderBlock(IndustryBlocks.reactor);
		RenderHelper.renderBlock(IndustryBlocks.nuclear_bomb);
		RenderHelper.renderBlock(IndustryBlocks.uranium_ore);
		RenderHelper.renderBlock(IndustryBlocks.castle_gate);
		RenderHelper.renderBlock(IndustryBlocks.garage);
		RenderHelper.renderBlock(IndustryBlocks.halogen_light);
		RenderHelper.renderBlock(IndustryBlocks.halogen_torch);
		RenderHelper.renderBlock(IndustryBlocks.rway);
		RenderHelper.renderBlock(IndustryBlocks.rway_light_off);
		RenderHelper.renderBlock(IndustryBlocks.rway_light_on);
		RenderHelper.renderBlock(IndustryBlocks.rway_manhole);
		RenderHelper.renderBlock(IndustryBlocks.sidewalk);
		RenderHelper.renderBlock(IndustryBlocks.door_chain);
		RenderHelper.renderBlock(IndustryBlocks.door_glass);
		RenderHelper.renderBlock(IndustryBlocks.door_steel);
		RenderHelper.renderBlock(IndustryBlocks.concrete);
		RenderHelper.renderBlock(IndustryBlocks.modern_tile);
		RenderHelper.renderBlock(IndustryBlocks.tempered_glass);
		RenderHelper.renderBlock(IndustryBlocks.horizontal_siding);
		RenderHelper.renderBlock(IndustryBlocks.vertical_siding);
		RenderHelper.renderBlock(IndustryBlocks.chain_fence);
		RenderHelper.renderBlock(IndustryBlocks.fountain);
		RenderHelper.renderBlock(IndustryBlocks.fuel_tank);
		RenderHelper.renderBlock(IndustryBlocks.aluminum_ore);
		RenderHelper.renderBlock(IndustryBlocks.aluminum_ladder);
		RenderHelper.renderBlock(IndustryBlocks.oil_ore);
		RenderHelper.renderBlock(IndustryBlocks.steel_block);
		RenderHelper.renderBlock(IndustryBlocks.steel_frame);
		RenderHelper.renderBlock(IndustryBlocks.steel_pipe);
		RenderHelper.renderBlock(IndustryBlocks.derrick);
		RenderHelper.renderBlock(IndustryBlocks.refinery);
		RenderHelper.renderBlock(IndustryBlocks.modern_furnace);
		RenderHelper.renderBlock(IndustryBlocks.camo_plate);
	}

	@Override
	public void initColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (state.getBlock() instanceof BlockSiding) {
					return state.getValue(BlockSiding.COLOR).color;
				}

				return -1;
			}
		}, IndustryBlocks.horizontal_siding, IndustryBlocks.vertical_siding);

		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (pos != null) {
					TileEntity te = worldIn.getTileEntity(pos);
					if (te != null && te instanceof TileEntityCamo) {
						return Minecraft.getMinecraft().getBlockColors().colorMultiplier(worldIn.getBlockState(pos.down()), worldIn, pos, tintIndex);
					}
				}
				return 16777215;
			}
		}, IndustryBlocks.camo_plate);
	}
}
