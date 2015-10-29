package com.dunbian.jujiabao.service.comment;

import java.util.List;

import com.dunbian.jujiabao.appobj.extend.CommentAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.DataList;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;

public interface ICommentService {

	List<CommentAO> getCommentListByUser(String userId);
	
	DataList<CommentAO> getCommentListBySet(String setId, Page page);
	
	Result<Boolean> comment(UserAO user, String[] orderDetail, String[] comment);
}
