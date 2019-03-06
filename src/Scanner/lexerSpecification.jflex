/*
 *  $Id: studio3.jflex 18 2010-05-08 13:33:52Z cytron $
 *
 */

/* This section has code that goes at the top of the generated parser
 *    Things like package declaration and imports
 *    You should need nothing here for this exercise
 */

package Scanner.AutoGen;

%%

/* This section has declarations and options settings
 *
 * First, some handy declarations
 *   Be sure you understand the syntax before moving on
 */

%public

LineTerminator = \r | \n | \r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]		/* The blank after the bracket is significant */
Slash = [/]
Minus = [-]
NotSlashMinus = [^/-]

/* Now we tell JFlex we are not part of CUP, just standalone
 */

%standalone

/* The following code is emitted in the generated class
 *   You should use it when you find something interesting
 */
 
%{
   /*  Call me to say what you found */
   public void found(String str) {
      System.out.println();  System.out.flush();  /* flush std out */
      System.err.println("Found |" + str + "| from text -->" + yytext() + "<--");
   }
%}

%%

/* Finally, patterns of interest and what to
 *   upon finding them
 */


"hello"			{ found("greeting"); }
("0"|"1")+		{ found("binary integer"); }