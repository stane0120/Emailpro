package kr.ac.kopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.ac.kopo.ui.MailMenuUI;
import kr.ac.kopo.util.ConnectionFactory;

public class TrashDAO {
	public void insertTrash(int mailCd) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_TRASH(TRASH_CD, MAIL_CD, MEMBER_CD) ");
		sql.append(" VALUES (SEQ_TBL_TRASH_TRASH_CD.NEXTVAL, ?, ?) ");
		try (
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
			pstmt.setInt(1, mailCd);
			pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePermanent(int trashCd) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_TRASH ");
		sql.append(" SET PERMANENT = 1 ");
		sql.append(" WHERE TRASH_CD = ? ");
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				pstmt.setInt(1, trashCd);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public void deleteTrash(int trashCd) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE ");
		sql.append("  FROM TBL_TRASH ");
		sql.append(" WHERE TRASH_CD = ? ");
		
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				pstmt.setInt(1, trashCd);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	
	// 전체 삭제
	public void insertMailsToTrash(String mode) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_TRASH (TRASH_CD, MAIL_CD, MEMBER_CD) ");
		sql.append("SELECT SEQ_TBL_TRASH_TRASH_CD.NEXTVAL, MAIL_CD, MAIL_" + mode + "_CD FROM TBL_MAIL ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ? ");		
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				) {
			pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
			pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void insertMailsToTrash(String mode, String spamWord) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_TRASH (TRASH_CD, MAIL_CD, MEMBER_CD) ");
		sql.append("SELECT SEQ_TBL_TRASH_TRASH_CD.NEXTVAL, MAIL_CD, MAIL_" + mode + "_CD FROM TBL_MAIL ");
		sql.append(" WHERE MAIL_");
		sql.append(mode);
		sql.append("_CD = ?");		
		sql.append("   AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ?) ");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_TITLE, ? )");
		sql.append("   AND NOT REGEXP_LIKE(MAIL_CONTENT, ? )");
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(3, spamWord);
				pstmt.setString(4, spamWord);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	} 
	
	// 휴지통 여러개 영구 삭제
	public void updateAllPermanent() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_TRASH ");
		sql.append("   SET PERMANENT = 1 ");
		sql.append(" WHERE MEMBER_CD = ? ");
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	// 스팸메일함 전체 삭제
	
	public void insertSpamMailsToTrash(String spamWord) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_TRASH (TRASH_CD, MAIL_CD, MEMBER_CD) ");
		sql.append(" SELECT SEQ_TBL_TRASH_TRASH_CD.NEXTVAL, MAIL_CD, MAIL_RECEIVER_CD ");
		sql.append("   FROM TBL_MAIL ");				
		sql.append("  WHERE MAIL_RECEIVER_CD = ? ");
		sql.append("    AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = ? ) ");
		sql.append("    AND (REGEXP_LIKE(MAIL_TITLE, ? ) OR REGEXP_LIKE(MAIL_CONTENT , ? )) ");
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				pstmt.setInt(1, MailMenuUI.loginMember.getMemberCd());
				pstmt.setInt(2, MailMenuUI.loginMember.getMemberCd());
				pstmt.setString(3, spamWord);
				pstmt.setString(4, spamWord);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	} 
	
	// 관리자 메일 전체 삭제
	public void insertAdminMailsToTrash() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_TRASH (TRASH_CD, MAIL_CD, MEMBER_CD) ");
		sql.append(" SELECT SEQ_TBL_TRASH_TRASH_CD.NEXTVAL, MAIL_CD, MAIL_RECEIVER_CD ");
		sql.append("   FROM TBL_MAIL ");				
		sql.append("  WHERE MAIL_RECEIVER_CD = 1 ");
		sql.append("    AND MAIL_CD NOT IN (SELECT MAIL_CD FROM TBL_TRASH WHERE MEMBER_CD = 1 ) ");
		try (
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				) {
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
