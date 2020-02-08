package com.williams.assessment.service;

import com.williams.assessment.exception.AssessmentApiException;
import com.williams.assessment.exception.NotFoundException;
import com.williams.assessment.exception.ProcessingException;
import com.williams.assessment.model.entity.SubjectRecord;
import com.williams.assessment.model.request.SubjectRecordRequest;
import com.williams.assessment.model.request.SubjectRecordSearchRequest;
import com.williams.assessment.model.response.SubjectRecordResponse;
import com.williams.assessment.repository.SubjectRecordRepository;
import com.williams.assessment.util.SecurityUtil;
import com.williams.assessment.util.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubjectRecordService {

    private final SubjectRecordRepository subjectRecordRepository;
    private final String SUBJECT_RECORD_EXIST = "Subject already exist";
    private final String RECORD_NOT_FOUND = "Record tp update was not found";


    public SubjectRecordService(SubjectRecordRepository subjectRecordRepository) {
        Assert.notNull(subjectRecordRepository);
        this.subjectRecordRepository = subjectRecordRepository;
    }

  public SubjectRecordResponse prepareRecordForSave(SubjectRecordRequest subjectRecordRequest){
        SubjectRecord recordToSave = subjectRecordRequest.toStudentRecords();
        generateUniqueKey(recordToSave);
        SubjectRecord recordSaved = subjectRecordRepository.save(recordToSave);
        SubjectRecordResponse subjectRecordResponse = new SubjectRecordResponse();
        subjectRecordResponse.setUniqueKey(recordSaved.getUniqueKey());
        subjectRecordResponse.setTotalScore(recordSaved.getTotalScore());
        subjectRecordResponse.setSubjectKey(recordSaved.getSubjectKey());
        subjectRecordResponse.setStudentKey(recordSaved.getStudentKey());
        subjectRecordResponse.setFourthAssessmentTest(recordSaved.getFourthAssessmentTest());
        subjectRecordResponse.setThirdAssessmentTest(recordSaved.getThirdAssessmentTest());
        subjectRecordResponse.setSecondAssessmentTest(recordSaved.getSecondAssessmentTest());
        subjectRecordResponse.setFirstAssessmentTest(recordSaved.getFirstAssessmentTest());
        subjectRecordResponse.setExaminationScore(recordSaved.getExaminationScore());
        subjectRecordResponse.setCreatedAt(TimeUtil.getIsoTime(recordSaved.getCreatedAt()));
        subjectRecordResponse.setUpdatedAt(TimeUtil.getIsoTime(recordSaved.getUpdatedAt()));
        return subjectRecordResponse;
  }

//    public SubjectRecord createSubjectRecord(SubjectRecordRequest subjectRecordRequest, SubjectRecord subjectRecord)
//    throws Exception{
//        prepareForSave(subjectRecord);
//        SubjectRecord subjectRecord1 = subjectRecordRepository.findOneByUniqueKey(subjectRecordRequest.getUniqueKey());
//        if(subjectRecord1 != null){
//            throw  new ConflictException(SUBJECT_RECORD_EXIST);
//        }
//        return subjectRecordRepository.save(subjectRecord);
//
//    }

    public SubjectRecord updateSubjectRecord (SubjectRecord updateSubjectRecord, String uniqueKey)
            throws Exception{
        SubjectRecord savedRecord = fetchRecordByUniqueKey(uniqueKey);
        savedRecord.setTotalScore(updateSubjectRecord.getTotalScore());
        savedRecord.setExaminationScore(updateSubjectRecord.getExaminationScore());
        savedRecord.setFirstAssessmentTest(updateSubjectRecord.getFirstAssessmentTest());
        savedRecord.setSecondAssessmentTest(updateSubjectRecord.getSecondAssessmentTest());
        savedRecord.setThirdAssessmentTest(updateSubjectRecord.getThirdAssessmentTest());
        savedRecord.setFourthAssessmentTest(updateSubjectRecord.getFourthAssessmentTest());
        savedRecord.setStudentKey(updateSubjectRecord.getStudentKey());
        savedRecord.setSubjectKey(updateSubjectRecord.getSubjectKey());
        return subjectRecordRepository.save(savedRecord);
    }

    public SubjectRecord viewRecord(String recordKey){
        SubjectRecord savedRecord = subjectRecordRepository.findOneByUniqueKey(recordKey);
        if(savedRecord == null){
            throw  new ProcessingException( "Record not found");
        }
        return savedRecord;
    }

     public Page<SubjectRecord> viewRecords(SubjectRecordSearchRequest request){
        return subjectRecordRepository.findAll(request.getBooleanExpression(), request.getPaginationQuery());
     }

//public List<SubjectRecord> viewRecords(){return (List<SubjectRecord>) subjectRecordRepository.findAll();}
//
//    private void prepareForSave(SubjectRecord subjectRecord) throws AssessmentApiException {
//        generateUniqueKey(subjectRecord);
//    }

    private void generateUniqueKey(SubjectRecord subjectRecord) throws ProcessingException {
        if (subjectRecord.getUniqueKey() != null) {
            return;
        }
        String rawKey = subjectRecord.getFirstAssessmentTest() + LocalDateTime.now() + Math.random() + subjectRecord.getExaminationScore()
                + subjectRecord.getFourthAssessmentTest();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        subjectRecord.setUniqueKey(uniqueKey);
    }

    public SubjectRecord fetchRecordByUniqueKey(String uniqueKey){
        SubjectRecord savedRecord = subjectRecordRepository.findOneByUniqueKey(uniqueKey);
        if( savedRecord== null){
            throw new NotFoundException(RECORD_NOT_FOUND);
        }
        return savedRecord;
    }
}
