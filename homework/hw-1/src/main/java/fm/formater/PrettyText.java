package fm.formater;

import java.util.Arrays;

public class PrettyText {

    public static String format(String text, Format... formats) {
        return getPrefix(formats) + text + "\033[0m";
    }

    private static String getPrefix(Format... formats) {
        return "\033[" + String.join(";", Arrays.stream(formats).map(Format::getLabel).toList()) + "m";
    }

    private PrettyText() {
    }
}
