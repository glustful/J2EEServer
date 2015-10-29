<#include "/common/base.ftl">

<@base title="收银台">
  <div class="container bd-content cart-content main-container" style="margin-bottom:68px;width:500px;margin-left:auto;margin-right:auto;">
    <div style="margin-top:50px;">
        <p style="color:#333333;font-size:16px;">本次应付 <span style="color:#ff4200;">98.00元</span> 请输入支付密码</p>
    </div>

    <div style="margin-top:20px;">
        <input placeholder="请输入支付密码" type="password" class="form-control" style="height:50px;line-height:50px;border-radius:0;border-color:#e9e9da;">
    </div>

    <div style="margin-top:30px;">
        <a href="" class="btn btn-primary btn-lg" style="background-color:#ff4200;border:1px solid #ff4200;border-radius:0;margin-right:20px;">立即支付</a>
        <a href="" class="btn btn-default btn-lg" style="border:1px solid #e1e2d3;border-radius:0;">取消</a>
    </div>
  </div>
</@base>