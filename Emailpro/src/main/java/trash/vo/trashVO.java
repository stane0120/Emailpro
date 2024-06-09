package trash.vo;

public class trashVO {
	private int mailCd;
	private String mailTitle;
	private String mailContent;
	private String SenderId;
	private String ReceiverId;
	private String mailSentDate;
	private int trashCd;
	
	public trashVO(int mailCd, String mailTitle, String mailContent, String senderId, String receiverId,
			String mailSentDate, int trashCd) {
		super();
		this.mailCd = mailCd;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		SenderId = senderId;
		ReceiverId = receiverId;
		this.mailSentDate = mailSentDate;
		this.trashCd = trashCd;
	}

	public int getMailCd() {
		return mailCd;
	}

	public void setMailCd(int mailCd) {
		this.mailCd = mailCd;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getSenderId() {
		return SenderId;
	}

	public void setSenderId(String senderId) {
		SenderId = senderId;
	}

	public String getReceiverId() {
		return ReceiverId;
	}

	public void setReceiverId(String receiverId) {
		ReceiverId = receiverId;
	}

	public String getMailSentDate() {
		return mailSentDate;
	}

	public void setMailSentDate(String mailSentDate) {
		this.mailSentDate = mailSentDate;
	}

	public int getTrashCd() {
		return trashCd;
	}

	public void setTrashCd(int trashCd) {
		this.trashCd = trashCd;
	}

	@Override
	public String toString() {
		return "TrashMailVO [mailCd=" + mailCd + ", mailTitle=" + mailTitle + ", mailContent=" + mailContent
				+ ", SenderId=" + SenderId + ", ReceiverId=" + ReceiverId + ", mailSentDate=" + mailSentDate
				+ ", trashCd=" + trashCd + "]";
	}	
}