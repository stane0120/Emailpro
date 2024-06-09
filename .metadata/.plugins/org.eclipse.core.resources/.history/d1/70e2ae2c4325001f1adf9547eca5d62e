package kr.ac.kopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.MemberVO;

public class MemberDAO {
	
	// 멤버 하나 추가
	public void insertMember(MemberVO member) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TBL_MEMBER(MEMBER_CD, MEMBER_ID, MEMBER_PW, MEMBER_NM, MEMBER_BIRTHDATE, MEMBER_PHONE) ");
		sql.append(" VALUES (SEQ_TBL_MEMBER_MEMBER_CD.NEXTVAL, ?, ?, ?, TO_DATE(?, 'YYYYMMDD'), ?) ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberNm());
			pstmt.setString(4, member.getMemberBirthDate());
			pstmt.setString(5, member.getMemberPhone());
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 아이디와 비밀번호로 멤버 찾기
	public int selectMemberCdByIdPw(String id, String password) {
		int memberCd = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MEMBER_CD ");
		sql.append("  FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_ID = ? AND MEMBER_PW = ? ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memberCd = rs.getInt("MEMBER_CD");
			}				
		} catch(Exception e) {
				e.printStackTrace();
		}
		
		return memberCd;
	}
		
	
	// 아이디로 멤버 cd 찾기
	public int selectMemberCdById(String id) {
		int memberCd = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MEMBER_CD ");
		sql.append("  FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_ID = ? ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memberCd = rs.getInt("MEMBER_CD");
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return memberCd;
	}
	
	// 이름과 휴대폰 번호로 멤버 아이디 찾기
	public String selectMemberIdByNmPhone(String name, String phone) {
		String memberId = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MEMBER_ID ");
		sql.append(" FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_NM = ? ");
		sql.append(" AND MEMBER_PHONE = ? ");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memberId = rs.getString("MEMBER_ID");
			}				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return memberId;
	}
	
	// 회원 하나 찾기
	public MemberVO selectOneMember(String id, String password) {
		MemberVO member = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append("  FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_ID = ? ");
		sql.append("   AND MEMBER_PW = ? ");
		
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberVO(rs.getInt("MEMBER_CD"), rs.getString("MEMBER_ID"), rs.getString("MEMBER_PW"), rs.getString("MEMBER_NM")
						, rs.getString("MEMBER_BIRTHDATE"), rs.getString("MEMBER_PHONE"), rs.getString("MEMBER_STATUS"), rs.getString("MEMBER_REGDATE"));
			} 				
		} catch(Exception e) {
				e.printStackTrace();
		}
		
		return member;
	}
	
	
	// 모든 멤버 조회
	public List<MemberVO> selectMembers() {
		List<MemberVO> list = new ArrayList<>(); 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM TBL_MEMBER");
		try(
				Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int memberCd = rs.getInt("MEMBER_CD");
				String memberId = rs.getString("MEMBER_ID");
				String memberNm = rs.getString("MEMBER_NM");
				String memberBirthDate = rs.getString("MEMBER_BIRTHDATE");
				String memberPhone = rs.getString("MEMBER_PHONE");
				String memberStatus = rs.getString("MEMBER_STATUS");
				String memberRegdate = rs.getString("MEMBER_REGDATE");
				MemberVO member = new MemberVO(memberCd, memberId, null, memberNm, memberBirthDate, memberPhone, memberStatus, memberRegdate);
				list.add(member);
			};
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 비밀번호 수정
	public int updateMemberPw(String id, String phone, String pw) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_MEMBER ");
		sql.append(" SET MEMBER_PW = ? ");
		sql.append(" WHERE MEMBER_ID = ? ");
		sql.append(" AND MEMBER_PHONE = ? ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			pstmt.setString(3, phone);
			result = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 핸드폰번호 수정
	public void updateMemberPhone(int memberCd, String phone) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_MEMBER ");
		sql.append("   SET MEMBER_PHONE = ? ");
		sql.append(" WHERE MEMBER_CD = ? ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, phone);
			pstmt.setInt(2, memberCd);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 멤버 활동 상태 수정
	public void updateMemberStatus(int memberCd, String status) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TBL_MEMBER ");
		sql.append("   SET MEMBER_STATUS = ? ");
		sql.append(" WHERE MEMBER_CD = ? ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, status);
			pstmt.setInt(2, memberCd);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 핸드폰번호 카운트 확인 > 존재 여부 확인용
	public int selectPhoneCnt(String phone) {
		int phoneCnt = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(MEMBER_PHONE) AS CNT ");
		sql.append("  FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_PHONE = ? ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, phone);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				phoneCnt = rs.getInt(1);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return phoneCnt;
	}

	public int selectMemberCdCnt(int memberCd) {
		int memberCnt = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(MEMBER_CD) AS CNT ");
		sql.append("  FROM TBL_MEMBER ");
		sql.append(" WHERE MEMBER_CD = ? ");
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, memberCd);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memberCnt = rs.getInt(1);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return memberCnt;
	}
}
