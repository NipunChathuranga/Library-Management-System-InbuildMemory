package db;

import java.time.LocalDate;

public class IssueDetail {

    private String issueId;
    private LocalDate issueDate;
    private String bookid;
    private String memberId;
    private String membername;

    public IssueDetail() {
    }



    public IssueDetail(String issueId, LocalDate issueDate, String bookid, String memberId, String membername) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.bookid = bookid;
        this.memberId = memberId;
        this.membername = membername;
    }



    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }


    @Override
    public String toString() {
        return "IssueDetail{" +
                "issueId='" + issueId + '\'' +
                ", issueDate=" + issueDate +
                ", bookid='" + bookid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", membername='" + membername + '\'' +
                '}';
    }
}
