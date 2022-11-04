    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminCompilationService;
    import ru.practicum.explorewithme.admin.service.AdminCompilationServiceImpl;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

    @RestController
    @RequestMapping(path = "/admin/compilations")
    @Slf4j
    public class AdminCompilationController {
        AdminCompilationService adminCompilationService;

        @Autowired
        public AdminCompilationController (AdminCompilationServiceImpl adminCompilationServiceImpl) {
            this.adminCompilationService = adminCompilationServiceImpl;
        }

        @PostMapping()
        public CompilationDto adminPostCompilation(
            @RequestBody NewCompilationDto newCompilationDto) throws ConditionsOperationNotMetException {
            log.info("adminPostCompilation, create compilation newCompilationDto {}", newCompilationDto);
            return adminCompilationService.createCompilation(newCompilationDto);
        }

        @DeleteMapping("/{compId}")
        public void adminDeleteCompilation(
            @PathVariable Long compId) throws ObjectNotFoundException, RequestErrorException {
            log.info("adminDeleteCompilation, delete compilation compId={}", compId);
            adminCompilationService.deleteCompilationById(compId);
        }
        @DeleteMapping("/{compId}/events/{eventId}")
        public void adminDeleteEventFromCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) throws ObjectNotFoundException, RequestErrorException {
            log.info("adminDeleteEventFromCompilation, delete event compId={}, eventId={}", compId, eventId);
            adminCompilationService.deleteEventByIdFromCompilation(compId, eventId);
        }

        @PatchMapping("/{compId}/events/{eventId}")
        public void adminPatchEventInCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) throws ObjectNotFoundException, RequestErrorException {
            log.info("adminPatchEventInCompilation, patch event in compilation compId={}, eventId={}", compId, eventId);
            adminCompilationService.patchEventInCompilationById(compId, eventId);
        }
        @DeleteMapping("/{compId}/pin")
        public void adminUnpinCompilation(
            @PathVariable Long compId) throws ObjectNotFoundException, RequestErrorException {
            log.info("adminUnpinCompilation, unpin compilation compId={}", compId);
            adminCompilationService.unpinCompilationById(compId);
        }

        @PatchMapping("/{compId}/pin")
        public void adminPinCompilation(
            @PathVariable Long compId) throws ObjectNotFoundException, RequestErrorException {
            log.info("adminPinCompilation, pin compilation compId={}", compId);
            adminCompilationService.pinCompilationById(compId);
        }

    }
