package Enums;

public enum AnsiColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BG_GREEN("\u001B[42m"),
    BG_BLUE("\u001B[44m"),
    BG_RESET("\u001B[49m"),
    BG_WHITE("\u001B[37m"),
    FONT_BOLD("\u001B[1m"),
    FONT_UNDERLINE("\u001B[4m");

    private String colorCode;

    AnsiColor(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return this.colorCode;
    }

    public static String removeColor(String s) {
        for (AnsiColor c : AnsiColor.values()) {
            s = s.replace(c.colorCode,"");
        }
        return s;
    }
}
