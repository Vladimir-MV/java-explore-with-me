    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.LocationGroupMapper;
    import ru.practicum.explorewithme.model.LocationGroup;
    import ru.practicum.explorewithme.repository.LocationGroupRepository;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicLocationGroupServiceImpl implements PublicLocationGroupService {

        private final LocationGroupRepository locationGroupRepository;

        @Transactional(readOnly = true)
        @Override
        public List<LocationGroupDto> getLocationGroups() throws ObjectNotFoundException {
            List<LocationGroup> locationGroupList = locationGroupRepository.findAll();
            if (locationGroupList.isEmpty()) {
                throw new ObjectNotFoundException("Объект не найден. ",
                        String.format("LocationGroupList locationGroupList {} was not found.",
                        locationGroupList));
            }
            log.info("Найден список локация(группа) locationGroupList {}", locationGroupList);
            return LocationGroupMapper.toListLocationGroupDto(locationGroupList);
        }

        @Transactional(readOnly = true)
        @Override
        public LocationGroupDto getLocationGroupById(Long id) throws ObjectNotFoundException {
            LocationGroup locationGroup = locationGroupRepository
                    .findById(id).orElseThrow( () -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("LocationGroup with id={} was not found.", id)));
            return LocationGroupMapper.toLocationGroupDto(locationGroup);
        }
    }
