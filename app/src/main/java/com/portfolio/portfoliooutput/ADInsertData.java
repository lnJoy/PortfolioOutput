package com.portfolio.portfoliooutput;
//DTO 클래스: 여러 개의 특성을 묶어서 하나로 표현하기 위한 클래스
//속성별로 저장해서 들고 다니면 여러 개를 가지고 다녀야 해서 불편하므로 하나로 묶어서 다니기 위한 클래스 - 마트의 비닐봉지
//변수는 private 으로 만들어서 외부에서 안보이게 하고 get을 이용해서 가져오고 set을 이용해서 값을 변경
public class ADInsertData {
    private String member_title;
    private String member_adex;
    private String member_youtubeid;
    private String member_email;
    private String member_longex;
    private String member_caution;
    private String member_startdate;
    private String member_enddate;


    public String getMember_title() {
        return member_title;
    }

    public String getMember_adex() {
        return member_adex;
    }

    public String getMember_youtubeid() {
        return member_youtubeid;
    }

    public String getMember_email() {
        return member_email;
    }

    public String getMember_longex() {
        return member_longex;
    }

    public String getMember_caution() {
        return member_caution;
    }

    public String getMember_startdate() {
        return member_startdate;
    }

    public String getMember_enddate() {
        return member_enddate;
    }

    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }

    public void setMember_adex(String member_adex) {
        this.member_adex = member_adex;
    }

    public void setMember_youtubeid(String member_youtubeid) {
        this.member_youtubeid = member_youtubeid;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public void setMember_longex(String member_longex) {
        this.member_longex = member_longex;
    }

    public void setMember_caution(String member_caution) {
        this.member_caution = member_caution;
    }

    public void setMember_startdate(String member_startdate) {
        this.member_startdate = member_startdate;
    }

    public void setMember_enddate(String member_enddate) {
        this.member_enddate = member_enddate;
    }
}