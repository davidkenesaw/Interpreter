package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String text = "";
        ArrayList<Scan> Array = new ArrayList<Scan>();
        BufferedReader reader=null;
        BufferedWriter writer=null;
        try{
            reader = new BufferedReader(new FileReader("inputfile.txt"));writer = new BufferedWriter(new FileWriter("output.txt"));
            String line ;

            while((line =reader.readLine()) != null){

                if(!line.substring(0,2).equals("//")){
                    text += line + " ";
                }
                else {
                    System.out.println("you");
                }

            }
            //System.out.println(text);
            String[] tokens = text.split("\\s+");

            for(int loop = 0; loop < tokens.length;loop++){
                Array.add(new Scan(tokens[loop]));
                //System.out.println();
                writer.write(Array.get(loop).toString()+"\n");
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            reader.close();
            writer.close();
        }

    }

}
