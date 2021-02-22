package api.models.command;

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

    boolean hidden() default false;

    int arguments() default 0;

}
