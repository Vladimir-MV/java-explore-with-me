    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCompilationService;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;
    import javax.validation.Valid;

    @RestController
    @RequestMapping(path = "/admin/compilations")
    @Slf4j
    @RequiredArgsConstructor
    public class AdminCompilationController {

        private final AdminCompilationService adminCompilationService;

        @PostMapping
        public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
            log.info("adminPostCompilation, create compilation newCompilationDto {}", newCompilationDto);
            return adminCompilationService.createCompilation(newCompilationDto);
        }

        @DeleteMapping("/{compId}")
        public void deleteCompilation(@PathVariable Long compId) {
            log.info("adminDeleteCompilation, delete compilation compId={}", compId);
            adminCompilationService.deleteCompilationById(compId);
        }

        @DeleteMapping("/{compId}/events/{eventId}")
        public void deleteEventInCompilation(@PathVariable Long compId,
                                             @PathVariable Long eventId) {
            log.info("adminDeleteEventFromCompilation, delete event compId={}, eventId={}", compId, eventId);
            adminCompilationService.deleteEventByIdFromCompilation(compId, eventId);
        }

        @PatchMapping("/{compId}/events/{eventId}")
        public void updateEventInCompilation(@PathVariable Long compId,
                                             @PathVariable Long eventId) {
            log.info("adminPatchEventInCompilation, patch event in compilation compId={}, eventId={}", compId, eventId);
            adminCompilationService.patchEventInCompilationById(compId, eventId);
        }

        @DeleteMapping("/{compId}/pin")
        public void unpinCompilation(@PathVariable Long compId) {
            log.info("adminUnpinCompilation, unpin compilation compId={}", compId);
            adminCompilationService.unpinCompilationById(compId);
        }

        @PatchMapping("/{compId}/pin")
        public void pinCompilation(@PathVariable Long compId) {
            log.info("adminPinCompilation, pin compilation compId={}", compId);
            adminCompilationService.pinCompilationById(compId);
        }
    }
