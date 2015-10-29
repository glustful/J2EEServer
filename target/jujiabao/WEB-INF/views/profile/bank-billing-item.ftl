  <#if dataList?? && dataList?size gt 0>
  	<#list dataList as accountDetail>
  		 <div class="billing-item">
	        <div class="ft">
	          <span>${(accountDetail.balanceTypeName)!}</span>
	          <span>${(accountDetail.amount?c)!}</span>
	        </div>
	        <div class="se">
	          <span>充值时间：${(accountDetail.tradeTimeStr)!}</span>
	          <span>余额类型：${(accountDetail.creditFlagName)!}</span>
	          <span>收支类型：${(accountDetail.tradeTypeName)!}</span>
	          <#if (accountDetail.dataNo)??>
	          		<span>订单号：${(accountDetail.dataNo)!}</span>
	          </#if>
	        </div>
	      </div>
  	</#list>
    <#if haveMore?? && haveMore>
		<div class="row" id="j-accountdetail-more" data-page="${(pageNo)!1}" style="padding-top:20px;text-align:center;font-size:16px;cursor:pointer;">
		  加载更多
		</div>
   </#if>
   <#else>
  	<div style="padding:10px;">暂无记录</div>
  </#if>
