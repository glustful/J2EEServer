<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>居家宝微信充值</title>
    <meta name="viewport" content="target-densitydpi=device-dpi,width=640,user-scalable=no">
    <style>
        *{
            font-family: "microsoft yahei";
            font-size: 10px;
        }
        body{
            margin:0 auto;
        }
        .text{
            text-align: center;
        }
        .text p{
            font-size: 2.6em;
        }
        .text button{
            /* position: absolute; */
            /* bottom: 30px; */
            width: 200px;
            /* right: 32px; */
            background-color: #ff4200;
            color: white;
            border-color: #ff4200;
            border-radius: 11px;
            font-size: 3em;
            height: 100px;
        }
    </style>
</head>
<body>
<div class="text">
    <p>请使用以下支付方式进行支付</p>
    <button onclick="payNow()">使用微信支付</button>
</div>
<script type="text/javascript">
    var payNow=function(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId" : '${appId}',     //公众号名称，由商户传入
                    "timeStamp": '${timeStamp}',         //时间戳，自1970年以来的秒数
                    "nonceStr" :  '${nonceStr}', //随机串
                    "package" :'${package}',
                    "signType" :'${signType}',         //微信签名方式:
                    "paySign" :'${paySign}' //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        window.location.href="/wx/content.html?#/recharge/index";
                    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    else{
                        alert(res.err_msg);
                        window.location.href="/wx/content.html?#/recharge/index";
                    }
                }
        );
    }
</script>
</body>
</html>