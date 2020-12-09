<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>电子协会管理系统</title>
    <link rel="icon" href="../favicon.ico" type="../image/ico">
    <meta name="keywords" content="黑龙江八一农垦大学电子协会管理系统">
    <meta name="description" content="黑龙江八一农垦大学电子协会管理系统">
    <meta name="author" content="byau">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/materialdesignicons.min.css" rel="stylesheet">
    <!--对话框-->
    <link rel="stylesheet" href="../js/jconfirm/jquery-confirm.min.css">
    <link href="../css/style.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/layui.css"  media="all">
</head>
<script type="text/javascript">

</script>
<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="${pageContext.request.contextPath}/index.jsp"><img src="../images/logo-sidebar.png" title="byau" alt="byau"/></a>
            </div>
            <div class="lyear-layout-sidebar-scroll">

                <nav class="sidebar-main">
                    <ul class="nav nav-drawer">
                        <li class="nav-item active"><a href="${pageContext.request.contextPath}/index.jsp"><i class="mdi mdi-home"></i> 首页</a></li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="mdi mdi-account"></i>协会成员信息管理</a>
                            <ul class="nav nav-subnav">
                                <li><a href="${pageContext.request.contextPath}/student/findByPage">查询所有成员</a></li>
                                <li><a href="${pageContext.request.contextPath}/student/findAll">条件查询</a></li>
                            </ul>
                        </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="mdi mdi-account-star"></i>指导教师信息管理</a>
                            <ul class="nav nav-subnav">
                                <li><a href="${pageContext.request.contextPath}/teacher/findByPage">指导教师列表</a></li>
                                <li><a href="${pageContext.request.contextPath}/teacher/findAll">筛选指导教师</a></li>
                            </ul>
                        </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="mdi mdi-guitar-acoustic"></i>活动信息管理</a>
                            <ul class="nav nav-subnav">
                                <li><a href="${pageContext.request.contextPath}/activity/findDoing">正在进行中</a></li>
                                <li><a href="${pageContext.request.contextPath}/activity/findAll">所有活动列表</a></li>
                                <li><a href="${pageContext.request.contextPath}/activity/activity_add.jsp">新建活动</a></li>
                            </ul>
                        </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="mdi mdi-memory"></i>元器件/材料信息管理</a>
                            <ul class="nav nav-subnav">
                                <li><a href="${pageContext.request.contextPath}/device/findAll">元器件查询</a></li>
                                <li><a href="${pageContext.request.contextPath}/device/device_add.jsp">添加元器件类型</a></li>
                                <li class="nav-item nav-item-has-subnav">
                                    <a href="#">元器件操作</a>
                                    <ul class="nav nav-subnav">
                                        <li><a href="${pageContext.request.contextPath}/devicelog/subdevice">元器件使用/分发</a></li>
                                        <li><a href="${pageContext.request.contextPath}/devicelog/adddevice">元器件入库</a></li>
                                        <li><a href="${pageContext.request.contextPath}/devicelog/findAll">元器件操作日志</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item nav-item-has-subnav">
                            <a href="javascript:void(0)"><i class="mdi mdi-flag-checkered"></i> 比赛信息管理</a>
                            <ul class="nav nav-subnav">
                                <li><a href="${pageContext.request.contextPath}/competition/findAll">比赛信息</a></li>
                                <li><a href="${pageContext.request.contextPath}/enjoyCompetition/findAll">参赛信息</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/errorInfo.jsp"><i class="mdi mdi-comment-remove-outline"></i> 错误信息反馈</a>
                        </li>
                    </ul>
                </nav>

                <div class="sidebar-footer">
                    <p class="copyright">Copyright</p>
                </div>
            </div>

        </aside>
        <!--End 左侧导航-->

        <!--头部信息-->
        <header class="lyear-layout-header">

            <nav class="navbar navbar-default">
                <div class="topbar">

                    <div class="topbar-left">
                        <div class="lyear-aside-toggler">
                            <span class="lyear-toggler-bar"></span>
                            <span class="lyear-toggler-bar"></span>
                            <span class="lyear-toggler-bar"></span>
                        </div>
                        <span class="navbar-page-title"> 指导教师信息管理 - 条件筛选 导入导出到Excel </span>
                    </div>

                    <ul class="topbar-right">
                        <li class="dropdown dropdown-profile">
                            <a href="javascript:void(0)" data-toggle="dropdown">
                                <span> <span class="caret"></span></span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/logout"><i class="mdi mdi-logout-variant"></i> 退出登录</a></li>
                            </ul>
                        </li>
                        <!--切换主题配色-->
                        <li class="dropdown dropdown-skin">
                            <span data-toggle="dropdown" class="icon-palette"><i class="mdi mdi-palette"></i></span>
                            <ul class="dropdown-menu dropdown-menu-right" data-stopPropagation="true">
                                <li class="drop-title"><p>主题</p></li>
                                <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="site_theme" value="default" id="site_theme_1" checked>
                    <label for="site_theme_1"></label>
                  </span>
                                    <span>
                    <input type="radio" name="site_theme" value="dark" id="site_theme_2">
                    <label for="site_theme_2"></label>
                  </span>
                                    <span>
                    <input type="radio" name="site_theme" value="translucent" id="site_theme_3">
                    <label for="site_theme_3"></label>
                  </span>
                                </li>
                                <li class="drop-title"><p>LOGO</p></li>
                                <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="logo_bg" value="default" id="logo_bg_1" checked>
                    <label for="logo_bg_1"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_2" id="logo_bg_2">
                    <label for="logo_bg_2"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_3" id="logo_bg_3">
                    <label for="logo_bg_3"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_4" id="logo_bg_4">
                    <label for="logo_bg_4"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_5" id="logo_bg_5">
                    <label for="logo_bg_5"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_6" id="logo_bg_6">
                    <label for="logo_bg_6"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_7" id="logo_bg_7">
                    <label for="logo_bg_7"></label>
                  </span>
                                    <span>
                    <input type="radio" name="logo_bg" value="color_8" id="logo_bg_8">
                    <label for="logo_bg_8"></label>
                  </span>
                                </li>
                                <li class="drop-title"><p>头部</p></li>
                                <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="header_bg" value="default" id="header_bg_1" checked>
                    <label for="header_bg_1"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_2" id="header_bg_2">
                    <label for="header_bg_2"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_3" id="header_bg_3">
                    <label for="header_bg_3"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_4" id="header_bg_4">
                    <label for="header_bg_4"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_5" id="header_bg_5">
                    <label for="header_bg_5"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_6" id="header_bg_6">
                    <label for="header_bg_6"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_7" id="header_bg_7">
                    <label for="header_bg_7"></label>
                  </span>
                                    <span>
                    <input type="radio" name="header_bg" value="color_8" id="header_bg_8">
                    <label for="header_bg_8"></label>
                  </span>
                                </li>
                                <li class="drop-title"><p>侧边栏</p></li>
                                <li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="sidebar_bg" value="default" id="sidebar_bg_1" checked>
                    <label for="sidebar_bg_1"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_2" id="sidebar_bg_2">
                    <label for="sidebar_bg_2"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_3" id="sidebar_bg_3">
                    <label for="sidebar_bg_3"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_4" id="sidebar_bg_4">
                    <label for="sidebar_bg_4"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_5" id="sidebar_bg_5">
                    <label for="sidebar_bg_5"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_6" id="sidebar_bg_6">
                    <label for="sidebar_bg_6"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_7" id="sidebar_bg_7">
                    <label for="sidebar_bg_7"></label>
                  </span>
                                    <span>
                    <input type="radio" name="sidebar_bg" value="color_8" id="sidebar_bg_8">
                    <label for="sidebar_bg_8"></label>
                  </span>
                                </li>
                            </ul>
                        </li>
                        <!--切换主题配色-->
                    </ul>

                </div>
            </nav>

        </header>
        <!--End 头部信息-->

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-toolbar clearfix">
                                <div class="toolbar-btn-action">
                                    <form class="form-horizontal" id="findForm" action="/teacher/findTeacherByEle" method="get"
                                          onsubmit="return false;">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">教师号</label>
                                            <div class="col-md-7">
                                                <input class="form-control" type="text" placeholder="教师号..."
                                                       name="tno">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">姓名</label>
                                            <div class="col-md-7">
                                                <input class="form-control" type="text" placeholder="姓名..."
                                                       name="tname">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">职位/职称</label>
                                            <div class="col-md-7">
                                                <input class="form-control" type="text" placeholder="职位/职称..."
                                                       name="tpos">
                                            </div>
                                        </div>

                                    </form>
                                    <div class="form-group">
                                        <div class="col-md-9 col-md-offset-3">
                                            <button class="btn btn-w-md btn-round btn-primary"
                                                    id="example-success-notify" href="/teacher/findTeacherByEle"
                                                    data-placement="top" title="筛选导出 模糊查询 部分项没有可不填">筛选
                                            </button>


                                            <button class="btn btn-w-md btn-round btn-info" data-placement="top"
                                                    data-toggle="modal" data-target=".bs-example-modal-sm"
                                                    title="如有筛选条件 则按筛选条件进行导出">导出
                                            </button>


                                            <button class="btn btn-w-md btn-round btn-warning" data-placement="top"
                                                    data-toggle="collapse" data-target="#collapseExample"
                                                    aria-expanded="false" aria-controls="collapseExample"
                                                    title="请按照导入模板进行导入">导入
                                            </button>
                                            <a class="btn btn-w-md btn-round btn-danger" data-placement="top" href="${pageContext.request.contextPath}/down/download.do?fileName=teacher">
                                                下载导入模板
                                            </a>
                                        </div>

                                    </div>
                                </div>

                            </div>
                            <div class="modal-dialog modal-sm" role="document">

                                <div class="collapse m-t-10" id="collapseExample">
                                    <form action="/teacher/import" method="post" enctype="multipart/form-data">
                                        <input type="file" name="excelFile">
                                        <input type="submit" value="上传">
                                    </form>
                                </div>
                            </div>
                            <form action="/teacher/export" id="exportForm">
                                <div id="modelexport" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
                                     aria-labelledby="mySmallModalLabel">
                                    <div class="modal-dialog modal-sm" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close"><span aria-hidden="true">×</span></button>
                                                <h4 class="modal-title" id="myLargeModalLabel">请选择要导出的属性</h4>
                                            </div>
                                            <div class="card-body">
                                                <div class="example-box">

                                                    <div class="form-group row m-b-10">
                                                        <div class="col-xs-6"> 教师号</div>
                                                        <div class="col-xs-6">
                                                            <label class="lyear-switch switch-solid switch-primary">
                                                                <input type="checkbox" checked="checked" value="tno"
                                                                       name="checkColumn">
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row m-b-10">
                                                        <div class="col-xs-6"> 姓名</div>
                                                        <div class="col-xs-6">
                                                            <label class="lyear-switch switch-solid switch-primary">
                                                                <input type="checkbox" checked="checked" value="tname"
                                                                       name="checkColumn">
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row m-b-10">
                                                        <div class="col-xs-6"> 职位</div>
                                                        <div class="col-xs-6">
                                                            <label class="lyear-switch switch-solid switch-primary">
                                                                <input type="checkbox" checked="checked" value="tpos"
                                                                       name="checkColumn">
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row m-b-10">
                                                        <div class="col-xs-6"> 手机号</div>
                                                        <div class="col-xs-6">
                                                            <label class="lyear-switch switch-solid switch-primary">
                                                                <input type="checkbox" checked="checked" value="ttel"
                                                                       name="checkColumn">
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row m-b-10">
                                                        <div class="col-xs-6"> qq账号</div>
                                                        <div class="col-xs-6">
                                                            <label class="lyear-switch switch-solid switch-primary">
                                                                <input type="checkbox" checked="checked" value="tqq"
                                                                       name="checkColumn">
                                                                <span></span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- end example-box -->
                                            </div>


                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                                                </button>
                                                <a id="btnsub" type="button" onclick="btnsub()"
                                                   class="btn btn-primary">确认</a>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </form>

                            <ul class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>教师号</th>
                                            <th>姓名</th>
                                            <th>职位</th>
                                            <th>手机号</th>
                                            <th>qq号</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${teacherlist}" var="teacher" varStatus="xh">
                                            <tr>
                                                <td>${xh.count}</td>
                                                <td>${teacher.tno}</td>
                                                <td>${teacher.tname}</td>
                                                <td>${teacher.tpos}</td>
                                                <td>${teacher.ttel}</td>
                                                <td>${teacher.tqq}</td>
                                                <td>
                                                    <div class="btn-group">
                                                        <a class="btn btn-xs btn-default"
                                                           href="/teacher/updateTeacher_page?tno=${teacher.tno}&inUpdatePage=excel"
                                                           title="编辑"
                                                           data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>
                                                        <a class="btn btn-xs btn-default"
                                                           onclick="ifdel(${teacher.tno})" title="删除"
                                                           data-toggle="tooltip"><i
                                                                class="mdi mdi-window-close"></i></a>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!--End 页面主要内容-->
    </div>
