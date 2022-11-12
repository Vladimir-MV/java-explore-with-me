    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.*;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.Location;
    import ru.practicum.explorewithme.model.State;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class EventMapper {

        public static EventFullDto toEventFullDto(Event event) {
            EventFullDto eventFullDto = new EventFullDto();
                eventFullDto.setAnnotation(event.getAnnotation());
                eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
                eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
                eventFullDto.setCreatedOn(event.getCreatedOn());
                eventFullDto.setDescription(event.getDescription());
                eventFullDto.setEventDate(event.getEventDate());
                eventFullDto.setId(event.getId());
                eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
                eventFullDto.setLocation(EventMapper.toLocationDto(event.getLocation()));
                eventFullDto.setPaid(event.isPaid());
                eventFullDto.setParticipantLimit(event.getParticipantLimit());
                eventFullDto.setPublishedOn(event.getPublishedOn());
                eventFullDto.setRequestModeration(event.getRequestModeration());
                eventFullDto.setState(event.getState());
                eventFullDto.setTitle(event.getTitle());
                eventFullDto.setViews(event.getViews());
                if (event.getLocationGroup() != null) {
                    eventFullDto.setLocationGroupDtos(LocationGroupMapper
                            .toSetLocationGroupDto(event.getLocationGroup()));
                }
            return eventFullDto;
        }

        public static LocationDto toLocationDto(Location locat) {
            return new LocationDto(locat.getLat(),
                                   locat.getLon());
        }

        public static Event toEvent(NewEventDto eventDto) {
            Event event = new Event();
            event.setAnnotation(eventDto.getAnnotation());
            event.setDescription(eventDto.getDescription());
            event.setEventDate(eventDto.getEventDate());
            event.setLocation(eventDto.getLocation());
            event.setTitle(eventDto.getTitle());
            event.setConfirmedRequests(0L);
            event.setPaid(eventDto.isPaid());
            event.setParticipantLimit(eventDto.getParticipantLimit());
            event.setRequestModeration(eventDto.getRequestModeration());
            event.setCreatedOn(LocalDateTime.now());
            event.setViews(0L);
            return event;
        }

        public static EventShortDto toEventShortDto(Event event) {
            return new EventShortDto(event.getAnnotation(),
                                      CategoryMapper.toCategoryDto(event.getCategory()),
                                      event.getConfirmedRequests(),
                                      event.getEventDate(),
                                      event.getId(),
                                      UserMapper.toUserShortDto(event.getInitiator()),
                                      event.isPaid(),
                                      event.getTitle(),
                                      event.getViews());
        }

        public static EventShortLocationDto toEventShortLocationDto(Event event) {
            return new EventShortLocationDto(event.getAnnotation(),
                                            CategoryMapper.toCategoryDto(event.getCategory()),
                                            event.getConfirmedRequests(),
                                            event.getEventDate(),
                                            event.getId(),
                                            UserMapper.toUserShortDto(event.getInitiator()),
                                            event.isPaid(),
                                            event.getTitle(),
                                            event.getViews(),
                                            LocationGroupMapper.toSetLocationGroupDto(event.getLocationGroup()));
        }

        public static Set<EventShortDto> toSetEventShortDto(Set<Event> set) {
            Set<EventShortDto> setDto = new HashSet<>();
            for (Event event : set) {
                setDto.add(toEventShortDto(event));
            }
            return setDto;
        }

        public static List<EventShortDto> toListEventShortDto(List<Event> list) {
            List<EventShortDto> listDto = new ArrayList<>();
            for (Event event : list) {
                listDto.add(toEventShortDto(event));
            }
            return listDto;
        }

        public static Set<EventShortLocationDto> toSetEventShortLocationDto(Set<Event> set) {
            Set<EventShortLocationDto> setDto = new HashSet<>();
            for (Event event : set) {
                setDto.add(toEventShortLocationDto(event));
            }
            return setDto;
        }

        public static List<EventFullDto> toListEventFullDto(List<Event> list) {
            List<EventFullDto> listDto = new ArrayList<>();
            for (Event event : list) {
                listDto.add(toEventFullDto(event));
            }
            return listDto;
        }
    }
