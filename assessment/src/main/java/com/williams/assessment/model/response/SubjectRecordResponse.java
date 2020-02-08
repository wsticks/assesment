package com.williams.assessment.model.response;

import com.williams.assessment.model.entity.Student;
import com.williams.assessment.model.entity.SubjectRecord;
import com.williams.assessment.model.entity.Subjects;
import com.williams.assessment.util.TimeUtil;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SubjectRecordResponse {

    private String uniqueKey;
    private String studentKey;
    private String subjectKey;
    private String firstAssessmentTest;
    private String secondAssessmentTest;
    private String thirdAssessmentTest;
    private String fourthAssessmentTest;
    private String examinationScore;
    private int totalScore;
    private String grade;
    private String createdAt;
    private String updatedAt;

    public static SubjectRecordResponse fromSubjectRecord(SubjectRecord subjectRecordResponse){
        return SubjectRecordResponse.builder()
        .uniqueKey(subjectRecordResponse.getUniqueKey())
        .studentKey(subjectRecordResponse.getStudentKey())
        .subjectKey(subjectRecordResponse.getSubjectKey())
        .firstAssessmentTest(subjectRecordResponse.getFirstAssessmentTest())
        .secondAssessmentTest(subjectRecordResponse.getSecondAssessmentTest())
        .thirdAssessmentTest(subjectRecordResponse.getThirdAssessmentTest())
        .fourthAssessmentTest(subjectRecordResponse.getFourthAssessmentTest())
        .examinationScore(subjectRecordResponse.getExaminationScore())
        .totalScore(subjectRecordResponse.getTotalScore())
        .createdAt(TimeUtil.getIsoTime(subjectRecordResponse.getCreatedAt()))
        .updatedAt(TimeUtil.getIsoTime(subjectRecordResponse.getCreatedAt()))
        .grade(subjectRecordResponse.getGrade())
        .build();
    }

    public static List<SubjectRecordResponse> fromSubjectRecord(List<SubjectRecord> subjectRecords) {
        return subjectRecords.stream().map(subjectRecord -> {
            return fromSubjectRecord(subjectRecord);
        }).collect(
                Collectors.toList());
    }
}
