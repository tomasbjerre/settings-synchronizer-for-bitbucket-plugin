package se.bjurr.ssfb.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.templaterenderer.TemplateRenderer;

public class GlobalAdminViewServlet extends HttpServlet {

 private static final long serialVersionUID = 4140910573589152778L;
 private final TemplateRenderer renderer;

 public GlobalAdminViewServlet(TemplateRenderer renderer) {
  this.renderer = renderer;
 }

 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  resp.setContentType("text/html;charset=UTF-8");
  renderer.render( //
    "templates/global_admin.vm", //
    resp.getWriter());
 }
}