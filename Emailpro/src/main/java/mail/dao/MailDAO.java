package mail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;
import mail.vo.MailVO;
import mail.vo.SelectedMailVO;

public class MailDAO {    
    
    // 메일 쓰기
    public void insertMail(MailVO mail) {
        String sql = "INSERT INTO TBL_MAIL(MAIL_CD, MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER_CD, MAIL_RECEIVER_CD) VALUES(SEQ_TBL_MAIL_MAIL_CD.NEXTVAL, ?, ?, ?, ?)";
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, mail.getMailTitle());
            pstmt.setString(2, mail.getMailContent());
            pstmt.setInt(3, mail.getMailSenderCd());
            pstmt.setInt(4, mail.getMailReceiverCd());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 공통 메일 조회 로직
    private List<SelectedMailVO> getMailsFromResultSet(ResultSet rs) throws Exception {
        List<SelectedMailVO> list = new ArrayList<>();
        while (rs.next()) {
            int mailCd = rs.getInt("MAIL_CD");
            String mailTitle = rs.getString("MAIL_TITLE");
            String mailContent = rs.getString("MAIL_CONTENT");
            String mailSenderId = rs.getString("SENDER_ID");
            String mailReceiverId = rs.getString("RECEIVER_ID");
            int mailOpenChk = rs.getInt("MAIL_OPEN_CHK");
            String mailSentDate = rs.getString("MAIL_SENT_DATE");
            SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, mailReceiverId, mailOpenChk, mailSentDate);
            list.add(mail);
        }
        return list;
    }

    // 받은 메일 전체 조회
    public List<SelectedMailVO> selectAllMails(int memberCd, String mode) {
        String sql = "SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID,  m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE "
                   + "FROM TBL_MAIL m1 "
                   + "JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD "
                   + "JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD "
                   + "WHERE MAIL_" + mode + "_CD = ? "
                   + "AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) "
                   + "ORDER BY m1.MAIL_SENT_DATE DESC";
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, memberCd);
            pstmt.setInt(2, memberCd);
            try (ResultSet rs = pstmt.executeQuery()) {
                return getMailsFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 스팸 단어 필터링 추가된 받은 메일 전체 조회
    public List<SelectedMailVO> selectAllMails(int memberCd, String mode, String spamWord) {
        String sql = "SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE "
                   + "FROM TBL_MAIL m1 "
                   + "JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD "
                   + "JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD "
                   + "WHERE MAIL_" + mode + "_CD = ? "
                   + "AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) "
                   + "AND NOT REGEXP_LIKE(MAIL_TITLE, ?) "
                   + "AND NOT REGEXP_LIKE(MAIL_CONTENT, ?) "
                   + "ORDER BY m1.MAIL_SENT_DATE DESC";
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, memberCd);
            pstmt.setInt(2, memberCd);
            pstmt.setString(3, spamWord);
            pstmt.setString(4, spamWord);
            try (ResultSet rs = pstmt.executeQuery()) {
                return getMailsFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 검색어로 받은 메일 조회
    public List<SelectedMailVO> selectMailsByWord(int memberCd, String mode, String word) {
        String sql = "SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE "
                   + "FROM TBL_MAIL m1 "
                   + "JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD "
                   + "JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD "
                   + "WHERE MAIL_" + mode + "_CD = ? "
                   + "AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) "
                   + "AND (m1.MAIL_TITLE LIKE '%' || ? || '%' OR m2.MEMBER_ID LIKE '%' || ? || '%') "
                   + "ORDER BY m1.MAIL_SENT_DATE DESC";
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, memberCd);
            pstmt.setInt(2, memberCd);
            pstmt.setString(3, word);
            pstmt.setString(4, word);
            try (ResultSet rs = pstmt.executeQuery()) {
                return getMailsFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 스팸 단어 필터링 추가된 검색어로 받은 메일 조회
    public List<SelectedMailVO> selectMailsByWord(int memberCd, String mode, String word, String spamWord) {
        String sql = "SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE "
                   + "FROM TBL_MAIL m1 "
                   + "JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD "
                   + "JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD "
                   + "WHERE MAIL_" + mode + "_CD = ? "
                   + "AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) "
                   + "AND (m1.MAIL_TITLE LIKE '%' || ? || '%' OR m2.MEMBER_ID LIKE '%' || ? || '%') "
                   + "AND NOT REGEXP_LIKE(MAIL_TITLE, ?) "
                   + "AND NOT REGEXP_LIKE(MAIL_CONTENT, ?) "
                   + "ORDER BY m1.MAIL_SENT_DATE DESC";
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, memberCd);
            pstmt.setInt(2, memberCd);
            pstmt.setString(3, word);
            pstmt.setString(4, word);
            pstmt.setString(5, spamWord);
            pstmt.setString(6, spamWord);
            try (ResultSet rs = pstmt.executeQuery()) {
                return getMailsFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }    
    }
        public MailVO selectByMailCd(int mailCd) {
            MailVO mail = null;
            String sql = "SELECT MAIL_CD, MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER_CD, MAIL_RECEIVER_CD, MAIL_OPEN_CHK, MAIL_SENT_DATE "
                       + "FROM TBL_MAIL "
                       + "WHERE MAIL_CD = ?";
            
            try (
                Connection conn = new ConnectionFactory().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                pstmt.setInt(1, mailCd);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        mail = new MailVO(
                            rs.getInt("MAIL_CD"),
                            rs.getString("MAIL_TITLE"),
                            rs.getString("MAIL_CONTENT"),
                            rs.getInt("MAIL_SENDER_CD"),
                            rs.getInt("MAIL_RECEIVER_CD"),
                            rs.getInt("MAIL_OPEN_CHK"),
                            rs.getString("MAIL_SENT_DATE")
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return mail;
    }
}