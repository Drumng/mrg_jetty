package mrg;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

public final class ContentGenerator {

    public @NotNull String content(){
        return  "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>Example</title></head>" +
                "<body><h1>" + "Current time " + Date.from(Instant.now()) + "</h1></body>" +
                "</html>";
    }
}
