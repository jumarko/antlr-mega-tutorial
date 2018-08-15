import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;


public class ChatParserTest {

    private TestErrorListener errorListener;

    public static class TestErrorListener extends BaseErrorListener {

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


    private ChatParser setupParser(String text) {
        final ChatLexer chatLexer = new ChatLexer(CharStreams.fromString(text));
        final CommonTokenStream tokens = new CommonTokenStream(chatLexer);
        final ChatParser chatParser = new ChatParser(tokens);

        // setup error listeners
        chatParser.removeErrorListeners();
        errorListener = new TestErrorListener();
        chatParser.addErrorListener(errorListener);
        
        return chatParser;
    }

    private void checkNameRule(String input) {
        final ChatParser parser = setupParser(input);
        final ChatParser.NameContext tree = parser.name();
        ParseTreeWalker.DEFAULT.walk(new MyChatListener(), tree);
    }

    @Test
    public void testValidName() {
        checkNameRule("John ");
        assertNull("Expected no errors, but found offendingSymbol: " + errorListener.getOffendingSymbol(),
                errorListener.getOffendingSymbol());
    }

    @Test
    public void testInvalidName() {
        checkNameRule("Joh-");
        assertEquals("Expected error, but found no errors",
                "-", ((CommonToken) errorListener.getOffendingSymbol()).getText());
    }
}
