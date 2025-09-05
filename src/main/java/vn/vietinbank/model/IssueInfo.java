package vn.vietinbank.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IssueInfo {

    @XmlElement
    private String key;
    @XmlElement
    private String summary;
    @XmlElement
    private String urd;
    @XmlElement
    private String epicLink;
    @XmlElement
    private String itMainDep;
    @XmlElement
    private String itRelationDev;
    @XmlElement
    private String assignee;
    @XmlElement
    private String devLeader;
    @XmlElement
    private String status;
    @XmlElement
    private String devStartDate; // dạng yyyy-MM-dd hoặc ISO-8601
    @XmlElement
    private String devMember;
    @XmlElement
    private String dueDate;
    @XmlElement
    private String pendingDate;
    @XmlElement
    private String manHoursActual;
    @XmlElement
    private String manHoursEstimate;

    public IssueInfo() {
    }

    public IssueInfo(String key, String summary, String urd, String epicLink, String itMainDep, String itRelationDev, String assignee, String devLeader, String status, String devStartDate, String devMember, String dueDate, String pendingDate, String manHoursActual, String manHoursEstimate) {
        this.key = key;
        this.summary = summary;
        this.urd = urd;
        this.epicLink = epicLink;
        this.itMainDep = itMainDep;
        this.itRelationDev = itRelationDev;
        this.assignee = assignee;
        this.devLeader = devLeader;
        this.status = status;
        this.devStartDate = devStartDate;
        this.devMember = devMember;
        this.dueDate = dueDate;
        this.pendingDate = pendingDate;
        this.manHoursActual = manHoursActual;
        this.manHoursEstimate = manHoursEstimate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrd() {
        return urd;
    }

    public void setUrd(String urd) {
        this.urd = urd;
    }

    public String getEpicLink() {
        return epicLink;
    }

    public void setEpicLink(String epicLink) {
        this.epicLink = epicLink;
    }

    public String getItMainDep() {
        return itMainDep;
    }

    public void setItMainDep(String itMainDep) {
        this.itMainDep = itMainDep;
    }

    public String getItRelationDev() {
        return itRelationDev;
    }

    public void setItRelationDev(String itRelationDev) {
        this.itRelationDev = itRelationDev;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDevLeader() {
        return devLeader;
    }

    public void setDevLeader(String devLeader) {
        this.devLeader = devLeader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevStartDate() {
        return devStartDate;
    }

    public void setDevStartDate(String devStartDate) {
        this.devStartDate = devStartDate;
    }

    public String getDevMember() {
        return devMember;
    }

    public void setDevMember(String devMember) {
        this.devMember = devMember;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPendingDate() {
        return pendingDate;
    }

    public void setPendingDate(String pendingDate) {
        this.pendingDate = pendingDate;
    }

    public String getManHoursActual() {
        return manHoursActual;
    }

    public void setManHoursActual(String manHoursActual) {
        this.manHoursActual = manHoursActual;
    }

    public String getManHoursEstimate() {
        return manHoursEstimate;
    }

    public void setManHoursEstimate(String manHoursEstimate) {
        this.manHoursEstimate = manHoursEstimate;
    }
}
