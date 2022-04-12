
/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P3 Interpreter
 */

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

    public void interpret(Queue<Scan> original){
        while (!original.isEmpty()) {
            if (original.peek().getToken().equals("id")) {
                AssignmentStatement(original);
            }else if(original.peek().getToken().equals("print_keyword")){
                PrintStatement(original);
            }
            else if(original.peek().getToken().equals("while_keyword")){
                WhileStatement(original);
            }else if(original.peek().getToken().equals("if_keyword")){
                IfStatement(original);
            }
            else if(original.peek().getToken().equals("end_keyword")){
                original.remove();
            }
        }
    }
    public void IfStatement(Queue<Scan> original){
        Queue<Scan> IfBody = new LinkedList<Scan>();
        Queue<Scan> ElseBody = new LinkedList<Scan>();


        original.remove();
        String repOperator = original.peek().getLexeme();
        original.remove();
        String variable = original.peek().getLexeme();
        original.remove();
        String Limit = original.peek().getLexeme();
        original.remove();

        original.remove();
        //System.out.println(repOperator+" "+variable+" "+Limit);

        while(!original.peek().getToken().equals("else_keyword")){
            IfBody.add(original.peek());
            original.remove();
        }
        IfBody.add(new Scan("end",tokenInterpret.peek().getLineNumber()));
        tokenInterpret.remove();
        while(!original.peek().getToken().equals("end_keyword")){
            ElseBody.add(original.peek());
            original.remove();
        }
        ElseBody.add(new Scan("end",tokenInterpret.peek().getLineNumber()));
        //printQueue(IfBody);
        //System.out.println();
        //printQueue(ElseBody);

        if(booleanExpression(getValueOfVariable(variable),Limit,repOperator)){
            interpret(IfBody);
        }else{
            interpret(ElseBody);
        }


    }

    public void WhileStatement(Queue<Scan> original){

        original.remove();
        String repOperator = original.peek().getLexeme();
        original.remove();
        String variable = original.peek().getLexeme();
        original.remove();
        String Limit = original.peek().getLexeme();
        original.remove();

        original.remove();

        WhileBlock(variable,Limit,repOperator);

    }
    public void WhileBlock(String left, String right, String rep_op){
        Queue<Scan> temp = new LinkedList<Scan>();

        while(!tokenInterpret.peek().getToken().equals("end_keyword")){
            WhileBody.add(tokenInterpret.peek());
            tokenInterpret.remove();
        }
        WhileBody.add(tokenInterpret.peek());
        //printArrayList(WhileBody);

        AddtoSpecificQueue(WhileBody,temp);
        //printQueue(temp);


        //interpret(temp);

        while(booleanExpression(getValueOfVariable(left),right,rep_op)){

            interpret(temp);
            AddtoSpecificQueue(WhileBody,temp);

        }

        WhileBody.clear();
    }

    public void AssignmentStatement(Queue<Scan> original){

        String variable = original.peek().getLexeme();
        original.remove();
        original.remove();



        String expression = ArithmeticExpression(original);
        //System.out.println(variable+"="+expression + " = " + ExpressionSolver.solve(expression));

        if(variableContains(variable)){
            variables.set(variableGetIndex(variable),new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }else{

            variables.add(new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }
    }

    public void PrintStatement(Queue<Scan> original){
        original.remove();
        String expression = ArithmeticExpression(original);
        System.out.println(ExpressionSolver.solve(expression));
    }

    //==============================Expression==================================

    public String ArithmeticExpression(Queue<Scan> original){
        String expression = "";
        int CurrentLine = original.peek().getLineNumber();
        while (CurrentLine==original.peek().getLineNumber()){
            if(original.peek().getToken().equals("id")){
                expression += getValueOfVariable(original.peek().getLexeme());
            }
            else{
                expression += original.peek().getLexeme();
            }
            original.remove();
        }
        return expression;
    }
    public boolean booleanExpression(String left, String right, String repOp){
        int IntLeft = Integer.parseInt(left);
        int IntRight = Integer.parseInt(right);
        if(repOp.equals("<")){
            if(IntLeft < IntRight)return true;
            else return false;
        }else if(repOp.equals("<=")){
            if(IntLeft <= IntRight)return true;
            else return false;
        }else if(repOp.equals(">")){
            if(IntLeft > IntRight)return true;
            else return false;
        }else if(repOp.equals(">=")){
            if(IntLeft >= IntRight)return true;
            else return false;
        }else if(repOp.equals("==")){
            if(IntLeft == IntRight)return true;
            else return false;
        }else if(repOp.equals("~=")){
            if(IntLeft != IntRight)return true;
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
    public void AddtoSpecificQueue(ArrayList<Scan> array, Queue<Scan> temp){
        //fills queue with tokens
        for(int loop = 0; loop < array.size(); loop++){
            temp.add(array.get(loop));
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
    public void printQueue(Queue<Scan> temp){
        while(!temp.isEmpty()){
            System.out.println(temp.peek().toString());
            temp.remove();
        }
    }
    public void printArrayList(ArrayList<Scan> temp){
        for(int loop = 0; loop < temp.size(); loop++){
            System.out.println(temp.get(loop).toString());
        }
    }
}
