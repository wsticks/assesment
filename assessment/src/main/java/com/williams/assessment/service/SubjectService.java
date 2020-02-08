package com.williams.assessment.service;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.exception.ConflictException;
import com.williams.assessment.exception.NotFoundException;
import com.williams.assessment.exception.ProcessingException;
import com.williams.assessment.model.entity.Subjects;
import com.williams.assessment.model.request.SubjectSearchRequest;
import com.williams.assessment.model.request.UpsertSubjectRequest;
import com.williams.assessment.repository.SubjectRepository;
import com.williams.assessment.util.GatewayBeanUtil;
import com.williams.assessment.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;
    private final String SUBJECT_EXIST = "Subject already exists";
    private final String SUBJECT_NOT_FOUND = "Subject to update was not found";




    public SubjectService(SubjectRepository subjectRepository) {
        Assert.notNull(subjectRepository);
        this.subjectRepository = subjectRepository;
    }

    public Subjects createSubject(UpsertSubjectRequest upsertSubjectRequest, Subjects subjects)
            throws Exception{
       prepareForSave(subjects);
       Subjects subjects1 = subjectRepository.findOneBySubjectCode(upsertSubjectRequest.getSubjectCode());
       if(subjects1 != null){
           throw new ConflictException(SUBJECT_EXIST);
       }
       return subjectRepository.save(subjects);
    }

    public  Subjects updateSubject( Subjects updateSubject, String subjectKey) throws Exception{
        Subjects subjectToUpdate = fetchSubjectByUniqueKey(subjectKey);
        GatewayBeanUtil.copyProperties(updateSubject,subjectToUpdate);
        return subjectRepository.save(subjectToUpdate);
    }

    public Subjects viewSubject(String productKey){
        Subjects subjects = subjectRepository.findOneByUniqueKey(productKey);
        return subjects;
    }

    public Page<Subjects> viewSubjects (SubjectSearchRequest subject){
       return  subjectRepository.findAll(subject.getBooleanExpression(), subject.getPaginationQuery());
    }

    private void prepareForSave(Subjects subjects) throws AssessmentApiException {
        generateUniqueKey(subjects);
    }

    private void generateUniqueKey(Subjects subjects) throws ProcessingException {
        if (subjects.getUniqueKey() != null) {
            return;
        }
        String rawKey = subjects.getSubjectTeacherName() + LocalDateTime.now()
                + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        subjects.setUniqueKey(uniqueKey);
    }

    public Subjects fetchSubjectByUniqueKey(String subjectKey){
        Subjects savedSubject = subjectRepository.findOneByUniqueKey(subjectKey);
        if(savedSubject == null){
            throw new NotFoundException(SUBJECT_NOT_FOUND);
        }
        return savedSubject;
    }

}
