package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名
        String username = request.getParameter("username");

        //2.调用service层判断用户是否存在
        //期望服务器响应的数据格式：
        // {"userExsit":true,"msg":"请用户太受欢迎，请换一个"}
        // {"userExsit":false,"msg":"用户名可用"}

        //如果在$.get(type)不设置json,也可以设置响应数据的格式为json
        //response.setContentType("application/json;charset=utf-8");
        //不知道怎么写，可以在tomcat目录的conf目录下找到web.xml 里面有
        response.setContentType("text/html;charset=utf-8");
        Map<String,Object> map = new HashMap<>();
//        if("tom".equals(username)){
//            //存在
//            map.put("userExsit",true);
//            map.put("msg","请用户太受欢迎，请换一个");
//        }else {
//            //不存在
//            map.put("userExsit",false);
//            map.put("msg","用户名可用");
//        }
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
        List<User> list=userService.findUserName(username);

        if(list!=null && list.size()>0){
            //存在
            map.put("userExsit",true);
            map.put("msg","请用户太受欢迎，请换一个");
        }else {
            //不存在
            map.put("userExsit",false);
            map.put("msg","用户名可用");
        }


        //将map转为json，并且传递给客户端
        ObjectMapper mapper = new ObjectMapper();
        //将数据传递给客户端并且在页面显示
        mapper.writeValue(response.getWriter(),map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