</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/perfect-scrollbar.min.js"></script>
<!--消息提示-->
<script src="../js/bootstrap-notify.min.js"></script>
<!--对话框-->
<script src="../js/jconfirm/jquery-confirm.min.js"></script>
<script type="text/javascript" src="../js/lightyear.js"></script>
<script type="text/javascript" src="../js/main.min.js"></script>
<!--对话框-->
<script src="../js/jconfirm/jquery-confirm.min.js"></script>

<script type="text/javascript">
    function ifdel(tno) {
        $.confirm({
            title: '提示：',
            content: '确定删除吗',
            type: 'orange',
            typeAnimated: true,
            buttons: {
                tryAgain: {
                    text: '确定',
                    btnClass: 'btn-orange',
                    action: function () {
                        location = "/teacher/deleteByTno_excel?tno=" + tno;
                    }
                },
                close: {
                    text: '取消'
                }
            }
        });
    }

    $('#example-success-notify').on('click', function () {
        lightyear.loading('show');
        // 假设ajax提交操作
        setTimeout(function () {
            lightyear.loading('hide');
            lightyear.notify('筛选成功，即将显示结果~', 'success', 800, 'mdi mdi-emoticon-happy');
            setTimeout(function () {
                var form = document.getElementById('findForm');
                //可在此修改input
                form.submit();
            }, 1300);
        }, 1e3)
    });
    $('#example-error-notify').on('click', function () {
        lightyear.loading('show');
        setTimeout(function () {
            lightyear.loading('hide');
            lightyear.notify('服务器错误，请稍后再试~', 'danger', 100);
        }, 1e3)
    });

    function btnsub() {
        var form = document.getElementById("exportForm");
        form.submit();
        $("#modelexport").modal("hide")
    }
</script>
</body>
</html>