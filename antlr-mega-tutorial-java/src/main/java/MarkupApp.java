import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class MarkupApp {

    public static void main(String[] args) {
        final CodePointCharStream noQuote =
                CharStreams.fromString("I would like to [b][i]empahsize[/i][/b] this and [u]underline[b]that[/b][/u]");
        // another example
        final CodePointCharStream quoteWithAuthor =
                CharStreams.fromString("I would like to [u]underline [b] that [/b] [/u]. Let's quote: [quote author=\"john\"]You're wrong! [/quote]");
        final CodePointCharStream quoteWithoutAuthor =
                CharStreams.fromString("I would like to [u]underline [b] that [/b] [/u]. Let's quote: [quote]You're wrong! [/quote]");
        final MarkupLexer markupLexer = new MarkupLexer(quoteWithAuthor);
        final MarkupParser markupParser = new MarkupParser(new CommonTokenStream(markupLexer));
        final MarkupParser.FileContext fileContext = markupParser.file();
        final MarkupVisitor markupVisitor = new MarkupVisitor();
        System.out.println(markupVisitor.visit(fileContext));
    }
}
