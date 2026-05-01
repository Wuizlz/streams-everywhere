import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchURLDemo {
    private static final Pattern CHARSET_PATTERN =
            Pattern.compile("charset=([^;]+)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws Exception {
        String url = args.length > 0 ? args[0] : "https://example.com";

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<byte[]> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception exception) {
            System.err.println("Request failed: " + exception.getClass().getSimpleName());
            System.err.println("This usually means the host is unavailable, DNS failed, or network access is blocked.");
            return;
        }

        System.out.println("URL: " + url);
        System.out.println("Status: " + response.statusCode());
        System.out.println();

        printSelectedHeaders(response.headers().map());
        System.out.println();

        Charset charset = resolveCharset(response.headers().firstValue("Content-Type"));
        String body = new String(response.body(), charset);

        System.out.println("Decoded charset: " + charset.name());
        System.out.println("Body preview:");
        System.out.println(body.substring(0, Math.min(body.length(), 400)));
    }

    private static void printSelectedHeaders(Map<String, List<String>> headers) {
        System.out.println("Headers:");
        printHeader(headers, "content-type");
        printHeader(headers, "location");
        printHeader(headers, "www-authenticate");
        printHeader(headers, "content-length");
    }

    private static void printHeader(Map<String, List<String>> headers, String headerName) {
        List<String> values = headers.get(headerName);
        if (values == null || values.isEmpty()) {
            System.out.println(headerName + ": <not present>");
            return;
        }

        System.out.println(headerName + ": " + String.join(", ", values));
    }

    private static Charset resolveCharset(Optional<String> contentTypeHeader) {
        if (contentTypeHeader.isEmpty()) {
            return StandardCharsets.UTF_8;
        }

        Matcher matcher = CHARSET_PATTERN.matcher(contentTypeHeader.get().toLowerCase(Locale.ROOT));
        if (!matcher.find()) {
            return StandardCharsets.UTF_8;
        }

        try {
            return Charset.forName(matcher.group(1).trim());
        } catch (Exception ignored) {
            return StandardCharsets.UTF_8;
        }
    }
}
