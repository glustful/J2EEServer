<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>支付确认</title>
    <script type="text/javascript" src="/jjbstatic/js/jquery-1.11.2.min.js"></script>
    <meta name="viewport" content="target-densitydpi=device-dpi,width=640,user-scalable=no">
    <style type="text/css">
        *{
            font-family: "microsoft yahei";
            font-size: 28px;
        }
        .p-l{
            padding-left: 100px;
        }
        .left{
            float: left;
        }
        .right{
            float: right;
        }
        .title{
            background: rgb(244,245,234);
            width: 640px;
            height: 100px;
            color: rgb(109,109,109);
            line-height: 100px;
            padding: 0 30px;
            box-sizing: border-box;
        }
        .main{
            width: 640px;
            height: 560px;
            background: white;
            border-bottom: 2px solid rgb(221,221,221);
            padding: 40px;
            box-sizing: border-box;
        }
        .menu{
            width: 640px;
            height: 105px;
            border-bottom: 2px solid rgb(221,221,221);
            line-height: 105px;
            box-sizing: border-box;
        }
        .spot{
            color: rgb(255,66,0);
        }
        span.icon{
            width: 60px;
            height: 60px;
            margin-right: 20px;
        }
        h1{
            font-size: 1.3em;
        }
        button{
            border-radius: 5px;
            margin-bottom: 20px;
            border: none;
        }
        button.fixed{
            width: 560px;
            height: 85px;
        }
        button.spot{
            color: white;
            background: rgb(255,66,0);
        }
        button.gray{
            background: rgb(244,245,234);
        }
        .payLogo{
            margin: 0 20px;
            width: 180px;
            padding-top: 8px;
        }
        .menu span{
            margin-right: 20px;
        }
        .mask{
            position: fixed;
            top: 0;
            width: 100%;
            height:100%;
            background: rgba(0,0,0,0.8);
        }
        .confirm{
            width: 400px;
            background: white;
            position: relative;
            top: 30%;
            margin: 0 auto;
            border: 1px solid gray;
            border-radius: 10px;
        }
        .top{
            width: 100%;
            height:140px;
            border-bottom:1px solid gray;
            text-align: center;
            padding-top: 30px;
            box-sizing: border-box;
        }
        .top input{
            width: 300px;
            height: 80px;
        }
        input::-webkit-input-placeholder{
            color: black;
        }
        .bottom{
            height: 140px;
        }
        .bottom button{
            width: 180px;
            border-radius: 5px;
            margin-left: 10px;
            margin-top: 20px;
            height: 80px;
        }
        .blue{
            background: rgb(55, 120, 239);
        }
        button.blue{
            color: white;
        }
    </style>
</head>
<body>
<div class="title">
    <div class="left">请选择支付方式</div>
    <div class="right">应付<span class="spot">${walletMoney}</span></div>
</div>
<div class="main">
    <p style="line-height: 0px;height: 60px;">
        <span class="icon">
            <img src="http://www.66jjb.com/wx/app/common/static/img/icon_new.png" style="width: 60px;height: 60px;">
        </span>
        余额支付
    </p>
    <h1 class="spot p-l">当前余额</h1>
    <h1 class="spot p-l">${validAmount}</h1>
    <p class="p-l">冻结金额 ${frozenAmount}</p>
    <button class="fixed fixed spot" onclick="showPwd()">使用乖宝钱包支付</button>
    <button class="fixed gray" onclick="recharge()">充值享受优惠</button>
</div>
<div class="menu" onclick="payNow()">
    <div class="payLogo left">
        <img src="http://www.66jjb.com/wx/app/common/static/img/wpay.png">
    </div>
    <div class="left">微信支付</div>
    <span class="right">></span>
</div>
<div class="mask" style="display: none;z-index: 10">
    <div class="confirm">
        <div class="top">
            <input type="password" placeholder="请输入钱包密码" id="txtPwd">
        </div>
        <div class="bottom">
            <button class="blue" onclick="payWithWallet()">确定</button>
            <button class="gray" onclick="hidePwd()">取消</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    var payNow = function () {
        $.ajax({
            type:'get',//可选get
            url:'/wcpay/canOrderWithTime?orderNo='+ '${orderNo}',
            dataType:'json',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
            success:function(data){
                if(data.res){
                    WeixinJSBridge.invoke(
                            'getBrandWCPayRequest', {
                                "appId": '${appId}',     //公众号名称，由商户传入
                                "timeStamp": '${timeStamp}',         //时间戳，自1970年以来的秒数
                                "nonceStr": '${nonceStr}', //随机串
                                "package": '${package}',
                                "signType": '${signType}',         //微信签名方式:
                                "paySign": '${paySign}' //微信签名
                            },
                            function (res) {
                                if (res.err_msg == "get_brand_wcpay_request:ok") {
                                    window.location.href = "/wx/content.html?#/user/myorder";
                                }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                                else {
                                    alert(res.err_msg);
                                    window.location.href = "/wx/content.html?#/user/myorder";
                                }
                            }
                    );
                }else{
                    alert('付款失败，失败原因：'+data.msg);
                }
            }
        })

    }
    var payWithWallet=function(){
        var orderId='${orderId}';
        var userId='${userId}';
        //var username1=document.getElementById("txtPwd");
        var walletMoney='${walletMoney}';
        //alert("data:"+orderId+" "+userId+" "+" ");
        var pass=$("#txtPwd").val()
        //alert("pass:"+pass);
        if(pass!=null){
            //document.getElementById('warn').innerText="正在提交……";
            $.ajax({
                type:'get',//可选get
                url:'/wcrecharge/walletPay?orderId='+orderId+'&payPass='+pass+'&walletMoney='+walletMoney+'&userId='+userId,
                dataType:'json',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
                success:function(data){
                    if(data.res){
                        alert('付款成功！');
                        window.location.href = "/wx/content.html?#/user/myorder";
                    }else{
                        alert('付款失败，失败原因：'+data.msg);
                    }

                }
            })
        }else{
            alert("密码为空！");
            document.getElementById('warn').innerText="提交失败！";
        }
    };
    var recharge=function(){
        window.location.href = "/wx/content.html?#/recharge/index";
    };
    var showPwd = function(){
        $(".mask").show();
    };
    var hidePwd = function(){
        $(".mask").hide();
    };
</script>
</body>
</html>