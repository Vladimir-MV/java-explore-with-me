package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.dto.NewLocationGroupDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.LocationGroupMapper;
    import ru.practicum.explorewithme.model.LocationGroup;
    import ru.practicum.explorewithme.repository.LocationGroupRepository;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class AdminLocationGroupServiceImpl implements AdminLocationGroupService {
        final private LocationGroupRepository locationGroupRepository;

//Фича: Создание, удаление, изменение локации(группы).
        @Transactional
        @Override
        public LocationGroupDto createLocationGroup(NewLocationGroupDto newLocationGroupDto) {
            LocationGroup locationGroup = LocationGroupMapper.toLocationGroup(newLocationGroupDto);
            if (newLocationGroupDto.getDescription() != null) {
                    locationGroup.setDescription(newLocationGroupDto.getDescription());
            }
            locationGroupRepository.save(locationGroup);
            log.info("Добавление новой локации(группы) name {}", locationGroup.getName());
            return LocationGroupMapper.toLocationGroupDto(locationGroup);
        }

        @Transactional
        @Override
        public void deleteLocationGroupById(Long id) throws ObjectNotFoundException {
            LocationGroup locationGroup = locationGroupRepository.findById(id).orElseThrow(
                    () -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("LocationGroup with id={} was not found.", id)));
            log.info("Удалена локация(группа) name={}", locationGroup.getName());
            locationGroupRepository.deleteById(id);
        }
        @Transactional
        @Override
        public LocationGroupDto patchLocationGroup(LocationGroupDto locationGroupDto)
                throws ObjectNotFoundException {
            LocationGroup locationGroup = locationGroupRepository
                    .findById(locationGroupDto.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Объект не найден. ",
                    String.format("LocationGroup with id={} was not found.", locationGroupDto.getId())));
            if (locationGroupDto.getName() != null) {
                locationGroup.setName(locationGroupDto.getName());
            }
            if (locationGroupDto.getLat() != 0.0f) {
                locationGroup.setLat(locationGroupDto.getLat());
            }
            if (locationGroupDto.getLon() != 0.0f) {
                locationGroup.setLon(locationGroupDto.getLon());
            }
            if (locationGroupDto.getRadius() != 0.0f) {
                locationGroup.setRadius(locationGroupDto.getRadius());
            }
            if (locationGroupDto.getDescription() != null) {
                locationGroup.setDescription(locationGroupDto.getDescription());
            }
            locationGroupRepository.save(locationGroup);
            log.info("Исправлена локация(группа) name={}", locationGroup.getName());
            return LocationGroupMapper.toLocationGroupDto(locationGroup);
        }
    }
