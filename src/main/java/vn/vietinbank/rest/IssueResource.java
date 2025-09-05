package vn.vietinbank.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IssueResource {
    Response getIssues(@QueryParam("groups") List<String> groups,
                              @QueryParam("limit") @DefaultValue("100") int limit,
                              @QueryParam("offset") @DefaultValue("0") int offset);
}
