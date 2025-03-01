    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.publicrequest.service.PublicLocationGroupService;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/locations")
    @RequiredArgsConstructor
    public class PublicLocationGroupController {


        private final PublicLocationGroupService publicLocationGroupService;

        @GetMapping
        public List<LocationGroupDto> publicGetLocationGroups() {
            return publicLocationGroupService.getLocationGroups();
        }

        @GetMapping("/{id}")
        public LocationGroupDto publicGetLocationGroupById(@PathVariable Long id) {
            return publicLocationGroupService.getLocationGroupById(id);
        }
    }
