    package ru.practicum.explorewithme;

    import org.junit.jupiter.api.Test;

    //    import lombok.RequiredArgsConstructor;
//    import org.junit.jupiter.api.Assertions;
//    import org.junit.jupiter.api.Test;
//    import org.junit.jupiter.api.extension.ExtendWith;
//    import org.mockito.junit.jupiter.MockitoExtension;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.test.context.SpringBootTest;
//    import java.util.*;
//    import javax.transaction.Transactional;
//    import lombok.RequiredArgsConstructor;
//    import org.junit.jupiter.api.Assertions;
//    import org.junit.jupiter.api.Test;
//    import org.junit.jupiter.api.extension.ExtendWith;
//    import org.mockito.junit.jupiter.MockitoExtension;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.test.context.SpringBootTest;
//    import org.springframework.test.annotation.Rollback;
//    import ru.practicum.explorewithme.admin.service.*;
//    import ru.practicum.explorewithme.dto.*;
//    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
//    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
//    import ru.practicum.explorewithme.exceptions.RequestErrorException;
//    import ru.practicum.explorewithme.mapper.UserMapper;
//    import ru.practicum.explorewithme.model.Event;
//    import ru.practicum.explorewithme.model.Location;
//    import ru.practicum.explorewithme.model.User;
//    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventService;
//    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventServiceImpl;
//    import ru.practicum.explorewithme.publicrequest.service.PublicLocationGroupService;
//    import ru.practicum.explorewithme.publicrequest.service.PublicLocationGroupServiceImpl;
//    import ru.practicum.explorewithme.repository.*;
//
//    import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//    @Transactional
//    @ExtendWith(MockitoExtension.class)
//    @SpringBootTest(
//            webEnvironment = SpringBootTest.WebEnvironment.NONE)
//    @RequiredArgsConstructor(onConstructor_ = @Autowired)
    public class LocationGroupIntegrateTest {

        // Тесты для Фичи в процессе.


//
//        @Autowired
//        LocationGroupRepository locationGroupRepository;
//        @Autowired
//        AdminLocationGroupService adminLocationGroupService = new AdminLocationGroupServiceImpl(locationGroupRepository);
//        @Autowired
//        PublicLocationGroupService publicLocationGroupService = new PublicLocationGroupServiceImpl(locationGroupRepository);
//        @Autowired
//        UserRepository userRepository;
//        @Autowired
//        AdminUserService adminUserService = new AdminUserServiceImpl(userRepository);
//        @Autowired
//        CategoryRepository categoryRepository;
//        @Autowired
//        AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl(categoryRepository);
//        @Autowired
//        LocationRepository locationRepository;
//        @Autowired
//        RequestRepository requestRepository;
//        @Autowired
//        EventRepository eventRepository;
//
//        @Autowired
//        PrivateUserEventService privateUserEventService = new PrivateUserEventServiceImpl(userRepository,
//                categoryRepository, eventRepository, requestRepository, locationGroupRepository, locationRepository);
//
//
//        NewUserRequest userRequest = NewUserRequest.builder()
//                .name("Philip Wisozk")
//                .email("Johan.Rempel47@yahoo.com")
//                .build();
//        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
//                .name("Cambridgeshire44")
//                .build();
//        NewLocationGroupDto locationGroupDto = new NewLocationGroupDto("Golyanovo district",
//                55.82096f,
//                37.80258f,
//                300f,
//                "Quam fugiat voluptatibus ut " +
//                        "error adipisci et et.");
//
//        Location location = new Location(null, 55.82096f, 37.80258f);
//
//        NewEventDto newEventDto = NewEventDto.builder()
//                .annotation("Voluptatem et accusantium odit aut dolores voluptatibus.")
//                .category(1L)
//                .description("Cupiditate alias vel neque aut eum aut. " +
//                        " Sed veritatis iste quas enim qui.")
//                .eventDate(LocalDateTime.parse("2022-12-10 04:05:43"))
//                .location(location)
//                .paid(true)
//                .participantLimit(439L)
//                .requestModeration(false)
//                .title("Quam fugiat voluptatibus ut error adipisci et et.")
//                .build();



        @Test
        void whenAddEvent_thenEventSaveLocationGroup() {

        }
//           // userRepository.findById(adminUserService.createUser(userRequest).getId()
//            userRepository.save(UserMapper.toUser(userRequest));
////            adminCategoryService.createCategory(newCategoryDto);
//            ;
////            privateUserEventService.createUserEvent(1L, newEventDto);
////            adminLocationGroupService.createLocationGroup(locationGroupDto);
//
//            Assertions.assertEquals("Johan.Rempel47@yahoo.com", userRepository.findById(1L).get().getEmail());
////            assertEquals(categoryRepository.findById(1L).get().getName(), newCategoryDto.getName());
////            assertEquals(locationGroupRepository.findById(1L).get().getName(), locationGroupDto.getName());
//           // assertEquals(eventRepository.findById(1L).get().getLocationGroup().stream().iterator(), locationGroupDto.getName());
//
//        }
    }


