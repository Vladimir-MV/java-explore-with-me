    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.dto.NewLocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;

    public interface AdminLocationGroupService {

        LocationGroupDto createLocationGroup(NewLocationGroupDto LocationGroupDto);

        void deleteLocationGroupById(Long id) throws ObjectNotFoundException;

        LocationGroupDto patchLocationGroup(LocationGroupDto locationGroupDto)
                throws ObjectNotFoundException;
    }
