package come.tool.Transplant;

import java.util.List;

import org.come.entity.Friend;

/**好友转移*/
public class FriendTransplant {

	List<Friend> friends;

	public FriendTransplant(List<Friend> friends) {
		super();
		this.friends = friends;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	
}
