import java.util.List;

public class MarkupVisitor extends MarkupParserBaseVisitor<String> {

    @Override
    public String visitFile(MarkupParser.FileContext ctx) {
        return visitElements(ctx.element());
    }

    @Override
    public String visitContent(MarkupParser.ContentContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitElement(MarkupParser.ElementContext ctx) {
        if (ctx.parent instanceof MarkupParser.FileContext) {
            if (ctx.content() != null) {
                return visitContent(ctx.content());
            } else if (ctx.tag() != null) {
                return visitTag(ctx.tag());
            }
        }

        throw new IllegalStateException("Unexpected element: " + ctx.getText());
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
                String author = null;
                if (ctx.attribute() != null && "author".equals(ctx.attribute().ID().getText())) {
                    final String attributeValue = ctx.attribute().STRING().getText();
                    // trim the leading and trailing quote
                    author = attributeValue.substring(1, attributeValue.length() - 1);
                }

                start = System.lineSeparator() + "> ";
                if (author != null) {
                    end = System.lineSeparator() + "> -" + author;
                }
                break;
        }

        text.append(start);
        text.append(visitElements(ctx.element()));
        text.append(end);

        return text.toString();
    }

    private String visitElements(List<MarkupParser.ElementContext> elements) {
        final StringBuilder text = new StringBuilder();
        for (MarkupParser.ElementContext element : elements) {
            if (element.content() != null) {
                text.append(visitContent(element.content()));
            } else if (element.tag() != null) {
                text.append(visitTag(element.tag()));
            }
        }
        return text.toString();
    }
}
