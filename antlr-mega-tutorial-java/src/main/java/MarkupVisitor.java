
public class MarkupVisitor extends MarkupParserBaseVisitor<String> {

    @Override
    public String visitFile(MarkupParser.FileContext ctx) {
        visitChildren(ctx);
        System.out.println("");
        return null;
    }

    @Override
    public String visitContent(MarkupParser.ContentContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitElement(MarkupParser.ElementContext ctx) {
        if (ctx.parent instanceof MarkupParser.FileContext) {
            if (ctx.content() != null) {
                System.out.print(visitContent(ctx.content()));
            } else if (ctx.tag() != null) {
                System.out.print(visitTag(ctx.tag()));
            }
        }

        return null;
    }

    @Override
    public String visitTag(MarkupParser.TagContext ctx) {
        final StringBuilder text = new StringBuilder();
        String start = "";
        String end = "";

        switch (ctx.ID(0).getText()) {
            case "b":
                start = end = "**";
                break;
            case "u":
                start = end = "*";
                break;
            case "quote":
                // TODO: check the attribute name is actually "author"?
                final String attributeValue = ctx.attribute().STRING().getText();
                // trim the leading and trailing quote
                final String author = attributeValue.substring(1, attributeValue.length() - 1);
                start = System.lineSeparator() + "> ";
                end = System.lineSeparator() + "> -" + author;
                break;
        }

        text.append(start);
        for (MarkupParser.ElementContext element : ctx.element()) {
            if (element.content() != null) {
                text.append(visitContent(element.content()));
            } else if (element.tag() != null) {
                text.append(visitTag(element.tag()));
            } 
        }
        text.append(end);

        return text.toString();
    }
}
