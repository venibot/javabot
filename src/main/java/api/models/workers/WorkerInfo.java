package api.models.workers;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WorkerInfo {

    String name();

    String description() default "";

    TimeUnit type() default TimeUnit.MINUTES;

    long duration() default 1;

    boolean active() default true;

}
