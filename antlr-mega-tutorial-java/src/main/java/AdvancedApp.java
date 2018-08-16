import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class AdvancedApp {

    public static void main(String[] args) {
        final CodePointCharStream inputStream =
                CharStreams.fromString("I would like to [b][i]empahsize[/i][/b] this and [u]underline[b]that[/b][/u]");
        final MarkupLexer markupLexer = new MarkupLexer(inputStream);
        final MarkupParser markupParser = new MarkupParser(new CommonTokenStream(markupLexer));
        final MarkupParser.FileContext fileContext = markupParser.file();
        final MarkupVisitor markupVisitor = new MarkupVisitor();
        markupVisitor.visit(fileContext);
    }
}
