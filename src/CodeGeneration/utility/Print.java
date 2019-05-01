package CodeGeneration.utility;

import Enums.AnsiColor;
//Hi!

/**
 * Contains static interfaces connect allow simplified and prettier prints into the console.
 */
public class Print {
    private AnsiColor color;
    private String name;
    private String indent;
    boolean isMuted = false;

    private static String lastPrefix = null;
    public static int minimalIndent = 35;

    public static boolean samePrefix(String prefix){
        if(prefix.equals(lastPrefix))
            return true;

        Print.lastPrefix = prefix;
        return false;
    }

    public Print(AnsiColor c, String name) {
        this.color = c;
        this.name = name;
        this.indent = (this.getPrefix()).replaceAll(".(?![^\\[]*\\])|\\[", " ");

    }

    private String getPrefix() {
        return  withLinePostfix("[" + name + "] says ");
    }

    private String withLinePostfix(String s) {
        return s + (emptyLine(minimalIndent - s.length()).replace(" ","─"));
    }

    public String say(String s) {
        return this.say(color, s);
    }

    private static String emptyLine(int length){
        if(length < 0)
            return "";
        char[] chars = new char[length];
        for (int i = 0; i < length; i++)
            chars[i] = ' ';
        return new String(chars);
    }

    private static String lineTop = "┬┄ ";
    private static String lineMiddle = "├┄ ";
    private static String lineBottom = "├┄ ";

    private static String formatString(String prefix, String line){
        //The indent line
        String indent = emptyLine(prefix.length()) + lineMiddle;
        line = line.replaceAll("\n","\n"+indent);

        //If it is the same prefix as last print, just print it with the indent.
        if(samePrefix(prefix))
            return indent + line;

        line = prefix + lineTop + line;

        //If it is a new line with no newline, just print it with the whole prefix
        return line;
    }


    public String say(AnsiColor color, String s) {
        if (!isMuted)
            return Print.echo(color, formatString(this.getPrefix(), s));
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
