package com.grim3212.mc.pack.industry.chunkloading;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class ChunkLoaderCommand extends CommandBase {

	@Override
	public String getName() {
		return "grim_cl_stats";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("gcls");
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.grim_cl_stats.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		for (int id : DimensionManager.getIDs()) {
			World world = DimensionManager.getWorld(id);
			int chunkCount = getChunkCount(ForgeChunkManager.getPersistentChunksFor(world));

			sender.sendMessage(new TextComponentTranslation("commands.grim_cl_stats.output", chunkCount, id));
		}
	}

	public int getChunkCount(ImmutableSetMultimap<ChunkPos, Ticket> tickets) {
		int count = 0;
		for (ChunkPos key : tickets.asMap().keySet()) {
			Collection<ForgeChunkManager.Ticket> ticketList = tickets.asMap().get(key);
			for (ForgeChunkManager.Ticket ticket : ticketList) {
				if (ticket.getModId().equals(GrimPack.modID)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
}
