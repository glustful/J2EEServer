package com.dunbian.jujiabao.appobj.extend;

import java.io.Serializable;
import java.util.List;

import com.dunbian.jujiabao.appobj.generator.Comment;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;

public class CommentAO extends Comment implements Serializable {

	private static final long serialVersionUID = 7101018736617578849L;

	// 用户评论
	public static String COMMENT_TYPE_USER = "00";

	// 系统评论
	public static String COMMENT_TYPE_SYS = "10";

	private List<CommentAO> commentList;

	public List<CommentAO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentAO> commentList) {
		this.commentList = commentList;
	}

	private UserAO user;

	public UserAO getUser() {
		return user;
	}

	public void setUser(UserAO user) {
		this.user = user;
	}

	public String getCreateTimeStr() {
		if (this.getCreateTime() != null) {
			return DateTimeUtil.format(this.getCreateTime(),
					DateTimeUtil.FORMAT_YMD_HM);
		} else {
			return null;
		}
	}
}
