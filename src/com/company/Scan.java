package com.company;


public class Scan {

    String Lexeme;
    String token;


    public Scan(String lexeme) {
        Lexeme = lexeme;
        token = isLexeme(lexeme);
    }

    public String isLexeme(String value){
        switch (value) {
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
        return "Lexeme='" + Lexeme + '\'' +
                ",      token='" + token + '\'';
    }
}
