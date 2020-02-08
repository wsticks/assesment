package com.williams.assessment.model.response;

import com.williams.assessment.model.entity.Subjects;
import com.williams.assessment.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubjectResponse {

    private String uniqueKey;
    private String subjectName;
    private String subjectCode;
    private String subjectTeacherName;
    private String createdAt;
    private String updateAt;

    public static SubjectResponse fromSubject(Subjects subjects){
        return SubjectResponse.builder()
                .updateAt(TimeUtil.getIsoTime(subjects.getUpdatedAt()))
                .createdAt(TimeUtil.getIsoTime(subjects.getCreatedAt()))
                .uniqueKey(subjects.getUniqueKey())
                .subjectName(subjects.getSubjectName())
                .subjectTeacherName(subjects.getSubjectTeacherName())
                .subjectCode(subjects.getSubjectCode())
                .build();
    }

    public static List<SubjectResponse> fromSubjects(List<Subjects> subjects) {
        return subjects.stream().map(loan -> {
            return fromSubject(loan);
        }).collect(
                Collectors.toList());
    }

}
