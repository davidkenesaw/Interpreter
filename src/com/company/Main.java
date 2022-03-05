
/*
 * Class:       CS 4308 Section
 * Term:        ____________
 * Name:       <Your Name>
 * Instructor:   Sharon Perry
 * Project:     Deliverable P1 Scanner
 */



package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Scan> SymbolTable = new ArrayList<Scan>();
    //array list used for storing tokens and lexemes
    public static BufferedReader reader=null;
    //reads to file
    public static BufferedWriter writer=null;
    //writes to file
    public static ArrayList<Integer> LineNumbers = new ArrayList<Integer>();
    //stores the line number
    public static void main(String[] args) throws IOException {

        String InputFile = "inputfile.txt";
        //source code file
        String OutputFile = "output.txt";
        //Symbol Table file

        try{
            scan(InputFile,OutputFile);
            //method used to scan input file to build the symbol table

        }catch (IOException e){
            e.printStackTrace();
            //display exception
        }
        finally {
            reader.close();
            writer.close();
            //close the reader and writer
        }

    }

    public static void scan(String input, String output) throws IOException {
        //method used for scanning the input file and building symbol table
        String text = "";

        reader = new BufferedReader(new FileReader(input));
        writer = new BufferedWriter(new FileWriter(output));

        String line;

        int count = 1;//temp variable for line number
        while((line =reader.readLine()) != null){//while line is not equal to null
            if(!line.contains("//")&& !line.isBlank()){//if the line contains "//" skip the line
                text += line + " ";//contains all text in the file
                String[] tempCounter = line.split(" ");
                for(int loop = 0;loop<tempCounter.length;loop++){
                    if(!line.isBlank()){
                        LineNumbers.add(count);
                    }
                }
            }
            count++;
        }
        String[] tokens = text.split("\\s+");//tokenize the text from the source code

        for(int loop = 0; loop < tokens.length;loop++){//check if lexeme contains parenthesis
            if(tokens[loop].contains("(") && tokens[loop].contains(")")){
                String temp = "";
                for(int loop1 = 0; loop1 < tokens[loop].length(); loop1++){
                    if(tokens[loop].charAt(loop1) == '('){
                        temp += " ( ";
                    }else if(tokens[loop].charAt(loop1) == ')'){
                        temp += " ) ";
                    }else{
                        temp += tokens[loop].charAt(loop1);
                    }
                }


                String[] tempArray = temp.split("\\s+");//tokenizes the lexeme further

                for(int loop1 = 0; loop1 < tempArray.length;loop1++){
                    //adds lexemes to the symbol table array list
                    SymbolTable.add(new Scan(tempArray[loop1], LineNumbers.get(loop)));
                }
            }else{
                //adds lexeme to the symbol table array list
                SymbolTable.add(new Scan(tokens[loop],LineNumbers.get(loop)));
            }


        }
        for(int loop = 0;loop< SymbolTable.size();loop++){
            //adds all lexemes and tokens to output file
            writer.write(SymbolTable.get(loop).toString()+"\n");
        }
    }

}
