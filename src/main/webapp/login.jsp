<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>登录页面 - 电子协会管理系统</title>
    <link href="css/default.css" rel="stylesheet" type="text/css"/>
    <!--必要样式-->
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
    <link href="css/demo.css" rel="stylesheet" type="text/css"/>
    <link href="css/loaders.css" rel="stylesheet" type="text/css"/>
    b
</head>
<body>
<div>
    <div class='login'>
        <div class='login_title'>
            <span>账号登录</span>
        </div>
        <div class='login_fields'>
            <div class='login_fields__user'>
                <div class='icon'>
                    <img alt="" src='img/user_icon_copy.png'>
                </div>
                <input name="username" id="username" placeholder='用户名' maxlength="16" type='text' autocomplete="off"/>
                <div class='validation'>
                    <img alt="" src='img/tick.png'>
                </div>
            </div>
            <div class='login_fields__password'>
                <div class='icon'>
                    <img alt="" src='img/lock_icon_copy.png'>
                </div>
                <input name="password" id="password" placeholder='密码' maxlength="16" type='text' autocomplete="off">
                <div class='validation'>
                    <img alt="" src='img/tick.png'>
                </div>
            </div>
            <div class='login_fields__password'>
                <div class='icon'>
                    <img alt="" src='img/key.png'>
                </div>
                <input name="code" placeholder='验证码' maxlength="4" type='text' name="ValidateNum" autocomplete="off">
                <div class='validation' style="opacity: 1; right: -5px;top: -3px;">
                    <canvas class="J_codeimg" id="myCanvas" onclick="Code();">对不起，您的浏览器不支持，请更换浏览器!</canvas>
                </div>
            </div>
            <div class='login_fields__submit'>
                <input type='button' value='登录'>
            </div>
        </div>
        <div class='success'>
        </div>
        <div class='disclaimer'>
            <p>欢迎登陆电子协会后台管理系统</p>
        </div>
    </div>
    <div class='authent'>
        <div class="loader" style="height: 44px;width: 44px;margin-left: 28px;">
            <div class="loader-inner ball-clip-rotate-multiple">
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
        <p>认证中...</p>
    </div>
</div>
<div class="OverWindows"></div>
<link href="layui/css/layui.css" rel="stylesheet" type="text/css"/>
<script src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src='js/stopExecutionOnTimeout.js?t=1'></script>
<script src="layui/layui.js" type="text/javascript"></script>
<script src="js/Particleground.js" type="text/javascript"></script>
<script src="js/Treatment.js" type="text/javascript"></script>
<script src="js/jquery.mockjax.js" type="text/javascript"></script>
<script type="text/javascript">

    var canGetCookie = 0;//是否支持存储Cookie 0 不支持 1 支持
    var ajaxmockjax = 0;//是否启用虚拟Ajax的请求响 0 不启用  1 启用
    //默认账号密码

    var truelogin = "admin";
    var truepassword = "admin";

    var CodeVal = 0;
    Code();

    function Code() {
        if (canGetCookie == 1) {
            createCode("AdminCode");
            var AdminCode = getCookieValue("AdminCode");
            showCheck(AdminCode);
        } else {
            showCheck(createCode(""));
        }
    }

    function showCheck(a) {
        CodeVal = a;
        var c = document.getElementById("myCanvas");
        var ctx = c.getContext("2d");
        ctx.clearRect(0, 0, 1000, 1000);
        ctx.font = "80px 'Hiragino Sans GB'";
        ctx.fillStyle = "#E8DFE8";
        ctx.fillText(a, 0, 100);
    }

    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            $('input[type="button"]').click();
        }
    });
    //粒子背景特效
    $('body').particleground({
        dotColor: '#E8DFE8',
        lineColor: '#133b88'
    });
    $('input[name="password"]').focus(function () {
        $(this).attr('type', 'password');
    });
    $('input[type="text"]').focus(function () {
        $(this).prev().animate({'opacity': '1'}, 200);
    });
    $('input[type="text"],input[type="password"]').blur(function () {
        $(this).prev().animate({'opacity': '.5'}, 200);
    });
    $('input[name="login"],input[name="password"]').keyup(function () {
        var Len = $(this).val().length;
        if (!$(this).val() == '' && Len >= 5) {
            $(this).next().animate({
                'opacity': '1',
                'right': '30'
            }, 200);
        } else {
            $(this).next().animate({
                'opacity': '0',
                'right': '20'
            }, 200);
        }
    });
    layui.use('layer', function () {
        //非空验证
        $('input[type="button"]').click(function () {
            var login = $('input[name="username"]').val();
            var password = $('input[name="password"]').val();
            var code = $('input[name="code"]').val();
            if (login == '') {
                ErroAlert('请输入您的账号');
            } else if (password == '') {
                ErroAlert('请输入密码');
            } else if (code == '') {
                ErroAlert('输入验证码');
            } else if (code.length != 4) {
                ErroAlert('请补全验证码');
            }else if (code.toUpperCase() != CodeVal.toUpperCase()) {
                ErroAlert('验证码错误！');
            } else {
                //认证中..
                $('.login').addClass('test'); //倾斜特效
                setTimeout(function () {
                    $('.login').addClass('testtwo'); //平移特效
                }, 300);
                setTimeout(function () {
                    $('.authent').show().animate({right: -320}, {
                        easing: 'easeOutQuint',
                        duration: 600,
                        queue: false
                    });
                    $('.authent').animate({opacity: 1}, {
                        duration: 200,
                        queue: false
                    }).addClass('visible');
                }, 500);
                //ErroAlert
                //登陆
                var jsonData = {
                    "username": login,
                    "password": password
                };
                //此处做为ajax内部判断
                var url = "login";
                var msg = 1;

                AjaxPost(url, jsonData,
                    function () {
                        $.ajax({
                            url: url,
                            type: "POST",
                            cache: false,
                            dataType: "json"
                        });
                    },
                    function (data) {
                        //ajax返回
                        //认证完成
                        setTimeout(function () {
                            $('.authent').show().animate({right: 90}, {
                                easing: 'easeOutQuint',
                                duration: 600,
                                queue: false
                            });
                            $('.authent').animate({opacity: 0}, {
                                duration: 200,
                                queue: false
                            }).addClass('visible');
                            $('.login').removeClass('testtwo'); //平移特效
                        }, 2000);
                        setTimeout(function () {
                            $('.authent').hide();
                            $('.login').removeClass('test');
                            if (data == 0) {
                                //登录成功
                                $('.login div').fadeOut(100);
                                $('.success').fadeIn(1000);
                                $('.success').html("登录成功！");
                                //跳转操作
                                window.location.href = 'main';
                            }else {
                                AjaxErro({"Status": "Erro", "Erro": "账号名或密码有误"});
                            }
                        }, 2400);
                    })
               }
           })
        })
</script>
</body>
</html>
