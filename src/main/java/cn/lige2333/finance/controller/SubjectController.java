package cn.lige2333.finance.controller;

import cn.lige2333.finance.Utils.JwtUtils;
import cn.lige2333.finance.Utils.MyPage;
import cn.lige2333.finance.dao.SubjectsMapper;
import cn.lige2333.finance.entity.Subjects;
import cn.lige2333.finance.entity.User;
import cn.lige2333.finance.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController{
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectsMapper subjectsMapper;
    @RequestMapping("/createSubject")
    @ResponseBody
    public String createSubject(String title,String content,HttpServletRequest req){
        String token = getCookieVal(req, "token");
        User user = JwtUtils.getObject(token, User.class);
        Subjects subjects = new Subjects();
        subjects.setContent(content);
        subjects.setDate(new Date());
        subjects.setTitle(title);
        subjects.setUsername(user.getAccount());
        subjectService.createSubject(subjects);
        return "success";
    }
    @ResponseBody
    @RequestMapping("/showSubjects")
    public MyPage<Subjects> showSubjects(Integer currentPage,Integer pageSize) {
        MyPage<Subjects> products = MyPage.selectAllByPage(subjectsMapper,currentPage,pageSize);
        return products;
    }
}
