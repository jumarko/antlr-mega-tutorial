import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class TestErrorListener extends BaseErrorListener {

        private Object offendingSymbol;

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            this.offendingSymbol = offendingSymbol;
            System.out.println("SYNTAX ERROR: " + offendingSymbol);
        }

        public Object getOffendingSymbol() {
            return offendingSymbol;
        }
}
