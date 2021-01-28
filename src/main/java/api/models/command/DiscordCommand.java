package api.models.command;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DiscordCommand {

    String name();

    String description() default "Отсутствует";

    String[] aliases();

    boolean hidden() default false;

    int arguments() default 0;

    CommandAttribute[] attributes() default {};
}