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
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_MAIL(MAIL_CD, MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER_CD, MAIL_RECEIVER_CD) ");
		sql.append(" VALUES(SEQ_TBL_MAIL_MAIL_CD.NEXTVAL, ?, ?, ?, ?) ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, mail.getMailTitle());
			pstmt.setString(2, mail.getMailContent());
			pstmt.setInt(3, mail.getMailSenderCd());
			pstmt.setInt(4, mail.getMailReceiverCd());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 받은 메일 전체 조회
	public List<SelectedMailVO> selectAllMails(String mode) {
		List<SelectedMailVO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID,  m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<SelectedMailVO> selectAllMails(String mode, String spamWord) {
		List<SelectedMailVO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_TITLE, ? ) ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_CONTENT, ? ) ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
			pstmt.setString(3, spamWord);
			pstmt.setString(4, spamWord);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	// 검색어로 받은 메일 조회
	public List<SelectedMailVO> selectMailsByWord(String mode, String word) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append("   AND (m1.MAIL_TITLE LIKE '%'|| ? ||'%' OR m2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
			    pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			    pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(3, word);
				pstmt.setString(4, word);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
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
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
	}
	
	public List<SelectedMailVO> selectMailsByWord(String mode, String word, String spamWord) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append("   AND (m1.MAIL_TITLE LIKE '%'|| ? ||'%' OR m2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_TITLE, ? ) ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_CONTENT, ? ) ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
			    pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			    pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(3, word);
				pstmt.setString(4, word);
				pstmt.setString(5, spamWord);
				pstmt.setString(6, spamWord);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
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
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
	}
	
	// 수신 확인 여부 업데이트
	public void updateOpenChk(int mailCd) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_MAIL ");
		sql.append("SET MAIL_OPEN_CHK = 1 ");
		sql.append("WHERE MAIL_CD = ? ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				pstmt.setInt(1, mailCd);
				pstmt.executeQuery();				
			} catch(Exception e) {
				e.printStackTrace();
			}		
	}
	
	// 휴지통 메일 가져오기
	
	public List<SelectedMailVO> selectAllTrashMails(){
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m3.MEMBER_ID AS SENDER_ID, m4.MEMBER_ID AS RECEIVER_ID , m1.MAIL_SENT_DATE, M2.TRASH_CD ");
		sql.append("  FROM TBL_MAIL M1 ");
		sql.append("  JOIN TBL_TRASH M2 ON M2.MAIL_CD = M1.MAIL_CD ");
		sql.append("                   AND M2.MEMBER_CD = ? ");
		sql.append("                   AND M2.PERMANENT = 0 ");
		sql.append("  JOIN TBL_MEMBER M3 ON M3.MEMBER_CD = M1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER M4 ON M4.MEMBER_CD = M1.MAIL_RECEIVER_CD ");
		sql.append(" ORDER BY MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					String mailReceiverId = rs.getString("RECEIVER_ID");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					int trashCd = rs.getInt("TRASH_CD");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, mailReceiverId, mailSentDate, trashCd);
					list.add(mail);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}

	// 휴지통 메일 검색하기
	public List<SelectedMailVO> selectTrashMailsByWord(String word) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS sender_id, M3.MEMBER_ID AS RECEIVER_ID, M1.MAIL_SENT_DATE, T1.TRASH_CD ");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_TRASH t1 ON T1.MAIL_CD = M1.MAIL_CD ");
		sql.append("   AND T1.MEMBER_CD = ? ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE m1.MAIL_RECEIVER_CD = ? ");
		sql.append("   AND (m1.MAIL_TITLE LIKE '%'|| ? ||'%' OR m2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
			    pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			    pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(3, word);
				pstmt.setString(4, word);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					String mailReceiverId = rs.getString("RECEIVER_ID");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					int trashCd = rs.getInt("TRASH_CD");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, mailReceiverId, mailSentDate, trashCd);
					list.add(mail);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	
	}
	
	// 내게 쓴 메일 조회
	public List<SelectedMailVO> selectAllSelfMails() {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, M3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE ");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE (m1.MAIL_SENDER_CD = ? OR m1.MAIL_RECEIVER_CD = ?) ");
		sql.append("   AND MAIL_SENDER_CD = MAIL_RECEIVER_CD ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(3, MailMenuUI.loginMember.getMemberCd());
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	
	// 내게 쓴 메일함 검색
	public List<SelectedMailVO> selectSelfMailsByWord(String word) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, M3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE ");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE (m1.MAIL_SENDER_CD = ? OR m1.MAIL_RECEIVER_CD = ?) ");
		sql.append("   AND MAIL_SENDER_CD = MAIL_RECEIVER_CD ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append("   AND (m1.MAIL_TITLE LIKE '%'|| ? ||'%' OR m2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(3, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(4, word);
				pstmt.setString(5, word);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	
	// 안읽은 메일 조회
	public List<SelectedMailVO> selectUnreadMails(String mode) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH) ");
		sql.append("   AND MAIL_OPEN_CHK = 0 ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<SelectedMailVO> selectUnreadMails(String mode, String spamWord) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID, m1.MAIL_OPEN_CHK, m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH) ");
		sql.append("   AND MAIL_OPEN_CHK = 0 ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_TITLE, ? ) ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_CONTENT, ? ) ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			pstmt.setString(2, spamWord);
			pstmt.setString(3, spamWord);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	// 스팸 메일 조회하기
	public List<SelectedMailVO> selectSpamMails(String spamWord){
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID ,m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_RECEIVER_CD = ? ");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH) ");
		sql.append("   AND (REGEXP_LIKE(MAIL_TITLE, ? ) ");
		sql.append("   OR REGEXP_LIKE(MAIL_CONTENT, ? )) ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(2, spamWord);
				pstmt.setString(3, spamWord);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					String mailReceiverId = rs.getString("RECEIVER_ID");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, mailReceiverId, 0, mailSentDate);
					list.add(mail);
				}				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	
	// 스팸 메일 검색 조회
	public List<SelectedMailVO> selectSpamMailsByWord(String spamWord, String word){
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m1.MAIL_CD, m1.MAIL_TITLE, m1.MAIL_CONTENT, m2.MEMBER_ID AS SENDER_ID, m3.MEMBER_ID AS RECEIVER_ID ,m1.MAIL_SENT_DATE");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append("  JOIN TBL_MEMBER m3 ON m3.MEMBER_CD = m1.MAIL_RECEIVER_CD ");
		sql.append(" WHERE MAIL_RECEIVER_CD = ?");
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH) ");
		sql.append("   AND (REGEXP_LIKE(MAIL_TITLE, ? ) ");
		sql.append("   OR REGEXP_LIKE(MAIL_CONTENT, ? )) ");
		sql.append("   AND (m1.MAIL_TITLE LIKE '%'|| ? ||'%' OR m2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append(" ORDER BY m1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			pstmt.setString(2, spamWord);
			pstmt.setString(3, spamWord);
			pstmt.setString(4, word);
			pstmt.setString(5, word);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int mailCd = rs.getInt("MAIL_CD");
				String mailTitle = rs.getString("MAIL_TITLE");
				String mailContent = rs.getString("MAIL_CONTENT");
				String mailSenderId = rs.getString("SENDER_ID");
				String mailReceiverId = rs.getString("RECEIVER_ID");
				String mailSentDate = rs.getString("MAIL_SENT_DATE");
				
				SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, mailReceiverId, 0, mailSentDate);
				list.add(mail);
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	//회원 전체에게 메일 보내기
	public void insertMailToAllMembers(MailVO mail) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_MAIL(MAIL_CD, MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER_CD, MAIL_RECEIVER_CD) ");
		sql.append(" SELECT SEQ_TBL_MAIL_MAIL_CD.NEXTVAL, ?, ?, 1, MEMBER_CD ");
		sql.append("   FROM TBL_MEMBER WHERE MEMBER_CD > 1 ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, mail.getMailTitle());
			pstmt.setString(2, mail.getMailContent());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 관리자 메일 전체 조회
	public List<SelectedMailVO> selectAllAdminMails(){
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M1.MAIL_CD, M1.MAIL_TITLE, M1.MAIL_CONTENT, M2.MEMBER_ID AS SENDER_ID, M1.MAIL_OPEN_CHK, M1.MAIL_SENT_DATE ");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append(" WHERE MAIL_RECEIVER_CD = 1 ");
		sql.append("   AND M1.MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = 1) ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					int mailOpenChk = rs.getInt("MAIL_OPEN_CHK");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, "Admin", mailOpenChk, mailSentDate);
					list.add(mail);
				}				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}
	
	public List<SelectedMailVO> selectUnreadAdminMails(){
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M1.MAIL_CD, M1.MAIL_TITLE, M1.MAIL_CONTENT, M2.MEMBER_ID AS SENDER_ID, M1.MAIL_OPEN_CHK, M1.MAIL_SENT_DATE ");
		sql.append("  FROM TBL_MAIL m1 ");
		sql.append("  JOIN TBL_MEMBER m2 ON m2.MEMBER_CD = m1.MAIL_SENDER_CD ");
		sql.append(" WHERE MAIL_RECEIVER_CD = 1 ");
		sql.append("   AND M1.MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = 1) ");
		sql.append("   AND M1.MAIL_OPEN_CHK  = 0 ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					int mailOpenChk = rs.getInt("MAIL_OPEN_CHK");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, "Admin", mailOpenChk, mailSentDate);
					list.add(mail);
				}				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}

	public List<SelectedMailVO> selectAdminMailsByWord(String word) {
		List<SelectedMailVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M1.MAIL_CD, M1.MAIL_TITLE, M1.MAIL_CONTENT, M2.MEMBER_ID AS SENDER_ID, M1.MAIL_OPEN_CHK, M1.MAIL_SENT_DATE ");
		sql.append("  FROM TBL_MAIL M1 ");
		sql.append("  JOIN TBL_MEMBER M2 ON M2.MEMBER_CD = M1.MAIL_SENDER_CD ");
		sql.append(" WHERE MAIL_RECEIVER_CD = 1 ");
		sql.append("   AND M1.MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = 1) ");
		sql.append("   AND M1.MAIL_OPEN_CHK  = 0 ");
		sql.append("   AND (M1.MAIL_TITLE LIKE '%'|| ? ||'%' OR M2.MEMBER_ID LIKE  '%'|| ? ||'%') ");
		sql.append(" ORDER BY M1.MAIL_SENT_DATE DESC ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			){
			pstmt.setString(1, word);
			pstmt.setString(2, word);
			ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int mailCd = rs.getInt("MAIL_CD");
					String mailTitle = rs.getString("MAIL_TITLE");
					String mailContent = rs.getString("MAIL_CONTENT");
					String mailSenderId = rs.getString("SENDER_ID");
					int mailOpenChk = rs.getInt("MAIL_OPEN_CHK");
					String mailSentDate = rs.getString("MAIL_SENT_DATE");
					
					SelectedMailVO mail = new SelectedMailVO(mailCd, mailTitle, mailContent, mailSenderId, "Admin", mailOpenChk, mailSentDate);
					list.add(mail);
				}				
			} catch(Exception e) {
				e.printStackTrace();
			}
		return list;
	}
}
