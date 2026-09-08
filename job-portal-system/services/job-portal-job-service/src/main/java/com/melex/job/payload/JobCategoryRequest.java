package com.melex.job.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategoryRequest {

    @NotBlank(message = "Category Name is Required ")
    private String name;

    @Size(max =500, message = " Description must be more than 500 characters")
    private String description;

    private String iconUrl;

    private Long parentId;

}
