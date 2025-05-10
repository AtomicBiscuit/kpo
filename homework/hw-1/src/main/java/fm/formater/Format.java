package fm.formater;

import lombok.Getter;

@Getter
public enum Format {
    BOLD("1"),
    UNDERLINE("4"),
    BLACK("30"),
    RED("31"),
    GREEN("32"),
    YELLOW("33"),
    BLUE("34"),
    PURPLE("35"),
    CYAN("36"),
    WHITE("37"),
    ERROR(String.join(";", RED.label, BOLD.label, UNDERLINE.label));

    private final String label;

    Format(String label) {
        this.label = label;
    }
}
