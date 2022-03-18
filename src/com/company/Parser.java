

/*
What to finish:
-do the if function
-find out what a repeat function is
*/





package com.company;

import com.company.Scan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

    ArrayList<Queue> queueArray = new ArrayList<Queue>();
    Queue<Scan> tokenParse = new LinkedList<Scan>();

    public Parser(ArrayList<Scan> array) {
        AddtoQueue(array);
    }

    //BNF

    public boolean funcion(){
        //if the token is equal to function
        if(tokenParse.peek().getToken().equals("func_keyword")){
            tokenParse.remove();
            //if the next token is equal to id
            if(tokenParse.peek().getToken().equals("id")){
                tokenParse.remove();
                if(tokenParse.peek().getToken().equals("left_parenth_operator")){
                    tokenParse.remove();
                    if(tokenParse.peek().getToken().equals("right_parenth_operator")){
                        tokenParse.remove();
                        //check for body of function

                        if(statement() == false)return false;


                        //check for end keyword

                        if(tokenParse.peek().getToken().equals("end_keyword")){
                            return true;
                        }
                        else return false;



                    }else {
                        System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                        System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                        System.out.println("missing right parenthesis");
                        return false;
                    }
                }else {
                    System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                    System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                    System.out.println("missing left parenthesis");
                    return false;
                }
            }else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id");
                return false;
            }
        }else {
            System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
            System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
            System.out.println("missing function keyword");
            return false;
        }


    }

    //==============================Statement functions===================================================
    public boolean statement(){

        //put in loop
        for(int loop = 0; loop < tokenParse.size()-1; loop++) {
            if(tokenParse.peek().getToken().equals("while_keyword")){
                tokenParse.remove();
                whileStatement();
            }else if (tokenParse.peek().getToken().equals("id")) {
                tokenParse.remove();
                if (AssignmentStatement() == false) {
                    return false;
                }

            } else if (tokenParse.peek().getToken().equals("print_keyword")) {
                tokenParse.remove();
                if (printStatement() == false) {
                    return false;
                }
            } else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("statement not recognized");
                return false;
            }
        }
        return true;

    }
    public boolean printStatement(){
        if(tokenParse.peek().getToken().equals("left_parenth_operator")){
            tokenParse.remove();
            if(tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")){
                tokenParse.remove();
                if(tokenParse.peek().getToken().equals("right_parenth_operator")){
                    tokenParse.remove();
                    return true;
                }else {
                    System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                    System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                    System.out.println("missing right parenthesis");
                    return false;
                }
            }else {
                System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                System.out.println("missing id or integer");
                return false;
            }
        }else {
            System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
            System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
            System.out.println("missing left parenthesis");
            return false;
        }
    }
    public boolean AssignmentStatement(){
        if(tokenParse.peek().getToken().equals("assignment_operator")){
            tokenParse.remove();
            if(tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")){
                tokenParse.remove();
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
            if(tokenParse.peek().getToken().equals("do_keyword")){
                tokenParse.remove();
                //check body
                if(statement() == false)return false;
                //check end keyword
                if(tokenParse.peek().getToken().equals("end_keyword")){
                    tokenParse.remove();
                    return true;
                }
                else return false;
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
            tokenParse.remove();
            if (tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")) {
                tokenParse.remove();
                if (tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")) {
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

    //============================Helpers=================================================================
    public void AddtoQueue(ArrayList<Scan> array){
        for(int loop = 0; loop < array.size(); loop++){
            tokenParse.add(array.get(loop));
        }
    }
    public boolean relativeOps(String value){
        if(value.equals("le_operator"))return true;
        else if(value.equals("lt_operator"))return true;
        else if(value.equals("gt_operator"))return true;
        else if(value.equals("ge_operator"))return true;
        else if(value.equals("eq_operator"))return true;
        else if(value.equals("ne_operator"))return true;
        return false;
    }

    public Queue tostring() {
        return tokenParse;
    }
}
