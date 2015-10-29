<div class="siderbar">
  <ul>
    <li <#if activetap?? && activetap == 'bankindex'>class="active"</#if> >
      <a href="/recharge/bankindex">
        <i class="glyphicon glyphicon-yen"></i>
        <span>充值中心</span>
        <i class="glyphicon glyphicon-menu-right"></i>
      </a>
    </li>
    <li>
      <a href="/order/orderlist/10">
        <i class="glyphicon glyphicon-th-list"></i>
        <span>我的订单</span>
        <i class="glyphicon glyphicon-menu-right"></i>
      </a>
    </li>

    <li <#if activetap?? && activetap == 'profile'>class="active"</#if>>
      <a href="/user/profile">
        <i class="glyphicon glyphicon-user"></i>
        <span>个人资料</span>
        <i class="glyphicon glyphicon-menu-right"></i>
      </a>
    </li>
  </ul>
</div>