    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.LocationGroupDto;
    import ru.practicum.explorewithme.dto.NewLocationGroupDto;
    import ru.practicum.explorewithme.model.LocationGroup;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class LocationGroupMapper {

        public static LocationGroup toLocationGroup(NewLocationGroupDto locationGroupDto) {
            LocationGroup locationGroup = new LocationGroup();
            locationGroup.setName(locationGroupDto.getName());
            locationGroup.setLat(locationGroupDto.getLat());
            locationGroup.setLon(locationGroupDto.getLon());
            locationGroup.setRadius(locationGroupDto.getRadius());
            return locationGroup;

        }

        public static LocationGroupDto toLocationGroupDto(LocationGroup locationGroup) {
            return new LocationGroupDto(locationGroup.getId(),
                                        locationGroup.getName(),
                                        locationGroup.getLat(),
                                        locationGroup.getLon(),
                                        locationGroup.getRadius(),
                                        locationGroup.getDescription());
        }

        public static Set<LocationGroupDto> toSetLocationGroupDto(Set<LocationGroup> set) {
            Set<LocationGroupDto> setDto = new HashSet<>();
            for (LocationGroup locationGroup : set) {
                setDto.add(toLocationGroupDto(locationGroup));
            }
            return setDto;
        }

        public static List<LocationGroupDto> toListLocationGroupDto(List<LocationGroup> list) {
            List<LocationGroupDto> setDto = new ArrayList<>();
            for (LocationGroup locationGroup : list) {
                setDto.add(toLocationGroupDto(locationGroup));
            }
            return setDto;
        }
    }
