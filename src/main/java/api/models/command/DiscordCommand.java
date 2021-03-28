package api.models.command;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DiscordCommand {

    String name();

    String description() default "Отсутствует";

    String group();

    String[] aliases();

    String usage() default "";

    Permission[] permissions() default {Permission.MESSAGE_WRITE};

    boolean hidden() default false;

    int arguments() default 0;
}