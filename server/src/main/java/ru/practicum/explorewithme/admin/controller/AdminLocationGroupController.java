    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminLocationGroupService;
    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.dto.NewLocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;

    import javax.validation.Valid;

    @RestController
    @RequestMapping(path = "/admin/locations")
    @Slf4j
    @RequiredArgsConstructor
    public class AdminLocationGroupController {

        final private AdminLocationGroupService adminLocationGroupService;

        @PostMapping
        public LocationGroupDto addLocationGroup(
                @Valid @RequestBody NewLocationGroupDto locationGroupDto) {
            log.info("adminCreateLocationGroup, create newLocationGroup {}", locationGroupDto);
            return adminLocationGroupService.createLocationGroup(locationGroupDto);
        }

        @DeleteMapping("/{id}")
        public void deleteLocationGroup(@PathVariable Long id) throws ObjectNotFoundException {
            log.info("adminDeleteLocationGroup, delete LocationGroup id={}", id);
            adminLocationGroupService.deleteLocationGroupById(id);
        }


        @PatchMapping
        public LocationGroupDto updateLocationGroup(
                @Valid @RequestBody LocationGroupDto locationGroupDto)
                throws ObjectNotFoundException {
            log.info("adminPatchLocationGroup, patch locationGroupDto {}", locationGroupDto);
            return adminLocationGroupService.patchLocationGroup(locationGroupDto);
        }

    }
