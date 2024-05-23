package me.beneush.commands;

import me.beneush.DiscordVariables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscordCommandManager extends ListenerAdapter {
    private List<DiscordCommand> commands;

    public DiscordCommandManager(DiscordCommand... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        DiscordCommand command = commands.stream()
                .filter(cmd -> cmd.getName().equals(event.getName()))
                .findFirst()
                .orElse(null);

        if (command == null)
            return;

        if (!event.getMember().hasPermission(command.getPermission())) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(DiscordVariables.COLOR_ERROR)
                    .setAuthor("Błąd")
                    .setTitle("Brak uprawnień")
                    .setDescription("Nie posiadasz wymaganych uprawnień aby użyć\ntej komendy, wymagane: **" + command.getPermission() + "**");

            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
        }

        command.execute(event);
    }

    public void registerCommands(Guild guild) {
        ArrayList<CommandData> commandData = new ArrayList<>();


        commands.forEach(cmd -> {
            SlashCommandData data = Commands.slash(cmd.getName(), cmd.getDescription());
            for (DiscordCommandOption option : cmd.getOptions()) {
                data = data.addOption(option.getType(), option.getName(), option.getDescription(), option.isRequired(), option.isAutoComplete());
            }
            commandData.add(data);
        });

        guild.updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        registerCommands(event.getGuild());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        registerCommands(event.getGuild());
    }
}
