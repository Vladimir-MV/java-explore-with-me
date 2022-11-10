    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.publicrequest.service.PublicCategoryService;

    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/categories")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class PublicCategoryController {

        private final PublicCategoryService publicCategoryService;

        @GetMapping
        public List<CategoryDto> getAllCategories(@PositiveOrZero  @RequestParam(name = "from",
                                                    defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(name = "size",
                                                    defaultValue = "10") Integer size)
                throws ObjectNotFoundException {
            log.info("publicGetCompilation, get categories with from={}, size={}", from, size);
            return publicCategoryService.getCategories(from, size);
        }

        @GetMapping("/{catId}")
        public CategoryDto getCategoryById(@PathVariable Long catId)
                throws ObjectNotFoundException, RequestErrorException {
            log.info("publicGetCategoryById get category by catId={}", catId);
            return publicCategoryService.getCategoryById(catId);
        }
    }