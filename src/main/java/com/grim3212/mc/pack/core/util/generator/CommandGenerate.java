package com.grim3212.mc.pack.core.util.generator;

public class CommandGenerate {
	
	/*public static void register(CommandDispatcher<CommandSource> dispatcher) {
	      LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("gdg").requires((source) -> {
	         return source.hasPermissionLevel(2);
	      });
	      
	      literalargumentbuilder.then(Commands.literal(gametype.getName()).executes((p_198483_1_) -> {
              return setGameMode(p_198483_1_, Collections.singleton(p_198483_1_.getSource().asPlayer()), gametype);
           }).then(Commands.argument("target", EntityArgument.players()).executes((p_198486_1_) -> {
              return setGameMode(p_198486_1_, EntityArgument.getPlayers(p_198486_1_, "target"), gametype);
           })));

	      dispatcher.register(literalargumentbuilder);
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
	}*/

}
