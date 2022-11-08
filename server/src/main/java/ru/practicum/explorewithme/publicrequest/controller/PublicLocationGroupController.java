    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.publicrequest.service.PublicLocationGroupService;
    import javax.validation.constraints.NotBlank;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/locations")
    @Slf4j
    @RequiredArgsConstructor
    public class PublicLocationGroupController {
        final private PublicLocationGroupService publicLocationGroupService;

        @GetMapping
        public List<LocationGroupDto> publicGetLocationGroups() throws ObjectNotFoundException {
            log.info("publicGetLocationGroups, get all locationGroups");
            return publicLocationGroupService.getLocationGroups();
        }

        @GetMapping("/{id}")
        public LocationGroupDto publicGetLocationGroupById(
                @NotBlank @PathVariable Long id) throws ObjectNotFoundException {
            log.info("publicGetLocationGroupById get locationGroup by id={}", id);
            return publicLocationGroupService.getLocationGroupById(id);
        }

    }
