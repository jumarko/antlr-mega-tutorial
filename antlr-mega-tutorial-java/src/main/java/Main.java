import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        final String input = "john SHOUTS: hello @michael /pink/this will work/ :) \n";

        final ChatLexer chatLexer = new ChatLexer(CharStreams.fromString(input));
        final CommonTokenStream tokens = new CommonTokenStream(chatLexer);

        final ChatParser chatParser = new ChatParser(tokens);
        final ChatParser.ChatContext tree = chatParser.chat();

        final MyChatListener listener = new MyChatListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);

        System.out.println(listener.response());
        
    }
}
