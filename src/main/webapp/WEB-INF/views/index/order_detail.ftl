<#if order?? && order.detailList??>
<#list order.detailList as detail>
	<div class="cart-item row">
        <div class="col col-md-6">
          <div class="row">
            <div class="col-md-4" style="width: 130px;padding-left:0;">
              <img class="cover" src="/cimg${(detail.logo)!}">
            </div>
            <div class="col-md-8" style="padding-top:5px;padding-left:0;">
              <p class="name">${(detail.typeName)!}</p>
              <p class="foods">${(detail.setTitle)!}</p>
              <p class="price">单价：${(detail.price?string(",###.00"))!} 元</p>
            </div>
          </div>
        </div>

        <div class="col col-md-3 action">
          <p class="lb">数量</p>
          <div>
            <span class="count j-count">${(detail.count)!}</span>
          </div>
        </div>

        <div class="col col-md-3 price-block">
          <p class="lb">价格</p>
          <p class="item-price">
            <span class="ip">${(detail.amount?string(",###.00"))!}</span>
            <span>元</span>
          </p>
        </div>
      </div>
</#list>
</#if>