import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UppercaseFilter {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.printf("%02d | %s%n", lineNumber++, line.toUpperCase());
            }
        }
    }
}
