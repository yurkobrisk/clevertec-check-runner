package by.korziuk.check_app.dao.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScriptUtils {

    public ScriptUtils() {
    }

    private static String readScript(String scriptName) {
        String line = "";
        try (InputStream stream = ScriptUtils.class.getClassLoader().getResourceAsStream(scriptName)) {
            line = new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .collect(Collectors.joining());

        } catch (IOException e) {
            System.out.println("Script file not found ...");
            throw new RuntimeException(e);
        }
        return line;
    }

    private static List<String> parseScript(String script) {
        return Arrays.stream(script.split(";"))
                .map(state -> state.concat(";"))
                .collect(Collectors.toList());
    }

    public static void executeSqlScript(Connection connection, String scriptName) {
        try (Statement stmt = connection.createStatement()) {
            for (String statement : parseScript(readScript(scriptName))) {
                stmt.execute(statement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
