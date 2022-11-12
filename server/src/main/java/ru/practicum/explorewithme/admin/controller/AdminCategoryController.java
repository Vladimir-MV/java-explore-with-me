    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCategoryService;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import javax.validation.Valid;

    @RestController
    @RequestMapping(path = "/admin/categories")
    @Slf4j
    @RequiredArgsConstructor
    public class AdminCategoryController {

        private final AdminCategoryService adminCategoryService;

        @PatchMapping
        public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
            log.info("adminPatchCategory, patch categoryDto {}", categoryDto);
            return adminCategoryService.patchCategoryByIdAndName(categoryDto);
        }

        @PostMapping
        public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
            log.info("adminPostCategory, create category newCategoryDto {}", newCategoryDto);
            return adminCategoryService.createCategory(newCategoryDto);
        }

        @DeleteMapping("/{catId}")
        public CategoryDto deleteCategory(@PathVariable Long catId) {
            log.info("adminDeleteCategory, delete category catId={}", catId);
            return adminCategoryService.deleteCategoryById(catId);
        }
    }
