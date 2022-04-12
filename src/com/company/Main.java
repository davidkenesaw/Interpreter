
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

package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Scan> SymbolTable = new ArrayList<Scan>();
    //array list used for storing tokens and lexemes
    public static BufferedReader reader=null;
    //reads to file

    //writes to file
    public static ArrayList<Integer> LineNumbers = new ArrayList<Integer>();
    //stores the line number
    public static void main(String[] args) throws IOException {

        String InputFile = "inputfile.txt";
        //source code file
        String OutputFile = "output.txt";
        //Symbol Table file



        //Scanner
        try{
            scan2(InputFile);
            //method used to scan input file to build the symbol table

        }catch (IOException e){
            e.printStackTrace();
            //display exception
        }
        finally {
            reader.close();

            //close the reader and writer
        }
        System.out.println("==================================Scanner=====================================");
        System.out.println();
        printTable();
        System.out.println();
        System.out.println("==================================Parser======================================");

        //Parser
        Parser parse = new Parser(SymbolTable);
        System.out.println();
        parse.function();
        parse.PARSETREE();
        System.out.println();
        System.out.println();

        System.out.println("==================================Interpreter==================================");
        System.out.println();
        //Interpreter
        Interpreter interpret = new Interpreter(SymbolTable);
        interpret.interpret(interpret.tokenInterpret);



    }
    public static void scan2(String input) throws IOException {
        reader = new BufferedReader(new FileReader(input));


        int LineNumber = 1;
        String line;
        while((line = reader.readLine()) != null){
            //System.out.println(line);
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
        for(int loop = 0; loop < SymbolTable.size(); loop++){
            System.out.println(SymbolTable.get(loop).toString());
        }
    }
    public static void AddToTable(String[] array, int lineNumber){
        for(int loop = 0; loop < array.length; loop++){
            SymbolTable.add(new Scan(array[loop],lineNumber));
        }
    }
}
