package com.company;

import com.sun.jdi.Value;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {

    ArrayList<Scan> WhileBody = new ArrayList<Scan>();

    Queue<Scan> tokenInterpret = new LinkedList<Scan>();
    ArrayList<VariableAssignment> variables = new ArrayList<VariableAssignment>();

    //constructor
    public Interpreter(ArrayList<Scan> array) {
        AddtoQueue(array);
        tokenInterpret.remove();
        tokenInterpret.remove();
    }
    //==============================Statement==================================

    public void interpret(){
        while (!tokenInterpret.isEmpty()) {
            if (tokenInterpret.peek().getToken().equals("id")) {
                AssignmentStatement();
            }else if(tokenInterpret.peek().getToken().equals("print_keyword")){
                PrintStatement();
            }
            else if(tokenInterpret.peek().getLexeme().equals("while_keyword")){
                WhileStatement();
            }
            else if(tokenInterpret.peek().getToken().equals("end_keyword")){
                tokenInterpret.remove();
                //if broken, might need to remove
                return;
            }
        }
    }

    public void WhileStatement(){
        tokenInterpret.remove();

        String repOperator = tokenInterpret.peek().getLexeme();
        tokenInterpret.remove();

        String variable = tokenInterpret.peek().getLexeme();
        tokenInterpret.remove();

        String Limit = tokenInterpret.peek().getLexeme();
        tokenInterpret.remove();

        tokenInterpret.remove();

        //while loop
        int variableInt = Integer.parseInt(getValueOfVariable(variable));
        int limit = Integer.parseInt(Limit);


    }

    public void AssignmentStatement(){
        String variable = tokenInterpret.peek().getLexeme();
        tokenInterpret.remove();
        tokenInterpret.remove();

        String expression = ArithmeticExpression();
        //System.out.println(variable+"="+expression + " = " + ExpressionSolver.solve(expression));

        if(variableContains(variable)){
            System.out.println("got it");
            variables.set(variableGetIndex(variable),new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }else{
            variables.add(new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }

    }

    public void PrintStatement(){
        //System.out.println(tokenInterpret.peek().getLexeme());
        tokenInterpret.remove();
        String expression = ArithmeticExpression();
        //System.out.println(expression);
        System.out.println(ExpressionSolver.solve(expression));
    }

    //==============================Expression==================================

    public String ArithmeticExpression(){
        String expression = "";
        int CurrentLine = tokenInterpret.peek().getLineNumber();
        while (CurrentLine==tokenInterpret.peek().getLineNumber()){
            if(tokenInterpret.peek().getToken().equals("id")){
                expression += getValueOfVariable(tokenInterpret.peek().getLexeme());
            }
            else{
                expression += tokenInterpret.peek().getLexeme();
            }
            tokenInterpret.remove();
        }
        return expression;
    }
    public boolean booleanExpression(int left, int right, String repOp){
        if(repOp.equals("<")){
            if(left < right)return true;
            else return false;
        }
        return false;
    }
    //==============================Helper==================================

    public void AddtoQueue(ArrayList<Scan> array){
        //fills queue with tokens
        for(int loop = 0; loop < array.size(); loop++){
            tokenInterpret.add(array.get(loop));
        }
    }
    public boolean variableContains(String Value){
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(Value)){
                return true;
            }
        }
        return false;
    }
    public int variableGetIndex(String Value){
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(Value)){
                return loop;
            }
        }
        return -1;
    }
    public String getValueOfVariable(String variable){
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(variable)){
                return variables.get(loop).getValue();
            }
        }
        return "couldnt find it";
    }
    public void print() {
        while(!tokenInterpret.isEmpty()){
            System.out.println(tokenInterpret.peek().toString());
            tokenInterpret.remove();
        }
    }
    public void printVariables(){
        for(int loop = 0; loop < variables.size(); loop++){
            System.out.println(variables.get(loop).toString());
        }
    }
}
