

package com.company;

import com.sun.jdi.Value;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {
    //storage for body of a while loop
    ArrayList<Scan> WhileBody = new ArrayList<Scan>();
    //queue that contains all lexemes and tokens
    Queue<Scan> tokenInterpret = new LinkedList<Scan>();
    //storage for all variables that are assigned a value
    ArrayList<VariableAssignment> variables = new ArrayList<VariableAssignment>();

    //constructor
    public Interpreter(ArrayList<Scan> array) {
        AddtoQueue(array,tokenInterpret);
        tokenInterpret.remove();
        tokenInterpret.remove();
    }
    //==============================Statement==================================

    //Driver function that checks what the current statement is
    public void interpret(Queue<Scan> original){
        //loop until queue is empty
        while (!original.isEmpty()) {
            if (original.peek().getToken().equals("id")) {
                //if Statement is an assignment statement
                AssignmentStatement(original);
            }else if(original.peek().getToken().equals("print_keyword")){
                //if statement is a print statement
                PrintStatement(original);
            }
            else if(original.peek().getToken().equals("while_keyword")){
                //if statement is a While statement
                WhileStatement(original);
            }else if(original.peek().getToken().equals("if_keyword")){
                //if statement is an if else statement
                IfStatement(original);
            }
            else if(original.peek().getToken().equals("end_keyword")){
                //if end Keyword, remove it
                original.remove();
            }
        }
    }
    //function used to handle if statements
    public void IfStatement(Queue<Scan> original){
        //store if body
        Queue<Scan> IfBody = new LinkedList<Scan>();
        //store else body
        Queue<Scan> ElseBody = new LinkedList<Scan>();

        //store values needed for boolean expression
        original.remove();
        String repOperator = original.peek().getLexeme();
        original.remove();
        String variable = original.peek().getLexeme();
        original.remove();
        String Limit = original.peek().getLexeme();
        original.remove();

        original.remove();

        //store the if and else body statements
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


        if(booleanExpression(getValueOfVariable(variable),Limit,repOperator)){
            //if boolean expression returns true
            interpret(IfBody);
        }else{
            //if boolean expression returns false
            interpret(ElseBody);
        }
    }

    //first function used to deal with while statements
    public void WhileStatement(Queue<Scan> original){
        //store values needed for boolean expression
        original.remove();
        String repOperator = original.peek().getLexeme();
        original.remove();
        String variable = original.peek().getLexeme();
        original.remove();
        String Limit = original.peek().getLexeme();
        original.remove();

        original.remove();

        //call second function needed for while statements
        WhileBlock(variable,Limit,repOperator);

    }
    //second function used for dealing with while statements
    public void WhileBlock(String left, String right, String rep_op){
        //temporarily store body of while loop
        Queue<Scan> temp = new LinkedList<Scan>();

        //temporarily store body of while loop
        while(!tokenInterpret.peek().getToken().equals("end_keyword")){
            WhileBody.add(tokenInterpret.peek());
            tokenInterpret.remove();
        }
        WhileBody.add(tokenInterpret.peek());

        AddtoQueue(WhileBody,temp);

        //execute while loop
        while(booleanExpression(getValueOfVariable(left),right,rep_op)){

            interpret(temp);
            AddtoQueue(WhileBody,temp);

        }
        //clear while loop array list
        WhileBody.clear();
    }

    //function for dealing with assignment statements
    public void AssignmentStatement(Queue<Scan> original){
        //variable name
        String variable = original.peek().getLexeme();
        original.remove();
        original.remove();


        //everything after equal sign
        String expression = ArithmeticExpression(original);

        if(variableContains(variable)){
            //if variable already exists
            variables.set(variableGetIndex(variable),new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }else{
            //if variable hasn't been initialized yet
            variables.add(new VariableAssignment(variable,ExpressionSolver.solve(expression)));
        }
    }

    //function for dealing with print statements
    public void PrintStatement(Queue<Scan> original){
        original.remove();
        String expression = ArithmeticExpression(original);
        System.out.println(ExpressionSolver.solve(expression));
    }

    //==============================Expression==================================

    //function for dealing with Arithmetic expressions
    public String ArithmeticExpression(Queue<Scan> original){
        //stores expression in string
        String expression = "";
        int CurrentLine = original.peek().getLineNumber();
        while (CurrentLine==original.peek().getLineNumber()){
            if(original.peek().getToken().equals("id")){
                //if lexeme is a variable
                expression += getValueOfVariable(original.peek().getLexeme());
            }
            else{
                //if lexeme is an integer
                expression += original.peek().getLexeme();
            }
            original.remove();
        }
        return expression;
    }
    public boolean booleanExpression(String left, String right, String repOp){
        //left side of equal statement
        int IntLeft = Integer.parseInt(left);
        //right side of equal statement
        int IntRight = Integer.parseInt(right);
        if(repOp.equals("<")){
            //less than
            return (IntLeft < IntRight) ?true :false;
        }else if(repOp.equals("<=")){
            //less than or equal to
            return (IntLeft <= IntRight) ?true :false;
        }else if(repOp.equals(">")){
            //greater than
            return (IntLeft > IntRight) ?true :false;
        }else if(repOp.equals(">=")){
            //greater than or equal to
            return (IntLeft >= IntRight) ?true :false;
        }else if(repOp.equals("==")){
            //equal to
            return (IntLeft == IntRight) ?true :false;
        }else if(repOp.equals("~=")){
            //not equal to
            return (IntLeft != IntRight) ?true :false;
        }
        return false;
    }
    //==============================Helper==================================


    public void AddtoQueue(ArrayList<Scan> array, Queue<Scan> temp){
        //fills a specific queue with tokens
        for(int loop = 0; loop < array.size(); loop++){
            temp.add(array.get(loop));
        }
    }
    public boolean variableContains(String Value){
        //checks if variable array list contains the string variable
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(Value)){
                return true;
            }
        }
        return false;
    }
    public int variableGetIndex(String Value){
        //get index of variable of in array list
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(Value)){
                return loop;
            }
        }
        return -1;
    }
    public String getValueOfVariable(String variable){
        //get the value of the variable
        for(int loop = 0; loop < variables.size(); loop++){
            if(variables.get(loop).getVariable().equals(variable)){
                return variables.get(loop).getValue();
            }
        }
        return "couldnt find it";
    }
    public void printQueue(Queue<Scan> temp){
        //prints a queue
        while(!temp.isEmpty()){
            System.out.println(temp.peek().toString());
            temp.remove();
        }
    }
    public void printArrayList(ArrayList<Scan> temp){
        //print array list
        for(int loop = 0; loop < temp.size(); loop++){
            System.out.println(temp.get(loop).toString());
        }
    }
}
