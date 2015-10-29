package com.dunbian.jujiabao.service.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.CommentAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderDetailAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.generator.CommentExample;
import com.dunbian.jujiabao.appobj.generator.OrderDetailExample;
import com.dunbian.jujiabao.appobj.generator.UserExample;
import com.dunbian.jujiabao.db.customer.FoodSetCustomMapper;
import com.dunbian.jujiabao.db.generator.CommentMapper;
import com.dunbian.jujiabao.db.generator.OrderDetailMapper;
import com.dunbian.jujiabao.db.generator.OrderMapper;
import com.dunbian.jujiabao.db.generator.UserMapper;
import com.dunbian.jujiabao.framework.obj.DataList;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;

@Service
public class CommentService implements ICommentService {

	@Resource
	private OrderDetailMapper orderDetailMapper;
	
	@Resource
	private CommentMapper commentMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private FoodSetCustomMapper foodSetCustomMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<CommentAO> getCommentListByUser(String userId) {
		CommentExample ce = new CommentExample();
		ce.createCriteria().andUserIdEqualTo(userId);
		ce.setOrderByClause("create_time desc");
		
		List<CommentAO> cmtList = commentMapper.selectByExample(ce);
		getSubComment(cmtList);
		return cmtList;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> comment(UserAO user, String[] orderDetail, String[] comment) {
		if(orderDetail == null || orderDetail.length == 0 || comment == null || comment.length == 0) {
			return new Result<>(false, "评论不能为空");
		}
		
		if(orderDetail.length != comment.length) {
			return new Result<Boolean>(false, "请填写评论");
		}
		
		List<String> odIds = new ArrayList<>();
		int i=0;
		Map<String, String> tmp = new TreeMap<String, String>();
		for(String od : orderDetail) {
			odIds.add(od);
			tmp.put(od, comment[i++]);
		}
		
		OrderDetailExample ode = new OrderDetailExample();
		ode.createCriteria().andIdIn(odIds);
		List<OrderDetailAO> odList = orderDetailMapper.selectByExample(ode);

		if(odList == null || odList.isEmpty()) {
			return new Result<Boolean>(false, "请填写评论");
		}
		
		String orderId = null;
		for(OrderDetailAO detail : odList) {
			if(detail.getOrderId() != null && !detail.getOrderId().equals(orderId)) {
				orderId = detail.getOrderId();
				OrderAO order = orderMapper.selectByPrimaryKey(detail.getOrderId());
				if(!OrderAO.COMMENT_STATUS_WAITFOR.equals(order.getCommentStatus())) {
					return new Result<Boolean>(false, "订单状态无法评论");
				} else if(!user.getId().equals(order.getUserId())) {
					return new Result<Boolean>(false, "您无权操作");
				}
			}
		}
		
		orderId = null;
		
		for(OrderDetailAO detail : odList) {
			if("undefined".equals(tmp.get(detail.getId()))) {
				continue;
			}
			CommentAO cmt = new CommentAO();
			cmt.setCommentType(CommentAO.COMMENT_TYPE_USER);
			cmt.setCreateTime(new Date());
			cmt.setGroupNo(null);
			cmt.setNickname(user.getNickname());
			cmt.setOrderId(detail.getOrderId());
			cmt.setSetId(detail.getSetId());
			cmt.setSubCount(0);
			cmt.setSummary(tmp.get(detail.getId()));
			cmt.setToTitle(detail.getSetTitle());
			cmt.setUserId(user.getId());
			
			commentMapper.insert(cmt);
			
			CommentAO upd = new CommentAO();
			upd.setId(cmt.getId());
			upd.setGroupNo(cmt.getId());
			commentMapper.updateByPrimaryKeySelective(upd);
			
			foodSetCustomMapper.updateCommentCount(1, cmt.getSetId());
			if(detail.getOrderId() != null && !detail.getOrderId().equals(orderId)) {
				orderId = detail.getOrderId();
				resetCommentStatus(orderId);
			}
		}
		
		return new Result<Boolean>(true);
	}
	
	private void resetCommentStatus(String id) {
		OrderAO upd = new OrderAO();
		upd.setId(id);
		upd.setCommentStatus(OrderAO.COMMENT_STATUS_COMMENTED);
		
		orderMapper.updateByPrimaryKeySelective(upd);
	}

	@Override
	@Transactional(readOnly=true)
	public DataList<CommentAO> getCommentListBySet(String setId, Page page) {
		CommentExample ce = new CommentExample();
		ce.createCriteria().andSetIdEqualTo(setId).andCommentTypeEqualTo(CommentAO.COMMENT_TYPE_USER);
		ce.setPageCount(page.getPageSize());
		ce.setStartRecord(page.getStart());
		ce.setOrderByClause("create_time desc");
		List<CommentAO> cmList = commentMapper.selectByExample(ce);
		getSubComment(cmList);
//		ce = new CommentExample();
//		ce.createCriteria().andSetIdEqualTo(setId).andCommentTypeEqualTo(CommentAO.COMMENT_TYPE_USER);
//		int total = commentMapper.countByExample(ce);
		DataList<CommentAO> ret = new DataList<CommentAO>();
		ret.setData(cmList);
//		ret.setTotal(total);
		return ret;
	}
	
	private void getSubComment(List<CommentAO> cmList) {
		if(cmList == null || cmList.isEmpty()) {
			return;
		}
		
		List<String> uids = new ArrayList<>();
		for(CommentAO cmt : cmList) {
			if(cmt.getSubCount() > 0) {
				CommentExample coe = new CommentExample();
				coe.createCriteria().andCommentTypeEqualTo(CommentAO.COMMENT_TYPE_SYS).andGroupNoEqualTo(cmt.getId());
				cmt.setCommentList(commentMapper.selectByExample(coe));
			}
			
			uids.add(cmt.getUserId());
		}
		
		UserExample ue = new UserExample();
		ue.createCriteria().andIdIn(uids);
		List<UserAO> userList = userMapper.selectByExample(ue);
		Map<String, UserAO> tmp = new HashMap<String, UserAO>();
		if(userList != null && !userList.isEmpty()) {
			for(UserAO u : userList) {
				tmp.put(u.getId(), u);
			}
		}
		
		for(CommentAO cmt : cmList) {
			cmt.setUser(tmp.get(cmt.getUserId()));
		}
	}

}
