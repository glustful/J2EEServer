var serverPath = "";

var _options = {
    element: 'body',
    position: null,
    type: 'danger',
    allow_dismiss: true,
    newest_on_top: false,
    placement: {
        from: 'top',
        align: 'right'
    },
    offset: {x: 20, y: 60},
    spacing: 10,
    z_index: 9000,
    delay: 1000,
    timer: 1000,
    url_target: '_blank',
    mouse_over: null,
    animate: {
        enter: 'animated fadeInDown',
        exit: 'animated fadeOutUp'
    },
    onShow: null,
    onShown: null,
    onClose: null,
    onClosed: null,
    icon_type: 'class',
    template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
        '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
        '<span data-notify="icon"></span> ' +
        '<span data-notify="title">{1}</span> ' +
        '<span data-notify="message">{2}</span>' +
        '<a href="{3}" target="{4}" data-notify="url"></a>' +
    '</div>'
}

var getOptions = function(opt) {
    return $.extend(_options, opt);
};

$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
    if (jqxhr.status === 405) {
        $('#loginModal').modal();
    }
});

$.fn.countdown = function (callback, duration) {
    var container = $(this[0]).html(duration);
    var countdown = setInterval(function () {
        if (--duration) {
            container.html(duration);
        } else {
            clearInterval(countdown);
            callback.call(container);
        }
    }, 1000);
};

/** trim() method for String */
String.prototype.trim=function() {
    return this.replace(/(^\s*)|(\s*$)/g,'');
};

