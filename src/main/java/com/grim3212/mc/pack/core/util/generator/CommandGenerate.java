package com.grim3212.mc.pack.core.util.generator;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandGenerate extends CommandBase {

	@Override
	public String getName() {
		return "grim_documentation_generation";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("gdg");
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.grim_documentation_generation.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender instanceof CommandBlockBaseLogic)) {
			if (!args[0].isEmpty()) {
				sender.sendMessage(new TextComponentTranslation("commands.grim_documentation_generation.start"));

				// Generate the file at the specified location
				if (Generator.document(args[0])) {
					sender.sendMessage(new TextComponentTranslation("commands.grim_documentation_generation.finish"));
				} else {
					sender.sendMessage(new TextComponentTranslation("commands.grim_documentation_generation.error"));
				}
			} else {
				sender.sendMessage(new TextComponentTranslation("commands.grim_documentation_generation.nofile"));
			}
		}
	}

}
