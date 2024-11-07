package com.jiuaoedu.evaluation.controller;

import com.jiuaoedu.evaluation.dto.Result;
import com.jiuaoedu.evaluation.dto.Student;
import com.jiuaoedu.evaluation.process_controller.Controller;
import com.jiuaoedu.evaluation.process_controller.Post;
import com.jiuaoedu.evaluation.check_param.NotNull;
import com.jiuaoedu.evaluation.process_param.RequestBody;
import com.jiuaoedu.evaluation.process_param.RequestParam;
import com.jiuaoedu.evaluation.dto.StudentQuery;


@Controller
public class StudentController {

    @Post(url = "/student/find")
//    @NotNull(message = "请求体不能为空")
    public Result<Student> find(@RequestBody @NotNull(message = "请求体不能为空") StudentQuery studentQuery,
                                @RequestParam("classId") @NotNull(message = "班级号不能为空") Long classId) {
        System.out.println(studentQuery.toString());
        System.out.println(classId);
        Student student = new Student( "rick", 1l);
        return Result.success(student);
    }
}
