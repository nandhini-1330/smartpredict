import java.io.*;
import java.net.*;
import java.util.*;

public class main {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server running at http://localhost:8080");

        while (true) {
            Socket socket = server.accept();
            handleRequest(socket);
        }
    }

    private static void handleRequest(Socket socket) throws Exception {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(out, true);

        String requestLine = in.readLine();
        if (requestLine == null) return;

        String path = requestLine.split(" ")[1];

        // Serve HTML
        if (path.equals("/")) {

            File file = new File("index.html");
            FileInputStream fis = new FileInputStream(file);

            writer.print("HTTP/1.1 200 OK\r\n");
            writer.print("Content-Type: text/html\r\n");
            writer.print("Content-Length: " + file.length() + "\r\n");
            writer.print("\r\n");
            writer.flush();

            byte[] buffer = new byte[1024];
            int bytes;

            while ((bytes = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }

            out.flush();
            fis.close();
        }

        // Handle detection
        else if (path.startsWith("/detect")) {

            Map<String, String> params = parseQuery(path);

            double speed = Double.parseDouble(params.getOrDefault("speed", "0"));
            double temp = Double.parseDouble(params.getOrDefault("temp", "0"));
            double battery = Double.parseDouble(params.getOrDefault("battery", "0"));
            double tire = Double.parseDouble(params.getOrDefault("tire", "0"));
            boolean brake = Boolean.parseBoolean(params.getOrDefault("brake", "true"));

            String response = detectFault(speed, temp, battery, tire, brake);

            writer.print("HTTP/1.1 200 OK\r\n");
            writer.print("Content-Type: text/plain\r\n");
            writer.print("\r\n");
            writer.print(response);
            writer.flush();
        }

        else {
            writer.print("HTTP/1.1 404 Not Found\r\n\r\n");
            writer.print("Cannot GET " + path);
            writer.flush();
        }

        socket.close();
    }

    private static Map<String, String> parseQuery(String path) {

        Map<String, String> map = new HashMap<>();

        if (!path.contains("?")) return map;

        String query = path.split("\\?")[1];
        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }

        return map;
    }

    private static String detectFault(double speed, double temp,
                                      double battery, double tire,
                                      boolean brake) {

        StringBuilder alerts = new StringBuilder();

        if (temp > 100)
            alerts.append("Engine Overheat - CRITICAL,");

        if (battery < 20)
            alerts.append("Battery Low - HIGH,");

        if (!brake)
            alerts.append("Brake Failure - CRITICAL,");

        if (tire < 30)
            alerts.append("Low Tire Pressure - HIGH,");

        if (alerts.length() == 0)
            return "No Fault Detected";

        return alerts.substring(0, alerts.length() - 1);
    }
}