package me.beneush.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DiscordCommand {
    private String name;
    private String description;
    private Permission permission;
    private List<DiscordCommandOption> options;

    public DiscordCommand(String name, String description, Permission permission, DiscordCommandOption... options) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permission getPermission() {
        return permission;
    }

    public List<DiscordCommandOption> getOptions() {
        return options;
    }
}
