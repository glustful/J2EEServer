<#include "/common/base.ftl">

<@base title="首页">
  <div class="container bd-content main-container" style="padding-bottom:28px;">
    <div class="row">
      <div class="carousel slide section top-ad" data-ride="carousel" style="margin-top:-32px;margin-bottom:30px;">
        <div class="carousel-inner" role="listbox">
          <#if top??>
          	<#list top as adv>
          		<div class="item <#if adv_index==0>active</#if>">
		          <a href="${serverPath!}/advertisement/detail?id=${(adv.id)!}"><img src="${uploaddir}${(adv.imgWeb)!}"></a>
		        </div>
          	</#list>
          </#if>
        </div>
      </div>
    </div>

    <div class="row" style="margin-bottom:30px;">
      <div class="row top-nav">
        <div class="col-md-6" style="padding-left:0;">
          <a class="<#if !time?? || time=='10'>active</#if>" href="/" style="margin-left:0;">今天</a>
          <a class="<#if time?? && time=='20'>active</#if>" href="/food?time=20">明天</a>
        </div>
        <div class="col-md-6" style="text-align:right;padding-right:0;">
          <i class="glyphicon glyphicon-time" style="top:2px;margin-right:8px;"></i>
          <span>${bookStr}</span>
          <span class="split" style="margin:0 8px;">|</span>
          <span>${deliveryStr}</span>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12" style="padding:0;">
        <#if seckillList??>
        	<#list seckillList as seckill>
			 <div class="food-item miaosha-item" data-id="${seckill.id}">
			 	  <input type="hidden" name="startTime" value="${(seckill.startTimeLong?c)!}"/>
			 	  <input type="hidden" name="endTime" value="${(seckill.endTimeLong?c)!}"/>
			 	  <input type="hidden" name="currentTime" value="${now?c!}"/>
		          <div class="row" style="position:relative;">
		          <div class="col-md-9">
		            <div style="over-flow:hide;">
		              <img class="cover" src="${uploaddir}${seckill.logo}">
		            </div>
		          </div>
		          <div class="col-md-3">
		            <div>
		              <img style="width:100%;" src="/jjbstatic/img/miaosha.png">
		            </div>
		            <div class="timer">
		              <p class="t1"><span class="duration"></span> <span class="lab"></span></p>
		              <p class="t2">${(seckill.startTimeStr)!} 开始 - ${(seckill.endTimeStr)!} 结束</p>
		            </div>
		            <div style="margin-top:32px;">
		              <p style="font-size:16px;font-weight:bold;">${(seckill.name)!}</p>
		              <p>${(seckill.memo)!}</p>
		              <p style="font-size:20px;margin-top:5px;">￥${seckill.price}元</p>
		            </div>
		          </div>
		          <div>
		            <span class="btn btn-primary btn-lg btn-block miaosha-now" data-set="" style="bottom:30px;">马上秒杀</span>
		          </div>
		          </div>
		        </div>
		        <div class="item-split" data-index="${seckill_index}"></div>
	      </#list>
        </#if>

        <#if dataList??>
          <#list dataList as foodSet>
            <div class="food-item">
              <div class="row" style="position:relative;">
              <div class="col-md-9">
                <div style="over-flow:hide;">
                  <img class="cover" src="/cover${(foodSet.logo)!}">
                </div>
                <div class="cover-ft row">
                  <div class="action col-md-6" style="padding-left:25px;">
                    <span class="food-detail" data-setid="${(foodSet.id)!}">套餐详情 <i class="glyphicon glyphicon-download"></i></span>
                    <span class="split"></span>
                    <span class="food-comment" data-setid="${(foodSet.id)!}">评论 ${(foodSet.commentCount)!}</span>
                  </div>
                  <div class="desc col-md-6" style="padding-right:25px;">
                    <p style="margin-top:8px;">已有${(foodSet.saleCount)!}人选购</p>
                    <p>${leftTime!}</p>
                  </div>
                </div>
              </div>
              <div class="col-md-3">
                <div style="border-bottom:1px solid #f0f2f2;padding-left:10px;padding-right:10px;">
                  <p class="name">${(foodSet.setTypeName)!}</p>
                  <p class="lb" style="margin-bottom:2px;">菜品：</p>
                  <p class="foods">${(foodSet.name)!}</p>
                </div>
                <div style="margin-top:35px;border-bottom:1px solid #f0f2f2;padding-left:10px;padding-right:10px;">
                  <p class="price">${(foodSet.price)!}元/份</p>
                  <p class="market">市场价：${(foodSet.marketPrice)!}元</p>
                  <p>${foodSet.remainMsg}</p>
                </div>
                <div style="margin-top:35px; <#if canOrder?? && !canOrder>display:none;</#if>" >
                  <span style="line-height:26px;">数量：</span>
                  <span class="btn btn-default btn-math" data-type="minus">-</span	><input id="cart-${(foodSet.id)!}" class="count" type="text" readonly value="1"><span class="btn btn-default btn-math" data-type="plus">+</span>
                </div>
              </div>
              <div>
                <span class="btn btn-primary btn-lg btn-block order-now" data-set="${(foodSet.id)!}" <#if canOrder?? && !canOrder>disabled</#if>>立即订餐</span>
              </div>
              </div>
            </div>

            <div class="item-split" data-index="${foodSet_index + (seckillList?size)!}"></div>
          </#list>
        </#if>
      </div>
    </div>

    <div class="gotop"><i class="glyphicon glyphicon-triangle-top"></i><span>回到顶部</span></div>
  </div>
</@base>

<div class="fs-ad" style="display:none;">
  <img src="">
  <span class="close-fs-ad">关闭</span>
</div>

<div class="modal fade miaosha-modal" id="miaoshaModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:300px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">回答问题</h4>
        <input type="hidden" name="data_id" value="">
      </div>
      <div class="modal-body">
        <div id="login-section">
          <form id="login-form">
            <div class="form-group">
              <label for="answer" class="control-label answer-lab"></label>
              <input type="text" class="form-control" id="answer">
            </div>
            <div class="form-group">
              <button type="button" class="domiaosha btn btn-danger btn-block">确&nbsp;&nbsp;认</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>