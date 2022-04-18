

package com.company;

public class VariableAssignment {
    String Variable;
    String Value;

    public VariableAssignment(String variable, String value) {
        Variable = variable;
        Value = value;
    }

    public VariableAssignment(String variable) {
        Variable = variable;
    }

    public String getVariable() {
        return Variable;
    }

    public void setVariable(String variable) {
        Variable = variable;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "VariableAssignment{" +
                "Variable='" + Variable + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
