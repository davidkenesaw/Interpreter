package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {

    Queue<Scan> tokenInterpret = new LinkedList<Scan>();

    //constructor
    public Interpreter(ArrayList<Scan> array) {
        AddtoQueue(array);
        tokenInterpret.remove();
        tokenInterpret.remove();

    }




//==============================Helper==================================
    public void AddtoQueue(ArrayList<Scan> array){
        //fills queue with tokens
        for(int loop = 0; loop < array.size(); loop++){
            tokenInterpret.add(array.get(loop));
        }
    }
    public void print(ArrayList<Scan> array) {
    }
}
