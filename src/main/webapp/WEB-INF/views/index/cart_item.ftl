<#if cart??>
			<div class="cart-item row j-cart-item" id="cart_${(cart.id)!}" data-id="${(cart.id)!}">
		        <div class="col col-md-7">
		          <div class="row">
		            <div class="col-md-4" style="width: 130px;padding-left:0;">
		              <img class="cover" src="/cimg${(cart.foodSet.logo)!}">
		            </div>
		            <div class="col-md-8" style="padding-top:5px;padding-left:0;">
		              <p class="name">${(cart.typeName)!}</p>
		              <p class="foods">${(cart.foodSet.name)!}</p>
		              <p class="price">单价：${(cart.foodSet.price)!} 元</p>
		            </div>
		          </div>
		        </div>

		        <div class="col col-md-2 action">
		          <p class="lb">数量</p>
		          <div>
		            <span class="btn j-cart-min" data-id="${(cart.id)!}" data-set="${(cart.foodSet.id)!}">-</span>
		            <span class="count j-count">${(cart.count)!}</span>
		            <span class="btn j-cart-add" data-id="${(cart.id)!}" data-set="${(cart.foodSet.id)!}">+</span>
		          </div>
		        </div>

		        <div class="col col-md-3 price-block">
		          <p class="lb">价格</p>
		          <p class="item-price">
		            <span class="ip">${(cart.cartPrice?string(",###0.00"))!}</span>
		            <span>元</span>
		          </p>
		        </div>

		      </div>
</#if>