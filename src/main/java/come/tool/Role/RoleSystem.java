package come.tool.Role;
/**玩家系统设置*/
public class RoleSystem {
	 /**显示模式 0-800*600 1-1024*768*/
    private Integer show=1;
    /**背景声音 0是关 1是开*/
    private Integer isSound=1;
    /**音效声音*/
    private Integer isSound2=1;
    /**PK*/
    private Integer isPk=1;
    /**消息*/
    private Integer isNews=1;
    /**邮件*/
    private Integer isMail=1;
    /**好友*/
    private Integer isFriend=1;
    /**接收物品*/
    private Integer isGood=1;
    /**接受组队*/
    private Integer isTeam=1;
    public void set(RoleSystem roleSystem){
    	if (roleSystem.show!=null) {
			this.show=roleSystem.show;
		}
    	if (roleSystem.isSound!=null) {
			this.isSound=roleSystem.isSound;
		}
    	if (roleSystem.isSound2!=null) {
			this.isSound2=roleSystem.isSound2;
		}
    	if (roleSystem.isPk!=null) {
			this.isPk=roleSystem.isPk;
		}
    	if (roleSystem.isNews!=null) {
			this.isNews=roleSystem.isNews;
		}
    	if (roleSystem.isMail!=null) {
			this.isMail=roleSystem.isMail;
		}
    	if (roleSystem.isFriend!=null) {
			this.isFriend=roleSystem.isFriend;
		}
    	if (roleSystem.isGood!=null) {
			this.isGood=roleSystem.isGood;
		}
    	if (roleSystem.isTeam!=null) {
			this.isTeam=roleSystem.isTeam;
		}
    }
	public Integer getShow() {
		return show;
	}
	public void setShow(Integer show) {
		this.show = show;
	}
	public Integer getIsSound() {
		return isSound;
	}
	public void setIsSound(Integer isSound) {
		this.isSound = isSound;
	}
	public Integer getIsSound2() {
		return isSound2;
	}
	public void setIsSound2(Integer isSound2) {
		this.isSound2 = isSound2;
	}
	public Integer getIsPk() {
		return isPk;
	}
	public void setIsPk(Integer isPk) {
		this.isPk = isPk;
	}
	public Integer getIsNews() {
		return isNews;
	}
	public void setIsNews(Integer isNews) {
		this.isNews = isNews;
	}
	public Integer getIsMail() {
		return isMail;
	}
	public void setIsMail(Integer isMail) {
		this.isMail = isMail;
	}
	public Integer getIsFriend() {
		return isFriend;
	}
	public void setIsFriend(Integer isFriend) {
		this.isFriend = isFriend;
	}
	public Integer getIsGood() {
		return isGood;
	}
	public void setIsGood(Integer isGood) {
		this.isGood = isGood;
	}
	public Integer getIsTeam() {
		return isTeam;
	}
	public void setIsTeam(Integer isTeam) {
		this.isTeam = isTeam;
	}
}
