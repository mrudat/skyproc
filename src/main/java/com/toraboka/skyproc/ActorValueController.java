package com.toraboka.skyproc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ActorValueController {

    private String source;

    public class ActorValue {
	int index;
	String name = "UNKNOWN";
	String type = "";
	String effect = "";
	String AVIF = "";
    }

    public Map<String, Object> getActorValues() {
	HashMap<String, Object> result = new HashMap<String, Object>();

	try {
	    result.put("actorValues", readActorValues());
	} catch (IOException e) {
	    result.put("actorValues", new ActorValue[0]);
	}

	return result;
    }

    private List<ActorValue> readActorValues() throws IOException {
	Path path = Paths.get(source);

	BufferedReader in = Files.newBufferedReader(path);

	ActorValue[] values = new ActorValue[256];

	String line;
	while ((line = in.readLine()) != null) {
	    Scanner tokenizer = new Scanner(line);
	    try {
		int index = Integer.valueOf(tokenizer.next());

		values[index] = new ActorValue();

		values[index].index = index;
		values[index].name = tokenizer.next();
		values[index].type = tokenizer.next();
		values[index].effect = tokenizer.next();
		values[index].AVIF = tokenizer.next();
	    } catch (NumberFormatException ex) {
	    } finally {
		tokenizer.close();
	    }
	}

	for (int i = 0; i < values.length; i++) {
	    if (values[i] != null) {
		values[i] = new ActorValue();
		values[i].index = i;
	    }
	}
	return Arrays.asList(values);
    }

    public void setProperties(Map<String, String> properties) {
	if (properties.containsKey("source")) {
	    source = properties.get("source");
	}
    }
}
