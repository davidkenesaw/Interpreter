

 /*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P2 Parser
 */



package com.company;

import com.company.Scan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    //output of parse tree
    ArrayList<String> ParseTree = new ArrayList<String>();
    //queue filled with tokens
    Queue<Scan> tokenParse = new LinkedList<Scan>();

    //constructor
    public Parser(ArrayList<Scan> array) {
        AddtoQueue(array);
    }

    //BNF
    public boolean function(){

        //if the token is equal to function
        if(tokenParse.peek().getToken().equals("func_keyword")){
            tokenParse.remove();
            //if the next token is equal to id
            if(tokenParse.peek().getToken().equals("id")){
                tokenParse.remove();



                        //check for body of function

                        ParseTree.add("<program> -> function id ( )");
                        if(statement() == false)return false;
                        else return true;

                        //check for end keyword




            }else {
                //if the next token is not equal to left Parenthesis
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id");
                return false;
            }
        }else {
            //if the next token is not equal to function
            System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
            System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
            System.out.println("missing function keyword");
            return false;
        }


    }

    //==============================Statement functions===================================================
    public boolean statement(){

        //put in loop
        while(!tokenParse.isEmpty()) {
            if(tokenParse.peek().getToken().equals("if_keyword")) {
                //if statement is an if statement
                tokenParse.remove();
                ParseTree.add("if");
                if(ifStatement()==false) {
                    return false;
                }
            }
            if(tokenParse.peek().getToken().equals("while_keyword")){
                //if statement is a while statement
                tokenParse.remove();
                ParseTree.add("while");
                if(whileStatement()==false){
                    return false;
                }
            }else if (tokenParse.peek().getToken().equals("id")) {
                //if statement is an assignment statement
                tokenParse.remove();
                ParseTree.add("id");
                if (AssignmentStatement() == false) {
                    return false;
                }

            } else if (tokenParse.peek().getToken().equals("print_keyword")) {
                //if statement is a print statement
                tokenParse.remove();
                ParseTree.add("print");
                if (printStatement() == false) {
                    return false;
                }
            }else if (tokenParse.peek().getToken().equals("else_keyword")) {
                System.out.println(tokenParse.peek().getLexeme());
                tokenParse.remove();
                ParseTree.add("else");
                if(ElseStatement()==false){
                    return false;
                }
                return true;
            }
            else if (tokenParse.peek().getToken().equals("end_keyword")) {
                tokenParse.remove();
                ParseTree.add("end");
                return true;
            }
            else{
                //if statement is not in the grammar
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("statement not recognized");
                return false;
            }
        }
        return true;
    }

    public boolean ifStatement(){
        if(boolExpression() == true){
            //checks for rest of if statement
            if(tokenParse.peek().getToken().equals("then_keyword")){

                //checks to see if there is a then keyword
                tokenParse.remove();
                ParseTree.add("then");

                //check body
                if(statement() == false)return false;
                return true;

            }else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing then keyword");
                return false;
            }
        }else {
            return false;
        }
    }
    public boolean ElseStatement(){
        if(statement()==false)return false;
        return true;
    }

    public boolean printStatement(){

            if(ArithmeticExpression(tokenParse.peek().getLineNumber())){
                //if the next token is equal to id | if the next token is equal to integer

                    return true;

            }else {
                System.out.println("problem");
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id or integer");
                return false;
            }

    }
    public boolean AssignmentStatement(){
        if(tokenParse.peek().getToken().equals("assignment_operator")){
            //checks to see if = sign is present
            ParseTree.add("<assignment_operator>");
            tokenParse.remove();
            if(ArithmeticExpression(tokenParse.peek().getLineNumber())){
                //checks to see if id is present | checks to see if integer is present
                //ParseTree.add("<"+tokenParse.peek().getToken()+">");
                //tokenParse.remove();
                return true;
            }else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id or integer");
                return false;
            }
        }else {
            System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
            System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
            System.out.println("missing assignment operator");
            return false;
        }
    }


    public boolean whileStatement(){
        if(boolExpression() == true){
            //checks for rest of while statement
            if(tokenParse.peek().getToken().equals("do_keyword")){
                //checks to see if there is a do keyword
                tokenParse.remove();
                ParseTree.add("do");
                //check body
                if(statement() == false)return false;
                return true;
            }else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing do keyword");
                return false;
            }
        }else {
            return false;
        }
    }

    //================================expressions========================================================================
    public boolean boolExpression(){

        if(relativeOps(tokenParse.peek().getToken()) == true) {
            //checks to see if token is a relative operator
            ParseTree.add(tokenParse.peek().getLexeme());
            tokenParse.remove();
            if (tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")) {
                //checks for an id | checks for an integer
                ParseTree.add("<"+tokenParse.peek().getToken()+">");
                tokenParse.remove();//ArithmeticExpression(tokenParse.peek().getLineNumber())
                if (tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")) {
                    //checks for an id | checks for an integer
                    ParseTree.add("<"+tokenParse.peek().getToken()+">");
                    tokenParse.remove();
                    return true;
                } else {
                    System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                    System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                    System.out.println("missing id or integer");
                    return false;
                }
            } else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id or integer");
                return false;
            }
        }else {
            System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
            System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
            System.out.println("missing relative operator");
            return false;
        }
    }
    public boolean ArithmeticExpression(int LineNumber){
        int temp = LineNumber;
        while (temp == tokenParse.peek().getLineNumber()){
            //System.out.println(tokenParse.peek().getLexeme() + tokenParse.peek().getLineNumber());

            if(tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")||ArithmeticOps(tokenParse.peek().getToken())==true){

                ParseTree.add("<"+tokenParse.peek().getToken()+">");
                tokenParse.remove();

                //return true;
            }else{
                return false;
            }

        }
        return true;
    }
    //============================Helpers=================================================================
    public void AddtoQueue(ArrayList<Scan> array){
        //fills queue with tokens
        for(int loop = 0; loop < array.size(); loop++){
            tokenParse.add(array.get(loop));
        }
    }
    public boolean relativeOps(String value){
        //checks if value is a relative operator
        if(value.equals("le_operator"))return true;
        else if(value.equals("lt_operator"))return true;
        else if(value.equals("gt_operator"))return true;
        else if(value.equals("ge_operator"))return true;
        else if(value.equals("eq_operator"))return true;
        else if(value.equals("ne_operator"))return true;
        return false;
    }
    public boolean ArithmeticOps(String value){
        //checks if value is a relative operator
        if(value.equals("add_ARITHoperator"))return true;
        else if(value.equals("sub_ARITHoperator"))return true;
        else if(value.equals("mul_ARITHoperator"))return true;
        else if(value.equals("div_ARITHoperator"))return true;
        return false;
    }
    public void PARSETREE(){
        //prints parse tree
        for(int loop = 0; loop < ParseTree.size();loop++){
            System.out.print(ParseTree.get(loop) + " ");
        }
    }

}
