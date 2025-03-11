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
    WHITE("37");

    private final String label;

    private Format(String label) {
        this.label = label;
    }
}
