<#include "/common/base.ftl">

<@base title="客户端下载">
  <div class="container bd-content main-container" style="padding-top:138px;">
    <div class="row">
      <div class="col col-md-8">
        <div>
          <img src="/jjbstatic/img/client1.png" style="height:580px;">
        </div>
        <div style="margin-top:25px;">
          <span style="font-size:18px;line-height: 28px;vertical-align: top;display: inline-block;">一键安装：</span>

          <img src="/jjbstatic/img/client_s3.png" style="margin-right:20px;">
          <img src="/jjbstatic/img/client_s4.png" style="margin-right:20px;">
          <img src="/jjbstatic/img/client_s2.png" style="margin-right:20px;">
          <img src="/jjbstatic/img/client_s1.png">
        </div>
      </div>
      <div class="col col-md-4" style="text-align:center;">
        <div>
          <p><img src="/jjbstatic/img/ios.png" style="width:160px;"></p>
          <p style="font-size:16px;">扫码下载，<span style="font-weight:bold;">更方便</span></p>
          <p><a href="${iosUrl!}" target="_blank" class="btn btn-danger" style="background:#ff4000;border-color:#ff4000;padding:8px 18px;">苹果客户端下载</a></p>
        </div>

        <div style="margin-top:68px;">
          <p><img src="/jjbstatic/img/android.png" style="width:160px;"></p>
          <p style="font-size:16px;">扫码下载，<span style="font-weight:bold;">更方便</span></p>
          <p><a href="${androidUrl!}" target="_blank" class="btn btn-success" style="background:#22ac38;border-color:22ac38;padding:8px 22px;">安卓客户端下载</a></p>
        </div>
      </div>
    </div>
  </div>
</@base>