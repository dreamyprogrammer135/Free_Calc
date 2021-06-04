package com.dreamyprogrammer.freecalc;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class SimpleCalculator {

    private StringBuilder expression = new StringBuilder();
    private Boolean lastCharacterOperatin = true;
    private Boolean stateSeparator = false;
    private String expressionFinal;

    public SimpleCalculator() {
        this.expression.setLength(0);
        this.expression.append("0");
    }

    public SimpleCalculator(StringBuilder expression, Boolean lastCharacterOperatin, Boolean stateSeparator) {
        this.expression.append(expression);
        this.lastCharacterOperatin = lastCharacterOperatin;
        this.stateSeparator = stateSeparator;
        getEquallyn();
    }

    public String getExpression() {
        return expression.toString();
    }

    public void setExpression(StringBuilder expression) {
        this.expression = expression;
    }

    public Boolean getLastCharacterOperatin() {
        return lastCharacterOperatin;
    }

    public void setLastCharacterOperatin(Boolean lastCharacterOperatin) {
        this.lastCharacterOperatin = lastCharacterOperatin;
    }

    public Boolean getStateSeparator() {
        return stateSeparator;
    }

    public void setStateSeparator(Boolean stateSeparator) {
        this.stateSeparator = stateSeparator;
    }

    public void onButtonNumPressed(int buttonId) {
        if (expression.substring(0).equals("0")) {
            expression.deleteCharAt(0);
        }
        switch (buttonId) {
            case R.id.button_1:
                expression.append("1");
                break;
            case R.id.button_2:
                expression.append("2");
                break;
            case R.id.button_3:
                expression.append("3");
                break;
            case R.id.button_4:
                expression.append("4");
                break;
            case R.id.button_5:
                expression.append("5");
                break;
            case R.id.button_6:
                expression.append("6");
                break;
            case R.id.button_7:
                expression.append("7");
                break;
            case R.id.button_8:
                expression.append("8");
                break;
            case R.id.button_9:
                expression.append("9");
                break;
            case R.id.button_0:
                if (expression.length() != 0) {
                    expression.append("0");
                }
                break;
            case R.id.button_parenthesis_right:
                expression.append("(");
                break;
            case R.id.button_parenthesis_left:
                expression.append(")");
                break;
            case R.id.button_clean_entry:
                if (expression.length() > 1) {
                    expression.delete(expression.length() - 1, expression.length());
                } else if (expression.length() > 0) {
                    expression.replace(expression.length() - 1, expression.length(), "0");
                }
                break;
            case R.id.button_clean:
                expression.delete(0, expression.length());
                expression.append("0");
                stateSeparator = false;
                break;
        }
        lastCharacterOperatin = false;
    }

    public void onButtonActionPressed(int buttonId) {
        if (!lastCharacterOperatin) {
            lastCharacterOperatin = true;
            switch (buttonId) {
                case R.id.button_plus:
                    expression.append("+");
                    stateSeparator = false;
                    break;
                case R.id.button_minus:
                    expression.append("-");
                    stateSeparator = false;
                    break;
                case R.id.button_multiplication:
                    expression.append("*");
                    stateSeparator = false;
                    break;
                case R.id.button_division:
                    expression.append("/");
                    stateSeparator = false;
                    break;
                case R.id.button_percent:
                    if (expression.substring(expression.length()) != "%") {
                        expression.append("%");
                        stateSeparator = false;
                        lastCharacterOperatin = false;
                    }
                    break;
                case R.id.button_equallyn:
                    lastCharacterOperatin = false;
                    break;
                case R.id.button_separator:
                    if (!stateSeparator) {
                        expression.append(".");
                        stateSeparator = true;
                    }
                    break;
            }
        }
    }

    public String getEquallyn() {
        if (expression.length() == 0) {
            expression.append("0");
        }
        expressionFinal = expression.toString();
        expressionFinal = expressionFinal.replaceAll("%", "/100");
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scriptable = rhino.initStandardObjects();
            return rhino.evaluateString(scriptable, expressionFinal, "javascript", 1, null).toString();

        } catch (Exception e) {
            return "Error";
        }
    }

    public String getText() {
        return expression.toString();

    }

    ;
}
