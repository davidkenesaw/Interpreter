

package com.company;

import org.python.util.PythonInterpreter;

public class ExpressionSolver {

    //solves mathematical expressions
    public static String solve(String value){
        PythonInterpreter py = new PythonInterpreter();
        return py.eval(value).toString();
    }

}
