package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据:参数里面与标签体里的name属性所对应的
        String verifycode = request.getParameter("verifycode");
        //2.1.验证码校验
        //通过session在servlet中保存验证码
        //获取session
        HttpSession session = request.getSession();
        //获取session保存的验证码信息
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //要保证验证码是一次性的，所以要立马删除session
        session.removeAttribute("CHECKCODE_SERVER");

        //判断在忽略大小的情况下，验证码是否相同
        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            //表示验证码不正确
            //1.提示信息，保存到request域中
            request.setAttribute("login_msg","验证码不正确！");
            //2.转发到登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

            return;//表示如果代码执行到这里，下面的代码就不执行了
        }


        //2.2获取所有的数据封装到User
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //3.封装User对象:可以通过BeanUtils里面的populate()方法，里面传入对象和map集合
        //注意：BeanUtils导apache的包
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //4.调用Service查询
        UserService userService=new UserServiceImpl();
        User loginUser = userService.login(user);

        //5.判断是否登录成功
        if(loginUser!=null){
            //登录成功
            //讲用户存入到session中
            session.setAttribute("user",loginUser);
            //因为与index,jsp页面无法共享，所以要用跳转
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或者密码不正确！");
            //转发到登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
