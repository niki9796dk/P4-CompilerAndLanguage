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
        XMLElement e = (XMLElement)p.parse().value;

        // create XML output file
        XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter sw = outFactory.createXMLStreamWriter(new FileOutputStream(new File("tree/simple.xml")));

        // dump XML output to the file
        XMLElement.dump(lexer,sw,e); //,"expr","stmt");

        // transform the parse tree into an AST and a rendered HTML version
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer(new StreamSource(new File("tree/tree.xsl")));
        Source text = new StreamSource(new File("tree/simple.xml"));

        transformer.transform(text, new StreamResult(new File("tree/output.html")));
    }
}