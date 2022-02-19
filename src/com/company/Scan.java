package com.company;


public class Scan {

    String Lexeme;
    String token;
    int LineNumber;

    public Scan(String lexeme, int LineNumber) {
        Lexeme = lexeme;
        token = isLexeme(lexeme);
        this.LineNumber = LineNumber;
    }



    public String isLexeme(String value){
        String linenumber = Integer.toString(LineNumber);
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
                if(isInteger(value)) {
                    token = "literal_integer";
                }else if(Character.isLetter(value.charAt(0)) && value.contains("()")){
                    token = "meth_name";
                }else if(Character.isLetter(value.charAt(0))){
                    token = "id";
                }
                else {
                    token = "error";
                }

        }

        return token;

    }
    public static boolean isInteger(String value) {
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
        String linenumber = Integer.toString(LineNumber);
        return "Lexeme: " + Lexeme + " Token: " + token + " LineNumber: " + linenumber;
    }
}
