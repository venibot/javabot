package api;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.LocalDateTime;

public class BasicEmbed extends EmbedBuilder {

    public BasicEmbed() {
        this.setFooter("dmemsm#1706 Все права закодированы", "https://images-ext-2.discordapp.net/external/L7-DkCwFDp1MUNWTxjgvInsppR1UNGtOVYw91im7HfU/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/453179077294161920/c4064a11220a15ff053ce7f2b674de3b.webp");
        this.setAuthor("VeniBot#0739", "https://images-ext-1.discordapp.net/external/mQoekC9Fhw-04HJPX8Wm8o9-ICgn3c-grwge4uVNxXw/https/cdn.discordapp.com/avatars/728030884179083354/eab5bec7816f46847d0a85915ba74d7c.png");
        this.setTimestamp(LocalDateTime.now());
    }

    public BasicEmbed(String type) {
        switch (type) {
            case "info":
                this.setColor(Color.cyan);
                break;
            case "success":
                this.setColor(Color.green);
                break;
            case "error":
                this.setColor(Color.red);
                break;
        }
        this.setFooter("dmemsm#1706 Все права закодированы", "https://images-ext-2.discordapp.net/external/L7-DkCwFDp1MUNWTxjgvInsppR1UNGtOVYw91im7HfU/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/453179077294161920/c4064a11220a15ff053ce7f2b674de3b.webp");
        this.setAuthor("VeniBot#0739", "https://images-ext-1.discordapp.net/external/mQoekC9Fhw-04HJPX8Wm8o9-ICgn3c-grwge4uVNxXw/https/cdn.discordapp.com/avatars/728030884179083354/eab5bec7816f46847d0a85915ba74d7c.png");
        this.setTimestamp(LocalDateTime.now());
    }

    public BasicEmbed(String description, String type) {
        this.setDescription(description);
        switch (type) {
            case "info":
                this.setColor(Color.cyan);
                break;
            case "success":
                this.setColor(Color.green);
                break;
            case "error":
                this.setColor(Color.red);
                break;
        }
        this.setFooter("dmemsm#1706 Все права закодированы", "https://images-ext-2.discordapp.net/external/L7-DkCwFDp1MUNWTxjgvInsppR1UNGtOVYw91im7HfU/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/453179077294161920/c4064a11220a15ff053ce7f2b674de3b.webp");
        this.setAuthor("VeniBot#0739", "https://images-ext-1.discordapp.net/external/mQoekC9Fhw-04HJPX8Wm8o9-ICgn3c-grwge4uVNxXw/https/cdn.discordapp.com/avatars/728030884179083354/eab5bec7816f46847d0a85915ba74d7c.png");
        this.setTimestamp(LocalDateTime.now());
    }

    public BasicEmbed(String title, String description, String type) {
        this.setTitle(title);
        this.setDescription(description);
        switch (type) {
            case "info":
                this.setColor(Color.cyan);
                break;
            case "success":
                this.setColor(Color.green);
                break;
            case "error":
                this.setColor(Color.red);
                break;
        }
        this.setFooter("dmemsm#1706 Все права закодированы", "https://images-ext-2.discordapp.net/external/L7-DkCwFDp1MUNWTxjgvInsppR1UNGtOVYw91im7HfU/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/453179077294161920/c4064a11220a15ff053ce7f2b674de3b.webp");
        this.setAuthor("VeniBot#0739", "https://cdn.discordapp.com/avatars/728030884179083354/eab5bec7816f46847d0a85915ba74d7c.png");
        this.setTimestamp(LocalDateTime.now());
    }

    public BasicEmbed addField(String name, String value) {
        this.addField(name, value, false);
        return this;
    }

    public BasicEmbed setFooter(String text) {
        this.setFooter(text + " | dmemsm#1706 Все права закодированы", "https://images-ext-2.discordapp.net/external/L7-DkCwFDp1MUNWTxjgvInsppR1UNGtOVYw91im7HfU/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/453179077294161920/c4064a11220a15ff053ce7f2b674de3b.webp");
        return this;
    }

}
