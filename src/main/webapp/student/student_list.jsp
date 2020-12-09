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
</head>
<script type="text/javascript">

    function batchDeletes() {
        //判断至少写了一项
        var checkedNum = $("input[name='subcheck']:checked").length;
        if (checkedNum == 0) {
            alert("请至少选择一项!");
            return false;
        }
        if (confirm("确定删除所选项目?")) {
            var checkedList = new Array();
            $("input[name='subcheck']:checked").each(function () {
                checkedList.push($(this).val());
            });
            $.ajax({
                type: "POST",
                url: "batchDeletes",
                data: {"delitems": checkedList.toString()},
                datatype: "html",
                success: function (data) {
                    $("[name='checkbox2']:checkbox").attr("checked", false);
                    location.reload();//页面刷新
                },
                error: function (data) {
                    art.dialog.tips('删除失败!');
                }
            });
        }
    }
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
                        <span class="navbar-page-title"> 协会成员信息管理 - 协会成员列表 </span>
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
                                    <a class="btn btn-primary m-r-5 mdi mdi-account-plus" href="student_add.jsp"><i class="mdi mdi-plus"></i>
                                        添加</a>
                                    <a class="btn btn-danger mdi mdi-account-off" onclick="batchDeletes()"><i
                                            class="mdi mdi-window-close "></i> 删除</a>
                                </div>
                            </div>
                            <ul class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input type="checkbox" id="check-all"><span></span>
                                                </label>
                                            </th>
                                            <th>序号</th>
                                            <th>学号</th>
                                            <th>姓名</th>
                                            <th>性别</th>
                                            <th>年级</th>
                                            <th>班级</th>
                                            <th>手机号</th>
                                            <th>qq号</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${list}" var="student" varStatus="xh">
                                            <tr>
                                                <td>
                                                    <label class="lyear-checkbox checkbox-primary">
                                                        <input type="checkbox" id="subcheck" name="subcheck"
                                                               value="${student.sno}"><span></span>
                                                    </label>
                                                </td>
                                                <td>${xh.count+(page.currentPage-1)*page.pageSize}</td>
                                                <td id="sno">${student.sno}</td>
                                                <td>${student.sname}</td>
                                                <td>${student.ssex}</td>
                                                <td>${student.syear}</td>
                                                <td>${student.sclass}</td>
                                                <td>${student.stel}</td>
                                                <td>${student.sqq}</td>
                                                <td>
                                                    <div class="btn-group">
                                                        <a class="btn btn-xs btn-default"
                                                           href="${pageContext.request.contextPath}/student/updateStudent_page?sno=${student.sno}&inUpdatePage=list"
                                                        title="编辑"
                                                        data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>
                                                        <a class="btn btn-xs btn-default"
                                                           onclick="ifdel(${student.sno})" title="删除"
                                                           data-toggle="tooltip"><i
                                                                class="mdi mdi-window-close"></i></a>
                                                        <a class="btn btn-xs btn-default"
                                                           href="${pageContext.request.contextPath}/student/findCompetitions_page?sno=${student.sno}&page=list" title="查看参赛信息"
                                                           data-toggle="tooltip"><i
                                                                class="mdi mdi-flag-checkered"></i></a>
                                                        <a class="btn btn-xs btn-default"
                                                           href="${pageContext.request.contextPath}/student/findActivitys_page?sno=${student.sno}&page=list" title="查看活动信息"
                                                           data-toggle="tooltip"><i
                                                                class="mdi mdi-guitar-acoustic"></i></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <ul class="pager">
                                    <ul class="pagination">
                                        <li><a id="begin" href="${pageContext.request.contextPath}/student/findByPage?page=${1}"><span>«</span></a></li>
                                        <li><a href="${pageContext.request.contextPath}/student/findByPage?page=${page.prefPage}" class="pager">上一页</a></li>
                                        <c:if test="${page.totalPages > 4}">
                                            <c:if test="${page.currentPage == page.totalPages - 1}">
                                                <li>
                                                    <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage-3}"><span>${page.currentPage-3}</span></a>
                                                </li>
                                            </c:if></c:if>
                                        <c:if test="${page.currentPage == page.totalPages}"><c:if
                                                test="${page.totalPages>=5}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage-4}"><span>${page.currentPage-4}</span></a>
                                            </li>
                                        </c:if><c:if test="${page.totalPages>=4}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage-3}"><span>${page.currentPage-3}</span></a>
                                            </li>
                                        </c:if></c:if>
                                        <c:if test="${page.currentPage > 2}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage-2}"><span>${page.currentPage-2}</span></a>
                                            </li>
                                        </c:if><c:if test="${page.currentPage > 1}">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage-1}"><span>${page.currentPage-1}</span></a>
                                        </li>
                                    </c:if>


                                        <li><a class="disabled"><span>${page.currentPage}</span></a></li>


                                        <c:if test="${page.totalPages >= page.currentPage + 1}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage+1}"><span>${page.currentPage+1}</span></a>
                                            </li>
                                        </c:if>
                                        <c:if test="${page.totalPages >= page.currentPage + 2}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage + 2}"><span>${page.currentPage+2}</span></a>
                                            </li>
                                        </c:if>

                                        <c:if test="${page.currentPage == 1}">
                                            <c:if test="${page.totalPages >= 4}">
                                                <li>
                                                    <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage + 3}"><span>${page.currentPage+3}</span></a>
                                                </li>
                                            </c:if><c:if test="${page.totalPages >= 5}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage + 4}"><span>${page.currentPage+4}</span></a>
                                            </li>
                                        </c:if></c:if>

                                        <c:if test="${page.currentPage == 2}"><c:if test="${page.totalPages > 4}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/student/findByPage?page=${page.currentPage + 3}"><span>${page.currentPage+3}</span></a>
                                            </li>
                                        </c:if></c:if>


                                        <li><a href="${pageContext.request.contextPath}/student/findByPage?page=${page.nextPage}" class="pager">下一页</a></li>
                                        <li><a href="${pageContext.request.contextPath}/student/findByPage?page=${page.totalPages}">»</a></li>
                                    </ul>
                                </ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

</main>
<!--End 页面主要内容-->
</div>
</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/perfect-scrollbar.min.js"></script>

<!--对话框-->
<script src="../js/jconfirm/jquery-confirm.min.js"></script>
<script type="text/javascript" src="../js/main.min.js"></script>
<script type="text/javascript">
    function batchDeletes(){
        //判断至少写了一项
        var checkedNum = $("input[name='subcheck']:checked").length;
        if(checkedNum==0){
            alert("请至少选择一项!");
            return false;
        }
        if(confirm("确定删除所选项目?")){
            var checkedList = new Array();
            $("input[name='subcheck']:checked").each(function(){
                checkedList.push($(this).val());
            });
            $.ajax({
                type:"POST",
                url:"/student/batchDeletes",
                data:{"delitems":checkedList.toString()},
                datatype:"html",
                success:function(data){
                    $("[name='checkbox2']:checkbox").attr("checked",false);
                    location.reload();//页面刷新
                },
                error:function(data){
                    art.dialog.tips('删除失败!');
                }
            });
        }
    }

    function ifdel(sno) {
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
                        location = "/student/deleteBySno_page?sno=" + sno;
                    }
                },
                close: {
                    text: '取消'
                }
            }
        });
    }

</script>
</body>
</html>