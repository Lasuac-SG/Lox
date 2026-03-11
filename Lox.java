package lox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lox {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) { // if command is "jlox a.lox b.lox" then error
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) { // if command is "jlox a.lox" then runFile()
            runFile(args[0]); // args[0] is "a.lox"
        } else {
            runPrompt(); // if command is "jlox" then runPrompt()
        }
    }

    // for command "jlox a.lox" , path = "a.lox"
    private static void runFile(String path) throws IOException { 
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    // for you want a more intimate conversation with your interpreter (command is "jlox")
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

// =============================================================================
// Scanning
// =============================================================================


    // run! input source, through Scanner it will be divided into several tokens
    private static void run(String source) {
        Scanner scanner = new Scanner(source); // Scanner here is what we will build next, not the java's scanner
        List<Token> tokens = scanner.scanTokens(); // token, the minimal unit

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    //Error handling
    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
    }

}