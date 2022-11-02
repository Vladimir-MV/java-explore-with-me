    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.apache.catalina.connector.Response;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCategoryService;
    import ru.practicum.explorewithme.admin.service.AdminCategoryServiceImpl;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

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
                @RequestBody CategoryDto categoryDto) throws RequestErrorException {
            log.info("adminPatchCategory, patch categoryDto {}", categoryDto);
            return adminCategoryService.patchCategoryByIdAndName(categoryDto);
        }

        @PostMapping()
        public CategoryDto adminPostCategory(
                @RequestBody NewCategoryDto newCategoryDto) throws RequestErrorException {
            log.info("adminPostCategory, create category newCategoryDto {}", newCategoryDto);
            return adminCategoryService.createCategory(newCategoryDto);
        }

        @DeleteMapping("/{catId}")
        public CategoryDto adminDeleteCategory(
                @PathVariable Long catId) throws RequestErrorException {
            log.info("adminDeleteCategory, delete category catId={}", catId);
            return adminCategoryService.deleteCategoryById(catId);
        }

    }
