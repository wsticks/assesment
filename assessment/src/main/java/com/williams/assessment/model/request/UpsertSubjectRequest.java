package com.williams.assessment.model.request;

import com.williams.assessment.model.entity.Subjects;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpsertSubjectRequest {

    private String uniqeKey;
    private String subjectName;
    private String subjectCode;
    private String subjectTeacherName;

    public Subjects toSubjects(){
        Subjects subjects = new Subjects();
        subjects.setSubjectName(subjectName);
        subjects.setSubjectCode(subjectCode);
        subjects.setSubjectTeacherName(subjectTeacherName);
        return subjects;
    }

}
