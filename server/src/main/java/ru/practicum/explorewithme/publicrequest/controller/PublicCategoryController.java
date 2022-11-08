    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.publicrequest.service.PublicCategoryService;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/categories")
    @Slf4j
    @RequiredArgsConstructor
    public class PublicCategoryController {
        final private PublicCategoryService publicCategoryService;

//        @Autowired
//        public PublicCategoryController (PublicCategoryService publicCategoryService) {
//            this.publicCategoryService = publicCategoryService;
//        }

        @GetMapping
        public List<CategoryDto> getAllCategories(
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size)
                throws ObjectNotFoundException {
            log.info("publicGetCompilation, get categories with from={}, size={}", from, size);
            return publicCategoryService.getCategories(from, size);
        }

        @GetMapping("/{catId}")
        public CategoryDto getCategoryById(
                @PathVariable Long catId) throws ObjectNotFoundException, RequestErrorException {
            log.info("publicGetCategoryById get category by catId={}", catId);
            return publicCategoryService.getCategoryById(catId);
        }

    }