    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.model.AdminUpdateEventRequest;

    import java.util.List;
    import java.util.Optional;

    public class AdminEventServiceImpl implements AdminEventService{

        @Override
        public List<EventFullDto> getEventsByUsersStatesCategories(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
            return null;
        }

        @Override
        public AdminUpdateEventRequest putEventById(Optional<Long> eventId, Optional<AdminUpdateEventRequest> adminUpdateEventRequest) {
            return null;
        }

        @Override
        public EventFullDto patchPublishEventById(Optional<Long> eventId) {
            return null;
        }

        @Override
        public EventFullDto patchRejectEventById(Optional<Long> eventId) {
            return null;
        }
    }
