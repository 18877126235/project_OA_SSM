/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-01-24 03:12:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.main_005fpages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

      out.write("\r\n");
      out.write("<link rel=\"icon\" href=\"favicon.ico\" />\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/layui/css/layui.css\"\r\n");
      out.write("\tmedia=\"all\" />\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/css/font_eolqem241z66flxr.css\"\r\n");
      out.write("\tmedia=\"all\" />\r\n");
      out.write("<!-- 导入jquey库文件 -->\r\n");
      out.write("<!-- BEGIN HEADER -->\r\n");
      out.write("<div class=\"header navbar navbar-inverse navbar-fixed-top\">\r\n");
      out.write("\t<!-- BEGIN TOP NAVIGATION BAR -->\r\n");
      out.write("\t<div class=\"navbar-inner\">\r\n");
      out.write("\t\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t\t<!-- BEGIN LOGO -->\r\n");
      out.write("\t\t\t<a class=\"brand\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/index.jsp\">\r\n");
      out.write("\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/media/image/logo.png\"\r\n");
      out.write("\t\t\t\talt=\"logo\" /> </a>\r\n");
      out.write("\t\t\t<!-- END LOGO -->\r\n");
      out.write("\t\t\t<!-- BEGIN RESPONSIVE MENU TOGGLER -->\r\n");
      out.write("\t\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/index.jsp\"\r\n");
      out.write("\t\t\t\tclass=\"btn-navbar collapsed\" data-toggle=\"collapse\"\r\n");
      out.write("\t\t\t\tdata-target=\".nav-collapse\"> <img\r\n");
      out.write("\t\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/media/image/menu-toggler.png\"\r\n");
      out.write("\t\t\t\talt=\"\" /> </a>\r\n");
      out.write("\t\t\t<!-- END RESPONSIVE MENU TOGGLER -->\r\n");
      out.write("\t\t\t<!-- BEGIN TOP NAVIGATION MENU -->\r\n");
      out.write("\t\t\t<ul class=\"nav pull-right\">\r\n");
      out.write("\t\t\t\t<!-- BEGIN 用户登录下拉菜单 -->\r\n");
      out.write("\t\t\t\t<li class=\"layui-nav-item showNotice\" id=\"showNotice\" pc><a\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\"><i class=\"iconfont icon-gonggao\"></i><cite>公司公告</cite>\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t<li class=\"dropdown user\"><a href=\"#\" class=\"dropdown-toggle\"\r\n");
      out.write("\t\t\t\t\tdata-toggle=\"dropdown\"> <img alt=\"\"\r\n");
      out.write("\t\t\t\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write('/');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.emp.empPhotoUrl}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\tonerror=\"this.src='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/media/image/default-head.png'\"\r\n");
      out.write("\t\t\t\t\t\tstyle=\"max-height:30px; max-height: 30px;\" /> <span\r\n");
      out.write("\t\t\t\t\t\tclass=\"username\">用户名:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.userName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</span> <i\r\n");
      out.write("\t\t\t\t\t\tclass=\"icon-angle-down\"></i> </a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"dropdown-menu\">\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/pages_hr/emp.jsp\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"icon-user\"></i> 我的资料</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/ResultSession.action\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"icon-envelope\"></i> 我的邮件</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li class=\"divider\"></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/lock.jsp\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"icon-lock\"></i> 锁屏</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/view/user/quitUser.action\"><i\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"icon-key\"></i> 注销</a></li>\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t<!-- END 用户登录下拉菜单 -->\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t\t<!-- END TOP NAVIGATION MENU -->\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- END TOP NAVIGATION BAR -->\r\n");
      out.write("</div>\r\n");
      out.write("<!-- END HEADER -->\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/layui/layui.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/index.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t//公告层\r\n");
      out.write("\tfunction showNotice() {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//* /2.准备发送的异步请求\r\n");
      out.write("\t\tvar url = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/view/announcement/findByNewnotice.action\";\r\n");
      out.write("\t\tvar bl = \"\"; //这是啥？？？\r\n");
      out.write("\t\tvar amconten = \"\";//内容\r\n");
      out.write("\t\tvar amtheme = \"\";//标题\r\n");
      out.write("\t\t//jquery实现  post   Ajax请求\r\n");
      out.write("\t\t$\r\n");
      out.write("\t\t\t\t.post(\r\n");
      out.write("\t\t\t\t\t\turl,\r\n");
      out.write("\t\t\t\t\t\tfunction(data) {\r\n");
      out.write("\t\t\t\t\t\t\tif (data == \"\") {\r\n");
      out.write("\t\t\t\t\t\t\t\tbl = false;\r\n");
      out.write("\t\t\t\t\t\t\t\tamconten = \"没有最新公告！\";\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tbl = true;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tvar arrayJson = data;\r\n");
      out.write("\t\t\t\t\t\t\t//遍历json数组\r\n");
      out.write("\t\t\t\t\t\t\tvar $jsons = $(arrayJson);\r\n");
      out.write("\t\t\t\t\t\t\tif (bl) {\r\n");
      out.write("\t\t\t\t\t\t\t\t$jsons.each(function() { //this 当前json对象\r\n");
      out.write("\t\t\t\t\t\t\t\t\t/* $(\"#emp_id\").val(this.empId); */\r\n");
      out.write("\t\t\t\t\t\t\t\t\tamconten = this.amConten;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tamtheme = this.amTheme;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tamdate = this.amDate;\r\n");
      out.write("\t\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\tamconten = \"没有最新公告！\";\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tlayer\r\n");
      out.write("\t\t\t\t\t\t\t\t\t.open({\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttype : 1,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle : \"公司公告\", //不显示标题栏\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tcloseBtn : false,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tarea : '520px',\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tshade : 0.8,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tid : 'LAY_layuipro', //设定一个id，防止重复弹出\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tbtn : [ '火速围观' ],\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tmoveType : 1, //拖拽模式，0或者1\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tcontent : '<div style=\"padding:50px;text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;\"><p><span><font color=\"red\"  style=\"font-weight: 900;\">标题：</font></span>'\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t+ amtheme\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t+ '</p><p><span><font color=\"blue\"  style=\"font-weight: 900;\">内容：</font></span>'\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t+ amconten + '</p></div>',\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tsuccess : function(layero) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tvar btn = layero\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t.find('.layui-layer-btn');\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tbtn.css('text-align', 'center');\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tbtn.on(\"click\", function() {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\twindow.sessionStorage.setItem(\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\"showNotice\", \"true\");\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t})\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tif ($(window).width() > 432) { //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\tbtn.on(\"click\", function() {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\tlayer.tips('公司公告躲在了这里',\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t'#showNotice', {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ttips : 3\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t})\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t}, \"json\");// 替代  eval() \r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("</script>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