$(function() {
    var isLastRegion = false;
    var intervalId = {};
    var _miao_wait = false;

    var _interval = function(duration, $obj) {
        var d = duration;
        intervalId[$obj.data('id')] = setInterval(function() {
            d = moment.duration(d - 1000);

            if (!d.hours() && !d.minutes() && !d.seconds()) {
                clearInterval(intervalId[$obj.data('id')]);
                if (_miao_wait) {
                    $obj.find('.lab').text('后结束');
                    _miao_wait = false;
                    _interval(moment.duration($obj.find('[name="endTime"]').val() - Date.now()), $obj);
                    $obj.find('.miaosha-now').attr('disabled', false);
                } else {
                    $obj.find('.duration').text('00:00:)0');
                    $obj.find('.timer .t1').html('活动已结束');
                    $obj.find('.miaosha-now').attr('disabled', true);
                }
            } else {
                var txt = d.hours() + ':' + d.minutes() + ':' + d.seconds();
                $obj.find('.duration').text(txt);
            }
        }, 1000);
    };

    if ($('.miaosha-item').length) {
        $.each($('.miaosha-item'), function(i, v) {
            var $item = $(v);
            var start = $item.find('[name="startTime"]').val(),
                end = $item.find('[name="endTime"]').val(),
                current = $item.find('[name="currentTime"]').val();

            if (end > current && current > start) {
                _miao_wait = false;
                _interval(moment.duration(end - current), $item);
                $item.find('.lab').text('后结束');
                $item.find('.miaosha-now').attr('disabled', false);
            } else if (end < current) {
                $item.find('.miaosha-now').attr('disabled', true);
                $item.find('.timer .t1').html('活动已结束');
            } else if (start > current) {
                _miao_wait = true;
                _interval(moment.duration(start - current), $item);
                $item.find('.lab').text('后开始');
                $item.find('.miaosha-now').attr('disabled', true);
            }
        });
    }

    $('.gotop').on('click', function() {
        $('html, body').animate({scrollTop: 0});
    });

    $('.close-fs-ad').on('click', function() {
        $('.fs-ad').hide();
    });

    if ($('.fs-ad').length) {
        $.ajax({
            url: '/advertisement'
        }).done(function(data) {
            if (typeof data.fill !== 'undefined') {
                var fillAd = store.get('fillAd_'+data.fill.id);

                var isNextDay = false;
                if(fillAd) {
                	var fillDay = new Date(fillAd*1000).getDay();
                    var today = new Date().getDay();
                    if(fillDay != today) {
                    	isNextDay = true;
                    }
                }

                if (!fillAd || ((Math.floor(Date.now() / 1000)) - fillAd) > 60*60*24*1 || isNextDay) {
                    $('.fs-ad').find('img').attr('src', '/uploaddir'+data.fill.imgWeb);
                    $('.fs-ad').find('img').click(function(){
                    	window.open(serverPath + "/advertisement/detail?id=" + data.fill.id);
                    });
                    store.set('fillAd_'+data.fill.id, Math.floor(Date.now() / 1000));
                    $('.fs-ad').show();
                }
            }
        });
    }

    $(window).scroll(function() {
        if ($(window).scrollTop() > 138) {
            $('.gotop').show();
        } else {
            $('.gotop').hide();
        }

        // clearTimeout($.data(this, 'scrollTimer'));
        // $.data(this, 'scrollTimer', setTimeout(function() {
        //     $('.right-nav').animate({top: $(window).scrollTop()+1}, 500, function() {
        //         var _top = $('.right-nav').position().top+($('.right-nav').height()/2);
        //         var $items = $('.food-item'),
        //             len = $items.length;

        //         for (var i=0; i<len; i++) {
        //             if ($($items[i]).position().top < _top && $($items[i+1]).length && _top < $($items[i+1]).position().top) {
        //                 $('.nav-item').removeClass('active');
        //                 $($('.nav-item')[i]).addClass('active');
        //                 break;
        //             } else {
        //                 $('.nav-item').removeClass('active');
        //                 $('.nav-item').last().addClass('active');
        //             }
        //         }
        //     });
        // }, 250));
    });

    $('.footer .qr').hover(function() {
        $(this).next().show();
    }, function() {
        $(this).next().hide();
    });

    var md,
        answerError;
    var _miao_sha = function(id, answer) {
        $('#miaoshaModal').find('[name="data_id"]').val(id);

        var params = '?id='+id;
        if (answer) {
            params += '&md='+md+'&answer='+answer;
        }
        $.ajax({
            url: '/seckill'+params,
            type: 'POST'
        }).done(function(data) {
            if (data.success) {
                if (data.success && data.code === 100) {
                    alert('秒杀成功');
                } else if (data.success && (data.code === 200 || data.code === 300)) {
                    md = data.data.md;
                    if (answerError) {
                        $('#miaoshaModal').find('.modal-title').text('回答错误');
                    }
                    $('#miaoshaModal').find('.answer-lab').text(data.data.questionWeb);
                    $('#miaoshaModal').modal();
                    answerError = 1;
                }
            } else {
                $('#miaoshaModal').modal('hide');
                alert(data.msg);
            }
        });
    };

    $('#miaoshaModal').on('hidden.bs.modal', function() {
        $(this).find('#answer').val('');
        answerError = 0;
        $('#miaoshaModal').find('.modal-title').text('回答问题');
    });

    $('.domiaosha').on('click', function() {
        _miao_sha($('#miaoshaModal').find('[name="data_id"]').val(), $('#miaoshaModal').find('#answer').val());
    });

    $('.miaosha-now').on('click', function() {
        var $me = $(this),
            $item = $me.parents('.miaosha-item'),
            dataId = $item.data('id');

        _miao_sha(dataId);
        // if ($scope.miaoshaData.answer) {
        //     params += '&md='+md+'&answer='+$scope.miaoshaData.answer;
        // }


        // DataListService.seckillApi(params, function(res) {
        //     if (res.success && res.code === 100) {
        //         $ionicPopup.alert({
        //             title: '提示',
        //             template: '秒杀成功'
        //         });
        //         $state.go('cart-order', {data: JSON.stringify(res.data)});
        //     } else if (res.success && (res.code === 200 || res.code === 300)) {
        //         md = res.data.md;
        //         if (answerError) {
        //             answerTitle = '回答错误'
        //         }
        //         $ionicPopup.show({
        //             template: '<div><p style="color:#333;font-size:15px;">'+res.data.questionPhone+'</p></div><div><input style="border:1px solid #ddd;" type="text" name="answer" ng-class="{required: required}" ng-model="miaoshaData.answer"></div>',
        //             title: answerTitle,
        //             scope: $scope,
        //             buttons: [
        //                 {
        //                     text: '取消',
        //                     onTap: function() {
        //                         $scope.required = false;
        //                         $scope.miaoshaData.answer = '';
        //                         md = '';
        //                         answerError = 0;
        //                         answerTitle = '请回答问题';
        //                     }
        //                 },
        //                 {
        //                     text: '<b>确认</b>',
        //                     type: 'button-positive',
        //                     onTap: function(e) {
        //                         if (!$scope.miaoshaData.answer) {
        //                             e.preventDefault();
        //                             $scope.required = true;
        //                         } else {
        //                             $scope.activityAction(data);
        //                             $scope.required = false;
        //                             $scope.miaoshaData.answer = '';
        //                         }
        //                     }
        //                 }
        //             ]
        //         });
        //         answerError = 1;
        //     }
        // });
    });

    $('.fancy-box').on('click', 'img', function() {
        var $me = $(this),
            img = $me.data('img');

        $('#fancyModal').find('.modal-body').html('<img src="'+img+'">');
        $('#fancyModal').modal();
    });

    $('.forget-password').on('click', function() {
        $('#login-section').hide();
        $('#forget-section').show();
    });
    $('.back-login').on('click', function() {
        $('#login-section').show();
        $('#forget-section').hide();
    });

    $('.go-reg-tow').on('click', function() {
        if ($('#register-form').valid()) {
            $('#reg-two').show();
            $('#reg-one').hide();

            $.ajax({
                url: '/address/region'
            }).done(function(res) {
                var html = '<option value="">请选择</option>';
                $.each(res, function(i, v) {
                    html += '<option value='+v.id+' data-type="'+v.type+'">'+v.name+'</option>';
                });

                $('#reg-two').find('.region-select-1').html(html);
            });
        }
    });
    $('#reg-two').on('change', '.region-select', function() {
        var $me = $(this),
            index = $me.data('index')+1;

        if ($me.val()) {
            $.ajax({
                url: '/address/region',
                data: {parent: $me.val()}
            }).done(function(res) {
                if (res.length) {
                    isLastRegion = false;
                } else {
                    isLastRegion = true;
                }

                $('.region-select-'+index+' option').remove();
                $('.region-select-'+index).append('<option value="">请选择</option>');
                $.each(res, function(i, v) {
                    $('.region-select-'+index).append('<option data-type="'+v.type+'" value="'+v.id+'">'+v.name+'</option>').show();
                });

                for (var i=index+1; i<=5; i++) {
                    $('.region-select-'+i).html('<option value="">请选择</option>');
                    if (i === 4 || i === 5) {
                        $('.region-select-'+i).hide();
                    }
                };
            });
        } else {
            for (var i=index; i<=5; i++) {
                $('.region-select-'+i).html('<option value="">请选择</option>');
                if (i === 4 || i === 5) {
                    $('.region-select-'+i).hide();
                }
            };
            isLastRegion = false;
        }
    });

    $('.send-forget-sms').on('click', function() {
        if ($.trim($('[name="user-forget"]').val()).length) {
            $.ajax({
                url: '/register/findpassmsg',
                type: 'POST',
                data: {'userName': $.trim($('[name="user-forget"]').val())}
            }).done(function(res) {
                if (res.success) {
                    $('.send-forget-sms').find('.cd').countdown(function() {
                        $('.send-forget-sms').attr('disabled', false);
                        $('.send-forget-sms').find('.cd').empty();
                    }, 60);
                    $.notify({
                        icon: 'glyphicon glyphicon-ok-circle',
                        message: '短信已经发送成，请查收'
                    }, getOptions({
                        type: 'success',
                        placement: {from: 'top', align: 'center'},
                        offset: {x: 20, y: 32},
                    }));
                } else {
                    alert(res.msg);
                    $('.send-forget-sms').attr('disabled', false);
                }
            }).fail(function() {
                $('.send-forget-sms').attr('disabled', false);
            });
        } else {
            $.notify({
                message: '请输入注册的手机号'
            }, getOptions({
                type: 'danger',
                placement: {from: 'top', align: 'center'},
                offset: {x: 20, y: 32},
            }));
        }
    });
    $('.find-forget-pwd').on('click', function() {
        var mobile = $.trim($('[name="user-forget"]').val()),
            verify = $.trim($('[name="find-pwd-verify"]').val()),
            newPwd = $.trim($('[name="new-pass"]').val());

        if (mobile.length && verify.length && newPwd.length) {
            $.ajax({
                url: '/register/findPassword',
                type: 'POST',
                data: {userName: mobile, verifyCode: verify, newPass: newPwd}
            }).done(function() {
                $.notify({
                    icon: 'glyphicon glyphicon-ok-circle',
                    message: '密码已经重置成功'
                }, getOptions({
                    type: 'success',
                    placement: {from: 'top', align: 'center'},
                    offset: {x: 20, y: 32},
                }));
                $('.back-login').trigger('click');
            });
        }
    });

    $('.agreement').on('change', function() {
        var $me = $(this);
        $('.doregister').attr('disabled', !$me.is(':checked'));
    });

    $('#pay-now-btn').on('click', function() {
    	var payPass = $("#pay-pwd").val(),
    	walletMoney = $("#walletMoney").val(),
    	orderId = $("#orderId").val();
    	if(payPass == '') {
    		alert('密码不能为空！');
    	}
    	$.ajax({
            url: '/recharge/pay',
            type: 'POST',
            data: {orderId: orderId, payPass: payPass, walletMoney: walletMoney}
        }).done(function(res) {
        	if(!res.success) {
        		alert(res.msg);
        	} else {
        		if(res.data.toPay <= 0) {
        			window.location.href = serverPath +"/pay?orderNo="+res.data.orderNo;
        		} else {
        			window.location.href = serverPath +"/pay/do?orderNo="+res.data.orderNo;
        		}
        	}
        	
        });
    	
    });

    	
    $('.nav-item').on('click', function() {
        var $me = $(this),
            index = $me.data('index');

        var rect = $($('.food-item')[index]).position();
        $('html, body').animate({scrollTop: rect.top+80});

        $('.nav-item').removeClass('active-0').removeClass('active-1').removeClass('active-2');
        $me.addClass('active-'+index);
    });
    $('.btn-math').on('click', function() {
        var $me = $(this),
            type = $me.data('type'),
            $item = $me.parents('.food-item'),
            $count = $item.find('.count'),
            count = parseInt($count.val());

        if (type === 'minus') {
            if (count > 1) {
                $count.val(count - 1);
            }
        } else if (type === 'plus') {
            $count.val(count + 1);
        }
    });
    $('.food-detail').on('click', function() {
        var $me = $(this),
            $parent = $me.parents('.food-item'),
            $itemDc = $parent.find('.item-dc');

        if ($itemDc.length && $itemDc.hasClass('detail-container')) {
            $itemDc.remove();
            $me.find('.glyphicon').removeClass('glyphicon-upload').addClass('glyphicon-download');
        } else {
            if ($itemDc.length) {
                $itemDc.remove();
            }
            var setid = $(this).data("setid");
            $.ajax({
                url: '/food/detail',
                data: {setid:setid}
            }).done(function(res) {
                $parent.append(res);
                $me.find('.glyphicon').removeClass('glyphicon-download').addClass('glyphicon-upload');
            });
        }
    });
    $('.food-comment').on('click', function() {
        var $me = $(this),
            $parent = $me.parents('.food-item'),
            $itemDc = $parent.find('.item-dc');

        $parent.find('.food-detail').find('.glyphicon').removeClass('glyphicon-upload').addClass('glyphicon-download');
        if ($itemDc.length && $itemDc.hasClass('comment-container')) {
            $itemDc.remove();
        } else {
            if ($itemDc.length) {
                $itemDc.remove();
            }
            var setid = $(this).data("setid");
            $.ajax({
                url: '/food/comment-list',
                data: {setid:setid}
            }).done(function(res) {
                $parent.append(res);
            });
        }
    });
    $('.cart-content').on('click', '.address-list', function() {
        var $me = $(this);

        $me.find('input[type="radio"]').prop('checked', true).trigger('change');
    });
    $('.cart-content').on('change', 'input[name="address_check"]', function() {
        var $me = $(this),
            $parent = $me.parents('.address-list');

        $('.address-list').removeClass('active');
        $parent.addClass('active');
        if ($me.is(':checked') && $me.hasClass('new-address')) {
            $('.create-address').show();
        } else {
            $('.create-address').hide();
        }
    });
    $('.cart-content').on('change', '.region-select', function() {
        var $me = $(this),
            index = $me.data('index')+1;

        if ($me.val()) {
            $.ajax({
                url: '/address/region',
                data: {parent: $me.val()}
            }).done(function(res) {
                if (res.length) {
                    isLastRegion = false;
                } else {
                    isLastRegion = true;
                }

                $('.region-select-'+index+' option').remove();
                $('.region-select-'+index).append('<option value="">请选择</option>');
                $.each(res, function(i, v) {
                    $('.region-select-'+index).append('<option data-type="' + v.type + '" value="'+v.id+'">'+v.name+'</option>').show();
                });

                for (var i=index+1; i<=5; i++) {
                    $('.region-select-'+i).html('<option value="">请选择</option>');
                    if (i === 4 || i === 5) {
                        $('.region-select-'+i).hide();
                    }
                };
            });
        } else {
            for (var i=index; i<=5; i++) {
                $('.region-select-'+i).html('<option value="">请选择</option>');
                if (i === 4 || i === 5) {
                    $('.region-select-'+i).hide();
                }
            };
            isLastRegion = false;
        }
    });

    $('.cart-content').on('blur', '.create-address .form-control', function() {
        if ($.trim($(this).val()).length) {
            $(this).removeClass('required');
        }

        if (isLastRegion) {
            $('.region-select').removeClass('required');
        }
    });

    $('.about-nav').on('click', function() {
        var $me = $(this),
            index = $me.data('index');

        $('.about-nav').removeClass('active');
        $me.addClass('active');
        $('.about-content .section').hide();
        $('#section'+index).show();
    });

    $('.profile-container').on('click', '.save-nickname', function() {
        var nickname = $("#user-nickname").val();
        $.ajax({
            url: serverPath + '/user/update',
            type: 'POST',
            data:{nickname:nickname}
        }).done(function(res) {
            if(res.success) {
                alert("修改成功！");
                window.location.href = serverPath +"/user/profile";
            } else {
                alert(res.msg);
            }
        });
    });

    $('.profile-container').on('click', '.save-pass', function() {
        var oldPassword = $("#oldPassword").val();
        var newPassword1 = $("#newPassword1").val();
        var newPassword2 = $("#newPassword2").val();
        if(oldPassword === '' || newPassword1 === '' || newPassword2 === '') {
            alert("新密码和旧密码都不能为空！");
            return;
        } else if(newPassword1 != newPassword2) {
            alert("2次新密码不同！");
            return;
        }

        $.ajax({
            url: serverPath + '/user/resetpwd',
            type: 'POST',
            data:{oldPwd:oldPassword, newPwd:newPassword1}
        }).done(function(res) {
            if(res.success) {
                alert("修改成功！");
                window.location.href = serverPath +"/user/profile";
            } else {
                alert(res.msg);
            }
        });
    });

    $('body').delegate('.order-now', 'click', function() {
        var set = $(this).data('set');
        var ss =  $('#cart-'+set).val();
        $.ajax({
            url: serverPath + '/cart/addcart',
            type: 'POST',
            data:{pcount:ss, set:set}
        }).done(function(res) {
            if(res.success) {
         	    // Detecting IE
                var oldIE;
                if ($('html').is('.ie6, .ie7, .ie8, .ie9')) {
                    oldIE = true;
                }
                if (oldIE) {
                    alert("成功加入购物车！");
                } else {
                	$('.cart-box').addClass('bounceIn animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
                        $(this).removeClass('bounceIn animated');
                        $.notify({
                            icon: 'glyphicon glyphicon-ok-circle',
                            message: '成功加入购物车'
                        }, getOptions({type: 'success'}));
                    });
                }

                if(!$('.cart-box').hasClass("active")){
                    $('.cart-box').addClass("active");
                }
                $(".cart-count").html(res.cartCount);
            } else {
                alert(res.msg);
            }
        });
    });

    $('body').delegate('.dologin', 'click', function() {
        $.ajax({
            url: serverPath + '/login/do',
            type: 'POST',
            data:$('#login-form').serialize(),// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                window.location.href = serverPath +"/food";
            } else {
                alert(res.msg);
            }
        });
    });

    $('body').delegate('.sendverify', 'click', function() {
        var mobile = $('#s-mobile').val();
        if($.trim(mobile) == '') {
            alert('请先输入手机号');
            return;
        }
        $.ajax({
            url: serverPath + '/register/sendSMS',
            type: 'POST',
            data: {userName: mobile},
            beforeSend: function() {
                $(".sendverify").attr('disabled', true);
            }
        }).done(function(res) {
            if (res.success) {
                $('.sendverify').find('.cd').countdown(function() {
                    $('.sendverify').attr('disabled', false);
                    $('.sendverify').find('.cd').empty();
                }, 60);
                $.notify({
                    icon: 'glyphicon glyphicon-ok-circle',
                    message: '短信已经发送成，请查收'
                }, getOptions({
                    type: 'success',
                    placement: {from: 'top', align: 'center'},
                    offset: {x: 20, y: 32},
                }));
            } else {
                alert(res.msg);
                $('.sendverify').attr('disabled', false);
            }
        }).fail(function() {
            $(".sendverify").attr('disabled', false);
        });
    });

    $('body').delegate('.doregister', 'click', function() {
        if ($('#address-form').valid() && isLastRegion) {
            var region = [];
            $.each($('#address-form .region-select'), function(i, v) {
                if (v.getBoundingClientRect().height) {
                    var selected = $(v).find('option:selected');
                    region.push({
                        id: selected.val(),
                        name: selected.text(),
                        type: selected.data('type')
                    });
                }
            });

            $.ajax({
                url: serverPath + '/register/do',
                type: 'POST',
                data: $('#register-form').serialize()+'&regionJson='+JSON.stringify(region)+'&name='+$('#address-form [name="name"]').val()+'&mobile='+$('#address-form [name="mobile"]').val()+'&floor='+$('#address-form [name="floor"]').val()+'&manualAddress='+$('#address-form [name="manualAddress"]').val()
            }).done(function(res) {
                if(res.success) {
                    alert("注册成功");
                    window.location.href = serverPath +"/food";
                } else {
                    alert(res.msg);
                }
            });
        }
    });

    var showAddressList = function() {
        $.ajax({
            url: serverPath + '/address/list',
            type: 'POST',
            data: {}// 要提交的表单
        }).done(function(res) {
            $("#address-container")[0].outerHTML = res;
        });
    };

    $("body").delegate('.change-address', 'click', function() {
        //$('#addressModal').modal();
        showAddressList();
    });

    $("body").delegate(".j-order-cancel", "click", function() {
        var id = $(this).data("id");
        $.ajax({
            url: serverPath + '/order/cancel',
            type: 'POST',
            data: {id:id}// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                window.location.href = serverPath +"/order/orderlist/50";
            } else {
                alert(res.msg);
            }
        });

        return false;
    });

    $("body").on("click", "#j-comment-more", function(){
        var pageNo = $(this).data("page");
        var set = $(this).data("set");

        $.ajax({
            url: serverPath + '/food/comment-list',
            type: 'POST',
            data: {pageNo:pageNo, setid:set}// 要提交的表单
        }).done(function(res) {
            $("#j-comment-more")[0].outerHTML = res;
        });

        return false;
    });

    $("body").delegate("#j-order-more", "click", function(){
        var pageNo = $(this).data("page");
        var status = $(this).data("status");

        $.ajax({
            url: serverPath + '/order/orderlist/' + status,
            type: 'POST',
            data: {pageNo:pageNo}// 要提交的表单
        }).done(function(res) {
            $("#j-order-more")[0].outerHTML = res;
        });

        return false;
    });
    
    $("body").delegate("#j-accountdetail-more", "click", function(){
        var pageNo = $(this).data("page");

        $.ajax({
            url: serverPath + '/recharge/detaillist',
            type: 'POST',
            data: {pageNo:pageNo}// 要提交的表单
        }).done(function(res) {
            $("#j-accountdetail-more")[0].outerHTML = res;
        });

        return false;
    });

    $('body').delegate('.j-submit-order', 'click', function() {
        if($("#addressId").length == 0 || $("#addressId").val() == "") {
            alert("请先保存收货人信息");
            return;
        }

        var param = "";
        var hasCart = false;
        $(".j-cart-item").each(function(){
            param += "carts=" + $(this).data("id") + "&";
            hasCart = true;
        });

        if(!hasCart) {
            alert("购物车内无套餐，快去选购吧!");
            window.location="/";
            return;
        };

        param += "address=" + $("#addressId").val();

        $.ajax({
            url: serverPath + '/order/submitorder',
            type: 'POST',
            data: param// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                window.location.href = serverPath +"/pay?from=0&orderNo=" + res.data.orderNo;
            } else {
                alert(res.msg);
            }
        });
    });

    $('body').delegate('.pay-btn', 'click', function() {
        $.ajax({
            url: serverPath + '/pay',
            type: 'POST',
            data:$('#cart-form').serialize(),// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                alert("保存成功");
                window.location.href = serverPath +"/food";
            } else {
                alert(res.msg);
            }
        });
    });

    $('body').delegate('.pay-now', 'click', function() {
        //提交支付宝支付表单请求
        $("#pay-form").submit();
    });

    var refreshCartInfo = function() {
    	$(".cart-count").html($(".total-count").html());
//        $.ajax({
//            url: serverPath + '/cart/cartinfo',
//            type: 'POST',
//            data: {}
//        }).done(function(res) {
//            if(res.success) {
//                if(!res.data) {
//                    window.location = "/cart";
//                    return;
//                }
//                $(".total-count").html(res.data.cnt);
//                $(".total-price").html(res.data.amount);
//                $(".cart-count").html(res.data.cnt);
//            }
//        });
    };

    $("body").delegate(".j-address-setdefault", "click", function(){
        $.ajax({
            url: serverPath + '/address/setdefault',
            type: 'POST',
            data:"id=" + $(this).data("id")// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                showAddressList();
            } else {
                alert(res.msg);
            }
        });

        return false;
    });

    $("body").delegate(".j-address-remove", "click", function(){
        $.ajax({
            url: serverPath + '/address/remove',
            type: 'POST',
            data:"id=" + $(this).data("id")// 要提交的表单
        }).done(function(res) {
            if(res.success) {
                showAddressList();
            } else {
                alert(res.msg);
            }
        });

        return false;
    });

    var chooseAddress = function(id) {
        $.ajax({
            url: serverPath + '/address/getaddress',
            type: 'POST',
            data: {id:id}// 要提交的表单
        }).done(function(res) {
            $("#address-container")[0].outerHTML = res;
        });

        $.ajax({
            url: serverPath + '/cart/refresh',
            type: 'POST',
            data: {address:id}
        }).done(function(res) {
        	if($.trim(res) == '') {
        		window.location = serverPath + "/cart";
        		return;
        	}
            $("#j-cart-content")[0].outerHTML = res;
            refreshCartInfo();
        });
    };

    $("body").delegate(".j-save-address", "click", function(){
        var chk = $("input[name='address_check']:checked");
        if(chk.length == 0) {
            alert("请选择一个地址");
            return false;
        }

        var id = chk.data("id");

        if(id == -1) {
            var isValid = true;
            if (!isLastRegion) {
                $('.region-select').addClass('required');
                isValid = false;
            }
            if (!$.trim($('[name="floor"]').val()).length) {
                $('[name="floor"]').addClass('required');
                isValid = false;
            }

            var r = /^[0-9]*$/ ;
            if(!r.test($.trim($('[name="floor"]').val()))) {
                $('[name="floor"]').addClass('required');
                isValid = false;
            }
            if (!$.trim($('[name="manualAddress"]').val()).length) {
                $('[name="manualAddress"]').addClass('required');
                isValid = false;
            }
            if (!$.trim($('[name="name"]').val()).length) {
                $('[name="name"]').addClass('required');
                isValid = false;
            }
            if (!$.trim($('[name="mobile"]').val()).length || $.trim($('[name="mobile"]').val()).length > 11) {
                $('[name="mobile"]').addClass('required');
                isValid = false;
            }

            if(!r.test($.trim($('[name="mobile"]').val()))) {
                $('[name="mobile"]').addClass('required');
                isValid = false;
            }

            if (!isValid) {
                alert("请正确填写地址信息");
                return false;
            }

            var param = {floor : $.trim($('[name="floor"]').val()), manualAddress: $.trim($('[name="manualAddress"]').val()),
                    name: $.trim($('[name="name"]').val()), mobile: $.trim($('[name="mobile"]').val())};
            if($(".j-status:checked").length > 0) {
                param.status = "20";
            }

            var reglist = [];
            $('.region-select').find("option:selected").each(function(){
                reglist.push({id: $(this).val(), name:$(this).html(), type: $(this).data("type")});
            });

            var regionJson = JSON.stringify(reglist);
            param.regionJson = regionJson;

            $.ajax({
                url: serverPath + '/address/save',
                type: 'POST',
                data: param// 要提交的表单
            }).done(function(res) {
                if(res.success && res.data) {
                    id = res.extend;
                    chooseAddress(id);
                } else {
                    alert(res.msg);
                }
            });
            return;
        }

        chooseAddress(id);
    });

    $("body").delegate(".j-cart-min", "click", function(){
        var next = $(this).next();
        var cnt = next.html();
        if(cnt == 0) {
            return;
        }

        cnt--;

        var param = {pcount:cnt, set: $(this).data("set"), cartId:$(this).data("id")};

        if($("#addressId").val() != '') {
        	param["address"] = $("#addressId").val();
        }

        $.ajax({
            url: serverPath + '/cart/setcart',
            type: 'POST',
            data: param
        }).done(function(res) {
        	if($.trim(res) == '') {
        		window.location = serverPath + "/cart";
        		return;
        	}
            $("#j-cart-content")[0].outerHTML = res;
            refreshCartInfo();
        });
    });

    $("body").delegate(".j-cart-add", "click", function(){
        var prev = $(this).prev();
        var cnt = prev.html();
        if(cnt == 500) {
            return;
        }

        cnt++;

        var param = {pcount:cnt, set: $(this).data("set"), cartId:$(this).data("id")};
        if($("#addressId").val() != '') {
        	param["address"] = $("#addressId").val();
        }

        $.ajax({
            url: serverPath + '/cart/setcart',
            type: 'POST',
            data: param
        }).done(function(res) {
        	if($.trim(res) == '') {
        		window.location = serverPath + "/cart";
        		return;
        	}
            $("#j-cart-content")[0].outerHTML = res;
            refreshCartInfo();
        });
    });

    $("body").on("click", ".j-btn-comment", function(){
        var orderId = $(this).data("id");
        var cmtText = $(".j-comment-text[data-id='" + orderId + "']");
        if(!cmtText.length) {
            return false;
        }

        var param = "";
        cmtText.each(function(){
            if(!$(this).val().trim().length) {
                return;
            }
            param += "orderDetail=" + $(this).data("detail") + "&comment=" + encodeURI($(this).val()) + "&";
        });

        if(!param.length) {
            alert("请填写评论");
            return false;
        }

        $.ajax({
            url: serverPath + '/order/comment',
            type: 'POST',
            data: param
        }).done(function(res) {
            if(res) {
                window.location = serverPath + "/order/orderlist/70";
            }
        });
        return false;
    });

    $("body").on("click", "#j-pay-now", function(){
        $("#payModal").modal();
    });
});

