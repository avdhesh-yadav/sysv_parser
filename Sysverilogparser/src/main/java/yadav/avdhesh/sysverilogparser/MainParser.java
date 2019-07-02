/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yadav.avdhesh.sysverilogparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Avdhesh
 */
public class MainParser {

    boolean isDebug = false;

    public String compile(String input) {

        boolean isBuildTree = true;
        StringBuilder ERROR = new StringBuilder();
        SysverilogParser parser;
        SysverilogLexer lexer;
        try {
            lexer = new SysverilogLexer(new ANTLRInputStream(input.trim()));
            // Create tokens stream
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            //Create parser...
            parser = new SysverilogParser(tokens);
            parser.removeErrorListeners();

            ParseTree tree = parser.source_text();
            parser.setBuildParseTree(true);
            Trees.inspect(tree, parser);

        } catch (Exception ex) {
            if (isDebug) {
                ERROR.append(ex.getMessage());
                ERROR.append(" Please fix Above compilation error.");

            } else {
                System.out.println(ex.getMessage());
                System.out.println(" Please fix Above compilation error.");
            }

        }
        return ERROR.toString();

    }

    public static String readDataFromFile(File file) {
        String input = null;
        StringBuilder inputBuf = new StringBuilder();
        FileInputStream file_in;
        try {
            file_in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(file_in));
            String line;
            while ((line = br.readLine()) != null) {
                inputBuf.append(line);
                inputBuf.append("\n");
            }
            input = inputBuf.toString();
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage() + " ocurred in issInputFile..!!");
        }
        // input = xmlInputToCProgram(input);
        return input;

    }

    public static void main(String args[]) {
        try {
            // String filePath = "C:\\Users\\Agnisys42\\Desktop\\UnitTesting\\dvCon\\iss-text\\iss_txt.py";

            String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\yadav\\junit\\simple\\svinterface_at_top.sv";
            File inputFile = new File(filePath);
            System.out.println("Start Processing  " + new Date().getTime());

            System.out.println(new MainParser().compile(readDataFromFile(inputFile)));
            //System.out.println("end processing " + new Date().getTime());

        } catch (Exception e) {
            System.out.println("Exception in main " + e.getMessage());
        }
    }

}
