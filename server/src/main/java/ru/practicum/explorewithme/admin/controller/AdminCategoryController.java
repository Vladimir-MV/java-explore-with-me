    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCategoryService;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import javax.validation.Valid;

    @RestController
    @RequestMapping(path = "/admin/categories")
    @Slf4j
    @RequiredArgsConstructor
    public class AdminCategoryController {
        final private AdminCategoryService adminCategoryService;

        @PatchMapping
        public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto)
                throws RequestErrorException {
            log.info("adminPatchCategory, patch categoryDto {}", categoryDto);
            return adminCategoryService.patchCategoryByIdAndName(categoryDto);
        }

        @PostMapping
        public CategoryDto addPostCategory(@Valid @RequestBody NewCategoryDto newCategoryDto)
                throws RequestErrorException {
            log.info("adminPostCategory, create category newCategoryDto {}", newCategoryDto);
            return adminCategoryService.createCategory(newCategoryDto);
        }

        @DeleteMapping("/{catId}")
        public CategoryDto deleteCategory(@PathVariable Long catId)
                throws RequestErrorException {
            log.info("adminDeleteCategory, delete category catId={}", catId);
            return adminCategoryService.deleteCategoryById(catId);
        }

    }
