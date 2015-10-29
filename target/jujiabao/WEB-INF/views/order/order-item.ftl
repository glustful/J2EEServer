<#if dataList??>
<#list dataList as order>
     <div class="order-item">
        <div class="num row">
          <div class="col-md-6">订单号：${(order.orderNo)!}</div>
          <div class="col-md-6" style="text-align:right;">总价：&yen; ${(order.amount)!}元</div>
        </div>
    <div class="cart-item row" data-id="${(order.id)!}">
          <div class="col col-md-10 item-list">
            <#if order.detailList??>
            <#list order.detailList as orderDetail>
                 <#include "order-detail.ftl">
            </#list>
            </#if>
          </div>

          <div class="col col-md-2">
            <div class="btns <#if order.status == '10'>unpaid-btn</#if>">
              <div>
                <#if order.status == "10">
                  <div class="clearfix">
                    <a href="${serverPath}/pay?from=1&orderNo=${order.orderNo!}" class="btn btn-default btn-pay pull-left">付款</a>
                    <a href="javascript:void(0);" class="cancel-pay j-order-cancel pull-right" data-id="${(order.id)!}">取消订单</a>
                  </div>
                </#if>
                <#if order.commentStatus?? && order.commentStatus == "00">
                  <a href="javascript:void(0);" class="btn btn-default btn-pay j-btn-comment" data-id="${(order.id)!}">提交评论</a>
                </#if>
                <div class="status green"><span>${(order.statusName)!}</span></div>
              </div>
            </div>
          </div>
        </div>
    </div>
</#list>

<#if haveMore?? && haveMore>
<div class="row" id="j-order-more" data-page="${(pageNo)!1}" data-status="${(status)!}" style="text-align:center;font-size:16px;cursor:pointer;">
  加载更多
</div>
</#if>
</#if>