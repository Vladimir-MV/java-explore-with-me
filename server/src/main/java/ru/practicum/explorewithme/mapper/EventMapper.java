    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.NewEventDto;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.State;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

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
                    event.getLocation(),
                    event.getPaid(),
                    event.getParticipantLimit(),
                    event.getPublishedOn(),
                    event.getRequestModeration(),
                    event.getState(),
                    event.getTitle(),
                    event.getViews());
        }

        public static Event toEvent(NewEventDto eventDto) {
            Event event = new Event();
            event.setAnnotation(eventDto.getAnnotation());
            event.setDescription(eventDto.getDescription());
            event.setEventDate(eventDto.getEventDate());
            event.setEventDate(eventDto.getEventDate());
            event.setLocation(eventDto.getLocation());
            event.setTitle(eventDto.getTitle());
            event.setConfirmedRequests(0L);
            event.setPaid(eventDto.getPaid());
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
                    event.getPaid(),
                    event.getTitle(),
                    event.getViews());
        }
        public static List<EventShortDto> toListEventShortDto(List<Event> list) {
            List<EventShortDto> listDto = new ArrayList<>();
            for (Event event : list) {
                listDto.add(toEventShortDto(event));
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
