    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCategoryService;
    import ru.practicum.explorewithme.admin.service.AdminCategoryServiceImpl;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;

    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/admin/categories")
    @Slf4j
    public class AdminCategoryController {
        AdminCategoryService adminCategoryService;

        @Autowired
        public AdminCategoryController (AdminCategoryServiceImpl adminCategoryServiceImpl) {
            this.adminCategoryService = adminCategoryServiceImpl;
        }

        @PatchMapping()
        public CategoryDto adminPatchCategory(
                @RequestBody Optional<CategoryDto> categoryDto) {
            log.info("adminPatchCategory, patch categoryDto {}", categoryDto);
            return adminCategoryService.patchCategoryByIdAndName(categoryDto);
        }

        @PostMapping()
        public NewCategoryDto adminPostCategory(
                @RequestBody Optional<NewCategoryDto> newCategoryDto) {
            log.info("adminPostCategory, create category newCategoryDto {}", newCategoryDto);
            return adminCategoryService.createCategory(newCategoryDto);
        }

        @DeleteMapping("/{catId}")
        public void adminDeleteCategory(
                @PathVariable Optional<Long> catId) {
            log.info("adminDeleteCategory, delete category catId={}", catId);
            adminCategoryService.deleteCategoryById(catId);
        }

    }
