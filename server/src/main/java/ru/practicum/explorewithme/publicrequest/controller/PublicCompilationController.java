    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.publicrequest.service.PublicCompilationService;

    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/compilations")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class PublicCompilationController {

        final private PublicCompilationService publicCompilationService;

        @GetMapping
        public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) boolean pinned,
                                                    @PositiveOrZero @RequestParam(name = "from",
                                                            defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(name = "size",
                                                            defaultValue = "10") Integer size)
                                                       throws ObjectNotFoundException {
            log.info("publicGetCompilation, get events with pinned={}, from={}, size={}",
                    pinned, from, size);
            return publicCompilationService.getCompilation(pinned, from, size);
        }

        @GetMapping("/{compId}")
        public CompilationDto getCompilation(@PathVariable Long compId)
                                                throws ObjectNotFoundException {
            log.info("publicGetCompilationById get compilation by compId={}", compId);
            return publicCompilationService.getCompilationById(compId);
        }
    }
