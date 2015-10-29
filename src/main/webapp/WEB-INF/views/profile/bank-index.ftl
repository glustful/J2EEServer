<#include "/common/base.ftl">

<@addJS ["plupload.full.min"] />
<@base title="充值中心">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container" style="padding-top:86px;">
    <div>
      <div style="background-color:white;height:168px;">
        <div class="row" style="margin:0;">
          <div class="col-md-6" style="padding:0;text-align:center;color:#ff4200;width:558px;">
            <p style="margin-top: 30px;margin-bottom: 0;">
              <span style="font-size:28px;margin-right:15px;">余额</span>
              <span style="font-size:68px;">${(wallet.validAmount)!0}</span>
            </p>
            <p style="margin-top: -12px;margin-bottom: 0;color:#666;font-size:18px;">
              <span style="">冻结金额：${(wallet.frozenAmount)!0}</span>
            </p>
          </div>
          <div class="col-md-6" style="padding:0;color:#666;line-height:168px;">
            <span style="border-left:1px solid #666;margin-right:20px;"></span>
            <i class="glyphicon glyphicon-cog"></i>
            <a href="/recharge/detaillist" style="color:#666;">充值管理</a>
          </div>
        </div>
      </div>

      <div style="margin-top:48px;margin-left:138px;width:668px;">
        <p><span style="color:#ff4200;">乖宝，多充多优惠！</span>请选择充值方式</p>

        <div class="row">
          <#list setList as set>
          <a href="${serverPath}/pay/recharge?setId=${(set.id)!}" target="_blank">
          <div class="col-md-6 j-recharge-item">
            <div class="bank-item">
              <span class="flag"><i class="glyphicon glyphicon-yen"></i></span>
              <span class="txt in">${(set.name)!}</span>
            </div>
          </div>
          </a>
          </#list>
        </div>
      </div>
    </div>
  </div>
</@base>

<script type="text/javascript">
</script>