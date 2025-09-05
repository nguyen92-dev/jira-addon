package vn.vietinbank.rest.impl;

import com.atlassian.annotations.security.XsrfProtectionExcluded;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.util.UserUtil;
import vn.vietinbank.dao.IssueDAO;
import vn.vietinbank.dao.impl.IssueDAOImpl;
import vn.vietinbank.model.IssueInfo;
import vn.vietinbank.rest.IssueResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Path("/issues")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IssueResourceImpl implements IssueResource {

    private final IssueDAO dao = new IssueDAOImpl();
    @Override
    @GET
    @XsrfProtectionExcluded // GET không cần XSRF
    public Response getIssues(List<String> groups, int limit, int offset) {
        try {
            Optional<List<String>> assignees = Optional.empty();
            if (groups != null && !groups.isEmpty()) {
                UserUtil userUtil = ComponentAccessor.getUserUtil();
                // Lấy users trong group
                List<String> usernames = userUtil.getAllUsersInGroupNames(groups)
                        .stream()
                        .map(u -> {
                            // Jira 7.x: getUsername() còn tồn tại; fallback getName()
                            try {
                                return u.getUsername();
                            } catch (NoSuchMethodError e) {
                                return u.getName();
                            }
                        })
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
                assignees = Optional.of(usernames);
            }

            // Chặn limit quá lớn
            int safeLimit = Math.max(1, Math.min(500, limit));
            int safeOffset = Math.max(0, offset);

            List<IssueInfo> rows = dao.findIssues(assignees, safeLimit, safeOffset);
            Map<String,Object> payload = new HashMap<>();
            payload.put("count", rows.size());
            payload.put("items", rows);

            return Response.ok(payload).build();

        } catch (SQLException e) {
            return Response.serverError()
                    .entity(Collections.singletonMap("error", "SQL error: " + e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(Collections.singletonMap("error", e.toString()))
                    .build();
        }
    }
}
