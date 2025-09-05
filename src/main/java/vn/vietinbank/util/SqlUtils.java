package vn.vietinbank.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SqlUtils {
    public static String placeholders(int n) {
        // n=3 -> "?,?,?"
        if (n <= 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(",");
            sb.append("?");
        }
        return sb.toString();
    }

    public static String dateToString(Timestamp ts, DateTimeFormatter df) {
        if (ts == null) return null;
        return ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(df);
    }
}
