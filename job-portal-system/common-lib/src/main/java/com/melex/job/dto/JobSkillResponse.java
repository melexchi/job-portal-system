package com.melex.job.dto;

import com.melex.job.domain.SkillCategory;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillResponse {

    private Long id;

    private String name;

    private String slug;

    private SkillCategory category;

    private Boolean active;
}
