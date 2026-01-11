package springaidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
class EtlController {

    private static final Logger log = LoggerFactory.getLogger(EtlController.class);
    private final VectorStore vectorStore;
    private final EtlProperties etlProperties;

    EtlController(VectorStore vectorStore, EtlProperties etlProperties) {
        this.vectorStore = vectorStore;
        this.etlProperties = etlProperties;
    }

    @PostMapping("/etl")
    public EtlResponse loadDocuments(@RequestBody EtlRequest request) {
        Path directoryPath = Paths.get(request.path());

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new IllegalArgumentException("Invalid directory path: " + request.path());
        }

        List<Document> allDocuments = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(directoryPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".md"))
                    .filter(this::isNotIgnored)
                    .forEach(path -> {
                        log.info("Processing file: {}", path);
                        FileSystemResource resource = new FileSystemResource(path);
                        MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, MarkdownDocumentReaderConfig.builder().build());
                        allDocuments.addAll(reader.read());
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error reading files", e);
        }

        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 10000, true);
        List<Document> splitDocuments = splitter.apply(allDocuments);

        vectorStore.add(splitDocuments);

        return new EtlResponse("Successfully loaded documents", splitDocuments.size(), allDocuments.size());
    }

    private boolean isNotIgnored(Path path) {
        for (Path part : path) {
            if (etlProperties.getIgnoredPaths().contains(part.toString())) {
                return false;
            }
        }
        return true;
    }

    record EtlRequest(String path) {}
    record EtlResponse(String message, int loadedChunks, int sourceDocuments) {}
}