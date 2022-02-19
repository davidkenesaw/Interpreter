package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Scan> Array = new ArrayList<Scan>();
    public static BufferedReader reader=null;
    public static BufferedWriter writer=null;

    public static ArrayList<Integer> Array2 = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {

        String InputFile = "inputfile.txt";
        String OutputFile = "output.txt";

        try{
            scan(InputFile,OutputFile);

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            writer.close();
        }

    }
    public static void scan(String input, String output) throws IOException {

        String text = "";

        reader = new BufferedReader(new FileReader(input));writer = new BufferedWriter(new FileWriter(output));
        String line ;

        int count = 1;
        while((line =reader.readLine()) != null){
            if(!line.contains("//")&& !line.isBlank()){
                text += line + " ";
                String[] tempCounter = line.split(" ");
                for(int loop = 0;loop<tempCounter.length;loop++){
                    if(!line.isBlank()){
                        Array2.add(count);
                    }
                }
            }
            count++;
        }



        String[] tokens = text.split("\\s+");

        for(int loop = 0; loop < tokens.length;loop++){
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


                String[] tempArray = temp.split("\\s+");

                for(int loop1 = 0; loop1 < tempArray.length;loop1++){
                        Array.add(new Scan(tempArray[loop1], Array2.get(loop)));
                }
            }else{
                Array.add(new Scan(tokens[loop],Array2.get(loop)));
            }


        }
        for(int loop = 0;loop< Array.size();loop++){
            writer.write(Array.get(loop).toString()+"\n");
        }
    }

}
