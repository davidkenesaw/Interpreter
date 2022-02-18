package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Scan> Array = new ArrayList<Scan>();
    public static BufferedReader reader=null;
    public static BufferedWriter writer=null;
    public static void main(String[] args) throws IOException {
        String text = "";


        try{
            reader = new BufferedReader(new FileReader("inputfile.txt"));writer = new BufferedWriter(new FileWriter("output.txt"));
            String line ;

            while((line =reader.readLine()) != null){

                if(!line.substring(0,2).equals("//")){
                    text += line + " ";
                }

            }

            scan(text);

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            writer.close();
        }

    }
    public static void scan(String text) throws IOException {
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
                        Array.add(new Scan(tempArray[loop1]));
                }
            }else{
                Array.add(new Scan(tokens[loop]));
            }


        }
        for(int loop = 0;loop< Array.size();loop++){
            writer.write(Array.get(loop).toString()+"\n");
        }
    }

}
