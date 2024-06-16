package spam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;
import spam.vo.SpamVO;

public class SpamDAO {
    public void insertSpam(String spam, int memberCd) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TBL_SPAM(SPAM_CD, SPAM_WORD, MEMBER_CD) ");
        sql.append(" VALUES (SEQ_TBL_SPAM_SPAM_CD.NEXTVAL, ?, ?) ");
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ) {            
            pstmt.setString(1, spam);
            pstmt.setInt(2, memberCd);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteSpam(int spamCd) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ");
        sql.append("  FROM TBL_SPAM ");
        sql.append(" WHERE SPAM_CD = ?");
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ) {            
            pstmt.setInt(1, spamCd);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<SpamVO> selectAllSpamWord(int memberCd){
        List<SpamVO> spamWords = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SPAM_CD, SPAM_WORD, SPAM_REGDATE ");
        sql.append("  FROM TBL_SPAM ");
        sql.append(" WHERE MEMBER_CD = ?");
        sql.append(" ORDER BY SPAM_REGDATE DESC");
        try (
            Connection conn = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ) {            
            pstmt.setInt(1, memberCd);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                SpamVO word = new SpamVO(rs.getInt("SPAM_CD"), rs.getString("SPAM_WORD"), rs.getString("SPAM_REGDATE"));
                spamWords.add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spamWords;
    }
}