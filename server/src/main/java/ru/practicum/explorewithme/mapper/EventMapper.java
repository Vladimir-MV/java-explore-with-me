    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.*;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.Location;
    import ru.practicum.explorewithme.model.LocationGroup;
    import ru.practicum.explorewithme.model.State;

    import javax.persistence.CascadeType;
    import javax.persistence.FetchType;
    import javax.persistence.JoinColumn;
    import javax.persistence.ManyToOne;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class EventMapper {
        public static EventFullDto toEventFullDto(Event event) {
            return new EventFullDto(
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDto(event.getCategory()),
                    event.getConfirmedRequests(),
                    event.getCreatedOn(),
                    event.getDescription(),
                    event.getEventDate(),
                    event.getId(),
                    UserMapper.toUserShortDto(event.getInitiator()),
                    EventMapper.toLocationDto(event.getLocation()),
                    event.isPaid(),
                    event.getParticipantLimit(),
                    event.getPublishedOn(),
                    event.getRequestModeration(),
                    event.getState(),
                    event.getTitle(),
                    event.getViews());
        }
        public static LocationDto toLocationDto (Location locat) {
            return new LocationDto(
                    locat.getLat(),
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
            event.setState(State.PENDING);
            event.setCreatedOn(LocalDateTime.now());
            event.setViews(0L);
            return event;
        }


        public static EventShortDto toEventShortDto (Event event) {
            return new EventShortDto (
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDto(event.getCategory()),
                    event.getConfirmedRequests(),
                    event.getEventDate(),
                    event.getId(),
                    UserMapper.toUserShortDto(event.getInitiator()),
                    event.isPaid(),
                    event.getTitle(),
                    event.getViews());
        }
        public static EventShortLocationDto toEventShortLocationDto (Event event) {
            return new EventShortLocationDto (
                    event.getAnnotation(),
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
        public static List<EventShortLocationDto> toListEventShortLocationDto(List<Event> list) {
            List<EventShortLocationDto> listDto = new ArrayList<>();
            for (Event event : list) {
                listDto.add(toEventShortLocationDto(event));
            }
            return listDto;
        }


        public static List<EventFullDto> toListEventFullDto(List<Event> list) {
            List<EventFullDto> listDto = new ArrayList<>();
            for (Event event : list) {
                listDto.add(toEventFullDto(event));
            }
            return listDto;
        }

    }
