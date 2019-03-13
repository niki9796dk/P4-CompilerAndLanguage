package AutoGen;

import java.io.*;
import java_cup.runtime.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java_cup.runtime.XMLElement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.XMLElement;

public class MainParse {
    public static void main(String args[]) throws Exception {

        parseFile(args[0]);

    }

    public static void parseFile(String path) throws Exception{
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new Lexer(new BufferedReader(new FileReader(path)),csf));

        // start parsing
        Parser p = new Parser(lexer,csf);
        //System.out.println("Parser runs: ");
        Parser.newScope();
        p.parse();

    }
}