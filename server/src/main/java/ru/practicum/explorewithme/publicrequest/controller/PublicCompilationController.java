    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.publicrequest.service.PublicCompilationService;
    import ru.practicum.explorewithme.publicrequest.service.PublicCompilationServiceImpl;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/compilations")
    @Slf4j
    public class PublicCompilationController {
        PublicCompilationService publicCompilationService;

        @Autowired
        public PublicCompilationController (PublicCompilationServiceImpl publicCompilationServiceImpl) {
            this.publicCompilationService = publicCompilationServiceImpl;
        }

        @GetMapping
        public List<CompilationDto> publicGetCompilation(
                @RequestParam(name = "pinned", required = false) Boolean pinned,
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size) throws ObjectNotFoundException {

            log.info("publicGetCompilation, get events with pinned={}, from={}, size={}",
                    pinned, from, size);
            return publicCompilationService.getCompilation(pinned, from, size);
        }

        @GetMapping("/{compId}")
        public CompilationDto publicGetCompilationById(
                @PathVariable Long compId) throws ObjectNotFoundException {
            log.info("publicGetCompilationById get compilation by compId={}", compId);
            return publicCompilationService.getCompilationById(compId);
        }

    }
