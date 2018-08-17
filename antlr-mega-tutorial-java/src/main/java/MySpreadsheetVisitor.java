import java.util.HashMap;
import java.util.Map;

public class MySpreadsheetVisitor extends SpreadsheetBaseVisitor<Double> {

    private Map<String, Double> data = new HashMap<>();

    public MySpreadsheetVisitor(Map<String, Double> data) {
        this.data.putAll(data);
    }

    @Override
    public Double visitNumericAtomExp(SpreadsheetParser.NumericAtomExpContext ctx) {
        System.out.println("visit number");
        return Double.parseDouble(ctx.NUMBER().getText());
    }

    @Override
    public Double visitPowerExp(SpreadsheetParser.PowerExpContext ctx) {
        System.out.println("visit power");
        double base = visit(ctx.expression(0));
        double exponent = visit(ctx.expression(1));
        return Math.pow(base, exponent);
    }

    @Override
    public Double visitMulDivExp(SpreadsheetParser.MulDivExpContext ctx) {
        System.out.println("visit muldiv");
        double a = visit(ctx.expression(0));
        double b = visit(ctx.expression(1));
        if (ctx.ASTERISK() != null) {
            return a * b;
        } else if (ctx.SLASH() != null) {
            return a / b;
        }
        throw new IllegalStateException("Unsupported operator: " + ctx.getText());
    }

    @Override
    public Double visitParenthesisExp(SpreadsheetParser.ParenthesisExpContext ctx) {
        System.out.println("visit parenthesis");
        return visit(ctx.expression());
    }

    @Override
    public Double visitIdAtomExp(SpreadsheetParser.IdAtomExpContext ctx) {
        System.out.println("visit IdAtom");
        return data.get(ctx.ID().getText());
    }

    @Override
    public Double visitAddSubExp(SpreadsheetParser.AddSubExpContext ctx) {
        System.out.println("visit add/sub");
        double a = visit(ctx.expression(0));
        double b = visit(ctx.expression(1));
        if (ctx.PLUS() != null) {
            return a + b;
        } else if (ctx.MINUS() != null) {
            return a - b;
        }
        throw new IllegalStateException("Unsupported operator: " + ctx.getText());
    }

    @Override
    public Double visitFunctionExp(SpreadsheetParser.FunctionExpContext ctx) {
        System.out.println("visit function");
        final String fnName = ctx.NAME().getText();
        final double argument = visit(ctx.expression());
        switch (fnName) {
            case "sqrt":
                return Math.sqrt(argument);
            case "log":
                return Math.log10(argument);
            default: throw new IllegalArgumentException("Unsuppored function: " + fnName);
        }
    }
}
