
/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P3 Interpreter
 */

package com.company;

import org.python.util.PythonInterpreter;

public class ExpressionSolver {

    public static String solve(String value){
        PythonInterpreter py = new PythonInterpreter();
        return py.eval(value).toString();
    }

}
