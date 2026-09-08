package com.melex.job.service;

import com.melex.job.dto.JobCategoryResponse;
import com.melex.job.model.JobCategory;
import com.melex.job.payload.JobCategoryRequest;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createCategory(JobCategoryRequest req) throws Exception;

    List<JobCategoryResponse> getAllCategories();

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) throws Exception;

    JobCategoryResponse getCategoryById(Long id) throws Exception;

    void deleteCategory(Long id) throws Exception;

    JobCategory getCategoryEntityById(Long id) throws Exception;

}
