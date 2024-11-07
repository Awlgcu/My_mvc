package com.jiuaoedu.evaluation.dto;

import com.jiuaoedu.evaluation.check_param.NotNull;


public class StudentQuery {
    @NotNull(message = "学生学号不能为空")
    private Long studentId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StudentQuery{" +
                "studentId=" + studentId +
                '}';
    }
}
