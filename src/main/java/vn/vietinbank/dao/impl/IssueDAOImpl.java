package vn.vietinbank.dao.impl;

import org.ofbiz.core.entity.ConnectionFactory;
import org.ofbiz.core.entity.DelegatorInterface;
import org.ofbiz.core.entity.GenericEntityException;
import vn.vietinbank.dao.IssueDAO;
import vn.vietinbank.model.IssueInfo;
import vn.vietinbank.util.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.atlassian.jira.component.pico.ComponentManager;
import java.util.List;
import java.util.Optional;

public class IssueDAOImpl implements IssueDAO {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public List<IssueInfo> findIssues(Optional<List<String>> assigneesOpt, int limit, int offset) throws SQLException {
        String baseSql =
                "SELECT KEY, SUMMARY, URD, EPIC_LINK, IT_MAIN_DEPT, IT_RELATION_DEPT, ASSIGNEE, " +
                        "DEV_LEADER, STATUS, DEV_START_DATE, DEV_MEMBER, DUEDATE, PENDING_DATE, " +
                        "MANHOURS_ACTUAL, MANHOURS_ESTIMATE " +
                        "FROM JIRA_ISSUE_CLOUD ";

        List<Object> params = new ArrayList<>();
        String where = "";

        if (assigneesOpt.isPresent()) {
            List<String> assignees = assigneesOpt.get();
            if (!assignees.isEmpty()) {
                String inClause = SqlUtils.placeholders(assignees.size());
                where = "WHERE ASSIGNEE IN (" + inClause + ") ";
                params.addAll(assignees);
            } else {
                // group trống ⇒ trả về không có dòng
                where = "WHERE 1=1 ";
            }
        }

        String orderLimit = "ORDER BY DUEDATE NULLS LAST, KEY ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        params.add(offset);
        params.add(limit);

        String sql = baseSql + where + orderLimit;

        ComponentManager componentManager = ComponentManager.getInstance();
        DelegatorInterface delegator = componentManager.getComponentInstanceOfType(DelegatorInterface.class);

        try (Connection c = ConnectionFactory.getConnection(delegator.getGroupHelperName("default"));
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
                else ps.setString(i + 1, String.valueOf(p));
            }

            List<IssueInfo> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IssueInfo r = new IssueInfo();
                    r.setKey(rs.getString("KEY"));
                    r.setSummary(rs.getString("SUMMARY"));
                    r.setUrd(rs.getString("URD"));
                    r.setEpicLink(rs.getString("EPIC_LINK"));
                    r.setItMainDep(rs.getString("IT_MAIN_DEPT"));
                    r.setItRelationDev(rs.getString("IT_RELATION_DEPT"));
                    r.setAssignee(rs.getString("ASSIGNEE"));
                    r.setDevLeader(rs.getString("DEV_LEADER"));
                    r.setStatus(rs.getString("STATUS"));
                    r.setDevStartDate(SqlUtils.dateToString(rs.getTimestamp("DEV_START_DATE"), DF));
                    r.setDevMember(rs.getString("DEV_MEMBER"));
                    r.setDueDate(SqlUtils.dateToString(rs.getTimestamp("DUEDATE"), DF));
                    r.setPendingDate(SqlUtils.dateToString(rs.getTimestamp("PENDING_DATE"), DF));
                    r.setManHoursActual(rs.getString("MANHOURS_ACTUAL"));
                    r.setManHoursEstimate(rs.getString("MANHOURS_ESTIMATE"));
                    list.add(r);
                }
            }
            return list;
        } catch (GenericEntityException e) {
            throw new RuntimeException(e);
        }
    }
}
