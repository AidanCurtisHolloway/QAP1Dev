package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    private final SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Mock
    private SuggestionsDatabase mockSuggestionDB;

    @Test
    public void testGenerateSuggestions() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));
        boolean testInstanceSame = true;
        Assertions.assertTrue(testInstanceSame);
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    @Test
    public void testSuggestionsAsMock() {
        Map<String, Integer> wordMapForTest = new HashMap<>();
        wordMapForTest.put("test", 1);

        Mockito.when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);
        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
    }

    @Test
    public void testGenerateSuggestionsEmptyDictionary() throws IOException {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("empty.txt").getPath()));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hello").isEmpty());
    }

    @Test
    public void testGenerateSuggestionsNullInput() throws IOException {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));
        Assertions.assertDoesNotThrow(() -> {
            suggestionEngine.generateSuggestions(null);
        });
    }
    
}
