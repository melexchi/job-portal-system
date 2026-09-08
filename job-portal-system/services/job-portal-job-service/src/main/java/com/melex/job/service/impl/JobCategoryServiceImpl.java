package com.melex.job.service.impl;

import com.melex.job.dto.JobCategoryResponse;
import com.melex.job.mapper.JobCategoryMapper;
import com.melex.job.model.JobCategory;
import com.melex.job.payload.JobCategoryRequest;
import com.melex.job.repository.JobCategoryRepository;
import com.melex.job.service.JobCategoryService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {


    private final JobCategoryRepository jobCategoryRepository;


    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest req) throws Exception {

        if(jobCategoryRepository.existsByName(req.getName())){
            throw new Exception("Category neme already exists use a difference name");
        }

        JobCategory parent =null;
        if(req.getParentId()!=null){
            parent= getCategoryEntityById(req.getParentId());
        }

        String slug =generateUniqueSlug(req.getName());


        JobCategory category = JobCategory.builder()
                .name(req.getName())
                .description(req.getDescription())
                .slug(slug)
                .iconUrl(req.getIconUrl())
                .parent(parent)
                .build();

        JobCategory savedjobCategory = jobCategoryRepository.save(category);

        return JobCategoryMapper.toJobCategoryResponse(savedjobCategory,true);
    }

    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return jobCategoryRepository.findByActiveTrue().stream().map(
                c-> JobCategoryMapper.toJobCategoryResponse(c,false)
        ).collect(Collectors.toList());
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) throws Exception {


        JobCategory category = getCategoryEntityById(id);

        if(!category.getName().equals(req.getName()) && jobCategoryRepository.existsByName(req.getName())){
            throw new Exception("Category name already exist, please choose a different name");
        }

        JobCategory parent = null;

        if(req.getParentId() !=null){
            if(req.getParentId().equals(id)){
                throw  new Exception("A Category cannot be its own parent");
            }
            parent =getCategoryEntityById(req.getParentId());
        }

        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIconUrl(req.getIconUrl());
        category.setParent(parent);

        JobCategory updatedCategory = jobCategoryRepository.save(category);

        return JobCategoryMapper.toJobCategoryResponse(updatedCategory,true);

    }

    @Override
    public JobCategoryResponse getCategoryById(Long id) throws Exception {

        JobCategory jobCategory = getCategoryEntityById(id);


        return JobCategoryMapper.toJobCategoryResponse(jobCategory,true);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {

        JobCategory category = getCategoryEntityById(id);
        category.setActive(false);
        jobCategoryRepository.save(category);

    }

    @Override
    public JobCategory getCategoryEntityById(Long id) throws Exception {
        return jobCategoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );
    }



    private String generateUniqueSlug(@NotBlank(message = "Company name is required") String name) {
        String base =name.toLowerCase().replaceAll("[^a-z0-9\\s-]+", "").trim().replaceAll("[\\s-]+", "-");

        if(!jobCategoryRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;

        while(jobCategoryRepository.existsBySlug(base+ "-"+counter)){
            counter++;
        }

        return base + "-" + counter;
    }

}
