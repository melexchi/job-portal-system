package com.melex.job.payload;

import com.melex.job.domain.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Service;


@Data
@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSkillRequest {


    @NotBlank(message = "Skill name is required")
    @Size(max= 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Skill Category is required")
    private SkillCategory category;
}
