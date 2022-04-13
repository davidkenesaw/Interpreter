
/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P1 Scanner
 */

//and

/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P2 Parser
 */

//and

/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P3 Interpreter
 */

package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {
    //array list used for storing tokens and lexemes
    public static ArrayList<Scan> SymbolTable = new ArrayList<Scan>();
    //reads to file
    public static BufferedReader reader=null;
    //stores the line number
    public static ArrayList<Integer> LineNumbers = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {
        //source code file
        String InputFile = "inputfile.txt";

        //Scanner
        try{
            //method used to scan input file to build the symbol table
            scan2(InputFile);
        }catch (IOException e){
            //display exception
            e.printStackTrace();
        }
        finally {
            //close the reader
            reader.close();
        }

        //parse and interpret the symbol table
        Parser parse = new Parser(SymbolTable);
        Interpreter interpret = new Interpreter(SymbolTable);

        System.out.println("==================================Scanner=====================================\n");
        printTable();
        System.out.println("\n==================================Parser======================================\n");
        if(parse.function() == true){
            parse.PARSETREE();
            System.out.println("\n\n==================================Interpreter==================================");
            interpret.interpret(interpret.tokenInterpret);
        }


    }
    public static void scan2(String input) throws IOException {
        //scans lexemes into symbol table
        reader = new BufferedReader(new FileReader(input));

        int LineNumber = 1;
        String line;
        while((line = reader.readLine()) != null){
            if(!line.isBlank()) {
                line = line.trim();
                String[] CurrentLine = line.split("[()\\s]+");
                AddToTable(CurrentLine, LineNumber);
                LineNumber++;
            }else{
                LineNumber++;
            }
        }
    }
    public static void printTable(){
        //print symbol table
        for(int loop = 0; loop < SymbolTable.size(); loop++){
            System.out.println(SymbolTable.get(loop).toString());
        }
    }
    public static void AddToTable(String[] array, int lineNumber){
        //add to symbol table
        for(int loop = 0; loop < array.length; loop++){
            SymbolTable.add(new Scan(array[loop],lineNumber));
        }
    }
}
