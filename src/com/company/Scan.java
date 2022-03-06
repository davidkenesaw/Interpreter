
/*
 * Class:       CS 4308 Section 3
 * Term:        Spring
 * Name:       David VanAsselberg
 * Instructor:   Sharon Perry
 * Project:     Deliverable P1 Scanner
 */

package com.company;

public class Scan {
    //lexeme fields
    String Lexeme;
    String token;
    int LineNumber;

    public Scan(String lexeme, int LineNumber) {
        //constructor for a lexeme
        Lexeme = lexeme;
        token = isLexeme(lexeme);
        //lexeme is checked in this function to see if it exists in the language
        this.LineNumber = LineNumber;
    }

    public String isLexeme(String value){
        //method checks to see if lexeme exists in language
        switch (value) {
            case "+=":
                token = "pe_operator";
                break;
            case "-=":
                token = "me_operator";
                break;
            case "(":
                token = "left_parenth_operator";
                break;
            case ")":
                token = "right_parenth_operator";
                break;
            case "=":
                token = "assignment_operator";
                break;
            case "<=":
                token = "le_operator";
                break;
            case "<":
                token = "lt_operator";
                break;
            case ">":
                token = "gt_operator";
                break;
            case ">=":
                token = "ge_operator";
                break;
            case "==":
                token = "eq_operator";
                break;
            case "~=":
                token = "ne_operator";
                break;
            case "+":
                token = "add_operator";
                break;
            case "-":
                token = "sub_operator";
                break;
            case "*":
                token = "mul_operator";
                break;
            case "/":
                token = "div_operator";
                break;

            case "function":
                token = "func_keyword";
                break;
            case "print":
                token = "print_keyword";
                break;
            case "end":
                token = "end_keyword";
                break;
            case "while":
                token = "while_keyword";
                break;
            case "if":
                token = "if_keyword";
                break;
            case "else":
                token = "else_keyword";
                break;
            case "then":
                token = "then_keyword";
                break;
            case "do":
                token = "do_keyword";
                break;

            default:
                if(isInteger(value)) {//checks if lexeme is a number
                    token = "literal_integer";
                }else if(Character.isLetter(value.charAt(0))){
                    token = "id";
                }
                else {//lexeme not recognized
                    token = "error";
                }

        }

        return token;

    }
    public static boolean isInteger(String value) {
        //method to check if variable is an integer
        try {
            Integer.parseInt(value);
        } catch(NumberFormatException exception) {
            return false;
        } catch(NullPointerException exception) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //returns a string to display lexeme, token, line number
        String linenumber = Integer.toString(LineNumber);
        return "Lexeme: " + Lexeme + ", Token: " + token + ", LineNumber: " + linenumber;
    }
}
