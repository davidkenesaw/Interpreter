package com.company;

import org.python.util.PythonInterpreter;

import java.util.Stack;


public class ExpressionSolver {


    public static String solve(String value){
        PythonInterpreter py = new PythonInterpreter();
        return py.eval(value).toString();
    }


}
