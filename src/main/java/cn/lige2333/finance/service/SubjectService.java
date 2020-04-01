package cn.lige2333.finance.service;

import cn.lige2333.finance.dao.SubjectsMapper;
import cn.lige2333.finance.entity.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectsMapper subjectsMapper;
    public void createSubject(Subjects subjects) {
        subjectsMapper.insert(subjects);
    }
}
