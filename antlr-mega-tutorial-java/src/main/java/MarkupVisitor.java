
public class MarkupVisitor extends MarkupParserBaseVisitor<String> {

    @Override
    public String visitFile(MarkupParser.FileContext ctx) {
        visitChildren(ctx);
        System.out.println("");
        return null;
    }

    @Override
    public String visitContent(MarkupParser.ContentContext ctx) {
        System.out.println(ctx.TEXT().getText());

        return visitChildren(ctx);
    }
}
