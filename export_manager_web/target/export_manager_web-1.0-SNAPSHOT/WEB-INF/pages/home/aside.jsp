<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../base.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="../img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p> ${sessionScope.loginUser.userName}</p>
                <a href="#">${sessionScope.loginUser.companyName}</a>
            </div>
        </div>

        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
            <c:forEach items="${sessionScope.modules}" var="item">
                <c:if test="${item.ctype == 0}">
                    <li class="treeview">
                        <a href="#">
                            <i class="fa fa-cube"></i> <span>${item.name}</span>
                            <span class="pull-right-container">
                                <i class="fa fa-angle-left pull-right"></i>
                            </span>
                        </a>
                        <c:forEach items="${sessionScope.modules}" var="item2">
                            <c:if test="${item2.ctype == 1 && item2.parentId == item.id}">
                                <ul class="treeview-menu">
                                    <li id="company-manager">
                                        <a href="${item2.curl}" onclick="setSidebarActive(this)" target="iframe">
                                            <i class="fa fa-circle-o"></i>${item2.name}
                                        </a>
                                    </li>

                                </ul>
                            </c:if>
                        </c:forEach>


                    </li>
                </c:if>

            </c:forEach>



        </ul>

    </section>
    <!-- /.sidebar -->
</aside>
