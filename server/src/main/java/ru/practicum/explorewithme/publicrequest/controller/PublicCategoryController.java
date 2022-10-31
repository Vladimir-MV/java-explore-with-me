    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.publicrequest.service.PublicCategoryService;
    import ru.practicum.explorewithme.publicrequest.service.PublicCategoryServiceImpl;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/categories")
    @Slf4j
    public class PublicCategoryController {
        PublicCategoryService publicCategoryService;

        @Autowired
        public PublicCategoryController (PublicCategoryServiceImpl publicCategoryServiceImpl) {
            this.publicCategoryService = publicCategoryServiceImpl;
        }

        @GetMapping
        public List<CategoryDto> publicGetCategories(
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size) throws ObjectNotFoundException {

            log.info("publicGetCompilation, get categories with from={}, size={}", from, size);
            return publicCategoryService.getCategories(from, size);
        }

        @GetMapping("/{catId}")
        public CategoryDto publicGetCategoryById(
                @PathVariable Optional<Long> catId) throws ObjectNotFoundException, RequestErrorException {
            log.info("publicGetCategoryById get category by catId={}", catId);
            return publicCategoryService.getCategoryById(catId);
        }

    }