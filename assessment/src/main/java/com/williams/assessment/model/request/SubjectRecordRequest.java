package com.williams.assessment.model.request;

import ch.qos.logback.core.util.TimeUtil;
import com.williams.assessment.model.entity.Student;
import com.williams.assessment.model.entity.SubjectRecord;
import com.williams.assessment.model.entity.Subjects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRecordRequest {

    private String uniqueKey;
    private String subjectKey;
    private String studentKey;
    private String firstAssessmentTest;
    private String secondAssessmentTest;
    private String thirdAssessmentTest;
    private String fourthAssessmentTest;
    private String examinationScore;
    private int totalScore;
    private String grade;







    public SubjectRecord toStudentRecords() {
        SubjectRecord subjectRecord = new SubjectRecord();
        subjectRecord.setUniqueKey(uniqueKey);
        subjectRecord.setExaminationScore(examinationScore);
        subjectRecord.setFirstAssessmentTest(firstAssessmentTest);
        subjectRecord.setSecondAssessmentTest(secondAssessmentTest);
        subjectRecord.setThirdAssessmentTest(thirdAssessmentTest);
        subjectRecord.setFourthAssessmentTest(fourthAssessmentTest);
        subjectRecord.setStudentKey(studentKey);
        subjectRecord.setSubjectKey(subjectKey);
        int total = Integer.parseInt(firstAssessmentTest) + Integer.parseInt(secondAssessmentTest) + Integer.parseInt(thirdAssessmentTest) + Integer.parseInt(fourthAssessmentTest) + Integer.parseInt(examinationScore);
        subjectRecord.setTotalScore(total);
        if(total>=70){
            grade = "A";
        } else if(total>=59 && total<70){
            grade = "B";
        } else if (total>49 && total<60){
            grade ="C";
        } else if (total>39 && total<50){
            grade ="D";
        } else if(total<=30){
            grade = "E";
        }
        subjectRecord.setGrade(grade);
        return subjectRecord;
    }


}
