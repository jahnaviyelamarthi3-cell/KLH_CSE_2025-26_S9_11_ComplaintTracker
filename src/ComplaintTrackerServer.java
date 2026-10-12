
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ComplaintTrackerServer {
    private static final Path CORPUS = Paths.get("corpus");

    public static void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/corpus", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                send(exchange, 204, "");
                return;
            }

            try {
                List<String> files = new ArrayList<>();
                if (Files.isDirectory(CORPUS)) {
                    try (Stream<Path> paths = Files.list(CORPUS)) {
                        paths.filter(Files::isRegularFile)
                             .filter(p -> p.getFileName().toString().endsWith(".txt"))
                             .sorted()
                             .forEach(p -> files.add(p.getFileName().toString()));
                    }
                }

                StringBuilder json = new StringBuilder();
                json.append("{\"count\":").append(files.size()).append(",\"files\":[");
                for (int i = 0; i < files.size(); i++) {
                    if (i > 0) json.append(",");
                    String name = files.get(i);
                    String content = readFile(CORPUS.resolve(name));
                    json.append("{\"name\":\"").append(escape(name))
                        .append("\",\"preview\":\"")
                        .append(escape(content.length() > 300
                            ? content.substring(0, 300) : content))
                        .append("\"}");
                }
                json.append("]}");
                send(exchange, 200, json.toString());
            } catch (Exception e) {
                send(exchange, 500, "{\"error\":\"Could not read corpus folder\"}");
            }
        });

        server.createContext("/api/search", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                send(exchange, 204, "");
                return;
            }

            try {
                String query = queryParameter(exchange.getRequestURI().getRawQuery(), "q");
                if (query.trim().isEmpty()) {
                    send(exchange, 400, "{\"error\":\"Search pattern is required\"}");
                    return;
                }

                StringBuilder json = new StringBuilder("{\"matches\":[");
                boolean first = true;

                if (Files.isDirectory(CORPUS)) {
                    try (Stream<Path> paths = Files.list(CORPUS)) {
                        for (Path path : (Iterable<Path>) paths
                                .filter(Files::isRegularFile)
                                .filter(p -> p.getFileName().toString().endsWith(".txt"))
                                .sorted()::iterator) {
                            String content = readFile(path);
                            if (ComplaintTrackerKMP.containsPattern(
                                    content.toLowerCase(), query.toLowerCase())) {
                                if (!first) json.append(",");
                                first = false;
                                json.append("{\"name\":\"")
                                    .append(escape(path.getFileName().toString()))
                                    .append("\",\"preview\":\"")
                                    .append(escape(content.length() > 500
                                        ? content.substring(0, 500) : content))
                                    .append("\"}");
                            }
                        }
                    }
                }

                json.append("]}");
                send(exchange, 200, json.toString());
            } catch (Exception e) {
                send(exchange, 500, "{\"error\":\"Search failed\"}");
            }
        });

        server.createContext("/api/algorithm", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                send(exchange, 204, "");
                return;
            }

            send(exchange, 501,
                "{\"error\":\"Algorithm API integration needs callable methods in the existing DP, Network Flow, and Set Cover classes. The console menu methods cannot safely be called as web requests without adapting their input/output.\"}");
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Complaint Tracker web API started at http://localhost:8080");
        System.out.println("Keep this terminal open while using the website.");
    }

    private static String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String queryParameter(String query, String key) throws Exception {
        if (query == null) return "";
        for (String part : query.split("&")) {
            String[] pair = part.split("=", 2);
            if (pair.length == 2 &&
                URLDecoder.decode(pair[0], "UTF-8").equals(key)) {
                return URLDecoder.decode(pair[1], "UTF-8");
            }
        }
        return "";
    }

    private static void addCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\r", "\\r")
                    .replace("\n", "\\n")
                    .replace("\t", "\\t");
    }

    private static void send(HttpExchange exchange, int status, String body)
            throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        if (status == 204) {
            exchange.sendResponseHeaders(status, -1);
            exchange.close();
            return;
        }
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }
}
