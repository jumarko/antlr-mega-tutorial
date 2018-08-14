package midlevel;

import beginner.ChatBaseListener;
import beginner.ChatParser;

public class MyChatListener extends ChatBaseListener {

    private final StringBuilder responseBuilder = new StringBuilder();

    public String response() {
        return responseBuilder.toString();
    }

    @Override
    public void enterName(ChatParser.NameContext ctx) {
        responseBuilder
                .append("<strong>");
    }

    @Override
    public void exitName(ChatParser.NameContext ctx) {
        responseBuilder
                .append(ctx.WORD().getText())
                .append("<strong>");
    }

    @Override
    public void exitEmoticon(ChatParser.EmoticonContext ctx) {
        final String emoticon = ctx.getText();

        if (":-)".equals(emoticon) || ":)".equals(emoticon)) {
            responseBuilder.append("?");
        } else if (":-(".equals(emoticon) || ":(".equals(emoticon)) {
            responseBuilder.append("?");
        }
    }

    @Override
    public void enterCommand(ChatParser.CommandContext ctx) {
        if (ctx.SAYS() != null) {
            responseBuilder.append(ctx.SAYS().getText() + ":<p>");
        } else if (ctx.SHOUTS() != null) {
            responseBuilder.append(ctx.getText() + ":<p style=\"text-transform:uppercase\">");
        }
    }

    @Override
    public void exitLine(ChatParser.LineContext ctx) {
        responseBuilder.append("</p>");
    }
}
