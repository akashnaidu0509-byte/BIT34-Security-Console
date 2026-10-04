package com.bit34.security_console.controller;

import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.SecurityEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class GraphController {

    private final SecurityEventRepository eventRepository;

    public GraphController(SecurityEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/graph")
    public Map<String, Object> getGraph() {

        List<SecurityEvent> events = eventRepository.findAll();

        Set<String> nodeIds = new HashSet<>();
        List<Map<String, String>> edges = new ArrayList<>();

        for (SecurityEvent event : events) {

            String ipNode = "ip:" + event.getSourceIp();
            nodeIds.add(ipNode);

            if (event.getUsername() != null && !event.getUsername().isBlank()) {

                String userNode = "user:" + event.getUsername();
                nodeIds.add(userNode);

                Map<String, String> edge = new HashMap<>();
                edge.put("source", ipNode);
                edge.put("target", userNode);
                edge.put("relationship", event.getEventType());

                edges.add(edge);
            }
        }

        List<Map<String, String>> nodes = new ArrayList<>();

        for (String nodeId : nodeIds) {

            Map<String, String> node = new HashMap<>();
            node.put("id", nodeId);

            if (nodeId.startsWith("ip:")) {
                node.put("type", "IP");
            } else {
                node.put("type", "USER");
            }

            nodes.add(node);
        }

        return Map.of(
                "nodes", nodes,
                "edges", edges
        );
    }
}