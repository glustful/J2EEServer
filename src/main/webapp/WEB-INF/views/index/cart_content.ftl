<#if cartCount?? && cartCount gt 0>
<div id="j-cart-content">
	<div style="margin-top:25px;">
      <#if discount??>
      <#list discount.discount as dis>
      <div class="discount">
        <span class="dis-item"><i class="">优惠</i> ${(dis.name)!}</span>
      </div>
      </#list>
      </#if>
      <#if dataList??>
        <#list dataList as cart>
          <#include "cart_item.ftl">
        </#list>
      </#if>
    </div>
    <div class="row" style="font-size:16px;margin-top:20px;">
      <div class="col-md-8" style="text-align:right;"></div>

      <div class="col-md-4" style="text-align:right;color:#ff4000;">
        <p>共 <span class="total-count">${cartCount!}</span> 份 / 合计：￥ <span class="total-price">${realSum?string(",##0.00")!}</span> 元</p>
        <#if disAmt??>
        	<p style="font-size:14px; color:#535353;">总额${sum?string(",##0.00")!}元 优惠${disAmt?string(",##0.00")!}元</p>
        </#if>
      </div>
    </div>
</div>
</#if>