    package ru.practicum.explorewithme.client;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.web.client.RestTemplateBuilder;
    import org.springframework.context.annotation.PropertySource;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
    import org.springframework.stereotype.Service;
//    import org.springframework.web.util.DefaultUriBuilderFactory;
    import org.springframework.web.util.DefaultUriBuilderFactory;
    import ru.practicum.explorewithme.model.EndpointHit;


    @Service
//    @PropertySource("classpath:application.properties")
    public class RestTemplateClientStat extends RestTemplateClient {

      //  private static final String API_PREFIX = "/hit";

        @Autowired
        public RestTemplateClientStat(@Value("${stats-server.url}") String serverUrl) {
            super(serverUrl);
        }

        public ResponseEntity<Object> createEndpointHitStatistics (EndpointHit endpointHit) {
            return post("", endpointHit);
        }

    }
