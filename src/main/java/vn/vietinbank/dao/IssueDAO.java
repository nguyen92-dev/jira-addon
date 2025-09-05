package vn.vietinbank.dao;

import vn.vietinbank.model.IssueInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IssueDAO {

    List<IssueInfo> findIssues(Optional<List<String>> assigneesOpt, int limit, int offset) throws SQLException;
}
