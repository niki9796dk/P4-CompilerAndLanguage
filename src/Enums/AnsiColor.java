package Enums;

/**
 * Enums for Ansi color codes. Used by stringbuilders and implicitly appended to strings.
 */
public enum AnsiColor {
    RESET("0m"),
    BLACK("30m"),
    RED("31m"),
    GREEN("32m"),
    YELLOW("33m"),
    BLUE("34m"),
    PURPLE("35m"),
    CYAN("36m"),
    WHITE("37m"),
    BG_GREEN("42m"),
    BG_BLUE("44m"),
    BG_RESET("49m"),
    BG_WHITE("47m"),
    FONT_BOLD("1m"),
    FONT_UNDERLINE("4m");

    /**
     * The colors escape code string.
     */
    private String colorCode;

    /**
     * Construct a new AnsiColor using a color code.
     * @param colorCode The color code without the escape code.
     */
    AnsiColor(String colorCode) {
        this.colorCode = "\u001B[" +colorCode;
    }

    /**
     * @return the escape color code of the enum.
     */
    @Override
    public String toString() {
        return this.colorCode;
    }

    /**
     * A static method to remove all color codes from a string.
     * @param s The string to remove color codes from.
     * @return The input string without any color codes.
     */
    public static String removeColor(String s) {
        for (AnsiColor c : AnsiColor.values()) {
            s = s.replace(c.colorCode,"");
        }
        return s;
    }
}
