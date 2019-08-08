package mrg;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

public final class ContentGenerator {

    public @NotNull String content(){
        return  "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>Example</title></head>" +
                "<body><p>" + "Current time " + Date.from(Instant.now()) + "</p></body>" +
                "</html>";
    }
}
