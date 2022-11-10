    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;

    import java.util.List;

    public interface PublicLocationGroupService {


        List<LocationGroupDto> getLocationGroups() throws ObjectNotFoundException;

        LocationGroupDto getLocationGroupById(Long id) throws ObjectNotFoundException;
    }
