package api.models.command;

public @interface CommandAttribute {

    String key();

    String value() default "";

}
