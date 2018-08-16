import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

public class MarkupParserTest {

      private TestErrorListener errorListener;


    private MarkupParser setupParser(String text) {
        final MarkupLexer markupLexer = new MarkupLexer(CharStreams.fromString(text));
        final CommonTokenStream tokens = new CommonTokenStream(markupLexer);
        final MarkupParser markupParser = new MarkupParser(tokens);

        // setup error listeners
        markupParser.removeErrorListeners();
        errorListener = new TestErrorListener();
        markupParser.addErrorListener(errorListener);

        return markupParser;
    }

    private String transformCode (String input) {
        final MarkupParser parser = setupParser(input);
        final MarkupParser.FileContext fileContext = parser.file();
        return new MarkupVisitor().visitFile(fileContext);
    }

    @Test
    public void testQuoteWithAuthor() {
        assertEquals("Markdown generated from BBCode different from the expected value.",
                "I would like to *underline ** that ** *. Let's quote: \n" +
                        "> You're wrong! \n" +
                        "> -john",
                transformCode("I would like to [u]underline [b] that [/b] [/u]. Let's quote: [quote author=\"john\"]You're wrong! [/quote]"));
    }
    
    @Test
    public void testQuoteWithoutAuthor() {
        assertEquals("Markdown generated from BBCode different from the expected value.",
                "I would like to *underline ** that ** *. Let's quote: \n" +
                        "> You're wrong! ",
                transformCode("I would like to [u]underline [b] that [/b] [/u]. Let's quote: [quote]You're wrong! [/quote]"));
    }
}
