    package ru.practicum.explorewithme.client;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.EndpointHitDto;


    @Service
    public class RestTemplateClientStat extends RestTemplateClient {


        @Autowired
        public RestTemplateClientStat(@Value("${stats-server.url}") String serverUrl) {

            super(serverUrl);
        }

        public ResponseEntity<Object> createEndpointHitStatistics(EndpointHitDto endpointHit) {

            return post("", endpointHit);
        }

    }
