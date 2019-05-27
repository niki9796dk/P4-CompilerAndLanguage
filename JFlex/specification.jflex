package AutoGen;

import java.io.*;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java.util.*;

%%

%public
%class Lexer
%cup
%line
%char
%column
%implements sym

%{

    ComplexSymbolFactory symbolFactory;
    StringBuffer string = new StringBuffer();

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
	this(in);
	symbolFactory = sf;
    }

    private Symbol symbol(String name, int sym) {
      return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+yylength(),yychar+yylength()));
  }
  private Symbol symbol(String name, int sym, Object val) {
      Location left = new Location(yyline+1,yycolumn+1,yychar);
      Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }
  private Symbol symbol(String name, int sym, Object val,int buflength) {
      Location left = new Location(yyline+1,yycolumn+yylength()-buflength,yychar+yylength()-buflength);
      Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
      return symbolFactory.newSymbol(name, sym, left, right,val);
  }

  private void error(String message) {
      System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }

%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+1,yychar+1));
%eofval}


Ident =         [a-zA-Z_][a-zA-Z0-9_]*

NumLiteral =    {IntLiteral}+(\.{IntLiteral}+)

IntLiteral =    [0-9]+

New_line =      \r | \n | \r\n | \n\r

white_space =   {New_line} | [ \t\f]

Comment = {One_Line_Comment} | {Multi_Line_Comment}
One_Line_Comment = "//"[^\r\n]* {New_line}
Multi_Line_Comment = [/][*][^*]*[*]+([^*/][^*]*[*]+)*[/]

%%

<YYINITIAL>{

/* keywords */
"block"         {return symbol("block"        , BLOCK,          yytext()); }
"blueprint"     {return symbol("blueprint"    , BLUEPRINT,      yytext()); }
"build"         {return symbol("build"        , BUILD,          yytext()); }
"draw"          {return symbol("draw"         , DRAW,           yytext()); }
"source"        {return symbol("source"       , SOURCE,         yytext()); }
"operation"     {return symbol("operation"    , OPERATION,      yytext()); }
"channel:in"    {return symbol("channel:in"   , CHANNELIN,      yytext()); }
"channel:out"   {return symbol("channel:out"  , CHANNELOUT,     yytext()); }
"mychannel:in"  {return symbol("mychannel:in" , MYCHANNELIN,    yytext()); }
"mychannel:out" {return symbol("mychannel:out", MYCHANNELOUT,   yytext()); }
"procedure"     {return symbol("procedure"    , PROCEDURE,      yytext()); }
"this"          {return symbol("this"         , THIS,           yytext()); }
"size"          {return symbol("size"         , SIZE,           yytext()); }

/* Identifier names */
{Ident}         {return symbol("Identifier"   , ID,             yytext()); }

/* Literal numbers*/
 {NumLiteral}    { return symbol("Number", NUMCONST, new Double(Double.parseDouble(yytext()))); }
 {IntLiteral}    { return symbol("Number", INTCONST, new Integer(Integer.parseInt(yytext()))); }

/* separators */
","             { return symbol("," , COMMA, ","); }
";"             { return symbol(";" , SEMICOLON, ";"); }
"("             { return symbol("(" , LPAR, "("); }
")"             { return symbol(")" , RPAR, ")"); }
"{"             { return symbol("{" , LCURLY, "{"); }
"}"             { return symbol("}" , RCURLY, "}"); }
"["             { return symbol("[" , LSQR, "["); }
"]"             { return symbol("]" , RSQR, "]"); }
"="             { return symbol("=" , ASSIGN, "="); }
"->"            { return symbol("->", CONNECTION, "->"); }
"."             { return symbol("." , DOT, "."); }

{white_space}     { /* ignore */ }
{Comment}         { /* ignore */ }

}

/* error fallback */
[^]              {  /* throw new Error("Illegal character <"+ yytext()+">");*/
		    error("Illegal character <"+ yytext()+">");
                  }
