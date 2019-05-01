package CodeGeneration.utility;

import Enums.AnsiColor;

/**
 * Contains static interfaces to allow simplified and prettier prints into the console.
 */
public class Print {
    private AnsiColor color;
    private String name;
    private String indent;
    boolean isMuted = false;

    public Print(AnsiColor c, String name) {
        this.color = c;
        this.name = name;
        this.indent = (this.getPrefix()).replaceAll(".(?![^\\[]*\\])|\\[", " ");

    }

    private String getPrefix() {
        return "[" + name + "] says -> ";
    }

    public String say(String s) {
        return this.say(color, s);
    }


    public String say(AnsiColor color, String s) {
        if (!isMuted)
            return Print.echo(color, this.getPrefix() + s.replaceAll("\n", "\n" + indent));
        return "";
    }

    public Print mute() {
        this.isMuted = true;
        return this;
    }

    /**
     * Prints a message into the console
     *
     * @param caller The object calling this print. Very useful for debugging.
     * @param c      The desired color of the print.
     * @param s      The string output
     * @return The coloured string.
     */

    public static String echo(Object caller, AnsiColor c, String s) {
        String out = c + s + AnsiColor.RESET;
        Print.echo(c, out);
        return out;
    }

    /**
     * Prints a message into the console
     *
     * @param c The desired color of the print.
     * @param s The string output
     * @return The coloured string.
     */
    public static String echo(AnsiColor c, String s) {
        String out = c + s + AnsiColor.RESET;
        System.out.println(out);
        return out;
    }

    public void newline() {
        System.out.println("\n");
    }
}
