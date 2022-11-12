    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.LocationGroupDto;

    import java.util.List;

    public interface PublicLocationGroupService {

        List<LocationGroupDto> getLocationGroups();

        LocationGroupDto getLocationGroupById(Long id);
    }
