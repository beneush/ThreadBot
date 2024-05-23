package me.beneush.commands.impl;

import me.beneush.DiscordVariables;
import me.beneush.commands.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestCommand extends DiscordCommand {
    public TestCommand() {
        super("test", "Sprawdza czy działa", Permission.ADMINISTRATOR);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        TextChannel channel = event.getJDA().getTextChannelById(DiscordVariables.CHANNEL_ID);

        channel.createThreadChannel("Weekly Thread", true).queue(thread -> {
            if (thread != null) {
                thread.sendMessage("This is the weekly thread.").queue();
            }
        });

        event.reply("Stworzono testowy wątek").queue();
    }
}
