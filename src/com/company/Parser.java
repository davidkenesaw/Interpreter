

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
    //this is a comment
    public boolean function(){
        //if the token is equal to function
        if(tokenParse.peek().getToken().equals("func_keyword")){
            tokenParse.remove();
            //if the next token is equal to id
            if(tokenParse.peek().getToken().equals("id")){
                tokenParse.remove();
                //if the next token is equal to left parenthesis
                if(tokenParse.peek().getToken().equals("left_parenth_operator")){
                    tokenParse.remove();
                    //if the next token is equal to right parenthesis
                    if(tokenParse.peek().getToken().equals("right_parenth_operator")){
                        tokenParse.remove();
                        //check for body of function

                        ParseTree.add("<program> -> function id ( )");
                        if(statement() == false)return false;

                        //check for end keyword

                        if(tokenParse.peek().getToken().equals("end_keyword")){
                            ParseTree.add("end");
                            PARSETREE();
                            return true;
                        }
                        else return false;
                    }else {
                        //if the next token is not equal to right parenthesis
                        System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                        System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                        System.out.println("missing right parenthesis");
                        return false;
                    }
                }else {
                    //if the next token is not equal to id
                    System.out.println("error on line number: " + tokenParse.peek().getLineNumber());
                    System.out.println("lexeme is: " + tokenParse.peek().getLexeme());
                    System.out.println("missing left parenthesis");
                    return false;
                }
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
        for(int loop = 0; loop < tokenParse.size()-1; loop++) {

            if(tokenParse.peek().getToken().equals("while_keyword")){
                //if statement is a while statement
                tokenParse.remove();
                ParseTree.add("while");
                whileStatement();
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
            } else {
                //if statement is not in the grammar
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
            //if the next token is equal to left parenthesis
            tokenParse.remove();
            ParseTree.add("(");
            if(tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")){
                //if the next token is equal to id | if the next token is equal to integer
                ParseTree.add("<"+tokenParse.peek().getToken()+">");
                tokenParse.remove();
                if(tokenParse.peek().getToken().equals("right_parenth_operator")){
                    //if the next token is equal to right parenthesis
                    tokenParse.remove();
                    ParseTree.add(")");
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
            //checks to see if = sign is present
            ParseTree.add("<assignment_operator>");
            tokenParse.remove();
            if(tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")){
                //checks to see if id is present | checks to see if integer is present
                ParseTree.add("<"+tokenParse.peek().getToken()+">");
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
            //checks for rest of while statement
            if(tokenParse.peek().getToken().equals("do_keyword")){
                //checks to see if there is a do keyword
                tokenParse.remove();
                ParseTree.add("do");
                //check body
                if(statement() == false)return false;
                //check end keyword
                if(tokenParse.peek().getToken().equals("end_keyword")){
                    //checks for end keyword
                    tokenParse.remove();
                    ParseTree.add("end");
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
            //checks to see if token is a relative operator
            ParseTree.add(tokenParse.peek().getLexeme());
            tokenParse.remove();
            if (tokenParse.peek().getToken().equals("id") || tokenParse.peek().getToken().equals("literal_integer")) {
                //checks for an id | checks for an integer
                ParseTree.add("<"+tokenParse.peek().getToken()+">");
                tokenParse.remove();
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
    public void PARSETREE(){
        //prints parse tree
        for(int loop = 0; loop < ParseTree.size();loop++){
            System.out.print(ParseTree.get(loop) + " ");
        }
    }

}
