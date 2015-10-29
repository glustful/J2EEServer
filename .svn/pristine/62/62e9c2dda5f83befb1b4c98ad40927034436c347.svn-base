<#include "/common/base.ftl">

<@base title="收银台">
  <div class="container bd-content cart-content main-container" style="margin-bottom:68px;width:700px;margin-left:auto;margin-right:auto;">
    <div>
        <p style="color:#333333;font-size:16px;">应付 <span style="color:#ff4200;">${(order.toPay?c)!}元</span> 请选择支付方式</p>
    </div>

    <div style="margin-bottom:30px;">
        <div class="row" style="background-color:white;padding:20px 40px;">
            <div class="col col-md-4" style="border-right:1px solid #e9e9da;width:168px;">
                <p><span style="background-color:#ffd637;display:inline-block;width:88px;height:88px;border-radius:44px;text-align:center;"><i class="glyphicon glyphicon-yen" style="font-size: 40px;color: #d9a600;display: inline-block;line-height: 88px;"></i></span></p>
                <p style="font-size:18px;padding-left:10px;padding-top:10px;">余额支付</p>
            </div>
            <div class="col col-md-8" style="padding-left:80px;">
                <div>
                    <span style="font-size:18px;margin-right:5px;">当前余额</span> <span style="color:#ff4200;font-size:68px;">${(order.wallet.validAmount?c)!0}</span>
                </div>
                <#if (order.wallet.validAmount)?? && (order.wallet.validAmount) gt 0>
                	<div>
                		<input type="hidden" id="walletMoney" value="${(order.walletMoney?c)!}" />
                		<input type="hidden" id="orderId" value="${(order.id)!}" />
	                    <a href="#paybankModal" data-toggle="modal" class="btn btn-primary btn-lg" style="border:0;background-color:#ff4200;width:100%;">立即支付</a>
	                </div>
                </#if>

                <div class="paybank-item disabled" style="margin-top:8px;">
                    <a href="${serverPath!}/recharge/bankindex" class="btn btn-primary btn-lg" style="border:0;background-color:#ff4200;width:100%;">充值享优惠</a>
                </div>
            </div>
        </div>
    </div>

   <!--
    <div class="paybank-item disabled" style="margin-bottom:30px;">
        <div class="row" style="background-color:white;padding:20px 40px;">
            <div class="col col-md-4" style="border-right:1px solid #e9e9da;width:168px;">
                <p><span style="background-color:#ffd637;display:inline-block;width:88px;height:88px;border-radius:44px;text-align:center;"><i class="glyphicon glyphicon-yen" style="font-size: 40px;color: #d9a600;display: inline-block;line-height: 88px;"></i></span></p>
                <p style="font-size:18px;padding-left:10px;padding-top:10px;">余额支付</p>
            </div>
            <div class="col col-md-8" style="padding-left:80px;">
                <div>
                    <span style="font-size:18px;margin-right:5px;">当前余额</span> <span style="color:#ff4200;font-size:68px;">0.00</span>
                </div>
            </div>
        </div>
        <div class="backdrop"></div>
    </div>
    -->

    <div>
        <div class="row" style="background-color:white;padding:20px 40px;">
            <div class="col col-md-4" style="width:38px;">
                <img style="width:80px;" src="/jjbstatic/img/zhifubao.png" alt="">
            </div>
            <div class="col col-md-8" style="padding-left:80px;">
                <a href="${serverPath}/pay/do?orderNo=${order.orderNo}"><p style="font-size:18px;margin:0;line-height:28px;">支付宝支付</p></a>
            </div>
        </div>
    </div>
  </div>
</@base>

<div class="modal fade ls-modal" id="paybankModal" tabindex="-1" role="dialog" aria-labelledby="paybankModalLabel"  aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">余额支付</h4>
      </div>
      <div class="modal-body">
        <div id="paybank-section">
        	 <form class="form-horizontal">
        	 <p style="color:#333333;font-size:16px;">本次应付 <span style="color:#ff4200;">${(order.walletMoney?c)!}元</span> 请输入支付密码</p>
            <div class="form-group input-group">

              <div class="col-sm-7">
               <input placeholder="请输入支付密码" type="password" class="form-control" id="pay-pwd" style="height:50px;line-height:50px;border-radius:0;border-color:#e9e9da;">
              </div>
            </div>
            <div class="form-group" style="margin-top: 20px;">
            	<a href="javascript:" class="btn btn-primary btn-lg" id="pay-now-btn" style="background-color:#ff4200;border:1px solid #ff4200;border-radius:0;margin-right:20px;">立即支付</a>
       			<a href="javascript:$('#paybankModal').hide();" class="btn btn-default btn-lg" style="border:1px solid #e1e2d3;border-radius:0;">取消</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
