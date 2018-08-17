import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.HashMap;

public class SpreadsheetApp {

    public static void main(String[] args) {

        final MySpreadsheetVisitor visitor = new MySpreadsheetVisitor(new HashMap<String, Double>() {{
            put("A2", 1000.0);
        }});

        final CodePointCharStream arithmetics =
                CharStreams.fromString("15/(3+2) - 2*3^4 + sqrt(16) - log(10*A2)"); // 15/5 - 2*81 => 3 - 162 + 4 - 4=> -159

        final SpreadsheetLexer lexer = new SpreadsheetLexer(arithmetics);
        final SpreadsheetParser parser = new SpreadsheetParser(new CommonTokenStream(lexer));
        final SpreadsheetParser.ExpressionContext expression = parser.expression();

        System.out.println(visitor.visit(expression));
    }
}
