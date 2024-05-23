package me.beneush.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class DiscordCommandOption {
    private OptionType type;
    private String name;
    private String description;
    private boolean required;
    private boolean autoComplete;

    /**
     * @param type Type of option
     * @param name Name of option
     * @param description Description of option
     */
    public DiscordCommandOption(OptionType type, String name, String description) {
        this(type, name, description, false);
    }

    /**
     * @param type Type of option
     * @param name Name of option
     * @param description Description of option
     * @param required Is option required
     */
    public DiscordCommandOption(OptionType type, String name, String description, boolean required) {
        this(type, name, description, required, false);
    }

    /**
     * @param type Type of option
     * @param name Name of option
     * @param description Description of option
     * @param required Is option required
     * @param autoComplete Is auto-completion enabled for option
     */
    public DiscordCommandOption(OptionType type, String name, String description, boolean required, boolean autoComplete) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.autoComplete = autoComplete;
    }

    public OptionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }
}
