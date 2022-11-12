    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.dto.NewLocationGroupDto;

    public interface AdminLocationGroupService {

        LocationGroupDto createLocationGroup(NewLocationGroupDto locationGroupDto);

        void deleteLocationGroupById(Long id);


        LocationGroupDto patchLocationGroup(LocationGroupDto locationGroupDto);
    }
