package org.example.lesson20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class CatalogController {
    private static final Logger log = LoggerFactory.getLogger(CatalogController.class);

    private final String catalogName;
    private final Environment environment;

    public CatalogController(
            @Value("${app.catalog-name}") String catalogName,
            Environment environment
    ) {
        this.catalogName = catalogName;
        this.environment = environment;
    }

    @GetMapping
    public CatalogInfo info() {
        String[] activeProfiles = environment.getActiveProfiles();
        String profile = activeProfiles.length == 0 ? "default" : activeProfiles[0];

        // TODO 3: 使用占位符记录 catalogName 和 profile。
        log.info("TODO");

        return new CatalogInfo(catalogName, profile);
    }
}
