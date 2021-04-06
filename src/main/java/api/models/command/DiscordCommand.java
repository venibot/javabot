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

    short accessLevel() default 0;
    // уровень доступа 0 - команда доступна всем(не учитываются права на сервере, для них отдельная проверка)
    // уровень доступа 1 - необходимо быть на сервере поддержки
    // уровень доступа 2 - нужно быть тестером бота
    // уровень доступа 3 - нужно иметь роль поддержки бота
    // уровень доступа 4 - нужно быть разработчиком бота
    // уровень доступа 5 - нужно быть создаетелем бота

    int arguments() default 0;
}