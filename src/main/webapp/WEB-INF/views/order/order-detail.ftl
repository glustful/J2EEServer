			<div class="row <#if order.commentStatus?? && order.commentStatus == "00">with-comment</#if>">
              <div class="col col-md-6">
                <div class="row">
                  <div class="col-md-4" style="width: 130px;">
                    <img class="cover" src="/cimg${(orderDetail.logo)!}">
                  </div>
                  <div class="col-md-8">
                    <p class="name">${(orderDetail.typeName)!}</p>
                    <p class="foods">${(orderDetail.setTitle)!}</p>
                    <p class="price">单价：${(orderDetail.price?string(",##0.00"))!}元</p>
                  </div>
                </div>
                
                <#if order.commentStatus?? && order.commentStatus == "00">
                <div class="row comment-txt">
                  <input class="form-control j-comment-text" data-detail="${(orderDetail.id)!}" data-id="${(order.id)!}" type="text" placeholder="请说说你的用餐感受">
                </div>
                </#if>
              </div>

              <div class="col col-md-3 action">
                <p class="lb">数量</p>
                <p class="cnt">x${(orderDetail.count)!}</p>
              </div>

              <div class="col col-md-3 price-block">
                <p class="lb">价格</p>
                <p class="item-price">${(orderDetail.amount?string(",##0.00"))!} 元</p>
              </div>
            </div>