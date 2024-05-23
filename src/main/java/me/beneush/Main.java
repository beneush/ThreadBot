package me.beneush;

import lombok.Getter;
import me.beneush.commands.DiscordCommandManager;
import me.beneush.commands.impl.TestCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends ListenerAdapter {
    @Getter
    public static JDA jda;
    public static DiscordCommandManager discordCommandManager;

    public static void main(String[] args) {

        discordCommandManager = new DiscordCommandManager(new TestCommand());

        jda = JDABuilder.createDefault(DiscordVariables.TOKEN)
                .setActivity(Activity.listening("Playboi Carti"))
                .addEventListeners(new Main(), discordCommandManager)
                .build();
    }

    @Override
    public void onReady(ReadyEvent event) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                createWeeklyThread(event.getJDA().getTextChannelById(DiscordVariables.CHANNEL_ID));
            }
        }, 0, Duration.ofDays(1).toMillis());
    }

    private void createWeeklyThread(TextChannel channel) {
        if (channel != null) {
            LocalDate today = LocalDate.now(ZoneId.of("GMT+2"));
            if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
                channel.createThreadChannel("Weekly Thread - " + today, true).queue(thread -> {
                    if (thread != null) {
                        thread.sendMessage("This is the weekly thread for the week starting " + today).queue();
                    }
                });
            }
        }
    }
}