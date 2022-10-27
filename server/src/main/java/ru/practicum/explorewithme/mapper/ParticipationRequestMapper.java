package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.ParticipationRequest;

import java.util.ArrayList;
import java.util.List;

public class ParticipationRequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus());
    }
    public static List<ParticipationRequestDto> toListParticipationRequestDto(List<ParticipationRequest> list) {
        List<ParticipationRequestDto> listDto = new ArrayList<>();
        for (ParticipationRequest request : list) {
            listDto.add(toParticipationRequestDto(request));
        }
        return listDto;
    }

}
